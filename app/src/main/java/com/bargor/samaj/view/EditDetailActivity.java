package com.bargor.samaj.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.BusinessCategory;
import com.bargor.samaj.model.Category;
import com.bargor.samaj.model.Categorylist;
import com.bargor.samaj.model.MemberDetail;
import com.bargor.samaj.model.MemberRegistration;
import com.bargor.samaj.model.MyRes;
import com.bargor.samaj.model.Part1;
import com.bargor.samaj.model.SubCat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class EditDetailActivity extends AppCompatActivity {
    private final long DOUBLE_TAP = 1500;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private LinearLayout llFirst;
    private TextInputEditText edtPart1Name;
    private TextInputEditText edtPart1Addr;
    private TextInputEditText edtPart1Phone;
    private TextInputEditText edtpart1Mobile;
    private TextInputEditText edtpart1FamilyCode;
    private TextInputEditText edtPart1Native;
    private TextInputEditText edtPart1Email;
    private TextView tvpart1DOB;
    private TextInputEditText edtPart1City;
    private TextInputEditText edtPart1PinCode;
    private TextInputEditText edtPart1Taluko;
    private TextInputEditText edtPart1District;
    private TextInputEditText edtPart1State;
    private Button btnPart1SelectBirth;
    private TextInputEditText edtAmount;
    private RadioGroup rgCOC;
    private RadioButton rbCash;
    private RadioButton rbCheque;
    private RadioButton rbVidhva;
    private TextInputLayout txtBankName;
    private TextInputEditText edtBankName;
    private TextInputLayout txtChequeNo;
    private TextInputEditText edtChequeNo;
    private Button btnNext11;
    private LinearLayout llSecond;
    private Button btnBack22;
    private Button btnNext22;
    private Spinner spRelation;
    private String profilePath;
    private RadioGroup rgIsParinit;
    private RadioButton rbParinit;
    private RadioButton rbUnMarried;
    private Calendar endCalender;
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            endCalender = Calendar.getInstance();
            endCalender.set(Calendar.YEAR, year);
            endCalender.set(Calendar.MONTH, monthOfYear);
            endCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            tvpart1DOB.setText(sdf.format(endCalender.getTime()));
//            dob = sdf.format(endCalender.getTime());


        }

    };
    private TextView tvVatan;
    private Spinner spShakh;
    private Spinner spBlood;
    private String main_cat_id = "";
    private String sub_cat_id = "";
    private String stydy_id = "";
    private String study = "";
    private GetBusinessCategory getBusinessCategory;
    private Spinner spCategory;
    private GetCategory getCategory;
    private ArrayList<Categorylist> categorylists;
    private FetchMemberDetail fetchMemberDetail;
    private ProgressDialog progressDialog;
    private String l_id;
    private String mem_id;
    private MemberRegAPi memberRegAPi;
    private MemberRegistration memberRegistration;
    private MemberDetail memberDetail;
    private TextView tvLablleRelation;
    private TextView tvImageUpload;
    private boolean isOwn = false;
    private ImageView ivPhoto;
    private String parinit = "";
    private String gender;
    private TextInputEditText edtStudy;
    private EditText edtBusinessAddr;
    private EditText edtOtherBusiness;
    private MyCustomAdapter33 myCustomAdapter33;
    private ProgressBar progressBar;
    private TextView tvSelectBusinessLable;
    private Spinner spSubCategory;
    private Spinner spStudy;
    private ArrayList<BusCategoryList> busCategoryLists;
    private MyCustomAdapter44 myCustomAdapter44;
    private MyCustomAdapter55 myCustomAdapter55;
    private ArrayList<BusCategoryList> copybusCategoryLists;

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }


    /**
     * ***********************************************************
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);
        progressBar = findViewById(R.id.progressBar);
        copybusCategoryLists = new ArrayList<>();
        profilePath = Constants.BASE_URL.concat("profile/");
        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, EditDetailActivity.this);
        // l_id = sharedPreferences.getString(Constants.ID, null);
        busCategoryLists = new ArrayList<>();

        if (getIntent().getStringExtra("from") != null) {
            mem_id = getIntent().getStringExtra("id");
            isOwn = sharedPreferences.getString(Constants.ID, "").equals(mem_id);

        } else {
            isOwn = true;
            mem_id = sharedPreferences.getString(Constants.ID, null);
        }

        final TextView tvLableAdd = findViewById(R.id.tvLableAdd);
        tvLableAdd.setVisibility(View.GONE);

        memberRegAPi = addMember(Constants.BASE_URL);
        fetchMemberDetail = getMember(Constants.BASE_URL);

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        memberRegistration = new MemberRegistration();
        spRelation = findViewById(R.id.spRelation);
        getCategory = getCategoryProfession(Constants.BASE_URL);
        getBusinessCategory = getCategoryByBusiness(Constants.BASE_URL);
        categorylists = new ArrayList<>();
        tvSelectBusinessLable = findViewById(R.id.tvLableSelectBusiness);
        llFirst = (LinearLayout) findViewById(R.id.llFirst);
        edtPart1Name = (TextInputEditText) findViewById(R.id.edtPart1Name);
        edtPart1Addr = (TextInputEditText) findViewById(R.id.edtPart1Addr);
        edtpart1Mobile = (TextInputEditText) findViewById(R.id.edtpart1Mobile);
        tvLablleRelation = findViewById(R.id.tvLableRelation);
        tvImageUpload = findViewById(R.id.tvSelectPhoto);
        tvImageUpload.setVisibility(View.GONE);
        ivPhoto = findViewById(R.id.ivPhotoGraph);
        rgIsParinit = findViewById(R.id.rgIsMarried);
        rbParinit = findViewById(R.id.rbParinit);
        rbUnMarried = findViewById(R.id.rbUnmaried);
        rbVidhva = findViewById(R.id.rbVidhva);
        spCategory = findViewById(R.id.spCategory);
        spSubCategory = findViewById(R.id.spSubCategory);
        spStudy = findViewById(R.id.spCategoryStudy);
        edtStudy = findViewById(R.id.edtStudy);
        edtBusinessAddr = findViewById(R.id.edtBusinessAddr);
        edtOtherBusiness = findViewById(R.id.edtOther);
        edtPart1Addr.setClickable(false);
        edtPart1Addr.setEnabled(false);
        spShakh = findViewById(R.id.spCategoryShakh);
        spBlood = findViewById(R.id.spCategoryBlood);
        spSubCategory.setVisibility(View.GONE);
        tvVatan = findViewById(R.id.tvVatan);
        tvVatan.setVisibility(View.VISIBLE);


        if (isOwn) {
            spRelation.setVisibility(View.GONE);
            tvLablleRelation.setVisibility(View.GONE);
            edtpart1Mobile.setClickable(false);
            edtpart1Mobile.setEnabled(false);
            edtPart1Addr.setEnabled(true);
            edtPart1Addr.setClickable(true);
            edtStudy.setVisibility(View.VISIBLE);
//            spCategory.setVisibility(View.GONE);
//            edtBusinessAddr.setVisibility(View.GONE);
//            edtOtherBusiness.setVisibility(View.GONE);
//            edtStudy.setVisibility(View.GONE);
//            spSubCategory.setVisibility(View.GONE);
//            spStudy.setVisibility(View.GONE);
//            tvSelectBusinessLable.setVisibility(View.GONE);
        } else {
            edtPart1Addr.setClickable(false);
            edtPart1Addr.setEnabled(false);
            edtpart1Mobile.setClickable(true);
            edtpart1Mobile.setEnabled(true);
            spRelation.setVisibility(View.VISIBLE);
            spCategory.setVisibility(View.VISIBLE);
            spSubCategory.setVisibility(View.VISIBLE);
            tvLablleRelation.setVisibility(View.VISIBLE);
            spStudy.setVisibility(View.VISIBLE);
            edtOtherBusiness.setVisibility(View.VISIBLE);
            edtBusinessAddr.setVisibility(View.VISIBLE);
            edtStudy.setVisibility(View.VISIBLE);
        }


        edtPart1Email = (TextInputEditText) findViewById(R.id.edtpart1Email);
        tvpart1DOB = (TextView) findViewById(R.id.tvpart1DOB);
        btnPart1SelectBirth = (Button) findViewById(R.id.btnPart1SelectBirth);


        //hide amount ,cheque


        btnNext11 = (Button) findViewById(R.id.btnNext11);
        llSecond = (LinearLayout) findViewById(R.id.llSecond);
        btnNext11.setText("Save");

        btnBack22 = (Button) findViewById(R.id.btnBack22);
        btnNext22 = (Button) findViewById(R.id.btnNext22);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (llFirst.getVisibility() == View.VISIBLE) {
                    finish();
                }

            }
        });
        rgIsParinit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rbParinit:
                        parinit = "1";
                        break;
                    case R.id.rbUnmaried:
                        parinit = "0";
                        break;
                    case R.id.rbVidhva:
                        parinit = "2";
                        break;
                }

            }
        });


//        getCategory.getCatList().enqueue(new Callback<Category>() {
//            @Override
//            public void onResponse(Call<Category> call, Response<Category> response) {
//                progressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful()) {
//
//                    categorylists = (ArrayList<Categorylist>) response.body().getCategorylist();
//                    if (categorylists.size() > 0) {
//
//                        myCustomAdapter33 = new MyCustomAdapter33(EditDetailActivity.this, R.layout.row_custom_spinner, categorylists);
//                        spCategory.setAdapter(myCustomAdapter33);
//
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Category> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//            }
//        });


        spShakh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                main_cat_id = ((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId();

                myCustomAdapter44 = new MyCustomAdapter44(EditDetailActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat());
                spSubCategory.setAdapter(myCustomAdapter44);

                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId().equals("10")) {
                    edtBusinessAddr.setVisibility(View.GONE);
                } else {
                    edtBusinessAddr.setVisibility(View.VISIBLE);

                }

                for (SubCat subCat : ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat()) {
                    if (subCat.getId().equals(sub_cat_id)) {
                        spSubCategory.setSelection(((BusCategoryList) parent.getItemAtPosition(position)).getSubCat().indexOf(subCat));
                        break;
                    }
                }


                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getStatus().equals("2")) {
                    edtOtherBusiness.setVisibility(View.VISIBLE);
                    spSubCategory.setVisibility(View.GONE); // hide sub cat
                    sub_cat_id = " ";

                } else {
                    spSubCategory.setVisibility(View.VISIBLE);
                    edtOtherBusiness.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spStudy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (((SubCat) parent.getItemAtPosition(position)).getStatus().equals("2")) {
                    edtStudy.setVisibility(View.VISIBLE);
                    stydy_id = ((SubCat) parent.getItemAtPosition(position)).getId();

                } else {
                    stydy_id = ((SubCat) parent.getItemAtPosition(position)).getId();
                    edtStudy.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (((SubCat) parent.getItemAtPosition(position)).getStatus().equals("2")) {
                    edtOtherBusiness.setVisibility(View.VISIBLE);
                    sub_cat_id = ((SubCat) parent.getItemAtPosition(position)).getId();
                } else {
                    sub_cat_id = ((SubCat) parent.getItemAtPosition(position)).getId();
                    edtOtherBusiness.setVisibility(View.GONE);
                }


                //


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        showProgressDialog();
        //Member detail fetch
        fetchMemberDetail.getMemberDetail(mem_id).enqueue(new Callback<MemberDetail>() {
            @Override
            public void onResponse(Call<MemberDetail> call, Response<MemberDetail> response) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (response.isSuccessful()) {

                    l_id = response.body().getId();
                    edtPart1Name.setText(response.body().getName());
                    edtPart1Addr.setText(response.body().getAddress());
                    edtPart1Email.setText(response.body().getEmail());
                    edtpart1Mobile.setText(response.body().getMobile());
                    tvpart1DOB.setText(response.body().getDob());
                    edtStudy.setText(response.body().getStudy());
                    edtBusinessAddr.setText(response.body().getB_addr());
                    spRelation.setSelection(getIndex(spRelation, response.body().getRelation()));
                    tvVatan.setText("વતન :-".concat(response.body().getVillage()));
                    study = response.body().getStudy();
                    main_cat_id = response.body().getBus_cat();
                    sub_cat_id = response.body().getBus_sub_cat();
                    stydy_id = response.body().getStudy_cat();
                    edtOtherBusiness.setText(response.body().getB_other());

                    switch (response.body().isParinit()) {
                        case "0":
                            rbUnMarried.setChecked(true);
                            break;
                        case "1":
                            rbParinit.setChecked(true);
                            break;
                        case "2":
                            rbVidhva.setChecked(true);
                            break;
                    }


                    if (response.body().getShakh() != null && response.body().getBlood() != null) {

                        spBlood.setSelection(getIndex(spBlood, response.body().getBlood()));
                        spShakh.setSelection(getIndex(spShakh, response.body().getShakh()));

                        if (!isOwn) {
                            spShakh.setEnabled(false);

                        } else {
                            spShakh.setEnabled(true);
                        }


                    }


                    progressBar.setVisibility(View.VISIBLE);
                    getBusinessCategory.getBusinessCatList().enqueue(new Callback<BusinessCategory>() {
                        @Override
                        public void onResponse(Call<BusinessCategory> call, Response<BusinessCategory> response) {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {
                                busCategoryLists = (ArrayList<BusCategoryList>) response.body().getCategoryList();

                                for (int i = 0; i < busCategoryLists.size(); i++) {
                                    if (busCategoryLists.get(i).getMainCat().getId().equals("1")) {

                                        myCustomAdapter55 = new MyCustomAdapter55(EditDetailActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) busCategoryLists.get(i).getSubCat());
                                        spStudy.setAdapter(myCustomAdapter55);

                                        for (SubCat subCat : busCategoryLists.get(i).getSubCat()) {
                                            if (subCat.getId().equals(stydy_id)) {
                                                spStudy.setSelection(busCategoryLists.get(i).getSubCat().indexOf(subCat));
                                                edtStudy.setText(study);
                                                break;
                                            }
                                        }


                                    } else {
                                        copybusCategoryLists.add(busCategoryLists.get(i));
                                    }


                                }

                                if (copybusCategoryLists.size() > 0) {


                                    myCustomAdapter33 = new MyCustomAdapter33(EditDetailActivity.this, R.layout.row_custom_spinner, copybusCategoryLists);
                                    spCategory.setAdapter(myCustomAdapter33);

                                    for (BusCategoryList busCategoryList : copybusCategoryLists) {
                                        if (busCategoryList.getMainCat().getId().equals(main_cat_id)) {
                                            spCategory.setSelection(copybusCategoryLists.indexOf(busCategoryList));

                                            for (SubCat subCat : busCategoryList.getSubCat()) {
                                                if (subCat.getId().equals(sub_cat_id)) {
                                                    myCustomAdapter44 = new MyCustomAdapter44(EditDetailActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) busCategoryList.getSubCat());
                                                    spSubCategory.setAdapter(myCustomAdapter44);

                                                    spSubCategory.setSelection(busCategoryList.getSubCat().indexOf(subCat));
                                                    break;

                                                }
                                            }


                                            break;
                                        }
                                    }


                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<BusinessCategory> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                    Glide.with(getApplicationContext())
                            .load(profilePath.concat(response.body().getProfile())) // image url
                            .into(ivPhoto);

                }

            }

            @Override
            public void onFailure(Call<MemberDetail> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

        spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// 1 Male 2 Female
                switch (position) {

                    case 0:      //wife
                        gender = "2";

                        break;
                    case 1:  //husband
                        gender = "1";

                        break;
                    case 2:  //son
                        gender = "1";
                        break;
                    case 3: //daughter
                        gender = "2";
                        break;
                    case 4:
                        gender = "2";//daughter_law

                        break;
                    case 5:   //
                        gender = "1"; //father

                        break;
                    case 6:
                        gender = "2";  // mother

                        break;
                    case 7:
                        gender = "1";  //brother

                        break;
                    case 8:
                        gender = "2"; //sister

                        break;
                    case 9:
                        gender = "1"; //grand_son


                        break;
                    case 10:
                        gender = "2"; //grand_daughter


                        break;

                    case 11:
                        gender = "2"; // bhabhi
                        break;

                    case 12:
                        gender = "1";  // kaka
                        break;

                    case 13:
                        gender = "2";  //kaki
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnPart1SelectBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDetailActivity.this, endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });


        btnNext11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPart1Valid()) {
                    llSecond.setVisibility(View.GONE);
                    showProgressDialog();
                    RequestBody lid = RequestBody.create(MediaType.parse("text/plain"), l_id);

                    final String relation = spRelation.getSelectedItem().toString();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", edtPart1Name.getText().toString().trim());
                        jsonObject.put("email", edtPart1Email.getText().toString().trim());
                        jsonObject.put("mobile", edtpart1Mobile.getText().toString().trim());
                        if (!isOwn) {
                            jsonObject.put("relation", relation);

                            jsonObject.put("study", edtStudy.getText().toString().trim());
                            jsonObject.put("b_addr", edtBusinessAddr.getText().toString().trim());
                            jsonObject.put("b_other", edtOtherBusiness.getText().toString().trim());
                        } else {
                            jsonObject.put("relation", "પોતે(મુખ્ય સભ્ય)");
                            jsonObject.put("study", edtStudy.getText().toString().trim());

                            jsonObject.put("b_addr", edtBusinessAddr.getText().toString().trim());
                            jsonObject.put("b_other", edtOtherBusiness.getText().toString().trim());

                        }

                        jsonObject.put("study_cat", stydy_id);
                        jsonObject.put("bus_cat", main_cat_id);
                        jsonObject.put("bus_sub_cat", sub_cat_id);
                        jsonObject.put("dob", tvpart1DOB.getText().toString().trim());
                        jsonObject.put("addr", edtPart1Addr.getText().toString().trim());
                        jsonObject.put("parinit", parinit);
                        jsonObject.put("gender", gender);
                        jsonObject.put("blood", spBlood.getSelectedItem().toString());
                        jsonObject.put("shakh", spShakh.getSelectedItem().toString());


                        RequestBody familyJson = RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());

                        Log.e("family json", jsonObject.toString());
                        memberRegAPi.editrMember(lid, familyJson).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                btnNext22.setEnabled(true);
                                if (response.isSuccessful()) {
                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
                                        Toast.makeText(EditDetailActivity.this, "Successfully Edited", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(EditDetailActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(EditDetailActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();
                                        finish();

                                    }
                                } else {
                                    Toast.makeText(EditDetailActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                btnNext22.setEnabled(true);
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(EditDetailActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();
                                finish();

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        });


        btnNext22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPart2Valid()) {
                    llFirst.setVisibility(View.GONE);


                }


            }
        });


        btnBack22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llFirst.setVisibility(View.VISIBLE);
                llSecond.setVisibility(View.GONE);
            }
        });


    }

    /**
     * **************************
     * Form validation functions
     */

    private boolean isPart1Valid() {

        final String name = edtPart1Name.getText().toString().trim();
        final String addr = edtPart1Addr.getText().toString().trim();
        final String mobile = edtpart1Mobile.getText().toString().trim();

        final String dob = tvpart1DOB.getText().toString().trim();


        if (name.isEmpty()) {
            edtPart1Name.setError("Enter Name");
            return false;
        } else if (addr.isEmpty()) {
            edtPart1Addr.setError("Enter address");
            return false;
        } else if (dob.isEmpty()) {

            Toast.makeText(EditDetailActivity.this, "Please Select birth date", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            Part1 part1 = new Part1();
            part1.setName(name);
            part1.setAddr(addr);
            part1.setDob(dob);
            part1.setEmail(edtPart1Email.getText().toString().trim());
            part1.setL_id(l_id);
            part1.setM_id(mem_id);

            memberRegistration.setPart1(part1);

            return true;
        }


    }

    private boolean isPart2Valid() {

        return true;


    }

    MemberRegAPi addMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(MemberRegAPi.class);
    }

    FetchMemberDetail getMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(FetchMemberDetail.class);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(EditDetailActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    GetCategory getCategoryProfession(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategory.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), EditDetailActivity.this);
    }

    GetBusinessCategory getCategoryByBusiness(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessCategory.class);
    }

    /**
     * **************************************
     */

    interface MemberRegAPi {
        @POST("member/editmemberapi/")
        @Multipart
        Call<MyRes> editrMember(@Part("id") RequestBody id,
                                @Part("family") RequestBody family);
    }

    interface FetchMemberDetail {
        @POST("member/profiledetailsapi/")
        @FormUrlEncoded
        Call<MemberDetail>
        getMemberDetail(@Field("id") String id);
    }


    interface GetCategory {
        @POST("directory/categorylistapi/")
        Call<Category> getCatList();
    }

    interface GetBusinessCategory {
        @POST("home/jobtypeapi/")
        Call<BusinessCategory> getBusinessCatList();
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

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            label.setText(categorylists.get(position).getMainCat().getName());


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

            LayoutInflater inflater = getLayoutInflater();
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

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            label.setText(categorylists.get(position).getSubName());


            return row;
        }
    }
}

