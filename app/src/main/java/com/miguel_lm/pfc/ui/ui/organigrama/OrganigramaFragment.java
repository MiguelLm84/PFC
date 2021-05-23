package com.miguel_lm.pfc.ui.ui.organigrama;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import com.miguel_lm.pfc.ui.ActivityNavigationDrawer;

public class OrganigramaFragment extends Fragment {

    TextView tv_presidente,tv_vicepresidente, tv_secretario_general, tv_tesorero, tv_vocal1, tv_vocal2, tv_vocal3, tv_vocal4;
    Button bt_aceptar;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_organigrama, container, false);

        tv_presidente = root.findViewById(R.id.tv_presidente_org);
        tv_vicepresidente = root.findViewById(R.id.tv_vicepresidente_org);
        tv_secretario_general = root.findViewById(R.id.tv_secretarioGeneral_org);
        tv_tesorero = root.findViewById(R.id.tv_tesorero_org);
        tv_vocal1 = root.findViewById(R.id.tv_vocal1_org);
        tv_vocal2 = root.findViewById(R.id.tv_vocal2_org);
        tv_vocal3 = root.findViewById(R.id.tv_vocal3_org);
        tv_vocal4 = root.findViewById(R.id.tv_vocal4_org);
        bt_aceptar = root.findViewById(R.id.btn_aceptar_org);

        mostrarDatosUser();
        bt_aceptar.setOnClickListener(v -> botonAceptar());

        return root;
    }

    public void mostrarDatosUser(){

        if (user != null) {

            mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            Usuario user = dataSnapshot.getValue(Usuario.class);
                            cargarDatosTextView(user);
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

    public String obtenerNombreCargo(Usuario user){

        String nombre = user.getNombre();
        String ap1 = user.getApellido1();
        String ap2 = user.getApellido2();

        return nombre + " "  + ap1 + " " + ap2;
    }

    public void cargarDatosTextView(Usuario user){

        String presidente = "presidente";
        String vicepresidente = "vicepresidente";
        String secretarioGeneral = "secretarioGeneral";
        String tesorero = "tesorero";
        String vocal1 = "vocal1";
        String vocal2 = "vocal2";

        String vocal3 = "vocal3";
        String vocal4 = "vocal4";

        if(user != null && presidente.equals(user.getRol())){
            tv_presidente.setText(obtenerNombreCargo(user));

        } else if(user != null && vicepresidente.equals(user.getRol())){
            tv_vicepresidente.setText(obtenerNombreCargo(user));

        } else if(user != null && secretarioGeneral.equals(user.getRol())){
            tv_secretario_general.setText(obtenerNombreCargo(user));

        } else if(user != null && tesorero.equals(user.getRol())){
            tv_tesorero.setText(obtenerNombreCargo(user));

        } else if(user != null && vocal1.equals(user.getRol())){
            tv_vocal1.setText(obtenerNombreCargo(user));

        } else if(user != null && vocal2.equals(user.getRol())){
            tv_vocal2.setText(obtenerNombreCargo(user));

        } else if(user != null && vocal3.equals(user.getRol())){
            tv_vocal3.setText(obtenerNombreCargo(user));

        } else if(user != null && vocal4.equals(user.getRol())){
            tv_vocal4.setText(obtenerNombreCargo(user));
        }
    }

    public void botonAceptar(){

        Intent intent = new Intent(getContext(), ActivityNavigationDrawer.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}