package com.example.nishangunawardena.costgurdapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Departure extends Activity implements View.OnClickListener{

    AutoCompleteTextView text;
    TextView harbour;
    TextView date, boatName;
    CheckBox crew, eqipment;
    Button sendButton;
    EditText remarks;
    String regNumber;
    String voyageNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);
        Getdata gd = new Getdata();
        gd.execute("http://192.248.22.121/GPS_mobile/Nishan/getlocal_reg_no.php");

        text = (AutoCompleteTextView) findViewById(R.id.autoRegNo);
        text.setThreshold(2);
        text.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Getdata.RegNo));

        RegNoSelect regNo = new RegNoSelect();
        text.setOnItemClickListener(regNo);
        text.setOnItemSelectedListener(regNo);
        text.setSelection(5);
        sendButton = (Button) findViewById(R.id.button);
        remarks = (EditText) findViewById(R.id.remarks);
        crew = (CheckBox) findViewById(R.id.crewCheck);
        eqipment = (CheckBox) findViewById(R.id.equiCheck);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch  (v.getId())
        {
            case R.id.button:
                int crewValue = 0;
                int equipmentValue = 0;
                String imul = text.getText().toString();
                if(crew.isChecked()) {
                    crewValue = 1;
                }
                if (eqipment.isChecked()) {
                    equipmentValue = 1;
                }
                String remarksValue = remarks.getText().toString();
                SendDepartureData sendDepartureData = new SendDepartureData();

                try{
                    String safeUrl = "http://192.248.22.121/GPS_mobile/Nishan/SendDepartureData.php?q="+ URLEncoder.encode(imul)+"&voyageNo="+URLEncoder.encode(voyageNo)+"&crew="+URLEncoder.encode(String.valueOf(crewValue))+"&equip="+URLEncoder.encode(String.valueOf(equipmentValue))+"&remarks="+URLEncoder.encode(remarksValue)+"";
                    sendDepartureData.execute(safeUrl);
                    Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_LONG).show();
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
                //System.out.println("PHP array" + s);
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
            //System.out.print("Boat = "+ array[3]);
            boatName.setText(array[3]);
            voyageNo = array[2];

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
