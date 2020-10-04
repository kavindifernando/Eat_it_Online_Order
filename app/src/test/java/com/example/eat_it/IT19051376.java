package com.example.eat_it;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class IT19051376 {
    private dr_delivery_accept dr_delivery_accepts;

    @Before
    public void setUp() {
        dr_delivery_accepts = new dr_delivery_accept();
    }

    @Test
    public void Calculation() {

        float result1 = dr_delivery_accepts.Calculation(100, 10, 1, 4000);
        assertEquals(1040, result1, 0.01);

        float result2 = dr_delivery_accepts.Calculation(200, 5, 2, 3500);
        assertEquals(1070, result2, 0.01);

        float result3 = dr_delivery_accepts.Calculation(0, 6, 3, 4500);
        assertEquals(135, result3, 0.01);

        float result4 = dr_delivery_accepts.Calculation(150, 11, 4, 5000);
        assertEquals(1850, result4, 0.01);

        float result5 = dr_delivery_accepts.Calculation(50, 20, 5, 8000);
        assertEquals(1400, result5, 0.01);

        float result6 = dr_delivery_accepts.Calculation(60, 25, 6, 10000);
        assertEquals(2100, result6, 0.01);

        float result7 = dr_delivery_accepts.Calculation(70, 30, 7, 3000);
        assertEquals(2310, result7, 0.01);

        float result8 = dr_delivery_accepts.Calculation(10, 15, 8, 2000);
        assertEquals(310, result8, 0.01);


    }


}



