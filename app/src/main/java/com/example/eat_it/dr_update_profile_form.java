package com.example.eat_it;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.eat_it.Prevalent.CurrentDriver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dr_update_profile_form extends AppCompatActivity {

    private Button updateDataBtn;
    private Button updatePwBtn;
    private EditText Name;
    private EditText Email;
    private TextView Phone;
    private EditText CurPassword;
    private EditText NewPassword;
    private EditText RePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_update_profile_form);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Deliver");


        //Show details in Text Fields
        updateDataBtn = (Button) findViewById(R.id.button3);
        updatePwBtn = (Button) findViewById(R.id.updateBtn);
        Name = (EditText) findViewById(R.id.dr_name);
        Phone = (TextView) findViewById(R.id.textView14);
        Email = (EditText) findViewById(R.id.dr_email);
        CurPassword = (EditText) findViewById(R.id.dr_password3);
        NewPassword = (EditText) findViewById(R.id.dr_password2);
        RePassword = (EditText) findViewById(R.id.dr_password);

        //Assign User Data to Text Field
        Name.setText(CurrentDriver.getName());
        Phone.setText(CurrentDriver.getPhoneNumb());
        Email.setText(CurrentDriver.getEmail());


        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altd = new AlertDialog.Builder(dr_update_profile_form.this);
                altd.setMessage("Do you want to update this Data?").setCancelable(false).setPositiveButton("Yes,Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String dr_name = Name.getText().toString();
                        String dr_email = Email.getText().toString();


                        if (TextUtils.isEmpty(dr_name)) {
                            Toast.makeText(dr_update_profile_form.this, "Please input Name", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(dr_email)) {
                            Toast.makeText(dr_update_profile_form.this, "Please Insert Email", Toast.LENGTH_SHORT).show();
                        } else {
                            ref.child(CurrentDriver.getPhoneNumb()).child("Name").setValue(dr_name);
                            ref.child(CurrentDriver.getPhoneNumb()).child("Email").setValue(dr_email);

                            Toast.makeText(dr_update_profile_form.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }


                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altd.create();
                alert.setTitle("Update Data");
                alert.show();

            }
        });


        updatePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altd = new AlertDialog.Builder(dr_update_profile_form.this);
                altd.setMessage("Do you want to update Password?").setCancelable(false).setPositiveButton("Yes,Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String cPW = CurPassword.getText().toString();
                        String nPW = NewPassword.getText().toString();
                        String conPW = RePassword.getText().toString();

                        if (TextUtils.isEmpty(cPW)) {
                            Toast.makeText(dr_update_profile_form.this, "Input the Current Password", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(nPW)) {
                            Toast.makeText(dr_update_profile_form.this, "Input the New Password", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(conPW)) {
                            Toast.makeText(dr_update_profile_form.this, "Input the Re-Enter New Password", Toast.LENGTH_SHORT).show();
                        } else {
                            if (CurrentDriver.getPassword().equals(cPW)) {
                                if (nPW.equals(conPW)) {
                                    ref.child(CurrentDriver.getPhoneNumb()).child("Password").setValue(nPW);
                                    Toast.makeText(dr_update_profile_form.this, "Password Updated Succesfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                    return;

                                } else {
                                    Toast.makeText(dr_update_profile_form.this, "Re-Entered Password is incorrect", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(dr_update_profile_form.this, "Current Password You entered is incorrect", Toast.LENGTH_SHORT).show();
                            }

                        }



                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altd.create();
                alert.setTitle("Update Password");
                alert.show();

            }
        });


    }

}

