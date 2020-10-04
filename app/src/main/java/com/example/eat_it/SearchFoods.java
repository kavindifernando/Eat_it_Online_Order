package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.eat_it.Model.Foods;
import com.example.eat_it.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchFoods extends AppCompatActivity {
private EditText productName;
private Button SearchProduct;
private RecyclerView SearchList;
private String SearchInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_foods);

        productName=findViewById(R.id.productName);
        SearchProduct=findViewById(R.id.Search_for_product);
        SearchList=findViewById(R.id.searchList);
        SearchList.setLayoutManager(new LinearLayoutManager(SearchFoods.this));


        SearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput = productName.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Foods");
        FirebaseRecyclerOptions<Foods> options=new FirebaseRecyclerOptions.Builder<Foods>()
                .setQuery(reference.orderByChild("fname").startAt(SearchInput).endAt(SearchInput),Foods.class)
                .build();

        FirebaseRecyclerAdapter<Foods, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Foods, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Foods model) {
                holder.txtproductName.setText(model.getFname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = "+ model.getPrice() + "$");
                Picasso.get().load(model.getImage()).into(holder.imageView);
//                    food details part in menu
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(SearchFoods.this,FoodDetails.class);
                        intent.putExtra("fid",model.getFid());
                        startActivity(intent);
                    }
                });
//                    end in here
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_items_layout,parent,false);
                ProductViewHolder holder =new ProductViewHolder(view);
                return holder;
            }
        };
        SearchList.setAdapter(adapter);
        adapter.startListening();
    }
}