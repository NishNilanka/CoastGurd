package com.example.nishangunawardena.costgurdapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Nishan Gunawardena on 12/4/2015.
 */
public class getArrivalIMUL extends AsyncTask<String,String,String> {
    public static ArrayList<String> ArrivalRegNo = new ArrayList<String>();
    @Override
    protected String doInBackground(String... params) {
        String result = "";
        InputStream isr = null;
        String s = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
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
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        //parse json data
        try {

            JSONArray jArray = new JSONArray(result);
            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                ArrivalRegNo.add(json.getString("local_reg_no"));

            }

            //text.setText(s);
            //System.out.println("nishan123 " + RegNo);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data " + e.toString());
        }
        return s;
    }
}
