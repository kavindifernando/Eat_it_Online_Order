package com.example.eat_it;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.Model.dr_conf_list;

import java.util.List;

public class dr_recycler_view2 {
    private Context mContext;
    private  dr_conf_listAdapter drAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<dr_conf_list> dlist, List<String> keys) {
        mContext = context;
        drAdapter = new dr_conf_listAdapter(dlist, keys);

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

            super(LayoutInflater.from(mContext).inflate(R.layout.dr_confirm_item, parent, false));

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


                }
            });


        }


        public void bind(dr_conf_list del, String key) {


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


    class dr_conf_listAdapter extends RecyclerView.Adapter<drListView> {
        private List<dr_conf_list> drList;
        private List<String> drkeys;

        public dr_conf_listAdapter(List<dr_conf_list> drList, List<String> drkeys) {
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

