package com.progettoium.appartamento;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NewInsertionActivity extends AppCompatActivity {
    User currentUser = Shared.userList.getCurrent();
    Insertion currentInsertion = Shared.currentInsertion;
    Insertion tempInsertion = new Insertion();

    boolean newInsertionMode;
    boolean deletePicturesMode = false; // true se l'utente può cancellare le foto, altrimenti false

    EditText city, address, description;
    LinearLayout pictures;
    Button addPicture, createInsertion;
    TextView pictureText, cancelOperation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_insertion);

        // L'activity viene usata per creare una nuova inserzione
        if (currentInsertion == null){
            newInsertionMode = true;
            currentInsertion = new Insertion();
        }
        // L'activity viene usata per modificare un'inserzione esistente
        else{
            newInsertionMode = false;
        }

        // Attributi
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        description = findViewById(R.id.descrizione);
        pictures = findViewById(R.id.pictures);
        pictureText = findViewById(R.id.pictureText);
        addPicture = findViewById(R.id.fpick);
        createInsertion = findViewById(R.id.adsButton);
        cancelOperation = findViewById(R.id.back);

        // Eventi
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                city.setError(null);
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address.setError(null);
            }
        });

        // Se si sta modificando un'inserzione
        ImageView pic;
        if(!newInsertionMode){
            city.setText(currentInsertion.city);
            address.setText(currentInsertion.address);
            description.setText(currentInsertion.description);
            // Aggiunge le foto dell'annuncio
            for(int i = 0; i < currentInsertion.pictures.size(); i++){
                pic = newPictureView(currentInsertion.getPicture(i), "pic_" + i);
                pictures.addView(pic);
            }
            createInsertion.setText("Pubblica modifiche");
        }
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PICK_IMAGE = 100;
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(gallery, PICK_IMAGE);
                pictureText.setError(null);
            }
        });
        createInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInformations()){
                    // Controlla che la posizione sia corretta
                    tempInsertion.city = city.getText().toString();
                    tempInsertion.address = address.getText().toString();
                    if (!tempInsertion.calculatePosition(getApplicationContext())){
                        address.setError("Posizione non trovata.");
                        return;
                    }
                    // Salva le informazioni
                    currentInsertion.owner = currentUser.username;
                    currentInsertion.city = city.getText().toString();
                    currentInsertion.address = address.getText().toString();
                    currentInsertion.pictures = tempInsertion.pictures;
                    currentInsertion.description = description.getText().toString();
                    currentInsertion.geoPoint = tempInsertion.geoPoint;
                    // Aggiunge l'annuncio se nuovo
                    if (newInsertionMode)
                        currentUser.insertions.add(currentInsertion);
                    Shared.saveApplicationData();
                    onBackPressed();
                }
            }
        });
        cancelOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // Importa le immagini selezionate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int PICK_IMAGE = 100;
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            ClipData clipData = data.getClipData();
            Uri uri;
            Bitmap bitmap;
            for (int i = 0; i < clipData.getItemCount(); i++){
                uri = clipData.getItemAt(i).getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    // Salva la nuova foto nell'inserzione
                    tempInsertion.addPicture(bitmap);
                    // Crea e aggiunge l'ImageView alla lista delle foto
                    pictures.addView(newPictureView(bitmap, "pic_" + i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Torna all'activity precedente
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Shared.currentInsertion = null;
    }

    // Crea una ImageView da mostrare con una foto
    private ImageView newPictureView(Bitmap bitmap, String id){
        ImageView picture = new ImageView(getApplicationContext());
        picture.setImageBitmap(bitmap);
        picture.setTag(id);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deletePicturesMode){
                    deletePictureOnClick(v);
                    sortPictures();
                }
            }
        });
        return picture;
    }

    // Cancella la foto se cliccata in modalità di rimozione
    private void deletePictureOnClick(@NonNull View v){
        int index = Integer.parseInt(v.getTag().toString().replace("pic_",""));
        pictures.removeViewAt(index);
        tempInsertion.pictures.remove(index);
    }

    // Riordina le foto per farle coincidere con l'indice della lista
    // "pic_0", "pic_1", "pic_2", ...
    private void sortPictures(){
        for (int i = 0; i < pictures.getChildCount(); i++){
            pictures.getChildAt(i).setTag("pic_" + i);
        }
    }

    // Controlla che le informazioni siano state inserite correttamente
    private boolean checkInformations(){
        boolean status = true;
        if (city.getText().toString().equals("")){
            status = false;
            city.setError("Questo campo non può essere vuoto.");
        }
        if (address.getText().toString().equals("")){
            status = false;
            address.setError("Questo campo non può essere vuoto.");
        }
        if (pictures.getChildCount() < 1){
            status = false;
            pictureText.setError("Devi caricare almeno un'immagine.");
        }
        if (description.getText().toString().equals("")){
            status = false;
            description.setError("Questo campo non può essere vuoto.\nCerca di includere tutte le informazioni relative all'alloggio.");
        }
        return status;
    }
}
