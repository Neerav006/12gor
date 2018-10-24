package com.bargor.samaj.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.R;
import com.bargor.samaj.Utils.Utils;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.ImageDetail;
import com.bargor.samaj.model.MyRes;
import com.facebook.drawee.view.SimpleDraweeView;
import com.soundcloud.android.crop.Crop;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class EditImageActivity extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/17GorYuva";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;
    private static final String FILE_NAME = "profile";
    private final int GALLERY = 1, CAMERA = 2;
    private Uri fileUri;
    private Uri output;
    private String path;
    private long lastclick = 0;
    private int PROFILE_TYPE;
    private HashMap<Integer, String> myPaths;
    private String reg_id;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private Uri outputUri;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private Button btnPart1Profile;
    private TextView tvPhoto1;
    private TextView tvPhoto1Path;
    private TextView tvstatus1;
    private Button btnUploadPic1;
    private Button btnPart2Profile;
    private TextView tvPhoto2;
    private TextView tvPhoto2Path;
    private TextView tvstatus2;
    private Button btnUploadPic2;
    private Button btnPart3Profile;
    private TextView tvPhoto3;
    private TextView tvPhoto3Path;
    private TextView tvstatus3;
    private Button btnUploadPic3;
    private ProgressDialog progressDialog;
    private SimpleDraweeView ivPhoto1;
    private SimpleDraweeView ivPhoto2;
    private SimpleDraweeView ivPhoto3;
    private UploadImageToServer uploadImageToServer;
    private ViewImageApi viewImageApi;
    private String mid;
    private String iv1Name;
    private String iv2Name;
    private String iv3Name;

    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }


    /**
     * ***************************************************
     *
     *  File storage and directory functions
     *
     *   Gallery and Camera
     *
     */

    /**
     * Create a File for saving an image or video
     */
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

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputUri = Uri.parse("file://".concat("/storage/emulated/0/17GorYuva/profile22.jpg"));

        SharedPreferences sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, EditImageActivity.this);

        if (getIntent().getStringExtra("from") != null) {
            mid = getIntent().getStringExtra("id");
        } else {
            mid = sharedPreferences.getString(Constants.ID, null);
        }

        setContentView(R.layout.activity_edit_image);

        uploadImageToServer = uploadImage(Constants.BASE_URL);
        viewImageApi = getImage(Constants.BASE_URL);

        if (getIntent() != null) {
            reg_id = getIntent().getStringExtra(Constants.ID);
        }

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPart1Profile = (Button) findViewById(R.id.btnPart1Profile);
        tvPhoto1 = (TextView) findViewById(R.id.tvPhoto1);
        tvPhoto1Path = (TextView) findViewById(R.id.tvPhoto1Path);
        tvstatus1 = (TextView) findViewById(R.id.tvstatus1);
        btnUploadPic1 = (Button) findViewById(R.id.btnUploadPic1);
        btnPart2Profile = (Button) findViewById(R.id.btnPart2Profile);
        tvPhoto2 = (TextView) findViewById(R.id.tvPhoto2);
        tvPhoto2Path = (TextView) findViewById(R.id.tvPhoto2Path);
        tvstatus2 = (TextView) findViewById(R.id.tvstatus2);
        btnUploadPic2 = (Button) findViewById(R.id.btnUploadPic2);
        btnPart3Profile = (Button) findViewById(R.id.btnPart3Profile);
        tvPhoto3 = (TextView) findViewById(R.id.tvPhoto3);
        tvPhoto3Path = (TextView) findViewById(R.id.tvPhoto3Path);
        tvstatus3 = (TextView) findViewById(R.id.tvstatus3);
        btnUploadPic3 = (Button) findViewById(R.id.btnUploadPic3);
        ivPhoto1 = (SimpleDraweeView) findViewById(R.id.ivPhoto1);


        myPaths = new HashMap<>();

        viewImageApi.viewImage(mid).enqueue(new Callback<ImageDetail>() {
            @Override
            public void onResponse(Call<ImageDetail> call, Response<ImageDetail> response) {
                if (response.isSuccessful()) {

                    ivPhoto1.setImageURI(Uri.parse(VIEW_PATH.concat(response.body().getPhoto())));
                    iv1Name = response.body().getPhoto();

                }
            }

            @Override
            public void onFailure(Call<ImageDetail> call, Throwable t) {

            }
        });


        btnPart1Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PROFILE_TYPE = 1;

                if (ContextCompat.checkSelfPermission(EditImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(EditImageActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditImageActivity.this);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("Write storage permission is necessary ");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(EditImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();

                    } else {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(EditImageActivity.this,
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


        btnUploadPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (path != null) {
                    File file = new File(path);


                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody regBody = RequestBody.create(MediaType.parse("text/plain"), mid);
                    RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "1");
                    RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), iv1Name);

                    if (iv1Name != null) {
                        showProgressDialog();
                        uploadImageToServer.uploadFile(fileToUpload, filename, regBody).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }


                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {

                                        tvstatus1.setText("Successfully uploaded");
                                        btnUploadPic1.setVisibility(View.GONE);

                                        Intent intent = new Intent(EditImageActivity.this, FamilyMemberList.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(EditImageActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                } else {
                                    Toast.makeText(EditImageActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                Toast.makeText(EditImageActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                finish();
                            }
                        });

                    }
                }


            }
        });


    }

    /**
     * *******************************************
     * <p>
     * File and camera code
     * ********************************************
     */

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(EditImageActivity.this);
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
                    "com.bargor.samaj.fileprovider",
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

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                path = saveImage(bitmap);

                Crop.of(contentURI, outputUri).asSquare().start(EditImageActivity.this);

            }

        } else if (requestCode == CAMERA) {

//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            path = saveImage(thumbnail);
//
//            ivPhoto.setImageBitmap(thumbnail);
//            profileBitmap = thumbnail;
            if (fileUri != null) {
                Crop.of(fileUri, outputUri).asSquare().start(EditImageActivity.this);
            }


        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {

            path = saveImage(getResizedBitmap(BitmapFactory.decodeFile(outputUri.getPath()), 540, 720));

            if (path != null) {
                ivPhoto1.setImageURI(Uri.parse(path));
                tvPhoto1Path.setText(path);
            }

        }
    }

    public String saveImage(Bitmap myBitmap) {
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
            MediaScannerConnection.scanFile(EditImageActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP

        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }

    UploadImageToServer uploadImage(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(UploadImageToServer.class);
    }

    ViewImageApi getImage(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(ViewImageApi.class);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(EditImageActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
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

                    Toast.makeText(EditImageActivity.this, "Permission Required", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    //  member/uploadimageapi/
    public interface UploadImageToServer {
        @Multipart
        @POST("member/uploadimageapi/")
        Call<MyRes> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name, @Part("id") RequestBody mid
        );
    }

    interface ViewImageApi {
        @POST("member/viewimgapi/")
        @FormUrlEncoded
        Call<ImageDetail> viewImage(@Field("id") String mid);
    }


}
