package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rani on 3/8/2016.
 */
public class PropertiesLayout extends LinearLayout {
    String TAG = "PropertiesLayout";
    Keys keys = Keys.getInstance();
    DatabaseHelper dbHelper;
    ListView list_properties_lv;
    ArrayAdapter<String> adapter;
    List<String> list_properties;
    String section, shape;
    String beam_label;
    String properties;
    Boolean isHSSSqr;
    Context context;
    AttributeProvider propertiesActivity;
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    ImageView detail;

    public PropertiesLayout(Context context) {
        super(context);
        this.context = context;
        if(context instanceof  AttributeProvider)
            propertiesActivity = (AttributeProvider) context;
        init(null, 0);
    }

    public PropertiesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if(context instanceof  AttributeProvider)
            propertiesActivity = (AttributeProvider) context;
        init(attrs, 0);
    }

    public PropertiesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if(context instanceof  AttributeProvider)
            propertiesActivity = (AttributeProvider) context;
        init(attrs, defStyleAttr);

    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.properties_layout, this, true);
        detail = (ImageView) findViewById(R.id.image_detail);
        list_properties_lv = (ListView) findViewById(R.id.list_properties);



        dbHelper = new DatabaseHelper(context.getApplicationContext(), propertiesActivity.getDatabaseName()+".sqlite");
        properties = "W, A, d, b t, kdes, kdet, x, y, xp, yp, Ix, "+
                "Zx, Sx, rx, Iy, Zy, Iz, Sy, ry, Iz, rz, Sz, J, Cw, ro, H, "+
                "Qs, Iw, zA, zC, wA, wB, wC, SwA, SwC, SzA, SzB, SzC";

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //maybe construct record; use OOD to fix this project.
        beam_label = propertiesActivity.getAttributeFromActivity();
        isHSSSqr = propertiesActivity.isHSSSqr();
        //returns section (called Shape in the SQL table) that beam label is specifying.
        shape = propertiesActivity.getShape();
        Log.e(TAG, "Where is it " + shape);
        keys.setDetailFromShape(detail, shape);
        //Get name of section from string. Prepare to possibly range over section within two inches.
        if (beam_label != null) {
            list_properties = dbHelper.getThoseItemProperties(properties, beam_label);
            Iterator<String> users = list_properties.iterator();
            adapter = new ArrayAdapter<String>(context,
                    R.layout.property_label, R.id.property_label, list_properties);
            list_properties_lv.setAdapter(adapter);
            //Then post starter set of properties. d, bf, tf, tw, k
            //adapter.clear();
        }


        //TODO figure out where these activities are getting generated from so I can make use of these views.
    }

}