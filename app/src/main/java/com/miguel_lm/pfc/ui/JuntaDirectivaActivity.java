package com.miguel_lm.pfc.ui;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import com.miguel_lm.pfc.providers.FirebaseSpinnerProvider;
import com.miguel_lm.pfc.providers.OnTaskCompleted;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JuntaDirectivaActivity extends AppCompatActivity {

    Spinner spinner_presidente,spinner_vicepresidente,spinner_secretario_general,spinner_tesorero,spinner_vocal1,spinner_vocal2,spinner_vocal3,spinner_vocal4;
    Button btn_aceptarJuntaDirectiva;
    ImageButton bt_volver_juntaDirectiva;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String nombre;
    String ap1;
    String ap2;
    List<CharSequence> presidenteSpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> vicepresidenteSpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> secretarioGeneralSpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> tesoreroSpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> vocal1SpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> vocal2SpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> vocal3SpinnerData = new ArrayList<CharSequence>();
    List<CharSequence> vocal4SpinnerData = new ArrayList<CharSequence>();
    ArrayAdapter<CharSequence> arrayAdapterPresidente;
    ArrayAdapter<CharSequence> arrayAdapterVicepresidente;
    ArrayAdapter<CharSequence> arrayAdapterSecretarioGeneral;
    ArrayAdapter<CharSequence> arrayAdapterTesorero;
    ArrayAdapter<CharSequence> arrayAdapterVocal1;
    ArrayAdapter<CharSequence> arrayAdapterVocal2;
    ArrayAdapter<CharSequence> arrayAdapterVocal3;
    ArrayAdapter<CharSequence> arrayAdapterVocal4;
    HashMap<String, Integer> hashMap = new HashMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        setContentView(R.layout.activity_junta_directiva);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getSupportActionBar().hide();

        initSpinnerPresidente();
        initSpinnerVicepresidente();
        initSpinnerSecretarioGeneral();
        initSpinnerTesorero();
        initSpinnerVocal1();
        initSpinnerVocal2();
        initSpinnerVocal3();
        initSpinnerVocal4();

        init();

        btn_aceptarJuntaDirectiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int presidenteIndice = hashMap.get("presidente");
                int vicepresidenteIndice = hashMap.get("vicepresidente");
                int secretarioGeneralIndice = hashMap.get("secretario general");
                int tesoreroIndice = hashMap.get("tesorero");
                int vocal1Indice = hashMap.get("vocal 1");
                int vocal2Indice = hashMap.get("vocal 2");
                int vocal3Indice = hashMap.get("vocal 3");
                int vocal4Indice = hashMap.get("vocal 4");

                String presidente = String.valueOf(presidenteSpinnerData.get(presidenteIndice));
                registrarCargosJuntaDirectiva(presidente, "presidente");
                String vicepresidente = String.valueOf(vicepresidenteSpinnerData.get(vicepresidenteIndice));
                registrarCargosJuntaDirectiva(vicepresidente, "vicepresidente");
                String secretarioGeneral = String.valueOf(secretarioGeneralSpinnerData.get(secretarioGeneralIndice));
                registrarCargosJuntaDirectiva(secretarioGeneral, "secretario general");
                String tesorero = String.valueOf(tesoreroSpinnerData.get(tesoreroIndice));
                registrarCargosJuntaDirectiva(tesorero, "tesorero");
                String vocal1 = String.valueOf(vocal1SpinnerData.get(vocal1Indice));
                registrarCargosJuntaDirectiva(vocal1, "vocal 1");
                String vocal2 = String.valueOf(vocal2SpinnerData.get(vocal2Indice));
                registrarCargosJuntaDirectiva(vocal2, "vocal 2");
                String vocal3 = String.valueOf(vocal3SpinnerData.get(vocal3Indice));
                registrarCargosJuntaDirectiva(vocal3, "vocal 3");
                String vocal4 = String.valueOf(vocal4SpinnerData.get(vocal4Indice));
                registrarCargosJuntaDirectiva(vocal4, "vocal 4");
            }
        });
        bt_volver_juntaDirectiva.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
    }

    public void init(){

        btn_aceptarJuntaDirectiva = findViewById(R.id.btn_aceptar_juntaDirectiva);
        bt_volver_juntaDirectiva = findViewById(R.id.bt_volver_juntaDirectiva);
    }

    public void initSpinnerPresidente(){
        // Tomo el componente visual de la vista para que me sea manipulable como JO (Java Object)
        spinner_presidente = findViewById(R.id.spinner_presidente);


        // Inicializo el adaptador visual del spinner con:
        //      La activdad actual como contexto de ejecución
        //      Mi layout del spinner que he personalizado (Miguel)
        //      El objeto que contendrá los valores posibles del spinner
        arrayAdapterPresidente = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text,
                presidenteSpinnerData);

        // Vinculo el spinner con el adaptador
        spinner_presidente.setAdapter(arrayAdapterPresidente);

        // Tengo 1 proveedor que me devuelve los posibles presidentes
        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                // Update data model
                presidenteSpinnerData.clear();
                presidenteSpinnerData.addAll(nombres);
                // Update User Interface (View)
                arrayAdapterPresidente.notifyDataSetChanged();
            }
        });
        // El spinner presidente es capaz de reaccionar cuando
        //      onItemSelected: se selecciona 1 item del desplegable
        //      onNothingSelected: cuando se cancela una selección
        spinner_presidente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_presidente] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());

                hashMap.put("presidente", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerVicepresidente() {

        spinner_vicepresidente = findViewById(R.id.spinner_vicepresidente);
        arrayAdapterVicepresidente = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, vicepresidenteSpinnerData);
        spinner_vicepresidente.setAdapter(arrayAdapterVicepresidente);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                vicepresidenteSpinnerData.clear();
                vicepresidenteSpinnerData.addAll(nombres);
                arrayAdapterVicepresidente.notifyDataSetChanged();
            }
        });
        spinner_vicepresidente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_vicepresidente] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("vicepresidente", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerSecretarioGeneral() {

        spinner_secretario_general = findViewById(R.id.spinner_secretario_general);
        arrayAdapterSecretarioGeneral = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, secretarioGeneralSpinnerData);
        spinner_secretario_general.setAdapter(arrayAdapterSecretarioGeneral);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                secretarioGeneralSpinnerData.clear();
                secretarioGeneralSpinnerData.addAll(nombres);
                arrayAdapterSecretarioGeneral.notifyDataSetChanged();
            }
        });
        spinner_secretario_general.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_secretario_general] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("secretario general", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerTesorero() {

        spinner_tesorero = findViewById(R.id.spinner_tesorero);
        arrayAdapterTesorero = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, tesoreroSpinnerData);
        spinner_tesorero.setAdapter(arrayAdapterTesorero);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                tesoreroSpinnerData.clear();
                tesoreroSpinnerData.addAll(nombres);
                arrayAdapterTesorero.notifyDataSetChanged();
            }
        });
        spinner_tesorero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_tesorero] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("tesorero", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerVocal1() {

        spinner_vocal1 = findViewById(R.id.spinner_vocal1);
        arrayAdapterVocal1 = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, vocal1SpinnerData);
        spinner_vocal1.setAdapter(arrayAdapterVocal1);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                vocal1SpinnerData.clear();
                vocal1SpinnerData.addAll(nombres);
                arrayAdapterVocal1.notifyDataSetChanged();
            }
        });
        spinner_vocal1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_vocal1] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("vocal 1", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerVocal2() {

        spinner_vocal2 = findViewById(R.id.spinner_vocal2);
        arrayAdapterVocal2 = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, vocal2SpinnerData);
        spinner_vocal2.setAdapter(arrayAdapterVocal2);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                vocal2SpinnerData.clear();
                vocal2SpinnerData.addAll(nombres);
                arrayAdapterVocal2.notifyDataSetChanged();
            }
        });
        spinner_vocal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_vocal2] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("vocal 2", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerVocal3() {

        spinner_vocal3 = findViewById(R.id.spinner_vocal3);
        arrayAdapterVocal3 = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, vocal3SpinnerData);
        spinner_vocal3.setAdapter(arrayAdapterVocal3);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                vocal3SpinnerData.clear();
                vocal3SpinnerData.addAll(nombres);
                arrayAdapterVocal3.notifyDataSetChanged();
            }
        });
        spinner_vocal3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_vocal3] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("vocal 3", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initSpinnerVocal4() {

        spinner_vocal4 = findViewById(R.id.spinner_vocal4);
        arrayAdapterVocal4 = new ArrayAdapter<>(JuntaDirectivaActivity.this,
                R.layout.spinner_item_text, vocal4SpinnerData);
        spinner_vocal4.setAdapter(arrayAdapterVocal4);

        FirebaseSpinnerProvider.getCargoDirectivo(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<CharSequence> nombres) {
                vocal4SpinnerData.clear();
                vocal4SpinnerData.addAll(nombres);
                arrayAdapterVocal4.notifyDataSetChanged();
            }
        });
        spinner_vocal4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("[spinner_vocal4] se ha seleccionado: " + adapterView.getItemAtPosition(i).toString());
                hashMap.put("vocal 4", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /*public void registrarJuntaDirectiva(){

        UserSeleccionSpinners(spinner_presidente,presidenteSpinnerData,"presidente");
        UserSeleccionSpinners(spinner_vicepresidente,vicepresidenteSpinnerData,"vicepresidente");
        UserSeleccionSpinners(spinner_secretario_general,secretarioGeneralSpinnerData,"secretario general");
        UserSeleccionSpinners(spinner_tesorero,tesoreroSpinnerData,"tesorero");
        UserSeleccionSpinners(spinner_vocal1,vocal1SpinnerData,"Vocal 1");
        UserSeleccionSpinners(spinner_vocal2,vocal2SpinnerData,"Vocal 2");
        UserSeleccionSpinners(spinner_vocal3,vocal3SpinnerData,"Vocal 3");
        UserSeleccionSpinners(spinner_vocal4,vocal4SpinnerData,"Vocal 4");
    }*/

    public void UserSeleccionSpinners(Spinner spinner, List<CharSequence> lista, String cargo){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               spinner.setSelection(position);
               String seleccionSpinner = spinner.getSelectedItem().toString();
                System.out.println("[SPINNER CARGO SELECCIONADO] se ha seleccionado: " + adapterView.getItemAtPosition(position).toString());
               for(int i=0;i<lista.size();i++){
                   if(seleccionSpinner.contentEquals(lista.get(i))){

                       //registrarCargosJuntaDirectiva(seleccionSpinner,cargo);
                   }
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void registrarCargosJuntaDirectiva(String nombre, String cargo){

        switch(cargo) {

            case "presidente":
                asignarRolEnBD("presidente", nombre);
                break;

            case "vicepresidente":
                asignarRolEnBD("vicepresidente", nombre);
                break;

            case "secretario general":
                asignarRolEnBD("secretario general", nombre);
                break;

            case "tesorero":
                asignarRolEnBD("tesorero", nombre);
                break;

            case "vocal 1":
                asignarRolEnBD("vocal 1", nombre);
                break;

            case "vocal 2":
                asignarRolEnBD("vocal 2", nombre);
                break;

            case "vocal 3":
                asignarRolEnBD("vocal 3", nombre);
                break;

            case "vocal 4":
                asignarRolEnBD("vocal 4", nombre);
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
                            mDatabase.child(id).child("admin").setValue(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void asignarRolEnBD(String cargo,String nombre){

        try{
            comprobarCargoEnBD(cargo);
            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Usuario user = ds.getValue(Usuario.class);
                            if (user != null) {
                                String nombreCompleto = user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2();
                                if (nombreCompleto.equals(nombre)) {
                                    user.setRol(cargo);
                                    user.setAdmin(true);
                                    mDatabase.child("Users").child(ds.getKey()).child("rol").setValue(cargo);
                                    mDatabase.child("Users").child(ds.getKey()).child("admin").setValue(true);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(JuntaDirectivaActivity.this, "Junta Directiva registrada correctamente.", Toast.LENGTH_SHORT).show();
            volverActivityAnterior();


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
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutJuntaDirectiva,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}