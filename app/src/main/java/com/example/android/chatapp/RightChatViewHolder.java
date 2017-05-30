package com.example.android.chatapp;

import android.view.View;
import android.widget.TextView;

/**
 * Created by amogh on 29/5/17.
 */

public class RightChatViewHolder extends ChatViewHolder {

    public TextView contents;
    public TextView time;

    public RightChatViewHolder(View itemView) {
        super(itemView);
        contents = (TextView) itemView.findViewById(R.id.chatItem_right_text);
        time = (TextView) itemView.findViewById(R.id.chatItem_right_time);
    }
}
