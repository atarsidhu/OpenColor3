package colorcoded.opencolor;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import colorcoded.opencolor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView grayscaleImg;
    ImageView colorImg;
    ImageView circleAdjustMid;
    ImageView circleAdjustTop;
    ImageView circleAdjustBot;
    BottomNavigationView bottomNavigationView;

    // TODO:
    //  LIBRARY CLASS
    //  FIX RESULTS PAGE
    //  IMPLEMENT SAVE IMAGE BUTTON AND FUNCTION IN PICTURE CLASS AFTER LIBRARY CLASS COMPLETE
    //  IF TEST ALREADY TAKEN AND USER SELECTS TEST BUTTON PRESENT RESULTS PAGE BUT ASK FOR REDO TEST?
    //  HOME PAGE IS A LITTLE REDUNDANT. FIND A PURPOSE FOR IT OR REPLACE IT WITH SOMETHING ELSE
    //  CUSTOM BUTTONS
    //  TEST IMAGES THAT WERE INCORRECTLY GUESSED SHOULD BE PLACED ON RESULTS PAGE WITH ABILITY TO REFINE COLOURS SO USER CAN SEE THEM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grayscaleImg = findViewById(R.id.imgGrayscale);
        colorImg = findViewById(R.id.imgColor);
        circleAdjustMid = findViewById(R.id.imgBlackCircleMid);
        circleAdjustTop = findViewById(R.id.imgBlackCircleTop);
        circleAdjustBot = findViewById(R.id.imgBlackCircleBot);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_camera:
                        startActivity(new Intent(getApplicationContext(), Picture.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_test:
                        startActivity(new Intent(getApplicationContext(), ColorBlindTest.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        return true;
                    //library case
                }
                return false;
            }
        });

        // Sample picture from grayscale to colour
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        grayscaleImg.startAnimation(fadeOut);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        colorImg.startAnimation(fadeIn);

        // Middle circle adjust
        Animation adjustToRightMid = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        circleAdjustMid.startAnimation(adjustToRightMid);

        final Animation adjustToLeft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                circleAdjustMid.startAnimation(adjustToLeft);
            }
        }, 2000);

        // Top circle adjust
        Animation adjustToLeftTop = AnimationUtils.loadAnimation(this, R.anim.righttoleft_top);
        circleAdjustTop.startAnimation(adjustToLeftTop);

        final Animation adjustToRightTop = AnimationUtils.loadAnimation(this, R.anim.lefttoright_top);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                circleAdjustTop.startAnimation(adjustToRightTop);
            }
        }, 2000);

        // Bottom circle adjust
        circleAdjustBot.startAnimation(adjustToLeftTop);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                circleAdjustBot.startAnimation(adjustToRightTop);
            }
        }, 2000);

    }

}
