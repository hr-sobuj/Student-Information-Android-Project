package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class forgot_password extends AppCompatActivity {

    private EditText editid,editmail,editphn;
    private Button btnsubmit;

    ProgressDialog progressDialog;
    String id,phn,mail,stdid,stdphn,stdmail;

    DatabaseReference reference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        getWindow().setNavigationBarColor(Color.WHITE);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    /*    progressDialog.show();
        progressDialog.setProgress(10);
        progressDialog.setMax(10);
        progressDialog.setMessage("Information Loading..");
        progressDialog.setIndeterminate(true);*/


        reference = FirebaseDatabase.getInstance().getReference("StudentInfo");

        editmail=(EditText) findViewById(R.id.mail);
/*        editphn=(EditText) findViewById(R.id.phn);
        editid=(EditText) findViewById(R.id.id);*/
        btnsubmit = (Button) findViewById(R.id.forgot);

        if (isOnline()) {


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                id=editid.getText().toString();
                phn=editphn.getText().toString();*/
                mail=editmail.getText().toString();


                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgot_password.this, "Please check your email address.", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(forgot_password.this,login.class);
                            startActivity(intent);
                        }
                    }
                });



/*

                String mails=mail.replaceAll("[^a-zA-Z0-9]","");


                reference.child(mails).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.exists()){
                            Toast.makeText(forgot_password.this,"Info Not found",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(forgot_password.this,MainActivity.class);
                            startActivity(intent);

                        }

                        */
                /*progressDialog.dismiss();*//*



                        stdid=dataSnapshot.child("id").getValue(String.class);
                        stdphn=dataSnapshot.child("mobile").getValue(String.class);
                        stdmail=dataSnapshot.child("email").getValue(String.class);

*/
/*
                        Toast.makeText(forgot_password.this,"Mail sent"+stdid+stdphn,Toast.LENGTH_LONG).show();*//*



                        if(id.equals(stdid)||phn.equals(stdphn)){
                            auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(forgot_password.this,"Password reset link on your email.Please check your mail and reset your password.",Toast.LENGTH_LONG).show();

                                        Intent intent=new Intent(forgot_password.this,login.class);
                                        startActivity(intent);

                                    }
                                }
                            });
                        }



                        */
                /*dataSnapshot.getRef().child("id").setValue(sid.getText().toString());*//*



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



*/


            }
        });
    }
        else {
            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.back_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.backId){


            Intent intent= new Intent(this, login.class);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,login.class);
        startActivity(intent);
        finish();

    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
