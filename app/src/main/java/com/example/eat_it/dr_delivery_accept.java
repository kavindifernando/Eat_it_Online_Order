package com.example.eat_it;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eat_it.Model.delivery_list;
import com.example.eat_it.Prevalent.CurrentDriver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class dr_delivery_accept extends AppCompatActivity {

    private TextView Name;
    private TextView Phone;
    private TextView Address;
    private TextView PayStatus;
    private TextView PostCode;
    private TextView Date;
    private TextView Status;
    private TextView Amount;

    private TextView fAmount;
    private EditText kilom;
    private EditText percent;
    private EditText dis;


    private Button calculate;

    private EditText editText1;
    private TextView editText2;

    private Button confirmbutton;

    private String name;
    private String phone;
    private String address;
    private String payStatus;
    private String postCode;
    private String date;
    private String status;
    private String amount;

    private String key;

    private String userPhone;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_delivery_accept);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        phone = getIntent().getStringExtra("phone");
        payStatus = getIntent().getStringExtra("payStatus");
        postCode = getIntent().getStringExtra("postCode");
        date = getIntent().getStringExtra("date");
        status = getIntent().getStringExtra("status");
        amount = getIntent().getStringExtra("amount");


        //Assign Values to Text view
        Name = (TextView) findViewById(R.id.textName);
        Name.setText(name);

        Address = (TextView) findViewById(R.id.textAddress);
        Address.setText(address);

        Phone = (TextView) findViewById(R.id.textPhone);
        Phone.setText(phone);

        PayStatus = (TextView) findViewById(R.id.txtpayStatus);
        PayStatus.setText(payStatus);

        Amount = (TextView) findViewById(R.id.txtAmount);
        Amount.setText(amount);

        editText2 = (TextView) findViewById(R.id.dr_Aphone);
        editText2.setText(String.valueOf(CurrentDriver.getPhoneNumb()));

        confirmbutton = (Button) findViewById(R.id.confirmBtn);

        editText1 = (EditText) findViewById(R.id.dr_Aname);
        editText1.setText(String.valueOf(CurrentDriver.getName()));


        kilom = (EditText) findViewById(R.id.km);
        dis = (EditText) findViewById(R.id.distence);
        percent = (EditText) findViewById(R.id.percent);
        fAmount = (TextView) findViewById(R.id.txtFinalAmount);
        calculate = (Button) findViewById(R.id.get);


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float KM = Float.parseFloat(kilom.getText().toString());
                float disc = Float.parseFloat(dis.getText().toString());
                float per = Float.parseFloat(percent.getText().toString());
                float am = Float.parseFloat(amount);


                if (TextUtils.isEmpty(kilom.getText().toString())) {
                    Toast.makeText(dr_delivery_accept.this, "Input the paid per KM", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(dis.getText().toString())) {
                    Toast.makeText(dr_delivery_accept.this, "Input the percentage", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(percent.getText().toString())) {
                    Toast.makeText(dr_delivery_accept.this, "Input the paid per amount", Toast.LENGTH_SHORT).show();
                } else {

                    if (per < 100) {
                            float totals = Calculation(KM,disc, per, am);
                        fAmount.setText(Float.toString(totals));
                        Toast.makeText(dr_delivery_accept.this, "Calculated", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(dr_delivery_accept.this, "Input the Valid Percentage", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Confirm Button with Yes No Dialog Box
                AlertDialog.Builder altd = new AlertDialog.Builder(dr_delivery_accept.this);
                altd.setMessage("Do you want Confirm this Order to Delivery?").setCancelable(false).setPositiveButton("Yes,Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                delivery_list dlist = new delivery_list();
                dlist.setCustomer_Name(name);
                dlist.setAddress(address);
                dlist.setDate(date);
                dlist.setPayment_Status(payStatus);
                dlist.setPhone_Number(phone);
                dlist.setPostal_code(postCode);
                dlist.setTotal_Amount(amount);

                dlist.setStatus1("Shipped");


                //Insert data to new table
                String DeliName = editText1.getText().toString();
                String Phone = editText2.getText().toString();
                String Status = "Shipped";

                InsertDeliverData(name, address, date, payStatus, phone, postCode, amount, Status, Phone, DeliName);
                //

                new dr_firebase_helper().updateDeliveryStatus(key, new dr_firebase_helper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<delivery_list> deList, List<String> key) {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altd.create();
                alert.setTitle("Confirm Order to Delivery");
                alert.show();
            }

        });

    }





    private void InsertDeliverData(final String name, final String address, final String date, final String payStatus, final String phone, final String postCode, final String amount, final String status, final String phone1, final String deliName) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("Deliver_Confirmed_Orders").child(phone1).child(phone).exists()) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Deliver_Phone", phone1);
                    userdataMap.put("Deliver_Name", deliName);
                    userdataMap.put("customer_Name", name);
                    userdataMap.put("date", date);
                    userdataMap.put("payment_Status", payStatus);
                    userdataMap.put("phone_Number", phone);
                    userdataMap.put("postal_code", postCode);
                    userdataMap.put("address", address);
                    userdataMap.put("total_Amount", amount);
                    userdataMap.put("Delivery_Status", status);
                    userdataMap.put("status1", "Shipped");

                    RootRef.child("Deliver_Confirmed_Orders").child(phone1).updateChildren(userdataMap)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(dr_delivery_accept.this, "You succesfully confirm order to delivery", Toast.LENGTH_SHORT).show();
                                        finish();
                                        return;

                                    } else {

                                    }
                                }
                            });

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public float Calculation(float Km, float disc, float percen,float amounts) {

        float total = (float) (amounts * (percen / 100.0) + (Km * disc));

       return total;


    }


}
