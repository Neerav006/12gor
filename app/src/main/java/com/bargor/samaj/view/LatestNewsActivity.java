package com.bargor.samaj.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.bargor.samaj.model.NewsListAPI;
import com.bargor.samaj.model.Newslist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class LatestNewsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvList;
    private CustomAdapter customAdapter;
    private GetLatestNews getLatestNews;
    private ProgressBar progressBar;
    private ArrayList<Newslist> newslistArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        newslistArrayList = new ArrayList<>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        getLatestNews = getCurrentNews(Constants.BASE_URL);

        rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(LatestNewsActivity.this));
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        getLatestNews = getCurrentNews(Constants.BASE_URL);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLatestNews.getLatestnews().enqueue(new Callback<NewsListAPI>() {
            @Override
            public void onResponse(Call<NewsListAPI> call, Response<NewsListAPI> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    newslistArrayList = (ArrayList<Newslist>) response.body().getNewslist();
                    if (newslistArrayList.size() > 0) {
                        customAdapter = new CustomAdapter(newslistArrayList);
                        rvList.setAdapter(customAdapter);
                    }
                    else {
                        Toast.makeText(LatestNewsActivity.this,"No Data available",Toast.LENGTH_SHORT).show();
                    }


                }


            }

            @Override
            public void onFailure(Call<NewsListAPI> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LatestNewsActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();

            }
        });


    }

    GetLatestNews getCurrentNews(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetLatestNews.class);
    }

    interface GetLatestNews {
        @POST("news/newslistapi/")
        Call<NewsListAPI> getLatestnews();
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Newslist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Newslist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_latest_news, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvTitle.setText(mDataSet.get(position).getTitle());
            viewHolder.tvDate.setText(mDataSet.get(position).getDate());
            viewHolder.tvDesc.setText(mDataSet.get(position).getDescription());

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

            private final TextView tvTitle;
            private final TextView tvDate;
            private final TextView tvDesc;

            ViewHolder(View v) {
                super(v);

                tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvDate = (TextView) v.findViewById(R.id.tvDate);
                tvDesc = (TextView) v.findViewById(R.id.tvDesc);

                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }


        }
    }
}
