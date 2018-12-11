package com.example.abrahamlaragranados.team3proj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
//import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CaseAdapter.GridItemClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private User authenticatedUser;

    public Spinner CaseType;
    public Spinner CaseStatus;
    public EditText caseBudget;
    public EditText CourtData;
    public TextView dateSelected;
    public Spinner Lawyer;
    public Button Search;
    public Button sort_by_date;
    public RecyclerView recyclerView;

    public FloatingActionButton notification_fab;

    //private boolean isSuperUser;
    public boolean isSuperUser = false;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().getParcelable("CurrentUser")!=null){
                authenticatedUser  =  getIntent().getExtras().getParcelable("CurrentUser");
            }
        }
        if(savedInstanceState!=null){
            authenticatedUser  = savedInstanceState.getParcelable("CurrentUser");
        }

        if(Preference.getSuperUserPreference(this) != null && Preference.getSuperUserPreference(this).equals("Yes")){
            isSuperUser = true;
             if(authenticatedUser==null){
               FetchUser();
             }
        } else{
            isSuperUser =  false;
            if(authenticatedUser==null){
                FetchUser();
            }
        }
        //pupulate view base on authenticated user roles
        PopulateMainActivityContentView();
        //lets populate the data base on the user logged
        if(isSuperUser && !isSuperUser){
            FilterDataForLoggedSuperUser();
        }

    }



    //the fetch of data
    public void  FilterDataForLoggedSuperUser()
    {
        Calendar calendar = Calendar.getInstance();
        HashMap<String, String> dataToFetch = new HashMap<>();
        dataToFetch.put("CaseType", "Litigation");
        dataToFetch.put("CaseStatus", "OnProgress");
        dataToFetch.put("Lawyer","");
        dataToFetch.put("Budget",""+200000);
        int year = calendar.get(Calendar.YEAR);
        int month =  calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dataToFetch.put("Date",""+month+"/"+day+"/"+year);
        dataToFetch.put("Location","");
        HashMap<RecyclerView,HashMap<String, String>> search =  new HashMap<>();
        search.put(recyclerView,dataToFetch);
        //lets fetch asynchronomously
        FetchDataBaseTask fetch =  new FetchDataBaseTask(this,this,authenticatedUser);
        fetch.execute(search);
    }

    public void FetchUser()
    {
        database.getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if(user.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                authenticatedUser =  user;
                                break;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void PopulateMainActivityContentView(){
        if(authenticatedUser!=null){
         Toast.makeText(getApplicationContext(),"The authenticated user is not null ",Toast.LENGTH_LONG).show();
            if(authenticatedUser.getUserRole().equals(getResources().getString(R.string.Secretary))
                    || authenticatedUser.getUserRole().equals(getResources().getString(R.string.Clerk))
                    || authenticatedUser.getUserRole().equals(getResources().getString(R.string.Ceo))){
                isSuperUser = true;
                Preference.saveAppSuperUser(this,"Yes");
                setContentView(R.layout.activity_main);
                CaseType =  findViewById(R.id.CaseType);
                CaseStatus =  findViewById(R.id.CaseStatus);
                caseBudget =  findViewById(R.id.sort_by_value);
                CourtData = findViewById(R.id.CourtLocation);
                dateSelected =  findViewById(R.id.date_picked);
                Lawyer =  findViewById(R.id.LawyerName);
                sort_by_date = findViewById(R.id.sort_by_date);
                sort_by_date.setOnClickListener(this);
                Search  = findViewById(R.id.searchDataBase);
                Search.setOnClickListener(this);
                recyclerView = findViewById(R.id.result_cases);
                notification_fab = findViewById(R.id.new_case);
                notification_fab.setOnClickListener(this);

                if(authenticatedUser.getUserRole().equals("Clerk")
                        && authenticatedUser.getUserRole().equals("Lawyer"))
                {

                    notification_fab.hide();
                }else{

                    notification_fab.show();

                }

                //get all the lawyer
                fetchAllDatabaseLawyer(Lawyer);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);

                ArrayAdapter<CharSequence> adapterCaseType = ArrayAdapter.createFromResource(this,
                        R.array.law_types, R.layout.spinner_item);
                adapterCaseType.setDropDownViewResource(android.R.layout.simple_spinner_item);
                CaseType.setAdapter(adapterCaseType);
                ArrayAdapter<CharSequence> adapterCaseStatus = ArrayAdapter.createFromResource(this,
                        R.array.case_status, R.layout.spinner_item);
                adapterCaseType.setDropDownViewResource(android.R.layout.simple_spinner_item);
                CaseStatus.setAdapter(adapterCaseStatus);


            }else{
                isSuperUser = false;
                Preference.saveAppSuperUser(this,"No");
                //if now is a lawyer who logs in the lawyer will have a different view
            }

        }
    }

    /*private void starActivityFor(final String project) {
        database.getReference().child("Projects").child(project).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> values = new ArrayList<String>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    values.add(postSnapshot.getValue().toString());
                }

                start(values, project);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    public void start(ArrayList<String> values, String project) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                mAuth.signOut();

                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchData()
    {
        if(isSuperUser) {
            String caseType = CaseType.getSelectedItem().toString();
            String caseStatus = CaseStatus.getSelectedItem().toString();
            String lawyer = Lawyer.getSelectedItem().toString();

            double budget;
            if(caseBudget.getText().toString().length() >0)
            {
                budget =  Double.parseDouble(caseBudget.getText().toString());
            }else{
                budget = 100000;
            }
            String location = CourtData.getText().toString();
            String date =  dateSelected.getText().toString();
            HashMap<String, String> dataToFetch = new HashMap<>();
            dataToFetch.put("CaseType", caseType);
            dataToFetch.put("CaseStatus", caseStatus);
            dataToFetch.put("Lawyer",lawyer);
            dataToFetch.put("Budget",""+budget);
            dataToFetch.put("Date",""+date);
            dataToFetch.put("Location",location);
            HashMap<RecyclerView,HashMap<String, String>> search =  new HashMap<>();
            search.put(recyclerView,dataToFetch);
            //lets fetch asynchronomously
            FetchDataBaseTask fetch =  new FetchDataBaseTask(this,this,authenticatedUser);
            fetch.execute(search);
        }else{

        }

    }


    public void openDatePicker()
    {
            boolean shouldAttachToParentImmediately = false;

            View view  = getLayoutInflater().inflate(R.layout.date_picker,null,shouldAttachToParentImmediately);
            final DatePicker date = view.findViewById(R.id.date_picked);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Date Chooser");
            builder.setView(view);

            builder.setPositiveButton("Pick", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                   if(isSuperUser){
                       dateSelected.setText(date.getMonth()+1+"/"+date.getDayOfMonth()+"/"+date.getYear());
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
    }
    @Override
    public void onClick(View view) {
        int id =  view.getId();
        switch(id)
        {
            case  R.id.searchDataBase:
                 //execute the search asynch...
                fetchData();
                break;
            case R.id.new_case:
                 PostNewCase();
                break;
            case R.id.sort_by_date:
                openDatePicker();
                break;
        }
    }

    //fetch all the database lawyer
    public void fetchAllDatabaseLawyer(final Spinner lawyer){
        database.getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> lawyers =  new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if(user.getUserRole().equals("Lawyer")){
                               lawyers.add(user.getEmail());
                            }
                        }
                        ArrayAdapter<String> lawyer_adapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_spinner_item, lawyers);
                        lawyer.setAdapter(lawyer_adapter);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    public void fetchAllClerk(final Spinner clerk){
        database.getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> lawyers =  new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if(user.getUserRole().equals("Clerk")){
                                lawyers.add(user.getEmail());
                            }
                        }
                        ArrayAdapter<String> clerk_adapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_spinner_item, lawyers);
                        clerk.setAdapter(clerk_adapter);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void fetchAllCaseType(Spinner caseType){
        ArrayAdapter<CharSequence> adapterCaseType = ArrayAdapter.createFromResource(this,
                R.array.law_types, R.layout.spinner_item);
        adapterCaseType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        caseType.setAdapter(adapterCaseType);
    }
    public void fetchCaseStatus(Spinner case_status)
    {
        ArrayAdapter<CharSequence> adapterCaseType = ArrayAdapter.createFromResource(this,
                R.array.case_status, R.layout.spinner_item);
        adapterCaseType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        case_status.setAdapter(adapterCaseType);
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

    //the listener to all the cases
    public void PostNewCase()
    {
        boolean shouldAttachToParentImmediately = false;

        View view  = getLayoutInflater().inflate(R.layout.new_case_post_view,null,shouldAttachToParentImmediately);
        final DatePicker date = view.findViewById(R.id.date_picked);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        final TextView date_selected =  view.findViewById(R.id.date_results);
        final Spinner  CaseType =  view.findViewById(R.id.CaseType);
        final Spinner Lawyer = view.findViewById(R.id.Lawyer);
        final Spinner Clerk = view.findViewById(R.id.Clerk);
        final EditText law_infraction = view.findViewById(R.id.Lawsuit);
        final EditText location_info  = view.findViewById(R.id.Location);
        final EditText Judge = view.findViewById(R.id.Judge);
        final EditText court_name = view.findViewById(R.id.Court_name);
        final Spinner case_status =  view.findViewById(R.id.CaseStatus);
        date.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                date_selected.setText(month+1 + "/" + (dayOfMonth) + "/" + year);
            }
        });

        fetchAllCaseType(CaseType);
        fetchAllDatabaseLawyer(Lawyer);
        fetchAllClerk(Clerk);
        fetchCaseStatus(case_status);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Date Chooser");
        builder.setView(view);

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                     if(law_infraction.getText().toString().length() > 0
                             && location_info.getText().toString().length() > 0
                             && Judge.getText().toString().length() > 0
                             && court_name.getText().toString().length()>0) {
                         String case_type =  CaseType.getSelectedItem().toString();
                         String case_lawyer =  Lawyer.getSelectedItem().toString();
                         String case_clerk =  Clerk.getSelectedItem().toString();
                         String law_inf = law_infraction.getText().toString();
                         double law_infraction = Double.parseDouble(law_inf);
                         String location =  location_info.getText().toString();

                         String id =  getTokenizeId();
                         Cases new_case  = new Cases(id, case_lawyer, case_clerk, location, case_type,
                                 law_infraction, Judge.getText().toString(), date_selected.getText().toString(), court_name.getText().toString()
                                 ,case_status.getSelectedItem().toString());

                         database.getReference().child("Cases").child(id).setValue(new_case);

                     }else{
                         Toast.makeText(MainActivity.this, "Please make sure you have all the needing data to post a new case!!", Toast.LENGTH_SHORT).show();
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
    }
    @Override
    public void onGridItemClick(int clickedItemGrid, View v) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("CurrentUser",authenticatedUser);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        authenticatedUser = savedInstanceState.getParcelable("CurrentUser");
    }
}
