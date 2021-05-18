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

    SwitchMaterial switchAzul;
    SwitchMaterial switchRojo;
    SwitchMaterial switchVerde;
    SwitchMaterial switchMorado;
    SwitchMaterial switchNegro;
    SharedPreferences preferencias;

    public static final String PREF_FICHERO = "preferencias";
    public static final String COLOR_APP = "color app";
    public static final String COLOR_PRIMARY = "color primary";
    public static final String BACKGRAUND = "backgraund";
    public static final String CHECKED = "checked";
    public static boolean SELECCIONADO = false;
    public static final String THEME = "tema";
    public static int TEMA = R.style.Theme_PFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        leerSharedPreferences();
        aplicarTema();
        setContentView(R.layout.activity_personalizacion);
        getSupportActionBar().setTitle("Personalización App");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        switchAzul = findViewById(R.id.switch_azul);
        switchRojo = findViewById(R.id.switch_rojo);
        switchVerde = findViewById(R.id.switch_verde);
        switchMorado = findViewById(R.id.switch_morado);
        switchNegro = findViewById(R.id.switch_negro);

        switchAzul.setChecked(false);
        switchRojo.setChecked(false);
        switchVerde.setChecked(false);
        switchMorado.setChecked(false);
        switchNegro.setChecked(false);

        seleccionTemaConSwitch();
    }

    @Override
    public void onResume() {

        super.onResume();
        leerSharedPreferences();
    }

    public void cambiarColor(String colorApp, String colorPrimary, String backgraund){

        getWindow().setStatusBarColor(Color.parseColor(colorApp));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
        getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        //getWindow().setLogo();
    }

    public void guardarEnSheredPreferences(String colorApp, String colorPrimary, String backgraund, int tema, boolean checked){

        preferencias = PersonalizacionActivity.this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString(COLOR_APP, colorApp);
        editor.putString(COLOR_PRIMARY, colorPrimary);
        editor.putString(BACKGRAUND, backgraund);
        editor.putInt(THEME, tema);
        editor.putBoolean(CHECKED, checked);
        editor.apply();
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

    public void eliminarDatosSheredPreferences(){

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        preferencias.edit().remove(COLOR_APP).apply();
        preferencias.edit().remove(COLOR_PRIMARY).apply();
        preferencias.edit().remove(BACKGRAUND).apply();
    }

    public void aplicarTema(){

        if(PersonalizacionActivity.TEMA == R.style.Theme_Azul){
            setTheme(R.style.Theme_Azul);

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_Rojo){
            setTheme(R.style.Theme_Rojo);

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_Verde){
            setTheme(R.style.Theme_Verde);

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_Morado){
            setTheme(R.style.Theme_Morado);

        }else if(PersonalizacionActivity.TEMA == R.style.Theme_PFC){
            setTheme(R.style.Theme_PFC);

        }else{
            setTheme(R.style.Theme_PFC);
        }
    }

    public void seleccionTemaConSwitch() {

        if (switchAzul != null) {

            switchAzul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Azul;
                        SELECCIONADO = true;
                        guardarEnSheredPreferences("#039BE5", "#82B1FF", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#039BE5", "#82B1FF", "#FFFFFF");

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            switchAzul.setChecked(SELECCIONADO);
            switchRojo.setChecked(false);
            switchVerde.setChecked(false);
            switchMorado.setChecked(false);
            switchNegro.setChecked(false);
        }

        if (switchRojo != null) {

            switchRojo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Rojo;
                        SELECCIONADO = true;
                        guardarEnSheredPreferences("#B71C1C", "#D50000", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#B71C1C", "#D50000", "#FFFFFF");

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            switchRojo.setChecked(SELECCIONADO);
            switchAzul.setChecked(false);
            switchVerde.setChecked(false);
            switchMorado.setChecked(false);
            switchNegro.setChecked(false);
        }

        if (switchVerde != null) {

            switchVerde.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Verde;
                        SELECCIONADO = true;
                        guardarEnSheredPreferences("#FF018786", "#B0018786", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF018786", "#B0018786", "#FFFFFF");

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            switchVerde.setChecked(SELECCIONADO);
            switchAzul.setChecked(false);
            switchRojo.setChecked(false);
            switchMorado.setChecked(false);
            switchNegro.setChecked(false);
        }

        if (switchMorado != null) {

            switchMorado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Morado;
                        SELECCIONADO = true;
                        guardarEnSheredPreferences("#2D0091", "#FF3700B3", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#2D0091", "#FF3700B3", "#FFFFFF");

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            switchMorado.setChecked(SELECCIONADO);
            switchAzul.setChecked(false);
            switchRojo.setChecked(false);
            switchVerde.setChecked(false);
            switchNegro.setChecked(false);
        }

        if (switchNegro != null) {

            switchNegro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_PFC;
                        SELECCIONADO = true;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            switchNegro.setChecked(SELECCIONADO);
            switchAzul.setChecked(false);
            switchRojo.setChecked(false);
            switchVerde.setChecked(false);
            switchMorado.setChecked(false);
        }
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