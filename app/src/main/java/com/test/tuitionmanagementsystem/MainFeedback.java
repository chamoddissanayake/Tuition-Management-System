package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainFeedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feedback);
    }

    public void addFeedback(View view){
        Intent in1 = new Intent(MainFeedback.this,FeedBack.class );
        startActivity(in1);
    }
    public void viewFeedback(View view){
        Intent in2 = new Intent(MainFeedback.this,searchFeedback.class );
        startActivity(in2);
    }
}
