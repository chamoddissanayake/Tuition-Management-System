package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private Button loginBtn;
    private RadioGroup radioGroup;
    boolean answer = false;
    ProgressBar pb;
    String  A_StudentID, A_name, A_address, A_tel, A_photo_link;
    String  A_TeacherID, A_TeacherName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputUsername = (TextInputLayout) findViewById(R.id.text_input_username);
        textInputPassword = (TextInputLayout) findViewById(R.id.text_input_password);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        radioGroup = (RadioGroup)findViewById(R.id.userTypeRadioBtnGroup);

        pb=findViewById(R.id.progress_loader);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Same code in Enter key listener
                if(validateUsername()){
                    pb.setVisibility(view.VISIBLE);
                    confirmInput();
                }

            }
        });
        textInputUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateUsernameLength();
                validateUsername();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInputPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword();

                if(charSequence.length()-1 >=0 && charSequence.charAt(charSequence.length()-1) == '\n'){
                    Toast.makeText(getApplicationContext(),"Enter pressed.",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public boolean validateUsername(){
        String usernameInput = textInputUsername.getEditText().getText().toString();

        if(usernameInput.isEmpty()){
            textInputUsername.setError("User Name cannot be empty");
            return false;
        }else{
                textInputUsername.setError(null);
                return  true;
            }
    }

    public boolean validateUsernameLength(){
        String usernameInput = textInputUsername.getEditText().getText().toString();

         if(usernameInput.length()>15){
            textInputUsername.setError("Username is too long.");
            return false;
        }else{
            textInputUsername.setError(null);
            return  true;
        }
    }

    public boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString();
        if(passwordInput.isEmpty()){
            textInputPassword.setError("Password cannot be empty.");
            return false;
        }else{
            textInputPassword.setError(null);
            return true;
        }
    }

    private void confirmInput() {
        if(!validateUsername()| !validatePassword()| !validateUsernameLength()){
            return;
        }else{
           // Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();

            //Radio Button test - start
            int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();

            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();

            if(selectedRadioButtonText.equals("Student")){

                    validateStudentCredentials();

            }else if(selectedRadioButtonText.equals("Teacher")){
//                if(validateTeacherCredentials()){
                    validateTeacherCredentials();
//                    //If found fetch Teacher data(id, name....) from database.
//                    String usernameInput = textInputUsername.getEditText().getText().toString();
//                    String userpasswordInput = textInputPassword.getEditText().getText().toString();
//                    //   fetchTeacherDataFromDB(usernameInput,userpasswordInput);    ->Return details of the teacher.
//
//                    //Sample values
//                    String  TeacherID = "STF001";
//                    String name = "Saman";
//
//
//                    Intent i = new Intent(getApplicationContext(),TeacherDashboard.class);
//                    i.putExtra("TeacherID",TeacherID);
//                    i.putExtra("tName",name);
//                    startActivity(i);

//                }else{
//                    //Teacher credentials are incorrect.
//                    Toast.makeText(getApplicationContext(),"Teacher: Your Username or Password Incorrect.",Toast.LENGTH_SHORT).show();
//                }
            }


            //Radio Button test -end

        }

    }

    private boolean validateTeacherCredentials() {
        final String usernameInput = textInputUsername.getEditText().getText().toString();
        final String userpasswordInput = textInputPassword.getEditText().getText().toString();
        //Teacher Credentials validation is here.

        DatabaseReference dbRef_Validate = FirebaseDatabase.getInstance().getReference().child("TeacherCredentials");
        dbRef_Validate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(usernameInput)){

                    String saltFormDb = dataSnapshot.child(usernameInput).child("salt").getValue().toString();
                    String secPwdFromDb = dataSnapshot.child(usernameInput).child("securedPassword").getValue().toString();
                    if(PasswordUtils.verifyUserPassword(userpasswordInput,secPwdFromDb,saltFormDb)){
                        answer = true;

                        getTeacherData();
                    }else{
                        answer = false;
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Password Incorrect",Toast.LENGTH_SHORT).show();
                    };

                }else{
                    Toast.makeText(getApplicationContext(),"Teacher not found",Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    answer = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                answer = false;
            }
        });

        return answer;
    }

    private void getTeacherData() {
        //If found fetch Student data(id, name....) from database.
        final String usernameInput = textInputUsername.getEditText().getText().toString();
        final String userpasswordInput = textInputPassword.getEditText().getText().toString();
        //   fetchStudentDataFromDB(usernameInput,userpasswordInput);    ->Return details of the Teacher.

        final DatabaseReference readLoginRef =  FirebaseDatabase.getInstance().getReference().child("Teacher");
        readLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(usernameInput)){

                    A_TeacherID = dataSnapshot.child(usernameInput).child("tid").getValue().toString();
                    A_TeacherName = dataSnapshot.child(usernameInput).child("tName").getValue().toString();
                    pb.setVisibility(View.GONE);


                    Intent i = new Intent(getApplicationContext(),TeacherDashboard.class);
                    i.putExtra("TeacherID",A_TeacherID);
                    i.putExtra("tName",A_TeacherName);
                    pb.setVisibility(View.GONE);
                    startActivity(i);

                }else{
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Error occurred.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private boolean validateStudentCredentials() {
        final String usernameInput = textInputUsername.getEditText().getText().toString();
        final String userpasswordInput = textInputPassword.getEditText().getText().toString();

        //Student Credentials validation is here.


        DatabaseReference dbRef_Validate = FirebaseDatabase.getInstance().getReference().child("StudentCredentials");
        dbRef_Validate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(usernameInput)){

                    String saltFormDb = dataSnapshot.child(usernameInput).child("salt").getValue().toString();
                    String secPwdFromDb = dataSnapshot.child(usernameInput).child("securedPassword").getValue().toString();
                    if(PasswordUtils.verifyUserPassword(userpasswordInput,secPwdFromDb,saltFormDb)){
                        answer = true;

                        getUserData();
                    }else{
                        answer = false;
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Password Incorrect",Toast.LENGTH_SHORT).show();
                    };

                }else{
                    Toast.makeText(getApplicationContext(),"Student not found",Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    answer = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                answer = false;
            }
        });

        return answer;
    }


    public void getUserData(){

//        pb.dismiss()
        //If found fetch Student data(id, name....) from database.
        final String usernameInput = textInputUsername.getEditText().getText().toString();
        final String userpasswordInput = textInputPassword.getEditText().getText().toString();
        //   fetchStudentDataFromDB(usernameInput,userpasswordInput);    ->Return details of the student.

        final DatabaseReference readLoginRef =  FirebaseDatabase.getInstance().getReference().child("StudentDetails");
        readLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(usernameInput)){
                    A_name = dataSnapshot.child(usernameInput).child("studentName").getValue().toString();
                    A_photo_link = dataSnapshot.child(usernameInput).child("photoLink").getValue().toString();
                    A_tel = dataSnapshot.child(usernameInput).child("tel").getValue().toString();
                    A_StudentID = dataSnapshot.child(usernameInput).child("admissionNo").getValue().toString();
                    A_address= dataSnapshot.child(usernameInput).child("address").getValue().toString();

                    pb.setVisibility(View.GONE);


                    Intent i = new Intent(getApplicationContext(),StudentDashboard.class);
                    i.putExtra("StudentID",A_StudentID);
                    i.putExtra("sName",A_name);
                    i.putExtra("address",A_address);
                    i.putExtra("tel",A_tel);
                    i.putExtra("photo_link",A_photo_link);
                    startActivity(i);

                }else{
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Error occurred.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //Sample values
//                        String  A_StudentID = "S001";
//                        String A_name = "Isuru";
//                        String A_address = "Colombo";
//                        String A_tel = "0771234567";
//                        String A_photo_link ="https://firebasestorage.googleapis.com/v0/b/tuitionmanagementsystem-1af02.appspot.com/o/StudentPhotos%2F1568433584744.jpg?alt=media&token=f9653d33-ffe5-4231-b6ae-282caa6c3780";

    }
}



