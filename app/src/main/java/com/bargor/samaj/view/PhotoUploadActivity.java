package com.bargor.samaj.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bargor.samaj.R;
import com.bargor.samaj.common.RetrofitClient;
import com.bargor.samaj.cons.Constants;
import com.bargor.samaj.model.MyRes;
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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class PhotoUploadActivity extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/Adatham";
    private static final String FILE_NAME = "profile";
    private final int GALLERY = 1, CAMERA = 2;
    private String VIEW_PATH = "http://12gor.codefuelindia.com/profile/";
    private Uri fileUri;
    private Uri output;
    private String path;
    private long lastclick = 0;
    private int PROFILE_TYPE;
    private HashMap<Integer, String> myPaths;
    private String reg_id;

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

    private TextView tvPhoto4;
    private TextView tvPhoto4Path;
    private TextView tvstatus4;
    private Button btnUploadPic4;
    private Button btnPart4Profile;

    private CardView cvCheque;

    private boolean isCheque = false;


    private ProgressDialog progressDialog;

    private UploadImageToServer uploadImageToServer;


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
        setContentView(R.layout.activity_photo_upload);

        uploadImageToServer = uploadImage(Constants.BASE_URL);

        if (getIntent() != null) {
            reg_id = getIntent().getStringExtra(Constants.ID);
            isCheque = getIntent().getBooleanExtra("c_no", false);
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

        cvCheque = (CardView) findViewById(R.id.cvCheque);

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

        tvPhoto4 = (TextView) findViewById(R.id.tvPhoto4);
        tvPhoto4Path = (TextView) findViewById(R.id.tvPhoto4Path);
        tvstatus4 = (TextView) findViewById(R.id.tvstatus4);
        btnUploadPic4 = (Button) findViewById(R.id.btnUploadPic4);
        btnPart4Profile = (Button) findViewById(R.id.btnPart4Profile);


        myPaths = new HashMap<>();


        btnPart1Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PROFILE_TYPE = 1;
                showPictureDialog();
            }
        });

        btnPart2Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PROFILE_TYPE = 2;
                showPictureDialog();
            }
        });

        btnPart3Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PROFILE_TYPE = 3;
                showPictureDialog();
            }
        });

        if (isCheque) {
            cvCheque.setVisibility(View.VISIBLE);

            btnPart4Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PROFILE_TYPE = 4;
                    showPictureDialog();
                }
            });


            btnUploadPic4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myPaths.get(4) != null && !myPaths.get(4).isEmpty()) {
                        File file = new File(myPaths.get(4));

                        showProgressDialog();

                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                        RequestBody regBody = RequestBody.create(MediaType.parse("text/plain"), reg_id);
                        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "4");

                        uploadImageToServer.uploadFile(fileToUpload, filename, regBody, typeBody).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }


                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
                                        tvstatus4.setText("Successfully uploaded");
                                        btnUploadPic4.setVisibility(View.GONE);

                                    } else {
                                        Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                }
            });


        }


        btnUploadPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myPaths.get(1) != null && !myPaths.get(1).isEmpty()) {
                    File file = new File(myPaths.get(1));

                    showProgressDialog();

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody regBody = RequestBody.create(MediaType.parse("text/plain"), reg_id);
                    RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "1");

                    uploadImageToServer.uploadFile(fileToUpload, filename, regBody, typeBody).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {

                                    tvstatus1.setText("Successfully uploaded");
                                    btnUploadPic1.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });
                }


            }
        });

        btnUploadPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPaths.get(2) != null && !myPaths.get(2).isEmpty()) {
                    File file = new File(myPaths.get(2));

                    showProgressDialog();

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody regBody = RequestBody.create(MediaType.parse("text/plain"), reg_id);
                    RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "2");

                    uploadImageToServer.uploadFile(fileToUpload, filename, regBody, typeBody).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {
                                    tvstatus2.setText("Successfully uploaded");
                                    btnUploadPic2.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        btnUploadPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPaths.get(3) != null && !myPaths.get(3).isEmpty()) {
                    File file = new File(myPaths.get(3));

                    showProgressDialog();

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody regBody = RequestBody.create(MediaType.parse("text/plain"), reg_id);
                    RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "3");

                    uploadImageToServer.uploadFile(fileToUpload, filename, regBody, typeBody).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {
                                    tvstatus3.setText("Successfully uploaded");
                                    btnUploadPic3.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            Toast.makeText(PhotoUploadActivity.this, Constants.ERROR, Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });
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
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(PhotoUploadActivity.this);
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

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();

        if (fileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (PROFILE_TYPE) {

            case 1:
                output = Uri.parse("file://".concat("/storage/emulated/0/Adatham/profile1.jpg"));
                break;
            case 2:
                output = Uri.parse("file://".concat("/storage/emulated/0/Adatham/profile2.jpg"));
                break;
            case 3:
                output = Uri.parse("file://".concat("/storage/emulated/0/Adatham/profile3.jpg"));
                break;
            case 4:
                output = Uri.parse("file://".concat("/storage/emulated/0/Adatham/profile4.jpg"));
                break;

        }


        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                Crop.of(contentURI, output).asSquare().start(PhotoUploadActivity.this);
            }
        } else if (requestCode == CAMERA) {
            if (fileUri != null) {
                Crop.of(fileUri, output).asSquare().start(PhotoUploadActivity.this);
            }
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(output.getPath());
            switch (PROFILE_TYPE) {
                case 1:
                    path = saveImage(getResizedBitmap(bitmap, 200, 200), "Profile1");
                    break;
                case 2:
                    path = saveImage(getResizedBitmap(bitmap, 200, 200), "Profile2");
                    break;
                case 3:
                    path = saveImage(getResizedBitmap(bitmap, 200, 200), "Profile3");
                    break;
                case 4:
                    path = saveImage(getResizedBitmap(bitmap, 200, 200), "Profile4");
                    break;
            }
            if (path != null) {
                myPaths.put(PROFILE_TYPE, path);
                switch (PROFILE_TYPE) {
                    case 1:
                        if (myPaths.get(PROFILE_TYPE) != null && !myPaths.get(PROFILE_TYPE).isEmpty()) {
                            tvPhoto1Path.setText(myPaths.get(PROFILE_TYPE));
                        }
                        break;
                    case 2:
                        if (myPaths.get(PROFILE_TYPE) != null && !myPaths.get(PROFILE_TYPE).isEmpty()) {
                            tvPhoto2Path.setText(myPaths.get(PROFILE_TYPE));
                        }
                        break;
                    case 3:
                        if (myPaths.get(PROFILE_TYPE) != null && !myPaths.get(PROFILE_TYPE).isEmpty()) {
                            tvPhoto3Path.setText(myPaths.get(PROFILE_TYPE));
                        }
                        break;

                    case 4:
                        if (myPaths.get(PROFILE_TYPE) != null && !myPaths.get(PROFILE_TYPE).isEmpty()) {
                            tvPhoto4Path.setText(myPaths.get(PROFILE_TYPE));
                        }
                        break;


                }


            }


        }
    }

    public String saveImage(Bitmap myBitmap, String fileName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, fileName + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(PhotoUploadActivity.this,
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
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    UploadImageToServer uploadImage(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(UploadImageToServer.class);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(PhotoUploadActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public interface UploadImageToServer {
        @Multipart
        @POST("member/uploadimageapi/")
        Call<MyRes> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name, @Part("id") RequestBody id,
                               @Part("type") RequestBody type);
    }
}
