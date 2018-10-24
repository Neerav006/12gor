package com.bargor.samaj.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.FamilyList;
import com.bargor.samaj.model.FamilyListOF;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class GroupViseFamily extends AppCompatActivity {
    private String l_id;
    private SearchMember searchMember;
    private CustomAdapter customAdapter;
    private ArrayList<FamilyList> memberlistArrayList;
    private RecyclerView rvList;
    private ProgressBar progressBar;
    private ArrayList<BusCategoryList> busCategoryLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_vise_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null) {
            l_id = getIntent().getStringExtra("family_id");
            busCategoryLists = getIntent().getParcelableArrayListExtra("bus");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchMember = getSearchedMember(Constants.BASE_URL);
        memberlistArrayList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        rvList = findViewById(R.id.rvList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(GroupViseFamily.this, DividerItemDecoration.VERTICAL);
        rvList.setLayoutManager(new LinearLayoutManager(GroupViseFamily.this));
        rvList.addItemDecoration(dividerItemDecoration);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        searchMember.getMemberDetail(l_id).enqueue(new Callback<FamilyListOF>() {
            @Override
            public void onResponse(Call<FamilyListOF> call, Response<FamilyListOF> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response.body().getFamilyList() != null && response.body().getFamilyList().size() > 0) {
                        customAdapter = new CustomAdapter((ArrayList<FamilyList>) response.body().getFamilyList());
                        rvList.setAdapter(customAdapter);

                    }

                }
            }

            @Override
            public void onFailure(Call<FamilyListOF> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    SearchMember getSearchedMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SearchMember.class);
    }

    interface SearchMember {

        @POST("user/totalfamilylistapi/")
        @FormUrlEncoded()
        Call<FamilyListOF> getMemberDetail(@Field("id") String id);


    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<FamilyList> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        CustomAdapter(ArrayList<FamilyList> dataSet) {
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
            viewHolder.tvMemNo.setText(mDataSet.get(position).getRelation());
            viewHolder.tvDOB.setText("BirthDay:- ".concat(Utils.getDDMMYYYY(mDataSet.get(position).getDob())));

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
            private final TextView tvAddr;
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
                tvAddr = (TextView) v.findViewById(R.id.tvCity);
                tvMemNo = (TextView) v.findViewById(R.id.tvMemberCode);
                tvDOB = v.findViewById(R.id.tvDOB);
                tvStudy = v.findViewById(R.id.tvStudy);
                ivView = (ImageView) v.findViewById(R.id.ivView);
                ivEdit = (ImageView) v.findViewById(R.id.ivEdit);
                ivEditProfile = (ImageView) v.findViewById(R.id.ivEditProfile);
                ivEdit.setVisibility(View.GONE);
                ivEditProfile.setVisibility(View.GONE);

                ivView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GroupViseFamily.this, MemberDetailActivity.class);
                        intent.putParcelableArrayListExtra("bus", busCategoryLists);
                        intent.putExtra("detail", mDataSet.get(getAdapterPosition()));
                        startActivity(intent);
                    }
                });


//                ivEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent intent = new Intent(GroupViseFamily.this, EditDetailActivity.class);
//                        intent.putExtra("id", mDataSet.get(getAdapterPosition()).getId());
//                        intent.putExtra("from", SearchMemberActivity.class.getSimpleName());
//                        startActivity(intent);
//                    }
//                });


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
}
