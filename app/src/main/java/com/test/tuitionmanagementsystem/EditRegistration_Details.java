package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_edit_registration__details);

       // Intent intent = getIntent();

        btnUpdate = (Button) findViewById(R.id.buttonUpdate);

        etName = (EditText) findViewById(R.id.etName);
        etAdmissionNo = (EditText)findViewById(R.id.etAdmissionNo);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etContactNo = (EditText)findViewById(R.id.etContactNo);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        StudentDetails_tb std = new StudentDetails_tb();

                        if(dataSnapshot.hasChild(etName.getText().toString())){

                            std.setStudentName(etName.getText().toString());
                            std.setAddress(etAddress.getText().toString());
                            std.setTel(etContactNo.getText().toString());
                            std.setAdmissionNo(etAdmissionNo.getText().toString());

                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(etName.getText().toString());
                            dbRef.setValue(std);

                            Toast.makeText(getApplicationContext(),"Data updated successfully!",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No sourse to update",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}