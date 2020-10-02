package com.example.eat_it.AdmListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.eat_it.AdmDatabaseHelper.AdminFoodDatabaseHelper;
import com.example.eat_it.AdmRecyclerView.Food_RecyclerView_Config;
import com.example.eat_it.R;
import com.example.eat_it.Model.Foods;

import java.util.List;

public class AdmFoodListViewActivity extends AppCompatActivity {

    private RecyclerView fRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_food_list_view);

        fRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_foods);
        new AdminFoodDatabaseHelper().readFoods(new AdminFoodDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Foods> foods, List<String> keys) {

                findViewById(R.id.foods_progressBar).setVisibility(View.GONE);

                new Food_RecyclerView_Config().setConfig(fRecyclerView, AdmFoodListViewActivity.this, foods, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}