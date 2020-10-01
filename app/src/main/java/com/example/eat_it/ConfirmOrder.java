package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eat_it.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrder extends AppCompatActivity {
private EditText nameEditText,phoneEditText,addressEditText,postalEditText,emailEditText;
private Button confirmAllButton;
private  String totalPrice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        totalPrice =getIntent().getStringExtra("Total Price");
        nameEditText =(EditText) findViewById(R.id.Crt_Customer_name_1);
        phoneEditText = (EditText) findViewById(R.id.Crt_Customer_Contact_1);
        addressEditText =(EditText) findViewById(R.id.Crt_Customer_Address_1);
        postalEditText = (EditText) findViewById(R.id.Crt_Customer_postal_1);
        emailEditText =(EditText) findViewById(R.id.Crt_Customer_email_1);
        confirmAllButton = (Button) findViewById(R.id.button2_ccc);

        confirmAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(ConfirmOrder.this,"Please Provide Name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(ConfirmOrder.this,"Please Provide Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(ConfirmOrder.this,"Please Provide  Delivery Address",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(ConfirmOrder.this,"Please Provide An Email Address",Toast.LENGTH_SHORT).show();
        }
        else
            ConfirmedOrder();
    }

    private void ConfirmedOrder() {
        final String saveCurrentTime;
        final String saveCurrentDate;
        final String driverName= "none";
        final String status1;
        Calendar calForDate= Calendar.getInstance() ;

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentDate.format(calForDate.getTime());

        final DatabaseReference finalOrderListRef=FirebaseDatabase.getInstance().getReference().child("Order List")
                .child(Prevalent.currentOnlineCustomer.getPhone());
        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("total Amount",totalPrice);
        orderMap.put("customer Name",nameEditText.getText().toString());
        orderMap.put("phone Number",phoneEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("postal code",postalEditText.getText().toString());
        orderMap.put("email",emailEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("driver name",driverName);
        orderMap.put("status1","not Shipped");

        finalOrderListRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("CartList")
                            .child("Customer View")
                            .child(Prevalent.currentOnlineCustomer.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ConfirmOrder.this,"Order Placed Successfully!",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmOrder.this, Menu.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                }
                            });
                }
            }
        });

    }
}