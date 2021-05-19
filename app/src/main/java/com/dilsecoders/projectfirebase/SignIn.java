package com.dilsecoders.projectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dilsecoders.projectfirebase.model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText email2, password2;
    Button sign_in;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    TextView tvsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email2 = findViewById(R.id.email2);
        password2 = findViewById(R.id.password2);
        sign_in = findViewById(R.id.sign_in);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    moveToHomeActivity(mFirebaseUser);
                } else {
                    Toast.makeText(SignIn.this, "Please Login!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email2.getText().toString();
                String getpassword = password2.getText().toString();

                if(getemail.isEmpty()){
                    Toast.makeText(SignIn.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
                } else if(getpassword.isEmpty()){
                    Toast.makeText(SignIn.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
                } else if(getemail.isEmpty() && getpassword.isEmpty()){
                    Toast.makeText(SignIn.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(getemail.isEmpty() && getpassword.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(getemail, getpassword)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(SignIn.this, "Try Again", Toast.LENGTH_SHORT).show();
                                    } else {
                                        moveToHomeActivity(task.getResult().getUser());
                                    }
                                }
                            });
                } else{
                    Toast.makeText(SignIn.this, "Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(SignIn.this, signUp.class);
                startActivity(intSignUp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void moveToHomeActivity(FirebaseUser mFirebaseUser) {
        firebaseDatabase.getReference().child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetails userDetails = snapshot.getValue(UserDetails.class);
                        String givename = userDetails.getName();
                        Intent i = new Intent(getApplicationContext(), NewActivity.class);
                        Toast.makeText(SignIn.this, "Logged In Successfully!", Toast.LENGTH_SHORT).show();
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra("name", givename);
                        startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        UserDetails userDetails = snapshot.getValue(UserDetails.class);
////                        String givename = userDetails.getName();
////                        Intent intent3 = new Intent(getApplicationContext(), NewActivity.class);
////                        Toast.makeText(SignIn.this, "Logged In Successfully!", Toast.LENGTH_SHORT).show();
////                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                        intent3.putExtra("name", givename);
////                        startActivity(intent3);
//
//                        @Override
//                        public void onCancelled (@NonNull DatabaseError error){
//
//                        }
//                    };
//                });
}
}