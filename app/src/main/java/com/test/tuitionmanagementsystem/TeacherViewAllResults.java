package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class TeacherViewAllResults extends AppCompatActivity {

    ListView listView;

//    String listStudentId[]={"S001","S002","S003","S004","S005"};
//    String listSubjectName[]={"Maths","Maths","Science","Maths","English"};
//    String listExamID[]={"E001","E001","E002","E002","E002"};
//    String listMark[]={"90","45","57","97","67"};
//    String listDocument[]={"aa","bb","cc","dd","ee"};




    String userName, userId, userType;
    TextView tNamelb, tIDlbl;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_all_results);

        final ArrayList<String> listStudentId = new ArrayList<String>();
        final ArrayList<String> listSubjectName = new ArrayList<String>();
        final ArrayList<String> listExamID = new ArrayList<String>();
        final ArrayList<String> listMark = new ArrayList<String>();
        final ArrayList<String> listDocument = new ArrayList<String>();

        pb=findViewById(R.id.progress_loader);

        pb.setVisibility(View.VISIBLE);

//        listStudentId.add("S001");listStudentId.add("S002");listStudentId.add("S003");listStudentId.add("S004");listStudentId.add("S005");
//        listSubjectName.add("Maths");listSubjectName.add("Maths");listSubjectName.add("English");listSubjectName.add("Maths");listSubjectName.add("Science");
//        listExamID.add("E001");listExamID.add("E001");listExamID.add("E001");listExamID.add("E001");listExamID.add("E001");
//        listMark.add("98");listMark.add("90");listMark.add("78");listMark.add("80");listMark.add("65");
//        listDocument.add("aaaa");listDocument.add("aaaa");listDocument.add("aaaa");listDocument.add("aaaa");listDocument.add("aaaa");

        Intent intent = getIntent();
        userName = intent.getStringExtra("ID");
        userId = intent.getStringExtra("Name");
        userType = intent.getStringExtra("Type");

        tNamelb = (TextView) findViewById(R.id.tNamelbl);
        tIDlbl = (TextView) findViewById(R.id.tIDlbl);

        tNamelb.setText(userName);
        tIDlbl.setText(userId);

        listView = (ListView) findViewById(R.id.listview);


        final ArrayList<Student_Take_Exam> student_take_examsArrayList = new ArrayList<>();

        final ArrayList<String>  ExamIDStrList = new ArrayList<>();
        DatabaseReference readRef1 = FirebaseDatabase.getInstance().getReference().child("Student_take_exam");
        readRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //    Toast.makeText(TeacherViewAllResults.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

//                ArrayList Examlist = new ArrayList();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                     String examidStr = dsp.getKey();

                    ExamIDStrList.add(examidStr);

                     //Toast.makeText(TeacherViewAllResults.this, examidStr+"", Toast.LENGTH_SHORT).show();
                }
                for(int i=0;i<ExamIDStrList.size();i++){
                    // Now Examlist has E001 , E002
                    final DatabaseReference refe1 = FirebaseDatabase.getInstance().getReference().child("Student_take_exam").child(ExamIDStrList.get(i));
                    refe1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){

                                ArrayList<Student> studentsList = new ArrayList<>();

                                for (DataSnapshot dsp1 : dataSnapshot.getChildren()) {

                                    final String student_ID =dsp1.getKey();
                                   // long a = dataSnapshot.getChildrenCount();

                                    refe1.child(student_ID);

                                    refe1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Student_Take_Exam student_take_examObj = new Student_Take_Exam();


                                            String sid_from_db = dataSnapshot.child(student_ID).child("sID").getValue().toString();
                                            String subName_from_db = dataSnapshot.child(student_ID).child("subName").getValue().toString();
                                            String mark_from_db = dataSnapshot.child(student_ID).child("mark").getValue().toString();
                                            String examID_from_db = dataSnapshot.child(student_ID).child("examID").getValue().toString();
                                            String documentLink_from_db =dataSnapshot.child(student_ID).child("documentLink").getValue().toString();


                                            student_take_examObj.setsID(sid_from_db);
                                            student_take_examObj.setSubName(subName_from_db);
                                            student_take_examObj.setMark(Integer.parseInt(mark_from_db) );
                                            student_take_examObj.setExamID(examID_from_db);
                                            student_take_examObj.setDocumentLink(documentLink_from_db);

                                            student_take_examsArrayList.add(student_take_examObj);

                                            listStudentId.add(sid_from_db);
                                            listSubjectName.add(subName_from_db) ;
                                            listExamID.add(examID_from_db) ;
                                            listMark.add(mark_from_db) ;
                                            listDocument.add(documentLink_from_db) ;

                                            MyAdapter adapter = new MyAdapter(getApplicationContext(),listStudentId,listSubjectName,listExamID,listMark,listDocument);

                                            listView.setAdapter(adapter);

                                            pb.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv= (TextView)view.findViewById(R.id.tvDocument);
                ImageView iv = (ImageView)view.findViewById(R.id.resultDownloadImage);
                TextView tvdoc = (TextView)view.findViewById(R.id.tvDocument);
                final String fullLink = tvdoc.getText().toString();
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Image clicked Code - start
                        Toast.makeText(getApplicationContext(),""+fullLink,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Download Starting... ",Toast.LENGTH_SHORT).show();
                        startDownloadResultDocument(fullLink);
                        //Image clicked code - end
                    }
                });

            }
        });


    }

    private void startDownloadResultDocument(String fullLink) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(fullLink);

        final File rootPath = new File(Environment.getExternalStorageDirectory(), " Results_Downloads");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, System.currentTimeMillis() + ".pdf");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "File Downloaded Successfully to Results_Downloads folder.", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Download Incompleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Permission
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
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                                new String[]{permission},
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


class MyAdapter extends ArrayAdapter<String>{

    Context context;
    ArrayList listStudentId;
    ArrayList listSubjectName;
    ArrayList listExamID;
    ArrayList listMark;
    ArrayList listDocument;

    public MyAdapter(Context c, ArrayList listStudentId, ArrayList listSubjectName, ArrayList listExamID, ArrayList listMark, ArrayList listDocument) {
        super(c, R.layout.result_row,R.id.textView,listStudentId);

        this.listStudentId = listStudentId;
        this.listSubjectName = listSubjectName;
        this.listExamID = listExamID;
        this.listMark = listMark;
        this.listDocument = listDocument;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View result_row = layoutInflater.inflate(R.layout.result_row,parent,false);

        TextView tvStudentId = (TextView) result_row.findViewById(R.id.tvStudentId);
        TextView tvSubjectName= (TextView) result_row.findViewById(R.id.tvSubjectName);
        TextView tvExamID= (TextView) result_row.findViewById(R.id.tvExamID);
        TextView tvMark= (TextView) result_row.findViewById(R.id.tvMark);
        TextView tvDocument= (TextView) result_row.findViewById(R.id.tvDocument);
        ImageView resultDownloadImage = (ImageView) result_row.findViewById(R.id.resultDownloadImage);

        tvStudentId.setText(listStudentId.get(position).toString());
        tvSubjectName.setText(listSubjectName.get(position).toString());
        tvExamID.setText(listExamID.get(position).toString());
        tvMark.setText(listMark.get(position).toString());
        tvDocument.setText(listDocument.get(position).toString());
//        final String doc_link = listDocument.get(position).toString();
//
//        resultDownloadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkPermissionREAD_EXTERNAL_STORAGE()) {
//                    downloadResultDocumenent(doc_link);
//                }
//
//            }
//        });


        return result_row;
    }











}

