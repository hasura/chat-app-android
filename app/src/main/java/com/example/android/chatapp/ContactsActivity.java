package com.example.android.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by amogh on 29/5/17.
 */

public class ContactsActivity extends AppCompatActivity {

    int userId_receiver;
    ContactsListAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ContactsListAdapter(new ContactsListAdapter.Interactor(){
            @Override
            public void onChatClicked(int position, Contact contact) {
                userId_receiver = contact.getUserId();
                Intent i = new Intent(ContactsActivity.this,ChattingActivity.class);
                i.putExtra("user_id",userId_receiver);
                startActivity(i);
            }

            @Override
            public void onChatLongClicked(final int position, final Contact contact) {
                checkForDeleteContact(position,contact);
            }

        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void checkForDeleteContact(final int position, Contact contact){
        AlertDialog.Builder alert = new AlertDialog.Builder(ContactsActivity.this);
        alert.setMessage("Are you sure you want to delete this chat?");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.deleteContact(position);
                //Also remove all entries from local database where user_id == userId
            }
        });
    }
}
