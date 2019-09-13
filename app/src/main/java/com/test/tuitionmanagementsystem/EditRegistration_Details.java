package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditRegistration_Details extends AppCompatActivity {

    Button btnUpdate;
    EditText etName, etAdmissionNo, etAddress, etContactNo;
    String photo_Link="aaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_edit_registration__details);

       // Intent intent = getIntent();

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        etName = (EditText) findViewById(R.id.etName);
        etAdmissionNo = (EditText)findViewById(R.id.etAdmissionNo);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etContactNo = (EditText)findViewById(R.id.etContactNo);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etAdmissionNo.getText().toString())){

                }else if(TextUtils.isEmpty(etName.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Name must be filled",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etAddress.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Address must be filled",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etContactNo.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Contact number must be filled.",Toast.LENGTH_SHORT).show();
                }else{
                    updateDetails();
                }
            }
        });


    }

    private void updateDetails() {

        DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
        updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                StudentDetails_tb stdObj = new StudentDetails_tb();

                if(dataSnapshot.hasChild(etAdmissionNo.getText().toString())){

                    stdObj.setStudentName(etName.getText().toString());
                    stdObj.setAddress(etAddress.getText().toString());
                    stdObj.setTel(etContactNo.getText().toString());
                    stdObj.setAdmissionNo(etAdmissionNo.getText().toString());
                    stdObj.setPhotoLink(photo_Link);

                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(etAdmissionNo.getText().toString());
                    dbRef.setValue(stdObj);

                    Toast.makeText(getApplicationContext(),"Data updated successfully!",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"No record",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}