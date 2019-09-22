package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class StudentRegistration extends AppCompatActivity {

    //declare edittext
   // TextView tx1, tx2;
    EditText etsName, etAdmissionNo, etAddress, etContactNo, etPassword, etRePassword;
    ImageView imgViewInputPhoto;

    public Uri imguri;
    StorageReference mStorageRef;

    Button btnSave, btnTakePhoto, btnChoosePhoto;
    DatabaseReference dbRef;

    private StorageTask uploadTask;

    String ID = "";
    String Name = "";
    String Type = "";

    String completeImagePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Name = intent.getStringExtra("Name");
        Type = intent.getStringExtra("Type");

        etsName = (EditText) findViewById(R.id.etsName);
        etAdmissionNo = (EditText) findViewById(R.id.etAdmissionNo);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etContactNo = (EditText) findViewById(R.id.etContactNo);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);

        btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnChoosePhoto = (Button) findViewById(R.id.btnChoosePhoto);

        imgViewInputPhoto= findViewById(R.id.imgViewInputPhoto);

//        tx1 = findViewById(R.id.tNamelbl);
//        tx2 = findViewById(R.id.tIDlbl);

//        tx1.setText(ID);
//        tx2.setText(Name);

        btnSave = findViewById(R.id.Submitbutton);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    if (TextUtils.isEmpty(etsName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etAdmissionNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Admission No", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etPassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(etRePassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Re-Enter Password", Toast.LENGTH_SHORT).show();
                    else if (!etPassword.getText().toString().equals(etRePassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Password and Re-Password not equal", Toast.LENGTH_SHORT).show();
                    else if (imgViewInputPhoto.getDrawable()==null)
                        Toast.makeText(getApplicationContext(), "Please Take a photo of the Student from camera or choose existing image", Toast.LENGTH_SHORT).show();
                    else {

                        if(uploadTask !=null && uploadTask.isInProgress()){
                            Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Uploading... ", Toast.LENGTH_LONG).show();
                            uploadImage();
                            //Register Student Details in 'StudentDetails'

                            Toast.makeText(getApplicationContext(),"New Student was registered successfully.",Toast.LENGTH_LONG).show();
                            imgViewInputPhoto.setImageDrawable(null);
                        }



                    }

                }
                catch ( NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Error occurred."+e,Toast.LENGTH_LONG).show();
                }catch ( Exception e){
                    Toast.makeText(getApplicationContext(),"Error occurred."+e,Toast.LENGTH_LONG).show();
                }

            }


        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }

            }
        });


        btnChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }
        });

        //End of onCreate()
    }

    private void uploadImage() {


            Long currentTime = System.currentTimeMillis();

            mStorageRef = FirebaseStorage.getInstance().getReference();
            String tempFileName= currentTime + "." + getExtension(imguri);
            final StorageReference  Ref = mStorageRef.child("StudentPhotos").child(tempFileName);

            uploadTask = Ref.putFile(imguri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return Ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    completeImagePath = task.getResult().toString();
                    // cant change value of  'completeImagePath' ????
                    registerStudent();

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }


    private void registerStudent() {

        StudentDetails_tb stdtb1 = new StudentDetails_tb();
        dbRef = FirebaseDatabase.getInstance().getReference().child("StudentDetails");

        stdtb1.setStudentName(etsName.getText().toString().trim());
        stdtb1.setAdmissionNo(etAdmissionNo.getText().toString().trim());
        stdtb1.setAddress(etAddress.getText().toString().trim());
        stdtb1.setTel(etContactNo.getText().toString().trim());
        stdtb1.setPhotoLink(completeImagePath);

        dbRef.child(stdtb1.getAdmissionNo()).setValue(stdtb1);

        //Save Student credentials in 'StudentCredentials'

        StudentCredentials stdCredObj = new StudentCredentials();
        dbRef = FirebaseDatabase.getInstance().getReference().child("StudentCredentials");

        String saltPwd = PasswordUtils.getSalt(30);
        String getSecured = PasswordUtils.generateSecurePassword(etPassword.getText().toString(),saltPwd);
        stdCredObj.setsID(etAdmissionNo.getText().toString().trim());

        stdCredObj.setSalt(saltPwd);
        stdCredObj.setSecuredPassword(getSecured);

        dbRef.child(stdCredObj.getsID()).setValue(stdCredObj);

        Toast.makeText(getApplicationContext(), "Student Registered Successfully.", Toast.LENGTH_SHORT).show();

        clearTextboxes();
    }

    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private void clearTextboxes() {
        etsName.setText("");
        etAdmissionNo.setText("");
        etAddress.setText("");
        etContactNo.setText("");
        etPassword.setText("");
        etRePassword.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data !=  null && data.getData() != null){
            imguri = data.getData();
            imgViewInputPhoto.setImageURI(imguri);

        }else if(requestCode==0 ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgViewInputPhoto.setImageBitmap(bitmap);
            imguri = getImageUri(getApplicationContext(),bitmap);

        }
    }


    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    public boolean checkPermissionREAD_EXTERNAL_STORAGE( ) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) this,
                                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
//                    Toast.makeText(Login.this, "GET_ACCOUNTS Denied",
//                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }



}