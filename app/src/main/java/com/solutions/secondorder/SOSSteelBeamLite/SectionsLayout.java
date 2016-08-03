package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by Rani on 2/23/2016.
 */
//Developer's hint: in defining attributes, always define the attribute above the declare-styleable object, not inside it
    //then, put the <attr name="attributeName /> tag inside the declare-styleable corresponding to your class.
    //Implementation Note: changing the image in a custom view depending on an attribute is a lot more involved than it seems.
        //Simply using setImageResource is not enough.
        //http://stackoverflow.com/questions/4393976/android-imageview-nullpointerexception
public class SectionsLayout extends LinearLayout implements View.OnClickListener{
    //int section;
    String TAG = "SectionsLayout";
    String shape = "";
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    ImageView label, detail;
    ImageButton sec1_button, sec2_button, sec3_button, sec4_button;
    TableLayout sections_table;
    TableRow row1, row2;

    //TODO: Add more classes to use the same styleable.
    Context context;
    AttributeProvider sectionsActivity;
    View v;
    public SectionsLayout(Context context) {
        super(context);
        this.context = context;
        sectionsActivity = (AttributeProvider) context;
        init(null, 0);

    }

    public SectionsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        sectionsActivity = (AttributeProvider) context;
        init(attrs, 0);

    }

    public SectionsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        sectionsActivity = (AttributeProvider) context;
        init(attrs, defStyle);

    }

    //TODO Figure out way of checking if current context is an Activity.
    //http://stackoverflow.com/questions/9891360/getting-activity-from-context-in-android for any issues. You were warned.

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        //section = a.getInt(R.styleable.ShapesLayout_shape, 0);

        Log.e("TAG","The section is "+shape);
        //TODO figure out where these activities are getting generated from so I can make use of these views.
        shape = sectionsActivity.getAttributeFromActivity();
        Log.e(TAG,"The section is "+shape);
        switch (shape) {
            case "i":
                //i
                li.inflate(R.layout.sections_layout, this, true);
                detail = (ImageButton) findViewById(R.id.image_detail);
                label = (ImageButton) findViewById(R.id.label);
                sections_table = (TableLayout) findViewById(R.id.sections_table);
                sec1_button = (ImageButton) findViewById(R.id.sec1_button);
                sec1_button.setOnClickListener(this);
                sec2_button = (ImageButton) findViewById(R.id.sec2_button);
                sec2_button.setOnClickListener(this);
                sec3_button = (ImageButton) findViewById(R.id.sec3_button);
                sec3_button.setOnClickListener(this);
                sec4_button = (ImageButton) findViewById(R.id.sec4_button);
                sec4_button.setOnClickListener(this);
                detail.setImageResource(R.drawable.image_i_detail);
                sec1_button.setImageResource(R.drawable.button_shape_w);
                sec2_button.setImageResource(R.drawable.button_shape_m);
                sec3_button.setImageResource(R.drawable.button_shape_s);
                sec4_button.setImageResource(R.drawable.button_shape_hp);
                break;
            case "l":
                //l
                li.inflate(R.layout.sections_pair_layout, this, true);
                detail = (ImageButton) findViewById(R.id.image_detail);
                label = (ImageButton) findViewById(R.id.label);
                sections_table = (TableLayout) findViewById(R.id.sections_table);
                sec1_button = (ImageButton) findViewById(R.id.sec1_button);
                sec1_button.setOnClickListener(this);
                sec2_button = (ImageButton) findViewById(R.id.sec2_button);
                sec2_button.setOnClickListener(this);
                detail.setImageResource(R.drawable.image_l_detail);
                sec1_button.setImageResource(R.drawable.button_shape_l);
                sec2_button.setImageResource(R.drawable.button_shape_two_l);
                break;
            case "tube":
                //tube
                li.inflate(R.layout.sections_layout, this, true);
                detail = (ImageButton) findViewById(R.id.image_detail);
                label = (ImageButton) findViewById(R.id.label);
                sections_table = (TableLayout) findViewById(R.id.sections_table);
                detail.setImageResource(R.drawable.image_tube_detail);
                sec1_button = (ImageButton) findViewById(R.id.sec1_button);
                sec1_button.setOnClickListener(this);
                sec2_button = (ImageButton) findViewById(R.id.sec2_button);
                sec2_button.setOnClickListener(this);
                sec3_button = (ImageButton) findViewById(R.id.sec3_button);
                sec3_button.setOnClickListener(this);
                sec4_button = (ImageButton) findViewById(R.id.sec4_button);
                sec4_button.setOnClickListener(this);
                sec1_button.setImageResource(R.drawable.button_shape_hss_rect);
                sec2_button.setImageResource(R.drawable.button_shape_hss_sqr);
                sec3_button.setImageResource(R.drawable.button_shape_hss_round);
                sec4_button.setImageResource(R.drawable.button_shape_pipe);
                break;
            case "c":
                //c
                li.inflate(R.layout.sections_pair_layout, this, true);
                detail = (ImageButton) findViewById(R.id.image_detail);
                label = (ImageButton) findViewById(R.id.label);
                sections_table = (TableLayout) findViewById(R.id.sections_table);
                detail.setImageResource(R.drawable.image_c_detail);
                sec1_button = (ImageButton) findViewById(R.id.sec1_button);
                sec1_button.setOnClickListener(this);
                sec2_button = (ImageButton) findViewById(R.id.sec2_button);
                sec2_button.setOnClickListener(this);
                sec1_button.setImageResource(R.drawable.button_shape_c);
                sec2_button.setImageResource(R.drawable.button_shape_mc);
                break;
            case "t":
                //t
                li.inflate(R.layout.sections_layout, this, true);
                detail = (ImageButton) findViewById(R.id.image_detail);
                label = (ImageButton) findViewById(R.id.label);
                sections_table = (TableLayout) findViewById(R.id.sections_table);
                detail.setImageResource(R.drawable.image_t_detail);
                sec1_button = (ImageButton) findViewById(R.id.sec1_button);
                sec1_button.setOnClickListener(this);
                sec2_button = (ImageButton) findViewById(R.id.sec2_button);
                sec2_button.setOnClickListener(this);
                sec3_button = (ImageButton) findViewById(R.id.sec3_button);
                sec3_button.setOnClickListener(this);
                sec4_button = (ImageButton) findViewById(R.id.sec4_button);
                sec4_button.setOnClickListener(this);
                sec1_button.setImageResource(R.drawable.button_shape_wt);
                sec2_button.setImageResource(R.drawable.button_shape_mt);
                sec3_button.setImageResource(R.drawable.button_shape_st);
                sec4_button.setImageResource(R.drawable.button_shape_two_l);
                break;
            default:
                Log.e(TAG,"DIDN'T TAKE");
                break;
        }
        //TODO link settings to "ABOUT" screen. Later, allow user to select code.
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sec1_button:
                sectionsActivity.screenDispatch(R.id.sec1_button);
                break;
            case R.id.sec2_button:
                sectionsActivity.screenDispatch(R.id.sec2_button);
                break;
            case R.id.sec3_button:
                sectionsActivity.screenDispatch(R.id.sec3_button);
                break;
            case R.id.sec4_button:
                sectionsActivity.screenDispatch(R.id.sec4_button);
                break;
            default:
                break;

        }

    }


}
