package com.uncc.mobileappdev.inclass13;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Stephen on 4/24/2018.
 */

public class InboxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView senderFullName;
    private TextView emailDate;
    private TextView emailSummary;
    private ImageView readImage;
    private ImageView unreadImage;
    private RecyclerViewClickListener recyclerViewClickListener;

    public InboxViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
        super(itemView);
        this.senderFullName = (TextView) itemView.findViewById(R.id.senderName);
        this.emailDate = (TextView) itemView.findViewById(R.id.dateView);
        this.emailSummary = (TextView) itemView.findViewById(R.id.emailSummary);
        this.readImage = (ImageView) itemView.findViewById(R.id.readIndicatorBlue);
        this.unreadImage = (ImageView) itemView.findViewById(R.id.readIndicatorGray);
        this.recyclerViewClickListener = recyclerViewClickListener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        recyclerViewClickListener.recyclerViewListClicked(v, this.getPosition());
    }

    public TextView getSenderFullName() {
        return senderFullName;
    }

    public TextView getEmailDate() {
        return emailDate;
    }

    public TextView getEmailSummary() {
        return emailSummary;
    }

    public ImageView getReadImage() {
        return readImage;
    }

    public ImageView getUnreadImage() {
        return unreadImage;
    }
}
