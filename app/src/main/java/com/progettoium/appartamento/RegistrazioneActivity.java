package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

public class RegistrazioneActivity extends AppCompatActivity {

    EditText username, name, surname, number, email, password1, password2;
    Button selectPicture, signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        // Attributi
        username = findViewById(R.id.editUsername);
        name = findViewById(R.id.editName);
        surname = findViewById(R.id.editSurname);
        number = findViewById(R.id.editNumber);
        email = findViewById(R.id.editEmail);
        password1 = findViewById(R.id.editPassw);
        password2 = findViewById(R.id.editNPassw);
        selectPicture = findViewById(R.id.fpick);
        signIn = findViewById(R.id.signInButton);

        // Listener
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Controllo input

                // Crea e salva l'oggetto utente
                saveUser();
                Shared.saveApplicationData(); // Salva i dati dell'applicazione

                // Cambia activity
                goToLogin();
            }
        });
    }

    private void saveUser(){
        User user = new User();
        user.username = username.getText().toString();
        user.name = name.getText().toString();
        user.surname = surname.getText().toString();
        user.password = password1.getText().toString();
        Shared.userList.add(user); // Aggiunge l'utente alla lista
    }
    private void goToLogin(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}