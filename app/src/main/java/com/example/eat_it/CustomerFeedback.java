package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eat_it.Model.Feedback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CustomerFeedback extends AppCompatActivity {

    EditText nameText, emailText, subjectText, messageText;
    Button sendButton, showButton, updateButton, deleteButton;

    DatabaseReference dbRef;
    Feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback);

        nameText = (EditText) findViewById(R.id.name);
        emailText = (EditText) findViewById(R.id.email);
        subjectText = (EditText) findViewById(R.id.sub);
        messageText = (EditText) findViewById(R.id.msg);

        sendButton = (Button) findViewById(R.id.sentBtn);
        showButton = (Button) findViewById(R.id.showBtn);
        updateButton = (Button) findViewById(R.id.updateBtn);
        deleteButton = (Button) findViewById(R.id.delBtn);

        feedback = new Feedback();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback");

                try {
                    if (TextUtils.isEmpty(nameText.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(emailText.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please Enter Your Email Address",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(subjectText.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please Enter Your Feedback Subject",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(messageText.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please Enter Your Message",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        feedback.setName(nameText.getText().toString().trim());
                        feedback.setEmail(emailText.getText().toString().trim());
                        feedback.setSubject(subjectText.getText().toString().trim());
                        feedback.setMessage(messageText.getText().toString().trim());

                        //dbRef.push().setValue(feedback);
                        dbRef.child("feedback").setValue(feedback);
                        clearData();

                        Toast.makeText(getApplicationContext(),"Your Feedback Saved Successfully...",Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception ex){

                }
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference showRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child("feedback");
                showRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            nameText.setText(snapshot.child("name").getValue().toString());
                            emailText.setText(snapshot.child("email").getValue().toString());
                            subjectText.setText(snapshot.child("subject").getValue().toString());
                            messageText.setText(snapshot.child("message").getValue().toString());
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Source to Display...",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Feedback");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("feedback")){
                            try {
                                feedback.setName(nameText.getText().toString().trim());
                                feedback.setEmail(emailText.getText().toString().trim());
                                feedback.setSubject(subjectText.getText().toString().trim());
                                feedback.setMessage(messageText.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child("feedback");
                                dbRef.setValue(feedback);

                                Toast.makeText(getApplicationContext(),"Your Feedback Updated Successfully...",Toast.LENGTH_SHORT).show();
                            }catch (Exception ex){

                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No Source to Update...",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Feedback");
               delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.hasChild("feedback")){
                           dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child("feedback");
                           delRef.removeValue();
                           clearData();

                           Toast.makeText(getApplicationContext(),"Your Feedback Deleted Successfully...",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"No Source to Delete...",Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });
    }

    private void clearData() {
        nameText.setText("");
        emailText.setText("");
        subjectText.setText("");
        messageText.setText("");
    }

}