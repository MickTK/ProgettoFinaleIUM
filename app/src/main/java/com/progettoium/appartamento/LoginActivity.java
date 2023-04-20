package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.progettoium.appartamento.R;
import com.progettoium.appartamento.shared.Shared;

public class LoginActivity extends AppCompatActivity {

    EditText username, passw;
    Button loginButton;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Shared.loadApplicationData(getApplicationContext());

        // Attributi
        username = findViewById(R.id.username);
        passw = findViewById(R.id.passw);
        loginButton = findViewById(R.id.loginButton);
        signIn = findViewById(R.id.signIn);

        // Listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Shared.UserData.exists(username.getText().toString())){
                    // Lo username inserito non esiste
                }
                else if(!Shared.UserData.exists(username.getText().toString(),passw.getText().toString())){
                    // Lo username inserito esiste ma la password è errata
                }
                else{
                    // Lo username esiste e la password è corretta
                    Shared.UserData.setCurrent(username.getText().toString(),passw.getText().toString());
                    // L'utente sarà accessibile con "Shared.UserData.current"
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn(){
        startActivity(new Intent(this, RegistrazioneActivity.class));
    }
}