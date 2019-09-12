package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegistration extends AppCompatActivity {

    TextView tx1, tx2;
    EditText T1;
    EditText T2;
    EditText T3;
    EditText T4;
    Button btnSave;
    DatabaseReference dbRef;

    String ID = "";
    String Name = "";
    String Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Name = intent.getStringExtra("Name");
        Type = intent.getStringExtra("Type");

        T1 = (EditText) findViewById(R.id.sName);
        T2 = (EditText) findViewById(R.id.AdmissionNo);
        T3 = (EditText) findViewById(R.id.Address);
        T4 = (EditText) findViewById(R.id.etContactNo);

        tx1 = findViewById(R.id.tNamelbl);
        tx2 = findViewById(R.id.tIDlbl);

        tx1.setText(ID);
        tx2.setText(Name);

        btnSave = findViewById(R.id.Submitbutton);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentDetails_tb stdtb1 = new StudentDetails_tb();
                dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");

                try {

                    if (TextUtils.isEmpty(T1.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(T2.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter the Admission No", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(T3.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter the Address", Toast.LENGTH_SHORT).show();


                    else {
                        stdtb1.setStudentName(T1.getText().toString().trim());
                        stdtb1.setAdmissionNo(T2.getText().toString().trim());
                        stdtb1.setAddress(T3.getText().toString().trim());
                        stdtb1.setTel(T4.getText().toString().trim());


                        dbRef.child(stdtb1.getAdmissionNo()).setValue(stdtb1);

                        Toast.makeText(getApplicationContext(), "Data saves successfully", Toast.LENGTH_SHORT).show();


                    }

                }
                catch ( NumberFormatException e){

                }

            }


        });

    }
}