package com.example.opencolor;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;

public class ColorBlindTest extends AppCompatActivity {

    ImageView test;
    EditText answer;
    Button btnNext;
    Button btnNotSure;

    int[] answerArr;
    int[] imageArr;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        test = findViewById(R.id.imgTest1);
        answer = findViewById(R.id.etAnswer1);
        btnNext = findViewById(R.id.btnNext);
        btnNotSure = findViewById(R.id.btnNotSure);
        imageArr = new int[]{R.drawable.test1, R.drawable.test2, R.drawable.test3};
        answerArr = new int[imageArr.length];

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If no answer given
                if(answer.getText().toString().equals(""))
                    Toast.makeText(ColorBlindTest.this, "Please enter a value or select 'Not Sure' to continue", Toast.LENGTH_SHORT).show();
                else{
                    // Store answer and increase count
                    answerArr[count] = Integer.parseInt(answer.getText().toString());
                    count++;

                    // If there are still images left, proceed to next image. Else, go to results page
                    if(count < imageArr.length)
                        nextImage();
                    else
                        passResults(Arrays.toString(answerArr));
                }
            }
        });

        btnNotSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If there are still images left, proceed to next image. Else, go to results page
                if(count < imageArr.length - 1){
                    answerArr[count] = 0;
                    count++;

                    nextImage();
                }else
                    passResults(Arrays.toString(answerArr));
            }
        });
    }

    public void passResults(String res){
        Intent startIntent = new Intent(ColorBlindTest.this, Results.class);
        startIntent.putExtra("RESULTS", res);
        startActivity(startIntent);
    }

    public void nextImage(){
        test.setImageResource(imageArr[count]);
        answer.setText("");

        if(count == imageArr.length -1)
            btnNext.setText(R.string.finish);
    }

}
