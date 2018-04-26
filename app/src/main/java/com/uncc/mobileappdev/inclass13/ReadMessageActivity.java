package com.uncc.mobileappdev.inclass13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReadMessageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView toFromLabel;
    private TextView senderName;
    private TextView emailMessage;
    private Toolbar toolbar;

    private ArrayList<String> emailContent;
    private String messageKey;
    private String senderUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Read Message");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        toFromLabel = findViewById(R.id.to_from_label);
        toFromLabel.setText("From: ");

        senderName = findViewById(R.id.to_from_name);
        emailMessage = findViewById(R.id.emailContent);
        emailMessage.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        emailContent = intent.getStringArrayListExtra(Constants.INTENT_KEY);

        senderName.setText(emailContent.get(0));
        emailMessage.setText(emailContent.get(1));
        senderUid = emailContent.get(2);
        messageKey = emailContent.get(3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        MenuItem reply = menu.findItem(R.id.reply);
        MenuItem newEmail = menu.findItem(R.id.newEmail);
        newEmail.setVisible(false);
        delete.setVisible(true);
        reply.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.reply:
                Intent replyIntent = new Intent(ReadMessageActivity.this, ComposeMessageActivity.class);

                ArrayList<String> senderInfo = new ArrayList<>();
                senderInfo.add(emailContent.get(0));
                senderInfo.add(senderUid);

                replyIntent.putStringArrayListExtra(Constants.INTENT_KEY, senderInfo);
                startActivity(replyIntent);
                finish();
                break;
            case  R.id.delete:
                deleteMessage();
                Intent deleteIntent = new Intent(ReadMessageActivity.this, InboxActivity.class);
                startActivity(deleteIntent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteMessage() {
        mDatabase.child("mailboxes").child(mAuth.getCurrentUser().getUid()).child(messageKey).removeValue();
    }

}
