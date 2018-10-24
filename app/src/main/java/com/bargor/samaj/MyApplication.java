package com.bargor.samaj;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.cons.Constants;
import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by USER on 03-08-2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getApplicationContext());

        int lastVersionCode = sharedPreferences.getInt("VERSION", 0);


        try {
            int currentVersion = getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;

            if (lastVersionCode < currentVersion) {
                Utils.deleteCache(getApplicationContext());
                getApplicationContext().getSharedPreferences(Constants.MY_PREF, 0).edit().clear().apply();
            }

            SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, getApplicationContext());
            editor.putInt("VERSION", currentVersion).apply();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }



}
