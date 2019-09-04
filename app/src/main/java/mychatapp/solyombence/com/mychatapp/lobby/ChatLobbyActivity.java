package mychatapp.solyombence.com.mychatapp.lobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mychatapp.solyombence.com.mychatapp.R;
import mychatapp.solyombence.com.mychatapp.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatLobbyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Chatroom> chatrooms = new ArrayList<>();

    private DatabaseReference dbRef;

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_lobby);

        dbRef = FirebaseDatabase.getInstance().getReference();


        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        initChatrooms();

        /*dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Messages").child(chatroomName).getChildrenCount() != 0) {
                    for (DataSnapshot messages : dataSnapshot.child("Messages").child(chatroomName).getChildren()) {
                        messages.getChildren()
                        Message message = messages.getValue(Message.class);
                        messagesList.add(message);
                    }
                    messageAdapter.notifyDataSetChanged();
                    userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        Intent intent = new Intent(ChatLobbyActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initChatrooms() {

        chatrooms.add(new Chatroom("Games", "Talk about video games"));
        chatrooms.add(new Chatroom("Sports", "Everything about sports"));
        chatrooms.add(new Chatroom("Movies", "Movies and series"));
        chatrooms.add(new Chatroom("Food", "Food and drinks"));
        chatrooms.add(new Chatroom("Cars", "Everything about cars"));
        chatrooms.add(new Chatroom("Random", "Talk about random topics"));
        chatrooms.add(new Chatroom("Animals", "Pets and animals"));
        chatrooms.add(new Chatroom("Electronics", "Talk about the newest hardware"));
        chatrooms.add(new Chatroom("University", "Talk with university students"));

        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ChatroomAdapter(this, chatrooms);
        recyclerView.setAdapter(mAdapter);
    }
}
