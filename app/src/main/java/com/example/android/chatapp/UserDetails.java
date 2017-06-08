package com.example.android.chatapp;

/**
 * Created by amogh on 5/6/17.
 */
public class UserDetails {
    String name;
    String status;
    byte[] picture;
    int id;

    public void setName(String name){
        this.name = name;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setPicture(byte[] picture){
        this.picture = picture;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getStatus(){
        return status;
    }

    public byte[] getPicture(){
        return picture;
    }

    public int getId(){
        return id;
    }

    public UserDetails(){

    }
}
