package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherResultManagementDashboard extends AppCompatActivity {

    TextView TeacherName, TeacherID;
    Button addResult, deleteResult, searchResult,updateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_result_management_dashboard);

        Intent intent = getIntent();
        final String tID = intent.getStringExtra("tID");
        final String tName = intent.getStringExtra("tName");

        TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(tName);
        TeacherID.setText(tID);

        addResult = findViewById(R.id.teacherAddStudentResultsButton);
        deleteResult = findViewById(R.id.teacherDeleteStudentResultsButton);
        searchResult = findViewById(R.id.teacherSearchStudentResultsButton);
        updateResult =findViewById(R.id.teacherUpdateStudentResultsButton);

        addResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Add Result",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), TeacherAddResults.class);
                i.putExtra("tID",tID);
                i.putExtra("tName",tName);
                startActivity(i);
            }
        });

        deleteResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Delete Result",Toast.LENGTH_SHORT).show();
            }
        });
        searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search Result",Toast.LENGTH_SHORT).show();
            }
        });

        updateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Update Result",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
