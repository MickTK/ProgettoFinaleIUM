package com.progettoium.appartamento.shared;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.classes.UserList;

import java.util.ArrayList;
import java.util.List;

public class Shared {
    private static SharedPreferences preferences;
    public static UserList userList;                 // Lista degli utenti registrati nell'applicazione
    public static Insertion currentInsertion = null; // Inserzione corrente

    /** Manipolazione delle inserzioni */
    // Ottiene tutte le inserzioni dell'applicazione
    public static List<Insertion> getInsertions(){
        List<Insertion> insertions = new ArrayList<>();
        for(User user : userList)
            insertions.addAll(user.insertions);
        return insertions;
    }
    // Ottiene tutte le inserzioni di un dato utente
    public static List<Insertion> getInsertionsFor(User user){
        List<Insertion> insertions = new ArrayList<>();
        for(User user_ : userList)
            if (user.username.equals(user_.username))
                insertions.addAll(user.insertions);
        return insertions;
    }
    // Ottiene tutte le inserzioni dell'applicazione, eccetto quelle del dato utente
    public static List<Insertion> getInsertionsWithout(User user){
        List<Insertion> insertions = new ArrayList<>();
        for(User user_ : userList)
            if (!user.username.equals(user_.username))
                insertions.addAll(user.insertions);
        return insertions;
    }

    public static Insertion getInsertion(String owner, String city, String address){
        for(User user : userList)
            for(Insertion insertion : user.insertions)
                if (insertion.equals(owner,city,address))
                    return insertion;
        return null;
    }

    /** Dati dell'applicazione */
    // Inizializza le preferenze per la manipolazione dei dati di salvataggio
    public static void setSharedPreferences(@NonNull Context context){
        preferences = context.getSharedPreferences("data", MODE_PRIVATE);
    }
    // Salva i dati dell'applicazione
    public static void saveApplicationData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userList", new Gson().toJson(userList));
        editor.apply();
        debugLog("Saved data.");
    }
    // Carica la lista di utenti e i loro attributi
    public static void loadApplicationData(){
        userList = new Gson().fromJson(preferences.getString("userList", null), new TypeToken<UserList>(){}.getType());
        if (userList == null) userList = new UserList();
        debugLog("Loaded data.");
    }
    // Cancella tutti i dati salvati dell'applicazione
    public static void clearApplicationData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        userList = new UserList();
        debugLog("Cleared data.");
    }

    // Stampa su terminale
    public static void debugLog(String text){
        Log.println(Log.DEBUG,"debug", text);
    }
}
