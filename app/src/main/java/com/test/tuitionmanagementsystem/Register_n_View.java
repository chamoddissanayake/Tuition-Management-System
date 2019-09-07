package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_n_View extends AppCompatActivity {

    Button Register, Editbtn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_n__view);

    Register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i  = new Intent(getApplicationContext(),StudentRegistration.class);
            startActivity(i);
        }
    });
    Editbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(),EditRegistration_Details.class);
            startActivity(i);
        }
    });

    }


}
