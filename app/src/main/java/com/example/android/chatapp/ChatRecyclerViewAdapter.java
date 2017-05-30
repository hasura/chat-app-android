package com.example.android.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amogh on 26/5/17.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private static final int ITEM_LEFT = 1;
    private static final int ITEM_RIGHT  = 2;

    List<ChatMessage> chatMessages = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).getSender())
            return 2;
        else
            return 1;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case ITEM_LEFT:
                return new LeftChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem_left,parent,false));
            case ITEM_RIGHT:
                return new RightChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem_right,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        final ChatMessage chatMessage = chatMessages.get(position);

        if(holder.getItemViewType() == ITEM_LEFT){
            LeftChatViewHolder viewHolder = (LeftChatViewHolder) holder;
            viewHolder.contents.setText(chatMessage.getContent());
            viewHolder.time.setText(chatMessage.getTime());
        }else{
            RightChatViewHolder viewHolder = (RightChatViewHolder) holder;
            viewHolder.contents.setText(chatMessage.getContent());
            viewHolder.time.setText(chatMessage.getTime());
        }
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void setChatMessages(List<ChatMessage> chatMessages){
        this.chatMessages = chatMessages;
        notifyDataSetChanged();
    }

    public void addMessage(ChatMessage chatMessage){
        this.chatMessages.add(chatMessage);
        notifyDataSetChanged();
    }

    public void deleteMessage(int position){
        this.chatMessages.remove(position);
        notifyDataSetChanged();
    }
}
