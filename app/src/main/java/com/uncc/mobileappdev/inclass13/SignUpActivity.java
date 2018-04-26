package com.uncc.mobileappdev.inclass13;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText fNameField;
    private EditText lNameField;
    private EditText passField;
    private EditText passConfirmField;
    private EditText emailField;
    private Button cancelButon;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(Constants.APP_TITLE_SIGNUP);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fNameField = findViewById(R.id.FirstName);
        lNameField = findViewById(R.id.LastName);
        emailField = findViewById(R.id.Email);
        passField = findViewById(R.id.Password);
        passConfirmField = findViewById(R.id.confirmPassword);
        cancelButon = findViewById(R.id.cancelButton);
        submitButton = findViewById(R.id.signUpButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String passOne = passField.getText().toString();
                String passTwo = passConfirmField.getText().toString();

                if(passwordsMatch(passOne, passTwo)) {
                    signup(email, passOne);
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean passwordsMatch(String passOne, String passTwo) {
        return passOne.equals(passTwo);
    }

    private void signup(String email, String password) {
        final String emailAddr = email;
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("AUTH", "createUserWithEmail:SUCCESS");
                            String fName = fNameField.getText().toString();
                            String lName = lNameField.getText().toString();
                            pushUserData(fName, lName, getUid(), emailAddr);

                            Intent intent = new Intent(SignUpActivity.this, InboxActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.d("AUTH", "createUserWithEmail:FAILURE", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void pushUserData(String firstName, String lastName, String uid, String email) {
        mDatabase.child("users").push().setValue(new User(firstName, lastName, uid, email));
    }

    private String getUid() {
        return mAuth.getCurrentUser().getUid();
    }
}
