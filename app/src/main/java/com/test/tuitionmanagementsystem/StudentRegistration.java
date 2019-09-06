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


    EditText txtID,txtName,txtAddress,txtContact,txtNIC;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        txtName = findViewById(R.id.sName);
        txtID = findViewById(R.id.AdmissionNo);
        txtAddress = findViewById(R.id.Address);
        txtContact = findViewById(R.id.EtContactNo);
        txtNIC = findViewById(R.id.EtNic);

        btnSave =  findViewById(R.id.Submitbutton);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Student1");
//                try {
//                    if (TextUtils.isEmpty(txtName.getText().toString().trim())
//                            || TextUtils.isEmpty(txtID.getText().toString().trim())
//                            || TextUtils.isEmpty(txtAddress.getText().toString().trim())
//                            || TextUtils.isEmpty(txtContact.getText().toString().trim())
//                            || TextUtils.isEmpty(txtNIC.getText().toString().trim())) {
//
//
//                    } else {
                        Student std = new Student();
                        std.setSid(txtID.getText().toString().trim());
                        std.setsName(txtName.getText().toString().trim());
                        std.setAddress(txtAddress.getText().toString().trim());
                        std.setTel(txtContact.getText().toString().trim());
                        std.setNic(txtNIC.getText().toString().trim());

                        dbRef.child(std.getSid()).setValue(std);
                        Toast.makeText(getApplicationContext(), "Data added", Toast.LENGTH_SHORT).show();
//                    }


//                } catch (Exception ex) {
//                    Toast.makeText(getApplicationContext(), "Error"+ex, Toast.LENGTH_SHORT).show();
//                }
            }

        });

    }

}
