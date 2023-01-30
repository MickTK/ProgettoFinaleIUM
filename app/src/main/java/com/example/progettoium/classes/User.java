package com.example.progettoium.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    /* Attributes */
    public String username;          // Identificativo dell'utente
    public String name;              // Nome dell'utente
    public String surname;           // Cognome dell'utente
    public String number;            // Numero di cellulare
    public String email;             // Email dell'utente
    public String password;          // Password d'accesso
    public String profilePicture;    // Immagine del profilo
    public List<Integer> insertions; // Inserzioni dell'utente

    /* Constructor */
    public User(String username, String name, String surname, String password){
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;

        number = null;
        email = null;
        profilePicture = null;
        insertions = new ArrayList<>();
    }

    /* Methods */
    public Uri getProfilePicture() {
        return profilePicture == null ? null : Uri.parse(profilePicture);
    }
    public void setProfilePicture(Uri immagine) {
        this.profilePicture = immagine == null ? null : immagine.toString();
    }
    public boolean equals(User user) {
        return username.equals(user.username);
    }
}
