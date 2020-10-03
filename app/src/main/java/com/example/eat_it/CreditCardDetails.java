package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.le.AdvertiseData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.eat_it.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class CreditCardDetails extends AppCompatActivity implements View.OnClickListener{
private Button ConfirmFinalOrder;
    private EditText nameEditText1,numberEditText,expDateEditText,cvcEditText;

    private RadioButton radioButton1,radioButton2;
    private RadioGroup radioGroup1;
    private  String totalPrice="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_details);
        ConfirmFinalOrder=(Button)findViewById(R.id.confirm_button_payment);
        totalPrice =getIntent().getStringExtra("Total Price");
        nameEditText1 =(EditText) findViewById(R.id.creditCardOwnerName);
       numberEditText = (EditText) findViewById(R.id.creditCardNumber);
        expDateEditText =(EditText) findViewById(R.id.creditCardExpDate);
        cvcEditText = (EditText) findViewById(R.id.creditCardCVC);



        ConfirmFinalOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_button_payment:


                AddCreditCardDetails();
                break;


            default:
        }

    }

    private void AddCreditCardDetails() {
        final String saveCurrentTime;
        final String saveCurrentDate;
        final String paymentMethod;
        final String status1;
        Calendar calForDate= Calendar.getInstance() ;

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentDate.format(calForDate.getTime());

        final DatabaseReference finalOrderListRef= FirebaseDatabase.getInstance().getReference().child("Payments")
                .child(Prevalent.currentOnlineCustomer.getPhone());
        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("total Amount",totalPrice);
        orderMap.put("card Owner Name",nameEditText1.getText().toString());
        orderMap.put("card Number",numberEditText.getText().toString());
        orderMap.put("exp Date",expDateEditText.getText().toString());
        orderMap.put("cvc",cvcEditText.getText().toString());

        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);


        finalOrderListRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                                    if(task.isSuccessful()){
                                        Toast.makeText(CreditCardDetails.this,"Payment Successful! press back Button",Toast.LENGTH_SHORT).show();


                                    }
                                }
                            }
        });
    }
}