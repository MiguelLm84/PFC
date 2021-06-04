package com.miguel_lm.pfc.ui.ui.direccion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.miguel_lm.pfc.R;

public class DireccionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_direccion, container, false);
       getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        return root;
    }
}