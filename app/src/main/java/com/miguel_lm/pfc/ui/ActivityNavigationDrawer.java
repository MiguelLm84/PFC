package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class ActivityNavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    static ImageButton btn_perfil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /*public static void customWindow(){

        PersonalizacionActivity customActivity = new PersonalizacionActivity();
        if(PersonalizacionActivity.TEMA == R.style.Theme_Azul){
            String colorApp = "#FFBBDEFB";
            String primary = "#82B1FF";
            String backgraund = "#FFFFFF";
            customActivity.cambiarColor(colorApp,primary,backgraund);

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Rojo){
            String colorApp = "#D50000";
            String primary = "90D50000";
            String backgraund = "#FFFFFF";
            customActivity.cambiarColor(colorApp,primary,backgraund);

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Verde){
            String colorApp = "#FF03DAC5";
            String primary = "8B26DAB0";
            String backgraund = "#FFFFFF";
            customActivity.cambiarColor(colorApp,primary,backgraund);

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_Morado){
            String colorApp = "#6A1B9A";
            String primary = "FF7E57C2";
            String backgraund = "#FFFFFF";
            customActivity.cambiarColor(colorApp,primary,backgraund);

        } else if(PersonalizacionActivity.TEMA == R.style.Theme_PFC){
            String colorApp = "#FF000000";
            String primary = "#273036";
            String backgraund = "#FFFFFF";
            customActivity.cambiarColor(colorApp,primary,backgraund);
        }
    }*/
}