package colorcoded.opencolor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    TextView tvStatement;
    TextView tvScore;
    Button tryOwnImage;
    BottomNavigationView bottomNavigationView;
    int[] correctAnswerArr;
    int[] testImages;
    ArrayList<Integer> incorrectImageArr;
    int correct;

    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvStatement = findViewById(R.id.tvStatement);
        tvScore = findViewById(R.id.tvScore);
        tryOwnImage = findViewById(R.id.btnTryOwnImage);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_test);
        incorrectImageArr = new ArrayList<>();
        testImages = new int[10];
        viewPager2 = findViewById(R.id.viewPager);

        correctAnswerArr = correctAnswers();

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

        tryOwnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Picture.class);
                startActivity(startIntent);
            }
        });

        compareAnswers();
        getScore();
        presentIncorrectTestImage();
    }

    public void presentIncorrectTestImage(){
        List<SliderItem> sliderItems = new ArrayList<>();

        if(correct < 10)
            for (Integer integer : incorrectImageArr) sliderItems.add(new SliderItem(integer));

            //for (int i = 0; i < incorrectImageArr.size(); i++)
        //sliderItems.add(new SliderItem(incorrectImageArr.get(i)));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        viewPager2.setPageTransformer(compositePageTransformer);
    }

    public void compareAnswers(){
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        int[] wrongImage = bundle.getIntArray("ARRAY");
        int[] userAnswers = bundle.getIntArray("USER_ANSWERS");

        for (int i = 0; i < correctAnswerArr.length; i++){
            assert userAnswers != null;
            if(userAnswers[i] == correctAnswerArr[i])
                correct++;
            else {
                assert wrongImage != null;
                incorrectImageArr.add(wrongImage[i]);
            }
        }
    }

    public void getScore(){
        tvScore.setText(getString(R.string.score, correct));

        // Modified version of Stanford's "analysis of results" for a simplified version of the test
        // https://web.stanford.edu/group/vista/wikiupload/0/0a/Ishihara.14.Plate.Instructions.pdf
        if(correct > 7)
            tvStatement.setText(R.string.no_color_deficiency);
        else
            tvStatement.setText(R.string.color_deficiency);
    }

    public int[] correctAnswers(){
        int[] ans = new int[10];
        ans[0] = 12;
        ans[1] = 8;
        ans[2] = 29;
        ans[3] = 5;
        ans[4] = 74;
        ans[5] = 45;
        ans[6] = 7;
        ans[7] = 16;
        ans[8] = 35;
        ans[9] = 96;
        return ans;
    }
}
