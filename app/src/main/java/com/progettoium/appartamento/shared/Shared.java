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
        for(User otherUser : userList)
            if (!user.username.equals(otherUser.username))
                insertions.addAll(otherUser.insertions);
        return insertions;
    }
    // Ottiene l'inserzione avente la chiave data
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
    public static void sampleApplicationData(@NonNull Context context){
        User user;
        Insertion insertion;

        user = new User();
        user.username = "mario";
        user.name = "Mario";
        user.surname = "Rossi";
        user.number = "0123456789";
        user.email = "mariorossi@gmail.com";
        user.password = "mario";
        userList.add(user);
        insertion = new Insertion();
        insertion.owner = user.username;
        insertion.city = "Cagliari";
        insertion.address = "58 Largo Carlo Felice";
        insertion.description = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, " +
                "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum " +
                "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium " +
                "optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis " +
                "obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam " +
                "nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit, " +
                "tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit, " +
                "quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos " +
                "sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam " +
                "recusandae alias error harum maxime adipisci amet laborum.";
        insertion.calculatePosition(context);
        user.insertions.add(insertion);

        user = new User();
        user.username = "luigi";
        user.name = "Luigi";
        user.surname = "Bianchi";
        user.number = "0123456789";
        user.email = "luigibianchi@gmail.com";
        user.password = "luigi";
        userList.add(user);
        insertion = new Insertion();
        insertion.owner = user.username;
        insertion.city = "Oristano";
        insertion.address = "10 Via Tempio";
        insertion.description = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, " +
                "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum " +
                "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium " +
                "optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis " +
                "obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam " +
                "nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit, " +
                "tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit, " +
                "quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos " +
                "sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam " +
                "recusandae alias error harum maxime adipisci amet laborum.";
        insertion.calculatePosition(context);
        user.insertions.add(insertion);
    }

    // Stampa su terminale
    public static void debugLog(String text){
        Log.println(Log.DEBUG,"debug", text);
    }
}
