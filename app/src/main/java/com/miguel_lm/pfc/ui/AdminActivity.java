package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.miguel_lm.pfc.R;

public class AdminActivity extends AppCompatActivity {

    private FragmentLista fragment_lista;
    private FragmentDatos fragment_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Administración Usuarios");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
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
    }

    private void mostrarFragment(Fragment fragment) {
        FragmentTransaction fragmentTransition = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragments, fragment);
        fragmentTransition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransition.commit();
    }
}