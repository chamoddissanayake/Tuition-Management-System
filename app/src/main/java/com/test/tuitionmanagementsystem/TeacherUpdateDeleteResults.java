package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherUpdateDeleteResults extends AppCompatActivity {

    TextView TeacherName, TeacherID;
    EditText mark;
    Spinner studentIDspn, subjectspn, ResultExamIDspn;
    Button Update, Delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_delete_results);

        Intent intent = getIntent();
        final String tID = intent.getStringExtra("tID");
        final String tName = intent.getStringExtra("tName");

        TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(tName);
        TeacherID.setText(tID);

        studentIDspn = (Spinner) findViewById(R.id.updateResultStudentIDSpinner);
        subjectspn = (Spinner) findViewById(R.id.updateResultSubjectSpinner);
        ResultExamIDspn = (Spinner) findViewById(R.id.updateResultExamIDSpinner);
        mark = (EditText) findViewById(R.id.markForAdd);

        fillStudentSpinner();
        fillSubjectSpinner();
        fillExamID();

        ResultExamIDspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(ResultExamIDspn.getSelectedItemPosition()==0)
                    mark.setText("89");
                else if(ResultExamIDspn.getSelectedItemPosition()==1)
                    mark.setText("78");
                else if(ResultExamIDspn.getSelectedItemPosition()==2)
                    mark.setText("73");
                else if(ResultExamIDspn.getSelectedItemPosition()==3)
                    mark.setText("85");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Update = (Button) findViewById(R.id.btnUpdate);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mark.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Fill Marks",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(mark.getText().toString()) >100 || Integer.parseInt(mark.getText().toString()) <0){
                    Toast.makeText(getApplicationContext(),"Marks should be in  range 0 - 100",Toast.LENGTH_SHORT).show();
                }else{
                    String selectedStudent, selectedSubject, selectedExamID;
                    int inputMark;

                    selectedStudent = studentIDspn.getSelectedItem().toString();
                    selectedSubject = subjectspn.getSelectedItem().toString();
                    selectedExamID = ResultExamIDspn.getSelectedItem().toString();
                    inputMark = Integer.parseInt(mark.getText().toString());

                    //Toast.makeText(getApplicationContext()," "+selectedStudent+" "+selectedSubject+" "+selectedExamID+" "+inputMark+" Added Successfully.",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view," "+selectedStudent+" "+selectedSubject+" "+selectedExamID+" "+inputMark+" Added Successfully.",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    mark.setText("");
                }

            }
        });




        Delete = (Button) findViewById(R.id.btnDelete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mark.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Fill Marks",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(mark.getText().toString()) >100 || Integer.parseInt(mark.getText().toString()) <0){
                    Toast.makeText(getApplicationContext(),"Marks should be in  range 0 - 100",Toast.LENGTH_SHORT).show();
                }else{
                    String selectedStudent, selectedSubject, selectedExamID;
                    int inputMark;

                    selectedStudent = studentIDspn.getSelectedItem().toString();
                    selectedSubject = subjectspn.getSelectedItem().toString();
                    selectedExamID = ResultExamIDspn.getSelectedItem().toString();
                    inputMark = Integer.parseInt(mark.getText().toString());

                    //Toast.makeText(getApplicationContext()," "+selectedStudent+" "+selectedSubject+" "+selectedExamID+" "+inputMark+" Added Successfully.",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view," "+selectedStudent+" "+selectedSubject+" "+selectedExamID+" "+inputMark+" Deleted Successfully.",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    mark.setText("");
                    fillStudentSpinner();
                    fillSubjectSpinner();
                    fillExamID();

                }
            }
        });


    }

    private void fillStudentSpinner() {
        String[] students = new String[]{
                "S0001","S0002","S0003","S0004","S0005","S0006","S0007","S0008","S0009","S0010"
        };
        final List<String> studentsList = new ArrayList<>(Arrays.asList(students));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,studentsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        studentIDspn.setAdapter(spinnerArrayAdapter);

    }

    private void fillSubjectSpinner() {
        String[] subjects = new String[]{
                "Science","Sinahla","English","Maths"
        };
        final List<String> subjectList = new ArrayList<>(Arrays.asList(subjects));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,subjectList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        subjectspn.setAdapter(spinnerArrayAdapter);
    }

    private void fillExamID() {
        String[] examID = new String[]{
                "E001","E002","E003","E004"
        };
        final List<String> examList = new ArrayList<>(Arrays.asList(examID));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,examList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ResultExamIDspn.setAdapter(spinnerArrayAdapter);
    }
}
