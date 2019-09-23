package com.test.tuitionmanagementsystem.managers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.tuitionmanagementsystem.R;
import com.test.tuitionmanagementsystem.Student;
import com.test.tuitionmanagementsystem.SubjectExam;
import com.test.tuitionmanagementsystem.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherResultManager {
    public static void loadStudentsOfTeacher(final Context context, final Teacher teacherObj,final Spinner studentIDspn){
        //Get Students of for the subject -start
        DatabaseReference readref_students_for_subject = FirebaseDatabase.getInstance().getReference().child("SubjectFollowingStudents");
        readref_students_for_subject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(teacherObj.getSpecialized_subject())){
                    //Toast.makeText(context,dataSnapshot.child(teacherObj.getSpecialized_subject()).getChildrenCount()+"",Toast.LENGTH_LONG).show();
                    DataSnapshot slist = dataSnapshot.child(teacherObj.getSpecialized_subject());
                    ArrayList<String> studentList = new ArrayList<>();
                    for(DataSnapshot ds : slist.getChildren()) {

                        //Get IDs of students in his class.
                        String studentId = ds.getKey();

                        Student std= new Student();
                        std.setSid(studentId);
                        studentList.add(studentId);
                    }

                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,studentList);

                    spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    studentIDspn.setAdapter(spinnerArrayAdapter);


                    // teacherObj.setSpecialized_subject(dataSnapshot.child(teacherObj.getSpecialized_subject()).child("specialized_subject").getValue().toString());
//                    Toast.makeText(getApplicationContext(),teacherObj.getSpecialized_subject()+"",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context,"No source to display",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Get Students of for the subject -end
    }

    public static void loadSubjectExams(final Context context,final Teacher teacherObj, final Spinner spinner){
        //Get Exam IDs -start
        DatabaseReference readref_examIDs = FirebaseDatabase.getInstance().getReference().child("SubjectExam");
        readref_examIDs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.hasChild(teacherObj.getSpecialized_subject())){

                    DataSnapshot examIDList = dataSnapshot.child(teacherObj.getSpecialized_subject());
                    ArrayList<String> ExamIDArrList = new ArrayList<>();
                    for(DataSnapshot ds : examIDList.getChildren()) {

                        //Get exam IDs of the subject
                        String examId = ds.getKey();

                        SubjectExam subjectExamObj = new SubjectExam();
                        subjectExamObj.setExamID(examId);
                        ExamIDArrList.add(examId);
                    }
                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,ExamIDArrList);

                    spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerArrayAdapter);

                }else{
                    Toast.makeText(context,"No source to display",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
