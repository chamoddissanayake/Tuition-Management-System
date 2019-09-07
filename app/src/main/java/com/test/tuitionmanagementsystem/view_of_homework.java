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
               DatabaseReference reafRef = FirebaseDatabase.getInstance().getReference().child("Homework_tbl").child(t1.getText().toString());
                reafRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            t2.setText(dataSnapshot.child("homeworkID").getValue().toString());
                            t4.setText(dataSnapshot.child("subName").getValue().toString());
                            t3.setText(dataSnapshot.child("hoework").getValue().toString());
//                            Toast.makeText(getApplicationContext(), "found.", Toast.LENGTH_SHORT).show();
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

       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                DatabaseReference upddRef = FirebaseDatabase.getInstance().getReference().child("Homework_tbl");
                upddRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Homework_tbl hwtbl = new Homework_tbl();
                        if(dataSnapshot.hasChild(t2.getText().toString())){
                            hwtbl.setHoework(t4.getText().toString());

                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Homework_tbl").child(t2.getText().toString());
                            dbRef.setValue(hwtbl);

                            Toast.makeText(getApplicationContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        }

                        else{

                            Toast.makeText(getApplicationContext(),"No sourse to Update", Toast.LENGTH_SHORT).show();
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

               DatabaseReference delRef =FirebaseDatabase.getInstance().getReference().child("Homework_tbl");
               delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(dataSnapshot.hasChild(t2.getText().toString())){
                           DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Homework_tbl").child(t2.getText().toString());
                           dbRef.removeValue();

                           Toast.makeText(getApplicationContext(), "Homework Deleted Successfully", Toast.LENGTH_SHORT).show();
                       }

                       else {
                           Toast.makeText(getApplicationContext(),"No sourse to Delete",Toast.LENGTH_SHORT).show();
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
