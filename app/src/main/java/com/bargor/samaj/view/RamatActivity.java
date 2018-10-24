package com.bargor.samaj.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bargor.samaj.R;
import com.bargor.samaj.fragment.AddTeamMemberFragment;
import com.bargor.samaj.fragment.SelectGameFragment;

public class RamatActivity extends AppCompatActivity {

    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramat);


        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_activity_ramat, new AddTeamMemberFragment());
        ft.commit();

    }
}
