package com.coverteam.pta;



import org.junit.Test;


import static org.junit.Assert.*;

import com.coverteam.pta.tools.CustomMask;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public  void  formatExample(){
        String teks = "111111111111111111";
//        String teks = "0000000000000000";
        String format  = CustomMask.formatNIP(teks);
        System.out.println(format);
    }

}