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

public class bloodinfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DatabaseReference reference;

    FirebaseAuth auth;
    FirebaseUser user;
    String mailadd;

    private Button showlistFromdatabase,savebtn;
    private Spinner spinner1;
    String[] bloodGroupNameList1;
    private DrawerLayout mNavDrawer;

    private RadioGroup genderRadioGroup;
    private RadioButton genderradioButton;
    private ProgressDialog progressDialog;


    EditText last;


    String lastdoante,wish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_blood_info);


        getWindow().setNavigationBarColor(Color.WHITE);


        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        savebtn=findViewById(R.id.submitYourInfo);
        last=findViewById(R.id.lastblooddonate);


        genderRadioGroup=(RadioGroup) findViewById(R.id.radiobtn);



        reference= FirebaseDatabase.getInstance().getReference("StudentInfo");

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



        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mailadd=user.getEmail();
                mailadd=mailadd.replaceAll("[^a-zA-Z0-9]","");




                lastdoante=last.getText().toString();

                int selected=genderRadioGroup.getCheckedRadioButtonId();
                genderradioButton=(RadioButton) findViewById(selected);

                wish=genderradioButton.getText().toString();



                reference.child(mailadd).child("blood").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        if(!dataSnapshot.exists()){
                            Toast.makeText(bloodinfo.this,"Info Not found",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(bloodinfo.this,MainActivity.class);
                            startActivity(intent);

                        }


                        if(isOnline()){

                            String xtmp=dataSnapshot.getValue(String.class);;

                            reference.child(mailadd).child("lastdonate").setValue(lastdoante);
                            reference.child(mailadd).child("yesno").setValue(wish);

                            /*Toast.makeText(bloodinfo.this,"Information Updated.",Toast.LENGTH_LONG).show();*/
                            Intent intent=new Intent(bloodinfo.this,successfull_msg.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(bloodinfo.this,"No internet connection.Please try again.",Toast.LENGTH_LONG).show();
                        }




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
