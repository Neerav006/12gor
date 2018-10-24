package com.bargor.samaj.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.AlarmReceiver;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.AllMember;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.BusinessCategory;
import com.bargor.samaj.model.Category;
import com.bargor.samaj.model.Categorylist;
import com.bargor.samaj.model.City;
import com.bargor.samaj.model.DistrictList;
import com.bargor.samaj.model.GetList;
import com.bargor.samaj.model.Gor;
import com.bargor.samaj.model.GorList;
import com.bargor.samaj.model.Memberlist;
import com.bargor.samaj.model.MobileRegistration;
import com.bargor.samaj.model.MyRes;
import com.google.gson.Gson;
import com.bargor.samaj.model.SubCat;
import com.bargor.samaj.model.VillageList;
import com.bargor.samaj.model.VillagelistOF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class RegistrationActivity extends AppCompatActivity {
    // role       2-->agent(Parent member)         3-->End user

    private final long DOUBLE_TAP = 1500;
    private GetCategory getCategory;
    private ArrayList<Categorylist> categorylists;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private TextInputEditText edtName;
    private TextInputEditText edtMobile;
    private TextInputEditText edtAddr;
    private TextInputEditText edtEmail;
    private RadioGroup radioGroup;
    private RadioButton rbExecutive;
    private RadioButton rbUser;
    private Button btnRegister;
    private long lastclick = 0;
    private RegisterMobile registerMobile;
    private ProgressDialog progressDialog;
    private TextInputEditText edtMemNo;
    private ArrayList<Memberlist> memberlistArrayList;
    private SearchMember searchMember;
    private LinearLayout llIsMem;
    private RadioGroup rgYesOrNo;
    private RadioButton rbYes;
    private RadioButton rbNo;
    private Button ivGo;
    private TextInputLayout txtInName;
    private TextInputLayout txtInAddr;
    private TextInputLayout txtInNative;
    private TextInputLayout txtInEmail;
    private TextInputLayout txtInMobile;
    private GetVillageName getVillageName;
    private ArrayList<VillageList> villageLists;
    private ArrayList<GorList> gorLists;
    private ArrayList<GetList> getLists;
    private GetGorName getGorName;
    private Spinner spVillageName;
    private Spinner spGor;
    private ProgressBar progressBar;
    private RelativeLayout rrIsMemberNo;
    private Calendar endCalender;
    private TextView tvBirthDay;
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


            tvBirthDay.setText(sdf.format(endCalender.getTime()));
            //dob = sdf.format(endCalender.getTime());


        }

    };
    private GetBusinessCategory getBusinessCategory;
    private RadioGroup rgMaleFemale;
    private RadioButton rbMale;
    private RadioButton rbWomen;
    private TextView tvLableselectVillage;
    private EditText edtOtherVillage;
    private EditText edtFamilyMemberCount;
    private EditText edtotherShakha;
    private GetDistrictName getDistrictName;
    private Spinner spDistrict;
    private Spinner spCity;
    private Spinner spGautra;
    private Spinner spBloodGrp;
    private TextView tvLableGautra;
    private TextView tvLableVatan;
    private EditText edtNoOfFamilyMember;
    private Spinner spCategory;
    private TextInputEditText edtStudy;
    private EditText edtBusinessAddr;
    private EditText edtOtherBusiness;
    private MyCustomAdapter33 myCustomAdapter33;
    private ArrayList<BusCategoryList> busCategoryLists;
    private ArrayList<BusCategoryList> copybusCategoryLists;
    private MyCustomAdapter44 myCustomAdapter44;
    private MyCustomAdapter55 myCustomAdapter55;
    private String main_cat_id = "";
    private String sub_cat_id = "";
    private String stydy_id = "";
    private String study = "";
    private Spinner spSubCategory;
    private Spinner spCategoryStudy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        searchMember = getSearchedMember(Constants.BASE_URL);
        registerMobile = registerMobileUser(Constants.BASE_URL);
        getVillageName = getVillageNameAPI(Constants.BASE_URL);
        getCategory = getCategoryProfession(Constants.BASE_URL);
        getBusinessCategory = getCategoryByBusiness(Constants.BASE_URL);
        villageLists = new ArrayList<>();
        gorLists = new ArrayList<>();
        getLists = new ArrayList<>();
        copybusCategoryLists = new ArrayList<>();
        categorylists = new ArrayList<>();
        getGorName = getGorNameAPI(Constants.BASE_URL);
        spVillageName = findViewById(R.id.spVillageName);
        spCity = findViewById(R.id.spCity);
        spDistrict = findViewById(R.id.spDistrict);
        spGor = findViewById(R.id.spGor);
        spGautra = findViewById(R.id.spGautra);
        spBloodGrp = findViewById(R.id.spBloodGrp);
        spSubCategory = findViewById(R.id.spSubCategory);
        spCategoryStudy = findViewById(R.id.spCategoryStudy);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtName = (TextInputEditText) findViewById(R.id.edtName);
        edtMobile = (TextInputEditText) findViewById(R.id.edtMobile);
        edtAddr = (TextInputEditText) findViewById(R.id.edtAddr);
        edtFamilyMemberCount = findViewById(R.id.edtOtherFamilyCount);
        edtOtherVillage = findViewById(R.id.edtOtherVillage);
        edtotherShakha = findViewById(R.id.edtOtherShakha);
        spCategory = findViewById(R.id.spCategory);
        edtStudy = findViewById(R.id.edtStudy);
        edtBusinessAddr = findViewById(R.id.edtBusinessAddr);
        edtOtherBusiness = findViewById(R.id.edtOther);

        progressBar = findViewById(R.id.progressBar);
        tvBirthDay = findViewById(R.id.tvBirthDay);
        getDistrictName = getDistrictNameAPI(Constants.BASE_URL);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmail);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        txtInName = (TextInputLayout) findViewById(R.id.txtInName);
        txtInAddr = (TextInputLayout) findViewById(R.id.txtInAddr);
        txtInEmail = (TextInputLayout) findViewById(R.id.txtInEmail);
        txtInMobile = (TextInputLayout) findViewById(R.id.txtInMobile);

        tvLableGautra = findViewById(R.id.tvLableGautra);
        tvLableVatan = findViewById(R.id.tvLableVillage);

        rgMaleFemale = findViewById(R.id.rgMaleFemale);
        rbMale = findViewById(R.id.rbMale);
        rbWomen = findViewById(R.id.rbFemale);

        progressBar.setVisibility(View.VISIBLE);

        getDistrictName.districtList().enqueue(new Callback<DistrictList>() {
            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getGetList().size() > 0) {
                        getLists = (ArrayList<GetList>) response.body().getGetList();
                        spDistrict.setAdapter(new MyCustomAdapterDistrict(RegistrationActivity.this,
                                R.layout.row_district_list, getLists));

                    }
                }

            }

            @Override
            public void onFailure(Call<DistrictList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final ArrayList<City> cityList = (ArrayList<City>) ((GetList) parent.getItemAtPosition(position)).getCity();
                spCity.setAdapter(new MyCustomAdapterCity(RegistrationActivity.this,
                        R.layout.row_city_list, cityList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getGorName.gorList().enqueue(new Callback<Gor>() {
            @Override
            public void onResponse(Call<Gor> call, Response<Gor> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    if (response.body().getGorList() != null && response.body().getGorList().size() > 0) {
                        gorLists = (ArrayList<GorList>) response.body().getGorList();
//                        spGor.setAdapter(new MyCustomAdapter22(RegistrationActivity.this,
//                                R.layout.row_village_list, (ArrayList<GorList>) response.body().getGorList()));
                    }

                }

            }

            @Override
            public void onFailure(Call<Gor> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        getBusinessCategory.getBusinessCatList().enqueue(new Callback<BusinessCategory>() {
            @Override
            public void onResponse(Call<BusinessCategory> call, Response<BusinessCategory> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    busCategoryLists = (ArrayList<BusCategoryList>) response.body().getCategoryList();

                    for (int i = 0; i < busCategoryLists.size(); i++) {
                        if (busCategoryLists.get(i).getMainCat().getId().equals("1")) {

                            myCustomAdapter55 = new MyCustomAdapter55(RegistrationActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) busCategoryLists.get(i).getSubCat());
                            spCategoryStudy.setAdapter(myCustomAdapter55);
                            spCategoryStudy.setSelection(busCategoryLists.get(i).getSubCat().size() - 1);

                        } else {
                            copybusCategoryLists.add(busCategoryLists.get(i));
                        }


                    }

                    if (copybusCategoryLists.size() > 0) {


                        myCustomAdapter33 = new MyCustomAdapter33(RegistrationActivity.this, R.layout.row_custom_spinner, copybusCategoryLists);
                        spCategory.setAdapter(myCustomAdapter33);
                    }
                }

            }

            @Override
            public void onFailure(Call<BusinessCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

//        getCategory.getCatList().enqueue(new Callback<Category>() {
//            @Override
//            public void onResponse(Call<Category> call, Response<Category> response) {
//
//                progressBar.setVisibility(View.GONE);
//                if (response.isSuccessful()) {
//
//                    categorylists = (ArrayList<Categorylist>) response.body().getCategorylist();
//                    if (categorylists.size() > 0) {
//
//                        myCustomAdapter33 = new MyCustomAdapter33(RegistrationActivity.this, R.layout.row_custom_spinner, categorylists);
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


        // Main business category
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                main_cat_id = ((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId();

                myCustomAdapter44 = new MyCustomAdapter44(RegistrationActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat());
                spSubCategory.setAdapter(myCustomAdapter44);


                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId().equals("10")) {
                    edtBusinessAddr.setVisibility(View.GONE);
                } else {

                    edtBusinessAddr.setVisibility(View.VISIBLE);

                }


                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getStatus().equals("2")) {
                    edtOtherBusiness.setVisibility(View.VISIBLE);
                    spSubCategory.setVisibility(View.GONE);

                } else {
                    spSubCategory.setVisibility(View.VISIBLE);
                    edtOtherBusiness.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // sub business category
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


        spCategoryStudy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spGautra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == getResources().getStringArray(R.array.shakha).length - 1) {
                    edtotherShakha.setVisibility(View.VISIBLE);
                } else {
                    edtotherShakha.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    edtOtherVillage.setVisibility(View.GONE);
                    spVillageName.setVisibility(View.VISIBLE);
                    edtFamilyMemberCount.setVisibility(View.GONE);
                    tvLableGautra.setVisibility(View.VISIBLE);
                    tvLableVatan.setVisibility(View.VISIBLE);
                    spGautra.setVisibility(View.VISIBLE);
                    getVillageName.villageList("2").enqueue(new Callback<VillagelistOF>() {
                        @Override
                        public void onResponse(Call<VillagelistOF> call, Response<VillagelistOF> response) {
                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().getVillageList() != null && response.body().getVillageList().size() > 0) {

                                    villageLists = (ArrayList<VillageList>) response.body().getVillageList();
                                    spVillageName.setAdapter(new MyCustomAdapter(RegistrationActivity.this,
                                            R.layout.row_village_name_list, (ArrayList<VillageList>) response.body().getVillageList()));

                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<VillagelistOF> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            villageLists.clear();
                            if (spVillageName.getAdapter() != null)
                                ((MyCustomAdapter) spVillageName.getAdapter()).notifyDataSetChanged();


                        }
                    });

                } else if (position == 1) {
                    tvLableGautra.setVisibility(View.GONE);
                    tvLableVatan.setVisibility(View.GONE);
                    edtOtherVillage.setVisibility(View.VISIBLE);
                    edtOtherVillage.requestFocus();
                    spVillageName.setVisibility(View.GONE);
                    edtFamilyMemberCount.setVisibility(View.VISIBLE);
                    spGautra.setVisibility(View.GONE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        edtMemNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
//                } else {
//
//
//                    Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        tvBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtEmail.setError(null);
                edtMobile.setError(null);
                edtName.setError(null);
                edtAddr.setError(null);

                if (SystemClock.elapsedRealtime() - lastclick < DOUBLE_TAP) {
                    Log.e("double tap", "");
                    return;
                }
                lastclick = SystemClock.elapsedRealtime();


                final String name = edtName.getText().toString().trim();
                final String mobile = edtMobile.getText().toString().trim();
                final String addr = edtAddr.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();

                if (spGor.getSelectedItemPosition() == 1 && edtOtherVillage.getText().toString().trim().isEmpty()) {
                    edtOtherVillage.requestFocus();
                    Toast.makeText(RegistrationActivity.this, "Enter Other village name if any", Toast.LENGTH_SHORT).show();
                } else if (spGor.getSelectedItemPosition() == 1 && edtFamilyMemberCount.getText().toString().trim().isEmpty()) {
                    edtFamilyMemberCount.requestFocus();
                    Toast.makeText(RegistrationActivity.this, "Enter No Of Family Member", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {

                    edtName.setError("Enter Name");

                } else if (tvBirthDay.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Select birthDate", Toast.LENGTH_SHORT).show();
                } else if (mobile.isEmpty() || mobile.length() < 10) {

                    edtMobile.setError("Enter mobile Number");
                } else if (spVillageName.getSelectedItem() == null || spGautra.getSelectedItem() == null) {
                    Toast.makeText(RegistrationActivity.this, "Unable to fetch village and gautra", Toast.LENGTH_SHORT).show();
                } else if (addr.isEmpty()) {

                    edtAddr.setError("Enter Address");
                } else if (spDistrict.getSelectedItem() == null || spCity.getSelectedItem() == null) {
                    Toast.makeText(RegistrationActivity.this, "Unable to fetch District and city name", Toast.LENGTH_SHORT).show();

                } else if (!email.isEmpty() && !Utils.isValidEmail(email)) {
                    edtEmail.setError("Enter valid email");

                } else if (villageLists.size() == 0 || spVillageName.getSelectedItem() == null) {
                    Toast.makeText(RegistrationActivity.this, "Unable to fetch village list", Toast.LENGTH_LONG).show();
                }
//                 else if(edtStudy.getText().toString().trim().isEmpty()){
//                    edtStudy.requestFocus();
//                    Toast.makeText(RegistrationActivity.this,"Enter your study detail",Toast.LENGTH_LONG).show();
//                }
//                else if(categorylists.size()==0 || spCategory.getSelectedItem()==null){
//                    Toast.makeText(RegistrationActivity.this, "Unable to fetch Business Categories..", Toast.LENGTH_LONG).show();
//
//                }
                else {
                    // all ok let's register

                    MobileRegistration mobileRegistration = new MobileRegistration();
                    mobileRegistration.setName(name);
                    mobileRegistration.setRelation("પોતે(મુખ્ય સભ્ય)");
                    mobileRegistration.setAddr(addr);
                    mobileRegistration.setEmail(email);
                    mobileRegistration.setMobile(mobile);
                    mobileRegistration.setGor(spGor.getSelectedItemPosition() == 0 ? "2" : "5");
                    mobileRegistration.setCity(((City) spCity.getSelectedItem()).getCity());
                    mobileRegistration.setDistrict(((GetList) spDistrict.getSelectedItem()).getDist().getDistrict());
                    mobileRegistration.setGender(rgMaleFemale.getCheckedRadioButtonId() == R.id.rbMale ? "1" : "2");
                    mobileRegistration.setDob(tvBirthDay.getText().toString());
                    mobileRegistration.setBloodGrp(spBloodGrp.getSelectedItem().toString());
                    mobileRegistration.setBus_cat(main_cat_id);
                    mobileRegistration.setBus_sub_cat(sub_cat_id);
                    mobileRegistration.setStudy_cat(stydy_id);


                    mobileRegistration.setStudy(edtStudy.getText().toString().trim());
                    mobileRegistration.setB_addr(edtBusinessAddr.getText().toString().trim());
                    mobileRegistration.setB_other(edtOtherBusiness.getText().toString().trim());


                    if (spGor.getSelectedItemPosition() == getResources().getStringArray(R.array.gor_list).length - 1) { // other
                        mobileRegistration.setNPlace(edtOtherVillage.getText().toString().trim());
                        mobileRegistration.setFamily_count(edtFamilyMemberCount.getText().toString().trim());
                        mobileRegistration.setGautra(edtotherShakha.getText().toString().trim());
                        mobileRegistration.setV_id("0");
                    } else {
                        //Modasiya  gor
                        mobileRegistration.setNPlace(((VillageList) spVillageName.getSelectedItem()).getName());
                        mobileRegistration.setFamily_count(edtFamilyMemberCount.getText().toString().trim().isEmpty() ? "0" : edtFamilyMemberCount.getText().toString().trim());
                        mobileRegistration.setGautra(spGautra.getSelectedItem().toString());
                        mobileRegistration.setV_id(((VillageList) spVillageName.getSelectedItem()).getId());
                    }


                    Log.e("json", new Gson().toJson(mobileRegistration));

                    SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, getApplicationContext());
                    String user_name = sharedPreferences.getString("ok", "");
                    String reg_in_progress = sharedPreferences.getString(Constants.REG_IN_PROGRESS, "");

                    if (!user_name.isEmpty() && !reg_in_progress.isEmpty()) {

                        Intent intent = new Intent(RegistrationActivity.this, ForgotPwd22Activity.class);
                        intent.putExtra(Constants.ID, reg_in_progress);
                        intent.putExtra(Constants.TYPE, "reg");
                        startActivity(intent);


                    } else {

                        showProgressDialog();
                        registerMobile.MakeRegister(mobileRegistration).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                if (progressDialog.isShowing()) progressDialog.dismiss();

                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {

                                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, getApplicationContext());
                                        editor.putString(Constants.REG_IN_PROGRESS, response.body().getId());
                                        editor.putString("ok", response.body().getMsg());
                                        editor.apply();

                                        scheduleAlarm();

                                        Intent intent = new Intent(RegistrationActivity.this, ForgotPwd22Activity.class);
                                        intent.putExtra(Constants.ID, response.body().getId());
                                        intent.putExtra(Constants.TYPE, "reg");
                                        startActivity(intent);

                                    } else if (response.body().getMsg().equalsIgnoreCase("xxx")) {
                                        Toast.makeText(RegistrationActivity.this, "Already registered", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {

                                        Toast.makeText(RegistrationActivity.this, "Not successfully saved", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                if (progressDialog.isShowing()) progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Not successfully saved", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }


                }


            }
        });

    }

    RegisterMobile registerMobileUser(String baseUrl) {

        return RetrofitClient.getClient(baseUrl).create(RegisterMobile.class);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    SearchMember getSearchedMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SearchMember.class);
    }

    public void scheduleAlarm() {
        // The time at which the alarm will be scheduled. Here the alarm is scheduled for 1 day from the current time.
        // We fetch the current time in milliseconds and add 1 day's time
        // i.e. 24*60*60*1000 = 86,400,000 milliseconds in a day.
        Long time = new GregorianCalendar().getTimeInMillis() + 24 * 60 * 60 * 1000;
        Long time2 = new GregorianCalendar().getTimeInMillis() + 2 * 60 * 1000;

        Calendar cal = Calendar.getInstance();
        // add 30 seconds to the calendar object
        cal.add(Calendar.MILLISECOND, 60 * 1000);
        // Create an Intent and set the class that will execute when the Alarm triggers. Here we have
        // specified AlarmReceiver in the Intent. The onReceive() method of this class will execute when the broadcast from your alarm is received.
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // Get the Alarm Service.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm for a particular time.
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), PendingIntent.getBroadcast(this, 1, intentAlarm, 0));
        //Toast.makeText(this, "Alarm Scheduled for Tomorrow", Toast.LENGTH_LONG).show();
    }

    GetVillageName getVillageNameAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetVillageName.class);
    }

    GetGorName getGorNameAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetGorName.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), RegistrationActivity.this);
    }

    GetDistrictName getDistrictNameAPI(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetDistrictName.class);
    }

    GetCategory getCategoryProfession(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategory.class);
    }

    GetBusinessCategory getCategoryByBusiness(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessCategory.class);
    }

    interface RegisterMobile {
        @POST("home/registrationapi/")
        Call<MyRes> MakeRegister(@Body MobileRegistration mobileRegistration);
    }

    interface SearchMember {

        @POST("member/searchdetailsapi/")
        @FormUrlEncoded()
        Call<AllMember> getMemberDetail(@Field("type") String type, @Field("search") String search);


    }

    interface GetVillageName {
        @POST("home/villagelistapi/")
        @FormUrlEncoded
        Call<VillagelistOF> villageList(@Field("gor") String gor);
    }

    public interface GetGorName {
        @POST("gor/gorlistapi/")
        Call<Gor> gorList();
    }

    interface GetDistrictName {
        @POST("home/citystateapi/")
        Call<DistrictList> districtList();
    }

    interface GetCategory {
        @POST("directory/categorylistapi/")
        Call<Category> getCatList();
    }

    interface GetBusinessCategory {
        @POST("home/jobtypeapi/")
        Call<BusinessCategory> getBusinessCatList();
    }

    public class MyCustomAdapter extends ArrayAdapter<VillageList> {

        private ArrayList<VillageList> objects;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<VillageList> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub

            this.objects = objects;
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
            View row = inflater.inflate(R.layout.row_village_name_list, parent, false);
            TextView tvVillageName = row.findViewById(R.id.tvVillageName);
            tvVillageName.setText(objects.get(position).getName());


            return row;
        }
    }

    public class MyCustomAdapter22 extends ArrayAdapter<GorList> {

        private ArrayList<GorList> objects;

        MyCustomAdapter22(Context context, int textViewResourceId,
                          ArrayList<GorList> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub

            this.objects = objects;
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
            View row = inflater.inflate(R.layout.row_village_list, parent, false);
            TextView tvVillageName = row.findViewById(R.id.tvVillageName);
            tvVillageName.setText(objects.get(position).getName());


            return row;
        }
    }

    public class MyCustomAdapterDistrict extends ArrayAdapter<GetList> {

        private ArrayList<GetList> objects;

        MyCustomAdapterDistrict(Context context, int textViewResourceId,
                                ArrayList<GetList> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub

            this.objects = objects;
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
            View row = inflater.inflate(R.layout.row_district_list, parent, false);
            TextView tvVillageName = row.findViewById(R.id.tvDistrictName);
            tvVillageName.setText(objects.get(position).getDist().getDistrict());


            return row;
        }
    }

    public class MyCustomAdapterCity extends ArrayAdapter<City> {

        private ArrayList<City> objects;

        MyCustomAdapterCity(Context context, int textViewResourceId,
                            ArrayList<City> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub

            this.objects = objects;
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
            View row = inflater.inflate(R.layout.row_city_list, parent, false);
            TextView tvVillageName = row.findViewById(R.id.tvCityName);
            tvVillageName.setText(objects.get(position).getCity());


            return row;
        }
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
