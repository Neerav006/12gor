package com.bargor.samaj.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.BusinessCategory;
import com.bargor.samaj.model.Category;
import com.bargor.samaj.model.Categorylist;
import com.bargor.samaj.model.MainCat;
import com.bargor.samaj.model.Marriagelist;
import com.bargor.samaj.model.SubCat;
import com.bargor.samaj.model.YuvatiInfo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class MatrimonialActivity extends AppCompatActivity implements OnFilter {

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private Spinner spType;
    private Spinner spProfession;
    private Spinner spFrom;
    private Spinner spTo;
    private Button btnSearch;
    private RecyclerView rvList;
    private GetYuvatiInfo getYuvatiInfo;
    private ProgressBar progressBar;
    private ArrayList<Marriagelist> marriagelists;
    private ArrayList<Categorylist> categorylists;
    private CustomAdapter customAdapter;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private GetCategory getCategory;
    private MyCustomAdapter myCustomAdapter;
    private EditText edtVillageName;
    private Spinner spSubType;
    private Spinner spBusinessType;
    private ArrayList<BusCategoryList> busCategoryLists;
    private ArrayList<BusCategoryList> copybusCategoryLists;
    private GetBusinessCategory getBusinessCategory;
    private String main_id="";
    private String sub_id="";
    private String study_id="";
    private Spinner spCategory;
    private Spinner spSubCategory;
    private Spinner spStudy;
    private MyCustomAdapter33 myCustomAdapter33;
    private MyCustomAdapter44 myCustomAdapter44;
    private MyCustomAdapter55 myCustomAdapter55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimonial);
        marriagelists = new ArrayList<>();
        getBusinessCategory = getCategoryByBusiness(Constants.BASE_URL);
        busCategoryLists = new ArrayList<>();
        copybusCategoryLists = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        categorylists = new ArrayList<>();
        getYuvatiInfo = getMyYuvati(Constants.BASE_URL);
        getCategory = getCategoryProfession(Constants.BASE_URL);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spType = (Spinner) findViewById(R.id.spType);
        spFrom = (Spinner) findViewById(R.id.spFrom);
        spTo = (Spinner) findViewById(R.id.spTo);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        rvList = (RecyclerView) findViewById(R.id.selectGame_rvList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MatrimonialActivity.this, DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
        rvList.setLayoutManager(new LinearLayoutManager(MatrimonialActivity.this));
        edtVillageName = findViewById(R.id.edtVillageName);

        spSubType = findViewById(R.id.spSubType);
        spCategory = findViewById(R.id.spMainCat);
        spSubCategory = findViewById(R.id.spSubCat);
        spStudy = findViewById(R.id.spCategoryStudy);


        spTo.setSelection(getResources().getStringArray(R.array.age).length - 1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getCategory.getCatList().enqueue(new Callback<Category>() {
//            @Override
//            public void onResponse(Call<Category> call, Response<Category> response) {
//                progressBar.setVisibility(View.GONE);
//                if (response.isSuccessful()) {
//
//                    categorylists = (ArrayList<Categorylist>) response.body().getCategorylist();
//                    if (categorylists.size() > 0) {
//
//                        final Categorylist categorylist = new Categorylist();
//                        categorylist.setName("All");
//                        categorylist.setId("0");
//                        categorylists.add(0, categorylist);
//
//                        myCustomAdapter = new MyCustomAdapter(MatrimonialActivity.this, R.layout.row_custom_spinner, categorylists);
//                        spProfession.setAdapter(myCustomAdapter);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Category> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//            }
//        });


        progressBar.setVisibility(View.VISIBLE);

        getBusinessCategory.getBusinessCatList().enqueue(new Callback<BusinessCategory>() {
            @Override
            public void onResponse(Call<BusinessCategory> call, Response<BusinessCategory> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    busCategoryLists = (ArrayList<BusCategoryList>) response.body().getCategoryList();

                    final BusCategoryList busCategoryList = new BusCategoryList();
                    final MainCat mainCat = new MainCat();
                    final SubCat subCat = new SubCat();
                    subCat.setId("-2");
                    subCat.setSubName("Any");
                    mainCat.setName("Any");
                    mainCat.setId("-2");

                    busCategoryList.setMainCat(mainCat);
                    final List<SubCat> subCatList = new ArrayList<>();
                    subCatList.add(subCat);
                    busCategoryList.setMainCat(mainCat);
                    busCategoryList.setSubCat(subCatList);
                    busCategoryLists.add(0, busCategoryList);


                    for (int i = 0; i < busCategoryLists.size(); i++) {

                        if (!busCategoryLists.get(i).getMainCat().getId().equals("-2")) {
                            final SubCat subCat1 = new SubCat();
                            subCat1.setSubName("Any");
                            subCat1.setId("-2");
                            busCategoryLists.get(i).getSubCat().add(0, subCat1);
                        }


                        if (busCategoryLists.get(i).getMainCat().getId().equals("1")) {

                            myCustomAdapter55 = new MyCustomAdapter55(MatrimonialActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) busCategoryLists.get(i).getSubCat());
                            spStudy.setAdapter(myCustomAdapter55);


                        } else {
                            copybusCategoryLists.add(busCategoryLists.get(i));
                        }


                    }

                    if (copybusCategoryLists.size() > 0) {


                        myCustomAdapter33 = new MyCustomAdapter33(MatrimonialActivity.this, R.layout.row_custom_spinner, copybusCategoryLists);
                        spCategory.setAdapter(myCustomAdapter33);
                    }

                }

            }

            @Override
            public void onFailure(Call<BusinessCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


        progressBar.setVisibility(View.VISIBLE);

        getYuvatiInfo.getYuvatiInfo(String.valueOf(spType.getSelectedItemPosition()), spFrom.getSelectedItem().toString(), spTo.getSelectedItem().toString(), edtVillageName.getText().toString().trim(), "", "", "").enqueue(new Callback<YuvatiInfo>() {
            @Override
            public void onResponse(Call<YuvatiInfo> call, Response<YuvatiInfo> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    marriagelists = (ArrayList<Marriagelist>) response.body().getMarriagelist();

                    customAdapter = new CustomAdapter(marriagelists);
                    rvList.setAdapter(customAdapter);
                }


            }

            @Override
            public void onFailure(Call<YuvatiInfo> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                getYuvatiInfo.getYuvatiInfo(String.valueOf(spType.getSelectedItemPosition()), spFrom.getSelectedItem().toString(), spTo.getSelectedItem().toString(), edtVillageName.getText().toString().trim(), main_id, sub_id, study_id).enqueue(new Callback<YuvatiInfo>() {
                    @Override
                    public void onResponse(Call<YuvatiInfo> call, Response<YuvatiInfo> response) {
                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()) {


                            marriagelists = (ArrayList<Marriagelist>) response.body().getMarriagelist();
                            if (marriagelists.size() > 0) {
                                customAdapter = new CustomAdapter(marriagelists);
                                rvList.setAdapter(customAdapter);
                            } else {
                                customAdapter = new CustomAdapter(marriagelists);
                                rvList.setAdapter(customAdapter);
                                Toast.makeText(MatrimonialActivity.this, "No Record found", Toast.LENGTH_SHORT).show();
                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<YuvatiInfo> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MatrimonialActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myCustomAdapter44 = new MyCustomAdapter44(MatrimonialActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat());
                spSubCategory.setAdapter(myCustomAdapter44);

                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId().equals("-2")) {
                    main_id = "";
                } else {
                    main_id = ((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (((SubCat) parent.getItemAtPosition(position)).getId().equals("-2")) {
                    sub_id = "";
                } else {
                    sub_id = ((SubCat) parent.getItemAtPosition(position)).getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spStudy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                if (((SubCat) parent.getItemAtPosition(position)).getId().equals("-2")) {
//                    study_id = " ";
//                } else {
//                    study_id = ((SubCat) parent.getItemAtPosition(position)).getId();
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }

    GetYuvatiInfo getMyYuvati(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetYuvatiInfo.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), MatrimonialActivity.this);
    }

    GetCategory getCategoryProfession(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategory.class);
    }

    GetBusinessCategory getCategoryByBusiness(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessCategory.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_filter) {

//            if (busCategoryLists.size() > 0) {
//
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("data", busCategoryLists);
//                MyCustomDialogFragment fragment = new MyCustomDialogFragment();
//                fragment.setArguments(bundle);
//                fragment.show(getFragmentManager(), "filter");
//
//
//            }

            progressBar.setVisibility(View.VISIBLE);

            getYuvatiInfo.getYuvatiInfo(String.valueOf(spType.getSelectedItemPosition()), spFrom.getSelectedItem().toString(), spTo.getSelectedItem().toString(), edtVillageName.getText().toString().trim(), "", "", "").enqueue(new Callback<YuvatiInfo>() {
                @Override
                public void onResponse(Call<YuvatiInfo> call, Response<YuvatiInfo> response) {
                    progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        marriagelists = (ArrayList<Marriagelist>) response.body().getMarriagelist();
                        customAdapter = new CustomAdapter(marriagelists);
                        rvList.setAdapter(customAdapter);
                    }


                }

                @Override
                public void onFailure(Call<YuvatiInfo> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onApply(String main_id, String sub_id, String study_id) {

        this.main_id = main_id;
        this.sub_id = sub_id;
        this.study_id = study_id;

        progressBar.setVisibility(View.VISIBLE);

        getYuvatiInfo.getYuvatiInfo(String.valueOf(spType.getSelectedItemPosition()), spFrom.getSelectedItem().toString(), spTo.getSelectedItem().toString(), edtVillageName.getText().toString().trim()
                , main_id, sub_id, study_id
        ).enqueue(new Callback<YuvatiInfo>() {
            @Override
            public void onResponse(Call<YuvatiInfo> call, Response<YuvatiInfo> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    marriagelists = (ArrayList<Marriagelist>) response.body().getMarriagelist();
                    customAdapter = new CustomAdapter(marriagelists);
                    rvList.setAdapter(customAdapter);
                }


            }

            @Override
            public void onFailure(Call<YuvatiInfo> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onClear(String main_id, String sub_id, String study_id) {

        this.main_id = main_id;
        this.sub_id = sub_id;
        this.study_id = study_id;

        progressBar.setVisibility(View.VISIBLE);

        getYuvatiInfo.getYuvatiInfo(String.valueOf(spType.getSelectedItemPosition()), spFrom.getSelectedItem().toString(), spTo.getSelectedItem().toString(), edtVillageName.getText().toString().trim()
                , "", "", ""
        ).enqueue(new Callback<YuvatiInfo>() {
            @Override
            public void onResponse(Call<YuvatiInfo> call, Response<YuvatiInfo> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    marriagelists = (ArrayList<Marriagelist>) response.body().getMarriagelist();
                    customAdapter = new CustomAdapter(marriagelists);
                    rvList.setAdapter(customAdapter);
                }


            }

            @Override
            public void onFailure(Call<YuvatiInfo> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    interface GetYuvatiInfo {
        @POST("directory/marrigelistapi/")
        @FormUrlEncoded
        Call<YuvatiInfo> getYuvatiInfo(@Field("sex") String sex, @Field("from") String from, @Field("to") String to, @Field("pre") String pre,

                                       @Field("main_id") String main_id, @Field("sub_id") String sub_id, @Field("study_id") String study_id


        );

    }

    interface GetCategory {
        @POST("directory/categorylistapi/")
        Call<Category> getCatList();
    }

    interface GetBusinessCategory {
        @POST("home/jobtypeapi/")
        Call<BusinessCategory> getBusinessCatList();
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Marriagelist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        CustomAdapter(ArrayList<Marriagelist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_matrimonial, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element

            Glide.with(MatrimonialActivity.this)
                    .load(VIEW_PATH.concat(mDataSet.get(position).getProfile()))
                    .apply(new RequestOptions().placeholder(R.drawable.noimage))
                    .into(viewHolder.ivProfile);


            viewHolder.tvAddr.setText(mDataSet.get(position).getCity());
            viewHolder.tvDOB.setText(Utils.getDDMMYYYY(mDataSet.get(position).getDob()));
            viewHolder.tvName.setText(mDataSet.get(position).getName());

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
            private final SimpleDraweeView ivProfile;
            private final TextView tvDOB;
            private final TextView tvAddr;

            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                tvName = (TextView) v.findViewById(R.id.tvName);
                ivProfile = (SimpleDraweeView) v.findViewById(R.id.ivProfile);
                tvDOB = (TextView) v.findViewById(R.id.tvDBO);
                tvAddr = (TextView) v.findViewById(R.id.tvAddr);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MatrimonialActivity.this, MetrimonimalDetailActivity.class);
                        intent.putExtra("detail", mDataSet.get(getAdapterPosition()));
                        intent.putParcelableArrayListExtra("list", busCategoryLists);
                        startActivity(intent);

                    }
                });

            }


        }
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


    public class MyCustomAdapter55 extends ArrayAdapter<SubCat> {

        private ArrayList<SubCat> categorylists;

        MyCustomAdapter55(Context context, int textViewResourceId,
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
