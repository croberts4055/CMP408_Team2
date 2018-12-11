package com.example.abrahamlaragranados.team3proj;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FetchDataBaseTask extends AsyncTask<HashMap<RecyclerView,HashMap<String,String>>, Void, Void> {

    private Context myContext;
    private FirebaseDatabase mDataBase;
    private User current_user;
    private MainActivity main;
    public FetchDataBaseTask(MainActivity main,Context context, User current_user)
    {
        this.myContext = context;
        this.main = main;
        this.mDataBase =  FirebaseDatabase.getInstance();
        this.current_user = current_user;

    }

    public void fetchData(final HashMap<RecyclerView,HashMap<String,String>> data){

        final RecyclerView toDeliver  = (RecyclerView)data.keySet().toArray()[0];
        final HashMap<String,String> query =  data.get(toDeliver);
        Set logs  = query.entrySet();
        Iterator it =  logs.iterator();
        while(it.hasNext()){
            Map.Entry p = (Map.Entry)it.next();
            System.out.println(" Key = "+p.getKey()+" Value = "+p.getValue());
        }


        final String Lawyer =  query.get("Lawyer");
        final String CaseType =  query.get("CaseType");
        final String CaseStatus =  query.get("CaseStatus");
        final double CaseBudget =  Double.parseDouble(query.get("Budget"));
        final String caseLocation  = query.get("Location");
        final String [] date =  query.get("Date").split("/");

        mDataBase.getReference().child("Cases").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Cases> values = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Cases currentCases =  postSnapshot.getValue(Cases.class);
                    if(currentCases.getCase_status().equals(CaseStatus)
                            && currentCases.getCase_type().equals(CaseType)
                            && currentCases.getCase_Lawyer().equals(Lawyer)
                            && currentCases.getCase_budget() <=  CaseBudget
                            && currentCases.getCase_location().equals(caseLocation)){

                        //if a clerk he only case about the case with all his lawyers
                        if(current_user.getUserRole().equals("Clerk")
                                && currentCases.getCase_clerk().equals(current_user.getEmail())){
                            values.add(currentCases);
                            //if is a lawyer he only care about case where he is participation
                        }else if(current_user.getUserRole().equals("Lawyer")
                                && currentCases.getCase_Lawyer().equals(current_user.getEmail())){
                            values.add(currentCases);
                        }else{
                            //ceo secretaty and owner can see all
                            values.add(currentCases);
                        }
                    }else  if(currentCases.getCase_status().equals(CaseStatus)
                            && currentCases.getCase_type().equals(CaseType)
                            && TextUtils.isEmpty(Lawyer)
                            && currentCases.getCase_budget() <=  CaseBudget
                            && currentCases.getCase_location().equals(caseLocation)){

                        //if a clerk he only case about the case with all his lawyers
                        if(current_user.getUserRole().equals("Clerk")
                                && currentCases.getCase_clerk().equals(current_user.getEmail())){
                            values.add(currentCases);
                            //if is a lawyer he only care about case where he is participation
                        }else if(current_user.getUserRole().equals("Lawyer")
                                && currentCases.getCase_Lawyer().equals(current_user.getEmail())){
                            values.add(currentCases);
                        }else{
                            //ceo secretaty and owner can see all
                            values.add(currentCases);
                        }

                    }else if(currentCases.getCase_status().equals(CaseStatus)
                            && currentCases.getCase_type().equals(CaseType)
                            && TextUtils.isEmpty(Lawyer)
                            && currentCases.getCase_budget() <=  CaseBudget
                            && TextUtils.isEmpty(caseLocation)){
                        //if a clerk he only case about the case with all his lawyers
                          if(current_user.getUserRole().equals("Clerk")
                                  && currentCases.getCase_clerk().equals(current_user.getEmail())){
                              values.add(currentCases);
                              //if is a lawyer he only care about case where he is participation
                          }else if(current_user.getUserRole().equals("Lawyer")
                                  && currentCases.getCase_Lawyer().equals(current_user.getEmail())){
                               values.add(currentCases);
                          }else{
                              //ceo secretaty and owner can see all
                              values.add(currentCases);
                          }
                    }

                }

                if(values.size()>0)
                {
                    CaseAdapter adapter = new CaseAdapter(current_user,values,values.size(),main);
                    toDeliver.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected Void doInBackground(HashMap... strings) {
        final HashMap<RecyclerView,HashMap<String,String>> data  =  strings[0];
        if(data==null)
            return null;
        try {

            fetchData(data);

        }catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}