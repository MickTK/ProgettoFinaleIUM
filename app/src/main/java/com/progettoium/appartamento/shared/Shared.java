package com.progettoium.appartamento.shared;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.progettoium.appartamento.classes.InsertionList;
import com.progettoium.appartamento.classes.UserList;

public class Shared {
    private static SharedPreferences preferences;

    public static UserList userList;           // Lista di tutti gli utenti registrati nell'applicazione
    public static InsertionList insertionList; // Lista di tutti gli annunci registrati nell'applicazione

    // Inizializza le preferenze per la manipolazione dei dati di salvataggio
    public static void setSharedPreferences(@NonNull Context context){
        preferences = context.getSharedPreferences("data", MODE_PRIVATE);
    }

    // Salva i dati dell'applicazione
    public static void saveApplicationData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userList", new Gson().toJson(userList));
        editor.putString("insertionList", new Gson().toJson(insertionList));
        editor.apply();
        Log.println(Log.DEBUG,"debug", "Saved data.");
    }

    // Carica la lista di utenti e i loro attributi
    public static void loadApplicationData(){
        userList = new Gson().fromJson(preferences.getString("userList", null), new TypeToken<UserList>(){}.getType());
        if (userList == null) userList = new UserList();
        insertionList = new Gson().fromJson(preferences.getString("insertionList", null),  new TypeToken<InsertionList>(){}.getType());
        if (insertionList == null) insertionList = new InsertionList();
        Log.println(Log.DEBUG,"debug", "Loaded data.");
    }

    // Cancella tutti i dati salvati dell'applicazione
    public static void clearApplicationData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        userList = new UserList();
        insertionList = new InsertionList();
        Log.println(Log.DEBUG,"debug", "Cleared data.");
    }
}
