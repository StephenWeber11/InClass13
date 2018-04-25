package com.uncc.mobileappdev.inclass13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class compaseMessageActivity extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compase_message);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Compose Message");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
    }
}
