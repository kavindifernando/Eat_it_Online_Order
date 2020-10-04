package com.example.eat_it;

import androidx.annotation.NonNull;

import com.example.eat_it.Model.dr_conf_list;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dr_helper2 {

    private FirebaseDatabase drDatabase;
    private DatabaseReference drDatabaseReference;
    private List<dr_conf_list> deList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<dr_conf_list> deList, List<String> key);
        void DataIsUpdated();
        void DataIsDeleted();
    }


    public dr_helper2() {
        drDatabase = FirebaseDatabase.getInstance();
        drDatabaseReference = drDatabase.getReference("Deliver_Confirmed_Orders");
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
                    dr_conf_list drlist = keyNode.getValue(dr_conf_list.class);
                        deList.add(drlist);

                }

                dataStatus.DataIsLoaded(deList,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
