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
    String email, password, name, surname, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpagelayout);

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    }

    public void loginClicked (View view){
        Intent intent = getIntent();
        email = intent.getStringExtra("mail");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        department = intent.getStringExtra("dep");
        if (emailText.getText().toString() == null || passwordText.getText().toString() == null) {
            Toast.makeText(loginpage.this, "Please enter mail address and password.", Toast.LENGTH_LONG).show();
        }else {
            if (emailText.getText().toString().equals(email) && passwordText.getText().toString().equals(password)) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(loginpage.this, "You logged in successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(loginpage.this, studentfeedpage.class);
                        intent.putExtra("name", name);
                        intent.putExtra("surname", surname);
                        intent.putExtra("dep", department);
                        intent.putExtra("mail", email);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginpage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(loginpage.this, "Mail address or password is incorrect.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void signupClicked (View view){
        Intent intent = new Intent(loginpage.this, signuppage.class);
        startActivity(intent);

    }
}
