package com.progettoium.appartamento.shared;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;

import java.util.ArrayList;
import java.util.List;

public class Shared {
    private static SharedPreferences preferences;

    private static List<User> userList;           // Lista di tutti gli utenti registrati nell'applicazione
    private static List<Insertion> insertionList; // Lista di tutti gli annunci registrati nell'applicazione

    public static class UserData{
        public static User current = null; // Dati dell'utente corrente

        // Imposta l'utente corrente
        public static void setCurrent(String username, String password){
            for (User user : userList){
                if(user.username.equals(username) && user.password.equals(password)){
                    current = user;
                    return;
                }
            }
            current = null;
        }

        // Controlla l'esistenza di un dato utente
        public static boolean exists(String username){
            for (User user : userList){
                if(user.username.equals(username)){
                    return true;
                }
            }
            return false;
        }

        // Controlla la correttezza di una data password in relazione ad un dato utente
        public static boolean exists(String username, String password){
            for (User user : userList){
                if(user.username.equals(username) && user.password.equals(password)){
                    return true;
                }
            }
            return false;
        }
    }

    public static class InsertionData{
        public static int add(User user, String city, String address, String description){
            int id = insertionList.size();
            id = id > 0 ? insertionList.get(id-1).id + 1 : 0;
            Insertion insertion = new Insertion(id, user.username, city, address, description);
            insertionList.add(insertion);
            return id;
        }

        // Cerca e restituisce tutti gli annunci registrati nell'applicazione tramite il nome della città
        public static List<Insertion> searchInsertionByCity(String city){
            Insertion insertion;
            List<Insertion> insertions = new ArrayList<>();

            for(int i = 0; i < Shared.insertionList.size(); i++){
                insertion = Shared.insertionList.get(i);
                if(insertion.city.equals(city))
                    insertions.add(insertion);
            }

            return insertions;
        }

        // Cerca e restituisce tutti gli annunci registrati nell'applicazione tramite il nome della città
        // escludendo gli annunci di un dato utente dalla lista
        public static List<Insertion> searchInsertionByCityExcludingUser(String city, String username){
            Insertion insertion;
            List<Insertion> insertions = new ArrayList<>();

            for(int i = 0; i < Shared.insertionList.size(); i++){
                insertion = Shared.insertionList.get(i);
                if(insertion.city.equals(city) && !insertion.owner.equals(username))
                    insertions.add(insertion);
            }

            return insertions;
        }
    }

    // Salva la lista di utenti e i loro attributi
    public static boolean savaApplicationData(){
        preferences.edit().putString("users", new Gson().toJson(userList)).apply();
        preferences.edit().putString("insertions", new Gson().toJson(insertionList)).apply();

        return true;
    }

    // Carica la lista di utenti e i loro attributi
    public static boolean loadApplicationData(@NonNull Context context){
        preferences = context.getSharedPreferences("data", MODE_PRIVATE);

        userList = new Gson().fromJson(preferences.getString("users", null), List.class);
        if (userList == null) userList = new ArrayList<>();
        insertionList = new Gson().fromJson(preferences.getString("insertions", null), List.class);
        if (insertionList == null) insertionList = new ArrayList<>();

        return true;
    }
}
