package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homeworkdashboard extends AppCompatActivity {

    Button uploadhomework, viewhomework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeworkdashboard);

        uploadhomework = findViewById(R.id.btnuploadHomework);
        viewhomework = findViewById(R.id.btnviewHomework);

        uploadhomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i33 = new Intent(homeworkdashboard.this,Homework.class);
                startActivity(i33);
            }
        });

        viewhomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i44 = new Intent(homeworkdashboard.this,view_of_homework.class);
                startActivity(i44);
            }
        });



    }
}
