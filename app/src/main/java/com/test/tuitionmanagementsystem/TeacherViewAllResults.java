package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherViewAllResults extends AppCompatActivity {

    ListView listView;

    String listStudentId[]={"S001","S002","S003","S004","S005"};
    String listSubjectName[]={"Maths","Maths","Science","Maths","English"};
    String listExamID[]={"E001","E001","E002","E002","E002"};
    String listMark[]={"90","45","57","97","67"};
    String listDocument[]={"aa","bb","cc","dd","ee"};

    String userName, userId, userType;
    TextView tNamelb, tIDlbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_all_results);

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


        MyAdapter adapter = new MyAdapter(this,listStudentId,listSubjectName,listExamID,listMark,listDocument);
        listView.setAdapter(adapter);


    }
}




class MyAdapter extends ArrayAdapter<String>{

    Context context;
    String listStudentId[];
    String listSubjectName[];
    String listExamID[];
    String listMark[];
    String listDocument[];

    public MyAdapter(Context c, String[] listStudentId, String[] listSubjectName, String[] listExamID, String[] listMark, String[] listDocument) {
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

        tvStudentId.setText(listStudentId[position]);
        tvSubjectName.setText(listSubjectName[position]);
        tvExamID.setText(listExamID[position]);
        tvMark.setText(listMark[position]);
        tvDocument.setText(listDocument[position]);

        return result_row;
    }
}
