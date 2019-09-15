package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedbacknext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacknext);

         final EditText nt1 = findViewById(R.id.editTextID2);
         final EditText nt2 = findViewById(R.id.editTextName2);
         final EditText nt3 = findViewById(R.id.editTextSubject2);
         final EditText nt4 = findViewById(R.id.editTextFeedback2);
         final EditText nt5 = findViewById(R.id.editTextFid2);

         Button btn1 = findViewById(R.id.btnSave);


         Intent intent2 = getIntent();
         String s1 = intent2.getStringExtra("FirstText");
         String s2 = intent2.getStringExtra("SecondText");
         String s3 = intent2.getStringExtra("ThirdText");
         String s4 = intent2.getStringExtra("ForthText");
         String s5 = intent2.getStringExtra("FifthText");

        nt1.setText(s1);
        nt2.setText(s2);
        nt3.setText(s3);
        nt4.setText(s4);
        nt5.setText(s5);



        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference dbRef;

                dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback");
                FeedbackTable feedback = new FeedbackTable();

                if (TextUtils.isEmpty(nt5.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter feedback ID",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(nt1.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter Student ID",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(nt2.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter Student Name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(nt3.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter Subject",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(nt4.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter Feedback",Toast.LENGTH_SHORT).show();
                }
                else{
                    feedback.setFid(nt5.getText().toString().trim());
                    feedback.setSid(nt1.getText().toString().trim());
                    feedback.setsName(nt2.getText().toString().trim());
                    feedback.setSubject(nt3.getText().toString().trim());
                    feedback.setFeedback(nt4.getText().toString().trim());




                    dbRef.child(feedback.getFid()).setValue(feedback);

                    Toast.makeText(getApplicationContext(),"Data Saved Succuessfully!",Toast.LENGTH_SHORT).show();

                    Intent i1 = new Intent(Feedbacknext.this,StudentDashboard.class);
                    startActivity(i1);
              }

            }
        });
    }











}
