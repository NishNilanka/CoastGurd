package com.example.nishangunawardena.costgurdapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Departure extends Activity implements View.OnClickListener {

    AutoCompleteTextView text;
    TextView harbour;
    TextView date, boatName;
    CheckBox imulCheck, logCheck, highSeaLicenseCheck, bluBookCheck, safetyJacketCheck;
    Button sendButton;
    EditText remarks;
    String regNumber;
    String voyageNo;
    ArrayList<String> departyreIMULANo = new ArrayList<String>();
    ArrayAdapter<String> departureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        sendButton = (Button) findViewById(R.id.button);
        text = (AutoCompleteTextView) findViewById(R.id.autoRegNo);
        harbour = (TextView) findViewById(R.id.harbourText);
        date = (TextView) findViewById(R.id.Date);
        boatName = (TextView) findViewById(R.id.boatTextfield);

        Getdata gd = new Getdata();
        gd.execute("http://192.248.22.121/GPS_mobile/Nishan/getlocal_reg_no.php");
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String areaCode = spinner.getSelectedItem().toString();


                text.setText("");
                text.setThreshold(1);
                departyreIMULANo.clear();
                //Toast.makeText(getBaseContext(),areaCode,Toast.LENGTH_SHORT).show();
                getDepartureIMULAbyAreaCode(areaCode);
                System.out.println(departyreIMULANo);
                departureAdapter = new ArrayAdapter<String>(Departure.this, R.layout.support_simple_spinner_dropdown_item, departyreIMULANo);
                departureAdapter.notifyDataSetChanged();
                text.setAdapter(departureAdapter);

                RegNoSelect regNo = new RegNoSelect();
                text.setOnItemClickListener(regNo);
                text.setOnItemSelectedListener(regNo);
                text.setSelection(0);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    System.out.println(text.getText().length());
                    if (text.getText().length() != 12) {
                        //Toast.makeText(getApplicationContext(), "Nishan", Toast.LENGTH_LONG).show();
                        harbour.setText("");
                        boatName.setText("");
                        date.setText("");
                        sendButton.setEnabled(false);
                    }
                }
                return false;
            }
        });




        remarks = (EditText) findViewById(R.id.remarks);
        imulCheck = (CheckBox) findViewById(R.id.imulCheck);
        logCheck = (CheckBox) findViewById(R.id.logCheck);
        highSeaLicenseCheck = (CheckBox) findViewById(R.id.highSeaLicenseCheck);
        bluBookCheck = (CheckBox) findViewById(R.id.blueBookCheck);
        safetyJacketCheck = (CheckBox) findViewById(R.id.safetyjacketCheck);
        sendButton.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        Getdata.RegNo.clear();
        finish();
        return;
    }

    public void getDepartureIMULAbyAreaCode(String code)
    {

        for (String original : Getdata.RegNo)
        {

            System.out.println(original.substring(original.length()-3) +" "+ code);
            //System.out.println(IMULANo);

            if (original.substring(original.length()-3).equals(code)){
                departyreIMULANo.add(original);
                System.out.println(original.substring(original.length() - 3));
                System.out.println("depart " + departyreIMULANo);
            }
        }


    }
    @Override
    public void onClick(View v) {

        switch  (v.getId())
        {
            case R.id.button:
                String imulChecking = null;
                String logBook = null;
                String highSea = null;
                String blueBook = null;
                String safetyJacket = null;
                String imul = text.getText().toString();
                if(imulCheck.isChecked()) {
                    imulChecking = "Yes";
                }
                else if(!imulCheck.isChecked()) {
                    imulChecking = "No";
                }
                if (logCheck.isChecked()) {
                    logBook = "Yes";
                }
                else if (!logCheck.isChecked()) {
                    logBook = "No";
                }
                if(highSeaLicenseCheck.isChecked()) {
                    highSea = "Yes";
                }
                else if(!highSeaLicenseCheck.isChecked()) {
                    highSea = "No";
                }
                if (bluBookCheck.isChecked()) {
                    blueBook = "Yes";
                }
                else if (!bluBookCheck.isChecked()) {
                    blueBook = "No";
                }
                if (safetyJacketCheck.isChecked()) {
                    safetyJacket = "Yes";
                }
                else if (!safetyJacketCheck.isChecked()) {
                    safetyJacket = "No";
                }
                String remarksValue = remarks.getText().toString();
                SendDepartureData sendDepartureData = new SendDepartureData();
                System.out.println(imul);
                if(isConnectingToInternet()) {
                    try {
                        String safeUrl = "http://192.248.22.121/GPS_mobile/Nishan/SendDepartureData.php?" +
                                "q=" + URLEncoder.encode(imul) + "&voyageNo=" + URLEncoder.encode(voyageNo) +
                                "&imulCheck=" + URLEncoder.encode(imulChecking) +
                                "&logBook=" + URLEncoder.encode(logBook) +
                                "&highSea=" + URLEncoder.encode(highSea) +
                                "&blueBook=" + URLEncoder.encode(blueBook) +
                                "&safetyJackets=" + URLEncoder.encode(safetyJacket) +
                                "&remarks=" + URLEncoder.encode(remarksValue) + "";
                        String result = sendDepartureData.execute(safeUrl).get();
                        System.out.print(safeUrl);
                        System.out.print(result);
                        if (result.length() > 0)
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                        Getdata.RegNo.clear();
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //System.out.print(s);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Departure.this);
                    builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                break;
        }

    }

    class RegNoSelect implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            regNumber = (String) text.getAdapter().getItem(position);
            String[] array;
            GetHarbourAndDate GHD = new GetHarbourAndDate();
            String s = null;
            if (isConnectingToInternet()) {
                try {
                    s = GHD.execute("http://192.248.22.121/GPS_mobile/Nishan/getHarbourAndDate.php?q=" + regNumber).get();
                    System.out.println("PHP array" + s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                array = s.split("@");
                harbour.setText("");
                date.setText("");
                boatName.setText("");
                harbour.setText(array[1]);
                date.setText(array[0]);
                boatName.setText(array[2]);
                voyageNo = array[3];
                sendButton.setEnabled(true);

                //System.out.println("1423333 " + array[1]);
            }else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Departure.this);
                builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            regNumber = (String) text.getAdapter().getItem(position);
            String[] array;
            System.out.println(regNumber);
            GetHarbourAndDate GHD = new GetHarbourAndDate();
            String s = null;
            try {
                s = GHD.execute("http://192.248.22.121/GPS_mobile/Nishan/getHarbourAndDate.php?q="+regNumber).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            array = s.split(" ");
            harbour.setText("");
            date.setText("");
            boatName.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
            boatName.setText(array[3]);
            voyageNo = array[2];
            sendButton.setEnabled(true);
            //System.out.println("1423333 " + array[1]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            sendButton.setEnabled(false);
        }
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
