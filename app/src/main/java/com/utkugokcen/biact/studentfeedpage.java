package com.utkugokcen.biact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class studentfeedpage extends AppCompatActivity {
    TextView namesurname;
    String name, surname, department, email, clubname;
    private FirebaseFirestore firebaseFireStore;

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
        namesurname = findViewById(R.id.namesurname);
        namesurname.setText(name + " " + surname + " " + department);

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
        feedRecyclerAdapter = new FeedRecyclerAdapter(eventNameFromFB ,eventDateFromFB, eventTimeFromFB, eventLocationFromFB,
                eventPointFromFB, eventDescriptionFromFB, eventImageFromFB);
        recyclerView.setAdapter(feedRecyclerAdapter);

    }

    public void getDataFromFireStore(){

        CollectionReference collectionReference = firebaseFireStore.collection("Events");

        collectionReference.orderBy("postdate", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null){
                    Toast.makeText(studentfeedpage.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(queryDocumentSnapshots != null){

                    for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                        Map<String, Object> data = snapshot.getData();

                        String eventName = (String) data.get("eventname");
                        String eventDate = (String) data.get("eventdate");
                        String eventTime = (String) data.get("eventtime");
                        String eventLocation = (String) data.get("eventlocation");
                        String eventPoint = (String) data.get("eventpoint");
                        String eventDescription = (String) data.get("eventdescription");
                        String downloadUrl = (String) data.get("downloadurl");

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
            startActivity(intent);
        }else{
            Toast.makeText(studentfeedpage.this, "You do not have a club.", Toast.LENGTH_LONG).show();
        }
    }
}
