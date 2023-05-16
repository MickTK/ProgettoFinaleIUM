package com.progettoium.appartamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

public class FavouritesActivity extends AppCompatActivity {

    User currentUser = Shared.userList.getCurrent();
    LinearLayout insertionList;
    View preview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        insertionList = findViewById(R.id.insertioList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        insertionList.removeAllViews();
        ImageView picture;
        // Mostra le anteprima cliccabili degli annunci preferiti
        for (Insertion insertion : currentUser.getFavourites()){
            preview = newPreview();
            picture = preview.findViewById(R.id.picture);
            picture.setImageBitmap(insertion.getPicture(0));
            picture.setBackgroundColor(getColor(R.color.white));
            ((TextView)preview.findViewById(R.id.description)).setText(insertion.city + "\n" + insertion.address);
            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Shared.currentInsertion = insertion;
                    startActivity(new Intent(FavouritesActivity.this, InsertionActivity.class));
                }
            });
            insertionList.addView(preview);
        }
        Shared.saveApplicationData();
    }

    private View newPreview(){
        return getLayoutInflater().inflate(R.layout.favorites_preview, null);
    }
}
