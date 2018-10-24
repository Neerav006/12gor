package com.bargor.samaj.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.cons.Constants;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 3;
    private Handler handler;
    private Runnable runnable;
    private String id;
    private boolean isLogin;
    private String name;
    private String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, SplashActivity.this);

        id = sharedPreferences.getString(Constants.ID, null);
        user_name = sharedPreferences.getString(Constants.USER_NAME, null);
        name = sharedPreferences.getString(Constants.NAME, null);
        isLogin = sharedPreferences.getBoolean(Constants.IS_LOGIN, false);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_splash);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SPLASH_TIME_OUT--;
                handler.postDelayed(runnable, 1000);

                if (SPLASH_TIME_OUT == 0) {
                    handler.removeCallbacks(runnable);
                    handler.removeCallbacksAndMessages(null);


                    if (isLogin) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }


            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

}
