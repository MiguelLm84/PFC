package com.miguel_lm.pfc.ui.ui.youtube;

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

public class WebViewYouTubeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_view_you_tube, container, false);

        WebView webViewYouTube = root.findViewById(R.id.WebViewYouTube);

        webViewYouTube.getSettings().setJavaScriptEnabled(true);
        webViewYouTube.getSettings().setBuiltInZoomControls(true);
        webViewYouTube.getSettings().setDisplayZoomControls(false);

        /*webViewYouTube.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("https://www.youtube.com/")) return false;

                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
        });*/
        webViewYouTube.loadUrl("https://www.youtube.com/");

        return root;
    }
}