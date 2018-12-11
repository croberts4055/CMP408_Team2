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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase myDataBase;

    private EditText email_text, password_tetx ;
    private Button login_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        myDataBase = FirebaseDatabase.getInstance();

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
                   startMainActivityContent(user.getEmail());

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

     public void startMainActivityContent(String authenticateEmail){
      myDataBase.getReference().child("Users")
              .addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if(user.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                Bundle extra = new Bundle();
                                extra.putParcelable("CurrentUser",user);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtras(extra);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                  }


                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });

     }
     public boolean fillOutLoginAreEmpty(){
        return email_text.getText().toString().isEmpty() | login_button.getText().toString().isEmpty();
     }


}
