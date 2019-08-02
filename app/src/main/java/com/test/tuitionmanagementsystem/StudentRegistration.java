package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StudentRegistration extends AppCompatActivity {

    TextView tx1;
    EditText T1;
    EditText T2;
    EditText T3;
    EditText T4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);



        T1 = findViewById(R.id.sName);
        T2 = findViewById(R.id.AdmissionNo);
        T3 = findViewById(R.id.Address);
        T4 = findViewById(R.id.ContactNo1);


        //T1.setText(sName);
        //T2.setText(adNo);

    }
    public void sendData (View view){
        Intent intent01 = new Intent(StudentRegistration.this,EditRegistration_Details.class);

        String data1 = T1.getText().toString();
        String data2 = T2.getText().toString();
        String data3 = T3.getText().toString();
        String data4 = T4.getText().toString();

        intent01.putExtra("FirstName",data1);
        intent01.putExtra("SecondText",data2);
        intent01.putExtra("ThirdText",data3);
        intent01.putExtra("FourthText",data4);

        startActivity(intent01);

    }

}
