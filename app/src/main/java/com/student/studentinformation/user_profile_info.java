package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class user_profile_info extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout mNavDrawer;
    private static final int REQUEST_CALL = 1;
    private ListView DataDisplay;
    private TextView txt;
    String val,val1;
    String stdid,stdname,stdname2,stdfalculty,stddept,stdblood,stdgender,stdphn,stdmail,stdjob,stdpresent,stdpast,stdimg,stdsession;
    DatabaseReference reference;
    AlertDialog progressDialog;

    FirebaseAuth auth;
    FirebaseUser user;
    private TextView sid,sname,sname2,sfaculty,sdept,sblood,sgender,sphn,smail,sjob,spresentad,spermanentad,ssession;
    private Button btn;

    private List<dbref> firebasess;
    private firebase_adapter firebaseAdapter;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_user_profile_info);

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


        imageView=(ImageView) findViewById(R.id.imageviewid);

        btn=(Button) findViewById(R.id.backid);

        sid=(TextView) findViewById(R.id.uid);
        sname=(TextView) findViewById(R.id.uname);
        sname2=(TextView) findViewById(R.id.uname2);
        sfaculty=(TextView) findViewById(R.id.ufaculty);
        sdept=(TextView) findViewById(R.id.udept);
        sblood=(TextView) findViewById(R.id.ublood);
        sgender=(TextView) findViewById(R.id.ugender);
        sphn=(TextView) findViewById(R.id.uphn);
        smail=(TextView) findViewById(R.id.umail);
        sjob=(TextView) findViewById(R.id.ujob);
        spresentad=(TextView) findViewById(R.id.upresent);
        spermanentad=(TextView) findViewById(R.id.upast);
        ssession=(TextView) findViewById(R.id.usession);





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_profile_info.this,profile.class);
                startActivity(intent);
            }
        });

        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        progressDialog=new AlertDialog.Builder(this).setView(view)
                .create();
        progressDialog.setCancelable(false);

        progressDialog.show();

        progressDialog.getWindow().setLayout(350,350);


        reference= FirebaseDatabase.getInstance().getReference("StudentInfo");




        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        val=user.getEmail();
        val=val.replaceAll("[^a-zA-Z0-9]","");

        reference.child(val).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){

                    Intent intent=new Intent(user_profile_info.this,info.class);
                    startActivity(intent);

                }

                progressDialog.dismiss();
                stdid=dataSnapshot.child("id").getValue(String.class);
                stdname=dataSnapshot.child("name").getValue(String.class);
                stdfalculty=dataSnapshot.child("faculty").getValue(String.class);
                stddept=dataSnapshot.child("department").getValue(String.class);
                stdblood=dataSnapshot.child("blood").getValue(String.class);
                stdgender=dataSnapshot.child("gender").getValue(String.class);
                stdphn=dataSnapshot.child("mobile").getValue(String.class);
                stdmail=dataSnapshot.child("email").getValue(String.class);
                stdjob=dataSnapshot.child("job").getValue(String.class);
                stdpresent=dataSnapshot.child("present").getValue(String.class);
                stdpast=dataSnapshot.child("permanent").getValue(String.class);
                stdimg=dataSnapshot.child("imgurl").getValue(String.class);
                stdsession=dataSnapshot.child("session").getValue(String.class);



                sid.setText(stdid);
                sname.setText(stdname);
                sname2.setText(stdname);
                sfaculty.setText(stdfalculty);
                sdept.setText(stddept);
                sblood.setText(stdblood);
                sgender.setText(stdgender);
                sphn.setText(stdphn);
                smail.setText(stdmail);
                sjob.setText(stdjob);
                spresentad.setText(stdpresent);
                spermanentad.setText(stdpast);
                ssession.setText(stdsession);

                if(!stdimg.isEmpty()){
                    Picasso.get().load(stdimg)

                            .placeholder(R.drawable.preloader)
                            .into(imageView);
                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sphn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall();
            }
        });

        smail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={stdmail};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Student Information.");
                intent.putExtra(Intent.EXTRA_TEXT,"Write Your Message..");
                intent.putExtra(Intent.EXTRA_CC,stdmail);
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));

            }
        });

    }


    private void makePhoneCall() {
        if(ContextCompat.checkSelfPermission(user_profile_info.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(user_profile_info.this,
                    new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }
        else{
            String dial = "tel:"+stdphn;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
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


            Intent intent= new Intent(this, profile.class);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,profile.class);
        startActivity(intent);
        finish();

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
