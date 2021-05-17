package com.miguel_lm.pfc.ui.ui.twitter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miguel_lm.pfc.R;

public class WebViewTwitterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_view_twitter, container, false);

        WebView webViewTwitter = root.findViewById(R.id.WebViewTwitter);

        webViewTwitter.getSettings().setJavaScriptEnabled(true);
        webViewTwitter.getSettings().setBuiltInZoomControls(true);
        webViewTwitter.getSettings().setDisplayZoomControls(false);

        /*webViewTwitter.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("https://twitter.com/home")) return false;

                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
        });*/
        webViewTwitter.loadUrl("https://twitter.com/home");

        return root;
    }
}