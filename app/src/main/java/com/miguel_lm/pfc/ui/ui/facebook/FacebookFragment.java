package com.miguel_lm.pfc.ui.ui.facebook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.ui.AdminActivity;

public class FacebookFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/association.manager.pro/")));
        getActivity().getSupportFragmentManager().popBackStack();
        return inflater.inflate(R.layout.fragment_facebook, container, false);
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), AdminActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}