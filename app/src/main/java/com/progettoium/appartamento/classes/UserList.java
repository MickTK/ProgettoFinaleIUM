package com.progettoium.appartamento.classes;

import java.util.ArrayList;

public class UserList extends ArrayList<User> {
    private User current = null;

    // Costruttore
    public UserList(){
        super();
    }

    // Imposta l'utente corrente
    public void setCurrent(String username){
        for (User user : this){
            if(user.username.equals(username)){
                current = user;
                return;
            }
        }
        current = null;
    }

    // Restituisce l'utente corrente
    public User getCurrent(){
        return current;
    }

    // Controlla l'esistenza di un dato utente
    public boolean exists(String username){
        for (User user : this){
            if(user.username.equals(username)){
                return true;
            }
        }
        return false;
    }

    // Controlla l'esistenza di un dato utente, avente data password
    public boolean exists(String username, String password){
        for (User user : this){
            if(user.username.equals(username) && user.password.equals(password)){
                return true;
            }
        }
        return false;
    }

    // Restituisce l'oggetto utente avente lo username dato
    public User get(String username){
        for (User user : this){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }
}
