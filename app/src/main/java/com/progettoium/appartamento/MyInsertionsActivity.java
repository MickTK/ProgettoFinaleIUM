package com.progettoium.appartamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.shared.Shared;

public class MyInsertionsActivity extends AppCompatActivity{

    View preview;
    LinearLayout insertionLIst;
    Button createNewInsertion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insertions);

        insertionLIst = findViewById(R.id.insertioList);
        createNewInsertion = findViewById(R.id.insertads);

        createNewInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.currentInsertion = null;
                startActivity(new Intent(MyInsertionsActivity.this, NewInsertionActivity.class));
            }
        });

        preview = newPreview();
        //ImageView picture = preview.findViewById(R.id.picture);
        //TextView description = preview.findViewById(R.id.description);
        //picture.setBackgroundColor(R.color.black);
        //description.setText("Annuncio 1");
        insertionLIst.addView(preview);

        preview = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        insertionLIst.addView(preview);
        preview = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        insertionLIst.addView(preview);
        preview = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        insertionLIst.addView(preview);
        preview = getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
        insertionLIst.addView(preview);
    }

    private View newPreview(){
        return getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
    }
}
