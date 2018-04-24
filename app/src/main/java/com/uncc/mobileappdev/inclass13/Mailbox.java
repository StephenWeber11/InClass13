package com.uncc.mobileappdev.inclass13;

import java.util.HashMap;

/**
 * Created by Stephen on 4/23/2018.
 */

public class Mailbox {
    private String uid;
    private HashMap<String, Message> message;

    public Mailbox() {}

    public Mailbox(String uid, HashMap<String, Message> message) {
        this.uid = uid;
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public HashMap<String, Message> getMessage() {
        return message;
    }

    public void setMessage(HashMap<String, Message> message) {
        this.message = message;
    }
}
