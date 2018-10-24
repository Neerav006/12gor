package com.bargor.samaj.view;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.model.Businesslist;
import com.facebook.drawee.view.SimpleDraweeView;

public class BusinessDetailActivity extends AppCompatActivity {
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private SimpleDraweeView ivProfile;
    private TextView tvName;
    private TextView tvAddr;
    private TextView tvMobile;
    private TextView tvPhone;
    private TextView tvNative;
    private TextView tvEmail;
    private TextView tvExp;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private TextView tvDegree;


    private Businesslist businesslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_businessdir);

        if (getIntent() != null) {
            businesslist = getIntent().getParcelableExtra("detail");
        }

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivProfile = (SimpleDraweeView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddr = (TextView) findViewById(R.id.tvAddr);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvNative = (TextView) findViewById(R.id.tvNative);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvExp = (TextView) findViewById(R.id.tvExp);
        tvDegree = (TextView) findViewById(R.id.tvDegree);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (businesslist != null) {
            tvName.setText(businesslist.getName());
            tvAddr.setText(businesslist.getAddress());
            tvMobile.setText(businesslist.getMobile());
            tvNative.setText(businesslist.getVillage());

            tvExp.setText(businesslist.getBusinessName());
            tvEmail.setText(businesslist.getEmail());
            tvDegree.setText(businesslist.getStudy());
            ivProfile.setImageURI(VIEW_PATH.concat(businesslist.getPhoto()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), BusinessDetailActivity.this);
    }
}
