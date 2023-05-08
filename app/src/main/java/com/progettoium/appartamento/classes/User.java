package com.progettoium.appartamento.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.progettoium.appartamento.shared.Shared;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User implements Serializable {

    /** Attributi */
    public String username;            // Identificativo dell'utente
    public String name;                // Nome dell'utente
    public String surname;             // Cognome dell'utente
    public String number;              // Numero di cellulare
    public String email;               // Email dell'utente
    public String password;            // Password d'accesso
    public String profilePicture;      // Immagine del profilo
    public List<Insertion> insertions; // Inserzioni dell'utente
    public List<List<String>> favourites;

    /** Costruttore */
    public User(){
        username = null;
        name = null;
        surname = null;
        number = null;
        email = null;
        password = null;
        insertions = new ArrayList<>();
        favourites = new ArrayList<>();
    }

    /** Metodi */
    // Ottiene l'immagine del profilo sottoforma di Uri
    public Bitmap getProfilePicture() {
        try {
            byte [] encodeByte= Base64.decode(profilePicture,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    // Imposta l'immagine del profilo tramite Uri
    public void setProfilePicture(Bitmap picture) {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        profilePicture = temp;
    }


    /* Preferiti */
    // Aggiunge un'inserzione ai preferiti
    public void addFavourite(Insertion insertion){
        List<String> i = Arrays.asList(insertion.owner,insertion.city,insertion.address);
        favourites.add(i);
    }
    // Ottiene tutte le inserzioni preferite
    public List<Insertion> getFavourites(){
        List<Insertion> insertions = new ArrayList<>();
        List<String> f;
        Insertion insertion;

        // Recupera tutte le inserzioni preferite
        for (int i = favourites.size()-1; i >= 0; i--) {
            f = favourites.get(i);
            insertion = Shared.getInsertion(f.get(0),f.get(1),f.get(2));
            // Se l'inserzione esiste
            if (insertion != null){
                // Se l'inserzione è pubblica
                if (insertion.status)
                    insertions.add(insertion);
            }
            // Se l'inserzione non esiste
            else {
                favourites.remove(i);
            }
        }
        return insertions;
    }
    // Rimuove un'inserzione dai preferiti
    public void removeFavourite(Insertion insertion){
        List<String> f;
        for (int i = 0; i < favourites.size()-1; i++){
            f = favourites.get(i);
            if (f.get(0).equals(insertion.owner) &&
                f.get(1).equals(insertion.city) &&
                f.get(2).equals(insertion.address)){
                favourites.remove(i);
                break;
            }
        }
    }
    // Controlla se l'inserzione è tra i preferiti
    public boolean isFavourite(Insertion insertion){
        List<String> f;
        for (int i = 0; i < favourites.size()-1; i++){
            f = favourites.get(i);
            if (f.get(0).equals(insertion.owner) &&
                    f.get(1).equals(insertion.city) &&
                    f.get(2).equals(insertion.address)){
                return true;
            }
        }
        return false;
    }

    public boolean equals(User user) {
        return username.equals(user.username);
    }
}
