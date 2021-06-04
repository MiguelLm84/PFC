package com.miguel_lm.pfc.ui.ui.twitter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.miguel_lm.pfc.R;

public class TwitterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/home")));
        getActivity().getSupportFragmentManager().popBackStack();
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }
}