package com.example.nishangunawardena.costgurdapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.concurrent.ExecutionException;

public class Departure extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView text;
    TextView harbour;
    TextView date, boatName;
    CheckBox imulCheck, logCheck, highSeaLicenseCheck, bluBookCheck, safetyJacketCheck;
    Button sendButton;
    EditText remarks;
    String regNumber;
    String voyageNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);
        final Getdata gd = new Getdata();

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        final String areaCode = spinner.getSelectedItem().toString();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  gd.execute("http://192.248.22.121/GPS_mobile/Nishan/getlocal_reg_no.php?code=areaCode");

                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }

                                          });

            text = (AutoCompleteTextView) findViewById(R.id.autoRegNo);
        text.setThreshold(1);
        text.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Getdata.RegNo));

        RegNoSelect regNo = new RegNoSelect();
        text.setOnItemClickListener(regNo);
        text.setOnItemSelectedListener(regNo);
        text.setSelection(0);

        sendButton = (Button) findViewById(R.id.button);
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

                try{
                    String safeUrl = "http://192.248.22.121/GPS_mobile/Nishan/SendDepartureData.php?" +
                            "q="+ URLEncoder.encode(imul)+"&voyageNo="+URLEncoder.encode(voyageNo)+
                            "&imulCheck="+URLEncoder.encode(imulChecking)+
                            "&logBook="+URLEncoder.encode(logBook)+
                            "&highSea="+URLEncoder.encode(highSea)+
                            "&blueBook="+URLEncoder.encode(blueBook)+
                            "&safetyJackets="+URLEncoder.encode(safetyJacket)+
                            "&remarks="+URLEncoder.encode(remarksValue)+"";
                    String result = sendDepartureData.execute(safeUrl).get();
                    if(result.length() > 0)
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                    Getdata.RegNo.clear();
                    finish();

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                //System.out.print(s);

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
            try {
                s = GHD.execute("http://192.248.22.121/GPS_mobile/Nishan/getHarbourAndDate.php?q="+regNumber).get();
                System.out.println("PHP array" + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            harbour = (TextView) findViewById(R.id.harbourText);
            date = (TextView) findViewById(R.id.Date);
            boatName = (TextView) findViewById(R.id.boatTextfield);
            array = s.split("@");
            harbour.setText("");
            date.setText("");
            boatName.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
            boatName.setText(array[2]);
            voyageNo = array[3];

            //System.out.println("1423333 " + array[1]);

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
            harbour = (TextView) findViewById(R.id.harbourText);
            date = (TextView) findViewById(R.id.Date);
            array = s.split(" ");
            harbour.setText("");
            date.setText("");
            boatName.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
            boatName.setText(array[3]);
            voyageNo = array[2];
            //System.out.println("1423333 " + array[1]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
