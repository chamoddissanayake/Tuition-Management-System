package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class StudentResultManagementDashboard extends AppCompatActivity {

    TextView StudentName, StudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result_management_dashboard);

        Intent intent = getIntent();
        final String sID = intent.getStringExtra("sID");
        final String sName = intent.getStringExtra("sName");

        StudentName = (TextView) findViewById(R.id.sNamelbl);
        StudentID = (TextView) findViewById(R.id.sIDlbl);


        StudentName.setText(sName);
        StudentID.setText(sID);


    }
}
