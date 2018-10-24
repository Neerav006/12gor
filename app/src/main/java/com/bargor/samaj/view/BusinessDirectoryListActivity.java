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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.BusinessCategory;
import com.bargor.samaj.model.BusinessListAPI;
import com.bargor.samaj.model.Businesslist;
import com.bargor.samaj.model.Category;
import com.bargor.samaj.model.Categorylist;
import com.facebook.drawee.view.SimpleDraweeView;
import com.bargor.samaj.model.SubCat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class BusinessDirectoryListActivity extends AppCompatActivity {

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private Spinner spType;
    private ProgressBar progressBar;
    private RecyclerView rvList;
    private GetCategory getCategory;
    private GetBusinessDetail getBusinessDetail;
    private ArrayList<Categorylist> categorylists;
    private MyCustomAdapter myCustomAdapter;
    private ArrayList<Businesslist> businesslists;
    private CustomAdapter customAdapter;
    private String gor;
    private String id;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private EditText edtSearchByBusinessName;
    private EditText edtSearchByDesignation;
    private Button btnSearch;
    private Spinner spSubType;
    private GetBusinessCategory getBusinessCategory;
    private Spinner spCategory;
    private ArrayList<BusCategoryList> busCategoryLists;
    private ArrayList<BusCategoryList> copybusCategoryLists;
    private MyCustomAdapter33 myCustomAdapter33;
    private MyCustomAdapter44 myCustomAdapter44;
    private String main_cat_id = "";
    private String sub_cat_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_directory_list);

        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, BusinessDirectoryListActivity.this);
        id = sharedPreferences.getString(Constants.ID, null);
        gor = sharedPreferences.getString(Constants.GOR, null);
        getBusinessCategory = getCategoryByBusiness(Constants.BASE_URL);
        copybusCategoryLists = new ArrayList<>();
        categorylists = new ArrayList<>();
        businesslists = new ArrayList<>();
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spType = (Spinner) findViewById(R.id.spType);
        spSubType = findViewById(R.id.spSubType);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rvList = (RecyclerView) findViewById(R.id.selectGame_rvList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(BusinessDirectoryListActivity.this, DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
        rvList.setLayoutManager(new LinearLayoutManager(BusinessDirectoryListActivity.this));
        edtSearchByBusinessName = findViewById(R.id.edtBusinessName);
        edtSearchByDesignation = findViewById(R.id.edtDesignation);
        btnSearch = findViewById(R.id.btnSearch);


        getCategory = getCategoryProfession(Constants.BASE_URL);
        getBusinessDetail = getBusinessProfile(Constants.BASE_URL);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressBar.setVisibility(View.VISIBLE);

//        getBusinessCategory.getBusinessCatList().enqueue(new Callback<BusinessCategory>() {
//            @Override
//            public void onResponse(Call<BusinessCategory> call, Response<BusinessCategory> response) {
//
//                progressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful()) {
//                    busCategoryLists = (ArrayList<BusCategoryList>) response.body().getCategoryList();
//
//                    for (int i = 0; i < busCategoryLists.size(); i++) {
//                        if (busCategoryLists.get(i).getMainCat().getId().equals("1")) {
//
//
//                        } else {
//                            copybusCategoryLists.add(busCategoryLists.get(i));
//                        }
//
//
//                    }
//
//                    if (copybusCategoryLists.size() > 0) {
//
//                        myCustomAdapter33 = new MyCustomAdapter33(BusinessDirectoryListActivity.this, R.layout.row_custom_spinner, copybusCategoryLists);
//                        spType.setAdapter(myCustomAdapter33);
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BusinessCategory> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//            }
//        });


//        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
////                main_cat_id = ((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId();
////
////                myCustomAdapter44 = new MyCustomAdapter44(BusinessDirectoryListActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat());
////                spSubType.setAdapter(myCustomAdapter44);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//        spSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                sub_cat_id = ((SubCat) parent.getItemAtPosition(position)).getId();
//
//                //
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        // filter member list


        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideSoftKeyBoard(BusinessDirectoryListActivity.this.getCurrentFocus(), BusinessDirectoryListActivity.this);

                progressBar.setVisibility(View.VISIBLE);
                if (categorylists.size() > 0) {
                    //call business detail
                    getBusinessDetail.getBusinessDetail(((Categorylist) parent.getSelectedItem()).getId(), gor
                            , edtSearchByBusinessName.getText().toString().trim(), edtSearchByDesignation.getText().toString().trim()).enqueue(new Callback<BusinessListAPI>() {
                        @Override
                        public void onResponse(Call<BusinessListAPI> call, Response<BusinessListAPI> response) {
                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {
                                businesslists = (ArrayList<Businesslist>) response.body().getBusinesslist();

                                if (businesslists.size() > 0) {

                                    customAdapter = new CustomAdapter(businesslists);
                                    rvList.setAdapter(customAdapter);

                                } else {
                                    customAdapter = new CustomAdapter(businesslists);
                                    rvList.setAdapter(customAdapter);
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<BusinessListAPI> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getCategory.getCatList().enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    categorylists = (ArrayList<Categorylist>) response.body().getCategorylist();
                    if (categorylists.size() > 0) {

                        final Categorylist categorylist = new Categorylist();
                        categorylist.setName("All");
                        categorylist.setId("0");
                        categorylists.add(0, categorylist);

                        myCustomAdapter = new MyCustomAdapter(BusinessDirectoryListActivity.this, R.layout.row_custom_spinner, categorylists);
                        spType.setAdapter(myCustomAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyBoard(BusinessDirectoryListActivity.this.getCurrentFocus(), BusinessDirectoryListActivity.this);


                progressBar.setVisibility(View.VISIBLE);

                if (categorylists.size() > 0) {
                    //call business detail
                    getBusinessDetail.getBusinessDetail(((Categorylist) spType.getSelectedItem()).getId(), gor
                            , edtSearchByBusinessName.getText().toString().trim(), edtSearchByDesignation.getText().toString().trim()).enqueue(new Callback<BusinessListAPI>() {
                        @Override
                        public void onResponse(Call<BusinessListAPI> call, Response<BusinessListAPI> response) {
                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {
                                businesslists = (ArrayList<Businesslist>) response.body().getBusinesslist();

                                if (businesslists.size() > 0) {

                                    customAdapter = new CustomAdapter(businesslists);
                                    rvList.setAdapter(customAdapter);

                                } else {
                                    customAdapter = new CustomAdapter(businesslists);
                                    rvList.setAdapter(customAdapter);
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<BusinessListAPI> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }

//                    getBusinessDetail.getBusinessDetail(gor
//                            , edtSearchByBusinessName.getText().toString().trim(), edtSearchByDesignation.getText().toString().trim(), main_cat_id, sub_cat_id).enqueue(new Callback<BusinessListAPI22>() {
//                        @Override
//                        public void onResponse(Call<BusinessListAPI22> call, Response<BusinessListAPI22> response) {
//                            progressBar.setVisibility(View.GONE);
//
//                            if (response.isSuccessful()) {
//                                businesslists = (ArrayList<com.goryuva.com.bargor.samaj.model22.Businesslist>) response.body().getBusinesslist();
//
//                                if (businesslists.size() > 0) {
//
//                                    customAdapter = new CustomAdapter(businesslists);
//                                    rvList.setAdapter(customAdapter);
//
//                                } else {
//                                    Toast.makeText(BusinessDirectoryListActivity.this, "No data found", Toast.LENGTH_SHORT).show();
//                                    customAdapter = new CustomAdapter(businesslists);
//                                    rvList.setAdapter(customAdapter);
//                                }
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<BusinessListAPI22> call, Throwable t) {
//
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    });


            }
        });

    }

    GetCategory getCategoryProfession(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategory.class);
    }

    GetBusinessDetail getBusinessProfile(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessDetail.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), BusinessDirectoryListActivity.this);
    }

    GetBusinessCategory getCategoryByBusiness(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessCategory.class);
    }


    interface GetCategory {
        @POST("directory/categorylistapi/")
        Call<Category> getCatList();
    }

    interface GetBusinessDetail {
        @POST("directory/businesslistapi/")
        @FormUrlEncoded
        Call<BusinessListAPI> getBusinessDetail(@Field("id") String id, @Field("gor") String gor
                , @Field("b_name") String businessName, @Field("d_name") String designationName
        );
    }

    interface GetBusinessCategory {
        @POST("home/jobtypeapi/")
        Call<BusinessCategory> getBusinessCatList();
    }

    public class MyCustomAdapter extends ArrayAdapter<Categorylist> {

        private ArrayList<Categorylist> categorylists;

        MyCustomAdapter(Context context, int textViewResourceId,
                        ArrayList<Categorylist> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
            this.categorylists = objects;
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
            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            label.setText(categorylists.get(position).getName());


            return row;
        }
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Businesslist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Businesslist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_business_dir, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvName.setText(mDataSet.get(position).getName());
            viewHolder.tvGraduation.setText(mDataSet.get(position).getBusinessName());
            viewHolder.tvProfession.setText(mDataSet.get(position).getStudy());
            viewHolder.ivProfile.setImageURI(VIEW_PATH.concat(mDataSet.get(position).getPhoto()));


            Glide.with(BusinessDirectoryListActivity.this)
                    .load(VIEW_PATH.concat(mDataSet.get(position).getPhoto()))
                    .apply(new RequestOptions().placeholder(R.drawable.noimage))
                    .into(viewHolder.ivProfile);

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
            private SimpleDraweeView ivProfile;
            private TextView tvName;
            private TextView tvGraduation;
            private TextView tvProfession;


            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.

                ivProfile = (SimpleDraweeView) v.findViewById(R.id.ivProfile);
                tvName = (TextView) v.findViewById(R.id.tvName);
                tvGraduation = (TextView) v.findViewById(R.id.tvGraduation);
                tvProfession = (TextView) v.findViewById(R.id.tvProfession);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(BusinessDirectoryListActivity.this, BusinessDetailActivity.class);
                        intent.putExtra("detail", mDataSet.get(getAdapterPosition()));
                        startActivity(intent);

                    }
                });

            }


        }
    }

    public class MyCustomAdapter33 extends ArrayAdapter<BusCategoryList> {

        private ArrayList<BusCategoryList> categorylists;

        MyCustomAdapter33(Context context, int textViewResourceId,
                          ArrayList<BusCategoryList> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
            this.categorylists = objects;
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
            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            label.setText(categorylists.get(position).getMainCat().getName());
            if (categorylists.get(position).getMainCat().getId().equals("1")) {
                row.setVisibility(View.GONE);

            } else {
                row.setVisibility(View.VISIBLE);
            }

            return row;
        }
    }

    public class MyCustomAdapter44 extends ArrayAdapter<SubCat> {

        private ArrayList<SubCat> categorylists;

        MyCustomAdapter44(Context context, int textViewResourceId,
                          ArrayList<SubCat> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
            this.categorylists = objects;
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
            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            label.setText(categorylists.get(position).getSubName());


            return row;
        }
    }


}


