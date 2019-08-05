package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StudentRegistration extends AppCompatActivity {

    TextView tx1;
    EditText T1;
    EditText T2;
    EditText T3;
    EditText T4;

    String tID = "";
    String tName = "";
    String Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Intent intent = getIntent();
        tID = intent.getStringExtra("ID");
        tName = intent.getStringExtra("Name");
        Type = intent.getStringExtra("Type");


        TextView TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TextView TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(tName);
        TeacherID.setText(tID);


        T1 = (EditText) findViewById(R.id.sName);
        T2 = (EditText) findViewById(R.id.AdmissionNo);
        T3 = (EditText) findViewById(R.id.Address);
        T4 = (EditText) findViewById(R.id.ContactNo1);


        //T1.setText(sName);
        //T2.setText(adNo);

    }
    public void sendData (View view) {
        String data1 = T1.getText().toString();
        String data2 = T2.getText().toString();
        String data3 = T3.getText().toString();
        String data4 = T4.getText().toString();

        if (data1.equals("") || data2.equals("") || data3.equals("") || data4.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent01 = new Intent(StudentRegistration.this, EditRegistration_Details.class);


            intent01.putExtra("FirstName", data1);
            intent01.putExtra("SecondText", data2);
            intent01.putExtra("ThirdText", data3);
            intent01.putExtra("FourthText", data4);
            intent01.putExtra("Type",Type);
            intent01.putExtra("Name",tName);
            intent01.putExtra("ID",tID);


            startActivity(intent01);
           Toast.makeText(getApplicationContext(), " " + data1 + " details submitted successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
