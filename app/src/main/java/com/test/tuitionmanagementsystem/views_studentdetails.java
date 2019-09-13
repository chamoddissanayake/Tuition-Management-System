package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;

public class views_studentdetails extends AppCompatActivity {

    Button btnUpdate;
    Button btnDelete;
    Button btnSearch;

    EditText AdmissionNo, sName, ContactNo, Address;
    EditText etSearchID;

    ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_studentdetails);

        String photo_Link;

        AdmissionNo = (EditText) findViewById(R.id.etAdmissionNo);
        sName = (EditText) findViewById(R.id.etsName);
        ContactNo = (EditText) findViewById(R.id.etContactNo);
        Address = (EditText) findViewById(R.id.etAddress);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        etSearchID = (EditText) findViewById(R.id.SearchID);

        userPhoto = (ImageView)findViewById(R.id.userPhoto);


        AdmissionNo.setEnabled(false);
        sName.setEnabled(false);
        ContactNo.setEnabled(false);
        Address.setEnabled(false);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etSearchID.getText().toString()) ){
                    Toast.makeText(getApplicationContext(),"Please enter Student ID for search",Toast.LENGTH_SHORT).show();
                }else{
                    //fetch data and fill in EditTexts
                    fillStudentData();

                }

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(AdmissionNo.getText().toString())){

                }else if(TextUtils.isEmpty(sName.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(ContactNo.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please Enter Contact Number",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Address.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please Enter Address",Toast.LENGTH_SHORT).show();
                }else{
                    //Update database
//                    Toast.makeText(getApplicationContext(),""+photo_Link,Toast.LENGTH_LONG).show();
                    updateStudentData();
                }

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteStudentDetails();

            }
        });

    }

    private void fillImage() {
//"https://firebasestorage.googleapis.com/v0/b/tuitionmanagementsystem-1af02.appspot.com/o/StudentPhotos%2F1568363430716.jpg?alt=media&token=2cb3ee0f-cc71-4139-8130-a51743849b02"
        Glide.with(this)
                .load(photo_Link)
                .into(userPhoto);

    }

    private void deleteStudentDetails() {
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(AdmissionNo.getText().toString())){
                    DatabaseReference dbRefDelete = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(AdmissionNo.getText().toString());

                    dbRefDelete.removeValue();

                    Toast.makeText(getApplicationContext(),"Data deleted succesfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"No source to delete",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateStudentData() {
        DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
        updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                StudentDetails_tb stdObj = new StudentDetails_tb();

                        if(dataSnapshot.hasChild(AdmissionNo.getText().toString())){

                            stdObj.setStudentName(sName.getText().toString());
                            stdObj.setAddress(Address.getText().toString());
                            stdObj.setTel(ContactNo.getText().toString());
                            stdObj.setAdmissionNo(AdmissionNo.getText().toString());
                            stdObj.setPhotoLink(photo_Link);

                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(AdmissionNo.getText().toString());
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

    String photo_Link;
    private void fillStudentData() {
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(etSearchID.getText().toString());
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){
                    sName.setText(dataSnapshot.child("studentName").getValue().toString());
                    AdmissionNo.setText(dataSnapshot.child("admissionNo").getValue().toString());
                    Address.setText(dataSnapshot.child("address").getValue().toString());
                    ContactNo.setText(dataSnapshot.child("tel").getValue().toString());
                    photo_Link = dataSnapshot.child("photoLink").getValue().toString().trim();
                    //Fetch Photo link

                    fillImage();

                    AdmissionNo.setEnabled(false);
                    sName.setEnabled(true);
                    ContactNo.setEnabled(true);
                    Address.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Student Not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

