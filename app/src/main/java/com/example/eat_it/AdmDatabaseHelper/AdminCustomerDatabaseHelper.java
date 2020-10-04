package com.example.eat_it.AdmDatabaseHelper;

import com.example.eat_it.Model.Customer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCustomerDatabaseHelper {

    private FirebaseDatabase cDatabase;
    private DatabaseReference cReferenceCustomers;
    private List<Customer> customers = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Customer> customers, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public AdminCustomerDatabaseHelper() {

        cDatabase = FirebaseDatabase.getInstance();
        cReferenceCustomers = cDatabase.getReference("Customer");
    }

    //Read the customer data from database
    public void readCustomers(final DataStatus dataStatus) {

        cReferenceCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customers.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Customer customer = keyNode.getValue(Customer.class);
                    customers.add(customer);
                }
                dataStatus.DataIsLoaded(customers, keys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
