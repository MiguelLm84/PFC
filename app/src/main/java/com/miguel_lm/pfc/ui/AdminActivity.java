package com.miguel_lm.pfc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.miguel_lm.pfc.R;

public class AdminActivity extends AppCompatActivity {

    private FragmentLista fragment_lista;
    private FragmentDatos fragment_datos;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Administración Usuarios");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        viewPager2 = findViewById(R.id.viewPage2_admin);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabs, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if(position == 1){
                    tab.setText("LISTA");

                } else {
                    tab.setText("DATOS");
                }
            }
        }).attach();

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