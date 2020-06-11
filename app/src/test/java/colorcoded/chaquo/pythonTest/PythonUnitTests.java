package colorcoded.chaquo.pythonTest;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit testicon, which will execute on the development machine (host).
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PythonUnitTests {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public void pythonWorks(){
        Python py = Python.getInstance();
        PyObject py_test = py.getModule("ColorShift");
        py_test.callAttr("py_print", "Chaquopy").toString();
        PyObject modifiedImage = py_test.callAttr("shiftImage", "colorBlindTestImage.png", "rightshift",1.5,"green","rightshift",1,"red");
        assertEquals(120, py_test.callAttr("pullPixel", modifiedImage, 10, 10));
    }

}