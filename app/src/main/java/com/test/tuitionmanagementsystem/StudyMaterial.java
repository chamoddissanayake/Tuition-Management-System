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

        btnTutorial=(ImageButton) findViewById(R.id.btnMoveToTutorial);
        btnHomework=(ImageButton)findViewById(R.id.btnMoveToHomework);

        btnHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(StudyMaterial.this,Homework.class);
                startActivity(i1);
            }
        });
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itute = new Intent(StudyMaterial.this,AddTutorials.class);
                startActivity(itute);

            }
        });
    }
}
