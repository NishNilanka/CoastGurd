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

import java.util.concurrent.ExecutionException;

/**
 * Created by Nishan Gunawardena on 11/7/2015.
 */
public class Departure extends Activity implements View.OnClickListener{

    AutoCompleteTextView text;
    TextView harbour;
    TextView date;
    CheckBox crew, eqipment;
    Button sendButton;
    EditText remarks;
    String regNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departure);
        Getdata gd = new Getdata();
        gd.execute("http://192.248.22.121/GPS_mobile/Nishan/getlocal_reg_no.php");
        text = (AutoCompleteTextView) findViewById(R.id.autoRegNo);
        text.setThreshold(2);
        text.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Getdata.RegNo));

        RegNoSelect regNo = new RegNoSelect();
        text.setOnItemClickListener(regNo);
        text.setOnItemSelectedListener(regNo);

        sendButton = (Button) findViewById(R.id.button);
        remarks = (EditText) findViewById(R.id.editText2);

        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch  (v.getId())
        {
            case R.id.button:
                boolean crewValue =  crew.isChecked();
                boolean equipmentValue = eqipment.isChecked();
                String remarksValue = remarks.getText().toString();
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
            array = s.split(" ");
            harbour.setText("");
            date.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
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
            harbour.setText(array[1]);
            date.setText(array[0]);
            //System.out.println("1423333 " + array[1]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
