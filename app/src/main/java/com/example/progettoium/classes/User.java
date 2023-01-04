package com.example.progettoium.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    /* Attributes */
    public String username;            // Identificativo dell'utente
    public String nome;                // Nome dell'utente
    public String cognome;             // Cognome dell'utente
    public String number;              // Numero di cellulare
    public String email;               // Email dell'utente
    public String password;            // Password d'accesso
    public String profilePicture;      // Immagine del profilo
    public List<Insertion> insertions; // Inserzioni dell'utente

    /* Constructor */
    public User(){
        username = null;
        nome = null;
        cognome = null;
        number = null;
        email = null;
        password = null;
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
