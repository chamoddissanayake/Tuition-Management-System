package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.tuitionmanagementsystem.listeners.TeacherSubjectListner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherUpdateDeleteResults extends AppCompatActivity {

    TextView TeacherName, TeacherID, TeacherSubject;
    EditText mark;
    Spinner studentIDspn, ResultExamIDspn;
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
        TeacherSubject= findViewById(R.id.txtvwSubject);

        TeacherName.setText(tName);
        TeacherID.setText(tID);

        studentIDspn = (Spinner) findViewById(R.id.updateResultStudentIDSpinner);
        ResultExamIDspn = (Spinner) findViewById(R.id.updateResultExamIDSpinner);
        mark = (EditText) findViewById(R.id.markForAdd);

        getStudentIDs();

        Update = (Button) findViewById(R.id.btnUpdate);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mark.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Fill Marks",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(mark.getText().toString()) >100 || Integer.parseInt(mark.getText().toString()) <0){
                    Toast.makeText(getApplicationContext(),"Marks should be in  range 0 - 100",Toast.LENGTH_SHORT).show();
                }else{
                    String selectedStudent, selectedSubject, selectedExamID, subject;
                    int inputMark;

                    selectedStudent = studentIDspn.getSelectedItem().toString();
                    selectedExamID = ResultExamIDspn.getSelectedItem().toString();
                    inputMark = Integer.parseInt(mark.getText().toString());
                    subject =  TeacherSubject.getText().toString();


                    DatabaseReference updateMarkRef = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");

                    Student_Take_Exam stdtkExamObj = new Student_Take_Exam();
                    stdtkExamObj.setsID(selectedStudent);
                    stdtkExamObj.setExamID(selectedExamID);
                    stdtkExamObj.setMark(inputMark);
                    stdtkExamObj.setSubName(subject);
                    stdtkExamObj.setDocumentLink("aaaaaaaaaaaaaaaaaaaa");

                    updateMarkRef.child(stdtkExamObj.getExamID()).child(stdtkExamObj.getsID()).setValue(stdtkExamObj);
                    Toast.makeText(getApplicationContext(),"Updated successfully.",Toast.LENGTH_LONG).show();

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
                    final String selectedStudent, selectedSubject, selectedExamID;
                    int inputMark;

                    selectedStudent = studentIDspn.getSelectedItem().toString();
                    selectedExamID = ResultExamIDspn.getSelectedItem().toString();
                    inputMark = Integer.parseInt(mark.getText().toString());


                    DatabaseReference deleteMarkRef = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
                    deleteMarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(selectedExamID)){
//                                if(dataSnapshot.hasChild(sid)){
//
//                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    mark.setText("");

                }
            }
        });


        ResultExamIDspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedStudent, subject, selectedExamID;
                int inputMark;

                selectedStudent = studentIDspn.getSelectedItem().toString();
                selectedExamID = ResultExamIDspn.getSelectedItem().toString();

                subject =  TeacherSubject.getText().toString();
                fillResultsOftheSelectedStudentAndExamID(selectedStudent,selectedExamID,subject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void fillResultsOftheSelectedStudentAndExamID(String selectedStudent, String selectedExamID, String subject) {

        final String student = selectedStudent;
        final String examID = selectedExamID;
        String subj =subject;

        DatabaseReference dbRefRetrieveMark = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
        dbRefRetrieveMark.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(examID)){
                    mark.setText(dataSnapshot.child(examID).child(student).child("mark").getValue().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"Not found",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    Teacher teacherObj = new Teacher();
    private void getStudentIDs() {
        //Get Specialized subject of the logged in teacher. - start
        DatabaseReference readref_subject_of_the_teacher = FirebaseDatabase.getInstance().getReference().child("Teacher");
        readref_subject_of_the_teacher.addListenerForSingleValueEvent(new TeacherSubjectListner(getApplicationContext(),teacherObj,studentIDspn,ResultExamIDspn,TeacherSubject));
        //Get Specialized subject of the logged in teacher. - end
        Toast.makeText(this, teacherObj.getSpecialized_subject()+"", Toast.LENGTH_SHORT).show();
    }
}
