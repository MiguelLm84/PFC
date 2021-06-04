package com.miguel_lm.pfc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PersonalizacionActivity extends AppCompatActivity {


    TextView tv_cambioColorApp;
    static SwitchMaterial switchAzul;
    static SwitchMaterial switchRojo;
    static SwitchMaterial switchVerde;
    static SwitchMaterial switchMorado;
    static SwitchMaterial switchNegro;

    public static final String PREF_FICHERO = "preferencias";
    public static final String COLOR_APP = "color app";
    public static final String COLOR_PRIMARY = "color primary";
    public static final String BACKGRAUND = "backgraund";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        setContentView(R.layout.activity_personalizacion);
        getSupportActionBar().setTitle("Personalización App");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        initSwitch();
        setPreviousCheckedSwitch();
        selectTheme();
    }

    @Override
    public void onResume() {

        super.onResume();
        disableAllSwitchs();
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        setPreviousCheckedSwitch();
        selectTheme();
    }
    // Desactiva todos los switches
    public void disableAllSwitchs(){
        // Recorro cada uno de los switch y proceso cada elemento con un consumidor (Java 8)
        // Es igual que si hago un for(SwitchMaterial s: getSwitchs() )
        getSwitchs().forEach(new Consumer<SwitchMaterial>() {
            @Override
            public void accept(SwitchMaterial switchMaterial) {
                switchMaterial.setChecked(false);
            }
        });
    }

    public void initSwitch(){

        tv_cambioColorApp = findViewById(R.id.tv_cambioColorApp);
        switchAzul = findViewById(R.id.switch_azul);
        switchRojo = findViewById(R.id.switch_rojo);
        switchVerde = findViewById(R.id.switch_verde);
        switchMorado = findViewById(R.id.switch_morado);
        switchNegro = findViewById(R.id.switch_negro);
        disableAllSwitchs();
    }

    public List<SwitchMaterial> getSwitchs(){

        List<SwitchMaterial> colorSwitch = new ArrayList<>();

        colorSwitch.addAll(
                // Convierto el array a lista (la lista es un collectionable)
                Arrays.asList(
                        // Array de materiales
                        new SwitchMaterial[]{switchAzul,
                                switchRojo,
                                switchVerde,
                                switchMorado,
                                switchNegro
                        }
                )
        );

        return colorSwitch;

    }

    public void setPreviousCheckedSwitch(){
        getSwitchs().get(ColorConfigurator.getInstance().getCheckedSwitch(this)).setChecked(true);
    }

    public void selectTheme(){

        // Controla que solo pueda haber 1 activo
        Context c = this;
        for(int i = 0; i < getSwitchs().size(); i++){
            SwitchMaterial s = getSwitchs().get(i);
            int temporalPosicionSwitch = i;
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(!isChecked){
                        System.out.println("Se ha desactivad: " + temporalPosicionSwitch);
                        int TEMA = R.style.Theme_PFC;
                        Resources.Theme theme = c.getTheme();
                        c.setTheme(TEMA);
                        ColorConfigurator.getInstance().applyColorFromSwitches(c, getSupportActionBar(), theme, TEMA, getSwitchs(), tv_cambioColorApp );
                    }

                    if(isChecked){
                        // Se activa i
                        s.setChecked(true);
                        ColorConfigurator.getInstance().saveCheckedSwitch(c, temporalPosicionSwitch);

                        // Desactivo los demás
                        for(int j = 0; j < getSwitchs().size(); j++){


                            // Desactivo los demás
                            if(temporalPosicionSwitch !=j){
                                getSwitchs().get(j).setChecked(false);
                            }
                        }

                        int TEMA = R.style.Theme_Azul;
                        switch(temporalPosicionSwitch){
                            case 0:
                                TEMA = R.style.Theme_Azul;
                                break;
                            case 1:
                                TEMA = R.style.Theme_Rojo;
                                break;
                            case 2:
                                TEMA = R.style.Theme_Verde;
                                break;
                            case 3:
                                TEMA = R.style.Theme_Morado;
                                break;
                            case 4:
                                TEMA = R.style.Theme_PFC;
                                break;
                            default:
                                TEMA = R.style.Theme_PFC;
                                break;
                        }

                        System.out.println("Aplico el tema: " + TEMA);
                        System.out.println("Se ha pulsado el swith: " + temporalPosicionSwitch);
                        // Aplico el tema TEMA
                        /*TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = c.getTheme();
                        theme.resolveAttribute(TEMA, typedValue, true);*/
                        Resources.Theme theme = c.getTheme();
                        c.setTheme(TEMA);
                        ColorConfigurator.getInstance().applyColorFromSwitches(c, getSupportActionBar(), theme, TEMA, getSwitchs(),tv_cambioColorApp );

                    }
                }

            });
        }
    }

    public void onBackPressed() {

        Intent intent = new Intent(PersonalizacionActivity.this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed2() {

        Fragment fragGestion = new FragmentGestion();
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayoutPersonalizacion,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
        getSupportActionBar().setTitle("Administración Usuarios");
    }
}