package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    EditText ed_numSocio,ed_nombre,ed_ap1,ed_ap2,ed_fechaNaci,ed_telefono,ed_email,ed_password;
    Button bt_registrar;
    ImageView btn_volver;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String numSocio = "";
    String nom = "";
    String ap1 = "";
    String ap2 = "";
    String fNaci = "";
    String tel = "";
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ed_numSocio = findViewById(R.id.ed_numSocio);
        ed_nombre = findViewById(R.id.ed_nombre);
        ed_ap1 = findViewById(R.id.ed_ap1);
        ed_ap2 = findViewById(R.id.ed_ap2);
        ed_fechaNaci = findViewById(R.id.ed_fechaNaci);
        ed_telefono = findViewById(R.id.ed_telefono);
        ed_email = findViewById(R.id.ed_correo);
        ed_password = findViewById(R.id.ed_Password);
        bt_registrar = findViewById(R.id.btn_registrar);
        btn_volver = findViewById(R.id.btn_volver);

        btn_volver.setOnClickListener(v -> onBackPressed());

        View.OnClickListener onClickEtiquetaFecha= v -> {

            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            final DatePickerDialog dpd = new DatePickerDialog(RegistroActivity.this, (datePicker, year1, monthOfYear, dayOfMonth) -> {
                cal.set(Calendar.YEAR, year1);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM 'de' yyyy", new Locale("es","ES"));
                ed_fechaNaci.setText(formatoFecha.format(cal.getTime()));
            }, year, month, day);
            dpd.show();
        };

        ed_fechaNaci.setOnClickListener(onClickEtiquetaFecha);

        bt_registrar.setOnClickListener(v -> {

            numSocio = ed_numSocio.getText().toString();
            nom = ed_nombre.getText().toString();
            ap1 = ed_ap1.getText().toString();
            ap2 = ed_ap2.getText().toString();
            fNaci = ed_fechaNaci.getText().toString();
            tel = ed_telefono.getText().toString();
            email = ed_email.getText().toString();
            password = ed_password.getText().toString();

            if(!numSocio.isEmpty() && !nom.isEmpty() && !ap1.isEmpty() && !ap2.isEmpty()
                    && !fNaci.isEmpty() && !tel.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                if(password.length() >= 6){
                    registrarUser();

                } else{
                    Toast.makeText(RegistroActivity.this, "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegistroActivity.this, "No puede haber campos vacios.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registrarUser(){

        String id = mDatabase.push().getKey();
        Usuario user = new Usuario(id, numSocio,nom, ap1,ap2, fNaci,tel,email, password);

        assert id != null;
        mDatabase.child("Users").push().setValue(user);
        mAuth.createUserWithEmailAndPassword(email, password);
        Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegistroActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, AuthActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}