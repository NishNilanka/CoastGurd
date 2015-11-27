package com.example.nishangunawardena.costgurdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        ImageView departureImage = (ImageView) findViewById(R.id.departure);
        ImageView arrivalImage = (ImageView) findViewById(R.id.arrival);
        ImageView livemap = (ImageView) findViewById(R.id.liveMap);
        departureImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.departure: {
                        /*ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();*/
                        Intent intent;
                        intent = new Intent(v.getContext(), Departure.class);
                        startActivity(intent);
                        /*view.getDrawable().clearColorFilter();
                        view.invalidate();*/
                        break;
                    }


                }
            }
        });

        arrivalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.arrival: {
                        /*ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();*/
                        Intent intent;
                        intent = new Intent(v.getContext(), Arrival.class);
                        startActivity(intent);
                        /*view.getDrawable().clearColorFilter();
                        view.invalidate();*/
                        break;
                    }
                }
            }
        });

        livemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.liveMap: {
                        Intent intent;
                        intent = getPackageManager().getLaunchIntentForPackage("com.example.dula.test1");
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }



}
