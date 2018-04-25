package com.uncc.mobileappdev.inclass13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class InboxActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private InboxAdapter adapter;
    Toolbar toolbar;
    private ImageButton imgbtn;

    private ArrayList<Message> messages;
    private ArrayList<String> keyList;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        //setTitle(Constants.APP_TITLE_INBOX);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inbox");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);







        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getMessageData();

        recyclerView = findViewById(R.id.inboxRecycleListView);
    }

    private void getMessageData() {
        messages = null;
        keyList = null;
        mDatabase.child("mailboxes").child(getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buildMessageList();
                buildKeyList();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    messages.add(message);
                    keyList.add(postSnapshot.getKey());
                }

                /*
                    Sort the messages by date in DESCENDING ORDER
                    This is accomplished by implementing compareTo
                    in the Message object.
                 */

                Collections.sort(messages);

                buildAdapterIfNecessary();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do Nothing
            }

        });
    }



    private String getUid() {
        return mAuth.getCurrentUser().getUid();
    }

    private void buildMessageList() {
        if(messages == null) {
            messages = new ArrayList<>();
        } else {
            messages.clear();
        }
    }

    private void buildKeyList() {
        if(keyList == null) {
            keyList = new ArrayList<>();
        } else {
            keyList.clear();
        }
    }


    private void buildAdapterIfNecessary() {
        if(adapter == null) {
            adapter = new InboxAdapter(InboxActivity.this, InboxActivity.this, messages);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(InboxActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Message message = messages.get(position);
        message.setRead(true);

        ArrayList<String> emailContent = new ArrayList<>();
        emailContent.add(message.getFromName());
        emailContent.add(message.getMessageText());

        mDatabase.child("mailboxes").child(getUid()).child(keyList.get(position)).setValue(message);

        Intent intent = new Intent(InboxActivity.this, ReadMessageActivity.class);
        intent.putStringArrayListExtra(Constants.INTENT_KEY, emailContent);
        startActivity(intent);

    }

    @Override
    public void removeItem(View v, int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.newEmail:
                Intent intent = new Intent(InboxActivity.this, compaseMessageActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
