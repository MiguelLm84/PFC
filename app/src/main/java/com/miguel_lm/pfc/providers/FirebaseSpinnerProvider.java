package com.miguel_lm.pfc.providers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseSpinnerProvider {

    public static void getCargoDirectivo(OnTaskCompleted listener){

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            List<CharSequence> nombres = new ArrayList<>();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String nombre = "", ap1 = "", ap2 = "";
                                nombre = ds.child("nombre").getValue().toString();
                                ap1 = ds.child("apellido1").getValue().toString();
                                ap2 = ds.child("apellido2").getValue().toString();

                                //listaUsuarios.add(new Usuario(nombre, ap1, ap2));
                                String nombreCompleto = nombre + " " + ap1 + " " + ap2;
                                //presidenteSpinnerData.add(nombreCompleto);
                                nombres.add(nombreCompleto);

                            }
                            System.out.println("He finalizado " + nombres.toArray().toString());
                            listener.onTaskCompleted(nombres);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        }
    }
