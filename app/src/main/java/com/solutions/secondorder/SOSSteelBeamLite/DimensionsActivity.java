package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.os.Bundle;

public class DimensionsActivity extends Activity implements AttributeProvider {
    DimensionsLayout dimensionsLayout;
    String section;
    Boolean isHSSSqr;
    Keys keys = Keys.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        section = getIntent().getExtras().getString(keys.intent_sections);
        if(section.equalsIgnoreCase(keys.hss_sqr_section))
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
        return section;
    }

    public void goBack() {
        finish();
    }

    public void screenDispatch(int button) {}

    public boolean isHSSSqr() {
        return isHSSSqr;
    }
}
