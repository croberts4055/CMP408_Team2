package com.example.abrahamlaragranados.team3proj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email_text, password_tetx ;
    private Button login_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null)
            Log.d("ERROR", "THERE IS NO CURRENT USER!!!!");
        Log.d("ERROR", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        email_text        = (EditText)findViewById(R.id.email_text);
        password_tetx     = (EditText) findViewById(R.id.password_text);
        login_button      = (Button) findViewById(R.id.login_button);


    }

    public void signUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class );
        startActivity(intent);


    }

    public void loginButton(View view) {
        if(fillOutLoginAreEmpty()){
            Toast.makeText(getApplicationContext(),"Username/email or password is incorrect",Toast.LENGTH_LONG).show();
            return;

        }



        mAuth.signInWithEmailAndPassword(email_text.getText().toString(), password_tetx.getText().toString())
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_SHORT).show();

                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Username/email or password is incorrect",Toast.LENGTH_LONG).show();

                }
                } else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Invalid Login",Toast.LENGTH_LONG).show();

            }
        });


    }
     public boolean fillOutLoginAreEmpty(){
        return email_text.getText().toString().isEmpty() | login_button.getText().toString().isEmpty();
     }


}
