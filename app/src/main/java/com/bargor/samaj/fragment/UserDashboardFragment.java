package com.bargor.samaj.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import com.bargor.samaj.view.HomeActivity;
import com.bargor.samaj.view.LatestNewsActivity;
import com.bargor.samaj.view.MemberRegistrationActivity;
import com.bargor.samaj.view.OnRefreshData;
import com.bargor.samaj.view.SendMsgActivity;
import com.bargor.samaj.view.VillageDetailActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;


public class UserDashboardFragment extends Fragment implements OnRefreshData {

    private long mLastClickTime = 0;
    final Handler handler = new Handler();
    private static final int REQ_CODE = 12;
    private  ViewPager mPager;
    private  int currentPage = 0;
    private Context context;
    private CardView cvMemberReg;
    private CardView cvMemberInfo;
    private CardView cvSocialSchema;
    private CardView cvDownloadForm;
    private CardView cvSendMsg;
    private CardView cvBirthDay;
    private CardView cvAddFamilyMember;
    private CardView cvNews;
    private CardView cvKhetiMahiti;
    private CardView cvSarkariYojana;
    private CardView cvNokariMahiti;
    private LinearLayout llFirstrow;
    private LinearLayout llSecondrow;
    private LinearLayout llthirdrow;
    private String id;
    private String user_name;
    private String name;
    private boolean isLogin;
    private String isReg;
    private boolean isSSy;
    private String role;
    private String gor;
    private CardView cvDirectory;
    private ArrayList<String> XMENArray = new ArrayList<String>();
    private GetBanner getBanner;
    private View view;
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
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        XMENArray.add(R.drawable.banner1);
//        XMENArray.add(R.drawable.banner2);
//        XMENArray.add(R.drawable.banner3);
//        XMENArray.add(R.drawable.banner4new);

        getBanner = showBanner(Constants.BASE_URL);
        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getActivity());
        id = sharedPreferences.getString(Constants.ID, null);
        user_name = sharedPreferences.getString(Constants.USER_NAME, null);
        name = sharedPreferences.getString(Constants.NAME, null);
        isLogin = sharedPreferences.getBoolean(Constants.IS_LOGIN, false);
        role = sharedPreferences.getString(Constants.ROLE, null);
        isReg = sharedPreferences.getString(Constants.IS_REGISTERED, null);
        isSSy = sharedPreferences.getBoolean(Constants.IS_SSY, false);
        family_count = sharedPreferences.getString("family_count", null);
        group_count = sharedPreferences.getString("gr_count", "");
        gor = sharedPreferences.getString(Constants.GOR, null);
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
        return inflater.inflate(R.layout.dashboard_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        cvDownloadForm = (CardView) view.findViewById(R.id.cvDownloadForm);
        cvSendMsg = (CardView) view.findViewById(R.id.cvSendMsg);
        cvNews = (CardView) view.findViewById(R.id.cvNews);
        cvAddFamilyMember = view.findViewById(R.id.cvAddFamilyMember);
        cvKhetiMahiti = view.findViewById(R.id.cvKhetiMahiti);
        cvSarkariYojana = view.findViewById(R.id.cvSarkariYojana);
        cvNokariMahiti = view.findViewById(R.id.cvNokariMahiti);
        tvVillageInfo = view.findViewById(R.id.tvVillageInfo);

        llFirstrow = (LinearLayout) view.findViewById(R.id.llTop);
        llSecondrow = view.findViewById(R.id.llSecond);
        llthirdrow = view.findViewById(R.id.llLast);

        llFirstrow.removeAllViews();
        llSecondrow.removeAllViews();
        llthirdrow.removeAllViews();


        llFirstrow.addView(cvDownloadForm);
        llFirstrow.addView(cvSendMsg);
        llFirstrow.addView(cvNews);

        if (gor != null && gor.equals("5") && family_count != null) {
            cvAddFamilyMember.setVisibility(View.VISIBLE);
            llFirstrow.addView(cvAddFamilyMember);
        }

        tvKarobariInfo = (TextView) view.findViewById(R.id.tvKarobariInfo);
        tvWomenClub = (TextView) view.findViewById(R.id.tvWomenClub);
        tvYuvaClub = (TextView) view.findViewById(R.id.tvYuvaClub);
        tvSharafiMandli = (TextView) view.findViewById(R.id.tvSharafiMandli);
        tvUtkarsMandli = (TextView) view.findViewById(R.id.tvUtkarsMandli);


        cvDownloadForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), DownLoadFormActivity.class);
//                startActivity(intent);
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

        cvAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getActivity());
                group_count = sharedPreferences.getString("gr_count", "");
                if(group_count.isEmpty()){
                    Intent intent = new Intent(getActivity(), MemberRegistrationActivity.class);
                    startActivity(intent);
                }
                else{
                    if (Integer.parseInt(group_count) <= Integer.parseInt(family_count)) {
                        Intent intent = new Intent(getActivity(), MemberRegistrationActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You have already added maximum member", Toast.LENGTH_LONG).show();
                    }
                }




            }
        });

        tvVillageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
            }
        });

        tvKarobariInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
                intent.putExtra("index", 2);
                startActivity(intent);
            }
        });

        tvSharafiMandli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
                intent.putExtra("index", 5);
                startActivity(intent);
            }
        });

        tvUtkarsMandli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
                intent.putExtra("index", 6);
                startActivity(intent);
            }
        });

        tvWomenClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
                intent.putExtra("index", 3);
                startActivity(intent);
            }
        });
        tvYuvaClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VillageDetailActivity.class);
                intent.putExtra("index", 4);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) (context)).setTitle("Dashboard");
    }


    @Override
    public void onResume() {
        super.onResume();

        llFirstrow.removeAllViews();
        llSecondrow.removeAllViews();
        llthirdrow.removeAllViews();

        llFirstrow.addView(cvDownloadForm);
        llFirstrow.addView(cvSendMsg);
        llFirstrow.addView(cvNews);

        if (gor != null && gor.equals("5") && family_count != null) {
            cvAddFamilyMember.setVisibility(View.VISIBLE);
            llFirstrow.addView(cvAddFamilyMember);
        }
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
//        }, 1000, 5000);
    }

    GetBanner showBanner(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBanner.class);
    }

    @Override
    public void updateData() {
        XMENArray.clear();
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
}
