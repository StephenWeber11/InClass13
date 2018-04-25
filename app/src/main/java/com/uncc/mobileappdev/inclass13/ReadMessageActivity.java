package com.uncc.mobileappdev.inclass13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ReadMessageActivity extends AppCompatActivity {

    private TextView senderName;
    private TextView emailMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        senderName = findViewById(R.id.fromName);
        emailMessage = findViewById(R.id.emailContent);

        Intent intent = getIntent();
        ArrayList<String> emailContent = intent.getStringArrayListExtra(Constants.INTENT_KEY);

        senderName.setText(emailContent.get(0));
        emailMessage.setText(emailContent.get(1));


    }
}
