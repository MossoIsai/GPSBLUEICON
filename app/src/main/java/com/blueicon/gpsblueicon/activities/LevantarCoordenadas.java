package com.blueicon.gpsblueicon.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.blueicon.gpsblueicon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;

/**
 * Created by developer on 24/07/17.
 */

public class LevantarCoordenadas extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapaGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levantar_coordenas_layout);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(LevantarCoordenadas.this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapaGoogle = googleMap;
       // mapaGoogle.setOnMapLongClickListener(listener);
       // mapaGoogle.setOnMapClickListener(onMapClickListener);

        boolean success = mapaGoogle.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mapaGoogle.setPadding(10, 250, 10, 250);
        mapaGoogle.setMyLocationEnabled(true);
        //mapaGoogle.getUiSettings().setMyLocationButtonEnabled(false);

    }
}
