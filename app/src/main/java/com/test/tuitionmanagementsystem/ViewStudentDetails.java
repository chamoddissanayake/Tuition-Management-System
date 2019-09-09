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

public class ViewStudentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_details);

        final EditText t0 = findViewById(R.id.SearchID);
        final EditText t2 = findViewById(R.id.SName2);
        final EditText t3 = findViewById(R.id.address);
        final EditText t4 = findViewById(R.id.ContactNo1);
        final TextView t5 = findViewById(R.id.txtID);
        Button btn1 = findViewById(R.id.btnSearch);

        btn1.setOnClickListener(new View.OnClickListener() {


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


