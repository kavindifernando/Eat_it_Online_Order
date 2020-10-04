package com.example.eat_it;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.Model.dr_conf_list;

import java.util.List;

public class dr_confirm_list extends AppCompatActivity {

    private RecyclerView mrecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_confirm_list);

        mrecyclerView = (RecyclerView) findViewById(R.id.dr_deliverList);
        new dr_helper2().getDeliveryList(new dr_helper2.DataStatus() {
            @Override
            public void DataIsLoaded(List<dr_conf_list> deList, List<String> key) {
                new dr_recycler_view2().setConfig(mrecyclerView, dr_confirm_list.this,deList,key);
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
