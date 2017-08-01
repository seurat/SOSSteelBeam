package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.util.Arrays;
import java.util.List;

public class SectionsActivity extends Activity implements AttributeProvider {
    String TAG = "SectionsActivity";
    String shape;
    String sec1, sec2, sec3, sec4;
    ImageButton sec1_button, sec2_button, sec3_button, sec4_button;
    Keys keys = Keys.getInstance();

    public String getAttributeFromActivity() {
        Log.e(TAG, "Shape is "+shape);
        return shape;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shape = getIntent().getExtras().getString(keys.intent_shapes);
        SectionsLayout sectionsLayout = new SectionsLayout(this);
        SectionsLayout.LayoutParams slp = new SectionsLayout.LayoutParams(
                SectionsLayout.LayoutParams.MATCH_PARENT,
                SectionsLayout.LayoutParams.MATCH_PARENT
        );
        setContentView(sectionsLayout, slp);
        //setContentView(R.layout.activity_sections);

        //TODO save all intent keys in one utility class.
        //Note; errors will default to t; maybe specify error handling. Look up best practices

        sec1_button = (ImageButton) findViewById(R.id.sec1_button);
        sec2_button = (ImageButton) findViewById(R.id.sec2_button);


        List<String> shapes_four_sections = Arrays.asList("i", "tube", "t");
        if(shapes_four_sections.contains(shape)) {
            sec3_button = (ImageButton) findViewById(R.id.sec3_button);
            sec4_button = (ImageButton) findViewById(R.id.sec4_button);
        }
        switch(shape) {
            case "i":
                sec1 = "w";
                sec2 = "m";
                sec3 = "s";
                sec4 = "hp";
                break;
            case "l":
                //l
                sec1 = "l";
                sec2 = keys.two_l_section;
                break;
            case "tube":
                //tube
                sec1 = keys.hss_rect_section;
                sec2 = keys.hss_sqr_section;
                sec3 = keys.hss_round_section;
                sec4 = "pipe";
                break;
            case "c":
                //c
                sec1 = "c";
                sec2 = "mc";
                break;
            case "t":
                //t
                sec1 = "wt";
                sec2 = "mt";
                sec3 = "st";
                sec4 = keys.two_l_section;
                break;
            default:
                break;
        }
        }

    public boolean isHSSSqr() {
        return false;
    }
    public void goBack() {
        finish();
    }

    public void screenDispatch(int button) {
        Intent nextScreen = new Intent(this, DimensionsActivity.class);
        nextScreen.putExtra(keys.intent_shapes, shape);
        switch(button) {
            case R.id.sec1_button:
                Log.e(TAG, "sec 1 with section "+sec1);
                nextScreen.putExtra(keys.intent_sections, sec1);
                break;
            case R.id.sec2_button:
                Log.e(TAG, "sec 2 with section "+sec2);
                nextScreen.putExtra(keys.intent_sections, sec2);
                break;
            case R.id.sec3_button:
                Log.e(TAG, "sec 3 with section "+sec3);
                nextScreen.putExtra(keys.intent_sections, sec3);
                break;
            case R.id.sec4_button:
                Log.e(TAG, "sec 4 with section "+sec4);
                nextScreen.putExtra(keys.intent_sections, sec4);
                break;
            default:
                break;
        }
        startActivity(nextScreen);
    }

    public String getDatabaseName() {
        return ((SOSSteelBeamsApplication) getApplication()).getDatabaseName();
    }

    public String getShape() {
        return ((SOSSteelBeamsApplication) getApplication()).getShape();
    }
    //Get extra; set attribute of SectionsLayout on launch; in Sections layout,set button effects ;

}
