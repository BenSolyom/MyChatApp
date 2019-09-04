package mychatapp.solyombence.com.mychatapp.chatroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import mychatapp.solyombence.com.mychatapp.R;

public class ChatActivity extends AppCompatActivity {

    private String messageReceiverID, messageReceiverName, messageReceiverImage, messageSenderID;
    private boolean loaded;

    private String chatroomName;
    private String userName;
    private String timeStamp;
    private String messageText;
    private CircleImageView userImage;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

    private Button SendMessageButton;
    private ImageButton SendFilesButton;
    private EditText MessageInputText;

    private final List<Message> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;


    private String saveCurrentTime, saveCurrentDate;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


        mAuth = FirebaseAuth.getInstance();
        //messageSenderID = mAuth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        chatroomName = intent.getStringExtra("chatroomname");

        IntialiseControllers();

        //userName.setText(messageReceiverName);
        //Picasso.get().load(messageReceiverImage).placeholder(R.drawable.chat_icon_small).into(userImage);
        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendMessage();
            }
        });
    }




    private void IntialiseControllers()
    {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //userName = findViewById(R.id.username);
        //userImage = findViewById(R.id.avatar);

        SendMessageButton = findViewById(R.id.send_message_btn);
        SendFilesButton = findViewById(R.id.send_image_button);
        MessageInputText = findViewById(R.id.input_message);

        messageAdapter = new MessageAdapter(messagesList);
        userMessagesList = findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        loaded = false;
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Messages").child(chatroomName).getChildrenCount() != 0 && !loaded) {
                    for (DataSnapshot messages : dataSnapshot.child("Messages").child(chatroomName).getChildren()) {
                        Message message = messages.getValue(Message.class);
                        messagesList.add(message);
                    }
                    messageAdapter.notifyDataSetChanged();
                    userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                    loaded = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void SendMessage()
    {
        String messageText = MessageInputText.getText().toString();

        if (TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this, "Write your message first...", Toast.LENGTH_SHORT).show();
        }
        else {
            userName = mAuth.getCurrentUser().getDisplayName();
            timeStamp = getCurrentTimeStamp();

            Message message = new Message(userName, messageText, chatroomName, timeStamp, "text");
            String messageId = dbRef.child("Messages").child(chatroomName).push().getKey();
            dbRef.child("Messages").child(chatroomName).child(messageId).setValue(message).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                        Toast.makeText(ChatActivity.this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    MessageInputText.setText("");
                }
            });

            messagesList.add(message);
            messageAdapter.notifyDataSetChanged();
            userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
        }
    }

    private String getCurrentTimeStamp() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        return saveCurrentDate + " - " + saveCurrentTime;
    }
}