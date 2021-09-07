package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class appmenu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    long backpress;

    BottomNavigationView bottomNavigationView;
    private DrawerLayout mNavDrawer;
    private CardView crd1,crd2,crd3,crd4,crd5,crd6,crd7,crd8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

       /* getWindow().setNavigationBarColor(Color.WHITE);*/
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.buttonNavigationid) ;


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





        crd1=(CardView) findViewById(R.id.student);
        /*crd2=(CardView) findViewById(R.id.reg);
        crd3=(CardView) findViewById(R.id.log);*/
        crd4=(CardView) findViewById(R.id.blood);

        crd1.setOnClickListener(this);
     /*   crd2.setOnClickListener(this);
        crd3.setOnClickListener(this);*/
        crd4.setOnClickListener(this);




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){


                    case R.id.tabhome:
                        Intent intent=new Intent(appmenu.this,profile.class);
                        startActivity(intent);
                        finish();
                        break;

                   /* case R.id.tabsetting:
                        Intent intent1=new Intent(appmenu.this,MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;*/

                    case R.id.tabcontact:
                        Intent intent2=new Intent(appmenu.this,developer.class);
                        startActivity(intent2);
                        finish();
                        break;


                }


                return true;
            }
        });





            }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.exit_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
/*

        if(item.getItemId()==R.id.exitId){


            AlertDialog.Builder builder=new AlertDialog.Builder(appmenu.this);
            builder.setTitle("Warning!");
            builder.setIcon(R.drawable.warning);
            builder.setMessage("Are you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //apphome.super.onBackPressed();
                    dialog.cancel();
                }
            });
            builder.show();




        }
*/

        if(item.getItemId()==R.id.profileID){
            Intent intent=new Intent(appmenu.this,profile.class);
            startActivity(intent);
            finish();
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
            /*super.onBackPressed();*/
        }
        else{
            Toast.makeText(getBaseContext(),"Tap to exit",Toast.LENGTH_LONG).show();
        }
        backpress=System.currentTimeMillis();

    }


/*    @Override
    public void onBackPressed(){




        AlertDialog.Builder builder=new AlertDialog.Builder(appmenu.this);
        builder.setTitle("Warning!");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                appmenu.super.onBackPressed();
                finishAffinity();
                finishActivity(1);
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //apphome.super.onBackPressed();

                dialog.cancel();
            }
        });
        builder.show();




    }*/

    @Override
    public void onClick(View v) {

        if(v==crd1){
            Intent intent=new Intent(appmenu.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

/*        if(v==crd2){
            Intent intent=new Intent(appmenu.this,registration.class);
            startActivity(intent);
            finish();
        }

        if(v==crd3){
            Intent intent=new Intent(appmenu.this,login.class);
            startActivity(intent);
            finish();
        }*/

        if(v==crd4){
            Intent intent=new Intent(appmenu.this,blood.class);
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

}
