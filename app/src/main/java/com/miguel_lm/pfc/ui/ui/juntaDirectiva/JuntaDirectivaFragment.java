package com.miguel_lm.pfc.ui.ui.juntaDirectiva;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.ui.ActivityNavigationDrawer;

public class JuntaDirectivaFragment extends Fragment {

    private JuntaDirectivaViewModel juntaDirectivaViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        juntaDirectivaViewModel = new ViewModelProvider(this).get(JuntaDirectivaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_junta_directiva, container, false);

        final TextView textView = root.findViewById(R.id.tv_JuntaDirectiva);

        juntaDirectivaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}