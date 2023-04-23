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
        if (startUp) Shared.loadApplicationData();
        // Shared.clearApplicationData();
        // Shared.saveApplicationData();
        startUp = false;

        // Attributi
        username = findViewById(R.id.username);
        passw = findViewById(R.id.passw);
        loginButton = findViewById(R.id.loginButton);
        signIn = findViewById(R.id.signIn);

        // Listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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