package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Application;
import android.util.Log;

/**
 * Created by ranialjondi on 8/23/16.
 */
public class SOSSteelBeamsApplication extends Application {

    public String databaseName = "AISC14.1";

    public String shape = "i";

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        Log.e("Application",databaseName);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setShape(String shape) {
        this.shape = shape;
        Log.e("Application", shape);
    }

    public String getShape() {
        return shape;
    }

}
