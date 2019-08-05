package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Todolist extends AppCompatActivity {
    TextView txt22;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

         txt22= findViewById(R.id.textveiwdesc);
         st = getIntent().getExtras().getString("Desc");
         txt22.setText(st);
         //txt22.setText(getIntent().getStringExtra("Desc"));

    }
}
