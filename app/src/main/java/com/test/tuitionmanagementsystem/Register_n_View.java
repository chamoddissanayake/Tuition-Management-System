package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_n_View extends AppCompatActivity {

    Button Register, Editbtn, btnViewAll;
    String ID = "";
    String Name = "";
    String type="";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_n__view);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Name = intent.getStringExtra("Name");
        type = intent.getStringExtra("type");

        Register = (Button) findViewById(R.id.btnRegister);
        Editbtn = (Button) findViewById(R.id.btnView);
        btnViewAll = findViewById(R.id.btnAllview);

    Register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i  = new Intent(getApplicationContext(),StudentRegistration.class);
            i.putExtra("ID",ID);
            i.putExtra("Name",Name);
            i.putExtra("Type",type);
            startActivity(i);
        }
    });
    Editbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), views_studentdetails.class);
            i.putExtra("ID",ID);
            i.putExtra("Name",Name);
            i.putExtra("Type",type);
            startActivity(i);
        }
    });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllStudents.class);
                i.putExtra("ID",ID);
                i.putExtra("Name",Name);
                i.putExtra("Type",type);
                startActivity(i);
            }
        });

    }


}
