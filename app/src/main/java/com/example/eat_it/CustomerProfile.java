package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eat_it.Prevalent.Prevalent;
import com.example.eat_it.Model.Customer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import android.os.Bundle;

public class CustomerProfile extends AppCompatActivity {

    EditText name_TextView,email_TextView,phone_TextView,pwdTextView;
    ImageView profileImage;
    Button updateBtn,delBtn,changeProfileBtn;

    Customer cus;
    Uri imgUri;
    String myUrl = "";
    private StorageTask uploadTask;
    StorageReference storeProfilePicture;
    String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        storeProfilePicture = FirebaseStorage.getInstance().getReference().child("Profile Picture");

        name_TextView = (EditText) findViewById(R.id.name_Text);
        email_TextView = (EditText) findViewById(R.id.email_Text);
        phone_TextView = (EditText) findViewById(R.id.phone_Text);
        pwdTextView = (EditText) findViewById(R.id.pwd_Text);
        profileImage = (ImageView) findViewById(R.id.imageView2);

        updateBtn = (Button) findViewById(R.id.updateBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
        changeProfileBtn = (Button) findViewById(R.id.changeProfileBtn);

        customerInfoDisplay(name_TextView,email_TextView,phone_TextView,profileImage);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){
                    CustomerUpdateProfile();
                }
                else{
                    customerUpdateProfileData();
                }
            }
        });

        changeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checker = "clicked";

                CropImage.activity(imgUri)
                        .setAspectRatio(1,1)
                        .start(CustomerProfile.this);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data == null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imgUri = result.getUri();

            profileImage.setImageURI(imgUri);
        }
        else{
            Toast.makeText(this,"Error,Please Try Again",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(CustomerProfile.this,CustomerProfile.class));
            finish();
        }

    }

    private void customerInfoDisplay(TextView name_textView, TextView email_textView, TextView phone_textView, final ImageView profileImage) {

        DatabaseReference dbRef1 = FirebaseDatabase.getInstance().getReference().child("Customer").child(Prevalent.currentOnlineCustomer.getEmail());

        dbRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("image").exists()){
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String password = snapshot.child("password").getValue().toString();

                        Picasso.get().load(image).into(profileImage);
                        name_TextView.setText(name);
                        email_TextView.setText(email);
                        phone_TextView.setText(phone);
                        pwdTextView.setText(password);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CustomerUpdateProfile() {

        if(TextUtils.isEmpty(name_TextView.getText().toString())){
            Toast.makeText(this,"Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone_TextView.getText().toString())){
            Toast.makeText(this,"Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email_TextView.getText().toString())){
            Toast.makeText(this,"Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked")){
            uploadImage();
        }

    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait,while we are updating your profile");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imgUri != null){
            final StorageReference fileRef = storeProfilePicture.child(Prevalent.currentOnlineCustomer.getEmail() + ".jpg");
            uploadTask = fileRef.putFile(imgUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference().child("Customer");

                                HashMap<String,Object> customermap = new HashMap<>();
                                customermap.put("name" , name_TextView.getText().toString());
                                customermap.put("phone" , phone_TextView.getText().toString());
                                customermap.put("email" , email_TextView.getText().toString());
                                customermap.put("image" , myUrl);

                                dbref2.child(Prevalent.currentOnlineCustomer.getPhone()).updateChildren(customermap);

                                progressDialog.dismiss();
                                startActivity(new Intent(CustomerProfile.this,CustomerProfile.class));
                                Toast.makeText(CustomerProfile.this,"Your Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(CustomerProfile.this,"Error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(CustomerProfile.this,"Please Select an image",Toast.LENGTH_SHORT).show();
        }
    }

    private void customerUpdateProfileData() {
        DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference().child("Customer");

        HashMap<String,Object> customermap = new HashMap<>();
        customermap.put("name" , name_TextView.getText().toString());
        customermap.put("phone" , phone_TextView.getText().toString());
        customermap.put("email" , email_TextView.getText().toString());

        dbref2.child(Prevalent.currentOnlineCustomer.getPhone()).updateChildren(customermap);

        startActivity(new Intent(CustomerProfile.this,CustomerProfile.class));
        Toast.makeText(CustomerProfile.this,"Your Profile Updated Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteCustomer() {

        final DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(Prevalent.currentOnlineCustomer.getPhone());
        delRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    delRef.removeValue();
                    claerData();
                    Toast.makeText(getApplicationContext(),"Customer Deleted Successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Source To Delete",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void claerData(){
        name_TextView.setText("");
        email_TextView.setText("");
        phone_TextView.setText("");
        pwdTextView.setText("");
    }

}