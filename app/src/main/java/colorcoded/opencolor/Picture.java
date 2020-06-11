package colorcoded.opencolor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chaquo.python.Python;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Picture extends AppCompatActivity {

    ImageView openCameraBtn;
    ImageView choosePictureBtn;
    ImageView selectedImage;
    Button processImage;
    Button saveImage;
    BottomNavigationView bottomNavigationView;
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
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_camera);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_camera:
                        return true;
                    case R.id.nav_test:
                        startActivity(new Intent(getApplicationContext(), ColorBlindTest.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_library:
                        startActivity(new Intent(getApplicationContext(), PhotoLibrary.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    public void associateResources(){
        selectedImage = findViewById(R.id.imgSelected);
        processImage = findViewById(R.id.processImage);
        saveImage = findViewById(R.id.btnSaveImage);
        openCameraBtn = findViewById(R.id.imgBtnOpenCamera);
        choosePictureBtn = findViewById(R.id.imgBtnGallery);
    }

    public String pythonText(String text){
        Python py = Python.getInstance();
        //PyObject py_test = py.getModule("ColorShift");
        //return py_test.callAttr("py_print", "Chaquopy").toString();
        return "String";
    }

    public void createListeners(){
        processImageClicked = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //selectedImage.setImageResource(R.drawable.imageplaceholder);
                processImage.setText(pythonText("Text"));
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

    }

    public void setListeners(){
        processImage.setOnClickListener(processImageClicked);
        openCameraBtn.setOnClickListener(cameraClicked);
        choosePictureBtn.setOnClickListener(chooseClicked);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Taking picture from camera
        if (requestCode == PICTURE_ID && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            super.onActivityResult(requestCode, resultCode, data);
            selectedImage.setImageBitmap(photo);
        }

        assert data != null;
        final Uri photo = data.getData();

        // Selecting picture from gallery
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            selectedImage.setImageURI(photo);
        }

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send user taken/selected image to PhotoLibraryImageAdapter


                Intent i = new Intent(getApplicationContext(), PhotoLibraryImageAdapter.class);
                //Intent intent = new Intent(getApplicationContext(), PhotoLibraryImageAdapter.class);
                i.putExtra("CHOSEN_IMAGE", photo);
                startActivity(i);



            }
        });
    }


}
