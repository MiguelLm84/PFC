package com.miguel_lm.pfc.ui.ui.facebook;

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
import com.miguel_lm.pfc.ui.AdminActivity;

public class WebViewFacebookFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_view_facebook, container, false);

        WebView webViewFacebook = root.findViewById(R.id.WebViewFacebook);

        webViewFacebook.getSettings().setJavaScriptEnabled(true);
        webViewFacebook.getSettings().setBuiltInZoomControls(true);
        webViewFacebook.getSettings().setDisplayZoomControls(false);

        webViewFacebook.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("facebook.com/miguel.lopezmartins")) return false;

                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
        });
        webViewFacebook.loadUrl("https://www.facebook.com/miguel.lopezmartins");

        return root;
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), AdminActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}