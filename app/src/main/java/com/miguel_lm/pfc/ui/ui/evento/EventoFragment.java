package com.miguel_lm.pfc.ui.ui.evento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Evento;
import com.miguel_lm.pfc.modelo.Usuario;
import com.miguel_lm.pfc.ui.AdapterEventos;
import com.miguel_lm.pfc.ui.AddEventosActivity;
import com.miguel_lm.pfc.ui.InfoEventosActivity;
import com.miguel_lm.pfc.ui.SeleccionarEvento;

import java.util.ArrayList;
import java.util.List;

public class EventoFragment extends Fragment implements SeleccionarEvento {

    List<Evento> listaEventos;
    AdapterEventos adapterEventos;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    BottomAppBar bottomAppBarEventos;
    FloatingActionButton fab_eventos;
    RecyclerView recyclerViewEventos;
    String id = "";
    String titulo = "";
    String body = "";
    String fecha = "";
    String hora = "";
    Evento evento;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    boolean emailVerified;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        comprobarIsAdmin();
        root = inflater.inflate(R.layout.fragment_eventos, container, false);
        init();

        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaEventos = new ArrayList<>();
        adapterEventos = new AdapterEventos(listaEventos, getContext(), this);
        recyclerViewEventos.setAdapter(adapterEventos);

        fab_eventos.setOnClickListener(v -> IrAddEventosActivity());
        obtenerDatosDeEventosDeLaBD();

        return root;
    }

    @Override
    public void onResume() {

        super.onResume();
       //ColorConfigurator.getInstance().readTheme(getContext(), getSupportActionBar());
    }

    public void init(){

        fab_eventos = root.findViewById(R.id.fab_eventos);
        bottomAppBarEventos = root.findViewById(R.id.bottomAppBarEventos);
        recyclerViewEventos = root.findViewById(R.id.RecycleViewEventos);
    }

    public void comprobarIsAdmin() {

        if (user != null) {
            String emailUser = user.getEmail();

            emailVerified = user.isEmailVerified();

            if (emailUser != null) {
                mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                Usuario user = dataSnapshot.getValue(Usuario.class);

                                if (user != null && emailUser.equals(user.getEmail())) {
                                    String rol = user.getRol();
                                    boolean admin = user.isAdmin();
                                    if(!rol.equals("user") && admin){
                                        bottomAppBarEventos.setVisibility(View.VISIBLE);
                                        fab_eventos.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                        } else {
                            Log.e("TAG ERROR COMPROBACIÓN ROL", "ERROR, los datos no se han podido recuperar.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    public void obtenerDatosDeEventosDeLaBD(){

        mDatabase.child("Eventos").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        id = dataSnapshot.child("uid").getValue().toString();
                        titulo = dataSnapshot.child("titulo").getValue().toString();
                        body = dataSnapshot.child("body").getValue().toString();
                        fecha = dataSnapshot.child("fecha").getValue().toString();
                        hora = dataSnapshot.child("hora").getValue().toString();

                        evento = new Evento(id, titulo, body, fecha, hora);
                        listaEventos.add(evento);
                    }
                }
                adapterEventos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void IrAddEventosActivity(){

        Intent intent = new Intent(getContext(), AddEventosActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void eventoInfo(Evento evento) {

        if (evento != null) {

            String idEvento = evento.getUid();

            Query query = mDatabase.child("Eventos").orderByChild("uid").equalTo(idEvento);
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            String id = dataSnapshot.getKey();
                            if(id != null){
                                String sSubCadena = id.substring(0,5);

                                String subCadenaUser = idEvento.substring(0,5);

                                if (sSubCadena.equals(subCadenaUser)) {

                                    Intent intent = new Intent(getContext(), InfoEventosActivity.class);
                                    intent.putExtra("titulo", evento.getTitulo());
                                    intent.putExtra("body", evento.getBody());
                                    intent.putExtra("fecha", evento.getFecha());
                                    intent.putExtra("hora", evento.getHora());
                                    startActivity(intent);
                                    getActivity().finish();
                                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                    return;
                                }

                            } else {
                                Log.e("TAG_ERROR_ID_EVENTO", "ERROR_ID_EVENTO: " + id );
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