package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.miguel_lm.pfc.R;

public class FragmentGestion extends Fragment {

    ImageButton btn_notificacion_individual,btn_recordatorios,btn_campanhas,btn_personalizacion_app,bt_volver_gestion,btn_juntaDirectiva;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_gestion, container, false);

        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        btn_notificacion_individual = root.findViewById(R.id.btn_notificacion_individual);
        btn_recordatorios = root.findViewById(R.id.btn_recordatorios);
        btn_campanhas = root.findViewById(R.id.btn_campanhas);
        btn_personalizacion_app = root.findViewById(R.id.btn_personalizacion_app);
        bt_volver_gestion = root.findViewById(R.id.bt_volver_gestion);
        btn_juntaDirectiva = root.findViewById(R.id.btn_junta_directiva);

        btn_notificacion_individual.setOnClickListener(v -> accederActivityNotificacion());

        btn_recordatorios.setOnClickListener(v -> accederActivityNotificacionPorSegmentos());

        btn_campanhas.setOnClickListener(v -> accederActivityNotificacionPorSegmentos());

        btn_personalizacion_app.setOnClickListener(v -> personalizacionApp());

        bt_volver_gestion.setOnClickListener(v -> onBackPressed());

        btn_juntaDirectiva.setOnClickListener(v -> juntaDirectiva());

        return root;
    }

    public void accederActivityNotificacion(){

        Intent intent = new Intent(getContext(), NotificacionActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void accederActivityNotificacionPorSegmentos(){

        Intent intent = new Intent(getContext(), NotificacionTopicActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void personalizacionApp(){

        Intent intent = new Intent(getContext(), PersonalizacionActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void juntaDirectiva(){

        Intent intent = new Intent(getContext(), JuntaDirectivaActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed() {

        Intent intent = new Intent(getContext(), ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}