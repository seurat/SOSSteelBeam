package com.solutions.secondorder.SOSSteelBeamLite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


/**
 * Description of W Shapes database:
 * Tables: W Shapes
 * Columns: ([Size] Text), ([d] Float), ([tw] Float), ([tf] Float), ([bf] Float))
 */

/**
 * Created by Rani on 7/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public String TAG = "DatabaseHelper";
    public String DB_PATH = "";
    public static String DB_NAME = "AISCShapesDetail.sqlite";
    private Keys keys = Keys.getInstance();

    /**
     * Problem: database changes are not updated on phone copy of the database in asset folder.
     * Ex: Changing the Table name in the PC copy of the database will not change the Table name
     * in the phone's copy.
     *
     * Explanation: The problem is that the relevant methods to update phone's copy of database
     * are not called until one of two conditions are met: either the database with that name is
     * deleted from the phone or DB_VERSION, a number that should be incremented by the user after
     * every change one makes to the database.
     *
     * SOLUTION: increment DB_VERSION by one.
     *
     *NOTE: The version number CANNOT go down, or SQLite will inform you that you can't downgrade.
     *
     */
    public static final int DB_VERSION = 4;
    /**
     * Note: if the table or column name includes a space, surround the name with 'escaped' double quotes
     * This instructs the database service to read the string as a whole instead of stopping at a space.
     * To 'escape' a double quote, precede with a \
     */
    public static final String TB_USER = "BeamTable";

    private SQLiteDatabase myDB;

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);// 1? Its database Version
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if(myDB != null) {
            myDB.close();
        }
        super.close();
    }


    /**
     * Copy database from source code assets to device
     */
    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception e){
            Log.e("tle99 - copyDatabase", e.getMessage());
        }
    }

    /**
     * Check if the database doesn't exist on device, create new one
     * @throws IOException
     */
    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch(IOException e) {
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }


    /**
     * Check if the database exists on dvice or not
     */
    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }


    /**
     * Open database
     * @throws java.sql.SQLException
     */

    public void openDataBase() throws SQLiteException {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    //Does SQL query on d and bf columns
    //Introduce method for three properties later.
    //Note: any queries comparing or discussing columns instead of values put the value names in quotations.
    //EX: to say ht equals b: WHERE "ht"="b"


    public List<String> getThoseItems(String queryBody) {
        List<String> listItems = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.

            //TODO Collect information from multiple rows and columns in a query.
            c = db.rawQuery("SELECT Section FROM " + TB_USER + queryBody, null);
            Log.e("DBHELPER","SELECT Section FROM " + TB_USER + queryBody);
            if (c == null) {
                Log.e("tle99", "CURSOR NOT FOUND");
                return null;
            }
            String name;
            c.moveToFirst();
            do {
                name = c.getString(0);
                listItems.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());

        }

        db.close();

        return listItems;
    }
    /**
     * The next three methods are used to create queries if one of the two textboxes is left blank. Both will cover situation if other textbox is also blank.
     */
    //I'm not yelling. This is normal to me. I am passionate about my opinions,
    // and I want you to hear all of them before you get to talk again. "Why are you yelling? Stop yelling/"
    // I'm not yelling. I got another couple of octaves.
    public List<String> getThoseItemsByFirstArg(String queryBody) {
        List<String> listItems = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.
            if(queryBody=="") {
                queryBody = "";
                return Collections.<String>emptyList();
            }
            c = db.rawQuery("SELECT Section FROM " + TB_USER +" "+queryBody, null);
            Log.e(TAG, "SELECT Section FROM " + TB_USER + " "+queryBody);

            if (c == null) {
                Log.e("tle99", "CURSOR NOT FOUND");
                return null;
            }
            String name;
            c.moveToFirst();
            do {
                name = c.getString(0);
                listItems.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }

        db.close();

        return listItems;
    }
/*
    public List<String> getThoseItemsByFirstArg(String dStr) {
        List<String> listItems = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String queryBody;
        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.
            if(dStr=="") {
                queryBody = "";
                return Collections.<String>emptyList();
            }
            else {
                queryBody = " WHERE d BETWEEN " + dStr + "-0.25 AND " + dStr + "+0.25";
            }

            c = db.rawQuery("SELECT Section FROM " + TB_USER + queryBody, null);
            Log.e("DBHelper", "SELECT Section FROM " + TB_USER + queryBody);

            if (c == null) {
                Log.e("tle99", "CURSOR NOT FOUND");
                return null;
            }
            String name;
            c.moveToFirst();
            do {
                name = c.getString(0);
                listItems.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());

        }

        db.close();

        return listItems;
    }
    */
    public List<String> getThoseItemsBySecondArg(String queryBody) {
        List<String> listItems = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.
            if(queryBody=="") {
                queryBody = "";
                return Collections.<String>emptyList();
            }

            c = db.rawQuery("SELECT Section FROM " + TB_USER + " " + queryBody, null);
            Log.e(TAG, "SELECT Section FROM " + TB_USER + " "+ queryBody);
            if (c == null) {
                Log.e(TAG, "CURSOR NOT FOUND");
                return null;
            }
            String name;
            c.moveToFirst();
            do {
                name = c.getString(0);
                listItems.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }

        db.close();

        return listItems;
    }

    public List<String> getAllItems(String section) {
        List<String> listUsers = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.
            //Additionally, if I'm using the LIKE clause, and I'm describing a text entry in a column
            //That entry must be surrounded by single quotes. For instance, look up 'W', not W.

            //TODO Collect information from multiple rows and columns in a query.
            String sectionQuery = keys.specifySectionString(section);
            c = db.rawQuery("SELECT Section FROM " + TB_USER + " " + sectionQuery , null);
            Log.e(TAG, "SELECT Section FROM " + TB_USER + " " + sectionQuery);
            if (c == null) {
                Log.e(TAG, "CURSOR NOT FOUND");
                return null;
            }
            String name;
            c.moveToFirst();
            do {
                name = c.getString(0);
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        db.close();

        return listUsers;
    }

    public List<String> getSomeItemProperties(String key) {
        List<String> listProps = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.

            //TODO Collect information from multiple rows and columns in a query.
            c = db.rawQuery("SELECT d, bf, tf, tw, k1 FROM " + TB_USER + " WHERE Section LIKE "+"\'%"+key+"%\'", null);
            if (c == null) {
                Log.e("tle99", "CURSOR NOT FOUND");
                return null;
            }
            String data;
            c.moveToFirst();
            data = "d = " + c.getString(c.getColumnIndex("d"));
            listProps.add(data);
            data = "bf = " + c.getString(c.getColumnIndex("bf"));
            listProps.add(data);
            data = "tf = "+c.getString(c.getColumnIndex("tf"));
            listProps.add(data);
            data = "tw = "+c.getString(c.getColumnIndex("tw"));
            listProps.add(data);
            data = "k1 = "+c.getString(c.getColumnIndex("k1"));
            listProps.add(data);

            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());

        }

        db.close();

        return listProps;
    }


    public List<String> getThoseItemProperties(String properties, String key) {
        List<String> listProps = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //ADJUSTMENT CONSISTENT WITH DATABASE/TABLE CHANGE
            //command now targets database (DB_NAME) WShapes.sqlite, table (TB_NAME) "W Shapes"
            //Remember when specifying values described as "TEXT" in general to surround them with
            // escape quotes.
            // for example, below I specify a row by its Size. Size is Text.
            // Therefore the value I put in is \"W44X335\", not W44X335.

            //TODO Collect information from multiple rows and columns in a query.
            Cursor c = db.rawQuery("SELECT * FROM " + TB_USER + " WHERE Section LIKE "+"\'"+key+"\'", null);
            Log.e(TB_USER, "SELECT " + properties + " FROM " + TB_USER + " WHERE Section LIKE " + "\'%" + key + "%\'");
            if (c == null) {
                Log.e(TAG, "CURSOR NOT FOUND");
                return null;
            }
            String data;
            int i = 0;

            //do not use continue or if statements in cursor loops; it closes the cursor]
            // do not call "getCount" in a loop.
            //Additionally, I have no idea why, but this is the only configuration of a cursor loop
            //that does not end up closing the cursor before it finishes; c.moveToNext returns false
            //at the end of the first loop otherwise, and that includes any alternative mentioned in the
            //following Stackoverflow page
            //http://stackoverflow.com/questions/10723770/whats-the-best-way-to-iterate-an-android-cursor
            //There is a peculiar relationship between OOD and Android Activities
            while(c.moveToFirst()||c.moveToNext()) {

                String property = c.getColumnName(i);
                String value = c.getString(c.getColumnIndex(property));
                data = property + " = " + value;
                listProps.add(data);
                i+=1;

            }
            c.close();
        } catch (Exception e) {
            Log.e(TAG, "Could not get data on " + key);

        }
        ListIterator<String> li = listProps.listIterator();
        String a = "";
        //Must initialize list iterator loop by using li.next()
        for(a=li.next(); li.hasNext(); a=li.next()) {
            if(a.contains("–")||a.contains("null"))
                li.remove();
        }
        if(a.contains("–")||a.contains("null"))
            li.remove();
        db.close();
        return listProps;
    }

    //Gets shape corresponding to the section; used in PropertyLayout to select
    //proper graphical assets
    //TODO Figure out why a second broken query is being made to the cursor.
    public String getShapeByBeamLabel(String beamLabel) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String data = "";
        String section = "";
        //TODO Collect information from multiple rows and columns in a query.
        try {
            c = db.rawQuery("SELECT Shape FROM " + TB_USER + " WHERE Section LIKE " + "\'%" + beamLabel + "%\'", null);
            //Log.e("ShapeByBeamLabel", "SELECT Shape FROM " + TB_USER + " WHERE Section LIKE " + "\'%" + beamLabel + "%\'");
            if (c == null) {
                Log.e(TAG, "CURSOR NOT FOUND");
                return null;
            }
            c.moveToFirst();
            section = c.getString(c.getColumnIndex("Shape"));
            Log.e(TAG, "GOT SHAPE " + section);

            c.close();
        }
        catch(Exception e) {
            Log.e(TAG, "Could not find data");
        }
        return section;
    }

}

