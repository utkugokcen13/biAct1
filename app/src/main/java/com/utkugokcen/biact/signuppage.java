package com.utkugokcen.biact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signuppage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    TextView emailText, passwordText, repasswordText, nameText, surnameText, depText;
    String email, password, repassword, name, surname, department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppagelayout);

        emailText = findViewById(R.id.depText);
        passwordText = findViewById(R.id.passwordText);
        repasswordText = findViewById(R.id.repasswordText);
        nameText = findViewById(R.id.nameText);
        surnameText = findViewById(R.id.surnameText);
        depText = findViewById(R.id.emailText);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signupClicked (View view){
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        repassword = repasswordText.getText().toString();
        name = nameText.getText().toString();
        surname = surnameText.getText().toString();
        department = depText.getText().toString();


        if(passwordText.getText().toString().equals(repasswordText.getText().toString())){
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(signuppage.this, "Your account has been created successfully.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(signuppage.this, loginpage.class);
                    intent.putExtra("mail", email);
                    intent.putExtra("password", password);
                    intent.putExtra("name", name);
                    intent.putExtra("surname", surname);
                    intent.putExtra("dep", department);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(signuppage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });


        } else {
            Toast.makeText(signuppage.this, "Please enter matching passwords.", Toast.LENGTH_LONG).show();
        }

    }
}
