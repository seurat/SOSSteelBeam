package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Rani on 3/8/2016.
 */
public class ClipboardView extends LinearLayout {

    String TAG = "ClipboardView";
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    Keys keys = Keys.getInstance();


    DatabaseHelper dbHelper;
    DataSetObserver observer;
    ArrayAdapter<String> adapter;
    List<String>list_beams;

    Context context;
    AttributeProvider dimensionsActivity;
    ImageView var1_iv, var2_iv;
    String var1, var2;
    String section;
    EditText d1, d2;
    ListView list_beams_lv;

    public ClipboardView(Context context) {
        super(context);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        init(null, 0);

    }

    public ClipboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        init(attrs, 0);

    }

    public ClipboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        init(attrs, defStyleAttr);

    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.clipboard_view, this, true);

        //Variable imageViews for dimensions
        var1_iv = (ImageView) findViewById(R.id.var1);
        var2_iv = (ImageView) findViewById(R.id.var2);

        section = dimensionsActivity.getAttributeFromActivity();
        //if button pressed was hss square, then view should interpret that as another instance of hssr.
        //messy but necessary to accomodate 'artificial' sections (called shapes in the table) that aren't among table properties.

        Log.e(TAG, "Shape is " + section);

        String shape = keys.shapeInterpreter(section);
        //TODO figure out where these activities are getting generated from so I can make use of these views.
        switch (shape) {
            case "i":
                //i
                //sections_table = (TableLayout) findViewById(R.id.sections_table);
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                //Note: swap this with bf_dim when you get it.
                var2_iv.setImageResource(R.drawable.text_bf_dim);
                var2 = "bf";
                break;
            case "tube":
                var1 = "ht";
                var1_iv.setImageResource(R.drawable.text_ht_dim);
                //insert ht dimension when you get the chance.
                var2_iv.setImageResource(R.drawable.text_b_dim);
                var2 = "b";
                break;
            case "pipe":
                //pipe subsection in tube
                var1_iv.setImageResource(R.drawable.text_od_dim);
                var1 = "od";
                //change image to tnom
                var2_iv.setImageResource(R.drawable.text_tnom_dim);
                var2 = "tnom";
                break;
            case "c":
                //c
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_bf_dim);
                var2 = "bf";
                break;
            case "t":
                //t
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_bf_dim);
                var2 = "bf";
                break;
            case "l":
                //l
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_b_one_dim);
                var2 = "b_1";
                break;
            case "2l":
                //two l subsection in t
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_b_one_dim);
                var2 = "b_1";
                break;
            default:
                break;
        }

        //EditTexts for dimensions
        d1 = (EditText) findViewById(R.id.d1);
        d2 = (EditText) findViewById(R.id.d2);
        //List of labels for beams
        list_beams_lv = (ListView) findViewById(R.id.list_beams_lv);

        dbHelper = new DatabaseHelper(context.getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list_beams = dbHelper.getAllItems(section);
        if (list_beams != null) {
            adapter = new ArrayAdapter<String>(context.getApplicationContext(),
                    R.layout.section_label, R.id.section_label, list_beams);
            list_beams_lv.setAdapter(adapter);
        }

        //Instead, use more general key listener function instead of individual ones.
        d1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)
                            context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.isFullscreenMode();
                    imm.hideSoftInputFromWindow(d1.getWindowToken(), 0);
                    boolean flag1 = d1.getText().length() == 0;
                    boolean flag2 = d2.getText().length() == 0;
                    //if both text fields are empty
                    if (flag1 && flag2) list_beams = dbHelper.getAllItems(section);
                    //if the first edit text is empty
                    else if (flag1)
                            list_beams = dbHelper.getThoseItemsBySecondArg
                            (keys.specifySectionString(section) +
                                    keys.specifyDimRangeString(var2, d2.getText().toString()));
                    //if the second edit text is empty
                    else if (flag2)
                            list_beams = dbHelper.getThoseItemsByFirstArg
                            (keys.specifySectionString(section) +
                                    keys.specifyDimRangeString(var1, d1.getText().toString()));
                    //if both are full
                    else {
                        list_beams = dbHelper.getThoseItems
                                (keys.specifySectionString(section)
                                        + keys.queryByBothString(var1,
                                                                d1.getText().toString(),
                                                                var2,
                                                                d2.getText().toString()));
                    }
                    adapter.clear();
                    adapter.addAll(list_beams);
                    adapter.notifyDataSetChanged();
                    return true;

                }

                return false;
            }
        });

        d2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)
                            context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(d2.getWindowToken(), 0);
                    imm.isFullscreenMode();
                    boolean flag1 = d1.getText().length() == 0;
                    boolean flag2 = d2.getText().length() == 0;
                    if (flag1 && flag2) list_beams = dbHelper.getAllItems(section);
                    else if (flag1 || flag2) {
                        //What about redundancies? Also Sections
                        list_beams = dbHelper.getThoseItemsBySecondArg(keys.specifySectionString(section)
                                + keys.specifyDimRangeString(var2, d2.getText().toString()));
                        list_beams.addAll(dbHelper.getThoseItemsByFirstArg(keys.specifySectionString(section)
                                + keys.specifyDimRangeString(var1, d1.getText().toString())));
                    } else {
                        list_beams = dbHelper.getThoseItems
                                (keys.specifySectionString(section)
                                        + keys.queryByBothString(var1,
                                                                d1.getText().toString(),
                                                                var2,
                                                                d2.getText().toString()));
                    }
                    adapter.clear();
                    adapter.addAll(list_beams);
                    adapter.notifyDataSetChanged();
                    return true;

                }

                return false;
            }
        });

        list_beams_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) view;
                String beam_label = item.getText().toString();
                Intent generateProperties = new Intent(context.getApplicationContext(), PropertiesActivity.class);
                generateProperties.putExtra(keys.intent_dimensions, beam_label);
                if(section.equalsIgnoreCase(keys.hss_sqr_section))
                    generateProperties.putExtra(keys.intent_dimensions_sqr_flag, true);
                else generateProperties.putExtra(keys.intent_dimensions_sqr_flag, false);
                context.startActivity(generateProperties);
            }
        });


    }
//TODO utilize overriding onBackPressed() to control what clicking back in keyboard does



}
