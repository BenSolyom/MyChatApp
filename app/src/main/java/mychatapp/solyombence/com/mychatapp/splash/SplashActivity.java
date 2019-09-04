package mychatapp.solyombence.com.mychatapp.splash;

import androidx.appcompat.app.AppCompatActivity;
import mychatapp.solyombence.com.mychatapp.R;
import mychatapp.solyombence.com.mychatapp.lobby.ChatLobbyActivity;
import mychatapp.solyombence.com.mychatapp.login.LoginActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent homeIntent;
            if (sharedPreferences.getBoolean("login", false)) {
                homeIntent = new Intent(SplashActivity.this, ChatLobbyActivity.class);
            } else {
                homeIntent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(homeIntent);
            finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
