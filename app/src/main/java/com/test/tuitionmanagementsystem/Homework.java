package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Homework extends AppCompatActivity {
    EditText txtdesc;
    Button btnsubmithome;
    Button update,delete;


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
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view," updated successfully",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view," Deleted successfully",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
        });

    }




}
