package com.example.eat_it.AdmRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.AdmAddNewFoodActivity;
import com.example.eat_it.AdmFoodDetailsActivity;
import com.example.eat_it.R;
import com.example.eat_it.Model.Foods;


import org.w3c.dom.Text;

import java.util.List;

public class Food_RecyclerView_Config {

    private Context fContext;

    private FoodsAdapter fFoodAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Foods> foods, List<String> keys) {
        fContext = context;
        fFoodAdapter = new FoodsAdapter(foods, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(fFoodAdapter);

    }

    class FoodItemView extends RecyclerView.ViewHolder {
        private TextView fFoodName;
        private TextView fFoodDescription;
        private TextView fFoodPrice;

        private String key;
        private String fFoodCategory;
        private String fFoodDate;
        private String fFoodTime;
        private String fFoodId;
        private String fFoodImage;


        //get the food item details
        public FoodItemView(ViewGroup parent) {
            super(LayoutInflater.from(fContext).
                    inflate(R.layout.adm_food_list_item, parent, false));

            fFoodName = (TextView) itemView.findViewById(R.id.foodName_txtView);
            fFoodDescription = (TextView) itemView.findViewById(R.id.foodDescription_txtView);
            fFoodPrice = (TextView) itemView.findViewById(R.id.foodPrice_txtView);

            //click any food in food view and go maintain page
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(fContext, AdmFoodDetailsActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("fname", fFoodName.getText().toString());
                    intent.putExtra("description", fFoodDescription.getText().toString());
                    intent.putExtra("price", fFoodPrice.getText().toString());

                    //lllllllllllllll
                    intent.putExtra("category", fFoodCategory);
                    intent.putExtra("date", fFoodDate);
                    intent.putExtra("time", fFoodTime);
                    intent.putExtra("fid", fFoodId);
                    intent.putExtra("image", fFoodImage);

                    fContext.startActivity(intent);
                }
            });
        }

        public void bind(Foods foods, String key) {

            fFoodName.setText(foods.getFname());
            fFoodDescription.setText(foods.getDescription());
            fFoodPrice.setText(foods.getPrice());

            fFoodCategory = (foods.getCategory());
            fFoodDate = (foods.getDate());
            fFoodTime = (foods.getTime());
            fFoodId = (foods.getFid());
            fFoodImage = (foods.getImage());

            this.key = key;

        }
    }

    class FoodsAdapter extends RecyclerView.Adapter<FoodItemView> {
        private List<Foods> fFoodList;
        private List<String> fKeys;


        public FoodsAdapter(List<Foods> fFoodList, List<String> fKeys) {
            this.fFoodList = fFoodList;
            this.fKeys = fKeys;
        }

        @NonNull
        @Override
        public FoodItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FoodItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodItemView holder, int position) {
            holder.bind(fFoodList.get(position), fKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return fFoodList.size();
        }
    }
}
