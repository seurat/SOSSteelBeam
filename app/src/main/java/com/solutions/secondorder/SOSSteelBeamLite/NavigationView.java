package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Rani on 2/23/2016.
 */
//Developer's hint: in defining attributes, always define the attribute above the declare-styleable object, not inside it
    //then, put the <attr name="attributeName /> tag inside the declare-styleable corresponding to your class.
    //Implementation Note: changing the image in a custom view depending on an attribute is a lot more involved than it seems.
        //Simply using setImageResource is not enough.
        //http://stackoverflow.com/questions/4393976/android-imageview-nullpointerexception
public class NavigationView extends LinearLayout {
    boolean flag;
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    ImageButton back, settings;
    ImageView dots;
    Context context;
    AttributeProvider prevActivity;
    Intent prevScreen;

    public NavigationView(Context context) {
        super(context);
        this.context = context;
        if(context instanceof AttributeProvider)
            prevActivity = (AttributeProvider) context;
        else flag = true;
        init(null, 0);

    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if(context instanceof AttributeProvider)
            prevActivity = (AttributeProvider) context;
        else flag = true;
        init(attrs, 0);

    }

    public NavigationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if(context instanceof AttributeProvider)
            prevActivity = (AttributeProvider) context;
        else flag = true;
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
        li.inflate(R.layout.navigation_view, this, true);
        back = (ImageButton) findViewById(R.id.back_button);
        settings = (ImageButton) findViewById(R.id.settings_button);
        dots = (ImageView) findViewById(R.id.dots);


        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NavigationView, defStyle, 0);
        int level = a.getInt(R.styleable.NavigationView_numDots, 0);
        Log.e("TAG",""+level);
        Log.e("TAG",""+settings.getHeight());

        switch (level) {
            case 1:
                dots.setImageResource(R.drawable.dots1);
                break;
            case 2:
                dots.setImageResource(R.drawable.dots2);
                break;
            case 3:
                dots.setImageResource(R.drawable.dots3);
                break;
            case 4:
                dots.setImageResource(R.drawable.dots4);
                break;
            default:
                dots.setImageResource(R.drawable.dots0);
                break;
        }
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.finish();
                //entry1 = dim.getText().toString()
                //query =
                Log.e("TAG", "Back");
                if(!flag)
                    prevActivity.goBack();
            }
        });
        //TODO link settings to "ABOUT" screen. Later, allow user to select code.
        settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.finish();
                //entry1 = dim.getText().toString()
                //query =
                context.startActivity(new Intent(context, ContactUs.class));
                Log.e("TAG", "Settings");
            }
        });





    }


}
