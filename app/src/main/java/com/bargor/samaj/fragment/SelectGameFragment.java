package com.bargor.samaj.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.ResGameList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class SelectGameFragment extends Fragment {

    GameListAPI gameListAPI;


    View view_main;
    RecyclerView recyclerView_gameList;
    TextView textView_noGames;
    Button button_next;
    ProgressBar progressBar;

    private ArrayList<ResGameList> responseArrayList = new ArrayList<>();
    GameListAdapter gameListAdapter;
    private ResGameList resGameList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_select_game, container, false);

        gameListAPI = getGameListAPIService(Constants.BASE_URL);

        recyclerView_gameList = view_main.findViewById(R.id.selectGame_rvList);
        textView_noGames = view_main.findViewById(R.id.selectGame_tv_noGames);
        button_next = view_main.findViewById(R.id.selectGame_btn_next);
        progressBar = view_main.findViewById(R.id.selectGame_progressbar);


        getAllGames();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<responseArrayList.size();i++){

                    if(responseArrayList.get(i).isChecked()){
                        resGameList = responseArrayList.get(i);
                        break;
                    }

                }

                if(resGameList!=null){

                    FragmentTransaction ft = getFragmentManager().beginTransaction();

                    Fragment fragment = new SelectCaptainFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data",resGameList);
                    fragment.setArguments(bundle);

                    ft.replace(R.id.content_activity_ramat, fragment).addToBackStack(null);
                    ft.commit();
                }




            }
        });


        return view_main;
    }


    private void getAllGames() {
        progressBar.setVisibility(View.VISIBLE);

        gameListAPI.get_games().enqueue(new Callback<List<ResGameList>>() {
            @Override
            public void onResponse(Call<List<ResGameList>> call, Response<List<ResGameList>> response) {
                progressBar.setVisibility(View.GONE);


                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        responseArrayList = (ArrayList<ResGameList>) response.body();


                        gameListAdapter = new GameListAdapter(getActivity(), responseArrayList);

                        recyclerView_gameList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                        if (getActivity() != null) {
                            recyclerView_gameList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                        }

                        recyclerView_gameList.setAdapter(gameListAdapter);

                        if (gameListAdapter.getItemCount() < 0) {
                            textView_noGames.setVisibility(View.VISIBLE);
                        }

                    } else {
                        //response body is null

                    }

                } else {
                    //response not successful
                }


            }

            @Override
            public void onFailure(Call<List<ResGameList>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

//---------------------------------- APIs ------------------------------------------------//

    GameListAPI getGameListAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GameListAPI.class);
    }

    interface GameListAPI {
        @POST("khelmahotsav/gamelistapi")
        Call<List<ResGameList>> get_games();
    }


//--------------------------------- Adapter Class -------------------------------------------//

    public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.MyViewHolder> {

        Context context;
        private ArrayList<ResGameList> mData;


        public GameListAdapter(Context context, ArrayList<ResGameList> mData) {
            this.mData = mData;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_games, parent, false);
            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            if (mData.get(position).isChecked()) {
                holder.checkedTextView_name.setChecked(true);
                holder.checkedTextView_name.setCheckMarkDrawable(R.drawable.checked_circle);
            } else {
                holder.checkedTextView_name.setChecked(false);
                holder.checkedTextView_name.setCheckMarkDrawable(null);
            }


            holder.checkedTextView_name.setText(mData.get(position).getGame_name());


        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            CheckedTextView checkedTextView_name;

            MyViewHolder(View view) {
                super(view);
                checkedTextView_name = (CheckedTextView) view.findViewById(R.id.row_gameList_tv_name);

                checkedTextView_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean value = checkedTextView_name.isChecked();


                        for (int i = 0; i < responseArrayList.size(); i++) {
                            responseArrayList.get(i).setChecked(false);
                        }


                        if (value) {
                            checkedTextView_name.setChecked(false);
                            mData.get(getAdapterPosition()).setChecked(false);
                            checkedTextView_name.setCheckMarkDrawable(null);

                        } else {

                            checkedTextView_name.setChecked(true);
                            mData.get(getAdapterPosition()).setChecked(true);
                            checkedTextView_name.setCheckMarkDrawable(R.drawable.checked_circle);

                        }


                        notifyDataSetChanged();


                        boolean isChecked = false;

                        for (int i = 0; i < mData.size(); i++) {

                            if (mData.get(i).isChecked()) {
                                isChecked = true;
                                break;
                            }
                        }

                        if (isChecked) {
                            button_next.setVisibility(View.VISIBLE);
                        } else {
                            button_next.setVisibility(View.GONE);
                        }

                    }

                });

            }


        }


    }

}
