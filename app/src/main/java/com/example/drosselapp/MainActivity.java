package com.example.drosselapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.imageButtonFaq).setOnClickListener(this);
        findViewById(R.id.imageButtonTicket).setOnClickListener(this);
        findViewById(R.id.imageButtonWasher).setOnClickListener(this);
        findViewById(R.id.imageButtonBlackboard).setOnClickListener(this);
        findViewById(R.id.imageButtonCheckIn).setOnClickListener(this);
        findViewById(R.id.logOutButton).setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.w("TAG", "User not signed in");
            Intent intent = new Intent().setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            welcomeTextView = findViewById(R.id.welcomeText);
            String welcomeText = getString(R.string.welcome) + currentUser.getEmail();
            welcomeTextView.setText(welcomeText);
        }
    }

    @Override
    public void onBackPressed() {
    // super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.imageButtonFaq) {
            Intent intent = new Intent().setClass(MainActivity.this, FaqActivity.class);
            startActivity(intent);
        } else if (i == R.id.imageButtonTicket) {
            Intent intent = new Intent().setClass(MainActivity.this, TicketActivity.class);
            startActivity(intent);
        }
        else if (i == R.id.imageButtonWasher) {
            Intent intent = new Intent().setClass(MainActivity.this, WasherActivity.class);
            startActivity(intent);
        }
        else if (i == R.id.imageButtonBlackboard) {
            Intent intent = new Intent().setClass(MainActivity.this, BlackboardActivity.class);
            startActivity(intent);
        }
        else if (i == R.id.imageButtonCheckIn) {
            Intent intent = new Intent().setClass(MainActivity.this, LivingActivity.class);
            startActivity(intent);
        }
        else if (i == R.id.logOutButton) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent().setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

