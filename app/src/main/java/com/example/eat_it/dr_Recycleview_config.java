package com.example.eat_it;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.Model.delivery_list;
import com.example.eat_it.dr_delivery_accept;

import java.util.List;

public class dr_Recycleview_config {
    private Context mContext;
    private deliveryListAdapter drAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<delivery_list> dlist, List<String> keys) {
        mContext = context;
        drAdapter = new deliveryListAdapter(dlist, keys);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(drAdapter);

    }


    class drListView extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private TextView address;
        private TextView payStatus;
        private TextView postCode;
        private TextView date;
        private TextView status;
        private TextView amount;


        private String key;

        public drListView(ViewGroup parent) {

            super(LayoutInflater.from(mContext).inflate(R.layout.dr_deliverylist_item, parent, false));

            name = (TextView) itemView.findViewById(R.id.txt_name);
            phone = (TextView) itemView.findViewById(R.id.txt_phone);
            address  = (TextView) itemView.findViewById(R.id.txt_address);
            payStatus = (TextView) itemView.findViewById(R.id.txt_payStatus);
            postCode = (TextView) itemView.findViewById(R.id.txt_postalcode);
            date  = (TextView) itemView.findViewById(R.id.txt_date);
            status = (TextView) itemView.findViewById(R.id.txt_status);
            amount = (TextView) itemView.findViewById(R.id.txt_amount);



            //send card viev delivery data to deliver accept page
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, dr_delivery_accept.class);
                    intent.putExtra("key", key);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("phone", phone.getText().toString());
                    intent.putExtra("address", address.getText().toString());
                    intent.putExtra("payStatus", payStatus.getText().toString());
                    intent.putExtra("postCode", postCode.getText().toString());
                    intent.putExtra("date", date.getText().toString());
                    intent.putExtra("status", status.getText().toString());
                    intent.putExtra("amount", amount.getText().toString());

                    mContext.startActivity(intent);

                }
            });


        }


        public void bind(delivery_list del, String key) {


                name.setText(del.getCustomer_Name());
                phone.setText(del.getPhone_Number());
                address.setText(del.getAddress());
                payStatus.setText(del.getPayment_Status());
                postCode.setText(del.getPostal_code());
                date.setText(del.getDate());
                status.setText(del.getStatus1());
                amount.setText(del.getTotal_Amount());

                this.key = key;

        }
    }


    class deliveryListAdapter extends RecyclerView.Adapter<drListView> {
        private List<delivery_list> drList;
        private List<String> drkeys;

        public deliveryListAdapter(List<delivery_list> drList, List<String> drkeys) {
            this.drList = drList;
            this.drkeys = drkeys;
        }


        @Override
        public drListView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new drListView(parent);
        }

        @Override
        public void onBindViewHolder(drListView holder, int position) {
            holder.bind(drList.get(position), drkeys.get(position));
        }

        @Override
        public int getItemCount() {

            return drList.size();
        }
    }


}
