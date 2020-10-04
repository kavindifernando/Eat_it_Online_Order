package com.example.eat_it.AdmListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.eat_it.AdmDatabaseHelper.AdminCustomerDatabaseHelper;
import com.example.eat_it.AdmDatabaseHelper.AdminDriverDatabaseHelper;
import com.example.eat_it.AdmRecyclerView.Customer_RecyclerView_Config;
import com.example.eat_it.AdmRecyclerView.Driver_RecyclerView_Config;
import com.example.eat_it.R;
import com.example.eat_it.Model.Customer;
import com.example.eat_it.Model.Driver;

import java.util.List;

public class AdmCustomerListViewActivity extends AppCompatActivity {

    private RecyclerView cRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_customer_list_view);

        cRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_customers);
        new AdminCustomerDatabaseHelper().readCustomers(new AdminCustomerDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Customer> customers, List<String> keys) {

                findViewById(R.id.customer_progressBar).setVisibility(View.GONE);

                new Customer_RecyclerView_Config().setConfig(cRecyclerView, AdmCustomerListViewActivity.this, customers, keys);
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