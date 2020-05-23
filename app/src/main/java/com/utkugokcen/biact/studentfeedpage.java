package com.utkugokcen.biact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class studentfeedpage extends AppCompatActivity {
    TextView namesurname, r_eventName,r_eventDate,r_eventTime,r_eventLocation,r_eventPoint,r_clubName,r_eventDescription;
    String name, surname, department, email, clubname,clubdescription, namesurnameText, password;
    //String eventName, eventDate, eventTime, eventLocation, eventPoint, eventDescription, eventOwner;
    private FirebaseFirestore firebaseFireStore;
    private StorageReference storageReference;

    ArrayList<String> eventOwnerFromFB;
    ArrayList<String> eventNameFromFB;
    ArrayList<String> eventDateFromFB;
    ArrayList<String> eventTimeFromFB;
    ArrayList<String> eventLocationFromFB;
    ArrayList<String> eventPointFromFB;
    ArrayList<String> eventDescriptionFromFB;
    ArrayList<String> eventImageFromFB;

    FeedRecyclerAdapter feedRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentfeedpagelayout);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        department = intent.getStringExtra("dep");
        email = intent.getStringExtra("mail");
        clubname = intent.getStringExtra("clubname");
        clubdescription = intent.getStringExtra("clubdes");
        namesurnameText = intent.getStringExtra("namesurname");
        password = intent.getStringExtra("password");
        namesurnameText = name + " " + surname + " " + department;


        namesurname = findViewById(R.id.namesurname);
        namesurname.setText(namesurnameText);

        r_eventName = findViewById(R.id.r_eventName);
        r_eventDate = findViewById(R.id.r_eventDate);
        r_eventTime = findViewById(R.id.r_eventTime);
        r_eventLocation = findViewById(R.id.r_eventLocation);
        r_eventPoint = findViewById(R.id.r_eventPoint);
        r_eventDescription = findViewById(R.id.r_eventDescription);
        r_clubName = findViewById(R.id.r_clubName);



        eventOwnerFromFB = new ArrayList<>();
        eventNameFromFB = new ArrayList<>();
        eventDateFromFB = new ArrayList<>();
        eventTimeFromFB = new ArrayList<>();
        eventLocationFromFB = new ArrayList<>();
        eventPointFromFB = new ArrayList<>();
        eventDescriptionFromFB = new ArrayList<>();
        eventImageFromFB = new ArrayList<>();



        firebaseFireStore = FirebaseFirestore.getInstance();

        getDataFromFireStore();

        //RecyclerView

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerAdapter = new FeedRecyclerAdapter(eventOwnerFromFB,eventNameFromFB ,eventDateFromFB, eventTimeFromFB, eventLocationFromFB,
                eventPointFromFB, eventDescriptionFromFB, eventImageFromFB);
        recyclerView.setAdapter(feedRecyclerAdapter);

    }

    public void upcomingClicked(View view){
        Intent intent = new Intent(studentfeedpage.this, myupcomingeventspage.class);
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

    public void attendClicked(View view){

       //feedRecyclerAdapter.onBindViewHolder1(holder);

    }

    public void settingsClicked(View view){
        Intent intent = new Intent(studentfeedpage.this, settingspage.class);
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



    public void getDataFromFireStore(){

        CollectionReference collectionReference = firebaseFireStore.collection("studentPageEvents");

        collectionReference.orderBy("postdate", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null){
                    Toast.makeText(studentfeedpage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(queryDocumentSnapshots != null){

                    for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                        Map<String, Object> data = snapshot.getData();

                        String eventOwner = (String) data.get("eventowner");
                        String eventName = (String) data.get("eventname");
                        String eventDate = (String) data.get("eventdate");
                        String eventTime = (String) data.get("eventtime");
                        String eventLocation = (String) data.get("eventlocation");
                        String eventPoint = (String) data.get("eventpoint");
                        String eventDescription = (String) data.get("eventdescription");
                        String downloadUrl = (String) data.get("downloadurl");

                        eventOwnerFromFB.add(eventOwner);
                        eventNameFromFB.add(eventName);
                        eventDateFromFB.add(eventDate);
                        eventTimeFromFB.add(eventTime);
                        eventLocationFromFB.add(eventLocation);
                        eventPointFromFB.add(eventPoint);
                        eventDescriptionFromFB.add(eventDescription);
                        eventImageFromFB.add(downloadUrl);

                        feedRecyclerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });




    }

    public void profileClicked(View view){
        Intent intent = new Intent(studentfeedpage.this, studentprofilepage.class);
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

    public void clubClicked(View view){
        if(clubname != null) {
            Intent intent = new Intent(studentfeedpage.this, clubfeedpage.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            intent.putExtra("dep", department);
            intent.putExtra("mail", email);
            intent.putExtra("clubname", clubname);
            intent.putExtra("clubdes", clubdescription);
            intent.putExtra("namesurname", namesurnameText);
            intent.putExtra("password", password);
            startActivity(intent);
        }else{
            Toast.makeText(studentfeedpage.this, "You do not have a club.", Toast.LENGTH_LONG).show();
        }
    }
}
