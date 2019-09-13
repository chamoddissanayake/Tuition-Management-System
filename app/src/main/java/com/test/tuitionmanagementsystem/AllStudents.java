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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllStudents extends AppCompatActivity {

    ListView stdListview;

    ArrayList<String> studentID;
    ArrayList<String> studentName;
    ArrayList<String> address;
    ArrayList<String> telephone;
    ArrayList<String> photo_link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        stdListview = (ListView) findViewById(R.id.stdListview);

        studentID = new ArrayList<String>();
        studentName = new ArrayList<String>();
        address = new ArrayList<String>();
        telephone = new ArrayList<String>();
        photo_link = new ArrayList<String>();

        final ArrayList<String> StudentStrList = new ArrayList<>();
        DatabaseReference readRef1 = FirebaseDatabase.getInstance().getReference().child("StudentDetails");
        readRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String StudentStr = dsp.getKey();
                    StudentStrList.add(StudentStr);
                }
                for(int i =0 ; i<StudentStrList.size(); i++){
                    DatabaseReference readRef2 = FirebaseDatabase.getInstance().getReference().child("StudentDetails").child(StudentStrList.get(i));

                    readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String strSid, strName, strAddress, strPhotoLink , strTel;

                            strSid = dataSnapshot.child("admissionNo").getValue().toString();
                            strName = dataSnapshot.child("studentName").getValue().toString();
                            strAddress = dataSnapshot.child("address").getValue().toString();
                            strPhotoLink = dataSnapshot.child("photoLink").getValue().toString();
                            strTel = dataSnapshot.child("tel").getValue().toString();

                            studentID.add(strSid);
                            studentName.add(strName);
                            address.add(strAddress);
                            telephone.add(strTel);
                            photo_link.add(strPhotoLink);

                            MyStudentAdapter adapter = new MyStudentAdapter(getApplicationContext(),studentID,studentName,address,telephone,photo_link);
                            stdListview.setAdapter(adapter);
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

class MyStudentAdapter extends ArrayAdapter<String> {
    Context context;

    ArrayList studentID;
    ArrayList studentName;
    ArrayList address;
    ArrayList telephone;
    ArrayList photo_link;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View stdrow = layoutInflater.inflate(R.layout.student_row,parent,false);

        TextView tvStudentID = stdrow.findViewById(R.id.tvStudentID);
        TextView tvStudentName = stdrow.findViewById(R.id.tvStudentName);
        TextView tvAddress = stdrow.findViewById(R.id.tvAddress);
        TextView tvTelephone = stdrow.findViewById(R.id.tvTelephone);
        ImageView stdPhoto = stdrow.findViewById(R.id.stdPhoto);

        tvStudentID.setText(studentID.get(position).toString());
        tvStudentName.setText(studentName.get(position).toString());
        tvAddress.setText(address.get(position).toString());
        tvTelephone.setText(telephone.get(position).toString());

        //Photo add to imageview here
        Glide.with(context)
                .load(photo_link.get(position).toString())
                .into(stdPhoto);
        return stdrow;
    }
    MyStudentAdapter(Context c,ArrayList studentID, ArrayList studentName, ArrayList address, ArrayList telephone, ArrayList photo_link){
        super(c, R.layout.student_row, R.id.tvStudentID, studentID);
        this.context = c;

        this.studentID = studentID;
        this.studentName = studentName;
        this.address = address;
        this.telephone = telephone;
        this.photo_link = photo_link;

    }

}