package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class TeacherResultManagementDashboard extends AppCompatActivity {

    TextView TeacherName, TeacherID;
    Button addResult, searchResult,updateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
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
        searchResult = findViewById(R.id.teacherSearchStudentResultsButton);
        updateResult =findViewById(R.id.teacherUpdateStudentResultsButton);

        addResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Add Result",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), TeacherAddResults.class);
                i.putExtra("tID",tID);
                i.putExtra("tName",tName);
                startActivity(i);
            }
        });


        searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Search Result",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), TeacherSearchResults.class);
                i.putExtra("tID",tID);
                i.putExtra("tName",tName);
                startActivity(i);
            }
        });

        updateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Update Result",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), TeacherUpdateDeleteResults.class);
                i.putExtra("tID",tID);
                i.putExtra("tName",tName);
                startActivity(i);
            }
        });


    }


}
