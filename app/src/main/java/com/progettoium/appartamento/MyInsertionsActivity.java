package com.progettoium.appartamento;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyInsertionsActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insertions);

        ViewGroup container = findViewById(R.id.insertioList);
        View item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        //ImageView picture = item.findViewById(R.id.picture);
        //TextView description = item.findViewById(R.id.description);
        //picture.setBackgroundColor(R.color.black);
        //description.setText("Annuncio 1");
        container.addView(item);

        item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        //picture = item.findViewById(R.id.picture);
        //description = item.findViewById(R.id.description);
        //picture.setBackgroundColor(R.color.black);
        //description.setText("Annuncio 2");
        container.addView(item);
    }
}
