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


public class SelectGameFragment extends Fragment {

    View view_main;
    RecyclerView recyclerView_gameList;
    TextView textView_noGames;
    Button button_next;
    ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_select_game, container, false);

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


    }


}
