package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.miguel_lm.pfc.R;

public class PersonalizacionActivity extends AppCompatActivity {

    public static final String PREF_FICHERO = "preferencias";
    public static final String COLOR_APP = "color app";
    public static final String COLOR_PRIMARY = "color primary";
    public static final String BACKGRAUND = "backgraund";
    public static final String THEME = "tema";
    public static int TEMA = R.style.Theme_PFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(TEMA);
        if(PersonalizacionActivity.TEMA == R.style.Theme_Azul){
            //setTheme(R.style.Theme_Azul);
            getTheme().applyStyle(R.style.Theme_Azul,true);
            leerSharedPreferences();

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_Rojo){
            //setTheme(R.style.Theme_Rojo);
            getTheme().applyStyle(R.style.Theme_Rojo,true);
            leerSharedPreferences();

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_Verde){
            //setTheme(R.style.Theme_Verde);
            getTheme().applyStyle(R.style.Theme_Verde,true);
            leerSharedPreferences();

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_Morado){
            //setTheme(R.style.Theme_Morado);
            getTheme().applyStyle(R.style.Theme_Morado,true);
            leerSharedPreferences();

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_PFC){
            //setTheme(R.style.Theme_PFC);
            getTheme().applyStyle(R.style.Theme_PFC,true);
            //leerSharedPreferences();

        }else{
            setTheme(R.style.Theme_PFC);
        }
        eliminarDatosSheredPreferences();
        setContentView(R.layout.activity_personalizacion);
        getSupportActionBar().setTitle("Personalización App");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        SwitchMaterial switchAzul = findViewById(R.id.switch_azul);

        if(switchAzul != null){

            switchAzul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked){

                        String colorApp = "#FFBBDEFB";
                        String primary = "#82B1FF";
                        String backgraund = "#FFFFFF";

                        TEMA = R.style.Theme_Azul;
                        //guardarEnSheredPreferences(TEMA);
                        guardarEnSheredPreferences(colorApp,primary,backgraund,TEMA);
                        //setTheme(TEMA);
                        cambiarColor(colorApp, primary, backgraund);
                        //leerSharedPreferences();
                        //switchAzul.setChecked(true);

                    } else {

                        switchAzul.setChecked(false);
                    }
                }
            });
            //switchAzul.setChecked(true);
        }
    }

    public void cambiarColor(String colorApp, String colorPrimary, String backgraund){

        getWindow().setStatusBarColor(Color.parseColor(colorApp));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
        getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        //getWindow().setLogo();
    }

    public void guardarEnSheredPreferences(String colorApp, String colorPrimary, String backgraund, int tema){

        SharedPreferences preferencias = PersonalizacionActivity.this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString(COLOR_APP, colorApp);
        editor.putString(COLOR_PRIMARY, colorPrimary);
        editor.putString(BACKGRAUND, backgraund);
        editor.putInt(THEME, tema);
        editor.apply();
    }

    /*public void guardarEnSheredPreferences(int colorApp){

        SharedPreferences preferencias = PersonalizacionActivity.this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt(THEME, colorApp);
        editor.apply();
    }*/

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

    public void eliminarDatosSheredPreferences(){

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        preferencias.edit().remove(COLOR_APP).apply();
        preferencias.edit().remove(COLOR_PRIMARY).apply();
        preferencias.edit().remove(BACKGRAUND).apply();
    }
    public void onBackPressed() {

        Intent intent = new Intent(PersonalizacionActivity.this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed2() {

        Fragment fragGestion = new FragmentGestion();
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayoutPersonalizacion,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
        getSupportActionBar().setTitle("Administración Usuarios");
    }
}