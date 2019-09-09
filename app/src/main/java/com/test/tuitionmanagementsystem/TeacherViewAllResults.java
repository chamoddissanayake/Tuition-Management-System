package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TeacherViewAllResults extends AppCompatActivity {

    String userName, userId, userType;
    TextView tNamelb, tIDlbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_all_results);

        Intent intent = getIntent();
        userName = intent.getStringExtra("ID");
        userId = intent.getStringExtra("Name");
        userType = intent.getStringExtra("Type");

        tNamelb = (TextView) findViewById(R.id.tNamelbl);
        tIDlbl = (TextView) findViewById(R.id.tIDlbl);

        tNamelb.setText(userName);
        tIDlbl.setText(userId);
    }
}
