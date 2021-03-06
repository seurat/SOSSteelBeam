package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class DatabaseActivity extends Activity implements AttributeProvider{
    String TAG = "DatabaseActivity";
    Keys keys = Keys.getInstance();
    boolean isHSSSqr;
    ListView list_dbs_lv;
    ArrayAdapter<String> adapter;


    List<String> listDatabases;
    String[] arrayDatabases = {"AISC14.1 (2013)","AISC13 (2005)","LRFD3 (2001)", "LRFD2 (1994)", "ASD9 (1989)",
            "LRFD1 (1986)", "ASD8 (1980)",
            "ASD7 (1970)","ASD6 (1964)", "ASD5 (1962)","Historical (1873-1952)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codes_layout);
        listDatabases =Arrays.asList(arrayDatabases);
        list_dbs_lv = (ListView) findViewById(R.id.list_dbs_lv);
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.property_label, R.id.property_label, keys.arrayDatabases);

        list_dbs_lv.setAdapter(adapter);

        list_dbs_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) view;
                String databaseName = item.getText().toString();
                Intent getShapesFromDatabase = new Intent(getApplicationContext(), ShapesActivity.class);
                getShapesFromDatabase.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getShapesFromDatabase.putExtra(keys.intent_databases, databaseName);
                SOSSteelBeamsApplication appState = (SOSSteelBeamsApplication) getApplication();
                appState.setDatabaseName(databaseName);
                DatabaseActivity.this.startActivity(getShapesFromDatabase);
            }
        });

    }
    @Override
    public String getAttributeFromActivity() {
        return null;
    }

    @Override
    public void screenDispatch(int button) {

    }

    @Override
    public boolean isHSSSqr() {
        return false;
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public String getShape() {
        return null;
    }

}
