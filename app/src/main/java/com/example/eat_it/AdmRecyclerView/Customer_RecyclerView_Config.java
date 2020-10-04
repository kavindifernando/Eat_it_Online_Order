package com.example.eat_it.AdmRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.R;
import com.example.eat_it.Model.Customer;

import org.w3c.dom.Text;

import java.util.List;

public class Customer_RecyclerView_Config {

    private Context cContext;

    private CustomersAdapter cCustomerAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Customer> customers, List<String> keys) {
        cContext = context;
        cCustomerAdapter = new CustomersAdapter(customers, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(cCustomerAdapter);

    }

    class CustomerItemView extends RecyclerView.ViewHolder {
        private TextView cCustomerName;
        private TextView cCustomerEmail;
        private TextView cCustomerPhone;

        private String key;


        //getting customers data
        public CustomerItemView(ViewGroup parent) {
            super(LayoutInflater.from(cContext).
                    inflate(R.layout.adm_customer_list_item, parent, false));

            cCustomerName = (TextView) itemView.findViewById(R.id.CustomerName_txtView);
            cCustomerEmail = (TextView) itemView.findViewById(R.id.CustomerEmail_txtView);
            cCustomerPhone = (TextView) itemView.findViewById(R.id.CustomerPhone_txtView);

        }

        public void bind(Customer customers, String key) {

            cCustomerName.setText(customers.getName());
            cCustomerEmail.setText(customers.getEmail());
            cCustomerPhone.setText(customers.getPhone());

            this.key = key;

        }
    }

    class CustomersAdapter extends RecyclerView.Adapter<CustomerItemView> {
        private List<Customer> cCustomerList;
        private List<String> cKeys;


        public CustomersAdapter(List<Customer> cCustomerList, List<String> cKeys) {
            this.cCustomerList = cCustomerList;
            this.cKeys = cKeys;
        }

        @NonNull
        @Override
        public CustomerItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomerItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerItemView holder, int position) {
            holder.bind(cCustomerList.get(position), cKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return cCustomerList.size();
        }
    }
}
