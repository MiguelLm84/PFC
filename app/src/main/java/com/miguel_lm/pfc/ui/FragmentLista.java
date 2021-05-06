package com.miguel_lm.pfc.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class FragmentLista extends Fragment implements SeleccionarUsuario {

    private List<Usuario> listaUsuarios;
    private AdapterUsuario adapterUsuario;
    DatabaseReference mDatabase;
    FragmentTransaction transaction;
    Usuario user;
    Fragment_info fragInfo;
    String id = "";
    String numSocio = "";
    String nombre = "";
    String ap1 = "";
    String ap2 = "";
    String fNaci = "";
    String tel = "";
    String mail = "";
    String psswrd = "";

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
                        id = dataSnapshot.child("uid").getValue().toString();
                        numSocio = dataSnapshot.child("numSocio").getValue().toString();
                        nombre = dataSnapshot.child("nombre").getValue().toString();
                        ap1 = dataSnapshot.child("apellido1").getValue().toString();
                        ap2 = dataSnapshot.child("apellido2").getValue().toString();
                        fNaci = dataSnapshot.child("fechaNaci").getValue().toString();
                        tel = dataSnapshot.child("telefono").getValue().toString();
                        mail = dataSnapshot.child("email").getValue().toString();
                        psswrd = dataSnapshot.child("password").getValue().toString();

                        user = new Usuario(id, numSocio, nombre, ap1, ap2, fNaci, tel, mail, psswrd);
                        listaUsuarios.add(user);
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

        fragInfo = new Fragment_info();
        Bundle bundle = new Bundle();

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
                            String sSubCadena = id.substring(0,5);

                            String idUser = usuario.getUid();
                            String subCadenaUser = idUser.substring(0,5);

                            if (sSubCadena.equals(subCadenaUser)) {

                                bundle.putString("numSocio", usuario.getNumSocio());
                                bundle.putString("nombre", usuario.getNombre());
                                bundle.putString("apellido1", usuario.getApellido1());
                                bundle.putString("apellido2", usuario.getApellido2());
                                bundle.putString("fechaNaci", usuario.getFechaNaci());
                                bundle.putString("telefono", usuario.getTelefono());
                                bundle.putString("email", usuario.getEmail());
                                bundle.putString("password", usuario.getPassword());

                                fragInfo.setArguments(bundle);

                                FragmentManager fragmentManager = getChildFragmentManager();
                                transaction = fragmentManager.beginTransaction();
                                transaction.add(R.id.FragmentLayoutLista, fragInfo, null).commit();
                                return;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

