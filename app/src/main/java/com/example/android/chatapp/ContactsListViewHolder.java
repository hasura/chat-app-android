package com.example.android.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by amogh on 29/5/17.
 */
public class ContactsListViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView contents;
    public TextView time;
    public TextView sent_or_received;

    public ContactsListViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.contact_name);
        contents = (TextView) itemView.findViewById(R.id.contact_message_contents);
        time = (TextView) itemView.findViewById(R.id.contact_message_time);
        sent_or_received = (TextView) itemView.findViewById(R.id.contact_sent_or_received);
    }
}
