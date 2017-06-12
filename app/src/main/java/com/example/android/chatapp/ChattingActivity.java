package com.example.android.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.hasura.sdk.Callback;
import io.hasura.sdk.Hasura;
import io.hasura.sdk.HasuraException;
import io.hasura.sdk.HasuraSessionStore;

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
    public void onBackPressed()
    {
        Intent i = new Intent(ChattingActivity.this,ContactsActivity.class);
        startActivity(i);
        finish();
    }


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
        adapter = new ChatRecyclerViewAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        Global.user = Hasura.currentUser();

        receiverId = Global.receiverId;
        senderId = Global.user.getId();

        sampleData = db.getAllMessages();
            if(sampleData.size() != 0)
                adapter.setChatMessages(sampleData);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long tsLong = System.currentTimeMillis();
                time = getRequiredTime(tsLong.toString());

                if(message.getText().toString().isEmpty() || message.getText().toString().length() == 0){}
                 else {
                    final ChatMessage chat = new ChatMessage();
                    chat.setContent(message.getText().toString());
                    chat.setTime(time);
                    chat.setSender(senderId);
                    chat.setReceiver(receiverId);
                    chat.setUserId(HasuraSessionStore.getSavedUser().getId());
                    adapter.addMessage(chat);
                    db.insertMessage(chat);

                    /*Call<JSONObject,HasuraException> call = Global.user.dataService()
                            .setRequestBody(new InsertMessageQuery(message.getText().toString(),time,senderId,receiverId,Global.user.getId()))
                            .build();
                    call.executeAsync(new Callback<JSONObject, HasuraException>() {

                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            adapter.addMessage(chat);
                            db.insertMessage(chat);
                        }

                        @Override
                        public void onFailure(HasuraException e) {
                            Toast.makeText(ChattingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });*/

                    Global.user.getQueryBuilder()
                            .useDataService()
                            .setRequestBody(new InsertMessageQuery(message.getText().toString(),time,senderId,receiverId,Global.user.getId()))
                            .expectResponseOfType(MessageResponse.class)
                            .build()
                            .executeAsync(new Callback<MessageResponse, HasuraException>() {
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
    public String getRequiredTime(String timeStampStr){
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date netDate = (new Date(Long.parseLong(timeStampStr)));
            return sdf.format(netDate);
        } catch (Exception ignored) {
            return "xx";
        }
    }
}
