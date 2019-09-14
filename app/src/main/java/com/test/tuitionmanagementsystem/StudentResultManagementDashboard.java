package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentResultManagementDashboard extends AppCompatActivity {

    TextView StudentName, StudentID;
    EditText stdName;
    Spinner examIDspn, Subjectspn;
    Button showResult;
    TextView marklbl, yourMarklbl;
    String sID, sName;
    ArrayList<String> subjectStrArray;
    ArrayList<String> ExamIDStrArray;
    String mark , document_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result_management_dashboard);

        Intent intent = getIntent();
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");

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

        subjectStrArray = new ArrayList<>();
        ExamIDStrArray = new ArrayList<>();

        loadSubjectsOfStudent();

        marklbl = (TextView) findViewById(R.id.markDisplaylbl);
        yourMarklbl = (TextView) findViewById(R.id.yourMarklbl);
        yourMarklbl.setVisibility(View.INVISIBLE);

        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Show Results Clicked.",Toast.LENGTH_SHORT).show();
                yourMarklbl.setVisibility(View.VISIBLE);
                //Get marks - start
                RetrieveResultsDataFromDB();
                //Get marks - end

            }
        });

        Subjectspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadExamsForTheSelectedSubject(Subjectspn.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void RetrieveResultsDataFromDB() {
        if(TextUtils.isEmpty(Subjectspn.getSelectedItem().toString())){
            Toast.makeText(getApplicationContext(),"Please Select Subject",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(examIDspn.getSelectedItem().toString())){
            Toast.makeText(getApplicationContext(),"Please Exam ID Subject",Toast.LENGTH_SHORT).show();
        }else {
            DatabaseReference readResultofTheSelected = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
            readResultofTheSelected.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(examIDspn.getSelectedItem().toString())){
                        if(dataSnapshot.child(examIDspn.getSelectedItem().toString()).hasChild(sID)){
                            //Exam ID and Student ID correct
                            DatabaseReference readResultRef = FirebaseDatabase.getInstance().getReference().child("Student_take_exam")
                                    .child(examIDspn.getSelectedItem().toString()).child(sID);
                            readResultRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    dataSnapshot.child("sID").getValue().toString();
//                                    dataSnapshot.child("subName").getValue().toString();
                                    mark = dataSnapshot.child("mark").getValue().toString();
//                                    dataSnapshot.child("examID").getValue().toString();
                                    document_link = dataSnapshot.child("documentLink").getValue().toString();
                                    fillMark();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }else{
                              Toast.makeText(getApplicationContext(),"Student Not found",Toast.LENGTH_SHORT).show();
                          }
                    }else{
                        Toast.makeText(getApplicationContext(),"Subject Not found",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void fillMark() {
        marklbl.setText(mark);
    }

    private void loadExamsForTheSelectedSubject(String subject) {
        DatabaseReference readExamsForTheSelectEdSubject = FirebaseDatabase.getInstance().getReference().child("SubjectExam");
        final String sub = subject;
        readExamsForTheSelectEdSubject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(sub)){

                    for (DataSnapshot child: dataSnapshot.child(sub).getChildren()) {
                        ExamIDStrArray.add(child.getKey());
                    }
                    fillExamIDSpinner();

                }else{
                    Toast.makeText(getApplicationContext(),"Subjects for current student not found.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadSubjectsOfStudent(){
        DatabaseReference readSubjectOfTheCurrentStudent = FirebaseDatabase.getInstance().getReference().child("StudentsFollowingSubjects");
        readSubjectOfTheCurrentStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(sID)){

                        for (DataSnapshot child: dataSnapshot.child(sID).getChildren()) {
                            subjectStrArray.add(child.getKey());
                        }
                        fillSubjectSpinner();

                }else{
                    Toast.makeText(getApplicationContext(),"Subjects for current student not found.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    private void fillExamIDSpinner() {

        String[] examIDs = ExamIDStrArray.toArray(new String[ExamIDStrArray.size()]);
        
        final List<String> examIDsList = new ArrayList<>(Arrays.asList(examIDs));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,examIDsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        examIDspn.setAdapter(spinnerArrayAdapter);


    }
    private void fillSubjectSpinner() {

        String[] Subjects = subjectStrArray.toArray(new String[subjectStrArray.size()]);

        final List<String> subjectsList = new ArrayList<>(Arrays.asList(Subjects));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,subjectsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Subjectspn.setAdapter(spinnerArrayAdapter);
    }


}
