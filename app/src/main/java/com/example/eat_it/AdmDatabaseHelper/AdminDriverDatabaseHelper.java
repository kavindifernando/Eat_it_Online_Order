package com.example.eat_it.AdmDatabaseHelper;

import com.example.eat_it.Model.Driver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDriverDatabaseHelper {

    private FirebaseDatabase dDatabase;
    private DatabaseReference dReferenceDrivers;
    private List<Driver> drivers = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Driver> drivers, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public AdminDriverDatabaseHelper() {

        dDatabase = FirebaseDatabase.getInstance();
        dReferenceDrivers = dDatabase.getReference("Deliver");
    }

    //Read the driver data from database
    public void readDrivers(final DataStatus dataStatus) {

        dReferenceDrivers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                drivers.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Driver driver = keyNode.getValue(Driver.class);
                    drivers.add(driver);
                }
                dataStatus.DataIsLoaded(drivers, keys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
