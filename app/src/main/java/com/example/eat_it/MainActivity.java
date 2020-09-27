package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eat_it.Model.Customer;
import com.example.eat_it.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button login1;
    TextView skipnow;
    TextView registernow;
    TextView iamanadmin;
    private ProgressDialog ladingBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login1= findViewById(R.id.login1);
        skipnow= (TextView) findViewById(R.id.skipnow);
        registernow = (TextView) findViewById(R.id.registernow);
        iamanadmin = (TextView) findViewById(R.id.iamanadmin);
        ladingBar =new ProgressDialog(this);
        login1.setOnClickListener(this);
        skipnow.setOnClickListener(this);
        registernow.setOnClickListener(this);
iamanadmin.setOnClickListener(this);
        Paper.init(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login1:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
            case R.id.registernow:
                Intent intent2 = new Intent(MainActivity.this, Register.class);
                startActivity(intent2);
                break;
            case R.id.skipnow:
                Intent intent3 = new Intent(MainActivity.this, dr_signupui.class);
                startActivity(intent3);
                break;
            case R.id.iamanadmin:
                Intent intent4 = new Intent(MainActivity.this, Adminlogin.class);
                startActivity(intent4);
                break;
            default:
        }
String UserPhoneKey =Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if(UserPhoneKey != null && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserPhoneKey,UserPasswordKey);


                ladingBar.setTitle("Already Logged in");
                ladingBar.setMessage("Please Wait");
                ladingBar.setCanceledOnTouchOutside(false);
                ladingBar.show();
            }
        }
    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference dbref;
        dbref= FirebaseDatabase.getInstance().getReference();

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Customer").child(phone).exists()){

                    Customer customerData = dataSnapshot.child("Customer").child(phone).getValue(Customer.class);

                    if(customerData.getPhone().equals(phone)){
                        if(customerData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this,"Log-in Successfully",Toast.LENGTH_SHORT).show();
                            ladingBar.dismiss();

                            Intent intent3 = new Intent(MainActivity.this, Userprofile.class);
                            startActivity(intent3);
                        }
                    }

                }
                else {
                    Toast.makeText(MainActivity.this,"please create an Account",Toast.LENGTH_SHORT).show();
                    ladingBar.dismiss();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}