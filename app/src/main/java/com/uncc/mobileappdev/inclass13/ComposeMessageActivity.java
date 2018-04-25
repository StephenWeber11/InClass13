package com.uncc.mobileappdev.inclass13;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Stephen on 4/25/2018.
 */

public class ComposeMessageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView toFromLabel;
    private TextView recipientName;
    private ImageView selectRecipientImage;
    private TextView emailMessage;
    private EditText enterMessage;
    private Button sendButton;

    private String recipientUid;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserData();

        toFromLabel = findViewById(R.id.to_from_name);
        recipientName = findViewById(R.id.to_from_name);
        selectRecipientImage = findViewById(R.id.person_list_image);
        emailMessage = findViewById(R.id.emailContent);
        enterMessage = findViewById(R.id.enterMessage);
        sendButton = findViewById(R.id.sendButton);

        Intent intent = getIntent();
        ArrayList<String> replyToSenderInfo = intent.getStringArrayListExtra(Constants.INTENT_KEY);

        if(replyToSenderInfo != null) {
            recipientName.setText(replyToSenderInfo.get(0));
            setRecipientUid(replyToSenderInfo.get(1));
        }

        sendButton.setVisibility(View.VISIBLE);
        enterMessage.setVisibility(View.VISIBLE);
        findViewById(R.id.emailContent).setVisibility(View.INVISIBLE);
        toFromLabel.setText("To: ");


        selectRecipientImage.setVisibility(View.VISIBLE);
        selectRecipientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(users);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = enterMessage.getText().toString();
                if(messageHasContent(message)) {
                    pushMessageData(message, getLoggedInUserFullName());

                    Toast.makeText(ComposeMessageActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ComposeMessageActivity.this, "Please enter a message!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getUserData() {
        users = null;
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }

    private void showPopup(ArrayList<User> users){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Users");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for(User user : users) {
            arrayAdapter.add(user.getFirstName() + " " + user.getFirstName());
        }

        final ArrayList<User> usersCopy = new ArrayList<>(users);
        alert.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recipientName.setText(arrayAdapter.getItem(which));
                setRecipientUid(usersCopy.get(which).getUid());

                dialog.dismiss();
            }
        });

        alert.show();
    }

    private String getLoggedInUserFullName() {
        StringBuilder sb = new StringBuilder();
        for(User user : users) {
            if(user.getUid().equals(mAuth.getCurrentUser().getUid())) {
                sb.append(user.getFirstName());
                sb.append(" ");
                sb.append(user.getLastName());
            }
        }

        return sb.toString();
    }

    private void pushMessageData(String message, String name) {
        mDatabase.child("mailboxes")
                .child(recipientUid)
                .push()
                .setValue(new Message(message, new Date(), name, mAuth.getCurrentUser().getUid(), false));
    }

    private void setRecipientUid(String recipientUid) {
        this.recipientUid = recipientUid;
    }

    private boolean messageHasContent(String message) {
        return message != null && message != "" && message != " ";
    }
}
