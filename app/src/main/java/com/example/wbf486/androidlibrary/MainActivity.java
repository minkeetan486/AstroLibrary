package com.example.wbf486.androidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String str_fullName = intent.getStringExtra("fullName");
        boolean b_isAdmin = intent.getBooleanExtra("isAdmin", false);

        String message = str_fullName + ", Welcome to Astro Library";
        EditText eTWelcome = (EditText) findViewById(R.id.eTWelcome);
        eTWelcome.setText(message);
    }


}
