package com.utkugokcen.biact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class settingspage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    String name, surname, department, email, clubname, clubdescription,namesurnameText,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspagelayout);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        department = intent.getStringExtra("dep");
        email = intent.getStringExtra("mail");
        clubname = intent.getStringExtra("clubname");
        clubdescription = intent.getStringExtra("clubdes");
        namesurnameText = intent.getStringExtra("namesurname");
        password = intent.getStringExtra("password");

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signoutClicked(View view){
        firebaseAuth.signOut();

        Intent intent = new Intent(settingspage.this,loginpage.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("dep", department);
        intent.putExtra("mail", email);
        intent.putExtra("clubname", clubname);
        intent.putExtra("clubdes", clubdescription);
        intent.putExtra("namesurname", namesurnameText);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    public void backClicked(View view){
        Intent intent = new Intent(settingspage.this,studentfeedpage.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("dep", department);
        intent.putExtra("mail", email);
        intent.putExtra("clubname", clubname);
        intent.putExtra("clubdes", clubdescription);
        intent.putExtra("namesurname", namesurnameText);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}
