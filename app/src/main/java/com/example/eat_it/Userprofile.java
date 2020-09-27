package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class Userprofile extends AppCompatActivity implements View.OnClickListener{
Button logout,menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        logout=findViewById(R.id.logout);
        menu=findViewById(R.id.menu);
logout.setOnClickListener(this);
menu.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                Paper.book().destroy();
                Intent intent = new Intent(Userprofile.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu:
                Intent intent2 = new Intent(Userprofile.this, Menu.class);
                startActivity(intent2);
                break;

            default:
        }
    }
}