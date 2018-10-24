package com.bargor.samaj.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.AllMember;
import com.bargor.samaj.model.Memberlist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class SelectCaptainFragment extends Fragment {

    View view_main;
    EditText editText_id;
    ImageView imageView_search;
    LinearLayout linearLayout_memberInfo;
    TextView tv_id, tv_name, tv_number, tv_city, tv_noData;
    Spinner spinner_size;
    ProgressBar progressBar;
    Button button_next;
    private ArrayList<Memberlist> memberlistArrayList;
    private SearchMember searchMember;
    private String gor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getActivity());
        gor = sharedPreferences.getString(Constants.GOR, null);

        searchMember = getSearchedMember(Constants.BASE_URL);
        memberlistArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_select_captain, container, false);

        editText_id = view_main.findViewById(R.id.selectCaptain_et_id);
        imageView_search = view_main.findViewById(R.id.selectCaptain_iv_search);
        linearLayout_memberInfo = view_main.findViewById(R.id.selectCaptain_ll_memberInfo);
        tv_id = view_main.findViewById(R.id.selectCaptain_tv_id);
        tv_name = view_main.findViewById(R.id.selectCaptain_tv_name);
        tv_number = view_main.findViewById(R.id.selectCaptain_tv_number);
        tv_city = view_main.findViewById(R.id.selectCaptain_tv_city);
        spinner_size = view_main.findViewById(R.id.selectCaptain_spinner_size);
        tv_noData = view_main.findViewById(R.id.selectCaptain_tv_noData);
        progressBar = view_main.findViewById(R.id.selectCaptain_progressbar);
        button_next = view_main.findViewById(R.id.selectCaptain_btn_next);


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editText_id.getText().toString().trim().isEmpty()) {
                    searchMemberData();
                }


            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (memberlistArrayList.size() > 0) {
                    Fragment fragment = new AddTeamMemberFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", memberlistArrayList.get(0));
                    fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .add(R.id.content_activity_ramat, fragment)
                            .addToBackStack(null)
                            .hide(SelectCaptainFragment.this).commit();
                }


            }
        });

        return view_main;
    }

    private void searchMemberData() {

        // API call here

        progressBar.setVisibility(View.VISIBLE);

        searchMember.getMemberDetail("1", editText_id.getText().toString().trim(), gor)
                .enqueue(new Callback<AllMember>() {
                    @Override
                    public void onResponse(Call<AllMember> call, Response<AllMember> response) {

                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            memberlistArrayList = (ArrayList<Memberlist>) response.body().getMemberlist();

                            if (memberlistArrayList.size() > 0) {

                                tv_id.setText(memberlistArrayList.get(0).getId());
                                tv_name.setText(memberlistArrayList.get(0).getName());
                                tv_number.setText(memberlistArrayList.get(0).getMobile());
                                tv_city.setText(memberlistArrayList.get(0).getCity());


                            } else {
                                tv_id.setText(" ");
                                tv_name.setText(" ");
                                tv_number.setText(" ");
                                tv_city.setText("  ");
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<AllMember> call, Throwable t) {

                        progressBar.setVisibility(View.GONE);

                    }
                });


    }

    private void testMeth() {

    }

    SearchMember getSearchedMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SearchMember.class);
    }

    interface SearchMember {

        @POST("member/searchdetailsapi/")
        @FormUrlEncoded()
        Call<AllMember> getMemberDetail(@Field("type") String type, @Field("search") String search, @Field("gor") String gor);


    }
}
