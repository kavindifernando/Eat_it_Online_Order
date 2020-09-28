package com.example.eat_it;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.eat_it_onilne_order.CartList;
import com.example.eat_it.Prevalent.Prevalent;
import com.example.eat_it.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
   private RecyclerView.LayoutManager layoutManager;
    private Button confirmToCartAll;
    private TextView textTotalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        confirmToCartAll =(Button) findViewById(R.id.add_to_cart_button);
        textTotalAmount = (TextView) findViewById(R.id.Total_Price_k);
    }
    @Override
    protected void onStart() {

        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");
        FirebaseRecyclerOptions<CartList> options= new FirebaseRecyclerOptions.Builder<CartList>()
                .setQuery(cartListRef.child("Customer View").child(Prevalent.currentOnlineCustomer.getPhone()).child("Foods"), CartList.class)
                .build();
        FirebaseRecyclerAdapter<CartList, CartViewHolder> adapter= new FirebaseRecyclerAdapter<CartList, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartList model) {
                holder.txtCartFoodName.setText(model.getFname());
                holder.txtCartFoodPrice.setText(model.getPrice());
                holder.txtCartFoodQuantity.setText(model.getQuantity());
                //Picasso.get().load(Foods.getImage()).into(foodImageView);
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=getLayoutInflater().from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}