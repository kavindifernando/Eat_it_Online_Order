package com.example.eat_it;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class IT19038292 {

    private AdmFoodDetailsActivity admFoodDetailsActivity;

    @Before
    public void setUp(){
        admFoodDetailsActivity = new AdmFoodDetailsActivity();
    }

    @Test
    public void discount(){
        int result1  = admFoodDetailsActivity.discount(10,150);
        assertEquals(135,result1);

        int result2  = admFoodDetailsActivity.discount(25,1700);
        assertEquals(1275,result2);

        int result3  = admFoodDetailsActivity.discount(50,60);
        assertEquals(30,result3);

        int result4  = admFoodDetailsActivity.discount(15,420);
        assertEquals(357,result4);

        int result5  = admFoodDetailsActivity.discount(20,180);
        assertEquals(144,result5);

        int result6  = admFoodDetailsActivity.discount(35,990);
        assertEquals(644,result6);

        int result7  = admFoodDetailsActivity.discount(10,1990);
        assertEquals(1791,result7);

        int result8  = admFoodDetailsActivity.discount(40,250);
        assertEquals(150,result8);

        int result9  = admFoodDetailsActivity.discount(10,599);
        assertEquals(540,result9);

        int result10  = admFoodDetailsActivity.discount(25,3200);
        assertEquals(2400,result10);
    }
}

