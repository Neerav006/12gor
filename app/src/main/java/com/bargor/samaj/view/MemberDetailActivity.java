package com.bargor.samaj.view;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.Deathlist;
import com.bargor.samaj.model.FamilyList;

import com.bargor.samaj.model.Memberlist;
import com.bargor.samaj.model.SubCat;

import java.util.ArrayList;

public class MemberDetailActivity extends AppCompatActivity {

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private TextView tvMemNo;
    private TextView tvRegDate;
    private TextView tvName;
    private TextView tvAddr;
    private TextView tvCity;
    private TextView tvTaluka;
    private TextView tvDist;
    private TextView tvState;
    private TextView tvPin;
    private TextView tvMobile;
    private TextView tvEmail;
    private TextView tvBirth;
    private TextView tvStudy;
    private TextView tvJobCategory;
    private TextView tvJobDetail;
    private Memberlist memberlist;
    private Deathlist deathlist;
    private FamilyList familyList;
    private TextView lableExpDate;
    private TextView tvExpDate;
    private TextView lablePay;
    private TextView tvPayableAmt;
    private ImageView ivPhoto;
    private String profilePath;
    private TextView tvBlood;
    private TextView tvShakh;
    private ArrayList<BusCategoryList> busCategoryLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        if (getIntent() != null) {


            if (getIntent().getParcelableExtra("detail") instanceof Memberlist) {
                memberlist = getIntent().getParcelableExtra("detail");
                busCategoryLists = getIntent().getParcelableArrayListExtra("bus");

            } else if (getIntent().getParcelableExtra("detail") instanceof FamilyList) {
                familyList = getIntent().getParcelableExtra("detail");
                busCategoryLists = getIntent().getParcelableArrayListExtra("bus");

            } else {
                deathlist = getIntent().getParcelableExtra("detail");
            }


        }
        profilePath = Constants.BASE_URL.concat("profile/");

        ivPhoto = findViewById(R.id.ivPhoto);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvMemNo = (TextView) findViewById(R.id.tvMemNo);
        tvRegDate = (TextView) findViewById(R.id.tvRegDate);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddr = (TextView) findViewById(R.id.tvAddr);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvTaluka = (TextView) findViewById(R.id.tvTaluka);
        tvDist = (TextView) findViewById(R.id.tvDist);
        tvState = (TextView) findViewById(R.id.tvState);
        tvPin = (TextView) findViewById(R.id.tvPin);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvBirth = (TextView) findViewById(R.id.tvBirth);
        lableExpDate = (TextView) findViewById(R.id.lableExpDate);
        tvExpDate = (TextView) findViewById(R.id.tvExpDate);
        lablePay = (TextView) findViewById(R.id.lablePay);
        tvPayableAmt = (TextView) findViewById(R.id.tvPayableAmt);
        tvBlood = findViewById(R.id.tvBloodGrp);
        tvShakh = findViewById(R.id.tvShakh);
        tvStudy = findViewById(R.id.tvStudy);
        tvJobCategory = findViewById(R.id.tvJobCategory);
        tvJobDetail = findViewById(R.id.tvJobDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (memberlist != null) {

            toolbar.setTitle(memberlist.getName());
            tvName.setText(memberlist.getName());
            tvShakh.setText(memberlist.getShakh());
            tvBlood.setText(memberlist.getBlood());
            tvMemNo.setText(memberlist.getId());
            tvAddr.setText(memberlist.getAddress());
            tvCity.setText(memberlist.getVillage());
            tvEmail.setText(memberlist.getEmail());
            tvMobile.setText(memberlist.getMobile());
            tvBirth.setText(Utils.getDDMMYYYY(memberlist.getDob()));

            if (busCategoryLists != null) {
                for (BusCategoryList busCategoryList : busCategoryLists) {
                    if (busCategoryList.getMainCat().getId().equals(memberlist.getBusCat())) {
                        tvJobCategory.setText(busCategoryList.getMainCat().getName());
                        break;
                    }
                }


                for (BusCategoryList busCategoryList : busCategoryLists) {

                    if (!busCategoryList.getMainCat().getId().equals("1")) {
                        for (SubCat subCat : busCategoryList.getSubCat()) {
                            if (subCat.getId().equals(memberlist.getBusSubCat())) {
                                tvJobDetail.setText(subCat.getSubName());
                                break;
                            }
                        }
                    }


                }


                for (BusCategoryList busCategoryList : busCategoryLists) {
                    for (SubCat subCat : busCategoryList.getSubCat()) {
                        if (subCat.getId().equals(memberlist.getStudyCat())) {
                            tvStudy.setText(subCat.getSubName());
                            break;
                        }
                    }
                }
            }


        } else if (familyList != null) {
            tvMemNo.setText(familyList.getId());
            toolbar.setTitle(familyList.getName());
            tvName.setText(familyList.getName());
            tvAddr.setText(familyList.getAddress());
            tvEmail.setText(familyList.getEmail());
            tvMobile.setText(familyList.getMobile());
            tvBirth.setText(Utils.getDDMMYYYY(familyList.getDob()));
            tvPin.setText("--");
            tvState.setText("--");
            tvCity.setText(familyList.getVillage());
            tvTaluka.setText("--");
            ivPhoto.setVisibility(View.VISIBLE);
            tvShakh.setText(familyList.getShakh());
            tvBlood.setText(familyList.getBlood());
            Glide.with(getApplicationContext())
                    .load(profilePath.concat(familyList.getProfile()))
                    .apply(new RequestOptions().placeholder(R.drawable.noimage))
                    .into(ivPhoto);


            if (busCategoryLists != null) {
                for (BusCategoryList busCategoryList : busCategoryLists) {
                    if (busCategoryList.getMainCat().getId().equals(familyList.getBusCat())) {
                        tvJobCategory.setText(busCategoryList.getMainCat().getName());
                        break;
                    }
                }


                for (BusCategoryList busCategoryList : busCategoryLists) {

                    if (!busCategoryList.getMainCat().getId().equals("1")) {
                        for (SubCat subCat : busCategoryList.getSubCat()) {
                            if (subCat.getId().equals(familyList.getBusSubCat())) {
                                tvJobDetail.setText(subCat.getSubName());
                                break;
                            }
                        }
                    }


                }


                for (BusCategoryList busCategoryList : busCategoryLists) {
                    for (SubCat subCat : busCategoryList.getSubCat()) {
                        if (subCat.getId().equals(familyList.getStudyCat())) {
                            tvStudy.setText(subCat.getSubName());
                            break;
                        }
                    }
                }
            }


        } else if (deathlist != null) {
            toolbar.setTitle(deathlist.getName());
            tvMemNo.setText(deathlist.getId());
            tvName.setText(deathlist.getName());
            tvRegDate.setText(deathlist.getJoinDate());
            tvMemNo.setText(deathlist.getMemberId());
            tvAddr.setText(deathlist.getAddress());
            tvCity.setText(deathlist.getCity());
            tvTaluka.setText(deathlist.getTaluka());
            tvEmail.setText(deathlist.getEmail());
            tvMobile.setText(deathlist.getMobile());
            tvBirth.setText(deathlist.getDob());
            tvPin.setText(deathlist.getPin());
            tvState.setText(deathlist.getState());

            lableExpDate.setVisibility(View.VISIBLE);
            lablePay.setVisibility(View.VISIBLE);
            tvExpDate.setVisibility(View.VISIBLE);
            tvPayableAmt.setVisibility(View.VISIBLE);
            tvExpDate.setText(deathlist.getExpireddate());
            tvPayableAmt.setText(deathlist.getAmount());
        }
    }
}
