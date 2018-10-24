package com.bargor.samaj.view;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.Marriagelist;
import com.facebook.drawee.view.SimpleDraweeView;
import com.bargor.samaj.model.SubCat;

import java.util.ArrayList;

public class MetrimonimalDetailActivity extends AppCompatActivity {


    private AppBarLayout appBar;
    private Toolbar toolbar;
    private SimpleDraweeView ivProfile;
    private TextView tvName;
    private TextView tvNative;
    private TextView tvMobile;
    private TextView tvDOB;
    private TextView tvStudy;
    private TextView tvDegree;
    private TextView tvJobType;
    private TextView tvJobDetail;
    private ArrayList<BusCategoryList> busCategoryLists;
    private TextView tvFamilyDetail;

    private Marriagelist marriagelist;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrimonimal_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (getIntent() != null) {
            marriagelist = getIntent().getParcelableExtra("detail");
            busCategoryLists = getIntent().getParcelableArrayListExtra("list");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivProfile = (SimpleDraweeView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvNative = (TextView) findViewById(R.id.tvNative);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvStudy = (TextView) findViewById(R.id.tvStudy);
        tvJobType = (TextView) findViewById(R.id.tvJobType);
        tvJobDetail = (TextView) findViewById(R.id.tvJobDetail);
        tvFamilyDetail = findViewById(R.id.tvFamilyDetail);


        if (marriagelist != null) {

            ivProfile.setImageURI(VIEW_PATH.concat(marriagelist.getProfile()));
            tvName.setText(marriagelist.getName());
            tvNative.setText(marriagelist.getVillage());
            tvMobile.setText(marriagelist.getMobile());
            tvDOB.setText(Utils.getDDMMYYYY(marriagelist.getDob()));

            if (busCategoryLists != null) {

                for (BusCategoryList busCategoryList : busCategoryLists) {
                    if (busCategoryList.getMainCat().getId().equals(marriagelist.getBusCat())) {
                        tvJobType.setText(busCategoryList.getMainCat().getName());
                        break;
                    }
                }


                for (BusCategoryList busCategoryList : busCategoryLists) {

                    if (!busCategoryList.getMainCat().getId().equals("1")) {
                        for (SubCat subCat : busCategoryList.getSubCat()) {
                            if (subCat.getId().equals(marriagelist.getBusSubCat())) {
                                tvJobDetail.setText(subCat.getSubName());
                                break;
                            }
                        }
                    }


                }


                for (BusCategoryList busCategoryList : busCategoryLists) {
                    for (SubCat subCat : busCategoryList.getSubCat()) {
                        if (subCat.getId().equals(marriagelist.getStudyCat())) {
                            tvStudy.setText(subCat.getSubName());
                            break;
                        }
                    }
                }


            }


            tvFamilyDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MetrimonimalDetailActivity.this, GroupViseFamily.class);
                    intent.putExtra("family_id", marriagelist.getId());
                    intent.putParcelableArrayListExtra("bus",busCategoryLists);
                    startActivity(intent);

                }
            });

        }
    }
}
