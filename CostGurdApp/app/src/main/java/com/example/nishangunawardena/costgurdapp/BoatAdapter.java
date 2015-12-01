package com.example.nishangunawardena.costgurdapp;

/**
 * Created by Nishan Gunawardena on 11/30/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.ExecutionException;

public class BoatAdapter {

    public static final String KEY_IMUL = "local_reg_no";
    public static final String VOYAGE_NO = "voyage_no";
    public static final String DEPARTURE_DATE = "departure_date";
    public static final String DEPARTURE_TIME = "departure_time";
    public static final String DEPARTURE_PORT = "departure_port";
    public static final String ARRIVAL_DATE = "arrival_date";
    public static final String ARRIVAL_TIME = "arrival_time";
    public static final String ARRIVAL_PORT = "arrival_port";
    public static final String VOYAGE_STATUS = "voyage_status";
    public static final String NAME = "name";

    private static final String TAG = "BoatAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "DB";
    private static final String SQLITE_TABLE = "Boat";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_IMUL + " integer PRIMARY KEY," +
                    VOYAGE_NO + " ," +
                    DEPARTURE_DATE + " ," +
                    DEPARTURE_TIME + " ," +
                    DEPARTURE_PORT + " ," +
                    ARRIVAL_DATE + " VARCHAR(50)," +
                    ARRIVAL_TIME + " VARCHAR(50)," +
                    ARRIVAL_PORT + " VARCHAR(50)," +
                    VOYAGE_STATUS + " ," +
                    NAME + " ," +
                    " UNIQUE (" + VOYAGE_NO +"));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public BoatAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public BoatAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createBoat(String imul, String voyageNO, String DepartDate,String DepartTime,
                              String DepartPort, String ArrivalDate, String ArrivalTime,String ArrivalPort, String status, String name) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IMUL, imul);
        initialValues.put(VOYAGE_NO, voyageNO);
        initialValues.put(DEPARTURE_DATE, DepartDate);
        initialValues.put(DEPARTURE_TIME , DepartTime);
        initialValues.put(DEPARTURE_PORT , DepartPort);
        initialValues.put(ARRIVAL_DATE , ArrivalDate);
        initialValues.put(ARRIVAL_TIME , ArrivalTime);
        initialValues.put(ARRIVAL_PORT , ArrivalPort);
        initialValues.put(VOYAGE_STATUS , status);
        initialValues.put(NAME,name);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllCountries() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_IMUL,
                            VOYAGE_NO, DEPARTURE_DATE, DEPARTURE_TIME , DEPARTURE_PORT , ARRIVAL_DATE , ARRIVAL_TIME  , ARRIVAL_PORT, VOYAGE_STATUS, NAME},
                    null, null, null, null, null);

        }
        else {
            /*mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_IMUL,
                            VOYAGE_NO, DEPARTURE_DATE, KEY_CONTINENT, KEY_REGION},
                    KEY_IMUL + " like '%" + inputText + "%'", null,
                    null, null, null, null);*/
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_IMUL, VOYAGE_NO, DEPARTURE_DATE, DEPARTURE_TIME , DEPARTURE_PORT , ARRIVAL_DATE , ARRIVAL_TIME  , ARRIVAL_PORT, VOYAGE_STATUS, NAME},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {

        getBoatDetails boat = new getBoatDetails();
        String s = null;
        try {
            s = boat.execute("http://192.248.22.121/GPS_mobile/Nishan/getAllBoats.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[] array = s.split("/");
        for (String a: array)
        {
            String[] data = a.split("@");
            System.out.println(data[5]);
            //for (String b: data) {
            if(data[5] == null)
            {
                System.out.println(data[0]);
            }
                //createBoat(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],"");
                //System.out.println(data[0]);
            //}
        }
        /*createCountry("AFG","Afghanistan","Asia","Southern and Central Asia");
        createCountry("ALB","Albania","Europe","Southern Europe");
        createCountry("DZA","Algeria","Africa","Northern Africa");
        createCountry("ASM","American Samoa","Oceania","Polynesia");
        createCountry("AND","Andorra","Europe","Southern Europe");
        createCountry("AGO","Angola","Africa","Central Africa");
        createCountry("AIA","Anguilla","North America","Caribbean");*/

    }
}
