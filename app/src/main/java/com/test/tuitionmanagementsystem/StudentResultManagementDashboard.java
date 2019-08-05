package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentResultManagementDashboard extends AppCompatActivity {

    TextView StudentName, StudentID;
    EditText stdName;
    Spinner examIDspn, Subjectspn;
    Button showResult;
    TextView marklbl, yourMarklbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result_management_dashboard);

        Intent intent = getIntent();
        final String sID = intent.getStringExtra("sID");
        final String sName = intent.getStringExtra("sName");

        StudentName = (TextView) findViewById(R.id.sNamelbl);
        StudentID = (TextView) findViewById(R.id.sIDlbl);

        StudentName.setText(sName);
        StudentID.setText(sID);

        stdName = (EditText)findViewById(R.id.nameTxt);
        examIDspn = findViewById(R.id.examIDspn);
        Subjectspn = findViewById(R.id.Subjectspn);
        showResult = findViewById(R.id.btnShowResult);
        stdName.setText(sName);
        stdName.setEnabled(false);

        fillExamIDSpinner();
        fillSubjectSpinner();

        marklbl = (TextView) findViewById(R.id.markDisplaylbl);
        yourMarklbl = (TextView) findViewById(R.id.yourMarklbl);
        yourMarklbl.setVisibility(View.INVISIBLE);

        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Show Results Clicked.",Toast.LENGTH_SHORT).show();
                yourMarklbl.setVisibility(View.VISIBLE);
                //Get marks - start

                //Get marks - end
                marklbl.setText("56");
            }
        });

    }



    private void fillExamIDSpinner() {

        String[] examIDs = new String[]{
                "E001","E002","E003","E004","E005"
        };
        final List<String> examIDsList = new ArrayList<>(Arrays.asList(examIDs));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,examIDsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        examIDspn.setAdapter(spinnerArrayAdapter);


    }
    private void fillSubjectSpinner() {

        String[] Subjects = new String[]{
                "Science","Sinhala","English","Maths"
        };
        final List<String> subjectsList = new ArrayList<>(Arrays.asList(Subjects));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,subjectsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Subjectspn.setAdapter(spinnerArrayAdapter);
    }


}
