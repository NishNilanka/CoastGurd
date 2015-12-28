package com.example.nishangunawardena.costgurdapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button b1,b2;
    EditText userName,password;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            b1=(Button)findViewById(R.id.login);
            userName =(EditText)findViewById(R.id.username);
            password =(EditText)findViewById(R.id.Password);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(userName.getText().toString().equals("admin") &&
                            password.getText().toString().equals("admin")) {
                        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                        if(isConnectingToInternet()) {
                            Intent intent;
                            intent = new Intent(v.getContext(), DashBoard.class);
                            startActivity(intent);
                            finish();

                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent;
                                            intent = new Intent(v.getContext(), DashBoard.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }

    public boolean isConnectingToInternet(){
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }


}
