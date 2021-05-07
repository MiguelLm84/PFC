package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.miguel_lm.pfc.R;

public class FragmentGestion extends Fragment {

    ImageButton btn_notificacion_individual,btn_recordatorios,btn_campanhas,btn_personalizacion_app,bt_volver_gestion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_gestion, container, false);

        btn_notificacion_individual = root.findViewById(R.id.btn_notificacion_individual);
        btn_recordatorios = root.findViewById(R.id.btn_recordatorios);
        btn_campanhas = root.findViewById(R.id.btn_campanhas);
        btn_personalizacion_app = root.findViewById(R.id.btn_personalizacion_app);
        bt_volver_gestion = root.findViewById(R.id.bt_volver_gestion);

        btn_notificacion_individual.setOnClickListener(v -> accederActivityNotificacion());

        btn_recordatorios.setOnClickListener(v -> accederActivityNotificacion());

        btn_campanhas.setOnClickListener(v -> accederActivityNotificacion());

        btn_personalizacion_app.setOnClickListener(v -> personalizacionApp());

        bt_volver_gestion.setOnClickListener(v -> onBackPressed());

        return root;
    }

    public void accederActivityNotificacion(){

        Intent intent = new Intent(getContext(), NotificacionActivity.class);
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

    public void onBackPressed() {

        Intent intent = new Intent(getContext(), ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        /*FragmentManager fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.FragmentLayoutLista, fragInfo, null).commit();


        Fragment fragGestion = new FragmentGestion();

        getChildFragmentManager().beginTransaction().add(R.id.FrameLayoutGestion,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();*/
    }
}