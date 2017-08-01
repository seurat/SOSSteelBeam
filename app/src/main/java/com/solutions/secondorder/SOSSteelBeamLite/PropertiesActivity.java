package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.os.Bundle;

public class PropertiesActivity extends Activity implements AttributeProvider{
    String TAG = "PropertiesActivity";
    Keys keys = Keys.getInstance();
    String beam_label;
    boolean isHSSSqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beam_label = getIntent().getStringExtra(keys.intent_dimensions);
        PropertiesLayout propertiesLayout = new PropertiesLayout(this);
        PropertiesLayout.LayoutParams pl = new PropertiesLayout.LayoutParams(
                PropertiesLayout.LayoutParams.MATCH_PARENT,
                PropertiesLayout.LayoutParams.MATCH_PARENT
        );
        setContentView(propertiesLayout, pl);
    }

    public String getAttributeFromActivity() {
        return beam_label;
    }

    public boolean isHSSSqr() {
        return isHSSSqr;
    }

    public void goBack() {
        finish();
    }

    public void screenDispatch(int button){}

    public String getDatabaseName() {
        return ((SOSSteelBeamsApplication) getApplication()).getDatabaseName();
    }

    public String getShape() {
        return ((SOSSteelBeamsApplication) getApplication()).getShape();
    }

}
