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

        studentID.add("S001");studentID.add("S002");studentID.add("S003");studentID.add("S004");studentID.add("S005");
        studentName.add("Saman");studentName.add("Isuru");studentName.add("Gayan");studentName.add("Sahan");studentName.add("Silva");
        address.add("Colombo");address.add("Galle");address.add("Gampaha");address.add("Kandy");address.add("Kurunegala");
        telephone.add("0771234561");telephone.add("0771234562");telephone.add("0771234563");telephone.add("0771234564");telephone.add("0771234565");
        photo_link.add("");
        photo_link.add("");
        photo_link.add("");
        photo_link.add("");
        photo_link.add("");

        MyStudentAdapter adapter = new MyStudentAdapter(this,studentID,studentName,address,telephone,photo_link);
        stdListview.setAdapter(adapter);

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