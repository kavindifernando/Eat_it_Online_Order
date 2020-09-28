package com.example.eat_it.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.Interface.ItemClickListner;
import com.example.eat_it.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtCartFoodName,txtCartFoodPrice,txtCartFoodQuantity;
    public ImageView foodImageView;
    public ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        foodImageView=(ImageView) itemView.findViewById(R.id.cart_food_image);
        txtCartFoodName=(TextView) itemView.findViewById(R.id.cart_food_name);
        txtCartFoodPrice=(TextView) itemView.findViewById(R.id.cart_food_price);
        txtCartFoodQuantity=(TextView) itemView.findViewById(R.id.cart_food_quantity);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner= itemClickListner;
    }
}
