package com.example.eat_it;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.Model.delivery_list;

import java.util.List;

public class dr_delivery_list extends AppCompatActivity {

    private RecyclerView mrecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_delivery_list);

        mrecyclerView = (RecyclerView) findViewById(R.id.dr_deliverList);
        new dr_firebase_helper().getDeliveryList(new dr_firebase_helper.DataStatus() {
            @Override
            public void DataIsLoaded(List<delivery_list> deList, List<String> key) {
                new dr_Recycleview_config().setConfig(mrecyclerView, dr_delivery_list.this,deList,key);

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
