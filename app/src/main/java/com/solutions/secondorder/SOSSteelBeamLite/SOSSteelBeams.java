package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SOSSteelBeams extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sossteel_beams);

    }
    public void inflateMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


}
