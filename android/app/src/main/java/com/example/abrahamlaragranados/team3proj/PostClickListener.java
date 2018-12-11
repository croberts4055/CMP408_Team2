package com.example.abrahamlaragranados.team3proj;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/*Author luis manon*/
public class PostClickListener implements View.OnClickListener, CaseAdapter.GridItemClickListener, NotesAdapter.GridItemClickListener {
    public Context context;
    public View currentView;
    public  final String TAG =  PostClickListener.class.getSimpleName();
    public FirebaseAuth currentUser;
    public FirebaseDatabase myDataBase;
    public User current_cuser_and_roles;
    public PostClickListener(Context context, View c, User current_logged_user)
    {
        this.context =  context;
        this.currentView =  c;
        this.current_cuser_and_roles = current_logged_user;
        currentUser = FirebaseAuth.getInstance();
        myDataBase =  FirebaseDatabase.getInstance();

    }

    private void openLocationInMap(String address) {
        Uri geoLocation = Uri.parse("geo:0,0?q=" + address);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + geoLocation.toString()
                    + ", no receiving apps installed!");
        }
    }


    public void inflateAnewViewForPost(final String key, final String budget,final RecyclerView RecyclerViewObject)
    {
       /* boolean shouldAttachToParentImmediately = false;
        Activity activity = (Activity)context;
        View view  = activity.getLayoutInflater().inflate(R.layout.post_bid,null,shouldAttachToParentImmediately);
        final EditText jobBudget =  view.findViewById(R.id.postBudget);
        final EditText postMessage = view.findViewById(R.id.postMessage);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerViewObject.setLayoutManager(layoutManager);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources()
                .getString(R.string.new_bid));
        builder.setView(view);

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if( postMessage.getText().length() > 0
                        && jobBudget.getText().length() > 0
                        ) {
                    if(currentUser!=null) {
                        FireBaseUtilitie temp = new FireBaseUtilitie(context);
                        Date date =  new Date();
                        Bids newBid = new Bids(key,budget,currentUser.getCurrentUser().getEmail(),jobBudget.getText().toString(), date.toString(),postMessage.getText().toString());
                        temp.getDatabaseReferenceObj().child("Bids").child(key+date.toString()).setValue(newBid);
                        PopulateBidRecyclerView(key,RecyclerViewObject);
                    }
                }else{
                    Toast.makeText(context.getApplicationContext(),context.getResources()
                            .getString(R.string.input_empty),Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        doKeepDialog(dialog);*/

    }
    public String getTokenizeId()
    {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder string = new StringBuilder();
        Random rnd = new Random();
        while (string.length() < 16) {
            int index = (int) (rnd.nextFloat() * characters.length());
            string.append(characters.charAt(index));
        }

        return string.toString();
    }

    public void  PopulateBidRecyclerView(final String key,final RecyclerView RecyclerViewObject){
        myDataBase.getReference().child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Notes> values = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Notes note =  postSnapshot.getValue(Notes.class);
                    if(note.getCase_id().equals(key)){
                        values.add(note);
                    }
                }

                if(values.size()>0)
                {
                    NotesAdapter adapter = new NotesAdapter(values,PostClickListener.this);
                    RecyclerViewObject.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inflateAnewViewForPost(final String key, final RecyclerView RecyclerViewObject)
    {
        boolean shouldAttachToParentImmediately = false;
        Activity activity = (Activity)context;
        View view  = activity.getLayoutInflater().inflate(R.layout.post_note,null,shouldAttachToParentImmediately);
        final EditText note =  view.findViewById(R.id.noteToTake);
       /* LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerViewObject.setLayoutManager(layoutManager);*/


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Case Note");
        builder.setView(view);

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if( note.getText().length() > 0
                        ) {
                    if(currentUser!=null) {

                        Date date =  new Date();

                        Notes notes_to_post = new Notes(getTokenizeId(),key,current_cuser_and_roles.getName()
                                ,current_cuser_and_roles.getUserRole(),note.getText().toString()
                                ,date.toString());
                        myDataBase.getReference().child("Bids").child(key+date.toString()).setValue(notes_to_post);
                        PopulateBidRecyclerView(key,RecyclerViewObject);
                    }
                }else{
                    Toast.makeText(context.getApplicationContext(),"Please input some notes!",Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        doKeepDialog(dialog);

    }
    private static void doKeepDialog(AlertDialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String key = v.getTag().toString();
        switch(id){
            case R.id.DirectLocation:
                openLocationInMap(key);
                break;
            case R.id.updateCase:

                break;
            case R.id.Delete:

                break;
            case R.id.Note:
                RecyclerView recyclerView = currentView.findViewWithTag("Recycler"+key);
                inflateAnewViewForPost(key,recyclerView);
                break;

           /* case R.id.postBid:
                RecyclerView recyclerView = currentView.findViewWithTag("recycler"+key);
                TextView budget =  currentView.findViewWithTag("budget"+key);
                //lets inflate the new bid
                inflateAnewViewForPost(key,budget.getText().toString(),recyclerView);

                break;
            case R.id.postLocation:
                openLocationInMap(v.getTag().toString());
                break;
            case R.id.Results:
                //here the user wants to see the result of the job and the images :-)
                break;
            case R.id.update:

                break;*/
        }
    }

    @Override
    public void onGridItemClick(int clickedItemGrid, View v) {

    }

    //note adapter click listener
    @Override
    public void onAppointmentClickListener(int clickedItemGrid, View v) {
    }
}
