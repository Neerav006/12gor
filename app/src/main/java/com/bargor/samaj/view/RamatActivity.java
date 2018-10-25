package com.bargor.samaj.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bargor.samaj.R;
import com.bargor.samaj.fragment.AddTeamMemberFragment;
import com.bargor.samaj.fragment.SelectGameFragment;

public class RamatActivity extends AppCompatActivity {

    private FragmentTransaction ft;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramat);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_activity_ramat, new SelectGameFragment());
        ft.commit();

    }
}
