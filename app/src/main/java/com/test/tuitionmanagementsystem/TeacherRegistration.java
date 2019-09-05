package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherRegistration extends AppCompatActivity {

    EditText txt_TID, txt_TName, txt_Nic, txt_Phone, txt_Email, txt_Description, txt_Password, txt_Qualification, txt_Specialized;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        txt_TID = findViewById(R.id.txtTID);
        txt_TName = findViewById(R.id.txtTName);
        txt_Nic =findViewById(R.id.txtNic);
        txt_Phone = findViewById(R.id.txtPhone);
        txt_Email = findViewById(R.id.txtEmail);
        txt_Qualification =findViewById(R.id.txtQualification);
        txt_Specialized = findViewById(R.id.txtSpecialized);
        txt_Password = findViewById(R.id.txtPassword);

        btn_Register = findViewById(R.id.btnRegister);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TID, TName, Nic, Phone, Email, Description, Password, Qualification, Specialized;

                TID = txt_TID.getText().toString().trim();
                TName = txt_TName.getText().toString().trim();
                Nic = txt_Nic.getText().toString().trim();
                Phone = txt_Phone.getText().toString().trim();
                Email = txt_Email.getText().toString().trim();
                Description = txt_Email.getText().toString().trim();
                Qualification = txt_Qualification.getText().toString().trim();
                Password = txt_Password.getText().toString().trim();
                Specialized = txt_Specialized.getText().toString().trim();



                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                if(TextUtils.isEmpty(TID)||TextUtils.isEmpty(TName)||TextUtils.isEmpty(Nic)||
                        TextUtils.isEmpty(Phone)||TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Description)|| TextUtils.isEmpty(Qualification)||
                        TextUtils.isEmpty(Specialized)|| TextUtils.isEmpty(Password)){
                    Toast.makeText(getApplicationContext(),"All fields should be filled.",Toast.LENGTH_SHORT).show();
                }else{
                    //Add to database
                    Teacher teacherobj = new Teacher();

                    teacherobj.setTid(TID);
                    teacherobj.settName(TName);
                    teacherobj.setNic(Nic);
                    teacherobj.setQualification(Qualification);
                    teacherobj.setTel(Phone);
                    teacherobj.setEmail(Email);
                    teacherobj.setSpecialized_subject(Specialized);
                    teacherobj.setPhotoLink("aaaa");

                    dbRef.child(teacherobj.getTid()).setValue(teacherobj);
                    Toast.makeText(getApplicationContext(),"Teacher Registered Successfully.",Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
}
