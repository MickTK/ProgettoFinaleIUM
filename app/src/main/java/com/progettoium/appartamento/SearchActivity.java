package com.progettoium.appartamento;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.shared.Shared;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

public class SearchActivity extends AppCompatActivity{
    private MapView map = null;
    private static IGeoPoint lastLocation = null;
    private static Double zoom = null;
    private Marker currentMarker = null;

    EditText searchBar;
    Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurazioni di osmdroid
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_search);

        // Attributi
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        // Controller
        IMapController mapController = map.getController();

        // Imposta la posizione iniziale della mappa
        if (lastLocation == null) {
            String startingPosition = "72 Via Ospedale, Cagliari, ITA"; // Palazzo delle Scienze
            try {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addresses = geocoder.getFromLocationName(startingPosition, 1);
                if (addresses.size() > 0) {
                    double lat = addresses.get(0).getLatitude();
                    double lon = addresses.get(0).getLongitude();
                    GeoPoint startPoint = new GeoPoint(lat, lon);
                    mapController.setCenter(startPoint);
                    mapController.setZoom(15.);
                }
            }
            catch (Exception exception) {}
        }
        else{
            mapController.setCenter(lastLocation);
            mapController.setZoom(zoom);
        }

        // Inizializza i Marker della mappa
        for (Insertion insertion : Shared.getInsertionsWithout(Shared.userList.getCurrent())){
            if (insertion.status){
                Marker marker = insertion.toMarker(map); // Crea il marker
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setIcon(getResources().getDrawable(R.drawable.segnapposto));
                marker.setTitle(insertion.address);
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        Shared.currentInsertion = insertion;
                        goToInsertionInfos();
                        return false;
                    }
                });
                map.getOverlays().add(marker); // Aggiunge il marker alla mappa
            }
        }

        // Eventi
        searchBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    searchBar.requestFocus();
                    return true;
                }
                return false;
            }
        });
        searchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    try {
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        List<Address> addresses = geocoder.getFromLocationName(searchBar.getText().toString(), 1);
                        if (addresses.size() > 0) {
                            double lat = addresses.get(0).getLatitude();
                            double lon = addresses.get(0).getLongitude();
                            GeoPoint geoPoint = new GeoPoint(lat, lon);
                            mapController.setCenter(geoPoint);
                            if(currentMarker == null){
                                currentMarker = new Marker(map);
                                currentMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                currentMarker.setIcon(getResources().getDrawable(R.drawable.puntina));
                                currentMarker.setPosition(geoPoint);
                                currentMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                                        return false;
                                    }
                                });
                                map.getOverlays().add(currentMarker);
                            }
                            else{
                                currentMarker.setPosition(geoPoint);
                            }
                        }
                    }
                    catch (Exception exception){}
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        lastLocation = null;
        zoom = null;
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        Shared.currentInsertion = null;
        Shared.saveApplicationData();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    private void goToInsertionInfos(){
        // Salva l'ultima posizione della mappa
        lastLocation = map.getMapCenter();
        zoom = map.getZoomLevelDouble();
        // Cambia activity
        startActivity(new Intent(this, InsertionActivity.class));
    }
}
