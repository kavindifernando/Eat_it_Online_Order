package com.example.eat_it;

import androidx.annotation.NonNull;

import com.example.eat_it.Model.delivery_list;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dr_firebase_helper {

    private FirebaseDatabase drDatabase;
    private DatabaseReference drDatabaseReference;
    private List<delivery_list> deList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<delivery_list> deList, List<String> key);
        void DataIsUpdated();
        void DataIsDeleted();
    }


    public dr_firebase_helper() {
            drDatabase = FirebaseDatabase.getInstance();
            drDatabaseReference = drDatabase.getReference("Order List");
    }

    //Get delivery List

    public void getDeliveryList(final DataStatus dataStatus){
        drDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deList.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    delivery_list drlist = keyNode.getValue(delivery_list.class);

                    if(!drlist.getStatus1().equals("Shipped")) {
                        deList.add(drlist);
                    }
                    else{
                    }
                }

                dataStatus.DataIsLoaded(deList,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //Update delivery status

    public void updateDeliveryStatus(String key,final DataStatus dataStatus){

        drDatabaseReference.child(key).child("status1").setValue("Shipped").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });

    }




}
