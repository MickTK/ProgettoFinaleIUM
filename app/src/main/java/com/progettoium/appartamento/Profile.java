package com.progettoium.appartamento;

import androidx.appcompat.app.AppCompatActivity;

import com.progettoium.appartamento.classes.User;
import com.progettoium.appartamento.classes.UserList.*;
import com.progettoium.appartamento.shared.Shared;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    User user = Shared.userList.getCurrent();

    TextView resultUsername, resultName, resultSurname, resultNum, resultEmail, editInfo;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        image = findViewById(R.id.resultImage);
        image.setImageURI(user.getProfilePicture());
        resultUsername = findViewById(R.id.resultUsername);
        resultUsername.setText(user.username);
        resultName = findViewById(R.id.resultName);
        resultName.setText(user.name);
        resultSurname = findViewById(R.id.resultSurname);
        resultSurname.setText(user.surname);
        resultNum = findViewById(R.id.resultNum);
        resultNum.setText(user.number);
        resultEmail = findViewById(R.id.resultEmail);
        resultEmail.setText(user.email);

        /*editInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Profile.this, ActivityChange.class));
            }
        });*/
    }
}
