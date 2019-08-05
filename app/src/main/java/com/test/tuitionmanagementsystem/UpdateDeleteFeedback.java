package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class UpdateDeleteFeedback extends AppCompatActivity {
    Button update, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_feedback);

        final EditText nt1 = findViewById(R.id.editTextID2);
        final EditText nt2 = findViewById(R.id.editTextName2);
        final EditText nt3 = findViewById(R.id.editTextSubject2);
        final EditText nt4 = findViewById(R.id.editTextFeedback2);

        Intent intent2 = getIntent();

        String s1 = intent2.getStringExtra("FirstText");
        String s2 = intent2.getStringExtra("SecondText");
        String s3 = intent2.getStringExtra("ThirdText");
        String s4 = intent2.getStringExtra("ForthText");

        nt1.setText(s1);
        nt2.setText(s2);
        nt3.setText(s3);
        nt4.setText(s4);

        update = (Button) findViewById(R.id.btnUpdateFeedback);
        delete = (Button) findViewById(R.id.btnDeleteFeedback);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = nt1.getText().toString();
                String str2 = nt2.getText().toString();
                String str3 = nt3.getText().toString();
                String str4 = nt4.getText().toString();

                if(str1.equals("")||str2.equals("")||str3.equals("")||str4.equals("")){
                    Toast.makeText(getApplicationContext(),"All columns should be filled.",Toast.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(view," Feedback Updated Successfully",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = nt1.getText().toString();
                String str2 = nt2.getText().toString();
                String str3 = nt3.getText().toString();
                String str4 = nt4.getText().toString();

                if(str1.equals("")||str2.equals("")||str3.equals("")||str4.equals("")){
                    Toast.makeText(getApplicationContext(),"All columns should be filled.",Toast.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(view," Feedback Deleted Successfully",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
            }
        });


    }

    public void sendNewFeedback(View view) {
        Intent intent03 = new Intent(UpdateDeleteFeedback.this, StudentDashboard.class);
        startActivity(intent03);
    }
}
