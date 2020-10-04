package com.example.eat_it;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IT19059846 {

    private Cart cart;

    @Before
    public void setUp(){
        cart = new Cart();
    }
    @Test
    public void CalculateTotalPriceOfCart(){

        double res1 = cart.CalculateTotalPriceOfCart(100,2);
        assertEquals(200,res1,0.001);

        double res2 = cart.CalculateTotalPriceOfCart(1,0);
        assertEquals(0,res2,0.001);

        double res3 = cart.CalculateTotalPriceOfCart((float) 1.0,0);
        assertEquals(0,res3,0.001);

        double res4 = cart.CalculateTotalPriceOfCart((float) 230.0,2);
        assertEquals(460,res4,0.001);

        double res5 = cart.CalculateTotalPriceOfCart((float) 230.0, (float) 2.0);
        assertEquals(460.0,res5,0.001);

    }

}
