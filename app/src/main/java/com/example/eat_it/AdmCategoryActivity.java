package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eat_it.AdmListView.AdmFoodListViewActivity;

public class AdmCategoryActivity extends AppCompatActivity {

    //define variables
    private ImageView Pizza;
    private ImageView ShortEats;
    private ImageView Beverages;
    private ImageView Desserts;
    private Button MaintainBtn;
    private Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_category);

        Pizza = (ImageView) findViewById(R.id.ad_pizza);
        ShortEats = (ImageView) findViewById(R.id.ad_shortEats);
        Beverages = (ImageView) findViewById(R.id.ad_beverages);
        Desserts = (ImageView) findViewById(R.id.ad_desserts);
        MaintainBtn = (Button) findViewById(R.id.adm_maintain_food_btn);
        BackBtn = (Button) findViewById(R.id.ad_back_btn);

        //clickable Pizza image
        Pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "Pizza");
                startActivity(intent);
            }
        });
        //clickable shorteats image
        ShortEats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "ShortEats");
                startActivity(intent);
            }
        });
        //clickable beverage image
        Beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "Beverages");
                startActivity(intent);
            }
        });
        //clickable dessert image
        Desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmAddNewFoodActivity.class);
                intent.putExtra("category", "Desserts");
                startActivity(intent);
            }
        });
        //maintain button
        MaintainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmFoodListViewActivity.class);
                Toast.makeText(AdmCategoryActivity.this, "Click any card to Update or Delete Food", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        //back button
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmCategoryActivity.this, AdmHomeActivity.class);
                Toast.makeText(AdmCategoryActivity.this, "Back to Category view", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}