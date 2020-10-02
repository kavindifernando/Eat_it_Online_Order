package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdmAddNewFoodActivity extends AppCompatActivity {

    //Define variables
    private String categoryName, Description, Price, Fname, saveCurrentDate, saveCurrentTime;
    private Button AddNewFoodButton;
    private Button BackBtn;
    private ImageView InputFoodImage;
    private EditText InputFoodName, InputFoodDescription, InputFoodPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String foodRandomKey, downloadImageUrl;
    private StorageReference FoodImagesRef;
    private DatabaseReference FoodsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_add_new_food);

        categoryName = getIntent().getExtras().get("category").toString();
        FoodImagesRef = FirebaseStorage.getInstance().getReference().child("Food Images");
        FoodsRef = FirebaseDatabase.getInstance().getReference().child("Foods");


        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        AddNewFoodButton = (Button) findViewById(R.id.adm_add_food_btn);
        BackBtn = (Button) findViewById(R.id.ad_add_back_btn);
        InputFoodImage = (ImageView) findViewById(R.id.adm_add_Food_image);
        InputFoodName = (EditText) findViewById(R.id.adm_add_foodName_ediTxt2);
        InputFoodDescription = (EditText) findViewById(R.id.adm_add_description_ediTxt2);
        InputFoodPrice = (EditText) findViewById(R.id.adm_add_price_ediTxt2);
        loadingBar = new ProgressDialog(this);

        //open gallery to choose image
        InputFoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        //Insert Button
        AddNewFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateFoodData();
                Intent intent = new Intent(AdmAddNewFoodActivity.this, AdmCategoryActivity.class);
            }

        });

        //back Button
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmAddNewFoodActivity.this, AdmCategoryActivity.class);
                Toast.makeText(AdmAddNewFoodActivity.this, "Back to Category view", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    //Open Phone gallery
    private void OpenGallery() {
        //add new image to firebase storage
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            //get image in firebase storage
            ImageUri = data.getData();
            //display image
            InputFoodImage.setImageURI(ImageUri);
        }
    }

    //validation before inserting
    private void ValidateFoodData() {

        Description = InputFoodDescription.getText().toString();
        Price = InputFoodPrice.getText().toString();
        Fname = InputFoodName.getText().toString();

        //validate image is empty
        if (ImageUri == null) {
            Toast.makeText(this, "Food Image is mandatory....", Toast.LENGTH_SHORT).show();
        }
        //validate name text view is empty
        else if (TextUtils.isEmpty(Fname)) {
            Toast.makeText(this, "Please write food name", Toast.LENGTH_SHORT).show();
        }
        //validate entered name does not contains number or any other symbols
        else if (!Fname.matches("[a-zA-Z-' ']+")) {
            Toast.makeText(this, "Enter valid food Name", Toast.LENGTH_SHORT).show();
        }
        //validate description text view is empty
        else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please write food Description", Toast.LENGTH_SHORT).show();
        }
        //Validate entered Description does not contains number or any other symbols
        else if (!Description.matches("[a-zA-Z-' ']+")) {
            Toast.makeText(this, "Enter valid food Description", Toast.LENGTH_SHORT).show();
        }
        //validate price text view is empty
        else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please write food Price", Toast.LENGTH_SHORT).show();
        }
        //validate entered price does not contains characters or any other symbols
        else if (!Patterns.PHONE.matcher(Price).matches()) {
            Toast.makeText(this, "Enter valid price", Toast.LENGTH_SHORT).show();
        }
        //no errors then store data in to database
        else {
            StoreFoodInformation();
        }
    }

    //get new food adding date and time
    private void StoreFoodInformation() {

        loadingBar.setTitle("Add New Food Item");
        loadingBar.setMessage("Please wait, while we are Adding New product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calender.getTime());

        foodRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = FoodImagesRef.child(ImageUri.getLastPathSegment() + foodRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AdmAddNewFoodActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdmAddNewFoodActivity.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {

                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdmAddNewFoodActivity.this, "got Food Image url successfully", Toast.LENGTH_SHORT).show();

                            SaveFoodInfoToDatabase();
                        }
                    }
                });
            }
        });


    }

    //save data using HashMap
    private void SaveFoodInfoToDatabase() {

        HashMap<String, Object> foodMap = new HashMap<>();
        foodMap.put("fid", foodRandomKey);
        foodMap.put("date", saveCurrentDate);
        foodMap.put("time", saveCurrentTime);
        foodMap.put("description", Description);
        foodMap.put("image", downloadImageUrl);
        foodMap.put("category", categoryName);
        foodMap.put("price", Price);
        foodMap.put("fname", Fname);

        FoodsRef.child(foodRandomKey).updateChildren(foodMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Intent intent = new Intent(AdmAddNewFoodActivity.this, AdmCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdmAddNewFoodActivity.this, "Food is added successfully", Toast.LENGTH_SHORT).show();
                        } else {

                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdmAddNewFoodActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}