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
        String table = "chat_message";

        @SerializedName("objects")
        List<ChatMessage> objects;
    }

    public InsertMessageQuery(String contents, String timestamp,Integer senderId,Integer receiverId,Integer userId){
        args = new Args();
        args.objects = new ArrayList<>();
        ChatMessage chat = new ChatMessage(contents,timestamp,senderId,receiverId,userId);
        args.objects.add(chat);
    }
}
