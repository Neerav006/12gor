package com.bargor.samaj.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.ResGameList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class SelectGameFragment extends Fragment {

    GameListAPI gameListAPI;


    View view_main;
    RecyclerView recyclerView_gameList;
    TextView textView_noGames;
    Button button_next;
    ProgressBar progressBar;

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
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("gamelistapi")
        @FormUrlEncoded
        Call<List<ResGameList>> get_games();
    }


//--------------------------------- Adapter Class -------------------------------------------//

    

}
