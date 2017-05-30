package com.example.android.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amogh on 29/5/17.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    int senderId;

    private static final String DATABASE_NAME = "chatapp";

    private static final String TABLE_NAME = "messages";

    private static final String KEY_ID  = "id";
    private static final String KEY_CONTENTS = "contents";
    private static final String KEY_TIME = "time";
    private static final String KEY_SENDER = "sender";
    private static final String KEY_RECEIVER = "receiver";
    //private static final String KEY_CONVERSATION = "conversation_id";

    String time;//extract this value
    Integer userId;//extract this value



    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CONTENTS +
                " TEXT," + KEY_TIME + " TEXT," + KEY_SENDER + " INTEGER," + KEY_RECEIVER + " INTEGER" + ")";
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLES IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void insertMessage(ChatMessage chatMessage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENTS,chatMessage.getContent());
        values.put(KEY_TIME,chatMessage.getTime());
        values.put(KEY_SENDER,chatMessage.getSender());
        values.put(KEY_RECEIVER,chatMessage.getReceiver());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public List<ChatMessage> getAllMessages(){
        List<ChatMessage> chatMessages = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                ChatMessage chatMessage = new ChatMessage("test",time,-1,-1);
                chatMessage.setContent(cursor.getString(0));
                chatMessage.setTime(cursor.getString(1));
                senderId = cursor.getInt(2);
                if(senderId == userId){
                    chatMessage.setSender(userId);
                    chatMessage.setReceiver(cursor.getInt(3));
                }else{
                    chatMessage.setSender(cursor.getInt(2));
                    chatMessage.setReceiver(userId);
                }
                chatMessages.add(chatMessage);
            }while (cursor.moveToNext());
        }
        return chatMessages;
    }
}
