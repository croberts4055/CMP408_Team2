package com.example.abrahamlaragranados.team3proj;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MPDetailsActivity extends AppCompatActivity {

    private VideoView video;
    private TextView title, description;
    private boolean flag = true;
    private ImageButton mediaPlay;

    private  TextView dialog_message;
    private  TextView dialog_title;
    private EditText dialog_text;
    private  EditText link_view;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;


    private String user;
    private  String link;

    private HashMap<String , Object> result = new HashMap<>();

    private static final String TAG = "MyActivity";

    private Button studentLinks;

    private String nameG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpdetails);

        mAuth     =  FirebaseAuth.getInstance();
        database  =  database.getInstance();
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        database.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userObj = dataSnapshot.getValue(User.class);
                user = userObj.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        video          =   (VideoView) findViewById(R.id.MPclassvid);
        title          =   (TextView) findViewById(R.id.MPtitle);
        description    =   (TextView) findViewById(R.id.MPdetails);
        dialog_message =   (TextView)findViewById(R.id.dialog_message);
        dialog_text    =   (EditText)findViewById(R.id.dialog_text);
        studentLinks = (Button) findViewById(R.id.MPdatabase);

        final Intent intent = getIntent();
        String url = intent.getExtras().getString("videoURL");
        final String name = intent.getExtras().getString("projectName");
        nameG = name;
        String desc = intent.getExtras().getString("projectDesc");

        title.setText(name);
        description.setText(desc);


        video.setVideoPath(String.valueOf(Uri.parse(url)));
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) video.start();
                else video.pause();
                flag = !flag;

            }
        });

        studentLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MPDetailsActivity.this, MPStudentLinks.class);
                intent1.putExtra("projectName", name);
                startActivity(intent1);
            }
        });


    }
    public void playMyVideo(View view) {

    }

    public void onCreateDialogLink(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        final View dialogView    = (inflater.inflate(R.layout.activity_dialog,null));
        link_view = (EditText)dialogView.findViewById(R.id.dialog_text);
        link_view.setHint("                          Enter the Link ");
        builder.setView(dialogView)

                .setPositiveButton((CharSequence) dialog_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                toMap();

            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_SHORT).show();


            }
        });
        builder.create().show();

    }
    public void toMap(){
        link = link_view.getText().toString();
        result.put("user", user);
        result.put("link", link_view.getText().toString());
        writeNewUserLink();

    }
    private void writeNewUserLink(){

        String key  = mDatabase.child("UserLinks").push().getKey();
        UserLink userLink = new UserLink();

        mDatabase.child("UserLinks").child(nameG).child(key).setValue(new UserLink(user, link))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"onSuccessful",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();

                    }
                });

    }

}