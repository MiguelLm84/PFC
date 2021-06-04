package com.miguel_lm.pfc.ui.ui.perfil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.miguel_lm.pfc.singletons.FotoPerfilProvider;
import com.miguel_lm.pfc.ui.ActivityNavigationDrawer;
import com.miguel_lm.pfc.ui.AuthActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PerfilFragment extends Fragment {

    // upper Camel Case miImagenView
    // snack case <--> kebab_case mi_imagen_view

    TextView tv_numSocio,tv_nombre, tv_ap1, tv_ap2, tv_fechaNaci, tv_telefono, tv_email, tv_password;
    Button bt_aceptar, btn_guardar;
    ImageView btn_eliminarUser, btn_editar, btn_fotoPerfil;
    DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser  user = mAuth.getCurrentUser();
    String numSoci = "";
    String name = "";
    String apell1 = "";
    String apell2 = "";
    String fechaNacim = "";
    String telf = "";
    String mail = "";
    String psswd = "";
    View root;

    public static final int PICK_IMAGE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_perfil, container, false);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        init();
        if(FotoPerfilProvider.fotoPerfil!=null){
            btn_fotoPerfil.setImageBitmap(Bitmap.createScaledBitmap(FotoPerfilProvider.fotoPerfil, 100,100, false));
        }

        /*StorageReference dondeEstaLaImagen = FirebaseStorage.getInstance().getReference("profileImages/"+FirebaseAuth.getInstance().getUid());
        dondeEstaLaImagen.getBytes(1024*1024*4).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                System.out.println("He encontrado la imagen y la vamos a meter en el hueco del image view");
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                btn_fotoPerfil.setImageBitmap(Bitmap.createScaledBitmap(bmp, btn_fotoPerfil.getWidth(), btn_fotoPerfil.getHeight(), false));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("No se pudo abrir el socker");
            }
        });*/
        //dondeEstaLaImagen.getFile(new Uri.parse(FirebaseAuth.getInstance().getUid()));

        mostrarDatosUser();

        btn_eliminarUser.setOnClickListener(v -> onClickEliminar());
        btn_editar.setOnClickListener(v -> onClickModificar());
        bt_aceptar.setOnClickListener(v -> onClickAceptar());


        btn_fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Quiero cambiar la foto de perfil");

                // Creando una intencion implicita
                // Miguel siempre usa explicitas (Intent t = new Intent(this, AdondeVoy.class)
                Intent galeria = new Intent();
                galeria.setType("image/*");
                galeria.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(galeria, "Selecciona una imagen de perfil"),PICK_IMAGE);


            }
        });

        return root;
    }



    public void init(){

        tv_numSocio = root.findViewById(R.id.tv_numero_socio);
        tv_nombre = root.findViewById(R.id.tv_nombre);
        tv_ap1 = root.findViewById(R.id.tv_apellido1);
        tv_ap2 = root.findViewById(R.id.tv_apellido2);
        tv_fechaNaci = root.findViewById(R.id.tv_fNaci);
        tv_telefono = root.findViewById(R.id.tv_telef);
        tv_email = root.findViewById(R.id.tv_correo_perfil);
        tv_password = root.findViewById(R.id.tv_psswrd_perfil);
        bt_aceptar = root.findViewById(R.id.bt_aceptar);
        btn_eliminarUser = root.findViewById(R.id.bt_eliminar);
        btn_editar = root.findViewById(R.id.bt_editar);
        btn_guardar = root.findViewById(R.id.bt_guardar);

        btn_fotoPerfil = root.findViewById(R.id.foto_perfil2);
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
                        Toast.makeText(getContext(),"ERROR, los datos no se han podido recuperar.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Toast.makeText(getContext(), "Error, no se han podido recuperar los datos.",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAceptar(){

        Intent intent = new Intent(getContext(), ActivityNavigationDrawer.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onClickEliminar(){

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

                                        Intent intent = new Intent(getContext(), AuthActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        Toast.makeText(getContext(), "Cuenta eliminada correctamente.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Toast.makeText(getContext(), "Error, no se ha podido eliminar la cuenta.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Toast.makeText(getContext(), "Error, no se ha podido eliminar la cuenta.",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickModificar(){

        habilitarFoco();

        btn_guardar.setVisibility(View.VISIBLE);
        bt_aceptar.setVisibility(View.INVISIBLE);

        mostrarDatosUser();

        View.OnClickListener onClickEtiquetaFecha= v -> {

            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            final DatePickerDialog dpd = new DatePickerDialog(getContext(), (datePicker, year1, monthOfYear, dayOfMonth) -> {
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
                    Toast.makeText(getContext(), "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "No puede haber campos vacios.", Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        Intent intent = new Intent(getContext(), ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}