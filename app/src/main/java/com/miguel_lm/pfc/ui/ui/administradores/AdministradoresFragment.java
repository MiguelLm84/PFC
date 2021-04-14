package com.miguel_lm.pfc.ui.ui.administradores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.tabs.TabLayout;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.ui.FragmentDatos;
import com.miguel_lm.pfc.ui.FragmentLista;

public class AdministradoresFragment extends Fragment {

    private AdministradoresViewModel administradoresViewModel;
    private FragmentLista fragment_lista;
    private FragmentDatos fragment_datos;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        administradoresViewModel = new ViewModelProvider(this).get(AdministradoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_administradores, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        administradoresViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        TabLayout tabs = (TabLayout) root.findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment frag = null;
                switch (tab.getPosition()) {
                    case 0: {
                        if (fragment_lista == null)
                            fragment_lista = new FragmentLista();
                        frag = fragment_lista;
                        break;
                    }
                    case 1: {
                        if (fragment_datos == null)
                            fragment_datos = new FragmentDatos();     //FragmentDatos.newInstance(AdminActivity.this);
                        frag = fragment_datos;
                        break;
                    }
                }

                mostrarFragment(frag);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    private void mostrarFragment(Fragment fragment) {
        FragmentTransaction fragmentTransition = getParentFragmentManager().beginTransaction().replace(R.id.frameLayoutFragments, fragment); // getSupportFragmentManager()
        fragmentTransition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransition.commit();
    }
}