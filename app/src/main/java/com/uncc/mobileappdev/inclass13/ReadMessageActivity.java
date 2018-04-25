package com.uncc.mobileappdev.inclass13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReadMessageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView senderName;
    private TextView emailMessage;

    private String messageKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        senderName = findViewById(R.id.to_from_name);
        emailMessage = findViewById(R.id.emailContent);

        Intent intent = getIntent();
        ArrayList<String> emailContent = intent.getStringArrayListExtra(Constants.INTENT_KEY);

        senderName.setText(emailContent.get(0));
        emailMessage.setText(emailContent.get(1));
        messageKey = emailContent.get(2);

    }

    private void deleteMessage() {
        mDatabase.child("mailboxes").child(mAuth.getCurrentUser().getUid()).child(messageKey).removeValue();
    }
}
