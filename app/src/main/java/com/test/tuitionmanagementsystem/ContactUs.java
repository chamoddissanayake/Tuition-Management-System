package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.Manifest.permission.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    ImageView phoneImage, whatsappLogo, smsLogo, emailLogo;
    EditText message;
    SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int SMS_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        phoneImage = (ImageView) findViewById(R.id.phoneImg);
        whatsappLogo = (ImageView) findViewById(R.id.ivWhatsappLogo);
        message = (EditText) findViewById(R.id.etMsg);
        smsLogo = (ImageView) findViewById(R.id.ivSmsLogo);
        emailLogo = (ImageView) findViewById(R.id.ivEmailLogo);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

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
                        String url = "https://api.whatsapp.com/send?phone=" + CommonConstants.whatsappReceiverPhoneNumber+ "&text=" + message.getText().toString();
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

                //send sms start
                    try{
                        SendMessage(CommonConstants.smsReceiverPhoneNumber, message.getText().toString());
                    }catch (Exception ex1){
                        Toast.makeText(getApplicationContext(),"Error occurred.",Toast.LENGTH_SHORT).show();
                    }

                //send sms end
                }


            }
        });


        emailLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(message.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please Enter Message",Toast.LENGTH_SHORT).show();
                }else{
                    sendEmail(message.getText().toString());
                }

            }
        });

//Start Permission

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactUs.this, Manifest.permission.SEND_SMS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Need SMS Permission");
                builder.setMessage("This app needs SMS permission to send Messages.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(ContactUs.this,
                                new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.SEND_SMS, false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Need SMS Permission");
                builder.setMessage("This app needs SMS permission to send Messages.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(),
                                "Go to Permissions to Grant SMS permissions", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.SEND_SMS}
                        , SMS_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.SEND_SMS, true);
            editor.commit();

        }
//End permission
    }

    private void sendEmail(String msg) {

        String[] TO = {CommonConstants.emailTo};
//        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Toast.makeText(getApplicationContext(),"Finished sending email...",Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),"There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }


    public void SendMessage(String strMobileNo, String strMessage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);
            Toast.makeText(getApplicationContext(), "Your Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
}
