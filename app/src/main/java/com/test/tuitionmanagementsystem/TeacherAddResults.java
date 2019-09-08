package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.tuitionmanagementsystem.listeners.TeacherSubjectListner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherAddResults extends AppCompatActivity {

    TextView TeacherName, TeacherID , TeacherSubject;
    EditText mark;
    Spinner studentIDspn, ResultExamIDspn;
    Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_results);

        Intent intent = getIntent();
        final String tID = intent.getStringExtra("tID");
        final String tName = intent.getStringExtra("tName");

        TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(tName);
        TeacherID.setText(tID);

        TeacherSubject = findViewById(R.id.txtvwSubject);

        studentIDspn = (Spinner) findViewById(R.id.addResultStudentIDSpinner);
        ResultExamIDspn = (Spinner) findViewById(R.id.addResultExamIDSpinner);
        mark = (EditText) findViewById(R.id.markForAdd);

        getDetailsFormDB();

        Add = (Button) findViewById(R.id.btnAdd);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mark.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Fill Marks",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(mark.getText().toString()) >100 || Integer.parseInt(mark.getText().toString()) <0){
                    Toast.makeText(getApplicationContext(),"Marks should be in  range 0 - 100",Toast.LENGTH_SHORT).show();
                }else{
                    String selectedStudent, selectedExamID, subject;
                    int inputMark;

                    selectedStudent = studentIDspn.getSelectedItem().toString();
                    selectedExamID = ResultExamIDspn.getSelectedItem().toString();
                    inputMark = Integer.parseInt(mark.getText().toString());
                    subject = teacherObj.getSpecialized_subject();

                   // Toast.makeText(getApplicationContext()," "+selectedStudent+" "+" "+selectedExamID+" "+inputMark+""+subject,Toast.LENGTH_SHORT).show();

                    DatabaseReference addMarkRef = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");

                    Student_Take_Exam stdtkExamObj = new Student_Take_Exam();
                    stdtkExamObj.setsID(selectedStudent);
                    stdtkExamObj.setExamID(selectedExamID);
                    stdtkExamObj.setMark(inputMark);
                    stdtkExamObj.setSubName(subject);
                    stdtkExamObj.setDocumentLink("aaaaaaaaaaaaaaaaaaaa");

                    addMarkRef.child(stdtkExamObj.getExamID()).child(stdtkExamObj.getsID()).setValue(stdtkExamObj);
                    Toast.makeText(getApplicationContext(),"Added to database successfully.",Toast.LENGTH_LONG).show();

                    mark.setText("");
                }

            }
        });
    }

    Student_Take_Exam stdTakeExamObj = new Student_Take_Exam();
    Teacher teacherObj = new Teacher();

    private void getDetailsFormDB() {
        //Get Specialized subject of the logged in teacher. - start
        DatabaseReference readref_subject_of_the_teacher = FirebaseDatabase.getInstance().getReference().child("Teacher");
        readref_subject_of_the_teacher.addListenerForSingleValueEvent(new TeacherSubjectListner(getApplicationContext(),teacherObj,studentIDspn,ResultExamIDspn,TeacherSubject));
        //Get Specialized subject of the logged in teacher. - end
    }

    public void addtoDB(View view) {

        DatabaseReference dbref;

    }
}
