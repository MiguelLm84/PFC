package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miguel_lm.pfc.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getSupportActionBar().setTitle("Notificaciones de comunicación");

        WebView webViewFirebase = findViewById(R.id.WebViewFirebase);

        webViewFirebase.getSettings().setJavaScriptEnabled(true);
        webViewFirebase.getSettings().setBuiltInZoomControls(true);
        webViewFirebase.getSettings().setDisplayZoomControls(false);

        webViewFirebase.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("firebase.google.com")) return false;

                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
        });
        webViewFirebase.loadUrl("https://console.firebase.google.com/project/mi-proyecto-pfc-89ba8/notification/compose");
    }

    public void onBackPressed(){

        Intent intent = new Intent(this, AdminActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}