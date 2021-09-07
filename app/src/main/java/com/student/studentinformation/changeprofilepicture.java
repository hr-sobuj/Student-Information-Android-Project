package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class changeprofilepicture extends AppCompatActivity  implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{



    private DrawerLayout mNavDrawer;
    private Button btnchooser,btnsaveimage;
    private ImageView imageView;

    private RadioButton yearbutton;
    private Uri filepath;
    StorageTask storageTask;

    private String yearvalue;

    private String genderValue;
    private RadioGroup resultgrp;

    FirebaseAuth auth;
    FirebaseUser user;
    String stdname,mailadd;
    MediaPlayer mediaPlayer;

    AlertDialog progressDialog;


    private static final int PICK_IMAGE_REQUEST=1;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    private Uri mImageUri;


    String stdid,stdfalculty,stddept,stdblood,stdgender,stdphn,stdmail,stdjob,stdpresent,stdpast;
    DatabaseReference reference;
    ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_changeprofilepicture);


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

        mailadd=user.getEmail();
        mailadd=mailadd.replaceAll("[^a-zA-Z0-9]","");


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        btnchooser=(Button) findViewById(R.id.imgchooserid);
        btnsaveimage=(Button) findViewById(R.id.saveimgid);

        imageView=(ImageView) findViewById(R.id.imageviewid);

        resultgrp = (RadioGroup) findViewById(R.id.radioGrp);


        btnsaveimage.setOnClickListener(this);
        btnchooser.setOnClickListener(this);

        reference= FirebaseDatabase.getInstance().getReference("StudentInfo");




    }

    @Override
    public void onClick(View v) {

        if (v==btnchooser){


            chooseImage();
        }


        if (v==btnsaveimage){

            uploadImage();

        }

    }


    private void chooseImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==RESULT_OK
                &&data!=null && data.getData()!=null){


            filepath=data.getData();



            Picasso.get().load(filepath).into(imageView);


        }
    }

    private void uploadImage() {


        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        progressDialog=new AlertDialog.Builder(this).setView(view)
                .create();




        progressDialog.setCancelable(false);


        progressDialog.show();

        progressDialog.getWindow().setLayout(350,350);

            /*final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading..");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();*/


            if (filepath != null) {


                StorageReference ref = mStorageRef.child(mailadd);

                ref.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                Task<Uri> urltask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urltask.isSuccessful()) ;
                                final Uri downloaduri = urltask.getResult();

                               /* alertDialog.dismiss();*/
                                progressDialog.dismiss();

                                /*Toast.makeText(changeprofilepicture.this, "Image uploaded successfully.", Toast.LENGTH_LONG).show();*/



                                reference.child(mailadd).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                        dataSnapshot.getRef().child("imgurl").setValue(downloaduri.toString());



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                Intent intent=new Intent(changeprofilepicture.this,image_uploaded_successfully_msg.class);
                                startActivity(intent);




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        /*alertDialog.dismiss();*/
                        progressDialog.dismiss();

                        Toast.makeText(changeprofilepicture.this, "Operation Failed.Please try again. :( ", Toast.LENGTH_LONG).show();

                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploading " + (int) progress + "%");
                                /*alertDialog.setMessage("Uploading " + (int) progress + "%");*/


                            }
                        });

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



            Intent intent= new Intent(changeprofilepicture.this, profile.class);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(changeprofilepicture.this,profile.class);
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
