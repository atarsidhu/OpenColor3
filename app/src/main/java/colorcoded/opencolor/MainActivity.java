package colorcoded.opencolor;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import colorcoded.opencolor.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView startTestBtn;
    ImageView openCameraBtn;
    ImageView grayscaleImg;
    ImageView colorImg;
    ImageView circleAdjustMid;
    ImageView circleAdjustTop;
    ImageView circleAdjustBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grayscaleImg = findViewById(R.id.imgGrayscale);
        colorImg = findViewById(R.id.imgColor);
        circleAdjustMid = findViewById(R.id.imgBlackCircleMid);
        circleAdjustTop = findViewById(R.id.imgBlackCircleTop);
        circleAdjustBot = findViewById(R.id.imgBlackCircleBot);

        startTestBtn = findViewById(R.id.imgBtnTest);
        startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ColorBlindTest.class);
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
