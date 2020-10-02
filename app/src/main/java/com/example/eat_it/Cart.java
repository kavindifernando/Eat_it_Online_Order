package com.example.eat_it;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eat_it.Model.CartList;
import com.example.eat_it.Prevalent.Prevalent;
import com.example.eat_it.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
   private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessButton;
    private TextView textTotalAmount;
    private  int overTotalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessButton =(Button) findViewById(R.id.next_process_button);
        textTotalAmount = (TextView) findViewById(R.id.total_price);

//        confirmToCartAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               //textTotalAmount.setText("Total Price = "+ String.valueOf(overTotalPrice));
//                Intent intent = new Intent(Cart.this, ConfirmOrder.class);
//                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
//                startActivity(intent);
//                finish();
//            }
//      });
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
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final CartList model) {
                holder.txtCartFoodName.setText(model.getFname());
                holder.txtCartFoodPrice.setText(model.getPrice());
                holder.txtCartFoodQuantity.setText(model.getQuantity());
                //Picasso.get().load(Foods.getImage()).into(foodImageView);
               int oneTypeModelTotalPrice = ((Integer.parseInt(model.getPrice())) * Integer.parseInt(model.getQuantity()));
              overTotalPrice = overTotalPrice + oneTypeModelTotalPrice;


                holder.itemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        CharSequence option []=new CharSequence[]
                                {
                                        "Remove",
                                        "Delete"
                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(Cart.this);
                        builder.setTitle("Cart Options");
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                {
                                    Intent intent=new Intent (Cart.this,FoodDetails.class);
                                    intent.putExtra("fid",model.getFid());
                                    startActivity(intent);
                                }
                                if (i==1){
                                    cartListRef.child("Customer View")
                                            .child(Prevalent.currentOnlineCustomer.getPhone())
                                            .child("Foods")
                                            .child(model.getFid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(Cart.this,"removed Successfully",Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent (Cart.this,Cart.class);

                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }

                            }
                        });
                        builder.show();
                    }
                });
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