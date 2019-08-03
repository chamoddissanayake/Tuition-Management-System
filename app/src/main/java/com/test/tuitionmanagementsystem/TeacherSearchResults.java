package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherSearchResults extends AppCompatActivity {
    TextView TeacherName, TeacherID;
    TextView StudentIDforSearchResults;
    Button buttonSearchResults;
    String studentIDforSearchR;
    private  static final String TAG = "TeacherSearchResults";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search_results);
        Log.d(TAG, "onCreate: Started.");

        Intent intent = getIntent();
        final String tID = intent.getStringExtra("tID");
        final String tName = intent.getStringExtra("tName");

        TeacherName = (TextView) findViewById(R.id.tNamelbl);
        TeacherID = (TextView) findViewById(R.id.tIDlbl);

        TeacherName.setText(tName);
        TeacherID.setText(tID);

        StudentIDforSearchResults = (TextView) findViewById(R.id.studentIDforSearch);
        buttonSearchResults = (Button) findViewById(R.id.btnSearchResult);

        studentIDforSearchR = StudentIDforSearchResults.getText().toString();

        buttonSearchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(studentIDforSearchR.equals("")){
                    Toast.makeText(getApplicationContext(),"Student ID is empty",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Search clicked",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
