package com.hit.assure.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.abt.component.AbtRegistrar;
import com.hit.assure.Model.SkinAi.OutData;
import com.hit.assure.Model.SkinAi.OutputData;
import com.hit.assure.Model.SkinAi.OutputImages;
import com.hit.assure.Model.SkinAi.OutputResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.RetrofitClientInstance;
import com.hit.assure.Retrofit.UploadReceiptService;
import com.moos.library.CircleProgressView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadSkinActivity extends AppCompatActivity implements View.OnClickListener {

    private String strImagePath = "";


    private UploadReceiptService apiServices = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);
    private TextView txt_uploadImage;
    private ImageView img_upload;
    private ImageView img_back;

    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int PICK_FILE_GALLERY = 2;
    Uri camera_gallery_uri;
    private String accesstoken = "swUcLnLRFX32gEDY5YrcgND8zPuTryGofVNh5ZM49P9QzTZNdPIru5zcRJImOWwZ";
    private OutputData outputData;
    private OutData outData;
    private OutputResponse outputResponse;
    private OutputImages outputImages;
    private RelativeLayout rel_upload;
    private CircleProgressView txt_oxgyen,txt_dark_circle,txt_dark_spot,txt_acne,txt_uneven_skin,txt_skin_dullness,txt_face_wrinkle;
    private CircleProgressView txtt_crowsfeet,txtt_eye_wrinkle,txtt_face_fairness,txtt_smoothness,txtt_hydration,txtt_skin_health_score;
    private ImageView img_acne_image,img_face_wrinkle,img_eye_wrinkle,img_crowsfeet,img_skin_dullness,img_uneven_skin,img_dark_spot,img_dark_circle,img_strenght;
    private ProgressDialog progressDialog;
    private LinearLayout txt_go;
    private TextView txt_report;
    private LinearLayout txt_camera;
    String camerapermission[];
    private static final int CAMERA_REQUEST_CODE = 1001;
    Uri img_uri;
    private static final int PICK_FRONT_AADHAR = 10001;
    private static final int IMAGE_PIC_CAMERA_CODE = 10001;
    private String onlyVI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_skin);

        camerapermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        init();
        if (getIntent() != null){

            onlyVI = getIntent().getStringExtra("onlyVI");
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init(){

        img_back = findViewById(R.id.img_back);
        txt_camera = findViewById(R.id.txt_camera);
        img_upload = findViewById(R.id.img_upload);
        txt_go = findViewById(R.id.txt_go);
        txt_go.setOnClickListener(this);
        img_back.setOnClickListener(this);
        txt_camera.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        //switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
        }

        else if(item_id== R.id.txt_go) {
            checkRuntimePermissionPickGallery(PICK_FILE_GALLERY);
        }
        else if(item_id== R.id.txt_camera){
            if (!checkCameraPermission()) {
                requestCameraPermission();
            } else {
                pickCamera_front();
            }

        }
    }

    private void pickCamera_front() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "newpic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "image to text");
        img_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
        startActivityForResult(cameraintent, PICK_FRONT_AADHAR);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(  UploadSkinActivity.this, camerapermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean resultCamera = ContextCompat.checkSelfPermission(UploadSkinActivity.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean resultStorage = ContextCompat.checkSelfPermission(UploadSkinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resultCamera && resultStorage;
    }







    //IMAGE UPLOAD CODE

    private void checkRuntimePermissionPickGallery(int code) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UploadSkinActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_SETTING);
        } else {
            proceedAfterPermissionPickGallery(code);
        }
    }

    private void proceedAfterPermissionPickGallery(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        String strImagePath = "";
        if (requestCode == PICK_FILE_GALLERY && resultCode == RESULT_OK && data != null) {
            try {
                camera_gallery_uri = data.getData();
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), camera_gallery_uri);
                Uri tempUri = getImageUri(this, imageBitmap);
                strImagePath = getPath(this, tempUri);
                //    txt_filepath.setText(strImagePath);
                Log.d("ioooo", strImagePath);

//                Picasso.get()
//                        .load(camera_gallery_uri)
//                        .fit().into(img_upload);

                Intent intent = new Intent(UploadSkinActivity.this,SkinAiActivity.class).putExtra("image", strImagePath).putExtra("onlyVI", onlyVI);
                startActivity(intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PIC_CAMERA_CODE) {
                CropImage.activity(img_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
        if (requestCode == PICK_FILE_GALLERY && resultCode == RESULT_OK && data != null) {
            try {
                Uri selectedFileUri = data.getData();
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFileUri);
                Uri tempUri = getImageUri(this, imageBitmap);
                strImagePath = getPath(this, tempUri);

                Log.e("Gallery Image Path", strImagePath);
//                Picasso.get()
//                        .load(strImagePath)
//                        .fit().into(img_upload);
                //   upload(strImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                Uri resulturi = result.getUri();

                camera_gallery_uri = result.getUri();

                strImagePath = getPath(this, camera_gallery_uri);

//                img_user.setImageURI(camera_gallery_uri);
                Log.e("checkimg", strImagePath);
//                Picasso.get()
//                        .load(strImagePath)
//                        .fit().into(img_upload);
                Intent intent = new Intent(UploadSkinActivity.this,SkinAiActivity.class).putExtra("image", strImagePath).putExtra("onlyVI", onlyVI);
                startActivity(intent);

            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage, "IMG_" + System.currentTimeMillis(), null);
        return Uri.parse(path);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }





}