package com.example.drosselapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddBlackboardItemActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blackboard_item);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        // Button listeners
        findViewById(R.id.addItemButton).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            FirebaseUser currentUser = mAuth.getCurrentUser();
            // Check if user is signed in (non-null)
            if (currentUser == null) {
                Log.w("TAG", "User not signed in");
                Intent intent = new Intent().setClass(AddBlackboardItemActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        else {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent().setClass(AddBlackboardItemActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        String name, location, smallDesc, description, type, price, phone, user;
        boolean valid = true;

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://drosselboapp.firebaseio.com/");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        TextView nameEditView = findViewById(R.id.itemNameEdit);
        name = nameEditView.getText().toString().trim();
        TextView priceEditView = findViewById(R.id.priceEdit);
        price = priceEditView.getText().toString();
        TextView descEditView = findViewById(R.id.descEdit);
        description = descEditView.getText().toString();
        TextView phoneEditView = findViewById(R.id.phoneEdit);
        phone = phoneEditView.getText().toString();


        if (description.length() > 50 ) {
            smallDesc = description.substring( 0 , 50 )+ "...";}
        else {
            smallDesc = description; }

        RadioGroup locationGroup = findViewById(R.id.locationGroup);
        int locationButtonID = locationGroup.getCheckedRadioButtonId();
        View locationRadioButton = locationGroup.findViewById(locationButtonID);
        int location_type = locationGroup.indexOfChild(locationRadioButton); // equals 0 for SV, 1 for KT
        location = location_type == 0 ?  "SV" : "KT";

        RadioGroup typeGroup = findViewById(R.id.typeGroup);
        int typeButtonID = typeGroup.getCheckedRadioButtonId();
        View typeRadioButton = typeGroup.findViewById(typeButtonID);
        type = String.valueOf(typeGroup.indexOfChild(typeRadioButton)); // equals 0 for borrow, 1 for looking for, 2 for sell

        if(name.equals("") || description.equals("") || price.equals("") || phone.equals("") || currentUser == null ){valid=false;}

        try {
            if (valid){
                user = Objects.requireNonNull(currentUser.getEmail()).substring(0,6);
                BlackboardItem newItem = new BlackboardItem(name, location, smallDesc, description, type, mdFormat.format(calendar.getTime()), price, user, phone);
                myRef.child("objects").push().setValue(newItem);
                Intent intent = new Intent().setClass(AddBlackboardItemActivity.this, BlackboardActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this, "Please fill up everything before posting", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Log.d(this.toString(), "error pushing Item");
        }
    }
}
