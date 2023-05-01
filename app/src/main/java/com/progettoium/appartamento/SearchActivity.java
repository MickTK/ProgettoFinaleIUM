package com.progettoium.appartamento;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.shared.Shared;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;

    EditText searchBar;
    Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Richiesta permessi
        requestPermissionsIfNecessary(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});

        // Configurazioni di osmdroid
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        // Attributi
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        // Controller
        IMapController mapController = map.getController();
        mapController.setZoom(10);

        // Imposta la posizione iniziale della mappa
        String startingPosition = "72 Via Ospedale, Cagliari, ITA"; // Palazzo delle Scienze
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            List<Address> addresses = geocoder.getFromLocationName(startingPosition, 1);
            if (addresses.size() > 0) {
                double lat = addresses.get(0).getLatitude();
                double lon = addresses.get(0).getLongitude();
                GeoPoint startPoint = new GeoPoint(lat, lon);
                mapController.setCenter(startPoint);
            }
        }
        catch (Exception exception){}

        // Inizializza i Marker della mappa
        for (Insertion insertion : Shared.getInsertionsWithout(Shared.userList.getCurrent())){
            if (insertion.status){
                Marker marker = insertion.toMarker(map); // Crea il marker
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                // TODO: startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
                marker.setTitle(insertion.address);
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        Shared.currentInsertion = insertion;
                        // TODO: Cambia activity
                        return false;
                    }
                });
                map.getOverlays().add(marker); // Aggiunge il marker alla mappa
            }
        }

        // Eventi
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    List<Address> addresses = geocoder.getFromLocationName(searchBar.getText().toString(), 1);
                    if (addresses.size() > 0) {
                        double lat = addresses.get(0).getLatitude();
                        double lon = addresses.get(0).getLongitude();
                        GeoPoint geoPoint = new GeoPoint(lat, lon);
                        mapController.setCenter(geoPoint);
                    }
                }
                catch (Exception exception){}
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}
