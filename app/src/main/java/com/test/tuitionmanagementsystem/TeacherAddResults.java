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

    TextView TeacherName, TeacherID;
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


        studentIDspn = (Spinner) findViewById(R.id.addResultStudentIDSpinner);
        ResultExamIDspn = (Spinner) findViewById(R.id.addResultExamIDSpinner);
        mark = (EditText) findViewById(R.id.markForAdd);

        getDetailsFormDB();

       // fillStudentSpinner();
       // fillExamID();

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

                    Toast.makeText(getApplicationContext()," "+selectedStudent+" "+" "+selectedExamID+" "+inputMark+""+subject,Toast.LENGTH_SHORT).show();

                    DatabaseReference addMarkRef = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");

                    Student_Take_Exam stdtkExamObj = new Student_Take_Exam();
                    stdtkExamObj.setsID(selectedStudent);
                    stdtkExamObj.setExamID(selectedExamID);
                    stdtkExamObj.setMark(inputMark);
                    stdtkExamObj.setSubName(subject);
                    stdtkExamObj.setDocumentLink("aaaaaaaaaaaaaaaaaaaa");

                    addMarkRef.child(stdtkExamObj.getExamID()).setValue(stdtkExamObj);
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
        readref_subject_of_the_teacher.addListenerForSingleValueEvent(new TeacherSubjectListner(getApplicationContext(),teacherObj,studentIDspn,ResultExamIDspn));
        //Get Specialized subject of the logged in teacher. - end

    }

//    private void fillStudentSpinner() {
//        String[] students = new String[]{
//                "S0001","S0002","S0003","S0004","S0005","S0006","S0007","S0008","S0009","S0010"
//        };
//
//        // Need to fetch list of students form db
//
//
//        final List<String> studentsList = new ArrayList<>(Arrays.asList(students));
//        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,studentsList);
//
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        studentIDspn.setAdapter(spinnerArrayAdapter);
//
//    }


//    private void fillExamID() {
//        String[] examID = new String[]{
//                "E001","E002","E003","E004"
//        };
//        final List<String> examList = new ArrayList<>(Arrays.asList(examID));
//        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,examList);
//
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        ResultExamIDspn.setAdapter(spinnerArrayAdapter);
//    }


    public void addtoDB(View view) {

        DatabaseReference dbref;
 //       dbref = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");

//        Student_Take_Exam obj = new Student_Take_Exam();
//        obj.setsID("S001");
//        obj.setSubName("Science");
//        obj.setExamID("E001");
//        obj.setMark(80);
//        obj.setDocumentLink("aaaaaaaa");
//
//            dbref.child("S001").setValue(obj);
//            Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();

//        dbref = FirebaseDatabase.getInstance().getReference().child("Exam");
//        Exam obj = new Exam();
//        obj.setExamID("E001");
//        obj.setDateTime("2019-08-25");
//        obj.setVenue("M503");
//        obj.setDescription("aaaaaa");
//
//            dbref.child("E001").setValue(obj);
//            Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();

//        dbref = FirebaseDatabase.getInstance().getReference().child("Teacher");
//        Teacher obj = new Teacher();
//        obj.setTid("T001");
//        obj.settName("aaa");
//        obj.setNic("123456789V");
//        obj.setQualification("qqq");
//        obj.setTel("0771234567");
//        obj.setEmail("aa@dd.com");
//        obj.setSpecialized_subject("Science");
//        obj.setPhotoLink("aaa");
//
//
//        dbref.child("T001").setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();


//        dbref = FirebaseDatabase.getInstance().getReference().child("Student");
//        Student obj = new Student();
//        obj.setSid("S001");
//        obj.setsName("aaa");
//        obj.setEmail("a@gmail.com");
//        obj.setTel("0771234567");
//        obj.setYor(2019);
//        obj.setPhotoLink("sss");
//        obj.setNic("123456789V");
//
//
//        dbref.child("S001").setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();

//        dbref = FirebaseDatabase.getInstance().getReference().child("Subject");
//        Subject obj = new Subject();
//        obj.setsName("Maths");
//
//        dbref.child(obj.getsName()).setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();

//        dbref = FirebaseDatabase.getInstance().getReference().child("StudentFollowSubject");
//        StudentFollowSubject obj = new StudentFollowSubject();
//        obj.setsID("S001");
//        obj.setSubName("Maths");


//        dbref.push().setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();


//        dbref = FirebaseDatabase.getInstance().getReference().child("StudentFollowSubject");
//        StudentFollowSubject obj = new StudentFollowSubject();
//        obj.setsID("S001");
//        obj.setSubName("Maths");
//
//
//        dbref.child(obj.getSubName()).setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();



//        dbref = FirebaseDatabase.getInstance().getReference().child("StudentCredentials");
//        StudentCredentials obj = new StudentCredentials();
//        obj.setsID("S001");
//        obj.setSecuredPassword("aaaaaaaaaaaaaaaaaaaaaa");
//        obj.setSalt("bbbbbbbbbbbbbbbbbbbbbbb");
//
//        dbref.child(obj.getsID()).setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();


//        dbref = FirebaseDatabase.getInstance().getReference().child("TeacherCredentials");
//        StudentCredentials obj = new StudentCredentials();
//        obj.setsID("T001");
//        obj.setSecuredPassword("aaaaaaaaaaaaaaaaaaaaaa");
//        obj.setSalt("bbbbbbbbbbbbbbbbbbbbbbb");
//
//        dbref.child(obj.getsID()).setValue(obj);
//        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();

    }
}
