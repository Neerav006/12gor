package com.bargor.samaj.view;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.R;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.DeathMemberListAPI;
import com.bargor.samaj.model.Deathlist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class DeathMemberListActivity extends AppCompatActivity {

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private RecyclerView rvList;
    private GetDeathMemberList getDeathMemberList;
    private ProgressBar progressBar;
    private CustomAdapter customAdapter;
    private ArrayList<Deathlist> deathlistArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_member_list);


        deathlistArrayList = new ArrayList<>();

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        getDeathMemberList = getListOfDeathMember(Constants.BASE_URL);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(DeathMemberListActivity.this, DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
        rvList.setLayoutManager(new LinearLayoutManager(DeathMemberListActivity.this));

        getDeathMemberList.getdeathmember().enqueue(new Callback<DeathMemberListAPI>() {
            @Override
            public void onResponse(Call<DeathMemberListAPI> call, Response<DeathMemberListAPI> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    deathlistArrayList = (ArrayList<Deathlist>) response.body().getDeathlist();

                    if (deathlistArrayList.size() > 0) {
                        customAdapter = new CustomAdapter(deathlistArrayList);
                        rvList.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(DeathMemberListActivity.this, "No Data available", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<DeathMemberListAPI> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeathMemberListActivity.this, "No Data available", Toast.LENGTH_SHORT).show();

            }
        });


    }

    GetDeathMemberList getListOfDeathMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetDeathMemberList.class);
    }

    interface GetDeathMemberList {
        @POST("directory/deathlistapi/")
        Call<DeathMemberListAPI> getdeathmember();

    }


    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Deathlist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Deathlist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_death_info, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the contents of the view
            // with that element

            viewHolder.tvName.setText(mDataSet.get(position).getName());
            viewHolder.tvMemNo.setText(mDataSet.get(position).getMemberId());
            viewHolder.tvPayAmount.setText(mDataSet.get(position).getAmount());
            viewHolder.tvExpdate.setText(mDataSet.get(position).getExpireddate());
            viewHolder.tvAddr.setText(mDataSet.get(position).getCity());

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
            private final TextView tvExpdate;
            private final TextView tvPayAmount;

            ViewHolder(View v) {
                super(v);

                tvName = (TextView) v.findViewById(R.id.tvName);
                tvAddr = (TextView) v.findViewById(R.id.tvAddr);
                tvExpdate = (TextView) v.findViewById(R.id.tvExpDate);
                tvMemNo = (TextView) v.findViewById(R.id.tvMemNo);
                tvPayAmount = (TextView) v.findViewById(R.id.tvPayableAmt);

                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(DeathMemberListActivity.this, MemberDetailActivity.class);
                        intent.putExtra("detail", mDataSet.get(getAdapterPosition()));
                        startActivity(intent);
                    }
                });

            }


        }
    }

}
