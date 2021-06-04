package com.miguel_lm.pfc.ui.ui.youtube;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.miguel_lm.pfc.R;

public class YouTubeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC5S4xbafl9vE2aGzI_ogjSg")));
        getActivity().getSupportFragmentManager().popBackStack();
        return inflater.inflate(R.layout.fragment_youtube, container, false);
    }

}