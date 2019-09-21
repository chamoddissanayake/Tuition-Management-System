package com.test.tuitionmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends AppCompatActivity {
    TextView helloMsg;
    ImageButton ResultsMgtBtn,AttendanceMgtBtn,FeedbackNotificationMgtBtn,StudyMaterialsManagementBtn;

    ViewPager viewPager;
    VideoAdapter adapter;
    List<ModelVideo> videoList;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        //Swipe video - start
        videoList = new ArrayList<>();

        DatabaseReference readRefYtd = FirebaseDatabase.getInstance().getReference().child("YoutubeLinks");

        readRefYtd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String img, title, link;
                    img = dsp.child("image").getValue().toString();
                    title = dsp.child("title").getValue().toString();
                    link = dsp.child("link").getValue().toString();



                videoList.add(new ModelVideo(img, title, link));


                }
                adapter = new VideoAdapter(videoList, getApplicationContext());
                viewPager = findViewById(R.id.viewPager);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(50, 0, 50, 0);
                viewPager.setPadding(50, 0, 50, 0);

                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                //Swipe video - end

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/VS07PI3rEE4/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDK-RGXEwXcLieu2W1kpqI7JB-qDA",
//                "Tissa Jananayake - Episode 20 | කොහොම හරි කැම්පස් යන්නයි ඕනේ",
//                "https://www.youtube.com/watch?v=VS07PI3rEE4"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/3H4t_G5SJDw/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDBxzsw1VrgDpDLNrkV4VZ3S7OE9A",
//                "Tissa Jananayake - Episode 19 | දත ගැලවීම",
//                "https://www.youtube.com/watch?v=3H4t_G5SJDw"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/KiLjS9n7YTQ/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLA66Cq70iotzHWy2Ys3_i4fnZj2DQ",
//                "Tissa Jananayake - Episode 18 | ටියුෂන් කාරයාගේ ඇතුලාන්තය",
//                "https://www.youtube.com/watch?v=KiLjS9n7YTQ"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/lJhf77NHJbM/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLB-r4a_P34GVHXC4DbS3ldv5ApTag",
//                "Tissa Jananayake - Episode 17 |  සම්පත් බෙදී යාමේ විෂමතාවය",
//                "https://www.youtube.com/watch?v=lJhf77NHJbM"));
//
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/BQDbq2zfSKI/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLD0DtlL7jccsvySmZNMCo-5xEa13Q",
//                "Tissa Jananayake - Episode 15 | සතුට සෙවීම",
//                "https://www.youtube.com/watch?v=BQDbq2zfSKI"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/CN3yn70uY6o/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLAVszvJqxykO6WYKObsKuwJL4VwkA",
//                "Tissa Jananayake - Episode 14 | ජනාධිපති නාඩගම",
//                "https://www.youtube.com/watch?v=CN3yn70uY6o"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/MQcseCPpDBM/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLBztaLaiZxnHfo-O0gf1t9ai5wOQw",
//                "Tissa Jananayake - Episode 13 | විවාහයක දී රුධිර ඝන ගලපන්න ඕනෙද?",
//                "https://www.youtube.com/watch?v=MQcseCPpDBM"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/sJvXKXt0SfE/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLD6uIIlTZLWIsOtmx5B1GSEsosizA",
//                "Tissa Jananayake - Episode 12 | මවුකිරි දීමේ වැදගත්කම",
//                "https://www.youtube.com/watch?v=sJvXKXt0SfE"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/14KTObc8b8A/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLC6hfkUZU5jx-9yfUiFgt77hYLBhA",
//                "Tissa Jananayake - Episode 11 | ප්රාතිහාර්ය පාන්න ඕනේ කොතනද?",
//                "https://www.youtube.com/watch?v=14KTObc8b8A"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/pfWJ3DMV5XI/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLCA3qaijaO1zipf1VwflGzYXH6P1A",
//                "Tissa Jananayake - Episode 10 | කැප්පෙටිපොල නිළමේ තුමාගේ කථාව",
//                "https://www.youtube.com/watch?v=pfWJ3DMV5XI"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/xUiTdg4xHXo/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLBAuIJTu8ySpO-akrU95v0AAFCLSQ",
//                "Tissa Jananayake - Episode 09 | මිනිස් වකුගඩු වෙලදාම ",
//                "https://www.youtube.com/watch?v=xUiTdg4xHXo"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/QDPgYkkznO4/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDcRzz9Xg8nFi9nVjsUuK4PsdeexQ",
//                "Tissa Jananayake - Episode 08 | අම්මලා අපිට මොනවාද දුන්නේ ?",
//                "https://www.youtube.com/watch?v=QDPgYkkznO4"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/YBa9dbg26JM/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLBaSJlfBOzfoNnQZcMEaRvsTekU7g",
//                "Tissa Jananayake - Episode 07 | පැලෝපීය නාලයේ කතාව",
//                "https://www.youtube.com/watch?v=YBa9dbg26JM"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/SmYfToycS7k/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDWsd-rtxnEEw6UDXIDgcL9FJ0ePw",
//                "Tissa Jananayake - Episode 06 | රයිස් කෑවම මොකද වෙන්නේ ?",
//                "https://www.youtube.com/watch?v=SmYfToycS7k"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/Q93VZDn6IhU/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLD3PsxilxWQougwskX_TbfuSh9X_Q",
//                "Tissa Jananayake - Episode 05 | කොහොමද මැරුණු බව දැනගන්නේ?",
//                "https://www.youtube.com/watch?v=Q93VZDn6IhU"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/iKGDVgZiQYU/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLCdNmvhkqa6Iaczm9n-ukYX3yQ5Ew",
//                "Tissa Jananayake Episod 04 | මොනදේ කරන්නත් අපිට රටක් තියෙන්න ඕනේ",
//                "https://www.youtube.com/watch?v=iKGDVgZiQYU"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/xFOOI_9u3J0/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLC0ATKqgudyBJX4kCtQZYO1T4O-cw",
//                "Tissa Jananayake - Episode 03 | මොකද උනේ ?",
//                "https://www.youtube.com/watch?v=xFOOI_9u3J0"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/eYC7dL9QEVs/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLAaJR8J-N_SoffC6v3yz_1HajFqrA",
//                "Tissa Jananayake - Episode 02 | කොල්ලන්ට බනින කෙල්ලන්ට මොකද වෙන්නේ?",
//                "https://www.youtube.com/watch?v=eYC7dL9QEVs"));
//
//        videoList.add(new ModelVideo(
//                "https://i.ytimg.com/vi/7sp6ZhlP4ho/hqdefault.jpg?sqp=-oaymwEjCNACELwBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDkZuRMzQSIpVpdsIBaEeM2ggCJsw",
//                "Tissa Jananayake - Episode 01 | විශ්වයේ සොදුරු ජිව විද්යාව ගුරුවරයා",
//                "https://www.youtube.com/watch?v=7sp6ZhlP4ho"));







        ResultsMgtBtn = (ImageButton) findViewById(R.id.studentResultsMgtBtn);
        AttendanceMgtBtn =(ImageButton) findViewById(R.id.studentAttendanceMgtBtn);
        FeedbackNotificationMgtBtn =(ImageButton) findViewById(R.id.studentFeedbackNotificationMgtBtn);
        StudyMaterialsManagementBtn = (ImageButton) findViewById(R.id.studentStudyMaterialsManagementBtn);

        Intent intent = getIntent();
        final String sID = intent.getStringExtra("StudentID");
        final String sName = intent.getStringExtra("sName");
        final String sAddress = intent.getStringExtra("address");
        final String sTel = intent.getStringExtra("tel");
        final String sPhoto_link = intent.getStringExtra("photo_link");


        helloMsg = (TextView)findViewById(R.id.HiStudentMessage);
        helloMsg.setText("Hi "+sName+",");

        ResultsMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Result Management Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),StudentResultManagementDashboard.class);
                i.putExtra("sID",sID);
                i.putExtra("sName",sName);
                startActivity(i);
            }
        });

        AttendanceMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = "student";
                Intent i = new Intent(getApplicationContext(),EditRegistration_Details.class);
                i.putExtra("ID",sID);
                i.putExtra("Name",sName);
                i.putExtra("Address",sAddress);
                i.putExtra("Tel",sTel);
                i.putExtra("Photo_link",sPhoto_link);
                i.putExtra("Type",t);
                startActivity(i);
            }
        });

        FeedbackNotificationMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainFeedback.class);
                i.putExtra("StudentID",sID);
                i.putExtra("sName",sName);
                startActivity(i);

            }
        });

        StudyMaterialsManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = "student";
                Intent i = new Intent(getApplicationContext(),StudyMaterial.class);
                i.putExtra("ID",sID);
                i.putExtra("Name",sName);
                i.putExtra("Type",t);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.locateUs:
                Intent intLocateus = new Intent(getApplicationContext(),Locate_Us.class);
                startActivity(intLocateus);
                return true;
            case R.id.contactUs:
                Intent intContactus = new Intent(getApplicationContext(),ContactUs.class);
                startActivity(intContactus);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToLocateUs(View view) {
        Intent i = new Intent(getApplicationContext(),Locate_Us.class);
        startActivity(i);
    }

    public void goToContactsUs(View view) {
        Intent i = new Intent(getApplicationContext(),ContactUs.class);
        startActivity(i);
    }
}
