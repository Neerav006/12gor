package com.bargor.samaj.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.AllMember;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.BusinessCategory;
import com.bargor.samaj.model.Memberlist;
import com.bargor.samaj.model.VillageList;
import com.bargor.samaj.model.VillagelistOF;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class SearchMemberActivity extends AppCompatActivity {
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private Button btnSearch;
    private RecyclerView rvList;
    private EditText edtSearch;
    private CustomAdapter customAdapter;
    private ArrayList<Memberlist> memberlistArrayList;
    private SearchMember searchMember;
    private Spinner spType;
    private ProgressBar progressBar;
    private String role;
    private Spinner spvillagelist;
    private GetVillageName getVillageName;
    private ArrayList<VillageList> villageLists;
    private String gor;
    private ArrayList<BusCategoryList> busCategoryLists;
    private ArrayList<BusCategoryList> copybusCategoryLists;
    private GetBusinessCategory getBusinessCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_member);
        getBusinessCategory = getCategoryByBusiness(Constants.BASE_URL);
        searchMember = getSearchedMember(Constants.BASE_URL);
        memberlistArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, SearchMemberActivity.this);
        role = sharedPreferences.getString(Constants.ROLE, null);
        gor = sharedPreferences.getString(Constants.GOR, null);
        getVillageName = getVillageNameAPI(Constants.BASE_URL);
        villageLists = new ArrayList<>();
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        rvList = (RecyclerView) findViewById(R.id.selectGame_rvList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(SearchMemberActivity.this, DividerItemDecoration.VERTICAL);
        rvList.setLayoutManager(new LinearLayoutManager(SearchMemberActivity.this));
        rvList.addItemDecoration(dividerItemDecoration);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        spType = (Spinner) findViewById(R.id.spType);
        spvillagelist = findViewById(R.id.spVillageList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        busCategoryLists = new ArrayList<>();
        copybusCategoryLists = new ArrayList<>();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getBusinessCategory.getBusinessCatList().enqueue(new Callback<BusinessCategory>() {
            @Override
            public void onResponse(Call<BusinessCategory> call, Response<BusinessCategory> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    busCategoryLists = (ArrayList<BusCategoryList>) response.body().getCategoryList();

                }


            }


            @Override
            public void onFailure(Call<BusinessCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


        getVillageName.villageList(gor).enqueue(new Callback<VillagelistOF>() {
            @Override
            public void onResponse(Call<VillagelistOF> call, Response<VillagelistOF> response) {

                Utils.hideSoftKeyBoard(SearchMemberActivity.this.getCurrentFocus(), SearchMemberActivity.this);


                if (response.isSuccessful()) {
                    if (response.body().getVillageList() != null && response.body().getVillageList().size() > 0) {

                        villageLists = (ArrayList<VillageList>) response.body().getVillageList();
                        spvillagelist.setAdapter(new MyCustomAdapter(SearchMemberActivity.this,
                                R.layout.row_village_list, (ArrayList<VillageList>) response.body().getVillageList()));

                    }

                }
            }

            @Override
            public void onFailure(Call<VillagelistOF> call, Throwable t) {
                Utils.hideSoftKeyBoard(SearchMemberActivity.this.getCurrentFocus(), SearchMemberActivity.this);

            }
        });


        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideSoftKeyBoard(SearchMemberActivity.this.getCurrentFocus(), SearchMemberActivity.this);


                switch (position) {
                    case 0:
                        spvillagelist.setVisibility(View.GONE);
                        edtSearch.getText().clear();
                        break;

                    case 1:
                        spvillagelist.setVisibility(View.VISIBLE);
                        edtSearch.getText().clear();
                        if (villageLists.size() > 0) {
                            searchMember.getMemberDetail("3", villageLists.get(0).getId(), gor)
                                    .enqueue(new Callback<AllMember>() {
                                        @Override
                                        public void onResponse(Call<AllMember> call, Response<AllMember> response) {
                                            btnSearch.setEnabled(true);
                                            progressBar.setVisibility(View.GONE);
                                            if (response.isSuccessful()) {
                                                memberlistArrayList = (ArrayList<Memberlist>) response.body().getMemberlist();

                                                if (memberlistArrayList.size() > 0) {
                                                    customAdapter = new CustomAdapter(memberlistArrayList);
                                                    rvList.setAdapter(customAdapter);

                                                } else {
                                                    customAdapter = new CustomAdapter(memberlistArrayList);
                                                    rvList.setAdapter(customAdapter);
                                                }
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<AllMember> call, Throwable t) {
                                            btnSearch.setEnabled(true);
                                            progressBar.setVisibility(View.GONE);

                                        }
                                    });
                        }

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spvillagelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Utils.hideSoftKeyBoard(SearchMemberActivity.this.getCurrentFocus(), SearchMemberActivity.this);


                edtSearch.setText(((VillageList) parent.getItemAtPosition(position)).getName());

                searchMember.getMemberDetail("3", ((VillageList) parent.getItemAtPosition(position)).getName().trim(), gor)
                        .enqueue(new Callback<AllMember>() {
                            @Override
                            public void onResponse(Call<AllMember> call, Response<AllMember> response) {
                                btnSearch.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                if (response.isSuccessful()) {
                                    memberlistArrayList = (ArrayList<Memberlist>) response.body().getMemberlist();

                                    if (memberlistArrayList.size() > 0) {
                                        customAdapter = new CustomAdapter(memberlistArrayList);
                                        rvList.setAdapter(customAdapter);

                                    } else {
                                        customAdapter = new CustomAdapter(memberlistArrayList);
                                        rvList.setAdapter(customAdapter);
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<AllMember> call, Throwable t) {
                                btnSearch.setEnabled(true);
                                progressBar.setVisibility(View.GONE);

                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideSoftKeyBoard(SearchMemberActivity.this.getCurrentFocus(), SearchMemberActivity.this);

                if (edtSearch.getText().toString().trim().isEmpty()) {

                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    btnSearch.setEnabled(false);

                    searchMember.getMemberDetail(String.valueOf(spType.getSelectedItemPosition() == 0 ? 2 : 3), edtSearch.getText().toString().trim(), gor)
                            .enqueue(new Callback<AllMember>() {
                                @Override
                                public void onResponse(Call<AllMember> call, Response<AllMember> response) {
                                    btnSearch.setEnabled(true);
                                    progressBar.setVisibility(View.GONE);
                                    if (response.isSuccessful()) {
                                        memberlistArrayList = (ArrayList<Memberlist>) response.body().getMemberlist();

                                        if (memberlistArrayList.size() > 0) {
                                            customAdapter = new CustomAdapter(memberlistArrayList);
                                            rvList.setAdapter(customAdapter);

                                        } else {
                                            customAdapter = new CustomAdapter(memberlistArrayList);
                                            rvList.setAdapter(customAdapter);
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<AllMember> call, Throwable t) {
                                    btnSearch.setEnabled(true);
                                    progressBar.setVisibility(View.GONE);

                                }
                            });


                }
            }
        });

    }

    SearchMember getSearchedMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SearchMember.class);
    }

    GetVillageName getVillageNameAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetVillageName.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), SearchMemberActivity.this);
    }

    GetBusinessCategory getCategoryByBusiness(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessCategory.class);
    }

    interface SearchMember {

        @POST("member/searchdetailsapi/")
        @FormUrlEncoded()
        Call<AllMember> getMemberDetail(@Field("type") String type, @Field("search") String search, @Field("gor") String gor);


    }

    interface GetVillageName {
        @POST("home/villagelistapi/")
        @FormUrlEncoded
        Call<VillagelistOF> villageList(@Field("gor") String gor);
    }

    interface GetBusinessCategory {
        @POST("home/jobtypeapi/")
        Call<BusinessCategory> getBusinessCatList();
    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Memberlist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        CustomAdapter(ArrayList<Memberlist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_member_info, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvName.setText(mDataSet.get(position).getName());
            viewHolder.tvMemNo.setText(mDataSet.get(position).getId());
            viewHolder.tvCity.setText(mDataSet.get(position).getCity());
            viewHolder.tvDOB.setText("Birth Day:- ".concat(Utils.getDDMMYYYY(mDataSet.get(position).getDob())));

        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvName;
            private final TextView tvCity;
            private final TextView tvMemNo;
            private final ImageView ivView;
            private final ImageView ivEdit;
            private final ImageView ivEditProfile;
            private final TextView tvDOB;
            private final TextView tvStudy;


            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.


                tvName = (TextView) v.findViewById(R.id.tvMemberName);
                tvCity = (TextView) v.findViewById(R.id.tvCity);
                tvMemNo = (TextView) v.findViewById(R.id.tvMemberCode);
                ivView = (ImageView) v.findViewById(R.id.ivView);
                ivEdit = (ImageView) v.findViewById(R.id.ivEdit);
                ivEditProfile = (ImageView) v.findViewById(R.id.ivEditProfile);
                tvDOB = v.findViewById(R.id.tvDOB);
                tvStudy = v.findViewById(R.id.tvStudy);

                ivEdit.setVisibility(View.GONE);
                ivEditProfile.setVisibility(View.VISIBLE);

                ivView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchMemberActivity.this, MemberDetailActivity.class);
                        intent.putExtra("detail", mDataSet.get(getAdapterPosition()));
                        intent.putParcelableArrayListExtra("bus", busCategoryLists);
                        startActivity(intent);
                    }
                });

                ivEditProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchMemberActivity.this, GroupViseFamily.class);
                        intent.putExtra("family_id", mDataSet.get(getAdapterPosition()).getGroupNo());
                        intent.putParcelableArrayListExtra("bus",busCategoryLists);
                        startActivity(intent);
                    }
                });

//                if (role != null && role.equals("2")) {
//                    ivEdit.setVisibility(View.VISIBLE);
//                    ivEditProfile.setVisibility(View.VISIBLE);
//                    ivEdit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            if (role.equals("2")) {
//                                Intent intent = new Intent(SearchMemberActivity.this, EditDetailActivity.class);
//                                intent.putExtra("id", mDataSet.get(getAdapterPosition()).getId());
//                                intent.putExtra("from", SearchMemberActivity.class.getSimpleName());
//                                startActivity(intent);
//                            }
//
//                        }
//                    });
//
//                    ivEditProfile.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (role.equals("2")) {
//                                Intent intent = new Intent(SearchMemberActivity.this, EditImageActivity.class);
//                                intent.putExtra("id", mDataSet.get(getAdapterPosition()).getId());
//                                intent.putExtra("from", SearchMemberActivity.class.getSimpleName());
//                                startActivity(intent);
//                            }
//                        }
//                    });
//                } else {
//                    ivEdit.setVisibility(View.GONE);
//                    ivEditProfile.setVisibility(View.GONE);
//                }


            }


        }
    }

    public class MyCustomAdapter extends ArrayAdapter<VillageList> {

        private ArrayList<VillageList> objects;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<VillageList> objects) {
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
}
