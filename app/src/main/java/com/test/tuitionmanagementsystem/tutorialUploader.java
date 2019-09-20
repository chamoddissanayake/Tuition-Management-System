package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class tutorialUploader extends AppCompatActivity {

    Button btnselectFile, btnUpload;
    TextView txtNotification;
    Uri pdfUri;
    String full_documentLink="";
    EditText etTopic, etDescription;

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

        etTopic = (EditText) findViewById(R.id.etTopic);
        etDescription = (EditText) findViewById(R.id.etDescription);

        btnselectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFileOpen();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etTopic.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter topic",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etDescription.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter description",Toast.LENGTH_SHORT).show();
                }else if(pdfUri == null)
                {
                    Toast.makeText(getApplicationContext(),"Please select a file to upload",Toast.LENGTH_SHORT).show();
                }else{
                    uploadDocument(pdfUri);
                    pdfUri = null;
                }
            }
        });


    }

    private void uploadDocument(Uri pdfUri) {
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

        StorageReference sRef = mStorageReference.child("Uploads/" + System.currentTimeMillis() + ".pdf");
        sRef.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

                        Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_LONG).show();
                        txtNotification.setText("File not selected");
                        full_documentLink  = taskSnapshot.getMetadata().getReference().toString();
                        //Add Record to DB
                        addTutorialUploadingDataToDb();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addTutorialUploadingDataToDb() {
        String inputTopic, inputDescription;
        inputTopic = etTopic.getText().toString();
        inputDescription = etDescription.getText().toString();

        DatabaseReference addTutRef = FirebaseDatabase.getInstance().getReference().child("TutorialUploads");

        TutorialUploads uploadObj = new TutorialUploads();
        uploadObj.setTopic(inputTopic);
        uploadObj.setDescription(inputDescription);
        uploadObj.setUploadPath(full_documentLink);

        addTutRef.push().setValue(uploadObj);
        Toast.makeText(getApplicationContext(),"Record Added successfully",Toast.LENGTH_SHORT).show();

        etDescription.setText("");
        etTopic.setText("");
    }

    private void chooseFileOpen() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data !=  null && data.getData() != null){
            pdfUri = data.getData();
            txtNotification.setText("File Selected.");
        }
    }

}
