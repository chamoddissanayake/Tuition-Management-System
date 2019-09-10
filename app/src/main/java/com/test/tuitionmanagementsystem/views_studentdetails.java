package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class views_studentdetails extends AppCompatActivity {

    Button btnUpdate;
    Button btnDelete;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_studentdetails);


        final EditText t0 = findViewById(R.id.SearchID);
        final EditText t2 = findViewById(R.id.SName2);
        final EditText t3 = findViewById(R.id.address);
        final EditText t4 = findViewById(R.id.ContactNo1);
        final TextView t5 = findViewById(R.id.txtID);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        StudentDetails_tb std = new StudentDetails_tb();

                        if(dataSnapshot.hasChild(t0.getText().toString())){

                            std.setStudentName(t2.getText().toString());
                            std.setAddress(t3.getText().toString());
                            std.setTel(t4.getText().toString());
                            std.setAdmissionNo(t0.getText().toString());

                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(t0.getText().toString());
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

        //delete function
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(t0.getText().toString())){
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(t5.getText().toString());

                            dbRef.removeValue();

                            Toast.makeText(getApplicationContext(),"Data deleted succesfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No source to delete",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        //view button



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reafRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(t0.getText().toString());
                reafRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {

                            t2.setText(dataSnapshot.child("studentName").getValue().toString());
                            t5.setText(dataSnapshot.child("admissionNo").getValue().toString());
                            t3.setText(dataSnapshot.child("address").getValue().toString());
                            t4.setText(dataSnapshot.child("tel").getValue().toString());

                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
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

