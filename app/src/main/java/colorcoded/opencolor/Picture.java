package colorcoded.example.opencolor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.opencolor.R;

public class Picture extends AppCompatActivity {

    ImageView openCameraBtn;
    ImageView choosePictureBtn;
    ImageView selectedImage;
    Button processImage;
    private static final int PICTURE_ID = 1;
    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    public Activity pictureActivity;
    public View.OnClickListener processImageClicked;
    public View.OnClickListener cameraClicked;
    public View.OnClickListener chooseClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        pictureActivity = this;

        associateResources();
        createListeners();
        setListeners();


    }

    public boolean associateResources(){
        selectedImage = findViewById(R.id.imgSelected);
        processImage = findViewById(R.id.processImage);
        openCameraBtn = findViewById(R.id.imgBtnOpenCamera);
        choosePictureBtn = findViewById(R.id.imgBtnGallery);
        return true;
    }

    public boolean createListeners(){
        processImageClicked = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedImage.setImageResource(R.drawable.imageplaceholder);
            }
        };

        cameraClicked = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (startIntent.resolveActivity(getPackageManager()) != null) {
                    if (ContextCompat.checkSelfPermission(pictureActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        startActivityForResult(startIntent, PICTURE_ID);
                    }
                    else{
                        ActivityCompat.requestPermissions(pictureActivity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        startActivityForResult(startIntent, PICTURE_ID);
                    }
                }
            }
        };

        chooseClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent();
                startIntent.setType("image/*");
                startIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(startIntent, "Pick an image"), GALLERY_REQUEST_CODE);
            }
        };

        return true;
    }

    public boolean setListeners(){
        processImage.setOnClickListener(processImageClicked);
        openCameraBtn.setOnClickListener(cameraClicked);
        choosePictureBtn.setOnClickListener(chooseClicked);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Taking picture from camera
        if (requestCode == PICTURE_ID && resultCode == RESULT_OK && data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(photo);
        }

        // Selecting picture from gallery
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri photo = data.getData();
            selectedImage.setImageURI(photo);
        }
    }


}
