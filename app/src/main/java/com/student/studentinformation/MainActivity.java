package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{




    private DrawerLayout mNavDrawer;
    private TextView registration;
    private EditText studentid;
    private Button search;

    private Button showlistFromdatabase,saveinfo;
    private Spinner spinner1,spinner;
    String[] deptlists,falist;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_find_student);


        getWindow().setNavigationBarColor(Color.WHITE);

        studentid=(EditText) findViewById(R.id.enterid);
        search=(Button) findViewById(R.id.searchinfo);




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

/*
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));*/


        //spinner

        showlistFromdatabase=(Button) findViewById(R.id.listshowww);
        spinner1=(Spinner) findViewById(R.id.chooseYourFaculty);
        deptlists=getResources().getStringArray(R.array.searchlist);

        ArrayAdapter<String> adapterspinnerFordept=new ArrayAdapter<String>(this,R.layout.samplelistviewforsearch,R.id.sampleListForSearchId,deptlists){
            @Override
            public boolean isEnabled(int position){

                if(position==0)
                {
                    return false;
                }
                else {
                    return true;
                }
            }



            @Override
            public View getDropDownView(int position, View convertView, ViewGroup aparent) {

                LinearLayout v = null;


                if (position == 0) {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = (LinearLayout) inflater.inflate(R.layout.samplelistviewforsearch, aparent, false);
                    TextView tv = (TextView) v.findViewById(R.id.sampleListForSearchId);

                    tv.setText(getItem(position));
                  /*  tv.setTextColor(Color.BLACK);*/

                } else {

                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = (LinearLayout) inflater.inflate(R.layout.samplelistviewforsearch, aparent, false);
                    TextView tv = (TextView) v.findViewById(R.id.sampleListForSearchId);
                    tv.setText(getItem(position));
                  /*  tv.setTextColor(Color.BLACK);*/


                }
                return v;
            }


        };
        spinner1.setAdapter(adapterspinnerFordept);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String value=studentid.getText().toString();
                String spinvalue=spinner1.getSelectedItem().toString();
                Intent intent=new Intent(MainActivity.this,info.class);
                intent.putExtra("key",value);
                intent.putExtra("spinkey",spinvalue);

                /*Toast.makeText(MainActivity.this,spinvalue,Toast.LENGTH_LONG).show();*/


                if(isOnline()){

                    if(!spinvalue.equals("Select One")){

                        if(studentid.length()<=0)
                        {
                            Toast.makeText(MainActivity.this,"Student Id is empty.Please input a id and try again",Toast.LENGTH_LONG).show();
                        }
                        else {

                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Haven't selected any searching category.",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
                }




            }
        });




/*
        registration=(TextView) findViewById(R.id.registrationid);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MainActivity.this, com.student.studentinformation.registration.class);
                startActivity(intent);
                finish();

            }
        });*/

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


            Intent intent= new Intent(this, appmenu.class);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,appmenu.class);
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
}
