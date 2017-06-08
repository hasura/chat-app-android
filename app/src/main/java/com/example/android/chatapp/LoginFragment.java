package com.example.android.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.hasura.sdk.auth.HasuraUser;
import io.hasura.sdk.auth.responseListener.AuthResponseListener;
import io.hasura.sdk.core.HasuraException;

import static com.example.android.chatapp.Global.user;

/**
 * Created by amogh on 1/6/17.
 */

public class LoginFragment extends Fragment {

    EditText mobile;
    Button login_button;
    View parentHolder;
    EditText username;


    public LoginFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        parentHolder = inflater.inflate(R.layout.fragment_login,container,false);

        mobile = (EditText) parentHolder.findViewById(R.id.login_mobile);
        login_button = (Button) parentHolder.findViewById(R.id.login_button);
        username = (EditText) parentHolder.findViewById(R.id.login_username);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                user.setUsername(username.getText().toString());
                user.setMobile(mobile.getText().toString());
                user.enableMobileOtpLogin();

                user.login(new AuthResponseListener() {
                    @Override
                    public void onSuccess(HasuraUser hasuraUser) {
                        Global.user = hasuraUser;
                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setMessage("Enter the OTP you received");
                        final EditText otp = new EditText(v.getContext());
                        alert.setView(otp);
                        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                user.otpLogin(otp.getText().toString(), new AuthResponseListener() {
                                    @Override
                                    public void onSuccess(HasuraUser hasuraUser) {
                                        Global.user = hasuraUser;
                                        Intent i = new Intent(getActivity().getApplicationContext(),ContactsActivity.class);
                                        startActivity(i);
                                        getActivity().finish();
                                    }

                                    @Override
                                    public void onFailure(HasuraException e) {
                                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        alert.show();
                    }

                    @Override
                    public void onFailure(HasuraException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                /*user.sendOtpToMobile(new OtpStatusListener() {
                    @Override
                    public void onSuccess() {

                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setMessage("Enter the OTP you received");
                        final EditText otp = new EditText(v.getContext());
                        alert.setView(otp);
                        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                user.otpLogin(otp.getText().toString(), new AuthResponseListener() {
                                    @Override
                                    public void onSuccess(HasuraUser hasuraUser) {
                                        Intent i = new Intent(getActivity().getApplicationContext(),ContactsActivity.class);
                                        startActivity(i);
                                        getActivity().finish();
                                    }

                                    @Override
                                    public void onFailure(HasuraException e) {
                                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        alert.show();
                    }

                    @Override
                    public void onFailure(HasuraException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
                /*Intent i = new Intent(getActivity().getApplicationContext(),ContactsActivity.class);
                startActivity(i);
                getActivity().finish();*/
            }
        });


        return parentHolder;
    }
}
