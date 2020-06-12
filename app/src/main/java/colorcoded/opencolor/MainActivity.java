package colorcoded.opencolor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    //  SAVE IMAGE AND SEND TO LIBRARY
    //  OPTIONAL: IF TEST ALREADY TAKEN AND USER SELECTS TEST BUTTON, PRESENT RESULTS PAGE BUT ASK FOR REDO TEST?
    //  HOME PAGE IS A LITTLE REDUNDANT. FIND A PURPOSE FOR IT OR REPLACE IT WITH SOMETHING ELSE
    //  OPTIONAL: CUSTOM BUTTONS
    //  PYTHON: ADJUST THE INCORRECT IMAGES ON RESULTS PAGE
    //  ISSUE: WHEN YOU FIRST OPEN THE APP AND SELECT 'TAKE A PICTURE', IT ASKS FOR THE USERS PERMISSION. AFTER TAPPING 'ALLOW', THE USER
    //   IS REDIRECTED TO THE HOME SCREEN WHICH SHOULD NOT HAPPEN.

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
                    case R.id.nav_library:
                        startActivity(new Intent(getApplicationContext(), PhotoLibrary.class));
                        overridePendingTransition(0,0);
                        return true;
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
