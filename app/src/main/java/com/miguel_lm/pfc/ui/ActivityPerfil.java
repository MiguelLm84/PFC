package com.miguel_lm.pfc.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityPerfil extends AppCompatActivity {

    TextView tv_numSocio,tv_nombre, tv_ap1, tv_ap2, tv_fechaNaci, tv_telefono, tv_email, tv_password;
    Button bt_aceptar, btn_guardar;
    ImageView btn_volver, btn_eliminarUser, btn_editar;
    CircleImageView fotoUsuario;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String numSoci = "";
    String name = "";
    String apell1 = "";
    String apell2 = "";
    String fechaNacim = "";
    String telf = "";
    String mail = "";
    String psswd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        tv_numSocio = findViewById(R.id.tv_num_socio);
        tv_nombre = findViewById(R.id.tv_nom);
        tv_ap1 = findViewById(R.id.tv_ap1);
        tv_ap2 = findViewById(R.id.tv_ap2);
        tv_fechaNaci = findViewById(R.id.tv_fechaNaci);
        tv_telefono = findViewById(R.id.tv_telefono);
        tv_email = findViewById(R.id.tv_email_perfil);
        tv_password = findViewById(R.id.tv_password_perfil);
        bt_aceptar = findViewById(R.id.btn_aceptar);
        fotoUsuario = findViewById(R.id.foto_user);
        btn_volver = findViewById(R.id.bt_volver);
        btn_eliminarUser = findViewById(R.id.btn_eliminar);
        btn_editar = findViewById(R.id.btn_editar);
        btn_guardar = findViewById(R.id.btn_guardar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        mostrarDatosUser();

        btn_volver.setOnClickListener(v -> onBackPressed());

        fotoUsuario.setOnClickListener(v -> cambiarFotoPerfil());
    }

    public void mostrarDatosUser(){

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        if (user != null) {

            String emailUser = user.getEmail();
            assert emailUser != null;

            mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            Usuario user = dataSnapshot.getValue(Usuario.class);
                            assert user != null;

                            if(emailUser.equals(user.getEmail())){
                                String numSocio = user.getNumSocio();
                                String nombre = user.getNombre();
                                String ap1 = user.getApellido1();
                                String ap2 = user.getApellido2();
                                String fNaci = user.getFechaNaci();
                                String telefono = user.getTelefono();
                                String email = user.getEmail();
                                String password = user.getPassword();

                                tv_numSocio.setText(numSocio);
                                tv_nombre.setText(nombre);
                                tv_ap1.setText(ap1);
                                tv_ap2.setText(ap2);
                                tv_fechaNaci.setText(fNaci);
                                tv_telefono.setText(telefono);
                                tv_email.setText(email);
                                tv_password.setText(password);
                            }
                        }

                    } else {
                        Toast.makeText(ActivityPerfil.this,"ERROR, los datos no se han podido recuperar.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Toast.makeText(this, "Error, no se han podido recuperar los datos.",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAceptar(View view){

        Intent intent = new Intent(ActivityPerfil.this, ActivityNavigationDrawer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onClickEliminar(View view){

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        numSoci = tv_numSocio.getText().toString();

        if (user != null) {

            Query query = mDatabase.orderByChild("numSocio").equalTo(numSoci);
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            String id = dataSnapshot.getKey();
                            assert id != null;
                            mDatabase.child(id).child("numSocio").removeValue();
                            mDatabase.child(id).child("nombre").removeValue();
                            mDatabase.child(id).child("apellido1").removeValue();
                            mDatabase.child(id).child("apellido2").removeValue();
                            mDatabase.child(id).child("fechaNaci").removeValue();
                            mDatabase.child(id).child("telefono").removeValue();
                            mDatabase.child(id).child("email").removeValue();
                            mDatabase.child(id).child("password").removeValue();
                            mDatabase.child(id).child("uid").removeValue();
                            mDatabase.child(id).child("token").removeValue();
                            mDatabase.child(id).removeValue();

                            if(user != null){

                                user.delete().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(ActivityPerfil.this, AuthActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        Toast.makeText(ActivityPerfil.this, "Cuenta eliminada correctamente.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Toast.makeText(ActivityPerfil.this, "Error, no se ha podido eliminar la cuenta.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Toast.makeText(ActivityPerfil.this, "Error, no se ha podido eliminar la cuenta.",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickModificar(View view){

        habilitarFoco();

        btn_guardar.setVisibility(View.VISIBLE);
        bt_aceptar.setVisibility(View.INVISIBLE);

        mostrarDatosUser();

        View.OnClickListener onClickEtiquetaFecha= v -> {

            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            final DatePickerDialog dpd = new DatePickerDialog(ActivityPerfil.this, (datePicker, year1, monthOfYear, dayOfMonth) -> {
                cal.set(Calendar.YEAR, year1);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM 'de' yyyy", new Locale("es","ES"));
                tv_fechaNaci.setText(formatoFecha.format(cal.getTime()));
            }, year, month, day);
            dpd.show();
        };

        tv_fechaNaci.setOnClickListener(onClickEtiquetaFecha);

        btn_guardar.setOnClickListener(v -> {

            numSoci = tv_numSocio.getText().toString();
            name = tv_nombre.getText().toString();
            apell1 = tv_ap1.getText().toString();
            apell2 = tv_ap2.getText().toString();
            fechaNacim = tv_fechaNaci.getText().toString();
            telf = tv_telefono.getText().toString();
            mail = tv_email.getText().toString();
            psswd = tv_password.getText().toString();
            Usuario usuario = new Usuario("",numSoci,name,apell1,apell2,fechaNacim,telf,mail,psswd);

            if(!numSoci.isEmpty() && !name.isEmpty() && !apell1.isEmpty() && !apell2.isEmpty()
                    && !fechaNacim.isEmpty() && !telf.isEmpty() && !mail.isEmpty() && !psswd.isEmpty()){

                if(psswd.length() >= 6){
                    modificarUser(usuario);

                } else{
                    Toast.makeText(ActivityPerfil.this, "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(ActivityPerfil.this, "No puede haber campos vacios.", Toast.LENGTH_SHORT).show();
            }

            bt_aceptar.setVisibility(View.VISIBLE);
            btn_guardar.setVisibility(View.INVISIBLE);

            deshabilitarFoco();
        });
    }

    public void habilitarFoco(){

        tv_numSocio.setFocusable(true);
        tv_numSocio.setFocusableInTouchMode(true);
        tv_nombre.setFocusable(true);
        tv_nombre.setFocusableInTouchMode(true);
        tv_ap1.setFocusable(true);
        tv_ap1.setFocusableInTouchMode(true);
        tv_ap2.setFocusable(true);
        tv_ap2.setFocusableInTouchMode(true);
        tv_fechaNaci.setFocusable(true);
        tv_fechaNaci.setFocusableInTouchMode(true);
        tv_telefono.setFocusable(true);
        tv_telefono.setFocusableInTouchMode(true);
        tv_email.setFocusable(true);
        tv_email.setFocusableInTouchMode(true);
        tv_password.setFocusable(true);
        tv_password.setFocusableInTouchMode(true);
    }

    public void deshabilitarFoco(){

        tv_numSocio.setFocusable(false);
        tv_numSocio.setFocusableInTouchMode(false);
        tv_nombre.setFocusable(false);
        tv_nombre.setFocusableInTouchMode(false);
        tv_ap1.setFocusable(false);
        tv_ap1.setFocusableInTouchMode(false);
        tv_ap2.setFocusable(false);
        tv_ap2.setFocusableInTouchMode(false);
        tv_fechaNaci.setFocusable(false);
        tv_fechaNaci.setFocusableInTouchMode(false);
        tv_telefono.setFocusable(false);
        tv_telefono.setFocusableInTouchMode(false);
        tv_email.setFocusable(false);
        tv_email.setFocusableInTouchMode(false);
        tv_password.setFocusable(false);
        tv_password.setFocusableInTouchMode(false);
    }

    public void modificarUser(Usuario usuario) {

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        if (user != null) {

            Query query = mDatabase.orderByChild("numSocio").equalTo(usuario.getNumSocio());
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            String id = dataSnapshot.getKey();
                            assert id != null;
                            mDatabase.child(id).child("numSocio").setValue(numSoci);
                            mDatabase.child(id).child("nombre").setValue(name);
                            mDatabase.child(id).child("apellido1").setValue(apell1);
                            mDatabase.child(id).child("apellido2").setValue(apell2);
                            mDatabase.child(id).child("fechaNaci").setValue(fechaNacim);
                            mDatabase.child(id).child("telefono").setValue(telf);
                            mDatabase.child(id).child("email").setValue(mail);
                            mDatabase.child(id).child("password").setValue(psswd);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void cambiarFotoPerfil(){

        //TODO: implementar método para que al pulsar sobre el circulo se acceda a la memoria interna del teléfono y acceda a las fotos.
        //TODO: Una vez seleccionada la foto se guardará en Storage de Firebase y se mostrará en el circulo.
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}