package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AdmHomeActivity extends AppCompatActivity {

    private ImageButton FoodItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_home);

        FoodItemBtn = (ImageButton) findViewById(R.id.HomeFoodimageButton);

        FoodItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmHomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}