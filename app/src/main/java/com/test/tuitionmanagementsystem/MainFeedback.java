package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainFeedback extends AppCompatActivity {
    String sID;
    String sName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feedback);

        Intent intent = getIntent();
         sID = intent.getStringExtra("StudentID");
         sName = intent.getStringExtra("sName");

    }


    public void addFeedback(View view){
        Intent in1 = new Intent(MainFeedback.this,FeedBack.class );
        in1.putExtra("sID",sID);
        in1.putExtra("sName",sName);
        startActivity(in1);
    }
    public void viewFeedback(View view){
        Intent in2 = new Intent(MainFeedback.this,searchFeedback.class );
        in2.putExtra("sID",sID);
        in2.putExtra("sName",sName);
        startActivity(in2);
    }
}
