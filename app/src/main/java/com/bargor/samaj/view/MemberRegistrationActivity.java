package com.bargor.samaj.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
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

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.BusCategoryList;
import com.bargor.samaj.model.BusinessCategory;
import com.bargor.samaj.model.Category;
import com.bargor.samaj.model.Categorylist;
import com.bargor.samaj.model.MemberRegistration;
import com.bargor.samaj.model.MyRes;
import com.bargor.samaj.model.Part1;
import com.bargor.samaj.model.SubCat;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class MemberRegistrationActivity extends AppCompatActivity {
    static final String REL_WIFE = "પત્ની";
    static final String REL_HUSBAND = "પતિ";
    static final String REL_SON = "પુત્ર";
    static final String REL_DAUGHTER = "પુત્રી";
    static final String REL_DAUGHTER_LAW = "પુત્રવધુ";
    static final String REL_FATHER = "પિતા";
    static final String REL_MOTHER = "માતા";
    static final String REL_BROTHER = "ભાઈ";
    static final String REL_SISTER = "બહેન";
    static final String REL_GRANDSON = "પૌત્ર";
    static final String REL_GRANDDAUGHTER = "પૌત્રી";
    static final String REL_BHABHI = "ભાભી";
    static final String REL_KAKA = "કાકા";
    static final String REL_KAKI = "કાકી";
    private static final String IMAGE_DIRECTORY = "/17GorYuva";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;
    private static final String FILE_NAME = "profile";
    private final long DOUBLE_TAP = 1500;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private LinearLayout llFirst;
    private TextInputEditText edtPart1Name;
    private TextInputEditText edtPart1Addr;
    private TextInputEditText edtpart1Mobile;
    private TextInputEditText edtPart1Email;
    private TextView tvpart1DOB;
    private TextView tvSelectPhoto;
    private ImageView ivPhoto;
    private int GALLERY = 1, CAMERA = 2;
    private Button btnPart1SelectBirth;
    private Button btnNext11;
    private LinearLayout llSecond;
    private Button btnBack22;
    private Button btnNext22;
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
            //dob = sdf.format(endCalender.getTime());


        }

    };
    private Spinner spShakh;
    private Spinner spBlood;
    private Spinner spSubCategory;
    private Spinner spCategoryStudy;
    private GetBusinessCategory getBusinessCategory;
    private Spinner spCategory;
    private GetCategory getCategory;
    private ArrayList<Categorylist> categorylists;
    private ArrayList<BusCategoryList> busCategoryLists;
    private ArrayList<BusCategoryList> copybusCategoryLists;
    private String gender;
    private RadioGroup rgIsMarried;
    private RadioButton rbParinit;
    private RadioButton rbunMarried;
    private Uri outputUri;
    private Uri fileUri;
    private String path;
    private Bitmap profileBitmap;
    private byte[] byteArray;
    private Spinner spRelation;
    private ProgressDialog progressDialog;
    private String l_id;
    private String gor;
    private MemberRegAPi memberRegAPi;
    private MemberRegistration memberRegistration;
    private HashMap<String, String> relationHashMap = new HashMap<>();
    private ArrayList<String> relationName = new ArrayList<>();
    private RadioButton rbVidhva;
    private String parinit = "";
    private String addr;
    private String family_count;
    private TextInputEditText edtStudy;
    private EditText edtBusinessAddr;
    private EditText edtOtherBusiness;
    private MyCustomAdapter33 myCustomAdapter33;
    private MyCustomAdapter44 myCustomAdapter44;
    private MyCustomAdapter55 myCustomAdapter55;
    private ProgressBar progressBar;
    private TextView tvLableShakh;
    private TextView tvLableBlood;
    private String main_cat_id = "";
    private String sub_cat_id = "";
    private String stydy_id = "";
    private String study = "";

    /**
     *
     * <item>પત્ની</item>
     <item>પતિ</item>
     <item>પુત્ર</item>
     <item>પુત્રી</item>
     <item>પુત્રવધુ</item>
     <item>પિતા</item>
     <item>માતા</item>
     <item>ભાઈ</item>
     <item>બહેન</item>
     <item>પૌત્ર</item>
     <item>પૌત્રી</item>
     *
     *
     *
     */
    /**
     * Converts bitmap to the byte array without compression
     *
     * @param bitmap source bitmap
     * @return result byte array
     */
    public static byte[] convertBitmapToByteArrayUncompressed(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }


    /**
     * ***********************************************************
     */

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY);


        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY, "failed to create directory");
                return null;
            }
        }

        // Create a media file name

        File mediaFile = null;

        try {
            mediaFile = File.createTempFile(FILE_NAME, ".jpg", mediaStorageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaFile;
    }

    /**
     * Create a file Uri for saving an image or video
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copybusCategoryLists = new ArrayList<>();

        relationHashMap.put("1", REL_WIFE);
        relationHashMap.put("2", REL_HUSBAND);
        relationHashMap.put("3", REL_SON);
        relationHashMap.put("4", REL_DAUGHTER);
        relationHashMap.put("5", REL_DAUGHTER_LAW);
        relationHashMap.put("6", REL_FATHER);
        relationHashMap.put("7", REL_MOTHER);
        relationHashMap.put("8", REL_BROTHER);
        relationHashMap.put("9", REL_SISTER);
        relationHashMap.put("10", REL_GRANDSON);
        relationHashMap.put("11", REL_GRANDDAUGHTER);
        relationHashMap.put("12", REL_BHABHI);
        relationHashMap.put("13", REL_KAKA);
        relationHashMap.put("14", REL_KAKI);

        outputUri = Uri.parse("file://".concat("/storage/emulated/0/17GorYuva/profile22.jpg"));

        for (int i = 1; i <= relationHashMap.size(); i++) {
            relationName.add(relationHashMap.get(String.valueOf(i)));
        }

//
//        ArrayAdapter<String> adapter =new ArrayAdapter<String>(MemberRegistrationActivity.this,android.R.layout.simple_spinner_item, relationName);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, MemberRegistrationActivity.this);
        l_id = sharedPreferences.getString(Constants.ID, null);
        gor = sharedPreferences.getString(Constants.GOR, null);
        setContentView(R.layout.activity_member_registration);
        memberRegAPi = addMember(Constants.BASE_URL);
        getCategory = getCategoryProfession(Constants.BASE_URL);
        categorylists = new ArrayList<>();
        busCategoryLists = new ArrayList<>();
        addr = sharedPreferences.getString("addr", "");
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        family_count = sharedPreferences.getString("family_count", null);
        getBusinessCategory = getCategoryByBusiness(Constants.BASE_URL);
        memberRegistration = new MemberRegistration();
        progressBar = findViewById(R.id.progressBar);
        spShakh = findViewById(R.id.spCategoryShakh);
        spBlood = findViewById(R.id.spCategoryBlood);
        tvLableShakh = findViewById(R.id.tvLableSelectShakh);
        tvLableBlood = findViewById(R.id.tvLableBlood);


        tvLableBlood.setVisibility(View.VISIBLE);
        tvLableShakh.setVisibility(View.GONE);
        spBlood.setVisibility(View.VISIBLE);
        spShakh.setVisibility(View.GONE);


        llFirst = (LinearLayout) findViewById(R.id.llFirst);
        spRelation = findViewById(R.id.spRelation);
        tvSelectPhoto = findViewById(R.id.tvSelectPhoto);
        ivPhoto = findViewById(R.id.ivPhotoGraph);
        edtPart1Name = (TextInputEditText) findViewById(R.id.edtPart1Name);
        edtPart1Addr = (TextInputEditText) findViewById(R.id.edtPart1Addr);
        edtpart1Mobile = (TextInputEditText) findViewById(R.id.edtpart1Mobile);
        edtPart1Email = (TextInputEditText) findViewById(R.id.edtpart1Email);
        tvpart1DOB = (TextView) findViewById(R.id.tvpart1DOB);
        rgIsMarried = findViewById(R.id.rgIsMarried);
        rbParinit = findViewById(R.id.rbParinit);
        rbunMarried = findViewById(R.id.rbUnmaried);
        rbVidhva = findViewById(R.id.rbVidhva);
        btnPart1SelectBirth = (Button) findViewById(R.id.btnPart1SelectBirth);
        spCategory = findViewById(R.id.spCategory);
        spSubCategory = findViewById(R.id.spSubCategory);
        spCategoryStudy = findViewById(R.id.spCategoryStudy);
        edtStudy = findViewById(R.id.edtStudy);
        edtBusinessAddr = findViewById(R.id.edtBusinessAddr);
        edtOtherBusiness = findViewById(R.id.edtOther);
        edtOtherBusiness.setVisibility(View.GONE);
        edtPart1Addr.setEnabled(false);
        edtPart1Addr.setClickable(false);

        btnNext11 = (Button) findViewById(R.id.btnNext11);
        llSecond = (LinearLayout) findViewById(R.id.llSecond);

        btnBack22 = (Button) findViewById(R.id.btnBack22);
        btnNext22 = (Button) findViewById(R.id.btnNext22);

        edtPart1Addr.setText(addr);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// 1 Male 2 Female
                switch (position) {

                    case 0:      //wife
                        gender = "2";
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;
                    case 1:  //husband
                        gender = "1";
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;
                    case 2:  //son
                        gender = "1";
                        rbParinit.setChecked(false);
                        rbunMarried.setChecked(true);
                        rbunMarried.setEnabled(true);
                        parinit = "0";
                        break;
                    case 3: //daughter
                        gender = "2";
                        rbParinit.setChecked(false);
                        rbunMarried.setChecked(true);
                        rbunMarried.setEnabled(true);
                        parinit = "0";
                        break;
                    case 4:
                        gender = "2";//daughter_law
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;
                    case 5:   //
                        gender = "1"; //father
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;
                    case 6:
                        gender = "2";  // mother
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;
                    case 7:
                        gender = "1";  //brother
                        rbParinit.setChecked(false);
                        rbunMarried.setChecked(true);
                        rbunMarried.setEnabled(true);
                        parinit = "0";
                        break;
                    case 8:
                        gender = "2"; //sister
                        rbParinit.setChecked(false);
                        rbunMarried.setChecked(true);
                        rbunMarried.setEnabled(true);
                        parinit = "0";
                        break;
                    case 9:
                        gender = "1"; //grand_son
                        rbParinit.setChecked(false);
                        rbunMarried.setChecked(true);
                        rbunMarried.setEnabled(true);
                        parinit = "0";
                        break;
                    case 10:
                        gender = "2"; //grand_daughter
                        rbParinit.setChecked(false);
                        rbunMarried.setChecked(true);
                        rbunMarried.setEnabled(true);
                        parinit = "0";
                        break;

                    case 11:

                        gender = "2"; //bhabhi
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";

                        break;

                    case 12:

                        gender = "1"; //kaka
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;

                    case 13:
                        gender = "2"; //kaki
                        rbParinit.setChecked(true);
                        rbunMarried.setChecked(false);
                        rbunMarried.setEnabled(false);
                        parinit = "1";
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                            myCustomAdapter55 = new MyCustomAdapter55(MemberRegistrationActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) busCategoryLists.get(i).getSubCat());
                            spCategoryStudy.setAdapter(myCustomAdapter55);
                            spCategoryStudy.setSelection(busCategoryLists.get(i).getSubCat().size() - 1);

                        } else {
                            copybusCategoryLists.add(busCategoryLists.get(i));
                        }


                    }

                    if (copybusCategoryLists.size() > 0) {


                        myCustomAdapter33 = new MyCustomAdapter33(MemberRegistrationActivity.this, R.layout.row_custom_spinner, copybusCategoryLists);
                        spCategory.setAdapter(myCustomAdapter33);
                    }
                }

            }

            @Override
            public void onFailure(Call<BusinessCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


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


        // Main business category
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                main_cat_id = ((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId();

                myCustomAdapter44 = new MyCustomAdapter44(MemberRegistrationActivity.this, R.layout.row_custom_spinner, (ArrayList<SubCat>) ((BusCategoryList) parent.getItemAtPosition(position)).getSubCat());
                spSubCategory.setAdapter(myCustomAdapter44);


                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getId().equals("10")) {
                    edtBusinessAddr.setVisibility(View.GONE);
                } else {

                    edtBusinessAddr.setVisibility(View.VISIBLE);

                }


                if (((BusCategoryList) parent.getItemAtPosition(position)).getMainCat().getStatus().equals("2")) {
                    edtOtherBusiness.setVisibility(View.VISIBLE);
                    spSubCategory.setVisibility(View.GONE);
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


        //study detail
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


//        getCategory.getCatList().enqueue(new Callback<Category>() {
//            @Override
//            public void onResponse(Call<Category> call, Response<Category> response) {
//
//
//                if (response.isSuccessful()) {
//
//                    categorylists = (ArrayList<Categorylist>) response.body().getCategorylist();
//                    if (categorylists.size() > 0) {
//
//                        myCustomAdapter33 = new MyCustomAdapter33(MemberRegistrationActivity.this, R.layout.row_custom_spinner, categorylists);
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
//            }
//        });


        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // camera picker dialog

                if (ContextCompat.checkSelfPermission(MemberRegistrationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MemberRegistrationActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MemberRegistrationActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {

                    showPictureDialog();
                }
            }
        });

        rgIsMarried.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rbParinit:
                        parinit = "1";  // married
                        break;
                    case R.id.rbUnmaried:
                        parinit = "0"; // unmarried
                        break;
                    case R.id.rbVidhva:
                        parinit = "2";   //vidhva
                        break;
                }

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (llFirst.getVisibility() == View.VISIBLE) {
                    finish();
                } else {
                    llFirst.setVisibility(View.VISIBLE);
                    llSecond.setVisibility(View.GONE);
                }


            }
        });

        btnPart1SelectBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MemberRegistrationActivity.this, endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });


        btnNext11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPart1Valid()) {

                    showProgressDialog();

                    final String relation = spRelation.getSelectedItem().toString();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", edtPart1Name.getText().toString().trim());
                        jsonObject.put("email", edtPart1Email.getText().toString().trim());
                        jsonObject.put("mobile", edtpart1Mobile.getText().toString().trim());
                        jsonObject.put("relation", relation);
                        jsonObject.put("dob", tvpart1DOB.getText().toString().trim());
                        jsonObject.put("addr", edtPart1Addr.getText().toString().trim());
                        jsonObject.put("parinit", parinit);
                        jsonObject.put("gender", gender);
                        jsonObject.put("gor", gor);
                        jsonObject.put("study", edtStudy.getText().toString().trim());
                        jsonObject.put("study_cat", stydy_id);
                        jsonObject.put("bus_cat", main_cat_id);
                        jsonObject.put("bus_sub_cat", sub_cat_id);
                        jsonObject.put("blood", spBlood.getSelectedItem().toString());

                        jsonObject.put("b_addr", edtBusinessAddr.getText().toString().trim());
                        jsonObject.put("b_other", edtOtherBusiness.getText().toString().trim());

                        Log.e("member json", jsonObject.toString());

                        RequestBody lid = RequestBody.create(MediaType.parse("text/plain"), l_id);
                        RequestBody familyJson = RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());
                        memberRegAPi.registerMember(lid, familyJson).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                btnNext22.setEnabled(true);
                                if (response.isSuccessful()) {
                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
                                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, MemberRegistrationActivity.this);

                                        editor.putString("gr_count", response.body().getGroup_count()).apply();
                                        Toast.makeText(MemberRegistrationActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MemberRegistrationActivity.this, EditImageActivity.class);
                                        intent.putExtra("from", "from");
                                        intent.putExtra("id", response.body().getId());
                                        startActivity(intent);
                                        finish();
                                    } else if (response.body().getMsg().equalsIgnoreCase("false")) {
                                        Toast.makeText(MemberRegistrationActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else if (response.body().getMsg().equalsIgnoreCase("xxx")) {
                                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, MemberRegistrationActivity.this);

                                        editor.putString("gr_count", response.body().getGroup_count()).apply();
                                        Toast.makeText(MemberRegistrationActivity.this, "Mobile Already Registered", Toast.LENGTH_LONG).show();
                                       // finish();

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                btnNext22.setEnabled(true);
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(MemberRegistrationActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();

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

                    //call add familly


                    btnNext22.setEnabled(false);
                    File file = new File(path);

                    showProgressDialog();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody lid = RequestBody.create(MediaType.parse("text/plain"), l_id);

                    final String relation = spRelation.getSelectedItem().toString();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", edtPart1Name.getText().toString().trim());
                        jsonObject.put("email", edtPart1Email.getText().toString().trim());
                        jsonObject.put("mobile", edtpart1Mobile.getText().toString().trim());
                        jsonObject.put("relation", relation);
                        jsonObject.put("dob", tvpart1DOB.getText().toString().trim());
                        jsonObject.put("addr", edtPart1Addr.getText().toString().trim());
                        jsonObject.put("parinit", parinit);
                        jsonObject.put("gender", gender);
                        jsonObject.put("gor", gor);
                        jsonObject.put("study", edtStudy.getText().toString().trim());
                        jsonObject.put("study_cat", stydy_id);
                        jsonObject.put("bus_cat", main_cat_id);
                        jsonObject.put("bus_sub_cat", sub_cat_id);

                        jsonObject.put("b_addr", edtBusinessAddr.getText().toString().trim());
                        jsonObject.put("b_other", edtOtherBusiness.getText().toString().trim());

                        Log.e("member json", jsonObject.toString());

                        RequestBody familyJson = RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());
//                        memberRegAPi.registerMember(fileToUpload, filename, lid, familyJson).enqueue(new Callback<MyRes>() {
//                            @Override
//                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
//                                btnNext22.setEnabled(true);
//                                if (response.isSuccessful()) {
//                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
//                                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, MemberRegistrationActivity.this);
//
//                                        editor.putString("gr_count", response.body().getGroup_count()).apply();
//                                        Toast.makeText(MemberRegistrationActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
//                                        finish();
//                                    } else if (response.body().getMsg().equalsIgnoreCase("false")) {
//                                        Toast.makeText(MemberRegistrationActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();
//                                        finish();
//                                    } else if (response.body().getMsg().equalsIgnoreCase("xxx")) {
//                                        SharedPreferences.Editor editor = Utils.writeToPreference(Constants.MY_PREF, MemberRegistrationActivity.this);
//
//                                        editor.putString("gr_count", response.body().getGroup_count()).apply();
//                                        Toast.makeText(MemberRegistrationActivity.this, "Already Registered", Toast.LENGTH_LONG).show();
//                                        finish();
//
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<MyRes> call, Throwable t) {
//                                btnNext22.setEnabled(true);
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
//                                Toast.makeText(MemberRegistrationActivity.this, "Error Occured..try Again", Toast.LENGTH_LONG).show();
//
//                            }
//                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //


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
        } else if (!mobile.isEmpty() && mobile.length() < 10) {
            edtpart1Mobile.setError("Enter mobile");
            return false;
        } else if (dob.isEmpty()) {
            Toast.makeText(MemberRegistrationActivity.this, "Please Select birth date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spCategory.getSelectedItem() == null) {
            Toast.makeText(MemberRegistrationActivity.this, "Unable to fetch business category", Toast.LENGTH_LONG).show();
            return false;

        } else if (spSubCategory.getSelectedItem() == null) {
            Toast.makeText(MemberRegistrationActivity.this, "Unable to fetch business category", Toast.LENGTH_LONG).show();
            return false;
        } else {

            Part1 part1 = new Part1();
            part1.setName(name);
            part1.setAddr(addr);
            part1.setDob(dob);
            part1.setEmail(edtPart1Email.getText().toString().trim());
            part1.setMobile(mobile);
            part1.setL_id(l_id);
            memberRegistration.setPart1(part1);

            return true;
        }


    }

    private boolean isPart2Valid() {

//        if(gor.equals("5") && family_count!=null &&  ){
//
//        }

        return path != null;
    }

    MemberRegAPi addMember(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(MemberRegAPi.class);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(MemberRegistrationActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        File file = getOutputMediaFile();

        if (file != null) {

            fileUri = FileProvider.getUriForFile(this,
                    "com.goryuva.com.bargor.samaj.fileprovider",
                    file);

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    path = saveImage(bitmap);
//                    profileBitmap = bitmap;
//                    ivPhoto.setImageBitmap(bitmap);
                Crop.of(contentURI, outputUri).asSquare().start(MemberRegistrationActivity.this);

            }

        } else if (requestCode == CAMERA) {

//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            path = saveImage(thumbnail);
//
//            ivPhoto.setImageBitmap(thumbnail);
//            profileBitmap = thumbnail;
            if (fileUri != null) {
                Crop.of(fileUri, outputUri).asSquare().start(MemberRegistrationActivity.this);
            }


        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {


            path = saveImage(BitmapFactory.decodeFile(outputUri.getPath()));

            if (path != null) {
                ivPhoto.setImageURI(Uri.parse(path));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    showPictureDialog();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    Toast.makeText(MemberRegistrationActivity.this, "Permission Required", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public String saveImage(Bitmap myBitmap) {

        if (myBitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            // have the object build the directory structure, if needed.

            deleteDir(wallpaperDirectory);

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }


            try {
                File f = new File(wallpaperDirectory, FILE_NAME + ".jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(MemberRegistrationActivity.this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

                return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return "";
    }

    GetCategory getCategoryProfession(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategory.class);
    }

    GetBusinessCategory getCategoryByBusiness(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetBusinessCategory.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftKeyBoard(this.getCurrentFocus(), MemberRegistrationActivity.this);
    }

    /**
     * **************************************
     */

    interface MemberRegAPi {
        @Multipart
        @POST("user/addfamilyapi/")
        Call<MyRes> registerMember(@Part("id") RequestBody id,
                                   @Part("family") RequestBody family);
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
