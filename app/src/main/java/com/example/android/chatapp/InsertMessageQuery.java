package com.example.android.chatapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amogh on 8/6/17.
 */

public class InsertMessageQuery {
    @SerializedName("type")
    String type = "insert";

    @SerializedName("args")
    Args args;

    class Args{
        @SerializedName("table")
        String table = "chatmessage";

        @SerializedName("messages")
        List<ChatMessage> messages;
    }

    public InsertMessageQuery(String contents, String timestamp,Integer senderId,Integer receiverId,Integer userId){
        args = new Args();
        args.messages = new ArrayList<>();
        ChatMessage chat = new ChatMessage(contents,timestamp,senderId,receiverId,userId);
        args.messages.add(chat);
    }
}
