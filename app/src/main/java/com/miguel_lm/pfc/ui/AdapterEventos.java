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
import com.miguel_lm.pfc.modelo.Evento;

import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.EventosViewHolder> {

    private final List<Evento> listaEventos;
    final Context context;
    final SeleccionarEvento seleccionarEvento;

    public AdapterEventos(List<Evento> listaEventos, Context context, SeleccionarEvento seleccionarEvento) {
        this.listaEventos = listaEventos;
        this.context = context;
        this.seleccionarEvento = seleccionarEvento;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventosViewHolder(v, seleccionarEvento);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Evento evento = listaEventos.get(position);
        holder.tv_titulo_evento.setText(evento.getTitulo());
        holder.tv_fecha_evento.setText(evento.getFecha());
        holder.tv_hora_evento.setText(evento.getHora());

        holder.mostrarEvento(evento);
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public static class EventosViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_titulo_evento,tv_fecha_evento,tv_hora_evento;
        ImageButton btn_info_evento;
        private final SeleccionarEvento seleccionarEvento;

        public EventosViewHolder(@NonNull View itemView, SeleccionarEvento seleccionarEvento) {
            super(itemView);

            this.seleccionarEvento = seleccionarEvento;

            tv_titulo_evento = itemView.findViewById(R.id.tv_titulo_evento);
            tv_fecha_evento = itemView.findViewById(R.id.tv_fecha_evento);
            tv_hora_evento = itemView.findViewById(R.id.tv_hora_evento);
            btn_info_evento = itemView.findViewById(R.id.btn_info_evento);
        }

        public void mostrarEvento(final Evento evento) {

            btn_info_evento.setOnClickListener(v -> seleccionarEvento.eventoInfo(evento));
        }
    }
}
