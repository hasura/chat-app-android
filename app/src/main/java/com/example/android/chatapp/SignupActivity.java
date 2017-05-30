package com.example.android.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.hasura.sdk.auth.AuthResponse;
import io.hasura.sdk.auth.HasuraException;
import io.hasura.sdk.core.Callback;
import io.hasura.sdk.utils.Hasura;

/**
 * Created by amogh on 30/5/17.
 */

public class SignupActivity extends AppCompatActivity {

    EditText username;
    EditText mobile;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        username = (EditText) findViewById(R.id.signup_username);
        mobile = (EditText) findViewById(R.id.signup_mobile);
        signup = (Button)findViewById(R.id.signup_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hasura.auth.registerUsingMobileOTP(mobile.getText().toString(),username.getText().toString())
                        .enqueue(new Callback<AuthResponse, HasuraException>() {
                            @Override
                            public void onSuccess(AuthResponse authResponse) {
                                if(authResponse.getAuthToken() == null){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(SignupActivity.this);
                                    final EditText otp = new EditText(SignupActivity.this);
                                    alert.setMessage("Enter the OTP received");
                                    alert.setView(otp);
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Hasura.auth.verifyOTPForMobileLogin(mobile.getText().toString(),otp.getText().toString())
                                                    .enqueue(new Callback<AuthResponse, HasuraException>() {
                                                        @Override
                                                        public void onSuccess(AuthResponse authResponse) {
                                                            //set userId to sharedPreferences here
                                                            Intent i = new Intent(SignupActivity.this,ContactsActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                        }

                                                        @Override
                                                        public void onFailure(HasuraException e) {
                                                            Toast.makeText(SignupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onFailure(HasuraException e) {
                                Toast.makeText(SignupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
