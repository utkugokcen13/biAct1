package com.utkugokcen.biact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class studentprofilepage extends AppCompatActivity {
    TextView nameText, emailText, depText, namesurname;
    String name, surname, department, email, clubname;
    Bitmap selectedImage;
    ImageView addphoto;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Uri imageData;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentprofilepagelayout);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        depText = findViewById(R.id.depText);
        namesurname = findViewById(R.id.clubname);

        addphoto = findViewById(R.id.addphoto);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        department = intent.getStringExtra("dep");
        email = intent.getStringExtra("mail");
        clubname = intent.getStringExtra("clubname");

        nameText.setText("Name: " + name + " " + surname);
        depText.setText("Department: " + department);
        emailText.setText("E-mail: " + email );
        namesurname.setText(name + " " + surname + " " + department);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void backClicked(View view){
        Intent intent = new Intent(studentprofilepage.this, studentfeedpage.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("dep", department);
        intent.putExtra("mail", email);
        intent.putExtra("clubname", clubname);
        startActivity(intent);
    }

    public void createclubClicked(View view){
        if(clubname == null) {
            Intent intent = new Intent(studentprofilepage.this, createclubpage.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            intent.putExtra("dep", department);
            intent.putExtra("mail", email);
            intent.putExtra("clubname", clubname);
            startActivity(intent);
        }else{
            Toast.makeText(studentprofilepage.this, "You already have a club.", Toast.LENGTH_LONG).show();
        }
    }

    public void addphotoClicked(View view){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1 );
        }else{
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageData = data.getData();
            try {
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    addphoto.setImageBitmap(selectedImage);
                }else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    addphoto.setImageBitmap(selectedImage);


                    if (imageData != null) {

                        UUID uuid = UUID.randomUUID();
                        final String imageName = "profilephotos/" + uuid + ".jpg";
                        storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                                newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadUrl = uri.toString();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(studentprofilepage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
