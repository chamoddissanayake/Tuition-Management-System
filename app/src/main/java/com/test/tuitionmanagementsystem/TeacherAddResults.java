package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.test.tuitionmanagementsystem.listeners.TeacherSubjectListner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherAddResults extends AppCompatActivity {

    TextView TeacherName, TeacherID , TeacherSubject;
    EditText mark;
    Spinner studentIDspn, ResultExamIDspn;
    Button Add;
    Button btnChooseResultFile;
    TextView fileChooseStatus;
    Uri pdfuri;
    String full_documentLink="";
    ProgressBar pb;

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

        btnChooseResultFile  = (Button) findViewById(R.id.btnChooseResultFile);
        fileChooseStatus = (TextView) findViewById(R.id.fileChooseStatus);

        getDetailsFormDB();

        Add = (Button) findViewById(R.id.btnAdd);

        pb=findViewById(R.id.progress_loader);

        UploadTask uploadTask;

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mark.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Fill Marks",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(mark.getText().toString()) >100 || Integer.parseInt(mark.getText().toString()) <0){
                    Toast.makeText(getApplicationContext(),"Marks should be in  range 0 - 100",Toast.LENGTH_SHORT).show();
                }else if(pdfuri == null){
                    Toast.makeText(getApplicationContext(),"Please select a file to upload",Toast.LENGTH_SHORT).show();
                }
                else{
                    pb.setVisibility(view.VISIBLE);
                    uploadDocument(pdfuri);


                }

            }
        });

        btnChooseResultFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFileOpen();
            }
        });

       //End of the onCreate
    }

    private void addResultsToDb() {
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
        stdtkExamObj.setDocumentLink(full_documentLink);

        addMarkRef.child(stdtkExamObj.getExamID()).child(stdtkExamObj.getsID()).setValue(stdtkExamObj);
        pb.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(),"Added to database successfully.",Toast.LENGTH_LONG).show();


        mark.setText("");

        
    }

    private void uploadDocument(Uri pdfuri) {
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

        StorageReference sRef = mStorageReference.child("ResultsDocuments/" + System.currentTimeMillis() + ".pdf");
        sRef.putFile(pdfuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

                        Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_LONG).show();
                        fileChooseStatus.setText("File not selected");
                        //full_documentLink = taskSnapshot.getUploadSessionUri().toString();
                        full_documentLink  = taskSnapshot.getMetadata().getReference().toString();
                        addResultsToDb();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.GONE);
                    }
                });

    }

    private void chooseFileOpen() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data !=  null && data.getData() != null){
            pdfuri = data.getData();
            fileChooseStatus.setText("File Selected.");
        }
    }

    Student_Take_Exam stdTakeExamObj = new Student_Take_Exam();
    Teacher teacherObj = new Teacher();

    private void getDetailsFormDB() {
        //Get Specialized subject of the logged in teacher. - start
        DatabaseReference readref_subject_of_the_teacher = FirebaseDatabase.getInstance().getReference().child("Teacher");
        readref_subject_of_the_teacher.addListenerForSingleValueEvent(new TeacherSubjectListner(getApplicationContext(),teacherObj,studentIDspn,ResultExamIDspn,TeacherSubject));
        //Get Specialized subject of the logged in teacher. - end
    }

}
