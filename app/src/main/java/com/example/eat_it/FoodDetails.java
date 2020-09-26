package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.eat_it.Model.Foods;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {
    // private FloatingActionButton addToCart;
    private ImageView productDetailsImage;
    private ElegantNumberButton numberButton1;
    private TextView productNameDetails,productDescriptionDetails,productPriceDetails;
    private String foodID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        foodID=getIntent().getStringExtra("fid");

        //addToCart = (FloatingActionButton) findViewById(R.id.add_foods_To_cart);
        numberButton1 =(ElegantNumberButton) findViewById(R.id.number_btn);
        productDetailsImage=(ImageView) findViewById(R.id.product_details_image);
        productNameDetails =(TextView) findViewById(R.id.product_details_name);
        productDescriptionDetails =(TextView)findViewById(R.id.product_details_description);
        productPriceDetails =(TextView)findViewById(R.id.product_details_price);

        getFoodDetails(foodID);
    }

    private void getFoodDetails(String foodID) {
        DatabaseReference FoodDetailsRef= FirebaseDatabase.getInstance().getReference().child("Foods");
        FoodDetailsRef.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Foods foods=snapshot.getValue(Foods.class);
                    productNameDetails.setText(foods.getFname());
                    productDescriptionDetails.setText(foods.getDescription());
                    productPriceDetails.setText(foods.getPrice());
                    Picasso.get().load(foods.getImage()).into(productDetailsImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}