package com.example.nishangunawardena.costgurdapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Nishan Gunawardena on 12/7/2015.
 */
public class SendArrivalData extends AsyncTask<String, String,String> {

    String result = "";
    InputStream isr = null;
    String s = "";
    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(params[0]);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
            //text.setText("Couldnt connect to database");
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");

            }

            isr.close();

            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        return s ;

    }
}
