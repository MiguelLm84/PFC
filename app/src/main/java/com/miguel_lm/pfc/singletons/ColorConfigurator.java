package com.miguel_lm.pfc.singletons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Literales;

import java.util.ArrayList;
import java.util.List;

public class ColorConfigurator {

    private static ColorConfigurator instance;

    private ColorConfigurator(){
        super();
    }

    public static ColorConfigurator getInstance(){
        if(instance == null){
            instance = new ColorConfigurator();
        }

        return instance;
    }

    public void saveTheme(@NonNull Context c, String colorApp, String colorPrimary, String backgraund, int tema){
        String archivoPreferencias = c.getResources().getString(R.string.preferencias);
        SharedPreferences preferencias = c.getSharedPreferences(archivoPreferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString(Literales.COLOR_APP, colorApp);
        editor.putString(Literales.COLOR_PRIMARY, colorPrimary);
        editor.putString(Literales.BACKGRAUND, backgraund);
        editor.putInt(Literales.THEME,tema);
        //editor.putBoolean(CHECKED, checked);
        editor.apply();
    }

    public void saveCheckedSwitch(@NonNull Context c, int posicion){
        String archivoPreferencias = c.getResources().getString(R.string.preferencias);
        SharedPreferences preferencias = c.getSharedPreferences(archivoPreferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt(Literales.CHECKED, posicion);
        editor.apply();
    }

    public int getCheckedSwitch(@NonNull Context c){
        String archivoPreferencias = c.getResources().getString(R.string.preferencias);
        SharedPreferences preferencias = c.getSharedPreferences(archivoPreferencias, Context.MODE_PRIVATE);
        @SuppressLint("ResourceType") int check = preferencias.getInt(
                Literales.CHECKED,
                4);

        return check;
    }

    public int getTheme(Context c){
        String archivoPreferencias = c.getResources().getString(R.string.preferencias);
        SharedPreferences preferencias = c.getSharedPreferences(archivoPreferencias, Context.MODE_PRIVATE);
        @SuppressLint("ResourceType") int TEMA = preferencias.getInt(
                Literales.THEME,
                R.style.Theme_PFC);

        return TEMA;
    }

    // Lee los colores desedos
    public  List<String> readTheme(@NonNull Context c, ActionBar ab){
        List<String> colores = new ArrayList<>();
        String archivoPreferencias = c.getResources().getString(R.string.preferencias);
        SharedPreferences preferencias = c.getSharedPreferences(archivoPreferencias, Context.MODE_PRIVATE);

        @SuppressLint("ResourceType") String colorApp = preferencias.getString(
                Literales.COLOR_APP,
                c.getResources().getString(R.color.primary));

        @SuppressLint("ResourceType") String colorPrimary = preferencias.getString(
                Literales.COLOR_PRIMARY,
                c.getResources().getString(R.color.primary));

        @SuppressLint("ResourceType") String backgraund = preferencias.getString(
                Literales.BACKGRAUND,
                c.getResources().getString(R.color.primary));


        @SuppressLint("ResourceType") int TEMA = preferencias.getInt(
                Literales.THEME,
                R.style.Theme_PFC);

        colores.add(colorApp);
        colores.add(colorPrimary);
        colores.add(backgraund);

        ((Activity)c).getWindow().setStatusBarColor(Color.parseColor(colorApp));

        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        ((Activity)c).getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
        ((Activity)c).getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        ((Activity)c).getTheme().applyStyle(TEMA, true);

        ((Activity)c).getWindow().getDecorView().invalidate();
        //((Activity)c).recreate();
        return colores;
    }

    @SuppressLint("ResourceAsColor")
    public void applyColorFromSwitches(Context c, ActionBar ab, Resources.Theme theme, int temaInt, List<SwitchMaterial> switches, TextView titulo){

        TypedValue typedValueColorApp = new TypedValue();
        TypedValue typedValuePrimary = new TypedValue();
        TypedValue typedValueBackground = new TypedValue();

        TypedValue typedValueSwithDesactivado = new TypedValue();

        theme.resolveAttribute(R.attr.colorPrimary, typedValueColorApp, true);
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValuePrimary, true);
        theme.resolveAttribute(R.attr.colorOnPrimary, typedValueBackground, true);
        //theme.resolveAttribute(R.attr.colorSecondary, typedValueSwithDesactivado, true);

        @ColorInt int colorApp = typedValueColorApp.data;
        @ColorInt int colorPrimary = typedValuePrimary.data;
        @ColorInt int colorBackground = typedValueBackground.data;
        @ColorInt int colorSwithDesactivado = typedValueSwithDesactivado.data;

        // Save colors as hex
        String hexColorColorApp = String.format("#%06X", (0xFFFFFF & colorApp));
        String hexColorColorPrimary = String.format("#%06X", (0xFFFFFF & colorPrimary));
        String hexColorColorBackground = String.format("#%06X", (0xFFFFFF & colorBackground));


        Log.d("[ColorConfigurator] ", "guardando colores en shared preferences: "
                +  hexColorColorApp + " " +
                hexColorColorPrimary + " " +
                hexColorColorBackground);

        saveTheme(c, hexColorColorApp, hexColorColorPrimary, hexColorColorPrimary, temaInt);


        ((Activity)c).getWindow().setStatusBarColor(colorApp);

        ab.setBackgroundDrawable(new ColorDrawable(colorPrimary));
        ((Activity)c).getWindow().setBackgroundDrawable(new ColorDrawable(colorBackground));
        ((Activity)c).getWindow().setNavigationBarColor(colorPrimary);
        System.out.println("[ColorConfigurator]: debo cambiar TODO el tema");


        if(switches != null) {
            for (SwitchMaterial s : switches) {
                ColorStateList colors = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_enabled},
                                new int[]{android.R.attr.state_checked},
                                new int[]{}
                        },
                        new int[]{
                                colorBackground,
                                colorPrimary,
                                colorPrimary,
                                //colorSwithDesactivado
                                //colorBackgrou
                        }
                );

                s.setThumbTintList(colors);
                s.setTrackTintList(colors);
                s.setTextColor(colors);
                titulo.setTextColor(colors);

            }
        }

        // Invalido la vista y fuerzo que la Interfaz gráfica se recarge: transparente al usuario
        ((Activity)c).getWindow().getDecorView().invalidate();

        //getWindow().setLogo();
    }

    public  List<String> readThemeNoBackgroundDrawable(@NonNull Context c, ActionBar ab){
        List<String> colores = new ArrayList<>();
        String archivoPreferencias = c.getResources().getString(R.string.preferencias);
        SharedPreferences preferencias = c.getSharedPreferences(archivoPreferencias, Context.MODE_PRIVATE);

        @SuppressLint("ResourceType") String colorApp = preferencias.getString(
                Literales.COLOR_APP,
                c.getResources().getString(R.color.primary));

        @SuppressLint("ResourceType") String colorPrimary = preferencias.getString(
                Literales.COLOR_PRIMARY,
                c.getResources().getString(R.color.primary));

        @SuppressLint("ResourceType") String backgraund = preferencias.getString(
                Literales.BACKGRAUND,
                c.getResources().getString(R.color.primary));


        @SuppressLint("ResourceType") int TEMA = preferencias.getInt(
                Literales.THEME,
                R.style.Theme_PFC);

        colores.add(colorApp);
        colores.add(colorPrimary);
        colores.add(backgraund);

        ((Activity)c).getWindow().setStatusBarColor(Color.parseColor(colorApp));

        //ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        ((Activity)c).getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(backgraund)));
        ((Activity)c).getWindow().setNavigationBarColor(Color.parseColor(colorPrimary));

        ((Activity)c).getTheme().applyStyle(TEMA, true);

        ((Activity)c).getWindow().getDecorView().invalidate();
        //((Activity)c).recreate();
        return colores;
    }

}
