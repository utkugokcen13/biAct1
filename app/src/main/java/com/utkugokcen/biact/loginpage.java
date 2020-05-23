package com.utkugokcen.biact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginpage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText emailText, passwordText;
    String email, password, name, surname, department,namesurnameText,clubname,clubdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpagelayout);

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        Intent intent2 = getIntent();
        name = intent2.getStringExtra("name");
        surname = intent2.getStringExtra("surname");
        department = intent2.getStringExtra("dep");
        email = intent2.getStringExtra("mail");
        clubname = intent2.getStringExtra("clubname");
        clubdescription = intent2.getStringExtra("clubdes");
        namesurnameText = intent2.getStringExtra("namesurname");
        password = intent2.getStringExtra("password");


        if(firebaseUser != null){

            Intent intent3 = getIntent();
            name = intent3.getStringExtra("name");
            surname = intent3.getStringExtra("surname");
            department = intent3.getStringExtra("dep");
            email = intent3.getStringExtra("mail");
            clubname = intent3.getStringExtra("clubname");
            clubdescription = intent3.getStringExtra("clubdes");
            namesurnameText = intent3.getStringExtra("namesurname");
            password = intent3.getStringExtra("password");
            Intent intent = new Intent(loginpage.this, studentfeedpage.class);
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

    public void loginClicked (View view){

        if (emailText.getText().toString() == null || passwordText.getText().toString() == null) {
            Toast.makeText(loginpage.this, "Please enter mail address and password.", Toast.LENGTH_LONG).show();
        }else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(loginpage.this, "You logged in successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(loginpage.this, studentfeedpage.class);
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
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginpage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        }
    }

    public void signupClicked (View view){
        Intent intent = new Intent(loginpage.this, signuppage.class);
        startActivity(intent);

    }
}
