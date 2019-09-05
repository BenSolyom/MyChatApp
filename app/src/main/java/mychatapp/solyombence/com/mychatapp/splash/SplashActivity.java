package mychatapp.solyombence.com.mychatapp.splash;

import androidx.appcompat.app.AppCompatActivity;
import mychatapp.solyombence.com.mychatapp.R;
import mychatapp.solyombence.com.mychatapp.lobby.ChatLobbyActivity;
import mychatapp.solyombence.com.mychatapp.login.LoginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

// The splash activity, which is shown upon starting the application
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // 3 second timeout
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent homeIntent;
            if (auth.getCurrentUser() != null) { // If the user is logged in, the chat lobby activity will start up
                homeIntent = new Intent(SplashActivity.this, ChatLobbyActivity.class);
            } else { // If the user is not logged in, the login activity will start up
                homeIntent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(homeIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Starting the fading animation
            finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
