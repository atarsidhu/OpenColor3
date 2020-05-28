package com.example.opencolor;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Results extends AppCompatActivity {

    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvResults = findViewById(R.id.tvResults);

        tvResults.setText("(DEV PURPOSES) Based on the results, you have ______ \n" +
                "Results: " + getIntent().getStringExtra("RESULTS"));

    }
}
