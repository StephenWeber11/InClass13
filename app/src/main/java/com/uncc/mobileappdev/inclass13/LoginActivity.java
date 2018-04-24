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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText userNameField;
    private EditText passwordField;
    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(Constants.APP_TITLE);

        userNameField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userNameField.getText().toString();
                String password = passwordField.getText().toString();

                login(email, password);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("AUTH", "signInWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Email or Password is Incorrect. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
