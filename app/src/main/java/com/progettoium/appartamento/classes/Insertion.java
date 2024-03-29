package com.progettoium.appartamento.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Base64;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.ByteArrayOutputStream;
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

    /** Methods */
    public Bitmap getPicture(int index){
        try {
            byte [] encodeByte= Base64.decode(pictures.get(index),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public void addPicture(Bitmap bitmap) {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        pictures.add(temp);
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
