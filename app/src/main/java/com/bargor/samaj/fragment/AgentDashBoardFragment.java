package com.bargor.samaj.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.MyAdapter;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.Banner;
import com.bargor.samaj.model.MyBanner;
import com.bargor.samaj.model.MyRes;
import com.bargor.samaj.view.AddBusinessActivity;
import com.bargor.samaj.view.DirectoryDashboardActivity;
import com.bargor.samaj.view.FamilyMemberList;
import com.bargor.samaj.view.HappyBirthdayActivity;
import com.bargor.samaj.view.HomeActivity;
import com.bargor.samaj.view.LatestNewsActivity;
import com.bargor.samaj.view.MemberRegistrationActivity;
import com.bargor.samaj.view.OnRefreshData;
import com.bargor.samaj.view.RamatActivity;
import com.bargor.samaj.view.SearchMemberActivity;
import com.bargor.samaj.view.SendMsgActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class AgentDashBoardFragment extends Fragment implements OnRefreshData {
    final Handler handler = new Handler();
    private ViewPager mPager;
    private int currentPage = 0;
    private long mLastClickTime = 0;
    private CardView cvMemberReg;
    private CardView cvMemberInfo;
    private CardView cvDownloadForm;
    private CardView cvSendMsg;
    private CardView cvBirthDay;
    private CardView cvNews;
    private CardView cvRamtosav;
    private CardView cvSarkariYojana;
    private CardView cvNokariMahiti;
    private Context context;
    private String id;
    private String user_name;
    private String name;
    private boolean isLogin;
    private String isReg;
    private String role;
    private String gor;
    private CardView cvDirectory;
    private CardView cvFamilymember;
    private GetBanner getBanner;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    private View view;
    private LinearLayout llTop;
    private LinearLayout llSecond;
    private LinearLayout llLast;
    private CardView cvRequest;
    private CardView cvAddBusiness;
    private ApplyForPrimium applyForPrimium;
    private ProgressDialog progressDialog;
    private TextView tvVillageInfo;
    private TextView tvKarobariInfo;
    private TextView tvWomenClub;
    private TextView tvYuvaClub;
    private TextView tvSharafiMandli;
    private TextView tvUtkarsMandli;
    private String family_count;
    private String group_count;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        ((HomeActivity) context).setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;

        ((HomeActivity) context).setOnRefreshListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        XMENArray.add(R.drawable.banner1);
//        XMENArray.add(R.drawable.banner2);
//        XMENArray.add(R.drawable.banner3);
//        XMENArray.add(R.drawable.banner4new);

        getBanner = showBanner(Constants.BASE_URL);
        applyForPrimium = applyForPrimiumMember(Constants.BASE_URL);

        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getActivity());
        id = sharedPreferences.getString(Constants.ID, null);
        user_name = sharedPreferences.getString(Constants.USER_NAME, null);
        name = sharedPreferences.getString(Constants.NAME, null);
        isLogin = sharedPreferences.getBoolean(Constants.IS_LOGIN, false);
        role = sharedPreferences.getString(Constants.ROLE, null);
        isReg = sharedPreferences.getString(Constants.IS_REGISTERED, null);
        gor = sharedPreferences.getString(Constants.GOR, null);
        family_count = sharedPreferences.getString("family_count", null);
        group_count = sharedPreferences.getString("gr_count", "");

        getBanner.getBanner().enqueue(new Callback<MyBanner>() {
            @Override
            public void onResponse(Call<MyBanner> call, Response<MyBanner> response) {

                if (response.isSuccessful()) {

                    ArrayList<Banner> bannerArrayList = (ArrayList<Banner>) response.body().getBanner();

                    if (bannerArrayList != null && bannerArrayList.size() > 0) {

                        for (int i = 0; i < bannerArrayList.size(); i++) {
                            XMENArray.add(Constants.BASE_URL.concat("banner/").concat(bannerArrayList.get(i).getImage()));
                        }

                        if (view != null)
                            init(view);

                    }


                }

            }

            @Override
            public void onFailure(Call<MyBanner> call, Throwable t) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) (context)).setTitle("Dashboard");
        return inflater.inflate(R.layout.dashboard_agent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        llTop = view.findViewById(R.id.llTop);
        llSecond = view.findViewById(R.id.llSecond);
        llLast = view.findViewById(R.id.llLast);
        cvMemberReg = (CardView) view.findViewById(R.id.cvMemberReg);
        cvMemberInfo = (CardView) view.findViewById(R.id.cvMemberInfo);
        cvDownloadForm = (CardView) view.findViewById(R.id.cvDownloadForm);
        cvSendMsg = (CardView) view.findViewById(R.id.cvSendMsg);
        cvBirthDay = (CardView) view.findViewById(R.id.cvBirthDay);
        cvNews = (CardView) view.findViewById(R.id.cvNews);
        cvDirectory = (CardView) view.findViewById(R.id.cvDirectory);
        cvFamilymember = view.findViewById(R.id.cvFamilyList);
        cvRequest = view.findViewById(R.id.cvApplyForPremium);
        cvAddBusiness = view.findViewById(R.id.cvAddBusinessdetail);
        cvRamtosav = view.findViewById(R.id.cvRamatosav);
        cvSarkariYojana = view.findViewById(R.id.cvSarkariYojana);
        cvNokariMahiti = view.findViewById(R.id.cvNokariMahiti);
        tvVillageInfo = view.findViewById(R.id.tvVillageInfo);

        llTop.removeAllViews();
        llSecond.removeAllViews();
        llLast.removeAllViews();

        tvKarobariInfo = (TextView) view.findViewById(R.id.tvKarobariInfo);
        tvWomenClub = (TextView) view.findViewById(R.id.tvWomenClub);
        tvYuvaClub = (TextView) view.findViewById(R.id.tvYuvaClub);
        tvSharafiMandli = (TextView) view.findViewById(R.id.tvSharafiMandli);
        tvUtkarsMandli = (TextView) view.findViewById(R.id.tvUtkarsMandli);

        if (isReg != null) {

            switch (isReg) {
                case "0":
//                    llTop.addView(cvMemberReg);
//                    llTop.addView(cvMemberInfo);
//                    llTop.addView(cvNews);
//                    llTop.addView(cvDownloadForm);
//                    cvAddBusiness.setVisibility(View.GONE);
//                    llSecond.addView(cvSendMsg);
//                    llSecond.addView(cvFamilymember);
//                    cvKhetiMahiti.setVisibility(View.VISIBLE);
//                    cvSarkariYojana.setVisibility(View.VISIBLE);
//                    cvNokariMahiti.setVisibility(View.VISIBLE);
//                    if (gor.equals("2")) {
//                        llSecond.addView(cvRequest);
//                        llSecond.addView(cvKhetiMahiti);
//                        llLast.addView(cvSarkariYojana);
//                        llLast.addView(cvNokariMahiti);
//
//                    }
//                    else {
//
//                        llSecond.addView(cvKhetiMahiti);
//                        llSecond.addView(cvSarkariYojana);
//                        llLast.addView(cvNokariMahiti);
//                    }


                    // Any verified member can view directory of com.bargor.samaj
                    llTop.addView(cvMemberReg);
                    llTop.addView(cvFamilymember);
                    llTop.addView(cvMemberInfo);

                    llTop.addView(cvDirectory);
                    llSecond.addView(cvAddBusiness);
                    llSecond.addView(cvBirthDay);
                    llSecond.addView(cvSendMsg);
                    cvAddBusiness.setVisibility(View.VISIBLE);
//                    if (gor.equals("2")) {
//                        cvAddBusiness.setVisibility(View.VISIBLE);
//
//                    } else {
//                        cvAddBusiness.setVisibility(View.GONE);
//
//                    }

                    llSecond.addView(cvNews);


                    cvRamtosav.setVisibility(View.VISIBLE);
                    cvSarkariYojana.setVisibility(View.VISIBLE);
                    cvNokariMahiti.setVisibility(View.VISIBLE);

                    llLast.addView(cvRamtosav);
                    llLast.addView(cvSarkariYojana);
                    llLast.addView(cvNokariMahiti);
                    llLast.addView(cvDownloadForm);

                    break;
                case "1":
                    llTop.addView(cvMemberReg);
                    llTop.addView(cvFamilymember);
                    llTop.addView(cvMemberInfo);

                    llTop.addView(cvDirectory);
                    llSecond.addView(cvAddBusiness);
                    llSecond.addView(cvBirthDay);
                    llSecond.addView(cvSendMsg);
                    cvAddBusiness.setVisibility(View.VISIBLE);
//                    if (gor.equals("2")) {
//                        cvAddBusiness.setVisibility(View.VISIBLE);
//
//                    } else {
//                        cvAddBusiness.setVisibility(View.GONE);
//
//                    }

                    llSecond.addView(cvNews);


                    cvRamtosav.setVisibility(View.VISIBLE);
                    cvSarkariYojana.setVisibility(View.VISIBLE);
                    cvNokariMahiti.setVisibility(View.VISIBLE);

                    llLast.addView(cvRamtosav);
                    llLast.addView(cvSarkariYojana);
                    llLast.addView(cvNokariMahiti);
                    llLast.addView(cvDownloadForm);
                    break;
                case "2":
//                    llTop.addView(cvMemberReg);
//                    llTop.addView(cvMemberInfo);
//                    llTop.addView(cvNews);
//                    cvAddBusiness.setVisibility(View.GONE);
//                    llTop.addView(cvDownloadForm);
//                    llSecond.addView(cvSendMsg);
//                    llSecond.addView(cvFamilymember);
//                    cvKhetiMahiti.setVisibility(View.VISIBLE);
//                    cvSarkariYojana.setVisibility(View.VISIBLE);
//                    cvNokariMahiti.setVisibility(View.VISIBLE);
//                    llSecond.addView(cvKhetiMahiti);
//                    llSecond.addView(cvSarkariYojana);
//                    llSecond.addView(cvNokariMahiti);

                    // Any verified member can view directory of com.bargor.samaj

                    llTop.addView(cvMemberReg);
                    llTop.addView(cvFamilymember);
                    llTop.addView(cvMemberInfo);

                    llTop.addView(cvDirectory);
                    llSecond.addView(cvAddBusiness);
                    llSecond.addView(cvBirthDay);
                    llSecond.addView(cvSendMsg);

                    cvAddBusiness.setVisibility(View.VISIBLE);
//                    if (gor.equals("2")) {
//                        cvAddBusiness.setVisibility(View.VISIBLE);
//
//                    } else {
//                        cvAddBusiness.setVisibility(View.GONE);
//
//                    }

                    llSecond.addView(cvNews);


                    cvRamtosav.setVisibility(View.VISIBLE);
                    cvSarkariYojana.setVisibility(View.VISIBLE);
                    cvNokariMahiti.setVisibility(View.VISIBLE);

                    llLast.addView(cvRamtosav);
                    llLast.addView(cvSarkariYojana);
                    llLast.addView(cvNokariMahiti);
                    llLast.addView(cvDownloadForm);


                    break;
                default:
//                    llTop.addView(cvMemberReg);
//                    llTop.addView(cvMemberInfo);
//                    llTop.addView(cvNews);
//
//                    llTop.addView(cvDownloadForm);
//                    llSecond.addView(cvSendMsg);
//                    llSecond.addView(cvFamilymember);
//
//                    cvKhetiMahiti.setVisibility(View.VISIBLE);
//                    cvSarkariYojana.setVisibility(View.VISIBLE);
//                    cvNokariMahiti.setVisibility(View.VISIBLE);
//
//                    cvAddBusiness.setVisibility(View.GONE);
//                    if (gor.equals("2")) {
//                        llSecond.addView(cvRequest);
//                    }

                    // Any ver

            }

        }


        cvFamilymember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FamilyMemberList.class);
                startActivity(intent);
            }
        });

        cvMemberReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gor.equals("5")) {
                    if (group_count.isEmpty()) {
                        Intent intent = new Intent(getActivity(), MemberRegistrationActivity.class);
                        startActivity(intent);
                    } else {
                        if (Integer.parseInt(group_count) <= Integer.parseInt(family_count)) {
                            Intent intent = new Intent(getActivity(), MemberRegistrationActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "You have already added maximum member", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Intent intent = new Intent(getActivity(), MemberRegistrationActivity.class);
                    startActivity(intent);
                }


            }
        });

        cvDownloadForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DownLoadFormActivity.class);
//                startActivity(intent);
            }
        });

        cvMemberInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchMemberActivity.class);
                startActivity(intent);
            }
        });

        cvBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HappyBirthdayActivity.class);
                startActivity(intent);
            }
        });

        cvSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendMsgActivity.class);
                startActivity(intent);
            }
        });

        cvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LatestNewsActivity.class);
                startActivity(intent);
            }
        });

        cvDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DirectoryDashboardActivity.class);
                startActivity(intent);
            }
        });

        cvAddBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBusinessActivity.class);
                startActivity(intent);
            }
        });

        cvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isReg.equals("0")) {
                    showAlertForAppy();
                } else if (isReg.equals("1")) {
                } else if (isReg.equals("2")) {
                    Toast.makeText(getActivity(), "Your request is under process..Please contact admin", Toast.LENGTH_SHORT).show();

                }

            }
        });


        cvRamtosav.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), RamatActivity.class));

        });


        tvVillageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
//                intent.putExtra("index", 1);
//                startActivity(intent);
            }
        });

        tvKarobariInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
//                intent.putExtra("index", 2);
//                startActivity(intent);
            }
        });

        tvSharafiMandli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
//                intent.putExtra("index", 5);
//                startActivity(intent);
            }
        });

        tvUtkarsMandli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
//                intent.putExtra("index", 6);
//                startActivity(intent);
            }
        });

        tvWomenClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
//                intent.putExtra("index", 3);
//                startActivity(intent);
            }
        });
        tvYuvaClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
//                intent.putExtra("index", 4);
//                startActivity(intent);
            }
        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) (context)).setTitle("Dashboard");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentPage = 0;

        handler.removeCallbacksAndMessages(null);
    }

    private void init(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getActivity(), XMENArray));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Auto start of viewpager


        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMENArray.size()) {
                    currentPage = 0;
                }

                mPager.setCurrentItem(currentPage++, true);
                if (mPager.getAdapter() != null) {
                    mPager.getAdapter().notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };

        handler.post(Update);


//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 10000);
    }

    GetBanner showBanner(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBanner.class);
    }


    ApplyForPrimium applyForPrimiumMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(ApplyForPrimium.class);
    }

    void showAlertForAppy() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Apply For Premium Member")
                .setMessage("Are you sure you want to apply?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        showProgressDialog();

                        applyForPrimium.applyPremium(id).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                if (getActivity() != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {

                                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, getActivity());
                                        editor.putString(Constants.IS_REGISTERED, response.body().isPremium()).apply();

                                        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getActivity());
                                        isReg = sharedPreferences.getString(Constants.IS_REGISTERED, null);
                                        Toast.makeText(getActivity(), "Request Sent..", Toast.LENGTH_LONG).show();
                                    }


                                }

                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                if (getActivity() != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(getActivity(), "Error occur..", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void updateData() {
        XMENArray.clear();
        currentPage = 0;

        handler.removeCallbacksAndMessages(null);
        getBanner.getBanner().enqueue(new Callback<MyBanner>() {
            @Override
            public void onResponse(Call<MyBanner> call, Response<MyBanner> response) {

                if (response.isSuccessful()) {

                    ArrayList<Banner> bannerArrayList = (ArrayList<Banner>) response.body().getBanner();

                    if (bannerArrayList != null && bannerArrayList.size() > 0) {

                        for (int i = 0; i < bannerArrayList.size(); i++) {
                            XMENArray.add(Constants.BASE_URL.concat("banner/").concat(bannerArrayList.get(i).getImage()));
                        }

                        if (view != null)
                            init(view);

                    }


                }

            }

            @Override
            public void onFailure(Call<MyBanner> call, Throwable t) {

            }
        });

    }


    interface GetBanner {

        @POST("banner/bannerlistapi/")
        Call<MyBanner> getBanner();

    }

    interface ApplyForPrimium {
        @POST("user/premiumrequestapi/")
        @FormUrlEncoded
        Call<MyRes> applyPremium(@Field("id") String id);
    }

}
