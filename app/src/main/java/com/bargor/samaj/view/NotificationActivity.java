package com.bargor.samaj.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.NotList;
import com.bargor.samaj.model.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class NotificationActivity extends AppCompatActivity {
    private final String BASE_URL = "http://12gor.codefuelindia.com";
    private RecyclerView rvList;
    private TextView tvNoNotification;
    private CustomAdapter customAdapter;
    private GetNotification getNotification;
    private ArrayList<NotList> notLists;
    private String u_id = "";
    private ProgressBar progressBar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notLists = new ArrayList<>();
        final SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, NotificationActivity.this);
        u_id = sharedPreferences.getString(Constants.MEMBER_ID, null);

        getNotification = getNotificationAPI(Constants.BASE_URL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        tvNoNotification = (TextView) findViewById(R.id.tvNoNotification);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getNotification.notlist(u_id).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    notLists = (ArrayList<NotList>) response.body().getNotList();

                    if (notLists.size() > 0) {
                        tvNoNotification.setVisibility(View.GONE);
                        customAdapter = new CustomAdapter(notLists);
                        rvList.setAdapter(customAdapter);
                    } else {

                        tvNoNotification.setVisibility(View.VISIBLE);
                        customAdapter = new CustomAdapter(notLists);
                        rvList.setAdapter(customAdapter);
                    }

                }


            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNoNotification.setVisibility(View.VISIBLE);
            }
        });


    }


    GetNotification getNotificationAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetNotification.class);
    }


    interface GetNotification {
        @POST("notification/viewnotificationapi/")
        @FormUrlEncoded
        Call<Notification> notlist(@Field("m_id") String m_id);
    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<NotList> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<NotList> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_notification, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            viewHolder.textView.setText(mDataSet.get(position).getName());
            viewHolder.tvDate.setText(mDataSet.get(position).getDate());
            // Get element from your dataset at this position and replace the contents of the view
            // with that element

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
            private final TextView textView;
            private final TextView tvDate;

            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!mDataSet.get(getAdapterPosition()).getLink().isEmpty()){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataSet.get(getAdapterPosition()).getLink()));
                            startActivity(browserIntent);

                        }


                    }
                });
                textView = (TextView) v.findViewById(R.id.tvTitle);
                tvDate = (TextView) v.findViewById(R.id.tvDate);
            }

            TextView getTextView() {
                return textView;
            }
        }
    }

}
