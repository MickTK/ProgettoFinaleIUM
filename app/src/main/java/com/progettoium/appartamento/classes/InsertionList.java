package com.progettoium.appartamento.classes;

import java.util.ArrayList;
import java.util.List;

public class InsertionList extends ArrayList<Insertion> {

    // Costruttore
    public InsertionList(){
        super();
    }

    // Aggiunge una nuova inserzione alla lista e l'assegna ad un dato utente
    public boolean add(Insertion insertion, User user){
        this.add(insertion);
        int id = this.size()-1;
        return user.insertions.add(id);
    }

    // Cancella (imposta a null) un'inserzione all'interno della lista
    public void delete(int id){
        this.set(id,null);
    }

    // Cerca e restituisce tutti gli annunci registrati nell'applicazione tramite il nome della città
    public List<Insertion> searchInsertionByCity(String city){
        Insertion insertion;
        List<Insertion> insertions = new ArrayList<>();
        for(int i = 0; i < this.size(); i++){
            insertion = this.get(i);
            if(insertion.city.equals(city))
                insertions.add(insertion);
        }
        return insertions;
    }

    // Cerca e restituisce tutti gli annunci registrati nell'applicazione tramite il nome della città
    // escludendo gli annunci di un dato utente dalla lista
    public List<Insertion> searchInsertionByCityExcludingUser(String city, String username){
        Insertion insertion;
        List<Insertion> insertions = new ArrayList<>();
        for(int i = 0; i < this.size(); i++){
            insertion = this.get(i);
            if(insertion.city.equals(city) && !insertion.owner.equals(username))
                insertions.add(insertion);
        }
        return insertions;
    }
}
