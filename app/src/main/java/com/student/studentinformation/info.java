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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class info extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout mNavDrawer;
    private ListView DataDisplay;
    private TextView txt;
    String val,val1;
    String stdid,stdname,stdfalculty,stddept,stdblood,stdgender,stdphn,stdmail,stdjob,stdpresent,stdpast;
    DatabaseReference reference;
    AlertDialog progressDialog;

    private TextView sid,sname,sfaculty,sdept,sblood,sgender,sphn,smail,sjob,spresentad,spermanentad;
    private Button btn;

    private List<dbref> firebasess;
    private firebase_adapter firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_info);


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

/*        btn=(Button) findViewById(R.id.backid);

        sid=(TextView) findViewById(R.id.uid);
        sname=(TextView) findViewById(R.id.uname);
        sfaculty=(TextView) findViewById(R.id.ufaculty);
        sdept=(TextView) findViewById(R.id.udept);
        sblood=(TextView) findViewById(R.id.ublood);
        sgender=(TextView) findViewById(R.id.ugender);
        sphn=(TextView) findViewById(R.id.uphn);
        smail=(TextView) findViewById(R.id.umail);
        sjob=(TextView) findViewById(R.id.ujob);
        spresentad=(TextView) findViewById(R.id.upresent);
        spermanentad=(TextView) findViewById(R.id.upast);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info.this,MainActivity.class);
                startActivity(intent);
            }
        });*/



        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        progressDialog=new AlertDialog.Builder(this).setView(view)
                .create();
        progressDialog.setCancelable(false);

        progressDialog.show();

        progressDialog.getWindow().setLayout(350,350);

        reference= FirebaseDatabase.getInstance().getReference("StudentInfo");




        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){

            val=bundle.getString("key");
            val1=bundle.getString("spinkey");

        }
        assert val1 != null;
        val1=val1.toLowerCase();


        firebasess=new ArrayList<>();

        firebaseAdapter=new firebase_adapter(info.this,firebasess);


        DataDisplay=findViewById(R.id.firebaseDataListView);




        DataDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(info.this,student_details.class);

                dbref firebaseDataUpdate=(dbref) firebaseAdapter.getItem(position);

                intent.putExtra("keys",firebaseDataUpdate.getPrimarykey());

                intent.putExtra("key",val);
                intent.putExtra("spinkey",val1);


                startActivity(intent);

            }
        });






        reference.orderByChild(val1).equalTo(val).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    progressDialog.dismiss();

                }
                else{
                    progressDialog.dismiss();
                    /*Toast.makeText(info.this,"Student not found :(",Toast.LENGTH_LONG).show();*/
                    Intent intent=new Intent(info.this,no_data_found.class);
                    startActivity(intent);


                }

                firebasess.clear();
                int count=0;

                for(DataSnapshot i:dataSnapshot.getChildren()){

                    /*    if(!dataSnapshot.child("user_Blood_Group").child("user_Department").child("user_Gender").child("user_Mobile_Number").child("user_Name").exists()){*/

                    progressDialog.dismiss();


                    dbref firesdata=i.getValue(dbref.class);

                    firebasess.add(firesdata);

               /* }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(firebase_data_display.this,"No data is Here",Toast.LENGTH_LONG).show();
                }*/





                }
                DataDisplay.setAdapter(firebaseAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*


        reference.orderByChild("blood").equalTo("O+").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){

                    Intent intent=new Intent(info.this,MainActivity.class);
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



                sid.setText(stdid);
                sname.setText(stdname);
                sfaculty.setText(stdfalculty);
                sdept.setText(stddept);
                sblood.setText(stdblood);
                sgender.setText(stdgender);
                sphn.setText(stdphn);
                smail.setText(stdmail);
                sjob.setText(stdjob);
                spresentad.setText(stdpresent);
                spermanentad.setText(stdpast);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/


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


            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,MainActivity.class);
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
