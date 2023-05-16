package com.progettoium.appartamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.classes.Insertion;
import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.shared.Shared;

public class MyInsertionsActivity extends AppCompatActivity{

    User currentUser = Shared.userList.getCurrent();

    View preview;
    LinearLayout insertionList;
    Button createNewInsertion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insertions);

        insertionList = findViewById(R.id.insertioList);
        createNewInsertion = findViewById(R.id.insertads);

        createNewInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.currentInsertion = null;
                startActivity(new Intent(MyInsertionsActivity.this, NewInsertionActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Insertion insertion;
        ImageView picture;
        TextView description;
        Button deleteButton, modifyButton, archiveButton;

        Shared.currentInsertion = null;
        insertionList.removeAllViews();

        // Mostra le anteprime degli annunci e i relativi pulsanti
        for (int i = 0; i < currentUser.insertions.size(); i++){
            insertion = currentUser.insertions.get(i);
            // Crea la nuova istanza di un'anteprima
            preview = newPreview();
            preview.setTag("preview_" + i);
            // Recupera gli elementi dall'anteprima
            picture = preview.findViewById(R.id.picture);
            description = preview.findViewById(R.id.description);
            deleteButton = preview.findViewById(R.id.deleteButton);
            archiveButton = preview.findViewById(R.id.archiviaButton);
            // Imposta gli eventi dei pulsanti
            picture.setImageBitmap(insertion.getPicture(0));
            picture.setBackgroundColor(getColor(R.color.white));
            description.setText(insertion.city + "\n" + insertion.address);
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToInsertionOnClick(v);
                }
            });
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToInsertionOnClick(v);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = ((Button)v).getText().toString();
                    String delete = "Conferma eliminazione";
                    // Il pulsante viene premuto per la prima volta
                    if (!text.equals(delete)){
                        ((Button)v).setText(delete);
                    }
                    // Il pulsante viene premuto per confermare l'eliminazione
                    else {
                        deleteInsertionOnClick(v);
                    }
                }
            });
            if(!insertion.status) archiveButton.setText("Pubblica");
            archiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = ((Button)v).getText().toString();
                    String p = "Pubblica";
                    // L'inserzione viene pubblicata
                    if (text.equals(p)){
                        ((Button)v).setText("Archivia");
                        archiveInsertionOnClick(v, true);
                    }
                    // L'inserzione viene archiviata
                    else {
                        ((Button)v).setText(p);
                        archiveInsertionOnClick(v, false);
                    }
                }
            });
            insertionList.addView(preview);
        }
        sortInsertions();
    }

    private View newPreview(){
        return getLayoutInflater().inflate(R.layout.my_insertion_preview, null);
    }

    // Cancella un'inserzione
    private void deleteInsertionOnClick(@NonNull View v){
        int index = Integer.parseInt(((LinearLayout)v.getParent()).getTag().toString().replace("preview_",""));
        insertionList.removeViewAt(index);
        currentUser.insertions.remove(index);
        Shared.saveApplicationData();
    }

    // Rimanda all'activity per la modifica dell'inserzione
    private void goToInsertionOnClick(View v){
        int index = Integer.parseInt(((RelativeLayout)v.getParent()).getTag().toString().replace("preview_",""));
        Shared.currentInsertion = currentUser.insertions.get(index);
        startActivity(new Intent(this, InsertionActivity.class));
    }

    // Modifica lo stato di un'inserzione (archiviata, pubblica)
    private void archiveInsertionOnClick(View v, boolean status){
        int index = Integer.parseInt(v.getTag().toString().replace("preview_",""));
        currentUser.insertions.get(index).status = status;
        Shared.saveApplicationData();
    }

    // Riordina le inserzioni nell'activity
    // "preview_0", "preview_1", "preview_2", ...
    private void sortInsertions(){
        RelativeLayout view;
        String tag;
        for (int i = 0; i < insertionList.getChildCount(); i++){
            tag = "preview_" + i;
            view = (RelativeLayout) insertionList.getChildAt(i);
            view.setTag(tag);
            addTagToAllChildren(view,tag);

            for(int j = 0; j < view.getChildCount(); j++){
                view.getChildAt(j).setTag(tag);
            }
        }
    }

    private void addTagToAllChildren(View view, Object tag) {
        // Aggiungi il tag alla vista corrente
        view.setTag(tag);

        // Verifica se la vista corrente è un ViewGroup e ha elementi figlio
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();

            // Itera attraverso tutti i figli e richiama la funzione ricorsivamente
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                addTagToAllChildren(child, tag);
            }
        }
    }
}
