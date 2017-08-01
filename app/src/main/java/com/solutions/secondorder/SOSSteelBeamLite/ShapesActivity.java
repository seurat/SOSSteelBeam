package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ShapesActivity extends Activity implements View.OnClickListener, AttributeProvider {
    String TAG = "ShapesActivity";
    ScaledImageView i_button, tube_button, c_button, t_button, l_button, pipe_button;
    Keys keys = Keys.getInstance();
    SOSSteelBeamsApplication appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);
        appState = (SOSSteelBeamsApplication) getApplication();

        i_button = (ScaledImageView) findViewById(R.id.i_shape_button);
        i_button.setOnClickListener(this);


        tube_button = (ScaledImageView) findViewById(R.id.tube_shape_button);
        c_button = (ScaledImageView) findViewById(R.id.c_shape_button);
        t_button = (ScaledImageView) findViewById(R.id.t_shape_button);
        l_button = (ScaledImageView) findViewById(R.id.l_shape_button);
        pipe_button = (ScaledImageView) findViewById(R.id.pipe_shape_button);

        if(appState.getDatabaseName().equals("Historical (1873-1952)")) {
            c_button.changeImageResource(R.drawable.button_group_c_gray);
            t_button.changeImageResource(R.drawable.button_group_t_gray);
            tube_button.changeImageResource(R.drawable.button_group_tube_gray);
            l_button.changeImageResource(R.drawable.button_group_l_gray);
            pipe_button.changeImageResource(R.drawable.button_group_pipe_gray);

        }
        else if(appState.getDatabaseName().equals("ASD5 (1962)")){
            tube_button.changeImageResource(R.drawable.button_group_tube_gray);
            pipe_button.changeImageResource(R.drawable.button_group_pipe_gray);
            c_button.setOnClickListener(this);
            t_button.setOnClickListener(this);
            l_button.setOnClickListener(this);
        }
        else {
            tube_button.setOnClickListener(this);

            c_button.setOnClickListener(this);

            t_button.setOnClickListener(this);

            l_button.setOnClickListener(this);

            pipe_button.setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        String shape_extra_name = keys.intent_shapes;
        Intent nextScreen = new Intent(this, DimensionsActivity.class);
        Log.e(TAG, ""+v.getId());
        switch(v.getId()) {
            case R.id.i_shape_button:
                nextScreen.putExtra(keys.intent_shapes, keys.i_shape);
                appState.setShape(keys.i_shape);
                break;
            case R.id.tube_shape_button:
                nextScreen.putExtra(keys.intent_shapes, keys.tube_shape);
                appState.setShape(keys.tube_shape);

                break;
            case R.id.c_shape_button:
                nextScreen.putExtra(keys.intent_shapes, keys.c_shape);
                appState.setShape(keys.c_shape);
                break;
            case R.id.t_shape_button:
                nextScreen.putExtra(keys.intent_shapes, keys.t_shape);
                appState.setShape(keys.t_shape);
                break;
            case R.id.l_shape_button:
                nextScreen.putExtra(keys.intent_shapes, keys.l_shape);
                appState.setShape(keys.l_shape);
                break;
            case R.id.pipe_shape_button:
                nextScreen.putExtra(keys.intent_shapes, keys.pipe_shape);
                appState.setShape(keys.pipe_shape);
                break;
            default:
                return;
        }
        startActivity(nextScreen);
        }


    @Override
    public String getAttributeFromActivity() {
        return null;
    }

    @Override
    public void screenDispatch(int button) {

    }

    @Override
    public boolean isHSSSqr() {
        return false;
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public String getDatabaseName() {
        return ((SOSSteelBeamsApplication) getApplication()).getDatabaseName();
    }

    @Override
    public String getShape() {
        return null;
    }
}
