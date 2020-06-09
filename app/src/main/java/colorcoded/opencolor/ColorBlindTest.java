package com.example.opencolor;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ColorBlindTest extends AppCompatActivity {

    ImageView test;
    EditText answer;
    Button btnNext;
    Button btnNotSure;
    Button btnBeginTest;
    Dialog popupInfo;

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

        //Load test images in a Unit Testable way
        imageArr = setupTestImages();
        answerArr = new int[imageArr.length];

        showPopup();

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
                        passUserAnswers(answerArr);
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
                    passUserAnswers(answerArr);
            }
        });

    }

    public void showPopup(){
        popupInfo = new Dialog(this);
        popupInfo.setContentView(R.layout.popup_test_instructions);
        btnBeginTest = popupInfo.findViewById(R.id.btnBeginTest);
        popupInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupInfo.setCanceledOnTouchOutside(false);
        popupInfo.setCancelable(false);
        popupInfo.show();

        btnBeginTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupInfo.dismiss();
            }
        });
    }

    public void passUserAnswers(int[] ans){
        Intent startIntent = new Intent(ColorBlindTest.this, Results.class);
        startIntent.putExtra("USER_ANSWERS", ans);
        startActivity(startIntent);
    }

    public void nextImage(){
        test.setImageResource(imageArr[count]);
        answer.setText("");

        if(count == imageArr.length -1)
            btnNext.setText(R.string.finish);
    }

    public int[] setupTestImages(){
        int[] images = new int[10];

        images[0] = R.drawable.colorblindtest01;
        images[1] = R.drawable.colorblindtest02;
        images[2] = R.drawable.colorblindtest03;
        images[3] = R.drawable.colorblindtest04;
        images[4] = R.drawable.colorblindtest05;
        images[5] = R.drawable.colorblindtest06;
        images[6] = R.drawable.colorblindtest07;
        images[7] = R.drawable.colorblindtest08;
        images[8] = R.drawable.colorblindtest09;
        images[9] = R.drawable.colorblindtest10;
/*
        Drawable res;
        for(int i = 1; i < imageArr.length; i++)
            res = getResources().getDrawable(getResources().getIdentifier("colorblindtest" + i + ".png", "drawable", getPackageName()))
*/
        return images;
    }

}
