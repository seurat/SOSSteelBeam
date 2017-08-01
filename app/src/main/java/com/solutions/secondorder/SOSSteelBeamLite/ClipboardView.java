package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
public class ClipboardView extends LinearLayout implements View.OnKeyListener {

    String TAG = "ClipboardView";
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    Keys keys = Keys.getInstance();
    DatabaseHelper dbHelper;
    DataSetObserver observer;
    ArrayAdapter<String> adapter;
    List<String>list_beams;

    Context context;
    AttributeProvider dimensionsActivity;
    LinearLayout var1_ll, var2_ll, var3_ll;
    ImageView var1_iv, var2_iv, var3_iv;
    String var1, var2, var3;
    String variables[];
    String section;
    String databaseName;
    EditText d1, d2, d3;
    ListView list_beams_lv;
    List<String> sections;
    boolean e1, e2, e3;

    public ClipboardView(Context context) {
        super(context);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        databaseName = dimensionsActivity.getDatabaseName();
        init(null, 0);

    }

    public ClipboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        databaseName = dimensionsActivity.getDatabaseName();
        init(attrs, 0);

    }

    public ClipboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        databaseName = dimensionsActivity.getDatabaseName();
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
        var3_iv = (ImageView) findViewById(R.id.var3);

        var3_ll = (LinearLayout) findViewById(R.id.third_dimension);

        section = dimensionsActivity.getAttributeFromActivity();
        //if button pressed was hss square, then view should interpret that as another instance of hssr.
        //messy but necessary to accomodate 'artificial' sections (called shapes in the table) that aren't among table properties.

        //The string 'section' for now refers to what is actually the Shape of the beam (with respect to what the database calls it)
        Log.e(TAG, "Shape is " + section);
        sections = keys.getSectionsFromShape(section);
        //TODO figure out where these activities are getting generated from so I can make use of these views.
        //refer to Key value for that code set for each variable. Ahaaaaa...
        //ALSO: if a textview is invisible then its string value will always be blank therefore you need to do nothing about discounting it.
        //bizarre: class members not considered globally accessible if instantiated by switch statement unless instantiated in every contingency.
        switch (section) {
            case "i":
                //i
                //sections_table = (TableLayout) findViewById(R.id.sections_table);
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                //Note: swap this with bf_dim when you get it.
                var2_iv.setImageResource(R.drawable.text_bf_dim);
                var2 = "bf";
                var3_iv.setImageResource(R.drawable.text_tf);
                var3 = "tf";
                //Order of variables has to synch with order of their corresponding boxes in the UI; or use a hashtable.
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;
                break;
            case "tube":
                var1 = "ht";
                var1_iv.setImageResource(R.drawable.text_ht_dim);
                //insert ht dimension when you get the chance.
                var2_iv.setImageResource(R.drawable.text_b_dim);
                var2 = "b";
                var3_iv.setImageResource(R.drawable.text_tnom_dim);
                var3 = "tnom";
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;
                break;
            case "pipe":
                //pipe subsection in tube
                var1_iv.setImageResource(R.drawable.text_od_dim);
                var1 = "od";
                //change image to tnom
                var2_iv.setImageResource(R.drawable.text_tnom_dim);
                var2 = "tnom";
                var3_ll.setVisibility(View.GONE);
                var3 = "no variable";
                variables = new String[2];
                variables[0] = var1;
                variables[1] = var2;
                break;
            case "c":
                //c
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_bf_dim);
                var2 = "bf";
                var3_iv.setImageResource(R.drawable.text_tf);
                var3 = "tf";
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;
                break;
            case "t":
                //t
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_bf_dim);
                var2 = "bf";
                var3_iv.setImageResource(R.drawable.text_tf);
                var3 = "tf";
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;
                break;
            case "l":
                //l
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_b_one_dim);
                var2 = "b_1";
                var3_iv.setImageResource(R.drawable.text_t);
                var3 = "t";
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;
                break;
            case "2l":
                //two l subsection in t
                var1_iv.setImageResource(R.drawable.text_d_dim);
                var1 = "d";
                var2_iv.setImageResource(R.drawable.text_b_one_dim);
                var2 = "b_1";
                var3_iv.setImageResource(R.drawable.text_t);
                var3 = "t";
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;
                break;
            default:
                Log.e(TAG, section + " does not exist.");
                var1 = "no valid shape";
                var2 = "no valid shape";
                var3 = "no valid shape";
                variables = new String[3];
                variables[0] = var1;
                variables[1] = var2;
                variables[2] = var3;

                break;
        }

        //EditTexts for dimensions
        d1 = (EditText) findViewById(R.id.d1);
        d2 = (EditText) findViewById(R.id.d2);
        d3 = (EditText) findViewById(R.id.d3);

        d1.setOnKeyListener(this);
        d2.setOnKeyListener(this);
        d3.setOnKeyListener(this);

        //List of labels for beams
        list_beams_lv = (ListView) findViewById(R.id.list_beams_lv);
        //TODO: get name of database figured out. Need to be able to select the file name that corresponds to the label the user clicked in database.
        dbHelper = new DatabaseHelper(context.getApplicationContext(), dimensionsActivity.getDatabaseName()+".sqlite");// "Historical.sqlite");
        Log.e(TAG, databaseName);
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



        list_beams_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) view;
                String beam_label = item.getText().toString();
                Intent generateProperties = new Intent(context.getApplicationContext(), PropertiesActivity.class);
                generateProperties.putExtra(keys.intent_dimensions, beam_label);
                generateProperties.putExtra(keys.intent_shapes, section);
                if(section.equalsIgnoreCase(keys.hss_sqr_section))
                    generateProperties.putExtra(keys.intent_dimensions_sqr_flag, true);
                else generateProperties.putExtra(keys.intent_dimensions_sqr_flag, false);
                context.startActivity(generateProperties);
            }
        });


    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        boolean var1IsBlank = d1.getText().toString().length()==0;
        boolean var2IsBlank = d2.getText().toString().length()==0;
        boolean var3IsBlank = d3.getText().toString().length()==0;
        Log.e(TAG, "Is clicked ");
        /*Log.e(TAG, var1 + (var1IsBlank?":is blank ":":is not blank ")+"\n"
                +var2+(var2IsBlank?":is blank":":is not blank")+"\n"+
                var3 + (var3IsBlank?":is blank ":":is not blank ")+"\n");*/
        boolean blankVariables[] = {var1IsBlank, var2IsBlank, var3IsBlank};
        String values[] = {d1.getText().toString(), d2.getText().toString(), d3.getText().toString()};
        switch(view.getId()) {
            case R.id.d1:
            case R.id.d2:
            case R.id.d3:
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(d2.getWindowToken(), 0);
                    imm.isFullscreenMode();
                    StringBuilder sqlStatementBuilder = new StringBuilder();
                    if (var1IsBlank && var2IsBlank && var3IsBlank) {
                        list_beams = dbHelper.getAllItems(section);
                        adapter.clear();
                        adapter.addAll(list_beams);
                        adapter.notifyDataSetChanged();
                        return true;
                    } else {
                        for (int i = 0; i < variables.length; i++) {
                            if (blankVariables[i]) {
                                Log.e(TAG, variables[i]+" is blank");
                                continue;
                            }
                            else {
                                Log.e(TAG, "Querying "+variables[i]);
                                sqlStatementBuilder.append(" AND ");
                                sqlStatementBuilder.append(keys.sqlDimRangeString(variables[i], values[i]));
                            }
                        }
                        Log.e(TAG, sqlStatementBuilder.toString());
                        list_beams = dbHelper.getThoseItemsByArgs(section, sqlStatementBuilder.toString());
                        Log.e("List Beams", list_beams.toString());
                        adapter.clear();
                        adapter.addAll(list_beams);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                }
                break;
            default: break;
        }
        return false;
    }
//TODO utilize overriding onBackPressed() to control what clicking back in keyboard does



}
