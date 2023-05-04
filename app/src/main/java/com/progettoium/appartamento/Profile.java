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
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        image = findViewById(R.id.resultImage);
        resultUsername = findViewById(R.id.resultUsername);
        resultName = findViewById(R.id.resultName);
        resultSurname = findViewById(R.id.resultSurname);
        resultNum = findViewById(R.id.resultNum);
        resultEmail = findViewById(R.id.resultEmail);
        editInfo = findViewById(R.id.changePwd);
        //logout = findViewById(R.id.logoutButton);
    }
}
