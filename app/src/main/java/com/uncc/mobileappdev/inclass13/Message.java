package com.uncc.mobileappdev.inclass13;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Stephen on 4/23/2018.
 */

public class Message implements Comparable<Message>{
    private String messageText;
    private Date date;
    private String fromName;
    private String senderUid;
    private boolean isRead;
    private String dataKey;

    public Message(){}

    public Message(String messageText, Date date, String fromName, String senderUid, boolean isRead) {
        this.messageText = messageText;
        this.date = date;
        this.fromName = fromName;
        this.senderUid = senderUid;
        this.isRead = isRead;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    //Sort by date in DESCENDING order
    @Override
    public int compareTo(@NonNull Message message) {
        return -(getDate().compareTo(message.getDate()));
    }
}
