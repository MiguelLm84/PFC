package com.miguel_lm.pfc.ui.ui.direccion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguel_lm.pfc.R;

public class MapsFragment extends Fragment {

    private final OnMapReadyCallback callback = googleMap -> {

        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        LatLng posicion =  new LatLng(42.2150023, -8.6693683);
        googleMap.addMarker(new MarkerOptions().position(posicion).title("Sede de AssociationManager"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 12));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(2.0f);
        googleMap.setMaxZoomPreference(30.0f);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}