package com.miguel_lm.pfc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.miguel_lm.pfc.R;

import static com.miguel_lm.pfc.ui.PersonalizacionActivity.BACKGRAUND;
import static com.miguel_lm.pfc.ui.PersonalizacionActivity.COLOR_APP;
import static com.miguel_lm.pfc.ui.PersonalizacionActivity.COLOR_PRIMARY;
import static com.miguel_lm.pfc.ui.PersonalizacionActivity.PREF_FICHERO;

public class AdminActivity extends AppCompatActivity {

    private FragmentLista fragment_lista;
    private FragmentGestion fragment_datos;
    ViewPager2 viewPager2;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leerSharedPreferences();
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle("Administración Usuarios");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setTheme(PersonalizacionActivity.TEMA);
        viewPager2 = findViewById(R.id.viewPage2_admin);
        tabs = findViewById(R.id.tabs);
        customWindow();
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabs, viewPager2, (tab, position) -> {

            if(position == 1){
                tab.setText("GESTIÓN");

            } else {
                tab.setText("LISTA");
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
                            fragment_datos = new FragmentGestion();
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

    @Override
    public void onStart() {

        super.onStart();
        leerSharedPreferences();
    }

    @Override
    public void onRestart() {

        super.onRestart();
        leerSharedPreferences();
    }

    @Override
    public void onResume() {

        super.onResume();
        leerSharedPreferences();
    }

    private void mostrarFragment(Fragment fragment) {
        FragmentTransaction fragmentTransition = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragments, fragment);
        fragmentTransition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransition.commit();
    }

    public void customWindow(){

        if(PersonalizacionActivity.TEMA == R.style.Theme_Azul){

            setTheme(PersonalizacionActivity.TEMA);
            tabs.setTabTextColors(Color.parseColor("#039BE5"),Color.parseColor("#039BE5"));
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#039BE5"));
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Rojo){

            setTheme(PersonalizacionActivity.TEMA);
            tabs.setTabTextColors(Color.parseColor("#D50000"),Color.parseColor("#D50000"));
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#D50000"));
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Verde){

            setTheme(PersonalizacionActivity.TEMA);
            tabs.setTabTextColors(Color.parseColor("#B0018786"),Color.parseColor("#B0018786"));
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#B0018786"));
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Morado){

            setTheme(PersonalizacionActivity.TEMA);
            tabs.setTabTextColors(Color.parseColor("#FF3700B3"),Color.parseColor("#FF3700B3"));
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF3700B3"));
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_PFC){

            setTheme(PersonalizacionActivity.TEMA);
            tabs.setTabTextColors(Color.parseColor("#FF000000"),Color.parseColor("#FF000000"));
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF000000"));
            leerSharedPreferences();
        }
    }

    public void leerSharedPreferences(){

        SharedPreferences preferencias = getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        String colorApp = preferencias.getString(COLOR_APP, "");
        getWindow().setStatusBarColor(Color.parseColor(colorApp));

        String colorPrimary = preferencias.getString(COLOR_PRIMARY,"");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
       getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        String backgraund = preferencias.getString(BACKGRAUND,"");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}