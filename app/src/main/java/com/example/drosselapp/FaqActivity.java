package com.example.drosselapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FaqActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        findViewById(R.id.faqSv).setOnClickListener(this);
        findViewById(R.id.faqKt).setOnClickListener(this);
        findViewById(R.id.faqBook).setOnClickListener(this);
        findViewById(R.id.faqMoveIn).setOnClickListener(this);
        findViewById(R.id.faqMoveOut).setOnClickListener(this);
        findViewById(R.id.faqAbout).setOnClickListener(this);
        findViewById(R.id.faqLiving).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView svText = findViewById(R.id.faqSvText);
        TextView ktText = findViewById(R.id.faqKtText);
        TextView bookText = findViewById(R.id.faqBookText);
        TextView moveInText = findViewById(R.id.faqMoveInText);
        TextView moveOutText = findViewById(R.id.faqMoveOutText);
        TextView aboutText = findViewById(R.id.faqAboutText);
        TextView livingText = findViewById(R.id.faqLivingText);

        svText.setVisibility(View.GONE);
        ktText.setVisibility(View.GONE);
        bookText.setVisibility(View.GONE);
        moveInText.setVisibility(View.GONE);
        moveOutText.setVisibility(View.GONE);
        aboutText.setVisibility(View.GONE);
        livingText.setVisibility(View.GONE);

        int i = view.getId();
        if (i == R.id.faqSv) {
            svText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.faqKt){
            ktText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.faqBook){
            bookText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.faqMoveIn){
            moveInText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.faqMoveOut){
            moveOutText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.faqAbout){
            aboutText.setVisibility(View.VISIBLE);
        }
        else if (i == R.id.faqLiving){
            livingText.setVisibility(View.VISIBLE);
        }
    }
}
