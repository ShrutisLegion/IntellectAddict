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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {
    EditText name, email, password;
    Button SignUp, button2;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        SignUp = findViewById(R.id.SignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(signUp.this, NewActivity.class);
                startActivity(inte);
            }
        });



        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getname = name.getText().toString();
                final String getemail = email.getText().toString();
                final String getpassword = password.getText().toString();
                if(getname.isEmpty()){
                    Toast.makeText(signUp.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if(getemail.isEmpty()){
                    Toast.makeText(signUp.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if(getpassword.isEmpty()){
                    Toast.makeText(signUp.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!(getemail.isEmpty() && getpassword.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(getemail, getpassword)
                            .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(signUp.this, "Try Again!", Toast.LENGTH_SHORT).show();
                            }else {
                                UserDetails userDetails = new UserDetails(getname);
                                String uid = task.getResult().getUser().getUid();
                                firebaseDatabase.getReference(uid).setValue(userDetails)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(signUp.this, "Woooohooo! You are a member now!", Toast.LENGTH_LONG).show();
                                                Intent intent1 = new Intent(signUp.this, NewActivity.class);
//                                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent1.putExtra("name", getname);
                                                startActivity(intent1);
                                            }
                                        });
                            }
                        }
                    });
                } else {
                    Toast.makeText(signUp.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signUp.this, SignIn.class);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}