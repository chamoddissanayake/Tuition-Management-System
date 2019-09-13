package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegistration extends AppCompatActivity {

    TextView tx1, tx2;
//    EditText T1;
//    EditText T2;
//    EditText T3;
//    EditText T4;
    EditText etsName, etAdmissionNo, etAddress, etContactNo, etPassword, etRePassword;

    Button btnSave;
    DatabaseReference dbRef;

    String ID = "";
    String Name = "";
    String Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Name = intent.getStringExtra("Name");
        Type = intent.getStringExtra("Type");

        etsName = (EditText) findViewById(R.id.etsName);
        etAdmissionNo = (EditText) findViewById(R.id.etAdmissionNo);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etContactNo = (EditText) findViewById(R.id.etContactNo);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);

        tx1 = findViewById(R.id.tNamelbl);
        tx2 = findViewById(R.id.tIDlbl);

        tx1.setText(ID);
        tx2.setText(Name);

        btnSave = findViewById(R.id.Submitbutton);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    if (TextUtils.isEmpty(etsName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etAdmissionNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Admission No", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etPassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etRePassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Re-Enter Password", Toast.LENGTH_SHORT).show();
                    else if (!etPassword.getText().toString().equals(etRePassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Password and Re-Password not equal", Toast.LENGTH_SHORT).show();
                    else {

                        //Register Student Details in 'StudentDetails'

                        StudentDetails_tb stdtb1 = new StudentDetails_tb();
                        dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");

                        stdtb1.setStudentName(etsName.getText().toString().trim());
                        stdtb1.setAdmissionNo(etAdmissionNo.getText().toString().trim());
                        stdtb1.setAddress(etAddress.getText().toString().trim());
                        stdtb1.setTel(etContactNo.getText().toString().trim());

                        dbRef.child(stdtb1.getAdmissionNo()).setValue(stdtb1);

                        //Save Student credentials in 'StudentCredentials'

                        StudentCredentials stdCredObj = new StudentCredentials();
                        dbRef = FirebaseDatabase.getInstance().getReference().child("StudentCredentials");

                        String saltPwd = PasswordUtils.getSalt(30);
                        String getSecured = PasswordUtils.generateSecurePassword(etPassword.getText().toString(),saltPwd);
                        stdCredObj.setsID(etAdmissionNo.getText().toString().trim());

                        stdCredObj.setSalt(saltPwd);
                        stdCredObj.setSecuredPassword(getSecured);

                        dbRef.child(stdCredObj.getsID()).setValue(stdCredObj);

                        Toast.makeText(getApplicationContext(), "Student Registered Successfully.", Toast.LENGTH_SHORT).show();

                        clearTextboxes();
                    }

                }
                catch ( NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Error occurred."+e,Toast.LENGTH_LONG).show();
                }catch ( Exception e){
                    Toast.makeText(getApplicationContext(),"Error occurred."+e,Toast.LENGTH_LONG).show();
                }

            }


        });

    }

    private void clearTextboxes() {
        etsName.setText("");
        etAdmissionNo.setText("");
        etAddress.setText("");
        etContactNo.setText("");
        etPassword.setText("");
        etRePassword.setText("");
    }
}