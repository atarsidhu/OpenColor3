package com.example.opencolor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Picture extends AppCompatActivity {

    Button openCameraBtn;
    Button choosePictureBtn;
    ImageView imageView;
    private static final int PICTURE_ID = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageView = findViewById(R.id.imageView);

        // Open camera and take a photo
        openCameraBtn = findViewById(R.id.btnCamera);
        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(startIntent, PICTURE_ID);
            }
        });

        // Open gallery and choose an image
        choosePictureBtn = findViewById(R.id.btnChoosePicture);
        choosePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent();
                startIntent.setType("image/*");
                startIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(startIntent, "Pick an image"), GALLERY_REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Taking picture from camera
        if (requestCode == PICTURE_ID) {
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }

        // Selecting picture from gallery
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri photo = data.getData();
            imageView.setImageURI(photo);
        }
    }


}
