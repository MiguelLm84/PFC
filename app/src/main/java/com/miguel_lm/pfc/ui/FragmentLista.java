package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;

public class FragmentLista extends Fragment implements SeleccionarUsuario {

    private AdapterUsers adapterUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lista, container, false);
        RecyclerView recyclerViewUsers = root.findViewById(R.id.RecyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapterUsers = new AdapterUsers(getContext(), this);
        recyclerViewUsers.setAdapter(adapterUsers);

        return root;
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {

    }

    @Override
    public void usuarioInfoPulsado(Usuario usuario) {

        Intent intent = new Intent(getActivity(), ActivityInfo.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}