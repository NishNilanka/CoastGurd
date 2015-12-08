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
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Arrival extends AppCompatActivity implements View.OnClickListener{

    AutoCompleteTextView text;
    TextView harbour;
    TextView date, boatName;
    CheckBox complteLogBook, prohibitCheckBox, sharkCheckBox;
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
        prohibitCheckBox = (CheckBox) findViewById(R.id.checksea);
        sharkCheckBox = (CheckBox) findViewById(R.id.checkshark);
        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch  (v.getId())
        {
            case R.id.btnArribalSubmit:
                String completeLogCheck = null;
                String prohibitCheck = null;
                String sharkCheck = null;
                String imulArrival = text.getText().toString();
                if(complteLogBook.isChecked()) {
                    completeLogCheck = "Yes";
                }
                else if(!complteLogBook.isChecked())
                {
                    completeLogCheck = "No";
                }
                if (prohibitCheckBox.isChecked()) {
                    prohibitCheck = "Yes";
                }
                else if (!prohibitCheckBox.isChecked()) {
                    prohibitCheck = "NO";
                }
                if(sharkCheckBox.isChecked()) {
                    sharkCheck = "yes";
                }
                else if(!sharkCheckBox.isChecked()) {
                    sharkCheck = "No";
                }

                String remarksValue = remarks.getText().toString();
                SendArrivalData sendArrivalData = new SendArrivalData();


                try{
                    String safeUrl = "http://192.248.22.121/GPS_mobile/Nishan/SendArrivalData.php?" +
                            "q="+ URLEncoder.encode(imulArrival)+"&voyageNo="+URLEncoder.encode(voyageNo)+
                            "&log_sheets="+URLEncoder.encode(completeLogCheck)+
                            "&prohibited_species="+URLEncoder.encode(prohibitCheck)+
                            "&whole_shark="+URLEncoder.encode(sharkCheck)+
                            "&remarks="+URLEncoder.encode(remarksValue)+"";


                    String result = sendArrivalData.execute(safeUrl).get();
                    if(result.length() > 0)
                        Toast.makeText(getApplicationContext(), result , Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Connection Error" , Toast.LENGTH_LONG).show();
                    getArrivalIMUL.ArrivalRegNo.clear();
                    finish();

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                //System.out.print(s);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        getArrivalIMUL.ArrivalRegNo.clear();
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
