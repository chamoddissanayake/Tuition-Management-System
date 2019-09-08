package com.test.tuitionmanagementsystem.listeners;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Spinner;
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

    public TeacherSubjectListner(Context appContext, Teacher teacher, Spinner studentIDspn,Spinner resultExamId) {
        this.teacherObj=teacher;
        this.appContext=appContext;
        this.studentIDspn=studentIDspn;
        this.resultExamId = resultExamId;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if(dataSnapshot.hasChild("T001")){
            teacherObj.setSpecialized_subject(dataSnapshot.child("T001").child("specialized_subject").getValue().toString());
//                    Toast.makeText(getApplicationContext(),teacherObj.getSpecialized_subject()+"",Toast.LENGTH_SHORT).show();
            TeacherResultManager.loadStudentsOfTeacher(appContext,teacherObj,studentIDspn);
            TeacherResultManager.loadSubjectExams(appContext,teacherObj,resultExamId);
        }else{
            Toast.makeText(appContext,"No source to display",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}

