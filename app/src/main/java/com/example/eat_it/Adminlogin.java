package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eat_it.Model.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Adminlogin extends AppCompatActivity {
    private EditText InputNumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog ladingBar;
    private String parentDbName= "Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        InputNumber = (EditText) findViewById(R.id.phoneU);
        InputPassword = (EditText) findViewById(R.id.passwordU);
        LoginButton = (Button)findViewById(R.id.login);
        ladingBar =new ProgressDialog(this);


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
        final DatabaseReference dbref;
        dbref= FirebaseDatabase.getInstance().getReference();

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists()){

                    Admin customerData = dataSnapshot.child(parentDbName).child(phone).getValue(Admin.class);

                    if(customerData.getPhone().equals(phone)){
                        if(customerData.getPassword().equals(password)){
                            Toast.makeText(Adminlogin.this,"Log-in Successfully",Toast.LENGTH_SHORT).show();
                            ladingBar.dismiss();

                            Intent intent3 = new Intent(Adminlogin.this, Userprofile.class);
                            startActivity(intent3);
                        }
                    }

                }
                else {
                    Toast.makeText(Adminlogin.this,"please create an Account",Toast.LENGTH_SHORT).show();
                    ladingBar.dismiss();
                    Intent intent3 = new Intent(Adminlogin.this, AdminRegister.class);
                    startActivity(intent3);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
