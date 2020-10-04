package com.example.eat_it;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class dr_signupui extends AppCompatActivity {

    private Button SignupBtn;
    private EditText name,phone,email,password;
    private ProgressDialog loadingBar;  /* Dialog Box */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_signupui);

        SignupBtn = (Button) findViewById(R.id.signUpBtn);
        name = (EditText) findViewById(R.id.dr_name);
        phone = (EditText) findViewById(R.id.dr_phone);
        email = (EditText) findViewById(R.id.dr_email);
        password = (EditText) findViewById(R.id.dr_password);
        loadingBar = new ProgressDialog(this);

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String dr_name = name.getText().toString();
        String dr_phone = phone.getText().toString();
        String dr_email = email.getText().toString();
        String dr_password = password.getText().toString();

        if (TextUtils.isEmpty(dr_name))
        {
            Toast.makeText(this, "Please Insert your name...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(dr_phone))
        {
            Toast.makeText(this, "Please Insert your name...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(dr_email))
        {
            Toast.makeText(this, "Please Insert your name...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(dr_password))
        {
            Toast.makeText(this, "Please Insert your name...", Toast.LENGTH_SHORT).show();
        }

        else {
            /* Dialog box */
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait, while we are checking the credential.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhoneNumber(dr_name, dr_password, dr_email, dr_phone);
        }
        }


        private void ValidatePhoneNumber(final String dr_name, final String dr_password, final String dr_email, final String dr_phone)
        {
            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.child("Deliver").child(dr_phone).exists())
                    {
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("Phone",dr_phone);
                        userdataMap.put("Email",dr_email);
                        userdataMap.put("Name",dr_name);
                        userdataMap.put("Password",dr_password);

                        RootRef.child("Deliver").child(dr_phone).updateChildren(userdataMap)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(dr_signupui.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                                 loadingBar.dismiss();        // Terminate the loading bar

                                 Intent intent = new Intent(dr_signupui.this, dr_login.class);
                                 startActivity(intent);
                             }

                             else{
                                 loadingBar.dismiss();
                                 Toast.makeText(dr_signupui.this, "Network error, Please Try again", Toast.LENGTH_SHORT).show();
                             }
                            }
                        });

                    }

                    else{
                        Toast.makeText(dr_signupui.this, "This" + dr_phone + "already Exists",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(dr_signupui.this, "Try again with using another phone number",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dr_signupui.this, dr_signupui.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
}
