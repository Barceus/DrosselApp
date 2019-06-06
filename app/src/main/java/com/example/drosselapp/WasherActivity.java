package com.example.drosselapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class WasherActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String[] location = {"Student Village", "Kamtjatka", "Arduino"};
    String zone;
    DatabaseReference myRef, myRef2;
    ArrayList<LaundryItem> laundryItemList, laundryItemList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer);

        TextView washerButton = findViewById(R.id.washerButton);
        washerButton.setOnClickListener(this);
        TextView dryerButton = findViewById(R.id.dryerButton);
        dryerButton.setOnClickListener(this);

        Spinner spinner = findViewById(R.id.locationSpinner);
        spinner.setOnItemSelectedListener(this);

        findViewById(R.id.washerNotifButton).setOnClickListener(this);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, location);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(location[position].equals("Student Village")){
            //Call the API for Student Village data
            zone = "sv";
        }
        else if(location[position].equals("Kamtjatka")){
            //Call the API for Kamtjatka data
            zone = "kt";
        }
        else{
            zone="arduino";
        }
        updateUI(zone);

        laundryItemList = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference().child("laundry").child(zone).child("washers");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    LaundryItem laundryItem = dataSnapshot1.getValue(LaundryItem.class);
                    laundryItemList.add(laundryItem);
                }
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText1), (ImageView) findViewById(R.id.washer1),0);
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText2), (ImageView) findViewById(R.id.washer2),1);
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText3),(ImageView) findViewById(R.id.washer3),2);
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText4),(ImageView) findViewById(R.id.washer4),3);
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText5),(ImageView) findViewById(R.id.washer5),4);
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText6),(ImageView) findViewById(R.id.washer6),5);
                getAvailability(laundryItemList, (TextView) findViewById(R.id.washerText7),(ImageView) findViewById(R.id.washer7),6);
                laundryItemList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        laundryItemList2 = new ArrayList<>();
        myRef2 = FirebaseDatabase.getInstance().getReference().child("laundry").child(zone).child("dryers");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    LaundryItem laundryItem = dataSnapshot1.getValue(LaundryItem.class);
                    laundryItemList2.add(laundryItem);
                }
                getAvailability(laundryItemList2, (TextView) findViewById(R.id.dryerText1), (ImageView) findViewById(R.id.dryer1),0);
                getAvailability(laundryItemList2, (TextView) findViewById(R.id.dryerText2), (ImageView) findViewById(R.id.dryer2),1);
                getAvailability(laundryItemList2, (TextView) findViewById(R.id.dryerText3),(ImageView) findViewById(R.id.dryer3),2);
                laundryItemList2.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        TextView washerText = findViewById(R.id.washerButton);
        TextView dryerText = findViewById(R.id.dryerButton);
        LinearLayout dryerLayout = findViewById(R.id.dryerLayout);
        LinearLayout washerLayout = findViewById(R.id.washerLayout);

        if (i == R.id.washerButton) {
            switchMachineType(dryerText, washerText, dryerLayout, washerLayout);
        } else if (i == R.id.dryerButton) {
            switchMachineType(washerText, dryerText, washerLayout, dryerLayout);
        }
    }
    public void switchMachineType(TextView oldButton, TextView newButton, LinearLayout oldLayout, LinearLayout newLayout){
        newButton.setBackgroundColor(0xFFDDDDDD);
        oldButton.setBackgroundColor(0xFFFFFFFF);
        oldButton.setElevation(0);
        newButton.setElevation(48);
        oldLayout.setVisibility(View.GONE);
        newLayout.setVisibility(View.VISIBLE);
    }

    public void getAvailability(ArrayList<LaundryItem> laundryItemList, TextView availText, ImageView availImage, int i){
        try{
            if ((laundryItemList.get(i).getStatus() == 0)) {
                availText.setText(getString(R.string.washer_run));
                availImage.setColorFilter(Color.argb(255, 229, 0, 0));
            } else {
                availText.setText(getString(R.string.washer_avail));
                availImage.setColorFilter(Color.argb(255, 0, 128, 0));
            }
        }
        catch (Exception exception){
            Log.d("LaundryList", "No more entries");
        }

    }

    public void updateUI(String zone){
        //this part is mainly for presentation purposes, when removing the "Arduino" part used during the presentation, the code becomes less convoluted
        findViewById(R.id.washer2).setVisibility(View.VISIBLE);
        findViewById(R.id.washerText2).setVisibility(View.VISIBLE);
        findViewById(R.id.washer3).setVisibility(View.VISIBLE);
        findViewById(R.id.washerText3).setVisibility(View.VISIBLE);
        findViewById(R.id.washer4).setVisibility(View.VISIBLE);
        findViewById(R.id.washerText4).setVisibility(View.VISIBLE);
        findViewById(R.id.washer5).setVisibility(View.VISIBLE);
        findViewById(R.id.washerText5).setVisibility(View.VISIBLE);
        findViewById(R.id.washer6).setVisibility(View.VISIBLE);
        findViewById(R.id.washerText6).setVisibility(View.VISIBLE);
        findViewById(R.id.dryer1).setVisibility(View.VISIBLE);
        findViewById(R.id.dryerText1).setVisibility(View.VISIBLE);
        findViewById(R.id.dryer2).setVisibility(View.VISIBLE);
        findViewById(R.id.dryerText2).setVisibility(View.VISIBLE);
        if(zone.equals("sv")){
            findViewById(R.id.washer7).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText7).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryer3).setVisibility(View.VISIBLE);
            findViewById(R.id.dryerText3).setVisibility(View.VISIBLE);
        }
        else if(zone.equals("kt")){
            findViewById(R.id.washer7).setVisibility(View.VISIBLE);
            findViewById(R.id.washerText7).setVisibility(View.VISIBLE);
            findViewById(R.id.dryer3).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryerText3).setVisibility(View.INVISIBLE);
        }
        else{
            findViewById(R.id.washer2).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText2).setVisibility(View.INVISIBLE);
            findViewById(R.id.washer3).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText3).setVisibility(View.INVISIBLE);
            findViewById(R.id.washer4).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText4).setVisibility(View.INVISIBLE);
            findViewById(R.id.washer5).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText5).setVisibility(View.INVISIBLE);
            findViewById(R.id.washer6).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText6).setVisibility(View.INVISIBLE);
            findViewById(R.id.washer7).setVisibility(View.INVISIBLE);
            findViewById(R.id.washerText7).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryer1).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryerText1).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryer2).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryerText2).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryer3).setVisibility(View.INVISIBLE);
            findViewById(R.id.dryerText3).setVisibility(View.INVISIBLE);


        }
    }
}
