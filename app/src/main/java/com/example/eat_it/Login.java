package com.example.eat_it;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eat_it.Model.Customer;
import com.example.eat_it.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    private EditText InputNumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog ladingBar;
    private String parentDbName= "Customer";
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputNumber = (EditText) findViewById(R.id.phoneU);
        InputPassword = (EditText) findViewById(R.id.passwordU);
        LoginButton = (Button)findViewById(R.id.login);
        ladingBar =new ProgressDialog(this);
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_checkbox);
        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginCustomer();
            }
        });
    }


    private void loginCustomer() {

        String phone= InputNumber.getText().toString();
        String password= InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getApplicationContext(),"Please Enter the Contact Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please Enter the Password",Toast.LENGTH_SHORT).show();
        }
        else{
            ladingBar.setTitle("Login Account");
            ladingBar.setMessage("Please Wait");
            ladingBar.setCanceledOnTouchOutside(false);
            ladingBar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }
        final DatabaseReference dbref;
        dbref= FirebaseDatabase.getInstance().getReference();

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists()){

                    Customer customerData = dataSnapshot.child(parentDbName).child(phone).getValue(Customer.class);

                    if(customerData.getPhone().equals(phone)){
                        if(customerData.getPassword().equals(password)){
                            Toast.makeText(Login.this,"Log-in Successfully",Toast.LENGTH_SHORT).show();
                            ladingBar.dismiss();

                            Intent intent3 = new Intent(Login.this, CustomerProfile.class);
                            Prevalent.currentOnlineCustomer=customerData;
                            startActivity(intent3);


                        }
                    }

                }
                else {
                    Toast.makeText(Login.this,"please create an Account",Toast.LENGTH_SHORT).show();
                    ladingBar.dismiss();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}