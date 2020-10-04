package com.example.eat_it.AdmRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.R;
import com.example.eat_it.Model.Driver;

import org.w3c.dom.Text;


import java.util.List;

public class Driver_RecyclerView_Config {

    private Context dContext;
    private DriversAdapter dDriverAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Driver> drivers, List<String> keys) {
        dContext = context;
        dDriverAdapter = new DriversAdapter(drivers, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(dDriverAdapter);

    }

    class DriverItemView extends RecyclerView.ViewHolder {
        private TextView dDriverName;
        private TextView dDriverEmail;
        private TextView dDriverPhone;

        private String key;


        //get drivers data
        public DriverItemView(ViewGroup parent) {
            super(LayoutInflater.from(dContext).
                    inflate(R.layout.adm_driver_list_item, parent, false));

            dDriverName = (TextView) itemView.findViewById(R.id.DriverName_txtView);
            dDriverEmail = (TextView) itemView.findViewById(R.id.DriverEmail_txtView);
            dDriverPhone = (TextView) itemView.findViewById(R.id.DriverPhone_txtView);

        }

        public void bind(Driver drivers, String key) {

            dDriverName.setText(drivers.getName());
            dDriverEmail.setText(drivers.getEmail());
            dDriverPhone.setText(drivers.getPhone());

            this.key = key;

        }
    }

    class DriversAdapter extends RecyclerView.Adapter<DriverItemView> {
        private List<Driver> dDriverList;
        private List<String> dKeys;


        public DriversAdapter(List<Driver> dDriverList, List<String> dKeys) {
            this.dDriverList = dDriverList;
            this.dKeys = dKeys;
        }

        @NonNull
        @Override
        public DriverItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DriverItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DriverItemView holder, int position) {
            holder.bind(dDriverList.get(position), dKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return dDriverList.size();
        }
    }
}
