package com.miguel_lm.pfc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.miguel_lm.pfc.R;

import static com.miguel_lm.pfc.ui.PersonalizacionActivity.BACKGRAUND;
import static com.miguel_lm.pfc.ui.PersonalizacionActivity.COLOR_APP;
import static com.miguel_lm.pfc.ui.PersonalizacionActivity.COLOR_PRIMARY;
import static com.miguel_lm.pfc.ui.PersonalizacionActivity.PREF_FICHERO;

public class ActivityNavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    static ImageButton btn_perfil = null;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setTheme(PersonalizacionActivity.TEMA);
        customWindow();

        btn_perfil = findViewById(R.id.btn_perfil);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_eventos,R.id.Organigrama,R.id.WebViewFacebook,
                R.id.WebViewTwitter,R.id.WebViewInstagram,R.id.WebViewYouTube)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        btn_perfil.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityNavigationDrawer.this, ActivityPerfil.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onResume() {

        super.onResume();
        leerSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.accionCerrarSesion) {
            accionCerrarSesion();
        }

        if (item.getItemId() == R.id.accionAdmin) {
            accionAdmin();
        }

        return super.onOptionsItemSelected(item);
    }

    public void accionCerrarSesion(){
        Intent intent = new Intent(ActivityNavigationDrawer.this, LogoutActivity.class);
        startActivity(intent);
        finish();
    }

    public void accionAdmin(){
        Intent intent = new Intent(ActivityNavigationDrawer.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void customWindow(){

        if(PersonalizacionActivity.TEMA == R.style.Theme_Azul){

            setTheme(PersonalizacionActivity.TEMA);
            toolbar.setBackgroundResource(R.color.azul);
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Rojo){

            setTheme(PersonalizacionActivity.TEMA);
            toolbar.setBackgroundResource(R.color.rojoOscuro);
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Verde){

            setTheme(PersonalizacionActivity.TEMA);
            toolbar.setBackgroundResource(R.color.verde);
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Morado){

            setTheme(PersonalizacionActivity.TEMA);
            toolbar.setBackgroundResource(R.color.purple_700);
            leerSharedPreferences();

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_PFC){

            setTheme(PersonalizacionActivity.TEMA);
            toolbar.setBackgroundResource(R.color.color_app);
            leerSharedPreferences();
        }
    }

    public void leerSharedPreferences(){

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        String colorApp = preferencias.getString(COLOR_APP, "");
        getWindow().setStatusBarColor(Color.parseColor(colorApp));

        String colorPrimary = preferencias.getString(COLOR_PRIMARY,"");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        String backgraund = preferencias.getString(BACKGRAUND,"");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
    }
}