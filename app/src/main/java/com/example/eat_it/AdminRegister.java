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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class AdminRegister extends AppCompatActivity {
    EditText txtName,txtEmail,txtPhone,txtPassword;
    Button btnRegister;
    ProgressDialog lodingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        txtName = (EditText) findViewById(R.id.editTextTextPersonName);
        txtEmail = (EditText) findViewById(R.id.editTextTextEmail);
        txtPhone = (EditText) findViewById(R.id.editTextTextPhone);
        txtPassword = (EditText) findViewById(R.id.editPassword);

        btnRegister = (Button) findViewById(R.id.button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createCustomerAccount();
            }
        });
    }

    private void createCustomerAccount() {

        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhone.getText().toString();
        String password = txtPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please Enter the Full Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please Enter the Email Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Please Enter the Contact Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please Enter the Password", Toast.LENGTH_SHORT).show();
        } else {


            ValidateEmailAddress(name, email, phone, password);

        }
    }

    private void ValidateEmailAddress(final String name, final String email, final String phone, final String password) {
        final DatabaseReference dbref;
        dbref= FirebaseDatabase.getInstance().getReference();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot.child("Admin").child(phone).exists())){

                    HashMap<String,Object> adminDataMap =new HashMap<>();
                    adminDataMap.put("phone",phone);
                    adminDataMap.put("name",name);
                    adminDataMap.put("email",email);

                    adminDataMap.put("password",password);

                    dbref.child("Admin").child(phone).updateChildren(adminDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(AdminRegister.this,"Your Account created Successfully",Toast.LENGTH_SHORT).show();
                                        lodingbar.dismiss();

                                        Intent intent3 = new Intent(AdminRegister.this, Menu.class);
                                        startActivity(intent3);
                                    }
                                    else{

                                        Toast.makeText(AdminRegister.this,"Network Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(AdminRegister.this,"This "+phone+" number already exist",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}