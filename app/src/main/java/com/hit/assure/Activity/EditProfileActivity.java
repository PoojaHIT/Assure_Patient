package com.hit.assure.Activity;


import static android.os.Build.VERSION.SDK_INT;
import static com.hit.assure.Retrofit.Requestor.apiServices;
import static com.hit.assure.Retrofit.ServerCode.UPDATEUSERDATA;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Activity.VirtualConsult.BookVirtualConsultActivity;
import com.hit.assure.Model.UpdateUserProfile.UpdateUserDataResponse;
import com.hit.assure.Model.UserDetails.UserDetailsData;
import com.hit.assure.Model.UserDetails.UserDetailsResponse;
import com.hit.assure.Model.UserimageResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.RetrofitBase;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.Constant;
import com.hit.assure.Util.Helpers;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    private ImageView img_back;
    private EditText editTextFirstName, edtxt_mobile, edtxt_age, edtxt_city, edtxt_pincode, edtxt_landmark, edtxt_address;
    private CircleImageView img_userprofile;
    ImageView img_profile_update;
    private String userId = "";
    String strImagePath = "";
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int PICK_FILE_GALLERY = 2;
    Uri camera_gallery_uri;
    private TextView txt_save, txt_consultation_form;
    private List<UserDetailsData> userDetailsDataList;
    private ProgressDialog progressDialog;
    private TextView edtDOB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        PreferenceServices.init(this);
        inita();

        //  SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        userId = PreferenceServices.getInstance().getUser_id();
        Log.e("userId", userId);

        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        new Requestor(UPDATEUSERDATA, this).getUserdata(userId);

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }


    private void inita() {

//        edtDOB = findViewById(R.id.edtdob);
        txt_consultation_form = findViewById(R.id.txt_consultation_form);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_consultation_form.setOnClickListener(this);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        edtxt_mobile = findViewById(R.id.edtxt_mobile);
        edtxt_age = findViewById(R.id.edtxt_age);
        edtxt_city = findViewById(R.id.edtxt_city);
        edtxt_pincode = findViewById(R.id.edtxt_pincode);
        edtxt_landmark = findViewById(R.id.edtxt_landmark);
        edtxt_address = findViewById(R.id.edtxt_address);
        img_profile_update = findViewById(R.id.img_profile_update);
        img_userprofile = findViewById(R.id.img_userprofile);
        img_userprofile.setBackground(getResources().getDrawable(R.drawable.baseline_person));
        img_profile_update.setOnClickListener(this);
        txt_save = findViewById(R.id.txt_save);
        txt_save.setOnClickListener(this);
//        edtDOB.setOnClickListener(this);


        if (PreferenceServices.getInstance().getApplication_form().equalsIgnoreCase("0")) {
            txt_consultation_form.setVisibility(View.GONE);
        } else {
            txt_consultation_form.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        //  switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
        }
        else if(item_id== R.id.txt_consultation_form) {
            startActivity(new Intent(EditProfileActivity.this, ConsultationFormActivity.class));
        }
        else if(item_id== R.id.img_profile_update) {
            Log.d("", "yyyyyyyyyyy==========");
            //  proceedAfterPermissionPickGallery(PICK_FILE_GALLERY);


            checkRuntimePermissionPickGallery(PICK_FILE_GALLERY);
        }
        else if(item_id== R.id.txt_save) {
            if (editTextFirstName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your  name", Toast.LENGTH_SHORT).show();
            } else if (edtxt_mobile.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your mobile", Toast.LENGTH_SHORT).show();
            } else if (edtxt_age.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your age", Toast.LENGTH_SHORT).show();
            } else if (edtxt_city.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your city", Toast.LENGTH_SHORT).show();
            } else if (edtxt_pincode.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your pincode", Toast.LENGTH_SHORT).show();
            } else if (edtxt_landmark.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your landmark", Toast.LENGTH_SHORT).show();
            } else {
                createUID();
            }
        }
//            case R.id.edtdob:
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        (DatePickerDialog.OnDateSetListener) EditProfileActivity.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH),
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd.setMaxDate(now);
//                dpd.setOnDateSetListener(this);
//                dpd.show(getFragmentManager(), "Datepickerdialog");
//                break;

        //  }
    }


//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        edtDOB.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
//        String mDate = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
//
//        String mAge = getAge(year, monthOfYear, dayOfMonth);
//        edtxt_age.setText(mAge);
//
//    }


//    private String getAge(int year, int month, int day) {
//        Calendar dob = Calendar.getInstance();
//        Calendar today = Calendar.getInstance();
//
//        dob.set(year, month, day);
//
//        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//
//        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
//            age--;
//        }
//
//        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();
//
//        return ageS;
//    }

    private void checkRuntimePermissionPickGallery(int code) {
        Log.d("","fffffffffff");

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION_SETTING);
            }  else {
                //proceedAfterPermissionPickGallery(code);
                pickFromGallery();
            }
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_SETTING);
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_SETTING);
            }  else {
                //  proceedAfterPermissionPickGallery(code);

                pickFromGallery();
            }
        }

    }
    private void pickFromGallery() {
        CropImage.activity().start(EditProfileActivity.this);
    }

    private void proceedAfterPermissionPickGallery(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), code);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        String strImagePath = "";

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //   Picasso.get().load(resultUri).into(userpic);
                Uri tempUri = Uri.parse(resultUri.toString());


                strImagePath = getPath(this, tempUri);

                Picasso.get()
                        .load(resultUri)
                        .fit().into(img_userprofile);
                uploadimage(strImagePath);

            }
        }

/*
        if (requestCode == PICK_FILE_GALLERY && resultCode == RESULT_OK && data != null) {
            try {
                camera_gallery_uri = data.getData();
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), camera_gallery_uri);
                Uri tempUri = getImageUri(this, imageBitmap);
                strImagePath = getPath(this, tempUri);
                //    txt_filepath.setText(strImagePath);
                Log.d("gsdfgergergerg", strImagePath);

                Picasso.get()
                        .load(camera_gallery_uri)
                        .fit().into(img_userprofile);
                uploadimage(strImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
*/
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //      String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "", null);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }


    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
        switch (code) {

            case UPDATEUSERDATA:
                UserDetailsResponse userDetailsResponse = (UserDetailsResponse) o;
                if (userDetailsResponse.getStatus() == 200) {
                    userDetailsDataList = userDetailsResponse.getUserDetailsData();
                    int i = 0;
                    editTextFirstName.setText(userDetailsDataList.get(i).getName());
                    edtxt_city.setText(userDetailsDataList.get(i).getCity());
                    edtxt_mobile.setText(userDetailsDataList.get(i).getPhone());
                    edtxt_address.setText(userDetailsDataList.get(i).getAddress());
                    strImagePath = Constant.UrlPath.USER_IMAGE + userDetailsDataList.get(i).getProfile_image();
                    Picasso.get().load(Constant.UrlPath.USER_IMAGE + userDetailsDataList.get(i).getProfile_image())
                            .fit().into(img_userprofile);
                    //             strImagePath = "/assureapi.handsintechnology.in/uploads/user_image/" + userDetailsDataList.get(i).getProfile_image();
                    edtxt_pincode.setText(userDetailsDataList.get(i).getPincode());
                    edtxt_landmark.setText(userDetailsDataList.get(i).getLandmark());
//                edtDOB.setText(userDetailsDataList.get(i).getDob());
                    edtxt_age.setText(userDetailsDataList.get(i).getAge());


                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void createUID() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        builder.addFormDataPart("user_id", userId);
//        builder.addFormDataPart("name", editTextFirstName.getText().toString());
//        builder.addFormDataPart("mobile_no", edtxt_mobile.getText().toString());
//        builder.addFormDataPart("age", edtxt_age.getText().toString());
//        builder.addFormDataPart("city", edtxt_city.getText().toString());
//        builder.addFormDataPart("pincode", edtxt_pincode.getText().toString());
//        builder.addFormDataPart("landmark", edtxt_landmark.getText().toString());
//        builder.addFormDataPart("address", edtxt_landmark.getText().toString());
//        builder.addFormDataPart("profile_pic", new File(strImagePath).getName(), RequestBody.create(MultipartBody.FORM, new File(strImagePath)));
//
//        RequestBody requestBody = builder.build();
//
//        Log.e("check", String.valueOf(requestBody));
//        Log.e("checksrr", strImagePath);

        Call<UpdateUserDataResponse> call = apiServices.getUpdateUserData(userId, editTextFirstName.getText().toString().trim(),
                edtxt_mobile.getText().toString().trim(), edtxt_age.getText().toString().trim(), edtxt_city.getText().toString().trim(),
                edtxt_pincode.getText().toString().trim(), edtxt_landmark.getText().toString().trim(), edtxt_address.getText().toString().trim());
        call.enqueue(new Callback<UpdateUserDataResponse>() {
            @Override
            public void onResponse(Call<UpdateUserDataResponse> call, Response<UpdateUserDataResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    UpdateUserDataResponse resource = response.body();
                    if (resource.getStatus() == 200) {

                        Toast.makeText(EditProfileActivity.this, "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfileActivity.this, EditProfileActivity.class));
                        Toast.makeText(EditProfileActivity.this, "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<UpdateUserDataResponse> call, Throwable t) {

                call.cancel();
            }
        });

    }

    private void uploadimage(String strImagePath) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", userId);
        builder.addFormDataPart("profile_pic", new File(strImagePath).getName(), RequestBody.create(MultipartBody.FORM, new File(strImagePath)));
//
        RequestBody requestBody = builder.build();
//
        Log.e("check", String.valueOf(requestBody));
        Log.e("checksrr", strImagePath);

        Call<UserimageResponse> call = apiServices.getUserimage(requestBody);
        call.enqueue(new Callback<UserimageResponse>() {
            @Override
            public void onResponse(Call<UserimageResponse> call, Response<UserimageResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    UserimageResponse resource = response.body();
                    if (resource.getStatus() == 200) {
                        Toast.makeText(EditProfileActivity.this, "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<UserimageResponse> call, Throwable t) {
                call.cancel();
            }
        });

    }


}