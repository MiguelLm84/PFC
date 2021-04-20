package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import com.miguel_lm.pfc.ui.ui.Fragment_info;

import java.util.ArrayList;
import java.util.List;

public class FragmentLista extends Fragment implements SeleccionarUsuario {

    private List<Usuario> listaUsuarios;
    private AdapterUsuario adapterUsuario;
    DatabaseReference mDatabase;
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lista, container, false);

        RecyclerView recyclerViewUsers = root.findViewById(R.id.RecyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        listaUsuarios = new ArrayList<>();

        adapterUsuario = new AdapterUsuario(listaUsuarios, getContext(), this);
        recyclerViewUsers.setAdapter(adapterUsuario);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String numSocio = dataSnapshot.child("numSocio").getValue().toString();
                        String nombre = dataSnapshot.child("nombre").getValue().toString();
                        String ap1 = dataSnapshot.child("apellido1").getValue().toString();
                        String ap2 = dataSnapshot.child("apellido2").getValue().toString();
                        listaUsuarios.add(new Usuario("",numSocio,nombre,ap1,ap2,"","","",""));
                    }
                }
                adapterUsuario.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void usuarioInfo(Usuario usuario) {

        Fragment fragInfoUser = new Fragment_info(usuario);

        getChildFragmentManager().beginTransaction().add(R.id.FragmentLayoutLista, fragInfoUser).commit();
        transaction = getChildFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.FragmentLayoutLista,fragInfoUser).commit();
        transaction.addToBackStack(null);
    }
}