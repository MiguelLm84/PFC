package com.miguel_lm.pfc.ui.ui.instagram;

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

public class WebViewInstagramFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_web_view_instagram, container, false);

        WebView webViewInstagram = root.findViewById(R.id.WebViewInstagram);

        webViewInstagram.getSettings().setJavaScriptEnabled(true);
        webViewInstagram.getSettings().setBuiltInZoomControls(true);
        webViewInstagram.getSettings().setDisplayZoomControls(false);

        webViewInstagram.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("https://www.instagram.com/")) return false;

                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
        });
        webViewInstagram.loadUrl("https://www.instagram.com/");

        return root;
    }
}