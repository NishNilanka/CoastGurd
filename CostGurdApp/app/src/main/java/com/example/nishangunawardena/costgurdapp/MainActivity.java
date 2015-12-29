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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends Activity {

    Button btnLoggin;
    EditText txtUserName,txtPassword;
    ProgressBar progressBar;
    TextView Loading;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            btnLoggin=(Button)findViewById(R.id.login);

            Loading = (TextView) findViewById(R.id.textView50);
            progressBar = (ProgressBar) findViewById(R.id.progressBar1);
            Loading.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            txtUserName =(EditText)findViewById(R.id.username);
            txtPassword =(EditText)findViewById(R.id.Password);
            btnLoggin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                        if(isConnectingToInternet()) {


                            txtUserName =(EditText)findViewById(R.id.username);
                            txtPassword =(EditText)findViewById(R.id.Password);
                            progressBar.setVisibility(View.VISIBLE);
                            Loading.setVisibility(View.VISIBLE);


                            if(txtUserName.getText().toString().trim().equals("")||txtPassword.getText().toString().trim().equals(""))
                            {
                                Loading.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                return;
                            }
                            else {
                                userValidation validity = new userValidation();
                                try {
                                    String result = validity.execute(txtUserName.getText().toString().trim(), calculateHMAC(txtPassword.getText().toString().trim())).get();
                                    JSONObject json = new JSONObject(result);
                                    System.out.println(json.getString("success"));
                                    if (json.getString("success").equals("1")) {
                                        Intent intent;
                                        intent = new Intent(v.getContext(), DashBoard.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Loading.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Invalid User Name or Password!\nවලංගු නොවන පරිශීලක නම හෝ මුරපදය!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                            /*Intent intent;
                                            intent = new Intent(v.getContext(), DashBoard.class);
                                            startActivity(intent);
                                            finish();*/

                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();

                                    }

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //boolean status = login(txtUserName.getText().toString().trim(), calculateHMAC(txtPassword.getText().toString().trim()));


                                //Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                                //System.out.println(calculateHMAC(txtPassword.getText().toString().trim()));
                            }

                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            /*Intent intent;
                                            intent = new Intent(v.getContext(), DashBoard.class);
                                            startActivity(intent);
                                            finish();*/

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }


                    }
                   /* else{
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                    }*/

            });

    }

    private boolean login(final String username, final String password) {


        return false;
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

    public static String calculateHMAC(String password) {
        try {
            String key = "47f476b4";
            SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "HMACSHA256");

            // Create a MAC object using HMAC-MD5 and initialize with key
            Mac mac1 = Mac.getInstance("HMACSHA256");
            mac1.init(sk);
            byte[] digest1 = mac1.doFinal(password.getBytes());
            String result = "";
            for (final byte element : digest1) {
                result += Integer.toString((element & 0xff) + 0x100, 16).substring(1);
            }
            return result;
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            //Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
