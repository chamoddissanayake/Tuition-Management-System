package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class ViewAllFeedbacks extends AppCompatActivity {

    ListView lv;
    ArrayList<String> feedbackID;
    ArrayList<String> studentID;
    ArrayList<String> studentName;
    ArrayList<String> subject;
    ArrayList<String> feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_feedbacks);

        //set id
        lv = (ListView) findViewById(R.id.fbListview);

        feedbackID = new ArrayList<String>();
        studentID = new ArrayList<String>();
        studentName = new ArrayList<String>();
        subject = new ArrayList<String>();
        feedback = new ArrayList<String>();

//        feedbackID.add("001");feedbackID.add("002");feedbackID.add("003");feedbackID.add("004");feedbackID.add("005");feedbackID.add("006");feedbackID.add("007");
//        studentID.add("S001");studentID.add("S002");studentID.add("S003");studentID.add("S004");studentID.add("S005");studentID.add("S006");studentID.add("S007");
//        studentName.add("Isuru");studentName.add("Gayan");studentName.add("Pasan");studentName.add("Sahan");studentName.add("Hasitha");studentName.add("Wikum");studentName.add("Nipuna");
//        subject.add("English");subject.add("Maths");subject.add("Science");subject.add("Science");subject.add("English");subject.add("Maths");subject.add("Maths");
//        feedback.add("good");feedback.add("bad");feedback.add("superb");feedback.add("excellent");feedback.add("good");feedback.add("good");feedback.add("average");


        final ArrayList<String> FeedbackStrList = new ArrayList<>();
        DatabaseReference readRef1 = FirebaseDatabase.getInstance().getReference().child("Feedback");
        readRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //dataSnapshot.getChildrenCount();
                Toast.makeText(getApplicationContext(),dataSnapshot.getChildrenCount()+"",Toast.LENGTH_LONG).show();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String feedbackStr = dsp.getKey();
                    FeedbackStrList.add(feedbackStr);

                    //dataSnapshot.child("B001").child("fid").getValue().toString();

                    //Toast.makeText(TeacherViewAllResults.this, examidStr+"", Toast.LENGTH_SHORT).show();
                }
                for(int i =0 ; i<FeedbackStrList.size(); i++){
                    DatabaseReference readRef2 = FirebaseDatabase.getInstance().getReference().child("Feedback").child(FeedbackStrList.get(i));

                    readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          //  dataSnapshot.getChildrenCount();
                            String strfeedback, strfid, strsName, strsid , strsubject;
                            strfeedback = dataSnapshot.child("feedback").getValue().toString();
                            strfid = dataSnapshot.child("fid").getValue().toString();
                            strsName = dataSnapshot.child("sName").getValue().toString();
                            strsid = dataSnapshot.child("sid").getValue().toString();
                            strsubject = dataSnapshot.child("subject").getValue().toString();

                            feedbackID.add(strfid);
                            studentID.add(strsid);
                            studentName.add(strsName);
                            subject.add(strsubject);
                            feedback.add(strfeedback);

                            MyFeedbackAdapter adapter = new MyFeedbackAdapter(getApplicationContext(),feedbackID,studentID,studentName,subject,feedback);
                            lv.setAdapter(adapter);
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



    }
}

//MyFeedbackAdapter class
class MyFeedbackAdapter extends ArrayAdapter<String> {
    Context context;

    ArrayList feedbackID;
    ArrayList studentID;
    ArrayList studentName;
    ArrayList subject;
    ArrayList feedback;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View fbrow = layoutInflater.inflate(R.layout.feedback_row,parent,false);

        TextView tvfeedbackID = fbrow.findViewById(R.id.tvfeedbackID);
        TextView tvStudentID = fbrow.findViewById(R.id.tvStudentID);
        TextView tvStudentName = fbrow.findViewById(R.id.tvStudentName);
        TextView tvSubject = fbrow.findViewById(R.id.tvSubject);
        TextView tvFeedback = fbrow.findViewById(R.id.tvFeedback);

        tvfeedbackID.setText(feedbackID.get(position).toString());
        tvStudentID.setText(studentID.get(position).toString());
        tvStudentName.setText(studentName.get(position).toString());
        tvSubject.setText(subject.get(position).toString());
        tvFeedback.setText(feedback.get(position).toString());

        return fbrow;
    }

    MyFeedbackAdapter(Context c,ArrayList feedbackID, ArrayList studentID, ArrayList studentName, ArrayList subject, ArrayList feedback){
        super(c, R.layout.feedback_row, R.id.tvfeedbackID, feedbackID);
        this.context = c;
        this.feedbackID = feedbackID;
        this.studentID = studentID;
        this.studentName = studentName;
        this.subject = subject;
        this.feedback = feedback;


    }
}
