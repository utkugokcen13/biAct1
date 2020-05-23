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

public class clubprofilepage extends AppCompatActivity {
    TextView nameText, descriptionText, clubnameText;
    String name, surname, department, email, clubname, clubdescription, namesurnameText,password;
    Bitmap selectedImage;
    ImageView addphoto;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Uri imageData;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubprofilepagelayout);


        nameText = findViewById(R.id.nameText);
        descriptionText = findViewById(R.id.descriptionText);
        clubnameText = findViewById(R.id.clubnameText);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        department = intent.getStringExtra("dep");
        email = intent.getStringExtra("mail");
        clubname = intent.getStringExtra("clubname");
        clubdescription = intent.getStringExtra("clubdes");
        namesurnameText = intent.getStringExtra("namesurname");
        password = intent.getStringExtra("password");

        clubnameText.setText(clubname);
        descriptionText.setText("Club Description: " + clubdescription);
        nameText.setText("Club Name: " + clubname);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


    }

    public void backClicked(View view){
        Intent intent = new Intent(clubprofilepage.this, clubfeedpage.class);
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
                                Toast.makeText(clubprofilepage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
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
