package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathDashPathEffect;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class profile extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    long backpress;

    private DrawerLayout mNavDrawer;
    private TextView currentusername;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    String stdname, mailadd;
    ProgressDialog progressDialog;
    private ImageView profile_picture;
    private CardView crd1, crd2, crd3, crd4, crd5, crd6;

    String stdid, stdfalculty, stddept, stdblood, stdgender, stdphn, stdmail, stdjob, stdpresent, stdpast, stdimg, stdsession;

    BottomNavigationView bottomNavigationView;

    AlertDialog alertDialog;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_profile);


        /////Finger_and_android_id
/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
     *//*       FingerprintManager fingerprintManager=(FingerprintManager) getApplicationContext().getSystemService(Context.FINGERPRINT_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){
                Log.e("tag","not support");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                Log.e("tag","enroll support");
            }
            else{

                Log.e("tag"," support");
            }*//*

            FingerprintManagerCompat fingerprintManager=FingerprintManagerCompat.from(this);
            if(!fingerprintManager.isHardwareDetected()){
                Log.e("tag","not support");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                Log.e("tag","enroll support");
            }
            else{

                Log.e("tag"," support");
            }


        }


        String identi = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("identify", identi);

        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        int x;

        *//*WifiManager manager=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info=manager.getConnectionInfo();
        x=info.getMacAddress();*//*


        *//*Toast.makeText(profile.this,identi,Toast.LENGTH_LONG).show();*//*
//d61018900b9118b5*/


        getWindow().setNavigationBarColor(Color.WHITE);
      /*  getWindow().setNavigationBarColor(Color.rgb(11,80,113));*/
        /*getWindow().setNavigationBarColor(Color.rgb(14,81,113));*/

  /*      Snackbar.make(findViewById(R.id.coordinator),"Profile loaded successfully.",Snackbar.LENGTH_LONG).show();*/



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

        profile_picture=(ImageView) findViewById(R.id.profilepicture);

        reference= FirebaseDatabase.getInstance().getReference("StudentInfo");
        currentusername=(TextView) findViewById(R.id.currentuser);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        alertDialog=new AlertDialog.Builder(this).setView(view)
                .create();




        alertDialog.setCancelable(false);

        alertDialog.getWindow().setLayout(50,50);

        alertDialog.show();
       /* WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width=350;
        lp.height=350;
        alertDialog.getWindow().setAttributes(lp);*/

        alertDialog.getWindow().setLayout(350,350);


        mailadd=user.getEmail();
        mailadd=mailadd.replaceAll("[^a-zA-Z0-9]","");


        crd1=(CardView) findViewById(R.id.crd1id);
        crd2=(CardView) findViewById(R.id.crd2id);
        crd3=(CardView) findViewById(R.id.crd3id);
        crd4=(CardView) findViewById(R.id.crd4id);
        crd5=(CardView) findViewById(R.id.crd5id);
        crd6=(CardView) findViewById(R.id.crd6id);

        crd1.setOnClickListener(this);
        crd2.setOnClickListener(this);
        crd3.setOnClickListener(this);
        crd4.setOnClickListener(this);
        crd5.setOnClickListener(this);
        crd6.setOnClickListener(this);








/*
        if( user.isEmailVerified()){
            Toast.makeText(profile.this,"Email is verified",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(profile.this,"Email is not verified",Toast.LENGTH_LONG).show();
        }*/

        reference.child(mailadd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(profile.this,"Info Not found",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(profile.this,MainActivity.class);
                    startActivity(intent);

                }

                /*progressDialog.dismiss();*/
                alertDialog.dismiss();

                stdname=dataSnapshot.child("name").getValue(String.class);

                stdname=stdname;


                currentusername.setText(stdname);

                stdimg=dataSnapshot.child("imgurl").getValue(String.class);
                if(!stdimg.isEmpty()){
                    Picasso.get().load(stdimg)

                            .placeholder(R.drawable.preloader)
                            .into(profile_picture);
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.profile_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.homeID){




            if( user.isEmailVerified()){
                Intent intent= new Intent(this, appmenu.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(profile.this," Please verify your email address.Then try again.",Toast.LENGTH_LONG).show();
            }


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        if(backpress+1000>System.currentTimeMillis()){
            moveTaskToBack(true);
            Process.killProcess(Process.myPid());
            System.exit(1);
            super.onBackPressed();

            finish();
            return;
        }
        else{
            Toast.makeText(getBaseContext(),"Tap to exit",Toast.LENGTH_LONG).show();
        }
        backpress=System.currentTimeMillis();

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
    public void onClick(View v) {
        if(v==crd1){
            if(true){
                Intent intent=new Intent(profile.this,updateinfopage.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(profile.this," Please verify your email address.Then try again.",Toast.LENGTH_LONG).show();
            }

        }

        if(v==crd2){
            if(true){
                Intent intent=new Intent(profile.this,changeprofilepicture.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(profile.this," Please verify your email address.Then try again.",Toast.LENGTH_LONG).show();
            }

        }

        if(v==crd3){
            if(true){
                Intent intent=new Intent(profile.this,add_job_circular.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(profile.this," Please verify your email address.Then try again.",Toast.LENGTH_LONG).show();
            }

        }

        if(v==crd4){

                Intent intent=new Intent(profile.this,login.class);
                startActivity(intent);
                finish();


        }

        if(v==crd5){

            Intent intent=new Intent(profile.this,appmenu.class);
            startActivity(intent);
            finish();


        }

        if(v==crd6){

            Intent intent=new Intent(profile.this,user_profile_info.class);
            startActivity(intent);
            finish();


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

    private void showdialogDismiss() {

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);



        final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(view)
                .create();

        alertDialog.dismiss();



    }

}
