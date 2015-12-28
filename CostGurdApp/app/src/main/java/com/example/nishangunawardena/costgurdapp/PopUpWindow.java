package com.example.nishangunawardena.costgurdapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class PopUpWindow extends Activity {
    private PopupWindow pwindo;
    Button btnClosePopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);
        Bundle extras = getIntent().getExtras();
        TextView txtRegNo = (TextView) findViewById(R.id.txtRegNumber);
        TextView boatName = (TextView) findViewById(R.id.txtBoatName);
        TextView highSeaLicense = (TextView) findViewById(R.id.txtHighSeasLicense);
        TextView boatLength = (TextView) findViewById(R.id.txtBoatLength);
        TextView grossTonnage = (TextView) findViewById(R.id.txtGrossTonnage);
        TextView callSign = (TextView) findViewById(R.id.txtCallSign);
        TextView ownerName = (TextView) findViewById(R.id.txtOwnerName);
        TextView mobileNumber = (TextView) findViewById(R.id.txtcontactMobile);
        TextView homeContact = (TextView) findViewById(R.id.txtContactHome);
        if (extras != null) {
           String value  = extras.getString("boatlist");
            System.out.print(value);

            try {
            JSONArray jArray = new JSONArray(value);

                JSONObject json = jArray.getJSONObject(0);

                txtRegNo.setText(json.getString("local_reg_no"));
                boatName.setText(json.getString("name"));
                highSeaLicense.setText(json.getString("high_seas_license_no"));
                boatLength.setText(json.getString("boat_length")+ " m");
                grossTonnage.setText(json.getString("gross_tonnage") + " T");
                callSign.setText(json.getString("call_sign"));
                String ownerNameText = json.getString("first_name") + " " + json.getString("last_name");
                ownerName.setText(ownerNameText);
                String mobile = json.getString("contact_mobile");
                if(mobile.equals("null"))
                    mobileNumber.setText("-");
                else
                    mobileNumber.setText(mobile);
                String landPhone = json.getString("contact_home");
                if(landPhone.equals("null"))
                    homeContact.setText("-");
                else
                    homeContact.setText(landPhone);

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error Parsing Data " + e.toString());
            }

        }


        //initiatePopupWindow();
        btnClosePopup = (Button) findViewById(R.id.btnclose);
        btnClosePopup.setOnClickListener(cancel_button_click_listener);


    }

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            //LayoutInflater inflater = (LayoutInflater) PopUpWindow.this
                    //.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View layout = inflater.inflate(R.layout.activity_pop_up_window,
                    //(ViewGroup) findViewById(R.id.popup_element));
            //pwindo = new PopupWindow(layout, 300, 370, true);
            //pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            finish();

        }
    };
}
