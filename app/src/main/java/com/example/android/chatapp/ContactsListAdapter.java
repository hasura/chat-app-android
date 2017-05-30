package com.example.android.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amogh on 29/5/17.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListViewHolder> {

    List<Contact> contacts = new ArrayList<>();

    public ContactsListAdapter(Interactor interactor) {
    }

    public interface Interactor{
        void onChatClicked(int position,Contact contact);
        void onChatLongClicked(int positin,Contact contact);
    }

    Interactor interactor;

    @Override
    public ContactsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ContactsListViewHolder holder, final int position) {

        final Contact contact = contacts.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactor.onChatClicked(position,contact);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                interactor.onChatLongClicked(position,contact);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public void addContact(Contact contact){
        contacts.add(contact);
        notifyDataSetChanged();
    }

    public void deleteContact(int position){
        contacts.remove(contacts.get(position));
        notifyDataSetChanged();
    }
}
