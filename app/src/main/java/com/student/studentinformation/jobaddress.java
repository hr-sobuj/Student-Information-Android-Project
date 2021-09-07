package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class jobaddress extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{




    private DrawerLayout mNavDrawer;
    private Button showlistFromdatabase,saveinfo;
    private Spinner spinner1,spinner;
    String[] deptlists,falist;

    String stdid,stdname,stdfalculty,stddept,stdblood,stdgender,stdphn,stdmail,stdjob,stdpresent,stdpast;
    DatabaseReference reference;
    AlertDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;
    String mailadd;


    private RadioGroup genderRadioGroup;
    private RadioButton genderradioButton;
    private boolean isFirstSelectInSpinner=true;

    private String genderValue;

    private EditText sid,sname,sfaculty,sdept,sblood,sgender,sphn,smail,sjob,spresentad,spermanentad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_job_address);


        getWindow().setNavigationBarColor(Color.WHITE);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNavDrawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(


                this,mNavDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        progressDialog=new AlertDialog.Builder(this).setView(view)
                .create();
        progressDialog.setCancelable(false);

        progressDialog.show();

        progressDialog.getWindow().setLayout(350,350);


        reference= FirebaseDatabase.getInstance().getReference("StudentInfo");


        ///EditText_finder

        sjob=(EditText) findViewById(R.id.job);
        spresentad=(EditText) findViewById(R.id.presentaddress);
        spermanentad=(EditText) findViewById(R.id.permanentaddress);

        saveinfo=(Button) findViewById(R.id.submitYourInfo);





        mailadd=user.getEmail();
        mailadd=mailadd.replaceAll("[^a-zA-Z0-9]","");


        reference.child(mailadd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(jobaddress.this,"Info Not found",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(jobaddress.this,MainActivity.class);
                    startActivity(intent);

                }

                progressDialog.dismiss();
                stdid=dataSnapshot.child("id").getValue(String.class);
                stdname=dataSnapshot.child("name").getValue(String.class);
                stdfalculty=dataSnapshot.child("faculty").getValue(String.class);
                stddept=dataSnapshot.child("dept").getValue(String.class);
                stdblood=dataSnapshot.child("blood").getValue(String.class);
                stdgender=dataSnapshot.child("gender").getValue(String.class);
                stdphn=dataSnapshot.child("mobile").getValue(String.class);
                stdmail=dataSnapshot.child("email").getValue(String.class);
                stdjob=dataSnapshot.child("job").getValue(String.class);
                stdpresent=dataSnapshot.child("present").getValue(String.class);
                stdpast=dataSnapshot.child("permanent").getValue(String.class);


                sjob.setText(stdjob);
                spresentad.setText(stdpresent);
                spermanentad.setText(stdpast);


                /*dataSnapshot.getRef().child("id").setValue(sid.getText().toString());*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        saveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(mailadd).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {






                        dataSnapshot.getRef().child("job").setValue(sjob.getText().toString());
                        dataSnapshot.getRef().child("present").setValue(spresentad.getText().toString());
                        dataSnapshot.getRef().child("permanent").setValue(spermanentad.getText().toString());

                        Intent intent=new Intent(jobaddress.this,successfull_msg.class);

                        startActivity(intent);
                        /*Toast.makeText(jobaddress.this,"Updated Successfully.",Toast.LENGTH_LONG).show();*/


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



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


            Intent intent= new Intent(this, updateinfopage.class);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,updateinfopage.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.bkhome){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, appmenu.class);
            startActivity(intent1);
            finish();
        }

        if(item.getItemId()==R.id.bk){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, profile.class);
            startActivity(intent1);
            finish();
        }

        if(item.getItemId()==R.id.bk1){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
        }

        if(item.getItemId()==R.id.bk2){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, blood.class);
            startActivity(intent1);
            finish();
        }

        if(item.getItemId()==R.id.bk3){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, find_job.class);
            startActivity(intent1);
            finish();
        }

        if(item.getItemId()==R.id.bk4){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, aboutus.class);
            startActivity(intent1);
            finish();
        }
        if(item.getItemId()==R.id.bk5){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, contact_us.class);
            startActivity(intent1);
            finish();
        }

        if(item.getItemId()==R.id.bk6){

            mNavDrawer.closeDrawer(GravityCompat.START);
            Intent intent1 = new Intent(this, developer.class);
            startActivity(intent1);
            finish();
        }


        return true;
    }
    private void showdialog() {

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);



        final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(view)
                .create();

        alertDialog.show();
        alertDialog.setCancelable(false);



    }
}
