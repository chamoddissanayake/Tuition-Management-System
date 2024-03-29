package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherDashboard extends AppCompatActivity {

    TextView helloMsg;
    ImageButton ResultsMgtBtn,AttendanceMgtBtn,FeedbackNotificationMgtBtn, StudyMaterialsManagementBtn;

    String tID;
    String tName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Intent intent = getIntent();
        tID = intent.getStringExtra("TeacherID");
        tName = intent.getStringExtra("tName");

        helloMsg = (TextView)findViewById(R.id.HiTeacherMessage);
        helloMsg.setText("Hi "+tName+",");

        ResultsMgtBtn = (ImageButton) findViewById(R.id.teacherResultsMgtBtn);
        AttendanceMgtBtn =(ImageButton) findViewById(R.id.teacherAttendanceMgtBtn);
        FeedbackNotificationMgtBtn =(ImageButton) findViewById(R.id.teacherFeedbackNotificationMgtBtn);
        StudyMaterialsManagementBtn = (ImageButton) findViewById(R.id.teacherStudyMaterialsManagementBtn);


        ResultsMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Result Management Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),TeacherResultManagementDashboard.class);
                i.putExtra("tID",tID);
                i.putExtra("tName",tName);
                startActivity(i);
            }
        });

        AttendanceMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = "teacher";
                Intent i = new Intent(getApplicationContext(),Register_n_View.class);
                i.putExtra("ID",tID);
                i.putExtra("Name",tName);
                i.putExtra("Type",t);
                startActivity(i);
            }
        });


        FeedbackNotificationMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewAllFeedbacks.class);
                startActivity(i);


            }
        });

        StudyMaterialsManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = "teacher";
                Intent i = new Intent(getApplicationContext(),StudyMaterial.class);
                i.putExtra("ID",tID);
                i.putExtra("Name",tName);
                i.putExtra("Type",t);
                startActivity(i);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.regTeacher:
                String t = "teacher";
                Intent i = new Intent(getApplicationContext(),TeacherRegistration.class);
                i.putExtra("ID",tID);
                i.putExtra("Name",tName);
                i.putExtra("Type",t);
                startActivity(i);
                return true;
            case R.id.locateUs:
                Intent intLocateus = new Intent(getApplicationContext(),Locate_Us.class);
                startActivity(intLocateus);
                return true;
            case R.id.contactUs:
                Intent intContactus = new Intent(getApplicationContext(),ContactUs.class);
                startActivity(intContactus);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
