package com.example.android.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.hasura.sdk.auth.HasuraException;
import io.hasura.sdk.auth.MessageResponse;
import io.hasura.sdk.core.Callback;
import io.hasura.sdk.utils.Hasura;

/**
 * Created by amogh on 30/5/17.
 */

public class LoginActivity extends AppCompatActivity {

    EditText mobile;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mobile = (EditText) findViewById(R.id.login_mobile);
        login = (Button) findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hasura.auth.loginUsingMobileOTP(mobile.getText().toString())
                        .enqueue(new Callback<MessageResponse, HasuraException>() {
                            @Override
                            public void onSuccess(MessageResponse messageResponse) {
                                //Handle this part after SDK is done.
                            }

                            @Override
                            public void onFailure(HasuraException e) {

                            }
                        });
            }
        });
    }
}
