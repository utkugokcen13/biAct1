package com.utkugokcen.biact;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<String> eventNamesList;
    private ArrayList<String> eventDatesList;
    private ArrayList<String> eventTimesList;
    private ArrayList<String> eventLocationsList;
    private ArrayList<String> eventPointsList;
    private ArrayList<String> eventDescriptionsList;
    private ArrayList<String> eventImagesList;




    public FeedRecyclerAdapter(ArrayList<String> eventNamesList, ArrayList<String> eventDatesList,
                               ArrayList<String> eventTimesList, ArrayList<String> eventLocationsList,
                               ArrayList<String> eventPointsList, ArrayList<String> eventDescriptionsList,
                               ArrayList<String> eventImagesList) {
        this.eventNamesList = eventNamesList;
        this.eventDatesList = eventDatesList;
        this.eventTimesList = eventTimesList;
        this.eventLocationsList = eventLocationsList;
        this.eventPointsList = eventPointsList;
        this.eventDescriptionsList = eventDescriptionsList;
        this.eventImagesList = eventImagesList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        //holder.clubNameText.setText(clubnameText.getText().toString());
        holder.eventName.setText("Event Name: " +eventNamesList.get(position));
        holder.eventDate.setText("Event Date: " + eventDatesList.get(position));
        holder.eventTime.setText("Event Time: " +eventTimesList.get(position));
        holder.eventLocation.setText("Event Location: " + eventLocationsList.get(position));
        holder.eventPoint.setText("GE250/1 Point: " + eventPointsList.get(position));
        holder.eventDescription.setText("Event Description: " +eventDescriptionsList.get(position));
        Picasso.get().load(eventImagesList.get(position)).into(holder.eventImage);
    }

    @Override
    public int getItemCount() {
        return eventNamesList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        ImageView eventImage;
        TextView clubNameText;
        TextView eventName;
        TextView eventDate;
        TextView eventTime;
        TextView eventLocation;
        TextView eventPoint;
        TextView eventDescription;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.r_eventImage);
            clubNameText = itemView.findViewById(R.id.r_clubName);
            eventName = itemView.findViewById(R.id.r_eventName);
            eventDate = itemView.findViewById(R.id.r_eventDate);
            eventTime = itemView.findViewById(R.id.r_eventTime);
            eventLocation = itemView.findViewById(R.id.r_eventLocation);
            eventPoint = itemView.findViewById(R.id.r_eventPoint);
            eventDescription = itemView.findViewById(R.id.r_eventDescription);

        }
    }
}
