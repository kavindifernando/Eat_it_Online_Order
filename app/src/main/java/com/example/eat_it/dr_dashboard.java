package com.example.eat_it;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.eat_it.Prevalent.CurrentDriver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dr_dashboard extends AppCompatActivity {

    private CardView updateView;
    private CardView pending;
    private CardView myOrders;
    private CardView logouts;
    private TextView user;


    private String Name;
    private String Phone;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_dashboard);

        getCurrentDriverrData();

        updateView = (CardView) findViewById(R.id.card3);
        pending = (CardView) findViewById(R.id.card1);
        myOrders = (CardView) findViewById(R.id.card2);
        logouts = (CardView) findViewById(R.id.card4);


        user = (TextView) findViewById(R.id.textView12);
        user.setText(CurrentDriver.getEmail());

        //Logout Button
        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altd = new AlertDialog.Builder(dr_dashboard.this);
                altd.setMessage("Do you want to Signout?").setCancelable(false).setPositiveButton("Signout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent1 = new Intent(dr_dashboard.this, dr_login.class);

                        intent1.addFlags(intent1.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.addFlags(intent1.FLAG_ACTIVITY_CLEAR_TASK);
                        intent1.addFlags(intent1.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent1);
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altd.create();
                alert.setTitle("Signout");
                alert.show();


            }
        });


        //Update Profile Card View
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(dr_dashboard.this, dr_update_profile_form.class);
                startActivity(intent2);
            }
        });

        //Update Profile Card View
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(dr_dashboard.this, dr_delivery_list.class);
                startActivity(intent3);
            }
        });

        //Update Profile Card View
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(dr_dashboard.this, dr_confirm_list.class);
                startActivity(intent4);
            }
        });

    }

    public void getCurrentDriverrData() {

        //
        //Get current Driver Details
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Deliver");
        ref.child(CurrentDriver.getPhoneNumb()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userName = String.valueOf(dataSnapshot.child("Name").getValue());
                CurrentDriver.setName(userName);

                String email = String.valueOf(dataSnapshot.child("Email").getValue());
                CurrentDriver.setEmail(email);

                String password = String.valueOf(dataSnapshot.child("Password").getValue());
                CurrentDriver.setPassword(password);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //
        //
    }
}