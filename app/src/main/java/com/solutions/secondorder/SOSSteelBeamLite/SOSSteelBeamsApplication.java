package com.solutions.secondorder.SOSSteelBeamLite;

import android.app.Application;
import android.util.Log;

/**
 * Created by ranialjondi on 8/23/16.
 */
public class SOSSteelBeamsApplication extends Application {

    public String databaseName = "AISC14.1";

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        Log.e("Application",databaseName);
    }

    public String getDatabaseName() {
        return databaseName;
    }

}
