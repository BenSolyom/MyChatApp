package mychatapp.solyombence.com.mychatapp.chatroom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mychatapp.solyombence.com.mychatapp.R;

public class ChatActivity extends AppCompatActivity {

    private boolean loaded;

    private String chatroomName;
    private String userName;
    private String timeStamp;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private Button SendMessageButton;
    private ImageButton GalleryButton;
    private ImageButton CameraButton;
    private EditText MessageInputText;

    private final List<Message> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;


    private String saveCurrentTime, saveCurrentDate;

    public static final int GET_FROM_GALLERY = 1;
    public static final int GET_FROM_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images");

        Intent intent = getIntent();
        chatroomName = intent.getStringExtra("chatroomname");

        IntialiseControllers();

        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendMessage();
            }
        });
        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), GET_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Upload from gallery

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            userName = mAuth.getCurrentUser().getDisplayName();
            timeStamp = getCurrentTimeStamp();
            Message message = new Message(userName, bitmap, chatroomName, timeStamp, "image");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmapData = baos.toByteArray();

            UploadTask uploadTask = storageRef.putBytes(bitmapData);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ChatActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

            messagesList.add(message);
            messageAdapter.notifyDataSetChanged();
            userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
        }


        //Upload from camera

        else if (requestCode==GET_FROM_CAMERA && resultCode == Activity.RESULT_OK) {

            Bitmap bitmap = null;
            bitmap = (Bitmap) data.getExtras().get("data");

            userName = mAuth.getCurrentUser().getDisplayName();
            timeStamp = getCurrentTimeStamp();
            Message message = new Message(userName, bitmap, chatroomName, timeStamp, "image");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmapData = baos.toByteArray();

            UploadTask uploadTask = storageRef.putBytes(bitmapData);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    exception.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });

            messagesList.add(message);
            messageAdapter.notifyDataSetChanged();
            userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
        }
    }

    private void IntialiseControllers()
    {
        SendMessageButton = findViewById(R.id.send_message_btn);
        GalleryButton = findViewById(R.id.gallery_button);
        CameraButton = findViewById(R.id.camera_button);
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
                long childrenCount = dataSnapshot.child("Messages").child(chatroomName).getChildrenCount();
                if (childrenCount != 0 && !loaded) {
                    int i = 0;
                    for (DataSnapshot messages : dataSnapshot.child("Messages").child(chatroomName).getChildren()) {
                        if (childrenCount - i++ <= 50) {
                            Message message = messages.getValue(Message.class);
                            messagesList.add(message);
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                    userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                    loaded = true;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                databaseError.toException().printStackTrace();
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