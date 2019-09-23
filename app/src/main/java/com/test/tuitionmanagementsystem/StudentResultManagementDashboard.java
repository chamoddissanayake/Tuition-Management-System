package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
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
    String mark, document_link;
    Button btnResultSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result_management_dashboard);

        Intent intent = getIntent();
        sID = intent.getStringExtra("sID");
        sName = intent.getStringExtra("sName");

        StudentName = (TextView) findViewById(R.id.sNamelbl);
        StudentID = (TextView) findViewById(R.id.sIDlbl);

        StudentName.setText(sName);
        StudentID.setText(sID);

        stdName = (EditText) findViewById(R.id.nameTxt);
        examIDspn = findViewById(R.id.examIDspn);
        Subjectspn = findViewById(R.id.Subjectspn);
        showResult = findViewById(R.id.btnShowResult);
        stdName.setText(sName);
        stdName.setEnabled(false);

        subjectStrArray = new ArrayList<>();
        ExamIDStrArray = new ArrayList<>();

        btnResultSheet = (Button) findViewById(R.id.btnResultSheet);

        loadSubjectsOfStudent();

        marklbl = (TextView) findViewById(R.id.markDisplaylbl);
        yourMarklbl = (TextView) findViewById(R.id.yourMarklbl);
        yourMarklbl.setVisibility(View.INVISIBLE);
        btnResultSheet.setVisibility(View.INVISIBLE);


        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Show Results Clicked.",Toast.LENGTH_SHORT).show();
                yourMarklbl.setVisibility(View.VISIBLE);
                btnResultSheet.setVisibility(View.VISIBLE);
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

        btnResultSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissionREAD_EXTERNAL_STORAGE()) {
                    downloadResultSheet();
                }

            }
        });

        //End of OnCreate
    }


    private void downloadResultSheet() {
//        Toast.makeText(getApplicationContext(),document_link,Toast.LENGTH_SHORT).show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(document_link);

//        ProgressDialog  pd = new ProgressDialog(this);
//        pd.setTitle("Nature.jpg");
//        pd.setMessage("Downloading Please Wait!");
//        pd.setIndeterminate(true);
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.show();


        final File rootPath = new File(Environment.getExternalStorageDirectory(), " ResultsDocuments");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, System.currentTimeMillis() + ".pdf");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                Toast.makeText(getApplicationContext(), "File Downloaded Successfully", Toast.LENGTH_SHORT).show();
//                if (!isVisible()){
//                    return;
//                }
//
//                if (localFile.canRead()){
//
//                    pd.dismiss();
//                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Download Incompleted", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void RetrieveResultsDataFromDB() {
        if (TextUtils.isEmpty(Subjectspn.getSelectedItem().toString())) {
            Toast.makeText(getApplicationContext(), "Please Select Subject", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(examIDspn.getSelectedItem().toString())) {
            Toast.makeText(getApplicationContext(), "Please Exam ID Subject", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference readResultofTheSelected = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
            readResultofTheSelected.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(examIDspn.getSelectedItem().toString())) {
                        if (dataSnapshot.child(examIDspn.getSelectedItem().toString()).hasChild(sID)) {
                            //Exam ID and Student ID correct
                            DatabaseReference readResultRef = FirebaseDatabase.getInstance().getReference().child("Student_take_exam")
                                    .child(examIDspn.getSelectedItem().toString()).child(sID);
                            readResultRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    mark = dataSnapshot.child("mark").getValue().toString();

                                    document_link = dataSnapshot.child("documentLink").getValue().toString();
                                    fillMark();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Student Not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Subject Not found", Toast.LENGTH_SHORT).show();
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
                if (dataSnapshot.hasChild(sub)) {

                    for (DataSnapshot child : dataSnapshot.child(sub).getChildren()) {
                        ExamIDStrArray.add(child.getKey());
                    }
                    fillExamIDSpinner();

                } else {
                    Toast.makeText(getApplicationContext(), "Subjects for current student not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadSubjectsOfStudent() {
        DatabaseReference readSubjectOfTheCurrentStudent = FirebaseDatabase.getInstance().getReference().child("StudentsFollowingSubjects");
        readSubjectOfTheCurrentStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(sID)) {

                    for (DataSnapshot child : dataSnapshot.child(sID).getChildren()) {
                        subjectStrArray.add(child.getKey());
                    }
                    fillSubjectSpinner();

                } else {
                    Toast.makeText(getApplicationContext(), "Subjects for current student not found.", Toast.LENGTH_SHORT).show();
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
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, examIDsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        examIDspn.setAdapter(spinnerArrayAdapter);


    }

    private void fillSubjectSpinner() {

        String[] Subjects = subjectStrArray.toArray(new String[subjectStrArray.size()]);

        final List<String> subjectsList = new ArrayList<>(Arrays.asList(Subjects));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, subjectsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Subjectspn.setAdapter(spinnerArrayAdapter);
    }


    //Permission
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
    ) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
//                    Toast.makeText(Login.this, "GET_ACCOUNTS Denied",
//                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }


    }
}
