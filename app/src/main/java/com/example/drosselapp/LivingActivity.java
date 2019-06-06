package com.example.drosselapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LivingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living);
        findViewById(R.id.livingHowTo).setOnClickListener(this);
        findViewById(R.id.livingRules).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        TextView howToText = findViewById(R.id.livingHowToText);
        TextView rulesText = findViewById(R.id.livingRulesText);

        howToText.setVisibility(View.GONE);
        rulesText.setVisibility(View.GONE);

        int i = view.getId();
        if (i == R.id.livingHowTo) {
            howToText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.livingRules) {
            rulesText.setVisibility(View.VISIBLE);
        }
    }
}
