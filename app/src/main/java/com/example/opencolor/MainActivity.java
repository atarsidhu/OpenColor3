package com.example.opencolor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView startTestBtn;
    ImageView openCameraBtn;
    ImageView grayscaleImg;
    ImageView colorImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTestBtn = findViewById(R.id.imgBtnTest);
        startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Test.class);
                startActivity(startIntent);
            }
        });

        openCameraBtn = findViewById(R.id.imgBtnCamera);
        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Picture.class);
                startActivity(startIntent);
            }
        });

        grayscaleImg = findViewById(R.id.imgGrayscale);
        colorImg = findViewById(R.id.imgColor);
        
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        grayscaleImg.startAnimation(fadeOut);
        
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        colorImg.startAnimation(fadeIn);
        
    }

}
