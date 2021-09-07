package com.student.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    long backpress;

    private Button btnlog;
    private TextView txtregistration,txtforgot;

    AlertDialog alertDialog;

    private Button loginuser,signupuser;
    private EditText email_login,password_login;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().setNavigationBarColor(Color.WHITE);

        mAuth = FirebaseAuth.getInstance();

        //Custom_Loading
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.custom_loading_layout,null);
        alertDialog=new AlertDialog.Builder(this).setView(view)
                .create();
        alertDialog.setCancelable(false);





        email_login=(EditText) findViewById(R.id.loginmail);
        password_login=(EditText) findViewById(R.id.loginPass);

        txtforgot=(TextView) findViewById(R.id.forgot_password);
        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,forgot_password.class);
                startActivity(intent);
            }
        });

        txtregistration=(TextView) findViewById(R.id.textreg);
        txtregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,registration.class);
                startActivity(intent);
            }
        });




        btnlog=(Button) findViewById(R.id.adminloginic);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(login.this,appmenu.class);
                startActivity(intent);*/


          /*     Hide Keyboard*/
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);

                if (isOnline()){

                    loginmethod();
                }

                else {


                    Toast.makeText(login.this,"No internet connection!",Toast.LENGTH_LONG).show();
                }
            }
        });



    }




    public void loginmethod() {


        final String email = email_login.getText().toString().trim();
        final String password = password_login.getText().toString().trim();
        /* final String passs2=repass1.getText().toString().trim();*/


        if (email.isEmpty()) {
            email_login.setError("Write a valid email address.");
            email_login.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_login.setError("Email is incorrect.");
            email_login.requestFocus();
            return;
        } else if (password.isEmpty()) {

            password_login.setError("Enter Your Password");
            password_login.requestFocus();
            return;
        } else if (password.length() < 6) {

            password_login.setError("Password Incorrect.");
            password_login.requestFocus();
            return;
        } else {



            /*progressDialog.show();*/
            alertDialog.show();
            /*WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width=350;
            lp.height=350;
            alertDialog.getWindow().setAttributes(lp);*/
            alertDialog.getWindow().setLayout(350,350);

        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    alertDialog.dismiss();
//                    alertDialog.dismiss();
                    /*Toast.makeText(login.this,"Log in successfull.",Toast.LENGTH_LONG).show();*/


                    Intent intent = new Intent(login.this, login_validation.class);
                    startActivity(intent);

                } else {
                    alertDialog.dismiss();
                    /*alertDialog.dismiss();*/
                   /* progressDialog.dismiss();*/

                    Toast.makeText(login.this, "Log in failed.Please try again.", Toast.LENGTH_LONG).show();

                }

            }
        });
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
