package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DimensionsActivity extends Activity implements AttributeProvider {
    DimensionsLayout dimensionsLayout;
    String shape;
    Boolean isHSSSqr;
    Keys keys = Keys.getInstance();
    String TAG = "DimensionsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent shapeIntent = getIntent();

        shape = shapeIntent.getStringExtra(keys.intent_shapes);
        if(shape.equalsIgnoreCase(keys.hss_sqr_section))
            isHSSSqr = true;
        else isHSSSqr = false;
        dimensionsLayout = new DimensionsLayout(this);
        DimensionsLayout.LayoutParams dlp = new DimensionsLayout.LayoutParams(
                SectionsLayout.LayoutParams.MATCH_PARENT,
                SectionsLayout.LayoutParams.MATCH_PARENT
        );
        setContentView(dimensionsLayout, dlp);

        //screenDispatch has multiple different impacts then, I guess, and it's contract is about how activity reacts to button presses.
    }

    public String getAttributeFromActivity() {
        return shape;
    }

    public void goBack() {
        finish();
    }

    public void screenDispatch(int button) {}

    public boolean isHSSSqr() {
        return isHSSSqr;
    }

    public String getDatabaseName() {
        return ((SOSSteelBeamsApplication) getApplication()).getDatabaseName();
    }

    public String getShape() {
        return ((SOSSteelBeamsApplication) getApplication()).getShape();
    }
}
