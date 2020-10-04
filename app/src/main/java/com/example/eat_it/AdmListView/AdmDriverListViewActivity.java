package com.example.eat_it.AdmListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.eat_it.AdmDatabaseHelper.AdminDriverDatabaseHelper;
import com.example.eat_it.AdmRecyclerView.Driver_RecyclerView_Config;
import com.example.eat_it.R;
import com.example.eat_it.Model.Driver;


import java.util.List;

public class AdmDriverListViewActivity extends AppCompatActivity {

    private RecyclerView dRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_driver_list_view);

        dRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_drivers);
        new AdminDriverDatabaseHelper().readDrivers(new AdminDriverDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Driver> drivers, List<String> keys) {

                findViewById(R.id.driver_progressBar).setVisibility(View.GONE);

                new Driver_RecyclerView_Config().setConfig(dRecyclerView, AdmDriverListViewActivity.this, drivers, keys);
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