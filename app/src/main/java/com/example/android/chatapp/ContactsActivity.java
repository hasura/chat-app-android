package com.example.android.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amogh on 29/5/17.
 */

public class ContactsActivity extends AppCompatActivity {

    ContactsListAdapter adapter;
    RecyclerView recyclerView;

    private static final String DATABASE_NAME = "chatapp";
    private static final int DATABASE_VERSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_activity);

        final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);

        Long tsLong = System.currentTimeMillis();
        String time = tsLong.toString();

        List<ChatMessage> sampleData = new ArrayList<>();
        sampleData.add(new ChatMessage("hello there",time,1,3));
        sampleData.add(new ChatMessage("Hey to different!!",time,3,1));
        sampleData.add(new ChatMessage("Hey!!",time,1,2));
        sampleData.add(new ChatMessage("this is some random text!!",time,1,4));
        sampleData.add(new ChatMessage("Hello World!!",time,3,1));

        db.insertMessage(sampleData.get(0));
        db.insertMessage(sampleData.get(1));
        db.insertMessage(sampleData.get(2));
        db.insertMessage(sampleData.get(3));
        db.insertMessage(sampleData.get(4));




        recyclerView = (RecyclerView) findViewById(R.id.contacts_recyclerview);

        Global.senderId = 1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ContactsListAdapter(new ContactsListAdapter.Interactor(){
            @Override
            public void onChatClicked(int position, ChatMessage contact) {

                if(contact.getSender() == Global.senderId)
                    Global.receiverId = contact.getReceiver();
                else
                    Global.receiverId = contact.getSender();

                Intent i = new Intent(ContactsActivity.this,ChattingActivity.class);
                i.putExtra("receiverid",Global.receiverId);
                startActivity(i);
            }

            @Override
            public void onChatLongClicked(final int position, final ChatMessage contact) {
                checkForDeleteContact(position,contact);
            }

        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setContacts(db.getAllContacts());
    }

    public void checkForDeleteContact(final int position, final ChatMessage contact){
        final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);

        AlertDialog.Builder alert = new AlertDialog.Builder(ContactsActivity.this);
        alert.setMessage("Are you sure you want to delete this chat?");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = contact.getReceiver();
                db.deleteContact(id);
                adapter.deleteContact(position);
            }
        });
    }
}
