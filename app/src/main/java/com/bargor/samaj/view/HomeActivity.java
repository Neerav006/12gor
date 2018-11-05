package com.bargor.samaj.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.common.NetworkSchedulerService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.fragment.AgentDashBoardFragment;
import com.bargor.samaj.fragment.UserDashboardFragment;
import com.bargor.samaj.model.Gor;
import com.bargor.samaj.model.GorList;
import com.bargor.samaj.model.ImageDetail;
import com.bargor.samaj.model.MyRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String root = "50e423ff4b2b6fd7d61055c4a80bb55d0b6fdbe8fa2a4ad6459087e729d2a11c";
    private final String BASE_URL = "http://12gor.codefuelindia.com";
    private String id;
    private String mem_id;
    private boolean isLogin;
    private String name;
    private String user_name;
    private String role;
    private String isReg;
    private NavigationView navigationView;
    private CounterTotal counterTotal;
    private int count;
    private ArrayList<GorList> gorLists;
    private String status;
    private TextView tvCall;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private ViewImageApi viewImageApi;
    private GetGorName getGorName;
    private SynchData synchData;
    private OnRefreshData onRefreshData;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gorLists = new ArrayList<>();
        counterTotal = getCountAPI(Constants.BASE_URL);
        viewImageApi = getImage(Constants.BASE_URL);
        synchData = getSynchedData(Constants.BASE_URL);
        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, HomeActivity.this);
        id = sharedPreferences.getString(Constants.ID, null);
//        mem_id = sharedPreferences.getString(Constants.MEMBER_ID, null);
        mem_id = id;

        user_name = sharedPreferences.getString(Constants.USER_NAME, null);
        name = sharedPreferences.getString(Constants.NAME, null);
        isLogin = sharedPreferences.getBoolean(Constants.IS_LOGIN, false);
        role = sharedPreferences.getString(Constants.ROLE, null);
        isReg = sharedPreferences.getString(Constants.IS_REGISTERED, null);
        status = sharedPreferences.getString(Constants.MEMBER_STATUS, null);
        getGorName = getGorNameAPI(Constants.BASE_URL);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_edit_profile).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_selector_gor).setVisible(false);


        if (role.equals("4")) {
            navigationView.getMenu().findItem(R.id.nav_selector_gor).setVisible(true);
            View view = navigationView.getMenu().findItem(R.id.nav_selector_gor).getActionView();
            if (view != null) {
                final Spinner spinner = view.findViewById(R.id.spGor);

                getGorName.gorList().enqueue(new Callback<Gor>() {
                    @Override
                    public void onResponse(Call<Gor> call, Response<Gor> response) {
                        if (response.isSuccessful()) {


                            if (response.body().getGorList() != null && response.body().getGorList().size() > 0) {
                                gorLists = (ArrayList<GorList>) response.body().getGorList();
                                final GorList gorList = new GorList();
                                gorList.setId("0");
                                gorList.setName("All");
                                gorLists.add(0, gorList);

                                spinner.setAdapter(new MyCustomAdapter22(HomeActivity.this,
                                        R.layout.row_village_list, (ArrayList<GorList>) response.body().getGorList()));
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<Gor> call, Throwable t) {


                    }
                });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, HomeActivity.this);
                        editor.putString(Constants.GOR, ((GorList) parent.getItemAtPosition(position)).getId()).apply();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        }

        if (status != null) {

            switch (status) {
                case "1":
                    //verified by com.bargor.samaj
                    navigationView.getMenu().findItem(R.id.nav_edit).setVisible(true);
                    replaceFragment(new AgentDashBoardFragment());
                    break;
                case "2":
                    //unverified
                    navigationView.getMenu().findItem(R.id.nav_edit).setVisible(false);
                    replaceFragment(new UserDashboardFragment());
                    break;
                default:
                    navigationView.getMenu().findItem(R.id.nav_edit).setVisible(false);
                    replaceFragment(new UserDashboardFragment());
                    break;
            }

        }


        View mHeaderView = navigationView.getHeaderView(0);


        TextView tvCall = (TextView) navigationView.findViewById(R.id.tvHelpline);
        TextView tvSamajCall = navigationView.findViewById(R.id.tvHelplineSamaj);
        final ImageView ivPhoto = mHeaderView.findViewById(R.id.imageView);

        viewImageApi.viewImage(id).enqueue(new Callback<ImageDetail>() {
            @Override
            public void onResponse(Call<ImageDetail> call, Response<ImageDetail> response) {
                if (response.isSuccessful()) {


                    Glide.with(getApplicationContext())
                            .load(VIEW_PATH.concat(response.body().getPhoto()))
                            .apply(new RequestOptions().placeholder(R.drawable.noimage))
                            .into(ivPhoto);
                }
            }

            @Override
            public void onFailure(Call<ImageDetail> call, Throwable t) {

            }
        });


        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919427745635"));
                startActivity(intent);
            }
        });

        tvSamajCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919723460540"));
                startActivity(intent);
            }
        });


        if (mHeaderView != null) {
            TextView tvName = (TextView) mHeaderView.findViewById(R.id.tvUserName);
            tvName.setText(name);


        }


        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (getFragmentManager().getBackStackEntryCount() > 0) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    }


                } else {
                    //show hamburger

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        toggle.syncState();
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawer.openDrawer(GravityCompat.START);
                            }
                        });
                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        if (isLogin) {
            getMenuInflater().inflate(R.menu.home, menu);

            final View notificaitons = menu.findItem(R.id.notification).getActionView();

            final TextView counterTv = (TextView) notificaitons.findViewById(R.id.txtCount);
            counterTv.setVisibility(View.GONE);

            counterTotal.getCount(mem_id).enqueue(new Callback<MyRes>() {
                @Override
                public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                    if (response.isSuccessful()) {

                        count = response.body().getCount();

                        if (count > 0) {
                            counterTv.setVisibility(View.VISIBLE);
                            counterTv.setText(String.valueOf(count));
                        } else {
                            counterTv.setVisibility(View.GONE);
                        }
                    }

                }

                @Override
                public void onFailure(Call<MyRes> call, Throwable t) {

                }
            });


            //dummy test
            notificaitons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counterTv.setVisibility(View.GONE);

                    Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                    startActivity(intent);


                }
            });


            return true;
        }

        return false;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOut) {
            // clear preference and navigate to login screen
            // clear state

            SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, HomeActivity.this);
            editor.putBoolean(Constants.IS_LOGIN, false);

            editor.putString(Constants.USER_NAME, null);
            editor.putString(Constants.NAME, null);
            editor.putString(Constants.ID, null);
            editor.putString(Constants.ROLE, null);
            editor.putBoolean(Constants.IS_REGISTERED, false);
            editor.putString(Constants.MEMBER_ID, null);
            editor.putString(Constants.MEMBER_NO, null);
            editor.putString(Constants.GOR, null);
            editor.putString(Constants.MEMBER_STATUS, null);
            editor.putString("family_count", "0");
            editor.putString("addr", "");
            editor.putString("gr_count", "0");
            editor.apply();


            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

            return true;
        } else if (id == R.id.action_changePwd) {

            Intent intent = new Intent(HomeActivity.this, ChangePwdActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.synch) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            synchData.synchMyData(mem_id).enqueue(new Callback<MyRes>() {
                @Override
                public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                    if (response.isSuccessful()) {

                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, HomeActivity.this);
                        editor.putString(Constants.ID, response.body().getId());
                        editor.putString(Constants.USER_NAME, response.body().getUser_name());
                        editor.putString(Constants.NAME, response.body().getName());
                        editor.putString(Constants.ROLE, response.body().getRole());
                        editor.putString(Constants.IS_REGISTERED, response.body().isPremium());
                        editor.putBoolean(Constants.IS_LOGIN, true);
                        editor.putString(Constants.MEMBER_ID, response.body().getMem_id());
                        editor.putString(Constants.MEMBER_STATUS, response.body().getStatus());
                        editor.putString(Constants.GOR, response.body().getGor());
                        editor.putString("family_count", response.body().getFamily_count());
                        editor.putString("addr", response.body().getAddr());
                        editor.putString("gr_count", response.body().getGroup_count());
                        editor.apply();


                        if (onRefreshData != null) {
                            onRefreshData.updateData();
                        }


                        Toast.makeText(HomeActivity.this, "Synch Complete", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<MyRes> call, Throwable t) {

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
            if (status != null) {

                switch (status) {
                    case "1":
                        replaceFragment(new AgentDashBoardFragment());
                        break;
                    case "2":
                        replaceFragment(new UserDashboardFragment());
                        break;
                    default:
                        replaceFragment(new UserDashboardFragment());

                        break;
                }

            }

        } else if (id == R.id.nav_edit) {
            Intent intent = new Intent(HomeActivity.this, EditDetailActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_edit_profile) {
            Intent intent = new Intent(HomeActivity.this, EditImageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_donate) {
//            Intent intent = new Intent(HomeActivity.this, PayInitActivity.class);
//            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment toReplace) {

        for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

            getFragmentManager().popBackStack();
        }

        if (toReplace.getClass().getSimpleName().equalsIgnoreCase(AgentDashBoardFragment.class.getSimpleName())

                || toReplace.getClass().getSimpleName().equalsIgnoreCase(UserDashboardFragment.class.getSimpleName())
                ) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, toReplace, toReplace.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, toReplace, toReplace.getClass().getSimpleName())
                    .addToBackStack(toReplace.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }


    }

    CounterTotal getCountAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(CounterTotal.class);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), HomeActivity.this);

        navigationView.getMenu().findItem(R.id.nav_edit).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_edit_profile).setVisible(true);
        final ImageView ivPhoto = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        viewImageApi.viewImage(id).enqueue(new Callback<ImageDetail>() {
            @Override
            public void onResponse(Call<ImageDetail> call, Response<ImageDetail> response) {
                if (response.isSuccessful()) {


                    Glide.with(getApplicationContext())
                            .load(VIEW_PATH.concat(response.body().getPhoto()))
                            .apply(new RequestOptions().placeholder(R.drawable.noimage))
                            .into(ivPhoto);
                }
            }

            @Override
            public void onFailure(Call<ImageDetail> call, Throwable t) {

            }
        });


    }

    ViewImageApi getImage(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(ViewImageApi.class);
    }

    GetGorName getGorNameAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetGorName.class);
    }

    SynchData getSynchedData(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SynchData.class);
    }

    public void setOnRefreshListener(OnRefreshData onRefreshListener) {
        this.onRefreshData = onRefreshListener;
    }


    interface CounterTotal {
        @POST("notification/countnotificationapi/")
        @FormUrlEncoded
        Call<MyRes> getCount(@Field("m_id") String m_id);
    }

    interface ViewImageApi {
        @POST("member/viewimgapi/")
        @FormUrlEncoded
        Call<ImageDetail> viewImage(@Field("id") String mid);
    }


    interface SynchData {
        @POST("home/refreshapi/")
        @FormUrlEncoded
        Call<MyRes> synchMyData(@Field("id") String mid);
    }

    public interface GetGorName {
        @POST("gor/gorlistapi/")
        Call<Gor> gorList();
    }

    public class MyCustomAdapter22 extends ArrayAdapter<GorList> {

        private ArrayList<GorList> objects;

        MyCustomAdapter22(Context context, int textViewResourceId,
                          ArrayList<GorList> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row_village_list, parent, false);
            TextView tvVillageName = row.findViewById(R.id.tvVillageName);
            tvVillageName.setText(objects.get(position).getName());


            return row;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }

}
