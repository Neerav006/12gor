package com.bargor.samaj.view;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bargor.samaj.R;

public class DownLoadFormActivity extends AppCompatActivity {
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private TextView tvArjiPatrak;
    private TextView tvSarnamaFerfar;
    private TextView tvVarsdarFerFar;
    private TextView tvMrutyuSahay;
    private TextView tvAbhinandan;
    private TextView tvSamajikSurksha;
    private TextView tvProfessionDirectory;
    private TextView tvYuvakYuvati;
    private TextView tvInsuranceForm;
    private String PDF_VIEW_BASE="http://drive.google.com/viewerng/viewer?embedded=true&url=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_form);

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvArjiPatrak = (TextView) findViewById(R.id.tvArjiPatrak);
        tvSarnamaFerfar = (TextView) findViewById(R.id.tvSarnamaFerfar);
        tvVarsdarFerFar = (TextView) findViewById(R.id.tvVarsdarFerFar);
        tvMrutyuSahay = (TextView) findViewById(R.id.tvMrutyuSahay);
        tvAbhinandan = (TextView) findViewById(R.id.tvAbhinandan);
        tvSamajikSurksha = (TextView) findViewById(R.id.tvSamajikSurksha);
        tvProfessionDirectory = (TextView) findViewById(R.id.tvProfessionDirectory);
        tvYuvakYuvati = (TextView) findViewById(R.id.tvYuvakYuvati);
        tvInsuranceForm= (TextView) findViewById(R.id.tvInsuranceForm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//         tvInsuranceForm.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                 intent.putExtra("path",PDF_VIEW_BASE.concat("http://12gor.codefuelindia.com/CIRCULAR_2017.pdf")) ;
//                 startActivity(intent);
//             }
//         });
//
//
//        tvArjiPatrak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/form_adaatham.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvAbhinandan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/APLICATION%20FORM_2.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvSarnamaFerfar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/APLICATION%20FORM_1.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvVarsdarFerFar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/APLICATION%20FORM_3.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvMrutyuSahay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/APLICATION%20FORM_4.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvYuvakYuvati.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/APLICATION_marriage.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvProfessionDirectory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/APLICATION_business.pdf")) ;
//                startActivity(intent);
//            }
//        });
//
//        tvSamajikSurksha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DownLoadFormActivity.this,ViewDownloadDocumentActivity.class);
//                intent.putExtra("path",PDF_VIEW_BASE.concat("http://adaathamwelfare.org/downloads/samajik.pdf")) ;
//                startActivity(intent);
//            }
//        });

    }
}
