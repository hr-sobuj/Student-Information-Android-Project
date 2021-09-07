package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class blood extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button showlistFromdatabase;
    private Spinner spinner1;
    String[] bloodGroupNameList1;
    private DrawerLayout mNavDrawer;

    private ProgressDialog progressDialog;
    private static final int REQUEST_CALL = 1;

    private Button btnsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_blood_search);

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

        showlistFromdatabase=(Button) findViewById(R.id.listshowww);
        spinner1=(Spinner) findViewById(R.id.searchBloodSpinner);
        bloodGroupNameList1=getResources().getStringArray(R.array.bloodgroupList);

        ArrayAdapter<String> adapterspinnerForBloodSearch=new ArrayAdapter<String>(this,R.layout.samplelistviewforsearch,R.id.sampleListForSearchId,bloodGroupNameList1){
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
                    tv.setTextColor(Color.GRAY);

                } else {

                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = (LinearLayout) inflater.inflate(R.layout.samplelistviewforsearch, aparent, false);
                    TextView tv = (TextView) v.findViewById(R.id.sampleListForSearchId);
                    tv.setText(getItem(position));
                    tv.setTextColor(Color.BLACK);


                }
                return v;
            }


        };
        spinner1.setAdapter(adapterspinnerForBloodSearch);



        showlistFromdatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val=spinner1.getSelectedItem().toString();

                Intent intent=new Intent(blood.this,blood_search_result_list.class);
                intent.putExtra("blooogroup",val);
                /*Toast.makeText(blood.this,val,Toast.LENGTH_LONG).show();*/
                startActivity(intent);


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
