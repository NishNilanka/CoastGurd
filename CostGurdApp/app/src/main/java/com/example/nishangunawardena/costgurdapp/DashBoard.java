package com.example.nishangunawardena.costgurdapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class DashBoard extends Activity {

    TextView boatDetailsType;
    ListView listView;
    static int clickedButton= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        final getBoatDetails boat = new getBoatDetails();
        String s = null;

        boatDetailsType = (TextView) findViewById(R.id.boatText);
        listView = (ListView) findViewById(R.id.listView);
        boatDetailsType.setText("All Boats");
        try {

            s = boat.execute("http://192.248.22.121/GPS_mobile/Nishan/getAllBoats.php").get();
            JSONArray jArray = new JSONArray(s);
            System.out.println(s);
            int count = jArray.length();
            String[] mobileArray = new String[count];
            for(int i=0; i<count;i++) {
                JSONObject json = jArray.getJSONObject(i);
                //System.out.println(json.getString("local_reg_no"));
                mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.boats, mobileArray);


            listView.setAdapter(adapter);

            //System.out.print(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView departureImage = (ImageView) findViewById(R.id.btnDeparture);
        ImageView arrivalImage = (ImageView) findViewById(R.id.btnArrival);
        ImageView livemap = (ImageView) findViewById(R.id.btnMap);


        final ImageView Leaving = (ImageView) findViewById(R.id.btnLeving);
        final ImageView allBoats = (ImageView) findViewById(R.id.btnAllBoats);
        final ImageView arriving = (ImageView) findViewById(R.id.btnArriving);
        final ImageView arrivedBoat = (ImageView) findViewById(R.id.btnArrived);
        final ImageView QCApprovedBoat = (ImageView) findViewById(R.id.btnQCApproved);
        final ImageView FIApproved = (ImageView) findViewById(R.id.btnFIapproaval);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (isConnectingToInternet()) {
                    Object o = listView.getItemAtPosition(position);
                    //As you are using Default String Adapter

                    String IMULA = o.toString().split("-")[0];
                    boatDetails bt = new boatDetails();
                    try {
                        String boat = bt.execute("http://192.248.22.121/GPS_mobile/Nishan/getBoat.php?IMULA=" + URLEncoder.encode(IMULA, "UTF-8")).get();
                        Intent popup = new Intent(DashBoard.this, PopUpWindow.class);
                        //Toast.makeText(getBaseContext(),boat,Toast.LENGTH_SHORT).show();
                        popup.putExtra("boatlist", boat);
                        startActivity(popup);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    return false;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                    builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }
            }
        });




        departureImage.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(isConnectingToInternet()) {
                    Intent intent;
                    intent = new Intent(v.getContext(), Departure.class);
                    startActivity(intent);
                    return false;
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                    builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }

            }
        });

        arrivalImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isConnectingToInternet()) {
                    Intent intent;
                    intent = new Intent(v.getContext(), Arrival.class);
                    startActivity(intent);
                    return false;
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                    builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }

            }
        });

        livemap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isConnectingToInternet()) {
                    Intent intent;
                    intent = getPackageManager().getLaunchIntentForPackage("com.example.dula.test1");
                    startActivity(intent);
                    return false;
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                    builder.setMessage("You are not connected to the internet!\nඔබ අන්තර්ජාලයට සම්බන්ද නැත!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }

            }
        });

        Leaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnectingToInternet()) {
                    String s = null;
                    getBoats deportBoats = new getBoats();
                    if (clickedButton == 0)
                        allBoats.setImageResource(R.drawable.allboats);
                    else if (clickedButton == 2)
                        arriving.setImageResource(R.drawable.arrivingboats);
                    else if (clickedButton == 3)
                        arrivedBoat.setImageResource(R.drawable.arrivedboats);
                    else if (clickedButton == 4)
                        FIApproved.setImageResource(R.drawable.fiapprovedboats);
                    else if (clickedButton == 5)
                        QCApprovedBoat.setImageResource(R.drawable.qcapprovedboats);
                    clickedButton = 1;
                    Leaving.setImageResource(R.drawable.leavingoatsp);
                    try {

                        s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getToBeDeportedBoats.php").get();
                        JSONArray jArray = new JSONArray(s);
                        int count = jArray.length();
                        String[] mobileArray = new String[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                        boatDetailsType.setText("Leaving Boats");
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        //System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
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
        });

        allBoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    String s = null;
                    getBoats deportBoats = new getBoats();
                    allBoats.setImageResource(R.drawable.allboatsp);
                    if (clickedButton == 1)
                        Leaving.setImageResource(R.drawable.leavingboats);
                    else if (clickedButton == 2)
                        arriving.setImageResource(R.drawable.arrivingboats);
                    else if (clickedButton == 3)
                        arrivedBoat.setImageResource(R.drawable.arrivedboats);
                    else if (clickedButton == 4)
                        FIApproved.setImageResource(R.drawable.fiapprovedboats);
                    else if (clickedButton == 5)
                        QCApprovedBoat.setImageResource(R.drawable.qcapprovedboats);
                    clickedButton = 0;
                    try {

                        s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getAllBoats.php").get();
                        JSONArray jArray = new JSONArray(s);
                        int count = jArray.length();
                        String[] mobileArray = new String[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                        boatDetailsType.setText("All Boats");
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        //System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
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
        });

        arriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    String s = null;
                    getBoats deportBoats = new getBoats();
                    if (clickedButton == 0)
                        allBoats.setImageResource(R.drawable.allboats);
                    else if (clickedButton == 1)
                        Leaving.setImageResource(R.drawable.leavingboats);
                    else if (clickedButton == 3)
                        arrivedBoat.setImageResource(R.drawable.arrivedboats);
                    else if (clickedButton == 4)
                        FIApproved.setImageResource(R.drawable.fiapprovedboats);
                    else if (clickedButton == 5)
                        QCApprovedBoat.setImageResource(R.drawable.qcapprovedboats);
                    arriving.setImageResource(R.drawable.arrivingboatsp);
                    clickedButton = 2;
                    try {

                        s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getReportingRangeBoats.php").get();
                        JSONArray jArray = new JSONArray(s);
                        int count = jArray.length();
                        String[] mobileArray = new String[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                        boatDetailsType.setText("Arriving Boats");
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        //System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
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
        });

        arrivedBoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    String s = null;
                    getBoats deportBoats = new getBoats();
                    if (clickedButton == 0)
                        allBoats.setImageResource(R.drawable.allboats);
                    else if (clickedButton == 1)
                        Leaving.setImageResource(R.drawable.leavingboats);
                    else if (clickedButton == 2)
                        arriving.setImageResource(R.drawable.arrivingboats);
                    else if (clickedButton == 4)
                        FIApproved.setImageResource(R.drawable.fiapprovedboats);
                    else if (clickedButton == 5)
                        QCApprovedBoat.setImageResource(R.drawable.qcapprovedboats);
                    arrivedBoat.setImageResource(R.drawable.arrivedboatsp);
                    clickedButton = 3;
                    try {

                        s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getArrivedBoats.php").get();
                        JSONArray jArray = new JSONArray(s);
                        int count = jArray.length();
                        String[] mobileArray = new String[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                        boatDetailsType.setText("Arrived Boats");
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        //System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
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
        });


        QCApprovedBoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    String s = null;
                    getBoats deportBoats = new getBoats();
                    if (clickedButton == 0)
                        allBoats.setImageResource(R.drawable.allboats);
                    else if (clickedButton == 1)
                        Leaving.setImageResource(R.drawable.leavingboats);
                    else if (clickedButton == 3)
                        arrivedBoat.setImageResource(R.drawable.arrivedboats);
                    else if (clickedButton == 4)
                        FIApproved.setImageResource(R.drawable.fiapprovedboats);
                    else if (clickedButton == 2)
                        arriving.setImageResource(R.drawable.arrivingboats);
                    QCApprovedBoat.setImageResource(R.drawable.qcapprovedboatsp);
                    clickedButton = 5;
                    try {

                        s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getQCApprovedBoats.php").get();
                        JSONArray jArray = new JSONArray(s);
                        int count = jArray.length();
                        String[] mobileArray = new String[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                        boatDetailsType.setText("QC Approved Boats -  Arrival");
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        //System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
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
        });

        FIApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    String s = null;
                    getBoats deportBoats = new getBoats();
                    if (clickedButton == 0)
                        allBoats.setImageResource(R.drawable.allboats);
                    else if (clickedButton == 1)
                        Leaving.setImageResource(R.drawable.leavingboats);
                    else if (clickedButton == 3)
                        arrivedBoat.setImageResource(R.drawable.arrivedboats);
                    else if (clickedButton == 2)
                        arriving.setImageResource(R.drawable.arrivingboats);
                    else if (clickedButton == 5)
                        QCApprovedBoat.setImageResource(R.drawable.qcapprovedboats);
                    FIApproved.setImageResource(R.drawable.fiapprovedboatsp);
                    clickedButton = 4;
                    try {

                        s = deportBoats.execute("http://192.248.22.121/GPS_mobile/Nishan/getFIApprovedBoats.php").get();
                        JSONArray jArray = new JSONArray(s);
                        int count = jArray.length();
                        String[] mobileArray = new String[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            mobileArray[i] = (json.getString("local_reg_no") + " - " + json.getString("name"));

                        }

                        ArrayAdapter adapter = new ArrayAdapter<String>(DashBoard.this, R.layout.boats, mobileArray);
                        boatDetailsType.setText("FI Approved Boats -  Arrival");
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        //System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
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
        });
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashBoard.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
