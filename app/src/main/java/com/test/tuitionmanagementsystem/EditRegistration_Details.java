package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class EditRegistration_Details extends AppCompatActivity {

    Button deleteBtn, updateBtn;
    String s1,s2,s3,s4;
    String strName,strAdmissionNo, strAddress, strContact;
    EditText etName, etAdmissionNo, etAddress ,etContact;
    String UserID, UserName, UserType;
    TextView TeacherName, TeacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration__details);

        Intent intent = getIntent();
        UserID = intent.getStringExtra("ID");
        UserName = intent.getStringExtra("Name");
        UserType = intent.getStringExtra("Type");

        TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(UserName);
        TeacherID.setText(UserID);

//        Toast.makeText(getApplicationContext()," "+UserID+" "+UserName+" "+UserType,Toast.LENGTH_LONG).show();

        EditText t1 = (EditText) findViewById(R.id.SName2);
        EditText t2 = (EditText) findViewById(R.id.AdmissionNo2);
        EditText t3 = (EditText) findViewById(R.id.Address2);
        EditText t4 = (EditText) findViewById(R.id.ContactNo1);

        updateBtn = (Button) findViewById(R.id.buttonUpdate);
        deleteBtn = (Button) findViewById(R.id.buttonDelete);

        if(UserType.equals("teacher")) {
            Intent intent2 = getIntent();
            s1 = intent2.getStringExtra("FirstName");
            s2 = intent2.getStringExtra("SecondText");
            s3 = intent2.getStringExtra("ThirdText");
            s4 = intent2.getStringExtra("FourthText");

            t1.setText(s1);
            t2.setText(s2);
            t3.setText(s3);
            t4.setText(s4);
        }else if(UserType.equals("student")){
            t1.setText(UserName);
            t2.setText("S001");
            t2.setEnabled(false);
            t3.setText("No.10, Kandy Road, Kadawatha");
            t4.setText("0771234567");

        }

        etName = (EditText) findViewById(R.id.SName2);
        etAdmissionNo = (EditText) findViewById(R.id.AdmissionNo2);
        etAddress = (EditText) findViewById(R.id.Address2);
        etContact = (EditText) findViewById(R.id.ContactNo1);




        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = etName.getText().toString();
                strAdmissionNo = etAdmissionNo.getText().toString();
                strAddress = etAddress.getText().toString();
                strContact = etContact.getText().toString();

                if(isEmpty()){
                    Snackbar.make(view,strName+" was deleted successfully",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = etName.getText().toString();
                strAdmissionNo = etAdmissionNo.getText().toString();
                strAddress = etAddress.getText().toString();
                strContact = etContact.getText().toString();

                if(isEmpty()){
                    Snackbar.make(view,strName+" was updated successfully",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }

            }
        });
    }

    private boolean isEmpty() {
        if(strName.equals("")||strAdmissionNo.equals("")||strAddress.equals("")||strContact.equals("")){
            Toast.makeText(getApplicationContext(),"All fields should be filled", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}
