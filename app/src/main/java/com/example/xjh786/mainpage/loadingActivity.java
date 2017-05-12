package com.example.xjh786.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by XJH786 on 5/12/2017.
 */

public class loadingActivity extends AppCompatActivity {

    public static loadingActivity fa;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        fa = this;
    }

}


