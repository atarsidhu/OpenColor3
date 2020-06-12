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

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        //Compare answers
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int[] blah = bundle.getIntArray("ARRAY");
        int[] usr = bundle.getIntArray("USER_ANSWERS");

        correct = compareAnswers(usr, blah, correctAnswerArr, incorrectImageArr);
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

    /**
     * Compares the integers inside input against the integers inside correctList. The total amount of matching values (found in order) is returned, and a list of the incorrect comparisons are passed into incorrectlist
     * @param input - The input list to be compared
     * @param imgRef - The image references of the test photos to be passed into incorrectList
     * @param correctList - The array to compare against
     * @param incorrectList - The list of images from imgRef that are incorrect, in correlation with incorrect matching of input.
     * @return -1 if input or imgRef are null
     * @return count - the amount of matches between input and correctList, considering that ordering is important
     */
    public int compareAnswers(int[] input, int[] imgRef, int[] correctList, ArrayList<Integer> incorrectList){

        int count = 0;

        //Input integrity check
        if (input == null){
            return -1;
        }
        if (imgRef == null){
            return -1;
        }

        for (int i = 0; i < correctList.length; i++){

            if(input[i] == correctList[i])

                count++;

            else {

                incorrectList.add(imgRef[i]);
            }
        }
        /* Potential conflict that I didnt merge. Keeping it here until I know the function still works.
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

         */
        return count;
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

    public void loadRandom(int[] array, int min, int max){

        final Random random = new Random();

        for(int i = 0; i < array.length; i++){
            array[i] = random.nextInt((max - min) + 1) + min;
        }
    }

    public int compareIntArrays(int[] firstArray, ArrayList<Integer> secondArray){

        int count = 0;

        if (firstArray.length != secondArray.size()){
            return -1;
        }
        for(int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray.size(); j++) {
                if (firstArray[i] == secondArray.indexOf(j)) {
                    count++;
                }
            }
        }
        return count;
    }

}
