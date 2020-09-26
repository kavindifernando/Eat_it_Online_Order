package com.example.eat_it.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat_it.Interface.ItemClickListner;
import com.example.eat_it.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtproductName,txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public  ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=(ImageView) itemView.findViewById(R.id.product_image);
        txtproductName=(TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription=(TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice=(TextView) itemView.findViewById(R.id.product_price);
    }
    public void itemClickListner(ItemClickListner listner){
        this.listner= listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
