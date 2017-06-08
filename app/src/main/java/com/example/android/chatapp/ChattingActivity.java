package com.example.android.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.hasura.sdk.core.Call;
import io.hasura.sdk.core.Callback;
import io.hasura.sdk.core.HasuraException;
import io.hasura.sdk.core.HasuraSessionStore;

public class ChattingActivity extends AppCompatActivity {

    EditText message;
    RecyclerView recyclerView;
    Button send;
    ChatRecyclerViewAdapter adapter;
    String time;

    int receiverId;
    int senderId;

    private static final String DATABASE_NAME = "chatapp";
    private static final int DATABASE_VERSION = 2;

    List<ChatMessage> sampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        final DataBaseHandler db = new DataBaseHandler(this,DATABASE_NAME,null,DATABASE_VERSION);

        message = (EditText) findViewById(R.id.chat_message);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        send = (Button) findViewById(R.id.chat_sendButton);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        //linearLayoutManager.setReverseLayout(true);
        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
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
                    final ChatMessage chat = new ChatMessage();
                    chat.setContent(message.getText().toString());
                    chat.setTime(time);
                    chat.setSender(senderId);
                    chat.setReceiver(receiverId);
                    chat.setUserId(HasuraSessionStore.getSavedUser().getId());

                    //inserting the messages to the hasura databases.
                    Call<MessageResponse,HasuraException> call = Global.user.dataService()
                            .setRequestBody(new InsertMessageQuery(message.getText().toString(),time,senderId,receiverId,Global.user.getId()))
                            .build();
                    call.executeAsync(new Callback<MessageResponse, HasuraException>() {
                        @Override
                        public void onSuccess(MessageResponse messageResponse) {
                            adapter.addMessage(chat);
                            db.insertMessage(chat);
                        }

                        @Override
                        public void onFailure(HasuraException e) {
                            Toast.makeText(ChattingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                message.setText("");

            }
        });

    }
}
