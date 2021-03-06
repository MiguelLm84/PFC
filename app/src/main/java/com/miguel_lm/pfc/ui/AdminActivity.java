package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private FragmentLista fragment_lista;
    private FragmentGestion fragment_datos;
    ViewPager2 viewPager2;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle("Administración Usuarios");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        viewPager2 = findViewById(R.id.viewPage2_admin);
        tabs = findViewById(R.id.tabs);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        TabLayoutMediator();
    }

    @Override
    public void onStart() {

        super.onStart();
        this.getWindow().getDecorView().invalidate();
        List<String> colores = ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        tabs.setTabTextColors(Color.parseColor(colores.get(0)), Color.parseColor(colores.get(0)));
        tabs.setSelectedTabIndicatorColor(Color.parseColor(colores.get(2)));
        this.getTheme().applyStyle(ColorConfigurator.getInstance().getTheme(this), true);
    }

    @Override
    public void onRestart() {

        super.onRestart();
        this.getWindow().getDecorView().invalidate();
        List<String> colores = ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        tabs.setTabTextColors(Color.parseColor(colores.get(0)), Color.parseColor(colores.get(0)));
        tabs.setSelectedTabIndicatorColor(Color.parseColor(colores.get(2)));
        this.getTheme().applyStyle(ColorConfigurator.getInstance().getTheme(this), true);
    }

    @Override
    public void onResume() {

        super.onResume();
        this.getWindow().getDecorView().invalidate();
        List<String> colores = ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        //tabs.setTabTextColors(Color.parseColor(colores.get(0)), Color.parseColor(colores.get(0)));
        //tabs.setSelectedTabIndicatorColor(Color.parseColor(colores.get(2)));
        tabs.setTabTextColors(Color.WHITE, Color.WHITE);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);
        this.getTheme().applyStyle(ColorConfigurator.getInstance().getTheme(this), true);
    }

    public void TabLayoutMediator(){

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

    private void mostrarFragment(Fragment fragment) {
        FragmentTransaction fragmentTransition = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragments, fragment);
        fragmentTransition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransition.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}