package com.example.eat_it.AdmDatabaseHelper;

import com.example.eat_it.Model.Foods;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminFoodDatabaseHelper {
    private FirebaseDatabase fDatabase;
    private DatabaseReference fReferenceFoods;
    private List<Foods> foods = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Foods> foods, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public AdminFoodDatabaseHelper() {

        fDatabase = FirebaseDatabase.getInstance();
        fReferenceFoods = fDatabase.getReference("Foods");
    }

    //Read the food data from database
    public void readFoods(final DataStatus dataStatus) {

        fReferenceFoods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foods.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Foods food = keyNode.getValue(Foods.class);
                    foods.add(food);
                }
                dataStatus.DataIsLoaded(foods, keys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Update Food data
    public void updateFoods(String key, Foods foods, final DataStatus dataStatus) {
        fReferenceFoods.child(key).setValue(foods)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    //Delete food data
    public void deleteFoods(String key, final DataStatus dataStatus) {
        fReferenceFoods.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }

}
