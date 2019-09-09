package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TeacherSearchResults extends AppCompatActivity {
    TextView TeacherName, TeacherID;
    String name, id;
    Button buttonSearchResults;
    EditText  examIDforSearch, studentIDforSearch;
    TextView tvSid, tvMark, tvSubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search_results);

        Intent intent = getIntent();
        name = intent.getStringExtra("tID");
        id = intent.getStringExtra("tName");

        TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(name);
        TeacherID.setText(id);

        studentIDforSearch = (EditText) findViewById(R.id.studentIDforSearch);
        examIDforSearch = (EditText)findViewById(R.id.examIDforSearch);
        buttonSearchResults = (Button) findViewById(R.id.btnSearchResult);


        tvSid = findViewById(R.id.tvSid);
        tvMark = findViewById(R.id.tvMark);
        tvSubName = findViewById(R.id.tvSubName);

        buttonSearchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(studentIDforSearch.getText().toString() )){
                    Toast.makeText(getApplicationContext(),"Student ID is empty",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(examIDforSearch.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Exam ID is empty",Toast.LENGTH_SHORT).show();
                }else{
                    final String sid = studentIDforSearch.getText().toString();
                    final String eId = examIDforSearch.getText().toString();

//                    Toast.makeText(getApplicationContext(),"Search clicked"+sid+""+eId,Toast.LENGTH_SHORT).show();

                    DatabaseReference dbRefSearchSpecificResult = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
                    dbRefSearchSpecificResult.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(eId)){
                                if(dataSnapshot.child(eId).hasChild(sid)){
                                    //Retrieve data - start
                                    DatabaseReference dbRefSearchresult = FirebaseDatabase.getInstance().getReference().child("Student_take_exam").child(eId).child(sid);

                                    dbRefSearchresult.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String exam_ID = dataSnapshot.child("examID").getValue().toString();
                                            String mark = dataSnapshot.child("mark").getValue().toString();
                                            String student_ID = dataSnapshot.child("sID").getValue().toString();
                                            String subject_Name = dataSnapshot.child("subName").getValue().toString();
                                            String document_Link = dataSnapshot.child("documentLink").getValue().toString();

                                            tvSid.setText(student_ID);
                                            tvMark.setText(mark);
                                            tvSubName.setText(subject_Name);

//                                            Toast.makeText(getApplicationContext(), ""+exam_ID+mark+student_ID+subject_Name+document_Link, Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    //Retrieve data - end
                                }else{
                                    Toast.makeText(getApplicationContext(),"Student not found",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Exam not found",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }

            }
        });






    }
}
