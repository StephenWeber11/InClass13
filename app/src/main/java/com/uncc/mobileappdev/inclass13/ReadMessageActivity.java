package com.uncc.mobileappdev.inclass13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    Toolbar toolbar;
    private String messageKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Read Message");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.reply:
                Intent intent = new Intent(ReadMessageActivity.this, ComposeMessageActivity.class);
                startActivity(intent);
                finish();
                break;
            case  R.id.delete:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteMessage() {
        mDatabase.child("mailboxes").child(mAuth.getCurrentUser().getUid()).child(messageKey).removeValue();
    }
}
