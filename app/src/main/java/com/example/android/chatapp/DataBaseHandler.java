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

    int senderId_d = 1;
    int receiverId = 2;
    int senderId;

    private static final String DATABASE_NAME = "chatapp";

    private static final String TABLE_MESSAGE = "messages";
    private static final String TABLE_USER = "userdetails";

    private static final String KEY_ID  = "id";
    private static final String MESSAGE_CONTENTS = "contents";
    private static final String MESSAGE_TIME = "timestamp";
    private static final String MESSAGE_SENDER = "sender_id";
    private static final String MESSAGE_RECEIVER = "receiver_id";

    private static final String USER_NAME = "name";
    private static final String USER_STATUS = "status";
    private static final String USER_PICTURE = "picture";
    private static final String USER_ID = "id";

    String timeStampStr;


    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE + "(" + MESSAGE_CONTENTS +
                " TEXT," + MESSAGE_TIME + " TEXT," + MESSAGE_SENDER + " INTEGER," + MESSAGE_RECEIVER + " INTEGER," + USER_ID + "INTEGER" + ")";

        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER +
                "(" + USER_NAME + " TEXT," + USER_STATUS + " TEXT," + USER_PICTURE + " BLOB," + USER_ID + " INTEGER" + ")";

        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void insertMessage(ChatMessage chatMessage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MESSAGE_CONTENTS,chatMessage.getContent());
        values.put(MESSAGE_TIME,chatMessage.getTime());
        values.put(MESSAGE_SENDER,chatMessage.getSender());
        values.put(MESSAGE_RECEIVER,chatMessage.getReceiver());
        //values.put(USER_ID,chatMessage.getUserId());

        db.insert(TABLE_MESSAGE,null,values);
        db.close();
    }

    public void insertUserDetails(UserDetails userDetails){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME,userDetails.getName());
        values.put(USER_STATUS,userDetails.getStatus());
        values.put(USER_PICTURE,userDetails.getPicture());
        values.put(USER_ID,userDetails.getId());

        db.insert(TABLE_USER,null,values);
        db.close();

    }

    public UserDetails getProfile(){
        UserDetails userDetails = new UserDetails();

        String selectProfileQuery = "SELECT NAME, STATUS, PICTURE, ID FROM " + TABLE_USER + " WHERE ( ID = " + Global.user.getId() + ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectProfileQuery,null);

        if(cursor.moveToFirst()){
            userDetails.setName(cursor.getString(0));
            userDetails.setStatus(cursor.getString(1));
            userDetails.setPicture(cursor.getBlob(2));
            userDetails.setId(cursor.getInt(3));
        }

        cursor.close();
        db.close();
        return userDetails;

    }

    public List<ChatMessage> getAllMessages(){
        List<ChatMessage> chatMessages = new ArrayList<>();

        receiverId = Global.receiverId;

        String selectQuery = "SELECT CONTENTS, TIMESTAMP, SENDER_ID, RECEIVER_ID FROM " + TABLE_MESSAGE +
                " WHERE ( ( sender_id = " + Global.user.getId()  + " AND receiver_id = " + receiverId + ") OR" +
                " ( sender_id = " + receiverId + " AND receiver_id = " + Global.user.getId() +
                " ) ) ORDER BY TIMESTAMP ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                ChatMessage chatMessage = new ChatMessage("test","0",-1,-1,-1);
                chatMessage.setContent(cursor.getString(0));
                chatMessage.setTime(cursor.getString(1));
                senderId = cursor.getInt(2);
                if(senderId == senderId_d ){
                    chatMessage.setSender(senderId_d);
                    chatMessage.setReceiver(cursor.getInt(3));
                }else{
                    chatMessage.setSender(cursor.getInt(2));
                    chatMessage.setReceiver(senderId_d);
                }
                chatMessages.add(chatMessage);
            }while (cursor.moveToNext());
        }
        db.close();
        return chatMessages;
    }

    public String getLatest(){
        String selectLatest = "SELECT TIMESTAMP FROM " + TABLE_MESSAGE +
                " ORDER BY TIMESTAMP DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectLatest,null);

        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }

        return null;
    }

    public List<ChatMessage> getAllContacts(){
        List<ChatMessage> contacts = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();

        String selectContactIdQuery = "SELECT CASE " +
                " WHEN SENDER_ID = " + Global.senderId + " THEN RECEIVER_ID " +
                " WHEN RECEIVER_ID = " + Global.senderId + " THEN SENDER_ID " +
                " ELSE -1" +
                " END " +
                " FROM "+ TABLE_MESSAGE + " ORDER BY TIMESTAMP DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectContactIdQuery,null);

        if(cursor.moveToFirst()){
            do {
                if(cursor.getInt(0) != -1) {
                    if(!(ids.contains(cursor.getInt(0)))) {
                        ids.add(cursor.getInt(0));
                    }
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        int i;
        for (i = 0;i < ids.size();i++){
            String selectContactQuery = "SELECT * FROM " + TABLE_MESSAGE +
                    " WHERE SENDER_ID = " + ids.get(i) + " OR RECEIVER_ID = " + ids.get(i) + " ORDER BY TIMESTAMP DESC";
            Cursor cursor1 = db.rawQuery(selectContactQuery,null);

            if(cursor1.moveToFirst()){
                ChatMessage contact = new ChatMessage(cursor1.getString(0),cursor1.getString(1),cursor1.getInt(2),cursor1.getInt(3),cursor1.getInt(4));
                contacts.add(contact);
            }
            cursor1.close();
        }
        db.close();
        return contacts;
    }

    public void deleteContact(int id){
        String deleteContactQuery = "DELETE FROM " + TABLE_MESSAGE + " WHERE ( SENDER_ID = " + id + " OR RECEIVER_ID = " + id + " ) ) ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(deleteContactQuery,null);
        cursor.close();
        db.close();
        return;
    }


}
