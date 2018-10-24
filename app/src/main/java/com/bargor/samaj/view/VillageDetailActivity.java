package com.bargor.samaj.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.bargor.samaj.R;

public class VillageDetailActivity extends AppCompatActivity {

    int index;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null) {
            index = getIntent().getIntExtra("index", 0);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = findViewById(R.id.webView);
        webView.setHorizontalScrollBarEnabled(true);

        switch (index) {
            case 1:
                webView.loadUrl("http://12gor.codefuelindia.com/samitee/patidar_2.html");
                break;
            case 2:
                webView.loadUrl("http://12gor.codefuelindia.com/samitee/patidar_3.html");
                break;
            case 3:
                webView.loadUrl("http://12gor.codefuelindia.com/samitee/patidar_4.html");
                break;
            case 4:
                webView.loadUrl("http://12gor.codefuelindia.com/samitee/patidar_5.html");
                break;
            case 5:
                webView.loadUrl("http://12gor.codefuelindia.com/samitee/patidar_6.html");
                break;
            case 6:
                webView.loadUrl("http://12gor.codefuelindia.com/samitee/patidar_7.html");
                break;
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
