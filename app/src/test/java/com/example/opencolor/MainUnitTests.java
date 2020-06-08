package com.example.opencolor;

import org.junit.Test;

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
    public void testingResultsPage(){

        //Testing there are three pictures loaded for the test
        ColorBlindTest test = new ColorBlindTest();
        int[] images = test.setupTestImages();
        assertEquals(3, images.length);
        assertEquals(images[0], R.drawable.test1);
        assertEquals(images[1], R.drawable.test2);
        assertEquals(images[2], R.drawable.test3);
    }

    //Testing the picture display page
    @Test
    public void testingPicturePage(){

        //make sure all the resources load properly
        Picture test = new Picture();
        //assertEquals(1, test.associateResources());
    }

}