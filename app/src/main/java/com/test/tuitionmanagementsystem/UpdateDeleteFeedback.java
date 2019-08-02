package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateDeleteFeedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_feedback);

        EditText nt1 = findViewById(R.id.editTextID2);
        EditText nt2 = findViewById(R.id.editTextName2);
        EditText nt3 = findViewById(R.id.editTextSubject2);
        EditText nt4 = findViewById(R.id.editTextFeedback2);

        Intent intent2 = getIntent();

        String s1 = intent2.getStringExtra("FirstText");
        String s2 = intent2.getStringExtra("SecondText");
        String s3 = intent2.getStringExtra("ThirdText");
        String s4 = intent2.getStringExtra("ForthText");

        nt1.setText(s1);
        nt2.setText(s2);
        nt3.setText(s3);
        nt4.setText(s4);


    }

    public void sendNewFeedback(View view) {
        Intent intent03 = new Intent(UpdateDeleteFeedback.this, StudentDashboard.class);
        startActivity(intent03);
    }
}
