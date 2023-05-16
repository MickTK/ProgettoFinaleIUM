package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InsertionActivity extends AppCompatActivity {
    TextView city, address, description, name, number, email;
    LinearLayout pictures;
    Button favourite, modifyButton;

    User currentUser = Shared.userList.getCurrent();
    Insertion currentInsertion = Shared.currentInsertion;
    User insertionOwner = Shared.userList.get(currentInsertion.owner);;

    boolean isFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertion);

        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        description = findViewById(R.id.description);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        pictures = findViewById(R.id.pictures);
        favourite = findViewById(R.id.favorites);
        modifyButton = findViewById(R.id.modifyButton);
        if(!currentInsertion.owner.equals(currentUser.username))
            modifyButton.setVisibility(View.GONE);

        city.setText(currentInsertion.city);
        address.setText(currentInsertion.address);
        description.setText(currentInsertion.description);
        name.setText(name.getText() + insertionOwner.name + " " + insertionOwner.surname);
        number.setText(number.getText() + insertionOwner.number);
        email.setText(email.getText() + insertionOwner.email);

        /** Pulsante dei preferiti */
        // Nasconde il pulsante se è il proprietario a visualizzare l'annuncio
        if (insertionOwner.username.equals(currentUser.username)){
            favourite.setVisibility(View.GONE);
        }
        // L'annuncio è tra i preferiti
        if (currentUser.isFavourite(currentInsertion)){
            isFavourite = true;
            favourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.stella_piena, 0, 0);
        }
        // L'annuncio non è tra i preferiti
        else{
            isFavourite = false;
            favourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.stella_vuota, 0, 0);
        }
        // Aggiunge e rimuove l'annuncio dai preferiti
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se l'annuncio è tra i preferiti
                if(isFavourite){
                    isFavourite = false;
                    currentUser.removeFavourite(currentInsertion);
                    favourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.stella_vuota, 0, 0);
                }
                // Se l'annuncio non è tra i preferiti
                else{
                    isFavourite = true;
                    currentUser.addFavourite(currentInsertion);
                    favourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.stella_piena, 0, 0);
                }
            }
        });
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsertionActivity.this, NewInsertionActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /** Album fotografico */
        ImageView picture;
        pictures.removeAllViews();
        for(int i = 0; i < currentInsertion.pictures.size(); i++){
            picture = new ImageView(getApplicationContext());
            picture.setImageBitmap(currentInsertion.getPicture(i));
            picture.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            );
            picture.setAdjustViewBounds(true);
            picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pictures.addView(picture);
        }
    }
}
