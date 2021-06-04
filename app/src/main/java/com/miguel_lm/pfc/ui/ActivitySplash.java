package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;

import androidx.appcompat.app.AppCompatActivity;

import com.miguel_lm.pfc.R;


public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> startActivity(new Intent(com.miguel_lm.pfc.ui.ActivitySplash.this, AuthActivity.class)), 2500);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
    }
}