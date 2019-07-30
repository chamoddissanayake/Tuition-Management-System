package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private Button loginBtn;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputUsername = (TextInputLayout) findViewById(R.id.text_input_username);
        textInputPassword = (TextInputLayout) findViewById(R.id.text_input_password);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        radioGroup = (RadioGroup)findViewById(R.id.userTypeRadioBtnGroup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput();



            }
        });
        textInputUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // Toast.makeText(getApplicationContext(),"aaaa",Toast.LENGTH_SHORT).show();
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

            if(selectedRadioButtonText.equals("Teacher")){
                Toast.makeText(getApplicationContext(),"Hello "+selectedRadioButtonText,Toast.LENGTH_SHORT).show();
            }else if(selectedRadioButtonText.equals("Student")){
                Toast.makeText(getApplicationContext(),"Hello "+selectedRadioButtonText,Toast.LENGTH_SHORT).show();
            }


            //Radio Button test -end

        }

    }

}



