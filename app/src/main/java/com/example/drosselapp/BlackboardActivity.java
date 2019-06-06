package com.example.drosselapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BlackboardActivity extends AppCompatActivity implements View.OnClickListener,BlackboardAdapter.OnItemClickListener {

    DatabaseReference myRef;
    RecyclerView recyclerView;
    ArrayList<BlackboardItem> blackboardList;
    BlackboardAdapter blackboardAdapter;
    FloatingActionButton itemFab;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackboard);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.itemsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        blackboardList = new ArrayList<>();

        // Button listeners
        itemFab = findViewById(R.id.addItemFab);
        itemFab.setOnClickListener(this);
        itemFab.setAlpha(0.75f);
        myRef = FirebaseDatabase.getInstance().getReference().child("objects");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blackboardList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BlackboardItem blackboardItem = dataSnapshot1.getValue(BlackboardItem.class);
                    blackboardList.add(blackboardItem);
                    blackboardAdapter.notifyDataSetChanged();
                }
                Collections.reverse(blackboardList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BlackboardActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        blackboardAdapter = new BlackboardAdapter(BlackboardActivity.this, blackboardList,this);
        recyclerView.setAdapter(blackboardAdapter);
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
                Intent intent = new Intent().setClass(BlackboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        else {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent().setClass(BlackboardActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.addItemFab) {
            Intent intent = new Intent().setClass(BlackboardActivity.this, AddBlackboardItemActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(BlackboardActivity.this, DetailBlackboardActivity.class);
        BlackboardItem item = blackboardList.get(pos);
        intent.putExtra("NAME", item.getName());
        intent.putExtra("USER", item.getUser());
        intent.putExtra("DESC", item.getDescription());
        intent.putExtra("TYPE", item.getType());
        intent.putExtra("PHONE", item.getPhone());
        intent.putExtra("LOCATION", item.getLocation());
        intent.putExtra("PRICE", item.getPrice());
        startActivity(intent);
    }
}
