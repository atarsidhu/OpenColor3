package colorcoded.opencolor;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit testicon, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainUnitTests {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //Testing the results of the color blind test
    @Test
    public void testingTestPage(){

        //Testing there are three pictures loaded for the test
        ColorBlindTest test = new ColorBlindTest();
        int[] images = test.setupTestImages();
        assertEquals(10, images.length);
        Assert.assertEquals(images[0], R.drawable.colorblindtest01);
        assertEquals(images[1], R.drawable.colorblindtest02);
        assertEquals(images[2], R.drawable.colorblindtest03);

    }

    //Testing the results are accurate against the documentation
    @Test
    public void testingResults(){

        //setup variables for testing
        Results results = new Results();
        results.correctAnswerArr = results.correctAnswers();
        results.incorrectImageArr = new ArrayList<>();
        assertEquals(-1, results.compareAnswers(null, null, null, null));
        int[] input = loadNormalList();
        assertEquals(-1, results.compareAnswers(input, null, results.correctAnswerArr, results.incorrectImageArr));
        int[] fakeImageRef = new int[10];
        results.loadRandom(fakeImageRef, 10, 100);
        assertEquals(10, results.compareAnswers(input, fakeImageRef, results.correctAnswerArr, results.incorrectImageArr));
        results.loadRandom(input, 100, 200);
        assertEquals(0, results.compareAnswers(input, fakeImageRef, results.correctAnswerArr, results.incorrectImageArr));
        results.compareIntArrays(fakeImageRef, results.incorrectImageArr);

    }

    public int[] loadNormalList(){
        int[] list = new int[10];
        list[0] = 12;
        list[1] = 8;
        list[2] = 29;
        list[3] = 5;
        list[4] = 74;
        list[5] = 45;
        list[6] = 7;
        list[7] = 16;
        list[8] = 35;
        list[9] = 96;
        return list;
    }

}