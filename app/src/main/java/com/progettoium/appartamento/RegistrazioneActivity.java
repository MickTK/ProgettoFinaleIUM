package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

import java.io.IOException;

public class RegistrazioneActivity extends AppCompatActivity {

    EditText username, name, surname, number, email, password1, password2;
    Button selectPicture, removePicture, signIn;
    TextView pictureText;
    ImageView pictureImage;
    Bitmap pictureBmp;

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
        removePicture = findViewById(R.id.fpickRemove);
        pictureText = findViewById(R.id.pictureText);
        pictureBmp = null;
        pictureImage = findViewById(R.id.pictureImage);
        signIn = findViewById(R.id.signInButton);

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
                if (Shared.userList.exists(username.getText().toString()))
                    username.setError("Nome utente non disponibile.");
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name.setError(null);
            }
        });
        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                surname.setError(null);
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                number.setError(null);
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email.setError(null);
            }
        });
        password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password1.setError(null);
            }
        });
        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password2.setError(null);
            }
        });
        selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PICK_IMAGE = 100;
                pictureText.setError(null);
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        removePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureBmp = null;
                pictureImage.setVisibility(View.GONE);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInformations()){         // Controllo input
                    saveUser();                   // Crea e salva l'oggetto utente
                    Shared.saveApplicationData(); // Salva i dati dell'applicazione
                    goToLogin();                  // Cambia activity
                }
            }
        });
    }

    // Salva l'immagine selezionata
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int PICK_IMAGE = 100;
        Uri uri = null;
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            try {
                uri = data.getData();
                pictureBmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                pictureImage.setImageBitmap(pictureBmp);
                pictureImage.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (pictureBmp == null)
            pictureText.setError("Questo campo non può essere vuoto.");
    }
    // Controlla se tutti i dati inseriti nel form sono corretti
    // @return true se tutti i dati sono corretti, altrimenti false
    private boolean checkInformations(){
        boolean status = true;

        // Controlla il nome utente
        if (Shared.userList.exists(username.getText().toString())){
            status = false;
            username.setError("Nome utente non disponibile.");
        }
        // Controlla il nome
        if (name.getText().toString().equals("")){
            status = false;
            name.setError("Questo campo non può essere vuoto.");
        }
        // Controlla il cognome
        if (surname.getText().toString().equals("")){
            status = false;
            surname.setError("Questo campo non può essere vuoto.");
        }
        // Controlla il numero di telefono
        if (number.getText().toString().equals("")){
            status = false;
            number.setError("Questo campo non può essere vuoto.");
        }
        // Controlla la email
        if (email.getText().toString().equals("")){
            status = false;
            email.setError("Questo campo non può essere vuoto.");
        }
        // Controlla la password
        String pass = password1.getText().toString();
        String specialCharacters = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/";
        if (pass.equals("")){
            status = false;
            password1.setError("Questo campo non può essere vuoto.");
        }
        // Lunghezza (minimo 8 caratteri)
        else if (pass.length() < 8){
            status = false;
            password1.setError("La password deve essere lunga almeno 8 caratteri.");
        }
        // Lettera minuscola
        else if (!pass.matches(".*[a-z].*")){
            status = false;
            password1.setError("La password deve contenere almeno una lettera minuscola.");
        }
        // Lettera maiuscola
        else if (!pass.matches(".*[A-Z].*")){
            status = false;
            password1.setError("La password deve contenere almeno una lettera maiuscola.");
        }
        // Numero
        else if (!pass.matches(".*[0-9].*")){
            status = false;
            password1.setError("La password deve contenere almeno un numero.");
        }
        // Carattere speciale
        else {
            boolean hasSpecialCharacter = false;
            for (int i = 0; i < specialCharacters.length(); i++) {
                if (pass.indexOf(specialCharacters.charAt(i)) >= 0) {
                    hasSpecialCharacter = true;
                    break;
                }
            }
            if (!hasSpecialCharacter) {
                status = false;
                password1.setError("La password deve contenere almeno un carattere speciale.");
            }
        }
        // Controlla la conferma della password
        if (!password2.getText().toString().equals(pass)){
            status = false;
            password2.setText("");
            password2.setError("La password non coindice.");
        }
        // Controlla l'immagine del profilo
        if (pictureBmp == null){
            status = false;
            pictureText.setError("Questo campo non può essere vuoto.");
        }

        return status;
    }
    // Crea e salva un nuovo utente nella lista degli utenti
    private void saveUser(){
        User user = new User();
        user.username = username.getText().toString();
        user.name = name.getText().toString();
        user.surname = surname.getText().toString();
        user.number = number.getText().toString();
        user.email = email.getText().toString();
        user.password = password1.getText().toString();
        user.setProfilePicture(pictureBmp);
        Shared.userList.add(user);
    }
    // Cambia activity
    private void goToLogin(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
