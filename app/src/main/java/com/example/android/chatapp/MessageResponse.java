package com.example.android.chatapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amogh on 8/6/17.
 */

public class MessageResponse {
    @SerializedName("affected_rows")
    Integer affectedRows;

    public Integer getAffectedRows(){
        return affectedRows;
    }
}
