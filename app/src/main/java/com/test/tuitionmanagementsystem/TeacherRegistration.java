package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class TeacherRegistration extends AppCompatActivity {

    EditText txt_TID, txt_TName, txt_Nic, txt_Phone, txt_Email, txt_Description, txt_Password, txt_Qualification, txt_Specialized;
    Button btn_Register;
    Button btn_camera;
    ImageView cameraPhotovw;
    public Uri imguri;
    private StorageReference mStorageRef;

    private StorageTask uploadTask;
    private String completeImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        txt_TID = findViewById(R.id.txtTID);
        txt_TName = findViewById(R.id.txtTName);
        txt_Nic =findViewById(R.id.txtNic);
        txt_Phone = findViewById(R.id.txtPhone);
        txt_Email = findViewById(R.id.txtEmail);
        txt_Qualification =findViewById(R.id.txtQualification);
        txt_Specialized = findViewById(R.id.txtSpecialized);
        txt_Password = findViewById(R.id.txtPassword);

        btn_Register = findViewById(R.id.btnRegister);
        btn_camera = findViewById(R.id.btnCamera);

        cameraPhotovw = findViewById(R.id.cameraPhoto);

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




                if(TextUtils.isEmpty(TID)||TextUtils.isEmpty(TName)||TextUtils.isEmpty(Nic)||
                        TextUtils.isEmpty(Phone)||TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Description)|| TextUtils.isEmpty(Qualification)||
                        TextUtils.isEmpty(Specialized)|| TextUtils.isEmpty(Password)){
                    Toast.makeText(getApplicationContext(),"All fields should be filled.",Toast.LENGTH_SHORT).show();
                }else{
                    //Add to database
                    //Teacher Details
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                    Teacher teacherobj = new Teacher();

                    uploadPhoto();

                    teacherobj.setTid(TID);
                    teacherobj.settName(TName);
                    teacherobj.setNic(Nic);
                    teacherobj.setQualification(Qualification);
                    teacherobj.setTel(Phone);
                    teacherobj.setEmail(Email);
                    teacherobj.setSpecialized_subject(Specialized);
                    teacherobj.setPhotoLink("");

                    dbRef.child(teacherobj.getTid()).setValue(teacherobj);

                    //Password
                    String saltpwd = PasswordUtils.getSalt(30);
                    String secpwd = PasswordUtils.generateSecurePassword(Password,saltpwd);

                    DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference().child("TeacherCredentials");
                    StaffCredentials credentialsObj = new StaffCredentials();
                    credentialsObj.setSalt(saltpwd);
                    credentialsObj.setSecuredPassword(secpwd);
                    dbRef2.child(teacherobj.getTid()).setValue(credentialsObj);

                    Toast.makeText(getApplicationContext(),"Teacher Registered Successfully.",Toast.LENGTH_SHORT).show();

                }

            }
        });



        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissionREAD_EXTERNAL_STORAGE()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }

            }
        });

    }


    private void uploadPhoto() {
        Long currentTime = System.currentTimeMillis();

        StorageReference teacherRef = mStorageRef.child("TeacherPhotos/"+currentTime+".jpg");

        teacherRef.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getApplicationContext(),"Error"+exception,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
    ) {
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

    public void showDialog(final String msg, final Context context,
                           final String permission) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            cameraPhotovw.setImageBitmap(bitmap);
            imguri = getImageUri(getApplicationContext(),bitmap);

        }
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
