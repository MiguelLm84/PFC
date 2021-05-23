package com.miguel_lm.pfc.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import java.util.List;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.UsuarioViewHolder>{

    private final List<Usuario> listaUsuarios;
    final Context context;
    final SeleccionarUsuario seleccionarUsuario;

    public AdapterUsuario(List<Usuario> listaUsuarios, Context context, SeleccionarUsuario seleccionarUsuario) {
        this.listaUsuarios = listaUsuarios;
        this.context = context;
        this.seleccionarUsuario = seleccionarUsuario;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UsuarioViewHolder(v,seleccionarUsuario);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario user = listaUsuarios.get(position);
        holder.tv_numSocio_item.setText(user.getNumSocio());
        holder.tv_nom_item.setText(user.getNombre());
        holder.tv_ap1_item.setText(user.getApellido1());
        holder.tv_ap2_item.setText(user.getApellido2());

        holder.mostrarContacto(user);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_numSocio_item,tv_nom_item,tv_ap1_item,tv_ap2_item;
        ImageButton btn_info_user;
        private final SeleccionarUsuario seleccionarUsuario;

        public UsuarioViewHolder(@NonNull View itemView, SeleccionarUsuario seleccionarUsuario) {
            super(itemView);

            this.seleccionarUsuario = seleccionarUsuario;

            tv_numSocio_item = itemView.findViewById(R.id.tv_numSocio_item);
            tv_nom_item = itemView.findViewById(R.id.tv_nom_item);
            tv_ap1_item = itemView.findViewById(R.id.tv_ap1_item);
            tv_ap2_item = itemView.findViewById(R.id.tv_ap2_item);
            btn_info_user = itemView.findViewById(R.id.btn_info_user);
        }

        public void mostrarContacto(final Usuario usuario) {

            btn_info_user.setOnClickListener(v -> seleccionarUsuario.usuarioInfo(usuario));
        }
    }
}
