package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class EditRegistration_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration__details);

        EditText t1 = findViewById(R.id.SName2);
        EditText t2 = findViewById(R.id.AdmissionNo2);
        EditText t3 = findViewById(R.id.Address2);
        EditText t4 = findViewById(R.id.ContactNo1);

        Intent intent2 = getIntent();
        String s1 = intent2.getStringExtra("FirstName");
        String s2 = intent2.getStringExtra("SecondName");
        String s3 = intent2.getStringExtra("ThirdName");
        String s4 = intent2.getStringExtra("FourthName");

        t1.setText(s1);
        t2.setText(s2);
        t3.setText(s3);
        t4.setText(s4);

    }
}
