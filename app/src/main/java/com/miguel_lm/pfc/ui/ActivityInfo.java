package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.miguel_lm.pfc.R;

public class ActivityInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setTitle("Información Usuario");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}