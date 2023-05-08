package com.progettoium.appartamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.shared.Shared;

public class MyInsertionsActivity extends AppCompatActivity{

    Button createNewInsertion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insertions);

        createNewInsertion = findViewById(R.id.insertads);

        createNewInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.currentInsertion = null;
                startActivity(new Intent(MyInsertionsActivity.this, NewInsertionActivity.class));
            }
        });

        ViewGroup container = findViewById(R.id.insertioList);
        View item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        //ImageView picture = item.findViewById(R.id.picture);
        //TextView description = item.findViewById(R.id.description);
        //picture.setBackgroundColor(R.color.black);
        //description.setText("Annuncio 1");
        container.addView(item);

        item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        container.addView(item);
        item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        container.addView(item);
        item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        container.addView(item);
        item = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        container.addView(item);
    }
}
