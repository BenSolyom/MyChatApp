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

import java.util.ArrayList;

public class ChatLobbyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> crNames = new ArrayList<>();
    private ArrayList<String> crDescriptions = new ArrayList<>();

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_lobby);

        initChatrooms();

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
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
        crNames.add("Games");
        crDescriptions.add("Talk about video games");

        crNames.add("Sports");
        crDescriptions.add("Everything about sports");

        crNames.add("Movies");
        crDescriptions.add("Movies and series");

        crNames.add("Food");
        crDescriptions.add("Food and drinks");

        crNames.add("Cars");
        crDescriptions.add("Everything about cars");

        crNames.add("Random");
        crDescriptions.add("Talk about random topics");

        crNames.add("Animals");
        crDescriptions.add("Pets and animals");

        crNames.add("Electronics");
        crDescriptions.add("Talk about the newest hardware");

        crNames.add("University");
        crDescriptions.add("Talk with university students");

        crNames.add("Fashion");
        crDescriptions.add("Everything about fashion");

        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new LobbyAdapter(this, crNames, crDescriptions);
        recyclerView.setAdapter(mAdapter);
    }
}
