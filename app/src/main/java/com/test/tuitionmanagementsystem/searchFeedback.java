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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class searchFeedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_feedback);

        final EditText t1 = findViewById(R.id.fid1);
        final TextView t2 = findViewById(R.id.fid);
        final TextView t3 = findViewById(R.id.sid2);
        final EditText t4 = findViewById(R.id.sname);
        final EditText t5 = findViewById(R.id.subject);
        final EditText t6 = findViewById(R.id.feedback);

        Button b1 = findViewById(R.id.btnView);
        Button b2 = findViewById(R.id.btnUpdate);
        Button b3 = findViewById(R.id.btnDelete);
        Button b4 = findViewById(R.id.btnNext);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reafRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child(t1.getText().toString());
                reafRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            t2.setText(dataSnapshot.child("fid").getValue().toString());
                            t3.setText(dataSnapshot.child("sid").getValue().toString());
                            t4.setText(dataSnapshot.child("sName").getValue().toString());
                            t5.setText(dataSnapshot.child("subject").getValue().toString());
                            t6.setText(dataSnapshot.child("feedback").getValue().toString());
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No source to display",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("Feedback");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        FeedbackTable ft = new FeedbackTable();

                        if(dataSnapshot.hasChild(t2.getText().toString())){
                            ft.setFid(t2.getText().toString());
                            ft.setSid(t3.getText().toString());
                            ft.setsName(t4.getText().toString());
                            ft.setSubject(t5.getText().toString());
                            ft.setFeedback(t6.getText().toString());

                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child(t2.getText().toString());
                            dbRef.setValue(ft);

                            Toast.makeText(getApplicationContext(),"Data updated successfully!",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"No sourse to update",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Feedback");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(t2.getText().toString())){
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child(t2.getText().toString());
                            dbRef.removeValue();

                            Toast.makeText(getApplicationContext(),"Data deleted succesfully!",Toast.LENGTH_SHORT).show();

                        }

                        else{
                            Toast.makeText(getApplicationContext(),"No source to delete!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(searchFeedback.this,StudentDashboard.class);
                startActivity(i1);
            }
        });
    }


}
