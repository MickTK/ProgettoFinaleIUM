package com.progettoium.appartamento.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    /* Attributes */
    public String username;            // Identificativo dell'utente
    public String name;                // Nome dell'utente
    public String surname;             // Cognome dell'utente
    public String number;              // Numero di cellulare
    public String email;               // Email dell'utente
    public String password;            // Password d'accesso
    public String profilePicture;      // Immagine del profilo
    public List<Insertion> insertions; // Inserzioni dell'utente

    /* Constructor */
    public User(){
        username = null;
        name = null;
        surname = null;
        number = null;
        email = null;
        password = null;
        insertions = null;
    }

    /* Methods */
    public Uri getProfilePicture() {
        return profilePicture == null ? null : Uri.parse(profilePicture);
    }
    public void setProfilePicture(Uri picture) {
        this.profilePicture = picture == null ? null : picture.toString();
    }
    public boolean equals(User user) {
        return username.equals(user.username);
    }
}
