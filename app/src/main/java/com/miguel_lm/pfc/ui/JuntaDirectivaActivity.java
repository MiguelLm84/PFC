package com.miguel_lm.pfc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import java.util.ArrayList;
import java.util.List;

public class JuntaDirectivaActivity extends AppCompatActivity {

    Spinner spinner_presidente,spinner_vicepresidente,spinner_secretario_general,spinner_tesorero,spinner_vocal1,spinner_vocal2,spinner_vocal3,spinner_vocal4;
    Button btn_aceptarJuntaDirectiva;
    ImageButton bt_volver_juntaDirectiva;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
    String nombre;
    String ap1;
    String ap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junta_directiva);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getSupportActionBar().hide();

        init();
        cargarDatosSpinnerPresidente();
        cargarDatosSpinnerVicepresidente();
        cargarDatosSpinnerSecretarioGeneral();
        cargarDatosSpinnerTesorero();
        cargarDatosSpinnerVocal1();
        cargarDatosSpinnerVocal2();
        cargarDatosSpinnerVocal3();
        cargarDatosSpinnerVocal4();

        btn_aceptarJuntaDirectiva.setOnClickListener(v -> registrarCargosJuntaDirectiva());
        bt_volver_juntaDirectiva.setOnClickListener(v -> onBackPressed());
    }

    public void init(){

        spinner_presidente = findViewById(R.id.spinner_presidente);
        spinner_vicepresidente = findViewById(R.id.spinner_vicepresidente);
        spinner_secretario_general = findViewById(R.id.spinner_secretario_general);
        spinner_tesorero = findViewById(R.id.spinner_tesorero);
        spinner_vocal1 = findViewById(R.id.spinner_vocal1);
        spinner_vocal2 = findViewById(R.id.spinner_vocal2);
        spinner_vocal3 = findViewById(R.id.spinner_vocal3);
        spinner_vocal4 = findViewById(R.id.spinner_vocal4);
        btn_aceptarJuntaDirectiva = findViewById(R.id.btn_aceptar_juntaDirectiva);
        bt_volver_juntaDirectiva = findViewById(R.id.bt_volver_juntaDirectiva);
    }

    public List<CharSequence> obtenerDatosBD(){

        List<CharSequence> listaUsuarios = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        nombre = ds.child("nombre").getValue().toString();
                        ap1 = ds.child("apellido1").getValue().toString();
                        ap2 = ds.child("apellido2").getValue().toString();

                        //listaUsuarios.add(new Usuario(nombre, ap1, ap2));
                        String nombreCompleto = nombre + " " + ap1 + " " + ap2;
                        listaUsuarios.add(nombreCompleto);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listaUsuarios;
    }

    public void cargarDatosSpinnerPresidente() {

        List<CharSequence> listaPresidente = obtenerDatosBD();
        ArrayAdapter<CharSequence> arrayAdapterPresidente = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text,listaPresidente);
        spinner_presidente.setAdapter(arrayAdapterPresidente);

        spinner_presidente.setPrompt("Presidente");
        spinner_presidente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_presidente.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void cargarDatosSpinnerVicepresidente() {

        ArrayAdapter<CharSequence> arrayAdapterVicepresidente = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_vicepresidente.setAdapter(arrayAdapterVicepresidente);
        /*spinner_vicepresidente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_vicepresidente.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void cargarDatosSpinnerSecretarioGeneral() {

        ArrayAdapter<CharSequence> arrayAdapterSecretarioGeneral = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_secretario_general.setAdapter(arrayAdapterSecretarioGeneral);
        /*spinner_secretario_general.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_secretario_general.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void cargarDatosSpinnerTesorero() {

        ArrayAdapter<CharSequence> arrayAdapterTesorero = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_tesorero.setAdapter(arrayAdapterTesorero);
        /*spinner_tesorero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_tesorero.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void cargarDatosSpinnerVocal1() {

        ArrayAdapter<CharSequence> arrayAdapterVocal1 = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_vocal1.setAdapter(arrayAdapterVocal1);
        /*spinner_vocal1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_vocal1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void cargarDatosSpinnerVocal2() {


        ArrayAdapter<CharSequence> arrayAdapterVocal2 = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_vocal2.setAdapter(arrayAdapterVocal2);
        /*spinner_vocal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_vocal2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void cargarDatosSpinnerVocal3() {

        ArrayAdapter<CharSequence> arrayAdapterVocal3 = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_vocal3.setAdapter(arrayAdapterVocal3);
        /*spinner_vocal3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_vocal3.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void cargarDatosSpinnerVocal4() {

        ArrayAdapter<CharSequence> arrayAdapterVocal4 = new ArrayAdapter<>(JuntaDirectivaActivity.this, R.layout.spinner_item_text, obtenerDatosBD());
        spinner_vocal4.setAdapter(arrayAdapterVocal4);
        /*spinner_vocal4.getSelectedItem().toString();
        spinner_vocal4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void UserSeleccionSpinners(){


    }

    public void registrarCargosJuntaDirectiva(){

        String cargo = null;

        switch(cargo) {

            case "presidente":
                asignarRolEnBD("presidente");
                break;

            case "vicepresidente":
                asignarRolEnBD("vicepresidente");
                break;

            case "secretario general":
                asignarRolEnBD("secretario general");
                break;

            case "tesorero":
                asignarRolEnBD("tesorero");
                break;

            case "vocal 1":
                asignarRolEnBD("vocal 1");
                break;

            case "vocal 2":
                asignarRolEnBD("vocal 2");
                break;

            case "vocal 3":
                asignarRolEnBD("vocal 3");
                break;

            case "vocal 4":
                asignarRolEnBD("vocal 4");
                break;

            default:
                Toast.makeText(JuntaDirectivaActivity.this, "Error, no se ha podido registrar la Junta Directiva.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void comprobarCargoEnBD(String cargo){

        Query query = mDatabase.orderByChild("rol").equalTo(cargo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String id = dataSnapshot.getKey();
                        if(id != null){
                            mDatabase.child(id).child("rol").setValue("user");
                            mDatabase.child(id).child("isAdmin").setValue(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void asignarRolEnBD(String cargo){

        try{
            String id = mDatabase.push().getKey();

            if(id != null){
                comprobarCargoEnBD(cargo);
                mDatabase.child("rol").setValue(cargo);
                mDatabase.child("isAdmin").setValue(true);
                Toast.makeText(JuntaDirectivaActivity.this, "Junta Directiva registrada correctamente.", Toast.LENGTH_SHORT).show();
                volverActivityAnterior();

            } else {
                Log.e("TAG_ERROR_JUNTA_DIRECTIVA", "ERROR JUNTA DIRECTIVA: " + id);
            }

        } catch(Exception e){
            Log.e("TAG_ERROR","Error al encriptar el password");
        }
    }

    public void volverActivityAnterior() {

        Intent intent = new Intent(JuntaDirectivaActivity.this, ActivityNavigationDrawer.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed() {

        /*Intent intent = new Intent(JuntaDirectivaActivity.this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

        Fragment fragGestion = new FragmentGestion();
        getSupportActionBar().show();
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayoutJuntaDirectiva,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}