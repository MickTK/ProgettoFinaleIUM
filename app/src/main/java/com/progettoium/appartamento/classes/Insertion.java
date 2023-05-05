package com.progettoium.appartamento.classes;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Insertion implements Serializable {

    /* Attributes */
    public String owner;          // Locatore
    public String city;           // Città dell'alloggio
    public String address;        // Indirizzo dell'alloggio
    public GeoPoint geoPoint;     // Posizione nella mappa
    public String description;    // Descrizione dell'annuncio
    public List<String> pictures; // Lista degli indirizzi delle immagini
    public boolean status;        // Stato dell'annuncio: true -> online, false -> archiviato

    /* Constructor */
    public Insertion(){
        owner = null;
        city = null;
        address = null;
        geoPoint = null;
        description = null;
        pictures = new ArrayList<>();
        status = true;
    }
    public Insertion(String owner, String city, String address, String description) {
        this.owner = owner;
        this.city = city;
        this.address = address;
        geoPoint = null;
        this.description = description;
        status = true;
    }

    /* Methods */
    public Uri getPicture(int index) {
        return pictures == null || pictures.size() < index ? null : Uri.parse(pictures.get(index));
    }
    public void addPicture(Uri picture) {
        if (picture != null) this.pictures.add(picture.toString());
    }

    // Calcola e imposta le coordinate geografiche dell'immobile
    // @return true se la posizione è corretta, altrimenti false
    public boolean calculatePosition(Context context){
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> addresses = geocoder.getFromLocationName(address+","+city, 1);
            if (addresses.size() > 0) {
                double lat = addresses.get(0).getLatitude();
                double lon = addresses.get(0).getLongitude();
                geoPoint = new GeoPoint(lat, lon);
                return true;
            }
        }
        catch (Exception exception){}
        return false;
    }

    public GeoPoint getPosition(){
        return geoPoint;
    }

    // Crea un oggetto Marker avente le coordinate dell'immobile
    // @return marker se le coordinate sono state inizializzate, altrimenti null
    public Marker toMarker(MapView map){
        if (geoPoint == null) return null;
        Marker marker = new Marker(map);
        marker.setPosition(geoPoint);
        return marker;
    }
    public boolean equals(@NonNull Insertion insertion){
        return owner.equals(insertion.owner) &&
                city.equals(insertion.city) &&
                address.equals(insertion.address);
    }
    public boolean equals(String owner, String city, String address){
        return this.owner.equals(owner) && this.city.equals(city) && this.address.equals(address);
    }
}
