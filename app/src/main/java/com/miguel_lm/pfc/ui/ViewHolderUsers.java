package com.miguel_lm.pfc.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;

public class ViewHolderUsers extends RecyclerView.ViewHolder {

    //private final ImageView imagenPerfil;
    private final TextView tv_numSocio, tvNombre, tv_ap1, tv_ap2;
    ImageButton btn_info_user;
    private final LinearLayout LinearLayoutUsers;
    private final SeleccionarUsuario seleccionarUsuario;

    public ViewHolderUsers(@NonNull View itemView, SeleccionarUsuario selecUser) {
        super(itemView);
        this.seleccionarUsuario = selecUser;

        //imagenPerfil = itemView.findViewById(R.id.imagenPerfil_item);
        tv_numSocio = itemView.findViewById(R.id.tv_numSocio_item);
        tvNombre = itemView.findViewById(R.id.tv_nom_item);
        tv_ap1 = itemView.findViewById(R.id.tv_ap1_item);
        tv_ap2 = itemView.findViewById(R.id.tv_ap2_item);
        btn_info_user = itemView.findViewById(R.id.btn_info_user);

        LinearLayoutUsers = itemView.findViewById(R.id.LinearLayoutUsers);
    }

    public void mostrarUsuario(final Usuario usuario, final Context context) {

        //int imagenId = context.getResources().getIdentifier(usuario.getImagen(), "drawable", context.getPackageName());

        //imagenPerfil.setImageResource(imagenId);
        tv_numSocio.setText(usuario.getNumSocio());
        tvNombre.setText(usuario.getNombre());
        tv_ap1.setText(usuario.getApellido1());
        tv_ap2.setText(usuario.getApellido2());

        btn_info_user.setOnClickListener(v -> seleccionarUsuario.usuarioInfoPulsado(usuario));
        /*LinearLayoutUsers.setOnLongClickListener(v -> {

            AlertDialog.Builder builderEliminar_Confirmar = new AlertDialog.Builder(context);
            builderEliminar_Confirmar.setIcon(R.drawable.ic_exclamation);
            builderEliminar_Confirmar.setMessage("¿Eliminar los usuarios?");
            builderEliminar_Confirmar.setNegativeButton("Cancelar", null);
            builderEliminar_Confirmar.setPositiveButton("Borrar", (dialogInterface, which) -> {
                seleccionarUsuario.eliminarUsuario(usuario);
                Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show();
            });
            builderEliminar_Confirmar.create().show();
            return false;
        });*/
    }
}
