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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class Todolist extends AppCompatActivity {

    ListView lv;
    ArrayList<String> topic;
    ArrayList<String> description;
    ArrayList<String> link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        lv= (ListView) findViewById(R.id.tutorialListview);

        topic = new ArrayList<String>();
        description = new ArrayList<String>();
        link = new ArrayList<String>();

        //Fetch from DB
        final ArrayList<String> TutorialStrList = new ArrayList<>();
        DatabaseReference readRef1 = FirebaseDatabase.getInstance().getReference().child("TutorialUploads");
        readRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String tutStr = dsp.getKey();
                    TutorialStrList.add(tutStr);

                }
                for(int i =0 ; i<TutorialStrList.size(); i++){
                    DatabaseReference readRef2 = FirebaseDatabase.getInstance().getReference().child("TutorialUploads").child(TutorialStrList.get(i));

                    readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String strtopic, strdescription, strLink;
                            strtopic = dataSnapshot.child("topic").getValue().toString();
                            strdescription = dataSnapshot.child("description").getValue().toString();
                            strLink = dataSnapshot.child("uploadPath").getValue().toString();

                            topic.add(strtopic);
                            description.add(strdescription);
                            link.add(strLink);

                            MyTutorialAdapter adapter = new MyTutorialAdapter(getApplicationContext(),topic,description,link);
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tv_Link = (TextView)findViewById(R.id.tvtutLink);

                if (checkPermissionREAD_EXTERNAL_STORAGE()) {
                    Toast.makeText(getApplicationContext(),"Start Downloading...",Toast.LENGTH_SHORT).show();
                    downloadTutorial(tv_Link.getText().toString());
                }

            }
        });








    }

    private void downloadTutorial(String document_link) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(document_link);

        final File rootPath = new File(Environment.getExternalStorageDirectory(), " Tutorial_Downloads");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, System.currentTimeMillis() + ".pdf");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "File Downloaded Successfully to Tutorial_Downloads folder.", Toast.LENGTH_SHORT).show();


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








//MyTutorialAdapter class
class MyTutorialAdapter extends ArrayAdapter<String> {
    Context context;

    ArrayList topic;
    ArrayList description;
    ArrayList link;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tutrow = layoutInflater.inflate(R.layout.tutorial_row,parent,false);

        TextView tvtutTopic = tutrow.findViewById(R.id.tvtutTopic);
        TextView tvtutDescription = tutrow.findViewById(R.id.tvtutDescription);
        TextView tvtutLink = tutrow.findViewById(R.id.tvtutLink);

        tvtutLink.setVisibility(View.INVISIBLE);

        tvtutTopic.setText(topic.get(position).toString());
        tvtutDescription.setText(description.get(position).toString());
        tvtutLink.setText(link.get(position).toString());

        return tutrow;
    }

    MyTutorialAdapter(Context c,ArrayList topic, ArrayList description, ArrayList link){
        super(c, R.layout.tutorial_row, R.id.tvtutTopic, topic);
        this.context = c;
        this.topic = topic;
        this.description = description;
        this.link = link;



    }




}

