package com.example.android.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by amogh on 30/5/17.
 */

public class AuthenticationActivity extends AppCompatActivity {

    Button signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        signup = (Button) findViewById(R.id.authentication_signup_button);
        login = (Button) findViewById(R.id.authentication_login_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthenticationActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthenticationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
