package com.example.drosselapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class DetailBlackboardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_blackboard);

        ImageButton detailEmailButton = findViewById(R.id.detailEmailButton);
        detailEmailButton.setOnClickListener(this);
        ImageButton detailPhoneButton = findViewById(R.id.detailPhoneButton);
        detailPhoneButton.setOnClickListener(this);

        TextView detailName = findViewById(R.id.detailName);
        detailName.setText(Objects.requireNonNull(getIntent().getExtras()).getString("NAME"));
        TextView detailDesc = findViewById(R.id.detailDesc);
        detailDesc.setText(getIntent().getExtras().getString("DESC"));

        TextView detailType = findViewById(R.id.detailType);
        String type = "Borrow";
        switch (Objects.requireNonNull(getIntent().getExtras().getString("TYPE"))){
            case "0":
                break;
            case "1":
                type = "Looking For";
                break;
            case "2":
                type = "Sell";
                break;
        }
        detailType.setText(type);

        TextView detailPhone = findViewById(R.id.detailPhone);
        detailPhone.setText(getIntent().getExtras().getString("PHONE"));
        TextView detailLocation = findViewById(R.id.detailLocation);

        String location = "Student Village";
        switch (Objects.requireNonNull(getIntent().getExtras().getString("LOCATION"))){
            case "sv":
                break;
            case "kt":
                location = "Kamtjatka";
                break;
        }
        detailLocation.setText(location);

        TextView detailPrice = findViewById(R.id.detailPrice);
        String price = getIntent().getExtras().getString("PRICE").equals("0") ? "FREE" : getIntent().getExtras().getString("PRICE") + " DKK";
        detailPrice.setText(price);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.detailEmailButton){
            String mail = getIntent().getExtras().getString("USER");
            if(mail.equals("")) {
                Toast.makeText(this, "User didn't give their email", Toast.LENGTH_SHORT).show();
            }
            else{
                try{
                   Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",mail +"@via.dk", null));
                   mailIntent.putExtra(Intent.EXTRA_SUBJECT, "DrosselApp Blackboard : " + getIntent().getExtras().getString("NAME"));
                   startActivity(Intent.createChooser(mailIntent, "Choose an Email client :"));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(i == R.id.detailPhoneButton){
            String tel = "tel:" + getIntent().getExtras().getString("PHONE");
            if(tel.equals("")) {
                Toast.makeText(this, "User didn't give their phone number", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(tel));
                startActivity(callIntent);
            }
        }
    }
}
