/*package com.test.tuitionmanagementsystem;

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

import java.util.ArrayList;

public class ViewFeedback extends AppCompatActivity {

    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        l1 = findViewById(R.id.listview);

        final ArrayList<String> listfid = new ArrayList<String>();
        final ArrayList<String> listsid = new ArrayList<String>();
        final ArrayList<String> listsname = new ArrayList<String>();
        final ArrayList<String> listsubject = new ArrayList<String>();
        final ArrayList<String> listfeedback = new ArrayList<String>();

        listfid.add("F1");listfid.add("F1");listfid.add("F1");listfid.add("F1");listfid.add("F1");
        listsid.add("S1"); listsid.add("S1"); listsid.add("S1"); listsid.add("S1"); listsid.add("S1");
        listsname.add("N1");listsname.add("N1");listsname.add("N1");listsname.add("N1");listsname.add("N1");
        listsubject.add("Su1");listsubject.add("Su1");listsubject.add("Su1");listsubject.add("Su1");listsubject.add("Su1");
        listfeedback.add("F1");listfeedback.add("F1");listfeedback.add("F1");listfeedback.add("F1");listfeedback.add("F1");

        ArrayList<FeedbackTable> feedbackList = new ArrayList<>();

        MyAdapter2 adapter2 = new MyAdapter2(getApplicationContext(),listfid,listsid,listsname,listsubject,listfeedback);
        l1.setAdapter(adapter2);

    }


}

class MyAdapter2 extends ArrayAdapter<String>{
    Context context;
    ArrayList listfid;
    ArrayList listsid;
    ArrayList listsname;
    ArrayList listsubject;
    ArrayList listfeedback;

//    public MyAdapter2(Context c,ArrayList listfid,ArrayList listsid,ArrayList listsname,ArrayList listsubject,ArrayList listfeedback){
//        super(c,R.layout.feedbackraw,R.id.textView,listfid);
//
//        this.listfid = listfid;
//        this.listsid = listsid;
//        this.listsname = listsname;
//        this.listsubject = listsubject;
//        this.listfeedback = listfeedback;
//    }

    public MyAdapter2(@NonNull Context context, ArrayList listfid, ArrayList listsid, ArrayList listsname, ArrayList listsubject, ArrayList listfeedback) {
        super(context, R.layout.feedbackraw, R.id.f1);
        this.listfid = listfid;
        this.listsid = listsid;
        this.listsname = listsname;
        this.listsubject = listsubject;
        this.listfeedback = listfeedback;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View frow = lf.inflate(R.layout.feedbackraw,parent,false);

        TextView f1 = (TextView) frow.findViewById(R.id.f1);
        TextView s1 = (TextView) frow.findViewById(R.id.s1);
        TextView n1 = (TextView) frow.findViewById(R.id.n1);
        TextView su1 = (TextView) frow.findViewById(R.id.su1);
        TextView fe1 = (TextView) frow.findViewById(R.id.fe1);

        f1.setText(listfid.get(position).toString());
        s1.setText(listsid.get(position).toString());
        n1.setText(listsname.get(position).toString());
        su1.setText(listsubject.get(position).toString());
        fe1.setText(listfeedback.get(position).toString());

        return frow;
    }
}*/
