package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        final ArrayList<Student_Take_Exam> student_take_examsArrayList = new ArrayList<>();

        final ArrayList<String>  ExamIDStrList = new ArrayList<>();
        DatabaseReference readRef1 = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
        readRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //    Toast.makeText(TeacherViewAllResults.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

//                ArrayList Examlist = new ArrayList();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                     String examidStr = dsp.getKey();

                    ExamIDStrList.add(examidStr);

                     //Toast.makeText(TeacherViewAllResults.this, examidStr+"", Toast.LENGTH_SHORT).show();
                }
                for(int i=0;i<ExamIDStrList.size();i++){
                    // Now Examlist has E001 , E002
                    final DatabaseReference refe1 = FirebaseDatabase.getInstance().getReference().child("Student_take_exam").child(ExamIDStrList.get(i));
                    refe1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                Toast.makeText(getApplicationContext(),""+dataSnapshot.getChildrenCount(),Toast.LENGTH_LONG).show();

                                ArrayList<Student> studentsList = new ArrayList<>();

                                for (DataSnapshot dsp1 : dataSnapshot.getChildren()) {

                                    final String student_ID =dsp1.getKey();
                                   // long a = dataSnapshot.getChildrenCount();

                                    refe1.child(student_ID);

                                    refe1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Student_Take_Exam student_take_examObj = new Student_Take_Exam();


                                            String sid_from_db = dataSnapshot.child(student_ID).child("sID").getValue().toString();
                                            String subName_from_db = dataSnapshot.child(student_ID).child("subName").getValue().toString();
                                            String mark_from_db = dataSnapshot.child(student_ID).child("mark").getValue().toString();
                                            String examID_from_db = dataSnapshot.child(student_ID).child("examID").getValue().toString();
                                            String documentLink_from_db =dataSnapshot.child(student_ID).child("documentLink").getValue().toString();


                                            student_take_examObj.setsID(sid_from_db);
                                            student_take_examObj.setSubName(subName_from_db);
                                            student_take_examObj.setMark(Integer.parseInt(mark_from_db) );
                                            student_take_examObj.setExamID(examID_from_db);
                                            student_take_examObj.setDocumentLink(documentLink_from_db);

                                            student_take_examsArrayList.add(student_take_examObj);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }
}
