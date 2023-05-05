package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InsertionActivity extends AppCompatActivity {
    TextView city, address, description, name, number, email;
    LinearLayout pictures;
    Button favourite;

    User owner = null;
    Insertion insertion = Shared.currentInsertion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertion);

        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        description = findViewById(R.id.descriptiom);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        pictures = findViewById(R.id.pictures);
        favourite = findViewById(R.id.favorites);

        owner = Shared.userList.get(insertion.owner); // Recupera le informazioni del locatore

        city.setText(insertion.city);
        address.setText(insertion.address);
        description.setText(insertion.description);
        name.setText(name.getText() + owner.name + " " + owner.surname);
        number.setText(number.getText() + owner.number);
        email.setText(email.getText() + owner.email);

        // Pulsante dei preferiti
        if (owner.username.equals(Shared.userList.getCurrent().username)){
            favourite.setVisibility(View.INVISIBLE);
        }

        // Album fotografico
        ImageView picture;
        for(int i = 0; i < insertion.pictures.size(); i++){
            picture = new ImageView(getApplicationContext());
            picture.setImageURI(insertion.getPicture(i));
            pictures.addView(picture);
        }
    }
}
