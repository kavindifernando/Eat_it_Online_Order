package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eat_it.AdmDatabaseHelper.AdminFoodDatabaseHelper;
import com.example.eat_it.AdmListView.AdmFoodListViewActivity;
import com.example.eat_it.Model.Foods;

import java.util.List;

public class AdmFoodDetailsActivity extends AppCompatActivity {

    private EditText fFoodName_editTxt;
    private EditText fFoodDescription_editTxt;
    private EditText fFoodPrice_editTxt;

    private Button fUpdate_btn;
    private Button fDelete_btn;
    private Button ad_maintain_back_btn;

    private String key;
    private String fname;
    private String description;
    private String price;

    //lllllllllll
    private String category;
    private String date;
    private String time;
    private String image;
    private String fid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_food_details);

        key = getIntent().getStringExtra("key");
        fname = getIntent().getStringExtra("fname");
        description = getIntent().getStringExtra("description");
        price = getIntent().getStringExtra("price");

        category = getIntent().getStringExtra("category");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        image = getIntent().getStringExtra("image");
        fid = getIntent().getStringExtra("fid");


        fFoodName_editTxt = (EditText) findViewById(R.id.adm_maintain_foodName_ediTxt);
        fFoodName_editTxt.setText(fname);
        fFoodDescription_editTxt = (EditText) findViewById(R.id.adm_maintain_description_ediTxt2);
        fFoodDescription_editTxt.setText(description);
        fFoodPrice_editTxt = (EditText) findViewById(R.id.adm_maintain_price_ediTxt);
        fFoodPrice_editTxt.setText(price);


        fUpdate_btn = (Button) findViewById(R.id.adm_update_food_btn);
        fDelete_btn = (Button) findViewById(R.id.adm_delete_food_btn);
        ad_maintain_back_btn = (Button) findViewById(R.id.ad_maintain_back_btn);

        ad_maintain_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmFoodDetailsActivity.this, AdmFoodListViewActivity.class);
                Toast.makeText(AdmFoodDetailsActivity.this, "Back to Category view", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        fUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Foods foods = new Foods();
                foods.setFname(fFoodName_editTxt.getText().toString());
                foods.setDescription(fFoodDescription_editTxt.getText().toString());
                foods.setPrice(fFoodPrice_editTxt.getText().toString());

                foods.setCategory(category);
                foods.setDate(date);
                foods.setTime(time);
                foods.setFid(fid);
                foods.setImage(image);


                new AdminFoodDatabaseHelper().updateFoods(key, foods, new AdminFoodDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Foods> foods, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                        Toast.makeText(AdmFoodDetailsActivity.this, "Food Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        fDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AdminFoodDatabaseHelper().deleteFoods(key, new AdminFoodDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Foods> foods, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                        Toast.makeText(AdmFoodDetailsActivity.this, "Food Record has been Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });
            }
        });


    }


}