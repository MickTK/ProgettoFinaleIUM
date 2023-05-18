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

public class EditAccountActivity extends AppCompatActivity {

    EditText newUsername, newName, newSurname, newNumber, newEmail, newPassw, newNPassw, passw;
    Button fpick, fpickRemove, changeButton;
    TextView newpictureText, cancel;
    ImageView newpictureImage;
    Bitmap pictureBmp;
    User user = Shared.userList.getCurrent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        newUsername = findViewById(R.id.newUsername);
        newUsername.setText(user.username);
        newName = findViewById(R.id.newName);
        newName.setText(user.name);
        newSurname = findViewById(R.id.newSurname);
        newSurname.setText(user.surname);
        newNumber = findViewById(R.id.newNumber);
        newNumber.setText(user.number);
        newEmail = findViewById(R.id.newEmail);
        newEmail.setText(user.email);
        newPassw = findViewById(R.id.newPassw);
        newNPassw = findViewById(R.id.newNPassw);
        fpick = findViewById(R.id.fpick);
        fpickRemove = findViewById(R.id.fpickRemove);
        newpictureText = findViewById(R.id.newpictureText);
        newpictureImage = findViewById(R.id.newpictureImage);
        newpictureImage.setImageBitmap(user.getProfilePicture());
        newpictureImage.setAdjustViewBounds(true);
        pictureBmp = Shared.userList.getCurrent().getProfilePicture();
        passw = findViewById(R.id.Passw);
        cancel = findViewById(R.id.back);

        changeButton = findViewById(R.id.changeButton);

        // Eventi
        newUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newUsername.setError(null);
                if(!newUsername.getText().toString().matches(user.username)){
                    if (Shared.userList.exists(newUsername.getText().toString())){
                        newUsername.setError("Nome utente non disponibile.");
                    }
                }
            }
        });
        newName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newName.setError(null);
            }
        });
        newSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newSurname.setError(null);
            }
        });
        newNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newNumber.setError(null);
            }
        });
        newEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newEmail.setError(null);
            }
        });
        newPassw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newPassw.setError(null);
            }
        });
        newNPassw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newNPassw.setError(null);
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
            public void afterTextChanged(Editable editable) {passw.setError(null);}
        });
        fpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PICK_IMAGE = 100;
                newpictureText.setError(null);
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        fpickRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureBmp = null;
                newpictureImage.setVisibility(View.GONE);
            }
        });
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInformations()){         // Controllo input
                    saveUser();                   // Crea e salva l'oggetto utente
                    Shared.saveApplicationData(); // Salva i dati dell'applicazione
                    goToHome();                  // Cambia activity
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onBackPressed();}
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
                newpictureImage.setImageBitmap(pictureBmp);
                newpictureImage.setVisibility(View.VISIBLE);
                newpictureImage.setAdjustViewBounds(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (pictureBmp == null)
            newpictureText.setError("Questo campo non può essere vuoto.");
    }
    // Controlla se tutti i dati inseriti nel form sono corretti
    // @return true se tutti i dati sono corretti, altrimenti false
    private boolean checkInformations() {
        boolean status = true;

        // Controlla il nome utente
        if (!newUsername.getText().toString().matches(user.username)) {
            if (Shared.userList.exists(newUsername.getText().toString())) {
                status = false;
                newUsername.setError("Nome utente non disponibile.");
            }
        }
        // Controlla il nome
        if (newName.getText().toString().equals("")) {
            status = false;
            newName.setError("Questo campo non può essere vuoto.");
        }
        // Controlla il cognome
        if (newSurname.getText().toString().equals("")) {
            status = false;
            newSurname.setError("Questo campo non può essere vuoto.");
        }
        // Controlla il numero di telefono
        if (newNumber.getText().toString().equals("")) {
            status = false;
            newNumber.setError("Questo campo non può essere vuoto.");
        }
        // Controlla la email
        if (newEmail.getText().toString().equals("")) {
            status = false;
            newEmail.setError("Questo campo non può essere vuoto.");
        }
        String mail = newEmail.getText().toString();
        String at = "@", dot = ".";
        boolean hasAt = false, hasDot = false;
        for (int i = 0; i < at.length(); i++) {
            if (mail.indexOf(at.charAt(i)) >= 0) {
                hasAt = true;
                break;
            }
        }
        if (!hasAt) {
            status = false;
            newEmail.setError("L'email deve contenere una @");
        }
        for (int i = 0; i < dot.length(); i++) {
            if (mail.indexOf(dot.charAt(i)) >= 0) {
                hasDot = true;
                break;
            }
        }
        if (!hasDot) {
            status = false;
            newEmail.setError("L'email deve contenere un .");
        }
        // Controlli nuova password
        String pass = newPassw.getText().toString();
        String specialCharacters = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/";
        if(!newPassw.getText().toString().equals("")){
            // Lunghezza (minimo 8 caratteri)
            if (pass.length() < 8) {
                status = false;
                newPassw.setError("La password deve essere lunga almeno 8 caratteri.");
            }
            // Lettera minuscola
            else if (!pass.matches(".*[a-z].*")) {
                status = false;
                newPassw.setError("La password deve contenere almeno una lettera minuscola.");
            }
            // Lettera maiuscola
            else if (!pass.matches(".*[A-Z].*")) {
                status = false;
                newPassw.setError("La password deve contenere almeno una lettera maiuscola.");
            }
            // Numero
            else if (!pass.matches(".*[0-9].*")) {
                status = false;
                newPassw.setError("La password deve contenere almeno un numero.");
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
                    newPassw.setError("La password deve contenere almeno un carattere speciale.");
                }
            }
        }
        // Controlla la conferma della password
        if (!newNPassw.getText().toString().equals(pass)){
            status = false;
            newNPassw.setText("");
            newNPassw.setError("La password non coincide.");
        }
        // Controlla l'immagine del profilo
        if (pictureBmp == null){
            status = false;
            newpictureText.setError("Questo campo non può essere vuoto.");
        }
        // Controllo vecchia password
        String oldPassw = user.password;
        if (!passw.getText().toString().equals(oldPassw)){
            status = false;
            passw.setText("");
            passw.setError("La password non coincide.");
        }
        return status;
    }
    // Crea e salva un nuovo utente nella lista degli utenti
    private void saveUser(){
        user.username = newUsername.getText().toString();
        user.name = newName.getText().toString();
        user.surname = newSurname.getText().toString();
        user.number = newNumber.getText().toString();
        user.email = newEmail.getText().toString();
        if(!newPassw.getText().toString().equals("")){
            user.password = newPassw.getText().toString();
        }
        user.setProfilePicture(pictureBmp);
    }
    // Cambia activity
    private void goToHome(){
        startActivity(new Intent(this, HomeActivity.class));
    }
}
