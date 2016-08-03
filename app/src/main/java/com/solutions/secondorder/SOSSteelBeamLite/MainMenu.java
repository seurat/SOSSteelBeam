package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainMenu extends Activity implements View.OnClickListener {
    String TAG = "MainMenu";
    ImageButton shapes_button, about_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        shapes_button = (ImageButton) findViewById(R.id.shapes);
        shapes_button.setOnClickListener(this);
        about_button = (ImageButton) findViewById(R.id.about);
        about_button.setOnClickListener(this);
    }

    public void about(View v) {
        Intent intent = new Intent(this, ContactUs.class);
        startActivity(intent);
    }

    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.shapes:
                intent = new Intent(this, ShapesActivity.class);
                startActivity(intent);
                Log.e(TAG, "SHAPES");
                break;
            case R.id.about:
                intent = new Intent(this, ContactUs.class);
                startActivity(intent);
                Log.e(TAG, "ABOUT");
                break;
            default:
                break;
        }
    }

}
