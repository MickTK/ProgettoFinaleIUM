package com.progettoium.appartamento.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;

public class Insertion implements Serializable {

    /* Attributes */
    public int id;                // Identificativo dell'annuncio
    public String owner;          // Locatore
    public String city;           // Città dell'alloggio
    public String address;        // Indirizzo dell'alloggio
    public String description;    // Descrizione dell'annuncio
    public List<String> pictures; // Lista degli indirizzi delle immagini
    public boolean status;        // Stato dell'annuncio: true -> online, false -> archiviato

    /* Constructor */
    public Insertion(int id, String owner, String city, String address, String description) {
        this.id = id;
        this.owner = owner;
        this.city = city;
        this.address = address;
        this.description = description;
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
