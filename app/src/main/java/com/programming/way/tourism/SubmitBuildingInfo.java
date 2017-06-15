package com.programming.way.tourism;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.programming.way.tourism.R.id.locateFlat;

/**
 * Created by heshamsalama on 6/16/2017.
 */

public class SubmitBuildingInfo extends Activity {
    //private DatabaseReference firebaseDatabase;
    private FirebaseDatabase database;
    private ImageView cameraImg;
    private int flatsNo =1;
    private DatabaseReference houses;
    ////////////////////////////////
    public SubmitBuildingInfo() {


    }

    public SubmitBuildingInfo(String building,Button locateFlat,final LinearLayout petsLayout, Switch petsSwitch, final EditText priceEditText, final EditText apartmentAreaEditText, final EditText noOfBedRoomsEditText, final EditText noOfBathRoomsEditText, final Switch parkingLotsSwitch, final Switch livingRoomSwitch, final Switch kitchenSwitch, final Switch coolingSystemSwitch, final Switch negotiablePriceSwitch) {
        petsLayout.setVisibility(View.GONE);

        petsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    petsLayout.setVisibility(View.VISIBLE);
                } else {
                    petsLayout.setVisibility(View.GONE);

                }

            }
        });
        database = FirebaseDatabase.getInstance();

        houses = database.getReference("home");
//
        houses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flatsNo= (int) dataSnapshot.getChildrenCount()+1;
                Log.e("nnnnnnn", String.valueOf(flatsNo));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        locateFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent = new Intent(getApplicationContext() , MapsActivity.class);

                DatabaseReference users = database.getReference("users");
                DatabaseReference regions = database.getReference("regions");

                users.child("userId/" + "houses/" + "owened/" + "houseId/"+flatsNo).setValue("");

                houses.child(flatsNo + "/bedRoomsNo/").setValue(noOfBedRoomsEditText.getText().toString());
                houses.child(flatsNo + "/bathNo/").setValue(noOfBathRoomsEditText.getText().toString());
                houses.child(flatsNo + "/price/").setValue(priceEditText.getText().toString());
                houses.child(flatsNo + "/parking/").setValue(String.valueOf(parkingLotsSwitch.isChecked()));
                houses.child(flatsNo + "/negotiablePrice/").setValue(String.valueOf(negotiablePriceSwitch.isChecked()));
                houses.child(flatsNo + "/livingRoom/").setValue(String.valueOf(livingRoomSwitch.isChecked()));
                houses.child(flatsNo + "/pets/").setValue("boolean");
                houses.child(flatsNo + "/kitchen/").setValue(String.valueOf(kitchenSwitch.isChecked()));
                houses.child(flatsNo + "/coolingSystem/").setValue(String.valueOf(coolingSystemSwitch.isChecked()));
                houses.child(flatsNo + "/area/").setValue(apartmentAreaEditText.getText().toString());
                houses.child(flatsNo + "/houseIdNo/" + "location/").setValue("");
//                Toast.makeText(getApplicationContext(),"sending",Toast.LENGTH_LONG).show();
                regions.child("contry/" + "city/" + flatsNo).setValue("location");
//                Toast.makeText(getApplicationContext() , "Data Sent Successfully .." , Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));

    }}