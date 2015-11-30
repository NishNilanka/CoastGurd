package com.example.nishangunawardena.costgurdapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Nishan Gunawardena on 11/23/2015.
 */
public class getBoatDetails extends AsyncTask<String,String,String> {
    //public static String Detail = new String();
    @Override
    protected String doInBackground(String... params) {
        String result = "";
        InputStream isr = null;
        String s = "";
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
        
        try {

            JSONArray jArray = new JSONArray(result);
            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                s += json.getString("voyage_no") + "@";
                s += json.getString("local_reg_no") + "@";
                s += json.getString("departure_date") + "@";
                s += json.getString("departure_time") + "@";
                s += json.getString("departure_port") + "@";
                s += json.getString("arrival_date") + "@";
                s += json.getString("arrival_time") + "@";
                s += json.getString("arrival_port") + "@";
                s += json.getString("voyage_status") + "@";
                s += json.getString("name")+ "@";

            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }
        return s ;
    }

}
