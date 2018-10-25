package com.bargor.samaj.view;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    getSupportFragmentManager().popBackStack();

                }
                else{
                    finish();
                }

            }
        });


        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_activity_ramat, new SelectGameFragment());
        ft.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         getMenuInflater().inflate(R.menu.menu_myteam,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_menu_my_team){

            startActivity(new Intent(RamatActivity.this,MyTeamActivity.class));

            return true;
        }


        return false;
    }
}
