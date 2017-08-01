package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Rani on 3/8/2016.
 */
public class DimensionsLayout extends LinearLayout {

    String TAG = "DimensionsLayout";
    Keys keys = Keys.getInstance();
    //Different from section; this is about what image is on dimensions, not the actual section data.
    String shape;
    String section;
    Context context;
    AttributeProvider dimensionsActivity;
    ClipboardView clipboard;
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    ImageView label, detail;


    public DimensionsLayout(Context context) {
        super(context);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        init(null, 0);
    }

    public DimensionsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        dimensionsActivity = (AttributeProvider) context;
        init(attrs, 0);
    }

    public DimensionsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        li.inflate(R.layout.dimensions_layout, this, true);
        detail = (ImageButton) findViewById(R.id.image_detail);
        //TODO figure out where these activities are getting generated from so I can make use of these views.
        section = dimensionsActivity.getAttributeFromActivity();
        keys.setDetailFromShape(detail, dimensionsActivity.getShape());
        /*
        switch (shape) {
            case "i":
                //i
                //sections_table = (TableLayout) findViewById(R.id.sections_table);
                detail.setImageResource(R.drawable.image_i_detail);
                break;
            case "l":
                //l
                detail.setImageResource(R.drawable.image_l_detail);
                break;
            case "tube":
                //tube
                detail.setImageResource(R.drawable.image_tube_detail);
                break;
            case "c":
                //c
                detail.setImageResource(R.drawable.image_c_detail);
                break;
            case "t":
                //t
                detail.setImageResource(R.drawable.image_t_detail);
                break;
            case "pipe":
                //pipe subsection in tube
                detail.setImageResource(R.drawable.image_pipe_detail);
                break;
            case "2L":
                //two l subsection in t
                detail.setImageResource(R.drawable.image_two_l_detail);
                break;
            default:
                break;
        }
        */
    }

}
