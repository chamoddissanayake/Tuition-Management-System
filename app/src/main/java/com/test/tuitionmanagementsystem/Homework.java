package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Homework extends AppCompatActivity {
    EditText txtdesc;
    Button btnsubmithome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        txtdesc = (EditText)findViewById(R.id.txtHomeworkDescription);
        btnsubmithome = (Button)findViewById(R.id.btnSubmitHomework);

        btnsubmithome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itodo = new Intent(Homework.this,Todolist.class);
                String nameval = txtdesc.getText().toString();
                itodo.putExtra("Desc ",nameval);
                startActivity(itodo);
                finish();
            }
        });

    }




}
