package com.bargor.samaj.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bargor.samaj.R;


public class SelectCaptainFragment extends Fragment {

    View view_main;
    EditText editText_id;
    ImageView imageView_search;
    LinearLayout linearLayout_memberInfo;
    TextView tv_id, tv_name, tv_number, tv_city, tv_noData;
    Spinner spinner_size;
    ProgressBar progressBar;
    Button button_next;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_select_captain, container, false);

        editText_id = view_main.findViewById(R.id.selectCaptain_et_id);
        imageView_search = view_main.findViewById(R.id.selectCaptain_iv_search);
        linearLayout_memberInfo = view_main.findViewById(R.id.selectCaptain_ll_memberInfo);
        tv_id = view_main.findViewById(R.id.selectCaptain_tv_id);
        tv_name = view_main.findViewById(R.id.selectCaptain_tv_name);
        tv_number = view_main.findViewById(R.id.selectCaptain_tv_number);
        tv_city = view_main.findViewById(R.id.selectCaptain_tv_city);
        spinner_size = view_main.findViewById(R.id.selectCaptain_spinner_size);
        tv_noData = view_main.findViewById(R.id.selectCaptain_tv_noData);
        progressBar = view_main.findViewById(R.id.selectCaptain_progressbar);
        button_next = view_main.findViewById(R.id.selectCaptain_btn_next);


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMemberData();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view_main;
    }

    private void searchMemberData() {

        // API call here

    }

    private void testMeth() {

    }


}
