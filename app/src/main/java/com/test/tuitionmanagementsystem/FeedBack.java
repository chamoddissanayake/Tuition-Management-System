package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class FeedBack extends AppCompatActivity {

    EditText t1;
    EditText t2;
    EditText t3;
    EditText t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Intent intent = getIntent();
        String sID = intent.getStringExtra("StudentID");
        String sName = intent.getStringExtra("sName");

        t1 = findViewById(R.id.editTextID2);
        t2 = findViewById(R.id.editTextName2);
        t3 = findViewById(R.id.editTextSubject);
        t4 = findViewById(R.id.editTextFeedback);

        t1.setText(sID);
        t2.setText(sName);


    }

    public void sendFeedback(View view){


        String msg1 = t1.getText().toString();
        String msg2 = t2.getText().toString();
        String msg3 = t3.getText().toString();
        String msg4 = t4.getText().toString();

        if(msg1.equals("")||msg2.equals("")||msg3.equals("")||msg4.equals("")){
            Toast.makeText(getApplicationContext(),"All fields must be filled.",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent01 = new Intent(FeedBack.this,UpdateDeleteFeedback.class);
            intent01.putExtra("FirstText",msg1);
            intent01.putExtra("SecondText",msg2);
            intent01.putExtra("ThirdText",msg3);
            intent01.putExtra("ForthText",msg4);

            startActivity(intent01);
        }


    }
}
