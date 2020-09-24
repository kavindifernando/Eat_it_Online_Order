package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button login1;
    TextView skipnow;
    TextView registernow;
    TextView iamanadmin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login1= findViewById(R.id.login1);
        skipnow= (TextView) findViewById(R.id.skipnow);
        registernow = (TextView) findViewById(R.id.registernow);
        iamanadmin = (TextView) findViewById(R.id.iamanadmin);

        login1.setOnClickListener(this);
        skipnow.setOnClickListener(this);
        registernow.setOnClickListener(this);
iamanadmin.setOnClickListener(this);


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

    }
}