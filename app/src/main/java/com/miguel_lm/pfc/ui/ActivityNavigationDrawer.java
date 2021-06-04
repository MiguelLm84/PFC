package com.miguel_lm.pfc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;
import com.miguel_lm.pfc.singletons.FotoPerfilProvider;

import java.io.IOException;

public class ActivityNavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;
    Bundle bundle;
    View btn_admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
        setContentView(R.layout.activity_navigation_drawer);




        if (recibiendoDatosTema()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.Theme_PFC_DARK);

        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.Theme_PFC);
        }

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        System.out.println("Los menus que tengo: " + toolbar.getMenu().size());
        //toolbar.getMenu().getItem(0).setVisible(false);
        setSupportActionBar(toolbar);

        //

        //btn_admin = toolbar.findViewById(R.id.accionAdmin); // findViewById(R.id.accionAdmin);
        //System.out.println("El boton admin que tengo es: " + btn_admin);

        bundle = this.getIntent().getExtras();
        obtenerComprobacionAdmin(bundle);

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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Resultado" + resultCode);

        Uri imageUri = data.getData();
        System.out.println("La imagen es: " + imageUri);

        Bitmap FixBitmap = null;
        try {
            FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            FotoPerfilProvider.fotoPerfil = FixBitmap;

            StorageReference storageRef =  FirebaseStorage.getInstance().getReference();
            StorageReference childRef = storageRef.child("/profileImages/"+ FirebaseAuth.getInstance().getUid());
            UploadTask uploadTask = childRef.putFile(data.getData());
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Ha habido un error subiendo la imagen");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("La imagen se ha subido con exito" + taskSnapshot.toString());
                    System.out.println(taskSnapshot.getUploadSessionUri());
                    System.out.println(taskSnapshot.getBytesTransferred());
                    System.out.println(taskSnapshot.getMetadata());
                    recreate();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        obtenerComprobacionAdmin(bundle);
    }

    public void obtenerComprobacionAdmin(Bundle bundle){

        System.out.println("El boton del admins que tengo es: " + btn_admin);

        if(bundle != null){

            boolean validacion = false;
            String rol = bundle.getString("rol");
            boolean isAdmin = bundle.getBoolean("isAdmin");

            if(rol != null && rol.equals("user") && isAdmin == validacion){
                //btn_admin.setVisibility(View.GONE);
                //toolbar.setVisibility(View.GONE);
            }

        } else{
            Log.e("TAG BUNDLE ADMIN", "Error, el Bundle con los datos de la comprobación del rol está vacío o es null.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Aqui tengo que saber si es o no admin
        System.out.println("onCreateOptionsMenu se esta llamadno");
        getMenuInflater().inflate(R.menu.activity_navigation_drawer, menu);


            boolean validacion = false;
            String rol = getIntent().getStringExtra("rol");
            boolean isAdmin = getIntent().getBooleanExtra("isAdmin",false);
            System.out.println("VEO : " +  rol + " " +  isAdmin);
            if (rol != null && rol.equals("user") && isAdmin == validacion) {
                System.out.println("Tengo que poner el primer menu");
               menu.getItem(0).setVisible(false);
            }else{
                menu.getItem(0).setVisible(true);
            }




        int FlagsModoOscuro = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (FlagsModoOscuro) {

            case Configuration.UI_MODE_NIGHT_YES:
                menu.getItem(1).setIcon(R.drawable.moon);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                menu.getItem(1).setIcon(R.drawable.sun);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int FlagsModoOscuro = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (item.getItemId() == R.id.accionCerrarSesion) {
            accionCerrarSesion();
        }
        if (item.getItemId() == R.id.accionAdmin) {
            accionAdmin();

        } if (item.getItemId() == R.id.light_dark_theme) {

            if (FlagsModoOscuro == Configuration.UI_MODE_NIGHT_YES) {
                item.setIcon(R.drawable.sun);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                guardandoDatosTema(false);

            } else if (FlagsModoOscuro == Configuration.UI_MODE_NIGHT_NO) {

                item.setIcon(R.drawable.moon);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                guardandoDatosTema(true);
            }
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



    public void guardandoDatosTema(boolean modoDark) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("preferenciasTema", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("modoDark", modoDark);
        editor.apply();
    }

    public boolean recibiendoDatosTema() {

        SharedPreferences sharedPreferences = this.getSharedPreferences("preferenciasTema", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("modoDark", false);
    }
}