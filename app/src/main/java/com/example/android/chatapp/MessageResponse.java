package com.example.android.chatapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amogh on 8/6/17.
 */

public class MessageResponse {
    @SerializedName("message")
    String message;

    public String getMessage(){
        return message;
    }
}
