package com.example.android.chatapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amogh on 29/5/17.
 */

public class Contact {
    @SerializedName("name")
    String name;

    @SerializedName("contents")
    String contents;

    @SerializedName("time")
    String time;

    @SerializedName("sender")
    Boolean sender;

    @SerializedName("user_id")
    Integer userId;

    public Contact(String name,String contents,String time,Boolean sender,Integer userId){
        this.name = name;
        this.contents = contents;
        this.time = time;
        this.sender = sender;
        this.userId = userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setContents(String contents){
        this.contents = contents;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setSender(Boolean sender){
        this.sender = sender;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public String getContents(){
        return contents;
    }

    public String getTime(){
        return time;
    }

    public Boolean getSender(){
        return sender;
    }

    public Integer getUserId(){
        return userId;
    }
}
