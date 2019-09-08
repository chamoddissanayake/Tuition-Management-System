package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class tutorialUploader extends AppCompatActivity {

    Button btnselectFile, btnUpload;
    TextView txtNotification;
    Uri pdfUri;

    FirebaseStorage storage;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_uploader);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        btnselectFile = findViewById(R.id.btnselectFile);
        btnUpload = findViewById(R.id.btnUpload);
        txtNotification = findViewById(R.id.txtNotification);

        btnselectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(tutorialUploader.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }

                else{
                    ActivityCompat.requestPermissions(tutorialUploader.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pdfUri != null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(tutorialUploader.this,"Select a file", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void uploadFile(Uri pdfUri) {

        StorageReference storageReference = storage.getReference();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }

        else

            Toast.makeText(tutorialUploader.this, "Please provide Permisson", Toast.LENGTH_SHORT).show();


    }

    private void selectPdf() {

        Intent i55 = new Intent();
        i55.setType("application/pdf");
        i55.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i55,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 86 && resultCode == RESULT_OK && data != null){

            pdfUri = data.getData();
        }
        else{

            Toast.makeText(tutorialUploader.this,"Please select a file", Toast.LENGTH_SHORT).show();

        }
    }
}
