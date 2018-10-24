package com.bargor.samaj.view;

import android.support.annotation.NonNull;
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
import com.bargor.samaj.model.BirthDayList;
import com.bargor.samaj.model.Bod;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class HappyBirthdayActivity extends AppCompatActivity {
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private RecyclerView rvList;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private ArrayList<Bod> bodArrayList;
    private CustomAdapter customAdapter;
    private GetBirthDayOf getBirthDayOf;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_birthday);
        bodArrayList = new ArrayList<>();
        getBirthDayOf = todayBirthDay(Constants.BASE_URL);

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(HappyBirthdayActivity.this,DividerItemDecoration.VERTICAL);
        rvList.setLayoutManager(new LinearLayoutManager(HappyBirthdayActivity.this));
        rvList.addItemDecoration(dividerItemDecoration);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getBirthDayOf.happyBirthDay().enqueue(new Callback<BirthDayList>() {
            @Override
            public void onResponse(Call<BirthDayList> call, Response<BirthDayList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    bodArrayList= (ArrayList<Bod>) response.body().getBod();

                    if(bodArrayList.size()>0){
                        customAdapter=new CustomAdapter(bodArrayList);
                        rvList.setAdapter(customAdapter);
                    }
                    else {
                        Toast.makeText(HappyBirthdayActivity.this,"No BirthDay Today..",Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<BirthDayList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(HappyBirthdayActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();

            }
        });


    }

    GetBirthDayOf todayBirthDay(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBirthDayOf.class);
    }

    interface GetBirthDayOf {
        @POST("member/todaybodapi/")
        Call<BirthDayList> happyBirthDay();
    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Bod> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
         CustomAdapter(ArrayList<Bod> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_happy_birthday, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvName.setText(mDataSet.get(position).getName());
            if(mDataSet.get(position).getPhoto()!=null){
                viewHolder.ivProfile.setImageURI(VIEW_PATH.concat(mDataSet.get(position).getPhoto()));
            }


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

             ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                tvName = (TextView) v.findViewById(R.id.tvMemberName);
                ivProfile = (SimpleDraweeView) v.findViewById(R.id.ivProfile);

            }


        }
    }
}
