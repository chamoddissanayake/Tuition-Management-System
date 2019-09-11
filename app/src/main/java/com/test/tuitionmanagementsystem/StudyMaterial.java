package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StudyMaterial extends AppCompatActivity {

    ImageButton btnTutorial;
    ImageButton btnHomework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("ID");
        final String name = intent.getStringExtra("Name");
        final String type = intent.getStringExtra("Type");


        btnTutorial=(ImageButton) findViewById(R.id.btnMoveToTutorial);
        btnHomework=(ImageButton)findViewById(R.id.btnMoveToHomework);

        btnHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("student") ){
                    Intent i1 = new Intent(StudyMaterial.this,Todolist.class);
                    startActivity(i1);
                }else if(type.equals("teacher") ){
                    Intent i2 = new Intent(StudyMaterial.this,homeworkdashboard.class);
                    startActivity(i2);
                }

            }
        });
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("teacher")){
                    Intent i1 = new Intent(StudyMaterial.this,tutorialUploader.class);
                    startActivity(i1);
                }

//                Intent itute = new Intent(StudyMaterial.this,AddTutorials.class);
//                startActivity(itute);

            }
        });
    }
}
