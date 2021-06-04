package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

public class InfoEventosActivity extends AppCompatActivity {

    EditText ed_titulo_evento, ed_fecha_evento, ed_hora_evento, ed_detalle_evento;
    Button btn_registrar_evento, btn_aceptar_evento;
    TextView titulo_eventos, titulo_info_evento;
    ImageButton bt_volver_eventos;
    //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
        setContentView(R.layout.activity_add_eventos);

        init();
        Bundle parametros = this.getIntent().getExtras();
        mostrarEvento(parametros);
        bt_volver_eventos.setOnClickListener(v -> onBackPressed());
        btn_aceptar_evento.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
    }

    public void init(){

        titulo_eventos = findViewById(R.id.titulo_eventos);
        titulo_info_evento = findViewById(R.id.titulo_info_evento);
        ed_titulo_evento = findViewById(R.id.ed_titulo_evento);
        ed_fecha_evento = findViewById(R.id.ed_fecha_evento);
        ed_hora_evento = findViewById(R.id.ed_hora_evento);
        ed_detalle_evento = findViewById(R.id.ed_detalle_evento);
        btn_registrar_evento = findViewById(R.id.btn_registrar_evento);
        btn_aceptar_evento  = findViewById(R.id.btn_aceptar_evento);
        bt_volver_eventos = findViewById(R.id.bt_volver_eventos);
        visibilidad();
        deshabilitarFoco();
    }

    public void deshabilitarFoco(){

        ed_titulo_evento.setFocusable(false);
        ed_titulo_evento.setFocusableInTouchMode(false);
        ed_fecha_evento.setFocusable(false);
        ed_fecha_evento.setFocusableInTouchMode(false);
        ed_hora_evento.setFocusable(false);
        ed_hora_evento.setFocusableInTouchMode(false);
        ed_detalle_evento.setFocusable(false);
        ed_detalle_evento.setFocusableInTouchMode(false);
    }

    public void visibilidad(){

        btn_registrar_evento.setVisibility(View.GONE);
        btn_aceptar_evento.setVisibility(View.VISIBLE);
        titulo_eventos.setVisibility(View.GONE);
        titulo_info_evento.setVisibility(View.VISIBLE);
    }

    public void mostrarEvento(Bundle bundle){

        if(bundle != null){
            String titulo = bundle.getString("titulo");
            String body = bundle.getString("body");
            String fecha = bundle.getString("fecha");
            String hora = bundle.getString("hora");

            ed_titulo_evento.setText(titulo);
            ed_fecha_evento.setText(fecha);
            ed_hora_evento.setText(hora);
            ed_detalle_evento.setText(body);

        } else {
            Toast.makeText(InfoEventosActivity.this,"ERROR, los datos no se han podido recuperar.",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(InfoEventosActivity.this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}