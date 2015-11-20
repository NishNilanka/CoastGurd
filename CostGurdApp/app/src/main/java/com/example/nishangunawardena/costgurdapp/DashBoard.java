package com.example.nishangunawardena.costgurdapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        ImageView departureImage = (ImageView)findViewById(R.id.departure);
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

        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.list); // you can use (ExpandableListView) findViewById(R.id.list)

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        ExpandabaleListView adapter = new ExpandabaleListView(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        //expandableList.setOnChildClickListener( this);

    }

    public void setGroupParents() {
        parentItems.add("Arrival");
        parentItems.add("Core Java");
        parentItems.add("Desktop Java");
        parentItems.add("Enterprise Java");
    }

    public void setChildData() {

        // Android
        ArrayList<String> child = new ArrayList<String>();
        child.add("Core");
        child.add("Games");
        childItems.add(child);

        // Core Java
        child = new ArrayList<String>();
        child.add("Apache");
        child.add("Applet");
        child.add("AspectJ");
        child.add("Beans");
        child.add("Crypto");
        childItems.add(child);

        // Desktop Java
        child = new ArrayList<String>();
        child.add("Accessibility");
        child.add("AWT");
        child.add("ImageIO");
        child.add("Print");
        childItems.add(child);

        // Enterprise Java
        child = new ArrayList<String>();
        child.add("EJB3");
        child.add("GWT");
        child.add("Hibernate");
        child.add("JSP");
        childItems.add(child);
    }

}
