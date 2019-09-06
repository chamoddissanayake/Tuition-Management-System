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

public class view_of_homework extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_of_homework);

        final EditText t1 = findViewById(R.id.txt_search_hwID);
        final EditText t2 = findViewById(R.id.txthwID);
        final TextView t3 = findViewById(R.id.txtsubName);
        final EditText t4 = findViewById(R.id.txtDesc);

        Button b1 = findViewById(R.id.btnsearchDetails);
        Button b2 = findViewById(R.id.btnUpdate);
        Button b3 = findViewById(R.id.btnDelete);

       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatabaseReference reafRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child(t1.getText().toString());
                reafRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            t2.setText(dataSnapshot.child("txthwID").getValue().toString());
                            t3.setText(dataSnapshot.child("txtsubName").getValue().toString());
                            t4.setText(dataSnapshot.child("txtDesc").getValue().toString());
                        }

                        else{
                            Toast.makeText(getApplicationContext(), "No sourse to display",Toast.LENGTH_SHORT).show();
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
