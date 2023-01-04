package com.example.progettoium.global;

import com.example.progettoium.classes.Insertion;
import com.example.progettoium.classes.User;

import java.util.ArrayList;
import java.util.List;

public class Insertions {

    // Cerca e restituisce tutti gli annunci registrati nell'applicazione tramite il nome della città
    public static List<Insertion> byCity(String city){
        List<Insertion> insertions = new ArrayList<>();
        User user = null;

        for(int i = 0; i < Users.list.size(); i++){
            user = Users.list.get(i);
            for(int j = 0; j < user.insertions.size(); j++){
                insertions.add(user.insertions.get(j));
            }
        }

        return insertions;
    }

    // Cerca e restituisce tutti gli annunci registrati nell'applicazione tramite il nome della città
    // escludendo gli annunci di un dato utente dalla lista
    public static List<Insertion> byCityExcludingUser(String city, User user){
        List<Insertion> insertions = new ArrayList<>();
        User user1 = null;

        for(int i = 0; i < Users.list.size(); i++){
            user1 = Users.list.get(i);
            if(!user.equals(user1))
                insertions.addAll(user1.insertions);
        }

        return insertions;
    }
}
