package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TeacherSearchResults extends AppCompatActivity {
    TextView TeacherName, TeacherID;
    TextView StudentIDforSearchResults;
    Button buttonSearchResults;
    String studentIDforSearchR;
    private  static final String TAG = "TeacherSearchResults";

    static String[][] resultTable ={
            {"S001","Vimukthi","Science","E001","78"},
            {"S002","Muditha","Science","E001","66"},
            {"S003","Buddhika","Science","E001","90"},
            {"S004","Amila","Science","E001","82"},
            {"S005","Dilshan","Science","E001","52"},
            {"S006","Gayan","Science","E001","67"},
            {"S007","Sameera","Science","E001","71"},
            {"S008","Thilina","Science","E001","88"},
            {"S009","Savindu","Science","E001","44"},
            {"S010","Pubudu","Science","E001","100"},
            {"S011","Kanishka","Science","E001","62"},
    };
    static String[] resultTableHeaders={"No","Name","Subject","ExamID","Mark"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
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



        final TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tblResults);
        tableView.setColumnCount(5);

        tableView.setBackgroundColor(Color.parseColor("#ADD8E6"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,resultTableHeaders));
        tableView.setColumnCount(5);

        tableView.setDataAdapter(new SimpleTableDataAdapter(TeacherSearchResults.this,resultTable));


    }
}
