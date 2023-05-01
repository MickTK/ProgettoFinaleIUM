package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.progettoium.appartamento.R;
import com.progettoium.appartamento.shared.Shared;

public class LoginActivity extends AppCompatActivity {

    static boolean startUp = true; // Serve per caricare i dati dell'applicazione solo all'avvio

    EditText username, passw;
    Button loginButton;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Dati dell'applicazione
        Shared.setSharedPreferences(getApplicationContext());
        if (startUp){
            Shared.loadApplicationData(); // Carica i dati solo all'avvio
            if (Shared.userList.size() == 0)
                Shared.sampleApplicationData(); // Carica dei dati per i test
        }
        // Shared.clearApplicationData();
        // Shared.saveApplicationData();
        startUp = false;

        // Attributi
        username = findViewById(R.id.username);
        passw = findViewById(R.id.passw);
        loginButton = findViewById(R.id.loginButton);
        signIn = findViewById(R.id.signIn);

        // Eventi
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                username.setError(null);
            }
        });
        passw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                passw.setError(null);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void logIn(){
        // Il nome utente e la password sono corretti
        if (Shared.userList.exists(username.getText().toString(), passw.getText().toString())){
            Shared.userList.setCurrent(username.getText().toString());
            startActivity(new Intent(this, HomeActivity.class));
        }
        // Il nome utente è corretto ma non la password
        else if (Shared.userList.exists(username.getText().toString())){
            passw.setText("");
            passw.setError("La password inserita non è corretta.");
        }
        // Il nome utente non è corretto
        else {
            username.setError("Il nome utente inserito non è corretto.");
            passw.setText("");
        }
    }
    private void signIn(){
        startActivity(new Intent(this, RegistrazioneActivity.class));
    }
}