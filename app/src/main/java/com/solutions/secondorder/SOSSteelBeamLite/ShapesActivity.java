package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShapesActivity extends Activity implements View.OnClickListener {
    ScaledImageView i_button, tube_button, c_button, t_button, l_button;
    Keys keys = Keys.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);

        i_button = (ScaledImageView) findViewById(R.id.i_shape_button);
        i_button.setOnClickListener(this);

        tube_button = (ScaledImageView) findViewById(R.id.tube_shape_button);
        tube_button.setOnClickListener(this);

        c_button = (ScaledImageView) findViewById(R.id.c_shape_button);
        c_button.setOnClickListener(this);

        t_button = (ScaledImageView) findViewById(R.id.t_shape_button);
        t_button.setOnClickListener(this);

        l_button = (ScaledImageView) findViewById(R.id.l_shape_button);
        l_button.setOnClickListener(this);

    }

    public void onClick(View v) {
        String shape_extra_name = keys.intent_shapes;
        Intent nextScreen = new Intent(this, SectionsActivity.class);
        switch(v.getId()) {
            case R.id.i_shape_button:
                nextScreen.putExtra(shape_extra_name, "i");
                break;
            case R.id.tube_shape_button:
                nextScreen.putExtra(shape_extra_name, "tube");
                break;
            case R.id.c_shape_button:
                nextScreen.putExtra(shape_extra_name, "c");
                break;
            case R.id.t_shape_button:
                nextScreen.putExtra(shape_extra_name, "t");
                break;
            case R.id.l_shape_button:
                nextScreen.putExtra(shape_extra_name, "l");
                break;
            default:
                return;
        }
        startActivity(nextScreen);
        }


}
