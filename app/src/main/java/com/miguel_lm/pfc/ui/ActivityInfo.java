package com.miguel_lm.pfc.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

public class ActivityInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setTitle("Información Usuario");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
    }
}