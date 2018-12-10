package com.example.abrahamlaragranados.team3proj;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private TextView message;
    private EditText name, email, password, confPassword;

    private Spinner Myspinner;



    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if (mAuth.getCurrentUser() != null) {
            takeUserToActivity(MainActivity.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Myspinner       =  (Spinner) findViewById(R.id.Myspinner);

        message         = (TextView) findViewById(R.id.message);
        name            = (EditText) findViewById(R.id.name);
        email           = (EditText) findViewById(R.id.email);
        password        = (EditText) findViewById(R.id.password);
        confPassword    = (EditText) findViewById(R.id.confPassword);
    }







    public void signup(View view) {
        if (inputsAreEmpty()) {
            message.setText("All fields must be filled in");
            return;
        }

        if (passwordMatchFails()) {
            message.setText("Password confirmation failed");
            return;
        }

        if (nameInputIsInvalid()) {
            message.setText("Invalid name input");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            database.getReference().child("Users").child(authResult.getUser().getUid())
                    .setValue(new User(email.getText().toString(), name.getText().toString()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            message.setText("Successfully registered.");
                            mAuth.signOut();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            message.setText(e.getMessage());
                        }
                    });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                message.setText(e.getMessage());
            }
        });
    }

    private boolean inputsAreEmpty() {
        return name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confPassword.getText().toString().isEmpty();
    }

    private boolean nameInputIsInvalid() {
        Pattern p = Pattern.compile("^[^\\d\\s]+$");
        Matcher m = p.matcher(name.getText().toString());

        return name.getText().toString().length() < 3 || m.matches();
    }

    private boolean passwordMatchFails() {
        return !(password.getText().toString().equals(confPassword.getText().toString()));
    }

    private void takeUserToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected void takeToLogin(View view) {
        takeUserToActivity(LoginActivity.class);
    }


}


