package com.test.tuitionmanagementsystem.listeners;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.test.tuitionmanagementsystem.Teacher;
import com.test.tuitionmanagementsystem.managers.TeacherResultManager;

public class TeacherSubjectListner implements ValueEventListener {
    Teacher teacherObj;
    Context appContext;
    Spinner studentIDspn;
    Spinner resultExamId;
    TextView teacherSubject;


    public TeacherSubjectListner(Context applicationContext, Teacher teacherObj, Spinner studentIDspn, Spinner resultExamIDspn, TextView teacherSubject) {
        this.teacherObj = teacherObj;
        this.appContext = applicationContext;
        this.studentIDspn = studentIDspn;
        this.resultExamId = resultExamIDspn;
        this.teacherSubject = teacherSubject;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.hasChild("T001")) {
            teacherObj.setSpecialized_subject(dataSnapshot.child("T001").child("specialized_subject").getValue().toString());
//                    Toast.makeText(getApplicationContext(),teacherObj.getSpecialized_subject()+"",Toast.LENGTH_SHORT).show();
            teacherSubject.setText(teacherObj.getSpecialized_subject());
            if(studentIDspn!=null) {
                TeacherResultManager.loadStudentsOfTeacher(appContext, teacherObj, studentIDspn);
            }
            if(resultExamId!=null) {
                TeacherResultManager.loadSubjectExams(appContext, teacherObj, resultExamId);
            }

        } else {
            Toast.makeText(appContext, "No source to display", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}

