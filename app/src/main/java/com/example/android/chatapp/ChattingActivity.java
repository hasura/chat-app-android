package com.example.android.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    EditText message;
    RecyclerView recyclerView;
    Button send;
    ChatRecyclerViewAdapter adapter;
    String time;
    Integer senderId = 1;
    Integer receiverId = 2;

    private static final String DATABASE_NAME = "chatapp";
    private static final int DATABASE_VERSION = 2;

    List<ChatMessage> sampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        sampleData = new ArrayList<>();

        //sampleData.add(new ChatMessage("hello there", DateFormat.getDateTimeInstance().format(new Date()),1,2));
        //sampleData.add(new ChatMessage("Hey to different!!", DateFormat.getDateTimeInstance().format(new Date()),3,1));
        //sampleData.add(new ChatMessage("Hey!!", DateFormat.getDateTimeInstance().format(new Date()),2,1));

        final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);

        message = (EditText) findViewById(R.id.chat_message);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        send = (Button) findViewById(R.id.chat_sendButton);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
        recyclerView.setAdapter(adapter);

        receiverId = Global.receiverId;
        senderId = Global.senderId;
        //Mocking Data

        sampleData = db.getAllMessages();
            if(sampleData.size() != 0)
                adapter.setChatMessages(sampleData);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long tsLong = System.currentTimeMillis();
                time = tsLong.toString();

                if(message.getText().toString().isEmpty() || message.getText().toString().length() == 0){}
                 else {
                    adapter.addMessage(new ChatMessage(message.getText().toString(),time,senderId,receiverId));
                    db.insertMessage(new ChatMessage(message.getText().toString(),time,senderId,receiverId));
                }
                message.setText("");

            }
        });

    }
}
