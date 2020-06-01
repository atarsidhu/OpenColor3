package com.example.opencolor;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Results extends AppCompatActivity {

    TextView tvResults;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvResults = findViewById(R.id.tvResults);
        homeButton = findViewById(R.id.homeButton);

        tvResults.setText("(DEV PURPOSES) Based on the results, you have ______ \n" +
                "Results: " + getIntent().getStringExtra("RESULTS"));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
