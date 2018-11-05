package com.bargor.samaj.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.NetworkSchedulerService;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.MyRes;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {


    private final long DOUBLE_TAP = 1500;
    private final String root = "BA326C6BC3AD52ED2861784D680D2348F2F8DFB9131DF2AAECBFBE20C1884FF7";
    private final String BASE_URL = "http://12gor.codefuelindia.com";
    private long lastclick = 0;
    private TextInputEditText edtUserName;
    private TextInputEditText edtPwd;
    private TextView tvForgot;
    private Button btnLogin;
    private View dummyView;
    private ImageView ivLoader;
    private ConstraintLayout constraintLayout;
    private LoginAPI loginAPI;
    private Button btnNewReg;
    private String reg_in_progress = "";
    private String user_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getApplicationContext());
        loginAPI = getAPIService(BASE_URL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob();
        }

        user_name = sharedPreferences.getString("ok", "");
        reg_in_progress = sharedPreferences.getString(Constants.REG_IN_PROGRESS, "");
        edtUserName = (TextInputEditText) findViewById(R.id.edtUserName);
        edtPwd = (TextInputEditText) findViewById(R.id.edtPassword);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        dummyView = findViewById(R.id.includeDummy);
        ivLoader = (ImageView) findViewById(R.id.ivProgress);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintTop);
        btnNewReg = (Button) findViewById(R.id.btnNewRegistration);

        btnNewReg.setVisibility(View.VISIBLE);


        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        btnNewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastclick < DOUBLE_TAP) {
                    Log.e("double tap", "");
                    return;
                }
                lastclick = SystemClock.elapsedRealtime();
                SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getApplicationContext());

                user_name = sharedPreferences.getString("ok", "");
                reg_in_progress = sharedPreferences.getString(Constants.REG_IN_PROGRESS, "");

                if (!user_name.isEmpty() && !reg_in_progress.isEmpty()) {

                    Intent intent = new Intent(LoginActivity.this, ForgotPwd22Activity.class);
                    intent.putExtra(Constants.ID, reg_in_progress);
                    intent.putExtra(Constants.TYPE, "reg");
                    startActivity(intent);


                } else {
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                }


            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - lastclick < DOUBLE_TAP) {
                    Log.e("double tap", "");
                    return;
                }
                lastclick = SystemClock.elapsedRealtime();

                if (edtUserName.getText().toString().trim().isEmpty()) {
                    edtUserName.requestFocus();
                    Toast.makeText(LoginActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                } else if (edtPwd.getText().toString().trim().isEmpty()) {
                    edtPwd.requestFocus();
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {
                    constraintLayout.setVisibility(View.GONE);
                    dummyView.setVisibility(View.VISIBLE);

                    Glide.with(LoginActivity.this).asGif().load(R.drawable.progress).into(ivLoader);

                    loginAPI.login(edtUserName.getText().toString().trim(),
                            edtPwd.getText().toString().trim(), root).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(@NonNull Call<MyRes> call, @NonNull Response<MyRes> response) {


                            if (response.isSuccessful()) {

                                if (response.body() != null && response.body().getMsg().equalsIgnoreCase("true")) {

                                    //save state in preference

                                    SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, LoginActivity.this);
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

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    constraintLayout.setVisibility(View.VISIBLE);
                                    dummyView.setVisibility(View.GONE);

                                    Toast.makeText(LoginActivity.this, "Invalid credential", Toast.LENGTH_SHORT).show();

                                }

                            } else {

                                //dummy
                                constraintLayout.setVisibility(View.VISIBLE);
                                dummyView.setVisibility(View.GONE);

                                Toast.makeText(LoginActivity.this, "Connection error", Toast.LENGTH_SHORT).show();


                            }


                        }

                        @Override
                        public void onFailure(@NonNull Call<MyRes> call, @NonNull Throwable t) {

                            constraintLayout.setVisibility(View.VISIBLE);
                            dummyView.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Connection error", Toast.LENGTH_SHORT).show();


                        }
                    });


                }


            }
        });


        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastclick < DOUBLE_TAP) {
                    return;
                }
                lastclick = SystemClock.elapsedRealtime();

                Intent intent = new Intent(LoginActivity.this, ForgotPwdActivity.class);
                startActivity(intent);


            }
        });


    }


    LoginAPI getAPIService(String baseUrl) {

        return RetrofitClient.getClient(baseUrl).create(LoginAPI.class);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    interface LoginAPI {

        @POST("/home/loginapi/")
        @FormUrlEncoded
        Call<MyRes> login(@Field("email") String username,
                          @Field("password") String pwd, @Field("root") String root
        );
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
