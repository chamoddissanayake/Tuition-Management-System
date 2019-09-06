package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

public class EditRegistration_Details extends AppCompatActivity {

    EditText txtName,txtId,txtAddress,txtcontact;
    Button btnAdd,btnDelete;
    DatabaseReference dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration__details);

        txtId = findViewById(R.id.EtID);
        txtId = findViewById(R.id.EtName);
        txtId = findViewById(R.id.EtAddress);
    }



    }