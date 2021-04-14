package com.miguel_lm.pfc.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<ViewHolderUsers> {

    private List<Usuario> listaUsuarios;
    private final Context context;
    private final SeleccionarUsuario selecUser;

    public AdapterUsers(final Context context, SeleccionarUsuario selecUser){
        this.context = context;
        this.selecUser = selecUser;
    }

    public void actualizarListado(List<Usuario> listaUsuariosSeleccionados) {
        this.listaUsuarios = listaUsuariosSeleccionados;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolderUsers(v, selecUser);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUsers holder, int position) {

        Usuario usuarioAPintar = listaUsuarios.get(position);
        holder.mostrarUsuario(usuarioAPintar,context);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios == null ? 0 : listaUsuarios.size();
    }
}
