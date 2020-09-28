package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.eat_it.Model.Foods;
import com.example.eat_it.Prevalent.Prevalent;
import com.example.eat_it.Model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class FoodDetails extends AppCompatActivity implements View.OnClickListener {
    // private FloatingActionButton addToCart;
    private Button addToCart,goToCart;
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
        addToCart = (Button) findViewById(R.id.add_to_cart_button);
        goToCart = (Button) findViewById(R.id.go_to_cart);
        numberButton1 =(ElegantNumberButton) findViewById(R.id.number_btn);
        productDetailsImage=(ImageView) findViewById(R.id.product_details_image);
        productNameDetails =(TextView) findViewById(R.id.product_details_name);
        productDescriptionDetails =(TextView)findViewById(R.id.product_details_description);
        productPriceDetails =(TextView)findViewById(R.id.product_details_price);
        addToCart.setOnClickListener(this);
        goToCart.setOnClickListener(this);
        getFoodDetails(foodID);



    }
    private void addingToCartList() {
        String saveCurrentTime;
        String saveCurrentDate;

        Calendar calForDate= Calendar.getInstance() ;

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String,Object> cartMap=new HashMap<>();

        cartMap.put("fid",foodID);
        cartMap.put("fname",productNameDetails.getText().toString());
        cartMap.put("price",productPriceDetails.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton1.getNumber());

        cartListRef.child("Customer View").child(Prevalent.currentOnlineCustomer.getPhone())
                //.child("Customer").child("phone")
                .child("Foods").child(foodID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineCustomer.getPhone())
                                    //.child("Customer").child("phone")
                                    .child("Foods").child(foodID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(FoodDetails.this,"Added to Cart",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(FoodDetails.this,Menu.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_to_cart_button:
                addingToCartList();
                break;
            case R.id.go_to_cart:
                Intent intent2 = new Intent(FoodDetails.this, Cart.class);
                startActivity(intent2);
                break;

            default:}
    }
}