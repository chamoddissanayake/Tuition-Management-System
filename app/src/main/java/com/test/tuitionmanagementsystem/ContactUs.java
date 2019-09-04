package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    ImageView phoneImage, whatsappLogo, smsLogo;
    EditText message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        phoneImage = (ImageView) findViewById(R.id.phoneImg);
        whatsappLogo = (ImageView) findViewById(R.id.ivWhatsappLogo);
        message = (EditText) findViewById(R.id.etMsg);
        smsLogo = (ImageView) findViewById(R.id.ivSmsLogo);

        phoneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", CommonConstants.callReceiverPhoneNumber, null));
                startActivity(intent);

            }
        });


        whatsappLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(message.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please Enter Message",Toast.LENGTH_SHORT).show();
                }else{

                    try{
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_VIEW);
                        String url = "https://api.whatsapp.com/send?phone=" + "94778695902" + "&text=" + message.getText().toString();
                        sendIntent.setData(Uri.parse(url));
                        startActivity(sendIntent);
                    }catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Error Occured.",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        smsLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(message.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please Enter Message",Toast.LENGTH_SHORT).show();
                }else{




                }


            }
        });


    }
}
