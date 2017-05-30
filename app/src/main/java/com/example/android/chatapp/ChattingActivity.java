package com.example.android.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    EditText message;
    RecyclerView recyclerView;
    Button send;
    ChatRecyclerViewAdapter adapter;
    String time;
    Integer userId;//extract userId
    Integer receiverId;//extract receiverId

    private static final String DATABASE_NAME = "chatapp";
    private static final int DATABASE_VERSION = 1;

    List<ChatMessage> sampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        //get userId from previous intent
        //userId = getIntent().getExtras("user_id");

        sampleData = new ArrayList<>();
        //sampleData.add(new ChatMessage(content1,DateFormat.getDateTimeInstance().format(new Date()),sender1));
        //sampleData.add(new ChatMessage(content2,DateFormat.getDateTimeInstance().format(new Date()),sender2));

        final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);

        message = (EditText) findViewById(R.id.chat_message);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        send = (Button) findViewById(R.id.chat_sendButton);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //Mocking Data

        sampleData = db.getAllMessages();
        if(sampleData.size() != 0)
            adapter.setChatMessages(sampleData);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //time = DateFormat.getDateTimeInstance().format(new Date());
                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                if(message.getText().toString().isEmpty() || message.getText().toString().length() == 0){}
                 else {
                    adapter.addMessage(new ChatMessage(message.getText().toString(),time,userId,receiverId));
                    db.insertMessage(new ChatMessage(message.getText().toString(),time,userId,receiverId));
                }
                message.setText("");

            }
        });

    }
}
