package com.dilsecoders.projectfirebase;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NewActivity extends AppCompatActivity {
    TextView textView = findViewById(R.id.textView);
    Button logout = findViewById(R.id.logout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent4 = new Intent(NewActivity.this, signUp.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent4);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent intent4 = new Intent(NewActivity.this, signUp.class);
        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent4);
        finish();
    }
}