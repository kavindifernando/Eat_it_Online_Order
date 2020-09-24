package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdmCategoryActivity extends AppCompatActivity {

    private ImageView Pizza;
    private ImageView ShortEats;
    private ImageView Beverages;
    private ImageView Desserts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_category);

        Pizza = (ImageView) findViewById(R.id.ad_pizza);
        ShortEats = (ImageView) findViewById(R.id.ad_shortEats);
        Beverages = (ImageView) findViewById(R.id.ad_beverages);
        Desserts = (ImageView) findViewById(R.id.ad_desserts);

        Pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "Pizza");
                startActivity(intent);
            }
        });

        ShortEats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "ShortEats");
                startActivity(intent);
            }
        });


        Beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "Beverages");
                startActivity(intent);
            }
        });

        Desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "Desserts");
                startActivity(intent);
            }
        });
    }
}