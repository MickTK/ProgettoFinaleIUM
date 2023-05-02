package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.progettoium.appartamento.R;
import com.progettoium.appartamento.shared.Shared;

public class HomeActivity extends AppCompatActivity {

    Button viewAds, favorites, insertAd, myAds, myInfo, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //attributes
        viewAds = findViewById(R.id.annunciButton);
        favorites = findViewById(R.id.prefeButton);
        insertAd = findViewById(R.id.insertButton);
        myAds = findViewById(R.id.ownButton);
        myInfo = findViewById(R.id.profileButton);
        logout = findViewById(R.id.logoutButton);

        // Switch activity
        viewAds.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
        favorites.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, Profile.class));
            }
        });
        insertAd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, Profile.class));
            }
        });
        myAds.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, Profile.class));
            }
        });
        myInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, Profile.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
    }
}
