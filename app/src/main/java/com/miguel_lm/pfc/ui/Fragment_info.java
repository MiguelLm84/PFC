package com.miguel_lm.pfc.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.miguel_lm.pfc.ui.ActivityNavigationDrawer;
import com.miguel_lm.pfc.ui.ActivityPerfil;
import com.miguel_lm.pfc.ui.AuthActivity;
import com.miguel_lm.pfc.ui.FragmentLista;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_info extends Fragment {

    Usuario usuario;
    FragmentTransaction transaction;

    TextView tv_numSocio,tv_nombre, tv_ap1, tv_ap2, tv_fechaNaci, tv_telefono, tv_email, tv_password;
    Button bt_aceptar, btn_guardar;
    ImageView imageView_user, btn_eliminarUser, btn_editar;
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

    public Fragment_info(Usuario usuario) {

        this.usuario = usuario;
    }
    public Fragment_info() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        tv_numSocio = root.findViewById(R.id.ed_numSocio_infoUser);
        tv_nombre = root.findViewById(R.id.ed_nombre_infoUser);
        tv_ap1 = root.findViewById(R.id.ed_ap1_infoUser);
        tv_ap2 = root.findViewById(R.id.ed_ap2_infoUser);
        tv_fechaNaci = root.findViewById(R.id.ed_fechaNaci_infoUser);
        tv_telefono = root.findViewById(R.id.ed_telefono_infoUser);
        tv_email = root.findViewById(R.id.ed_correo_infoUser);
        tv_password = root.findViewById(R.id.ed_Password_infoUser);
        bt_aceptar = root.findViewById(R.id.bt_aceptar_infoUser);
        btn_guardar = root.findViewById(R.id.btn_guardar_infoUser);
        imageView_user = root.findViewById(R.id.imageView_user);
        btn_eliminarUser = root.findViewById(R.id.btn_eliminar);
        btn_editar = root.findViewById(R.id.btn_editar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        mostrarDatosUser(usuario);

        return root;
    }

    public void mostrarDatosUser(Usuario usuario){

        /*mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        if (user != null) {

            String emailUser = user.getEmail();
            assert emailUser != null;

            if(emailUser.equals(usuario.getEmail())){
                mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                                Usuario user = dataSnapshot.getValue(Usuario.class);
                                assert user != null;*/

                                 if(Fragment_info.this.getArguments() != null){

                                   String numSocio = getArguments().getString("numSocio");
                                    String nombre = getArguments().getString("nombre");
                                    String ap1 = getArguments().getString("apellido1");
                                    String ap2 = getArguments().getString("apellido2");
                                    String fNaci = getArguments().getString("fechaNaci");
                                    String telefono = getArguments().getString("telefono");
                                    String email = getArguments().getString("email");
                                    String password = getArguments().getString("password");


                                    tv_numSocio.setText(numSocio);
                                    tv_nombre.setText(nombre);
                                    tv_ap1.setText(ap1);
                                    tv_ap2.setText(ap2);
                                    tv_fechaNaci.setText(fNaci);
                                    tv_telefono.setText(telefono);
                                    tv_email.setText(email);
                                    tv_password.setText(password);

                                } else {
                                    Toast.makeText(getContext(),"ERROR, los datos no se han podido recuperar.",Toast.LENGTH_SHORT).show();
                                }
                            }

                        /*} else {
                            Toast.makeText(getContext(),"ERROR, los datos no se han podido recuperar.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }else {
                Toast.makeText(getContext(), "Error, no se han podido cargar los datos.",Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getContext(), "Error, no se han podido recuperar los datos.",Toast.LENGTH_SHORT).show();
        }
    }*/

    public void onClickAceptarInfo(View view){

        Fragment fragLista = new FragmentLista();
        getChildFragmentManager().beginTransaction().add(R.id.FragmentLayoutLista, fragLista).commit();
        transaction = getChildFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.FragmentLayoutLista,fragLista).commit();
        transaction.addToBackStack(null);
    }

    public void onClickEliminarInfo(View view){

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

                            if(user != null){

                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Intent intent = new Intent(getContext(), AuthActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            Toast.makeText(getContext(), "Cuenta eliminada correctamente.",Toast.LENGTH_SHORT).show();
                                        }
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

    public void onClickModificarInfo(View view){

        habilitarFoco();

        btn_guardar.setVisibility(View.VISIBLE);
        bt_aceptar.setVisibility(View.INVISIBLE);

        mostrarDatosUser(usuario);

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

        btn_guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
            }
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

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(getContext(), ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }*/
}