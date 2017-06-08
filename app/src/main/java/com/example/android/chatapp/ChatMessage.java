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

    @SerializedName("sender_id")
    Integer sender;

    @SerializedName("receiver_id")
    Integer receiver;

    public String getContent(){
        return content;
    }

    public String getTime(){
        return time;
    }

    public Integer getSender(){
        return sender;
    }

    public Integer getReceiver(){
        return receiver;
    }

    public ChatMessage(String content,String time,Integer sender,Integer receiver){
        this.content = content;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setSender(Integer sender){
        this.sender = sender;
    }

    public void setReceiver(Integer receiver){
        this.receiver = receiver;
    }

}
