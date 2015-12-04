package com.example.nishangunawardena.costgurdapp;

import android.support.v7.app.AppCompatActivity;
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

public class Arrival extends AppCompatActivity {

    AutoCompleteTextView text;
    TextView harbour;
    TextView date, boatName;
    CheckBox complteLogBook, eqipment;
    Button sendButton;
    EditText remarks;
    String regNumber;
    String voyageNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        getArrivalIMUL ga = new getArrivalIMUL();
        ga.execute("http://192.248.22.121/GPS_mobile/Nishan/getArrivalIMUL.php");

        text = (AutoCompleteTextView) findViewById(R.id.arrivalRegNo);
        text.setThreshold(1);
        text.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getArrivalIMUL.ArrivalRegNo));

        RegNoSelect regNo = new RegNoSelect();
        text.setOnItemClickListener(regNo);
        text.setOnItemSelectedListener(regNo);
        text.setSelection(5);
        sendButton = (Button) findViewById(R.id.btnArribalSubmit);
        remarks = (EditText) findViewById(R.id.arrivalRemarks);
        complteLogBook = (CheckBox) findViewById(R.id.checkCompleteLogBook);
        eqipment = (CheckBox) findViewById(R.id.equiCheck);

    }


    @Override
    public void onBackPressed() {
        Getdata.RegNo.clear();
        finish();
        return;
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
            harbour = (TextView) findViewById(R.id.arrharbourText);
            date = (TextView) findViewById(R.id.arrdepDate);
            boatName = (TextView) findViewById(R.id.arrboatTextfield);
            array = s.split("@");
            System.out.print(array);
            harbour.setText("");
            date.setText("");
            boatName.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
            boatName.setText(array[2]);
            voyageNo = array[3];

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
            harbour = (TextView) findViewById(R.id.arrharbourText);
            date = (TextView) findViewById(R.id.arrdepDate);
            boatName = (TextView) findViewById(R.id.arrboatTextfield);
            array = s.split("@");
            System.out.print(array);
            harbour.setText("");
            date.setText("");
            boatName.setText("");
            harbour.setText(array[1]);
            date.setText(array[0]);
            boatName.setText(array[2]);
            voyageNo = array[3];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
