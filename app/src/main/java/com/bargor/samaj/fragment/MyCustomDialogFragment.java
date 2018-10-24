package com.bargor.samaj.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.bargor.samaj.R;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.SubCat;
import com.bargor.samaj.view.MatrimonialActivity;

import java.util.ArrayList;

public class MyCustomDialogFragment extends DialogFragment {
    private TextView tvLableMainCat;
    private Spinner spMainCat;
    private TextView tvLableSubCat;
    private Spinner spSubCat;
    private Spinner spStudy;
    private ArrayList<BusCategoryList> busCategoryLists;
    private MyCustomAdapter33 myCustomAdapter33;
    private MyCustomAdapter44 myCustomAdapter44;
    private MyCustomAdapter55 myCustomAdapter55;
    private ArrayList<BusCategoryList> copybusCategoryLists;
    private Context context;
    private String main_id;
    private String sub_id;
    private String study_id;
    private Button btnApply;
    private Button btnClear;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        copybusCategoryLists = new ArrayList<>();
        if (getArguments() != null) {
            busCategoryLists = getArguments().getParcelableArrayList("data");
            Log.e("filter size", "" + busCategoryLists.size());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        final View view = inflater.inflate(R.layout.row_search_matrimonila_dialog, null);
        tvLableMainCat = (TextView) view.findViewById(R.id.tvLableMainCat);
        spMainCat = (Spinner) view.findViewById(R.id.spMainCat);
        tvLableSubCat = (TextView) view.findViewById(R.id.tvLableSubCat);
        spStudy = view.findViewById(R.id.spCategoryStudy);
        spSubCat = (Spinner) view.findViewById(R.id.spSubCat);
        btnApply = view.findViewById(R.id.btnApply);
        btnClear = view.findViewById(R.id.btnClear);

        if (busCategoryLists != null) {

            for (int i = 0; i < busCategoryLists.size(); i++) {
                if (busCategoryLists.get(i).getMainCat().getId().equals("1")) {

                    myCustomAdapter55 = new MyCustomAdapter55(getActivity(), R.layout.row_custom_spinner, (ArrayList<SubCat>) busCategoryLists.get(i).getSubCat());
                    spStudy.setAdapter(myCustomAdapter55);
                    spStudy.setSelection(busCategoryLists.get(i).getSubCat().size() - 1);

                } else {
                    copybusCategoryLists.add(busCategoryLists.get(i));
                }


            }

            if (copybusCategoryLists.size() > 0) {


                myCustomAdapter33 = new MyCustomAdapter33(getActivity(), R.layout.row_custom_spinner, copybusCategoryLists);
                spMainCat.setAdapter(myCustomAdapter33);
            }

        }

        spMainCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myCustomAdapter44 = new MyCustomAdapter44(getActivity(), R.layout.row_custom_spinner, (ArrayList<SubCat>) ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat());
                spSubCat.setAdapter(myCustomAdapter44);

                main_id = ((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spSubCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub_id = ((SubCat) parent.getItemAtPosition(position)).getSubId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spStudy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                study_id = ((SubCat) parent.getItemAtPosition(position)).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MatrimonialActivity) context).onClear(main_id, sub_id, study_id);
                dismiss();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MatrimonialActivity) context).onApply(main_id, sub_id, study_id);
                dismiss();
            }
        });


        return builder.create();
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


            LayoutInflater inflater = getActivity().getLayoutInflater();
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

            LayoutInflater inflater = getActivity().getLayoutInflater();
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

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            label.setText(categorylists.get(position).getSubName());


            return row;
        }
    }
}
