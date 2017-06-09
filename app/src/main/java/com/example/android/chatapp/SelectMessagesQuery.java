package com.example.android.chatapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amogh on 8/6/17.
 */

public class SelectMessagesQuery {
    @SerializedName("type")
    String type = "select";

    @SerializedName("args")
    Args args;

    class Args{
        @SerializedName("table")
        String table = "chat_message";

        @SerializedName("columns")
        String[] columns = {"content","time","sender_id","receiver_id","user_id"};

        @SerializedName("where")
        Where where;
    }

    class Where{
        @SerializedName("time")
        GT gt;
    }
    class GT{
        @SerializedName("$gt")
        String timestamp;
    }

    public SelectMessagesQuery(String time){
        args = new Args();
        args.where = new Where();
        args.where.gt = new GT();
        args.where.gt.timestamp = time;
    }

}
