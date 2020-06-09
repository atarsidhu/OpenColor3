package colorcoded.example.opencolor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opencolor.R;

public class Results extends AppCompatActivity {

    TextView tvResults;
    Button homeButton;
    Button tryOwnImage;
    ImageView sampleImage;
    int[] correctAnswerArr;
    int correct;
    String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvResults = findViewById(R.id.tvResults);
        homeButton = findViewById(R.id.btnHome);
        tryOwnImage = findViewById(R.id.btnTryOwnImage);
        sampleImage = findViewById(R.id.imgSample);
        correctAnswerArr = correctAnswers();
        resultText = "";

        compareAnswers();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        tryOwnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Picture.class);
                startActivity(startIntent);
            }
        });
    }

    public void compareAnswers(){
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int[] usr = bundle.getIntArray("USER_ANSWERS");

        for (int i = 0; i < correctAnswerArr.length; i++){
            assert usr != null;
            if(usr[i] == correctAnswerArr[i])
                correct++;
        }

        // Modified version of Stanford's analysis of results for a simplified version of the test
        // https://web.stanford.edu/group/vista/wikiupload/0/0a/Ishihara.14.Plate.Instructions.pdf
        if(correct > 7){
            resultText = "You scored: " + correct + "/10" +
                    "\n\nBased on your answers, you do not have red-green color vision deficiency";
            sampleImage.setImageResource(R.drawable.sample_color);
        }
        else
            resultText = "You scored: " + correct + "/10" +
                    "\n\nBased on your answers, you may have some form of red-green color vision deficiency";

        tvResults.setText(resultText);
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
