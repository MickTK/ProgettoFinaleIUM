package com.example.progettoium.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Insertion implements Serializable {

    /* Attributes */
    public String city;           // Città dell'alloggio
    public String address;        // Indirizzo dell'alloggio
    public String description;    // Descrizione dell'annuncio
    public List<String> pictures; // Lista degli indirizzi delle immagini
    public boolean status;        // Stato dell'annuncio: true -> online, false -> archiviato

    /* Constructor */
    public Insertion(){
        city = null;
        address = null;
        description = null;
        pictures = new ArrayList<>();
        status = true;
    }
    public Insertion(String city, String address, String description, ArrayList<String> pictures) {
        this.city = city;
        this.address = address;
        this.description = description;
        this.pictures = pictures;
        status = true;
    }

    /* Methods */
    public Uri getPicture(int index) {
        return pictures == null || pictures.size() < index ? null : Uri.parse(pictures.get(index));
    }
    public void addPicture(Uri picture) {
        if (picture != null)
            this.pictures.add(picture.toString());
    }
}
