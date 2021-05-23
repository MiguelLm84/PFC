package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.miguel_lm.pfc.R;

public class PersonalizacionActivity extends AppCompatActivity {

    static SwitchMaterial switchAzul;
    static SwitchMaterial switchRojo;
    static SwitchMaterial switchVerde;
    static SwitchMaterial switchMorado;
    static SwitchMaterial switchNegro;
    SharedPreferences preferencias;

    public static final String PREF_FICHERO = "preferencias";
    public static final String COLOR_APP = "color app";
    public static final String COLOR_PRIMARY = "color primary";
    public static final String BACKGRAUND = "backgraund";
    public static final String CHECKED = "checked";
    public static boolean SELECCIONADO = true;
    public static final String THEME = "tema";
    public static int TEMA = R.style.Theme_PFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aplicarTema();
        setTheme(TEMA);
        setContentView(R.layout.activity_personalizacion);
        getSupportActionBar().setTitle("Personalización App");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        switchAzul = findViewById(R.id.switch_azul);
        switchRojo = findViewById(R.id.switch_rojo);
        switchVerde = findViewById(R.id.switch_verde);
        switchMorado = findViewById(R.id.switch_morado);
        switchNegro = findViewById(R.id.switch_negro);

        seleccionTemaConSwitch();
        leerSharedPreferences();
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

    public void cambiarColor(String colorApp, String colorPrimary, String backgraund){

        getWindow().setStatusBarColor(Color.parseColor(colorApp));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
        getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        //getWindow().setLogo();
    }

    public void colorSwitch(String colorApp){

        int colorAzul = R.color.colorPrimaryDarkAzul;
        int colorRojo = R.color.rojoOscuro;
        int colorVerde = R.color.teal_700;
        int colorMorado = R.color.purple_500;
        int colorNegro = R.color.black;

        if(colorApp.equals("#039BE5")){
            switchAzul.setThumbResource(colorAzul);
            switchRojo.setThumbResource(colorAzul);
            switchVerde.setThumbResource(colorAzul);
            switchMorado.setThumbResource(colorAzul);
            switchNegro.setThumbResource(colorAzul);
        }
        if(colorApp.equals("#B71C1C")){
            switchAzul.setThumbResource(colorRojo);
            switchRojo.setThumbResource(colorRojo);
            switchVerde.setThumbResource(colorRojo);
            switchMorado.setThumbResource(colorRojo);
            switchNegro.setThumbResource(colorRojo);
        }
        if(colorApp.equals("#FF018786")){
            switchAzul.setThumbResource(colorVerde);
            switchRojo.setThumbResource(colorVerde);
            switchVerde.setThumbResource(colorVerde);
            switchMorado.setThumbResource(colorVerde);
            switchNegro.setThumbResource(colorVerde);
        }
        if(colorApp.equals("#2D0091")){
            switchAzul.setThumbResource(colorMorado);
            switchRojo.setThumbResource(colorMorado);
            switchVerde.setThumbResource(colorMorado);
            switchMorado.setThumbResource(colorMorado);
            switchNegro.setThumbResource(colorMorado);
        }
        if(colorApp.equals("#FF000000")){
            switchAzul.setThumbResource(colorNegro);
            switchRojo.setThumbResource(colorNegro);
            switchVerde.setThumbResource(colorNegro);
            switchMorado.setThumbResource(colorNegro);
            switchNegro.setThumbResource(colorNegro);
        }
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
                        guardarEnSheredPreferences("#039BE5", "#82B1FF", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#039BE5", "#82B1FF", "#FFFFFF");
                        //colorSwitch("#039BE5");
                        leerSharedPreferences();
                        switchRojo.setChecked(false);
                        switchVerde.setChecked(false);
                        switchMorado.setChecked(false);
                        switchNegro.setChecked(false);

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            leerSharedPreferences();
        }

        if (switchRojo != null) {

            switchRojo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Rojo;
                        guardarEnSheredPreferences("#B71C1C", "#D50000", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#B71C1C", "#D50000", "#FFFFFF");
                        //colorSwitch("#B71C1C");
                        leerSharedPreferences();
                        switchAzul.setChecked(false);
                        switchVerde.setChecked(false);
                        switchMorado.setChecked(false);
                        switchNegro.setChecked(false);

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            leerSharedPreferences();
        }

        if (switchVerde != null) {

            switchVerde.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Verde;
                        guardarEnSheredPreferences("#FF018786", "#B0018786", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF018786", "#B0018786", "#FFFFFF");
                        //colorSwitch("#FF018786");
                        leerSharedPreferences();
                        switchAzul.setChecked(false);
                        switchRojo.setChecked(false);
                        switchMorado.setChecked(false);
                        switchNegro.setChecked(false);

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            leerSharedPreferences();
        }

        if (switchMorado != null) {

            switchMorado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_Morado;
                        guardarEnSheredPreferences("#2D0091", "#FF3700B3", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#2D0091", "#FF3700B3", "#FFFFFF");
                        //colorSwitch("#2D0091");
                        leerSharedPreferences();
                        switchAzul.setChecked(false);
                        switchRojo.setChecked(false);
                        switchVerde.setChecked(false);
                        switchNegro.setChecked(false);

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            leerSharedPreferences();
        }

        if (switchNegro != null) {

            switchNegro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                        //colorSwitch("#FF000000");
                        leerSharedPreferences();
                        switchAzul.setChecked(false);
                        switchRojo.setChecked(false);
                        switchVerde.setChecked(false);
                        switchMorado.setChecked(false);

                    } else {

                        TEMA = R.style.Theme_PFC;
                        guardarEnSheredPreferences("#FF000000", "#273036", "#FFFFFF", TEMA, SELECCIONADO);
                        setTheme(TEMA);
                        cambiarColor("#FF000000", "#273036", "#FFFFFF");
                    }
                }
            });
            leerSharedPreferences();

            /*if(switchAzul.isChecked()){
                switchAzul.setChecked(SELECCIONADO);

            }else{
                switchAzul.setChecked(false);
            }
            if(switchRojo.isChecked()){
                switchRojo.setChecked(SELECCIONADO);

            } else{
                switchRojo.setChecked(false);
            }
            if(switchVerde.isChecked()){
                switchVerde.setChecked(SELECCIONADO);

            } else {
                switchVerde.setChecked(false);
            }
            if(switchMorado.isChecked()){
                switchMorado.setChecked(SELECCIONADO);

            } else {
                switchMorado.setChecked(false);
            }
            if(switchNegro.isChecked()){
                switchNegro.setChecked(SELECCIONADO);

            } else{
                switchNegro.setChecked(false);
            }*/
        }
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
        String colorApp = preferencias.getString(COLOR_APP, "#FF000000");
        getWindow().setStatusBarColor(Color.parseColor(colorApp));

        String colorPrimary = preferencias.getString(COLOR_PRIMARY,"#273036");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        String backgraund = preferencias.getString(BACKGRAUND,"#FFFFFF");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));

        boolean checked = preferencias.getBoolean(CHECKED,SELECCIONADO);

        if(switchAzul.isChecked()){
            switchAzul.setChecked(checked);
            switchRojo.setChecked(false);
            switchVerde.setChecked(false);
            switchMorado.setChecked(false);
            switchNegro.setChecked(false);

        } else if(switchRojo.isChecked()){
            switchAzul.setChecked(false);
            switchRojo.setChecked(checked);
            switchVerde.setChecked(false);
            switchMorado.setChecked(false);
            switchNegro.setChecked(false);

        } else if(switchVerde.isChecked()){
            switchAzul.setChecked(false);
            switchRojo.setChecked(false);
            switchVerde.setChecked(checked);
            switchMorado.setChecked(false);
            switchNegro.setChecked(false);

        } else if(switchMorado.isChecked()){
            switchAzul.setChecked(false);
            switchRojo.setChecked(false);
            switchVerde.setChecked(false);
            switchMorado.setChecked(checked);
            switchNegro.setChecked(false);

        } else if(switchNegro.isChecked()){
            switchAzul.setChecked(false);
            switchRojo.setChecked(false);
            switchVerde.setChecked(false);
            switchMorado.setChecked(false);
            switchNegro.setChecked(checked);
        }
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