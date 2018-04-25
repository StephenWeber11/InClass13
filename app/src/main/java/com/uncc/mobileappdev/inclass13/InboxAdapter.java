package com.uncc.mobileappdev.inclass13;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Stephen on 4/24/2018.
 */

public class InboxAdapter extends RecyclerView.Adapter {

    private RecyclerViewClickListener recyclerViewClickListener;
    private Activity activity;
    private ArrayList<Message> messages;
    private User user;
    private Message message;

    public InboxAdapter(RecyclerViewClickListener recyclerViewClickListener, Activity activity, ArrayList<Message> mesages) {
        this.recyclerViewClickListener = recyclerViewClickListener;
        this.activity = activity;
        this.messages = mesages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item_recycler, parent, false);
        return new InboxViewHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(messages == null) {
            return;
        }

        final InboxViewHolder messageHolder = (InboxViewHolder) holder;
        message = messages.get(position);
        messageHolder.getSenderFullName().setText(message.getFromName());
        messageHolder.getEmailDate().setText(DateFormat.getInstance().format(message.getDate()));
        messageHolder.getEmailSummary().setText(message.getMessageText());

        if(message.isRead()) {
            messageHolder.getReadImage().setVisibility(View.INVISIBLE);
            messageHolder.getUnreadImage().setVisibility(View.VISIBLE);
        } else {
            messageHolder.getReadImage().setVisibility(View.VISIBLE);
            messageHolder.getUnreadImage().setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if(messages != null) {
            return messages.size();
        }

        return 0;
    }
}
