package com.student.studentinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_validation extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_validation);


        getWindow().setNavigationBarColor(Color.WHITE);


        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        assert user != null;
        if(user.isEmailVerified()){
                Intent intent=new Intent(login_validation.this,profile.class);
                startActivity(intent);
                finish();
            }
            else{
            Intent intent=new Intent(login_validation.this,email_verification.class);
            startActivity(intent);
            finish();
            }


    }
}
