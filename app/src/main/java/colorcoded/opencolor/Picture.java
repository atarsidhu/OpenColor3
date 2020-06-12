package colorcoded.opencolor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Picture extends AppCompatActivity {

    private static final String TAG = "0";
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
    public static final Python py = Python.getInstance();
    private Object RenderScriptImageEdit;

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
        //saveImage = findViewById(R.id.btnSaveImage);
        openCameraBtn = findViewById(R.id.imgBtnOpenCamera);
        choosePictureBtn = findViewById(R.id.imgBtnGallery);
    }

    public String pythonText(String text){
        PyObject py_test = py.getModule("test");
        PyObject py_output = py_test.callAttr("py_print", text);
        return py_output.toJava(String.class);
    }

    private void savePythonImage(Bitmap bitmap, String imageName){
        try {
            File path = new File(Picture.this.getFilesDir().getAbsolutePath(), "chaquopy" + File.separator + "assetfinder" + File.separator + "app");
            if(!path.exists()){
                path.mkdirs();
            }
            File outFile = new File(path, imageName + ".png");
            FileOutputStream outputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Saving received message failed with", e);

        } catch (IOException e) {
            Log.e(TAG, "Saving received message failed with", e);
        }
    }

    private Bitmap loadPythonImage(String path){


        return BitmapFactory.decodeFile(Picture.this.getFilesDir().getAbsolutePath() + "chaquopy" + File.separator + "assetfinder" + File.separator + "app" + File.separator + path);
        //File f=new File(yourdir, imagename);
        //Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArray){

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void modifyImage(ImageView image){

        PyObject py_colorshift = py.getModule("colorshift");
        PyObject py_test = py.getModule("test");

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();

        savePythonImage(bitmap, "testImage");

        byte[] byteArray = convertBitmapToByteArray(bitmap);
        PyObject array1 = PyObject.fromJava(byteArray);

        byte[] result = py_test.callAttr("pic_func", array1).toJava(byte[].class);

        Bitmap newBitmap = convertByteArrayToBitmap(result);
        newBitmap = doColorFilter(newBitmap, 0.9, 1.1, 1.1);



        //PyObject py_shiftImage = py_colorshift.callAttr("shiftImage", "testImage.png", "leftshift", 2, "red", "leftshift", 2, "red");

        //bitmap = loadPythonImage("testImage.png");

        image.setImageBitmap(newBitmap);

        //return py_shiftImage.toJava(ImageView.class);

    }

    public static Bitmap doColorFilter(Bitmap src, double red, double green, double blue) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * red);
                G = (int)(Color.green(pixel) * green);
                B = (int)(Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    public void createListeners(){
        processImageClicked = new View.OnClickListener(){

            //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v){
                processImage.setText(pythonText("Process More"));
                modifyImage(selectedImage);
            }
        };

        cameraClicked = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (startIntent.resolveActivity(getPackageManager()) != null) {
                    if (ContextCompat.checkSelfPermission(pictureActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        processImage.setText(pythonText("Process Image"));
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
                processImage.setText(pythonText("Process Image"));
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

        /*saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send user taken/selected image to PhotoLibraryImageAdapter


                Intent i = new Intent(getApplicationContext(), PhotoLibraryImageAdapter.class);
                //Intent intent = new Intent(getApplicationContext(), PhotoLibraryImageAdapter.class);
                i.putExtra("CHOSEN_IMAGE", photo);
                startActivity(i);
            }
        });*/
    }


}
