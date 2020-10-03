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

import com.example.eat_it.Model.Foods;
import com.example.eat_it.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Menu extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference FoodsRef;
    private RecyclerView recyclerView;
    private Button goToCartFromMenu;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FoodsRef= FirebaseDatabase.getInstance().getReference().child("Foods");
        recyclerView=findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        goToCartFromMenu=findViewById(R.id.go_to_cart2);
        goToCartFromMenu.setOnClickListener(this);

    }
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Foods> options =
                new FirebaseRecyclerOptions.Builder<Foods>()
                        .setQuery(FoodsRef,Foods.class)
                        .build();

        FirebaseRecyclerAdapter<Foods, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Foods, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, final int position, @NonNull final Foods model) {
                        holder.txtproductName.setText(model.getFname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = "+ model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
//                    food details part in menu
                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(Menu.this,FoodDetails.class);
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_to_cart2:
                Paper.book().destroy();
                Intent intent = new Intent(Menu.this, Cart.class);
                startActivity(intent);
                break;


            default:
        }
    }
}