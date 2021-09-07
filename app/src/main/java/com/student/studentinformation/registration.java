package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.MailTo;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    private Button showlistFromdatabase;
    private Spinner spinner1;
    String[] bloodGroupNameList1;
    private DrawerLayout mNavDrawer;

    private AlertDialog progressDialog;
    private static final int REQUEST_CALL = 1;

    private EditText sid,sname,sfaculty,sdept,sblood,sgender,sphn,smail,sjob,spresentad,spermanentad,spass;
    private Button btnsubmit,btnback;

    DatabaseReference databaseReference;

private String id,name,faculty,department,bloodgroup,gender,phonenumber,email,job,present,past,password,lastblooddonate,imgurldatabase,userkey,sessions;


    Button btnaYes,btnaNo;

    private Button signup_button;
    private EditText mailadress1,pass1,repass1;
    private FirebaseAuth mAuth;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraton);


        getWindow().setNavigationBarColor(Color.WHITE);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        databaseReference= FirebaseDatabase.getInstance().getReference("StudentInfo");
        mAuth = FirebaseAuth.getInstance();
        sid=(EditText) findViewById(R.id.id);
        sname=(EditText) findViewById(R.id.writeYourName);
        /*sfaculty=(EditText) findViewById(R.id.faculty);
        sdept=(EditText) findViewById(R.id.dept);*/
        sblood=(EditText) findViewById(R.id.blood);
       /* sgender=(EditText) findViewById(R.id.gender);*/
        sphn=(EditText) findViewById(R.id.phn);
        smail=(EditText) findViewById(R.id.mail);
        /*sjob=(EditText) findViewById(R.id.job);
        spresentad=(EditText) findViewById(R.id.presentaddress);
        spermanentad=(EditText) findViewById(R.id.permanentaddress);*/
        spass=(EditText) findViewById(R.id.pass);



        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        progressDialog=new AlertDialog.Builder(this).setView(view)
                .create();
        progressDialog.setCancelable(false);



/*
        btnback=(Button) findViewById(R.id.back);*/
        btnsubmit=(Button) findViewById(R.id.submitYourInfo);


/*        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registration.this, MainActivity.class);
                startActivity(intent);
            }
        });*/




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





        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                id=sid.getText().toString().trim();
                name=sname.getText().toString().trim();
                faculty="";
                department="";
                bloodgroup=spinner1.getSelectedItem().toString().trim();
                gender="";
                phonenumber=sphn.getText().toString();
                email=smail.getText().toString().trim();
                job="";
                present="";
                past="";
                lastblooddonate="";
                imgurldatabase="";
                password=spass.getText().toString().trim();


                if(id.isEmpty()&&id.length()==7){
                    sname.setError("Student id is wrong");
                    sname.requestFocus();
                    return;
                }



                if(name.isEmpty()){
                    sname.setError("Input your name");
                    sname.requestFocus();
                    return;
                }
       /*         if((bloodgroup.length() >4)){
                    sblood.setError("Input blood group");
                    sblood.requestFocus();
                    return;
                }*/

                if(phonenumber.isEmpty()){
                    sphn.setError("Input phone number");
                    sphn.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    smail.setError("Input your email");
                    smail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    smail.setError("Input a valid email address");
                    smail.requestFocus();
                    return;
                }
                if(password.length()<6){

                    spass.setError("Password length 6");
                    spass.requestFocus();
                    return;
                }
                if(!Patterns.PHONE.matcher(phonenumber).matches()){
                    sphn.setError("Invalid phone number");
                    sphn.requestFocus();
                    return;
                }


                ////validation_checker
                if(id.length()==7){



                    if(phonenumber.length()==11&&Patterns.PHONE.matcher(phonenumber).matches()){

                        if(bloodgroup.length()<=3){





                     if(!(id.length() <=0) && !(name.length() <=0) && !(phonenumber.length() <=0)){



                         progressDialog.show();

                         progressDialog.getWindow().setLayout(350,350);

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();

                                String key=email.replaceAll("[^a-zA-Z0-9]","");
                                userkey=email.replaceAll("[^a-zA-Z0-9]","");
                                dbref ref=new dbref(id,name,faculty,department,bloodgroup,gender,phonenumber,email,job,present,past,password,lastblooddonate,imgurldatabase,userkey,sessions);
                                databaseReference.child(key).setValue(ref);


                                Intent intent1=new Intent(registration.this,email_verification.class);
                                intent1.putExtra("current-user",key);
                                startActivity(intent1);
                                /*Toast.makeText(registration.this,"Registration Successful.",Toast.LENGTH_LONG).show();*/

                                auth=FirebaseAuth.getInstance();
                                user=auth.getCurrentUser();

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            //Toast.makeText(registration.this,"Email verification code sent to your mail.Please verify your email address.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });







                            } else {
                                progressDialog.dismiss();

                                if(task.getException() instanceof FirebaseAuthUserCollisionException){

                                    Toast.makeText(registration.this,"User Already registered",Toast.LENGTH_LONG).show();

                                }
                                else {
                                    Toast.makeText(registration.this,"Exception "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    });



                }

                else {

                    Toast.makeText(registration.this,"Star sign field must be fill up.",Toast.LENGTH_LONG).show();

                }


                ///validation_checker
                        }
                        else{
                            Toast.makeText(registration.this,"Blood group is empty.",Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(registration.this,"Invalid Phone number",Toast.LENGTH_LONG).show();
                    }

                }

                else {
                    Toast.makeText(registration.this,"Student id is wrong",Toast.LENGTH_LONG).show();
                }



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

    private void showdialog() {

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);



        final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(view)
                .create();

        alertDialog.show();
        alertDialog.setCancelable(false);



    }

}
