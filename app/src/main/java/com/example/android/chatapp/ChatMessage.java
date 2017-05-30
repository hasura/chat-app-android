package com.example.android.chatapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amogh on 26/5/17.
 */

public class ChatMessage {
    @SerializedName("content")
    String content;

    @SerializedName("time")
    String time;

    @SerializedName("sender")
    Boolean sender;

    public String getContent(){
        return content;
    }

    public String getTime(){
        return time;
    }

    public Boolean getSender(){
        return sender;
    }

    public ChatMessage(String content,String time,Boolean sender){
        this.content = content;
        this.time = time;
        this.sender = sender;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setSender(Boolean sender){
        this.sender = sender;
    }

}
