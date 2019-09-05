package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Homework extends AppCompatActivity {
    EditText txtdesc;
    Button btnsubmithome;
    Button update,delete;

    EditText txthomeworkID, txtsubName, txtHomeworkDescription;

    DatabaseReference dbRef;
    Homework_tbl hwtbl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        txthomeworkID = findViewById(R.id.txt_homeworkID);
        txtsubName = findViewById(R.id.txt_subName);
        txtHomeworkDescription = findViewById(R.id.txt_HomeworkDescription);

        txtdesc = (EditText)findViewById(R.id.txt_HomeworkDescription);
        btnsubmithome = (Button)findViewById(R.id.btnSubmitHomework);

        hwtbl = new Homework_tbl();

        btnsubmithome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Homework_tbl");

                /*try{

                    if(TextUtils.isEmpty(txthomeworkID.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter homework ID",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtsubName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter the subject name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtHomeworkDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter the description",Toast.LENGTH_SHORT).show();

                    else{

                        hwtbl.setHomeworkID(txthomeworkID.getText().toString().trim());
                        hwtbl.setSubName(txtsubName.getText().toString().trim());
                        hwtbl.setHoework(txtHomeworkDescription.getText().toString().trim());

                        dbRef.push().setValue(hwtbl);

                        Toast.makeText(getApplicationContext(),"Data saves successfully",Toast.LENGTH_SHORT).show();
                        clearControls();


                    }

                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid ID",Toast.LENGTH_SHORT).show();
                }*/

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

    private void clearControls(){
        txthomeworkID.setText("");
        txtsubName.setText("");
        txtHomeworkDescription.setText("");
    }






}
