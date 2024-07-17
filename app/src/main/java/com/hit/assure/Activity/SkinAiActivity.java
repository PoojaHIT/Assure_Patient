package com.hit.assure.Activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.hit.assure.Model.SkinAi.InputData;
import com.hit.assure.Model.SkinAi.OutData;
import com.hit.assure.Model.SkinAi.OutputData;
import com.hit.assure.Model.SkinAi.OutputImages;
import com.hit.assure.Model.SkinAi.OutputResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.RetrofitClientInstance;
import com.hit.assure.Retrofit.UploadReceiptService;
import com.hit.assure.Suggestions.Question_Answer_Get_Set;
import com.hit.assure.Variables;
import com.moos.library.CircleProgressView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkinAiActivity extends AppCompatActivity implements View.OnClickListener  {

    private String strImagePath = "";

    private TextView txt_uploadImage;
    private ImageView img_upload;
//  private FloatingActionButton chat_float;
    private ImageView img_back;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int PICK_FILE_GALLERY = 2;
    Uri camera_gallery_uri;
    private String accesstoken = "swUcLnLRFX32gEDY5YrcgND8zPuTryGofVNh5ZM49P9QzTZNdPIru5zcRJImOWwZ";
    private OutputData outputData;
    private OutData outData;
    private OutputResponse outputResponse;
    private OutputImages outputImages;
    private UploadReceiptService apiServices = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);
    private CircleProgressView img_girl;
    private InputData inputData;
    private CircleProgressView txt_oxgyen,txt_dark_circle,txt_dark_spot,txt_acne,txt_uneven_skin,txt_skin_dullness,txt_face_wrinkle,txt_skin_score;
    private CircleProgressView txtt_crowsfeet,txtt_eye_wrinkle,txtt_face_fairness,txtt_smoothness,txtt_hydration,txtt_skin_health_score;
    private TextView txt_view_all;
    private LinearLayout ll_view_all;
    float darkCircle = (float) 70.9698;
    private CircleImageView img_pic;
    private TextView txt_skin_health_score;
    private ProgressDialog progressDialog;
    private TextView txt_skin_report;
    private String acne_image, face_wrinkle_image,eye_wrinkle_image,crowsfeet_image,skin_dullness_image,uneven_skin_image,darkspot_image,darkcircle_image,strenght_image;
    private String onlyVI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_ai);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Variables.sharedPreferences=getSharedPreferences(Variables.pref,MODE_PRIVATE);

        init();
        if (getIntent() != null){
            onlyVI = getIntent().getStringExtra("onlyVI");
            strImagePath = getIntent().getStringExtra("image");
            Picasso.get()
                    .load(strImagePath)
                    .placeholder(R.drawable.loading)
                    .into(img_pic);


        }
        getSkin(strImagePath);






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
        img_back.setOnClickListener(this);
        txt_oxgyen = findViewById(R.id.txt_oxgyen);
        txt_dark_circle = findViewById(R.id.txt_dark_circle);
        txt_dark_spot = findViewById(R.id.txt_dark_spot);
        txt_skin_score = findViewById(R.id.txt_skin_score);
        txt_skin_health_score = findViewById(R.id.txt_skin_health_score);
        txt_acne = findViewById(R.id.txt_acne);
        txt_uneven_skin = findViewById(R.id.txt_uneven_skin);
        txt_skin_dullness = findViewById(R.id.txt_skin_dullness);
        txt_face_wrinkle = findViewById(R.id.txt_face_wrinkle);
        txtt_crowsfeet = findViewById(R.id.txtt_crowsfeet);
        txtt_eye_wrinkle = findViewById(R.id.txtt_eye_wrinkle);
        txtt_face_fairness = findViewById(R.id.txtt_face_fairness);
        txtt_smoothness = findViewById(R.id.txtt_smoothness);
        txtt_hydration = findViewById(R.id.txtt_hydration);
        img_pic = findViewById(R.id.img_pic);
        txt_skin_report = findViewById(R.id.txt_skin_report);
        txt_view_all = findViewById(R.id.txt_view_all);
        txt_view_all.setOnClickListener(this);
        txt_skin_report.setOnClickListener(this);
//        chat_float.setOnClickListener(this);
        ll_view_all = findViewById(R.id.ll_view_all);






    }


    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        // switch (v.getId()){
        if(item_id== R.id.txt_view_all) {
            ll_view_all.setVisibility(View.VISIBLE);
            txt_view_all.setVisibility(View.GONE);

        }
        else if(item_id== R.id.txt_skin_report) {
            startActivity(new Intent(SkinAiActivity.this, SkinAiImageActivity.class)
                    .putExtra("acne", acne_image)
                    .putExtra("facewrinkle", face_wrinkle_image)
                    .putExtra("eyewrinkle", eye_wrinkle_image)
                    .putExtra("crowsfeet", crowsfeet_image)
                    .putExtra("skindullness", skin_dullness_image)
                    .putExtra("darkspot", darkspot_image)
                    .putExtra("darkcircle", darkcircle_image)
                    .putExtra("strenght", strenght_image)
                    .putExtra("uneven", uneven_skin_image)
                    .putExtra("image", strImagePath)
                    .putExtra("onlyVI", onlyVI)

            );
        }
        else  if(item_id== R.id.img_back) {
            finish();
        }
        else  if(item_id== R.id.chat_float) {
//                if(Variables.sharedPreferences.getString(Variables.q_and_a,"").equals("")){
//                    Toast.makeText(SkinAiActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
//                }else {
//                    open_chat();
//                }
        }




        // }
    }

    public void open_chat(){

        Intent intent=new Intent(this,ChatActivity.class).putExtra("onlyVI", onlyVI);
        startActivity(intent);
    }

    public void Save_Q_A(Map<String, Question_Answer_Get_Set> map) {
        SharedPreferences.Editor editor = Variables.sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        editor.putString(Variables.q_and_a,json);
        editor.apply();
    }

//    public void getSkin(String img) {
//
//        String accesstoken = "swUcLnLRFX32gEDY5YrcgND8zPuTryGofVNh5ZM49P9QzTZNdPIru5zcRJImOWwZ";
//        File file = new File(img);
////        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);
////        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(),requestBody);
//        String clientKey = "igGFb7qrUswANwHE";
////        MultipartBody.Part.createFormData("client_key","igGFb7qrUswANwHE");
//
//        MultipartBody.Builder builder1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        builder1.addFormDataPart("image", new File(img).getName(), RequestBody.create(MultipartBody.FORM, new File(img)));
//        builder1.addFormDataPart("client_key", clientKey);
//        RequestBody requestBody = builder1.build();
//
//        Log.e("body", String.valueOf(img));
//        Log.e("token", accesstoken);
//
//        Call<OutputResponse> call = apiServices.getSkinResult(requestBody);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.item_loader);
//        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        call.enqueue(new Callback<OutputResponse>() {
//            @Override
//            public void onResponse(Call<OutputResponse> call, Response<OutputResponse> response) {
//
//                OutputResponse outputResponse = response.body();
//
//                if (response.code() == 200) {
//                    progressDialog.dismiss();
//                    outData = outputResponse.getOutData();
//                    inputData = outputResponse.getInputData();
//
//                    Picasso.get()
//                            .load(inputData.getUrl())
//                            .into(img_pic);
//                    outputData = outData.getOutputData();
//                    int oxygenScore = outputData.getOxygen_score();
//                    float newFloat = (float) oxygenScore;
//                    Log.e("Oxygen Score", String.valueOf(oxygenScore));
//                    int dark_circle_percentage = outputData.getDark_circle_percentage();
//                    int dark_spot_percentage = outputData.getDark_spot_percentage();
//                    int acne_percentage = outputData.getAcne_percentage();
//                    int uneven_skin_percentage = outputData.getUneven_skin_percentage();
//                    int skin_dullness_percentage = outputData.getSkin_dullness_percentage();
//                    int face_wrinkle_percentage = outputData.getFace_wrinkle_percentage();
//                    int crowsfeet_percentage = outputData.getCrowsfeet_percentage();
//                    int eye_wrinkle_percentage = outputData.getEye_wrinkle_percentage();
//                    int face_firmness_percentage = outputData.getFace_firmness_percentage();
//                    int smoothness = outputData.getSmoothness();
//                    int hydration_score = outputData.getHydration_score();
//                    int skin_health_score = outputData.getSkin_health_score();
//                    txt_oxgyen.setStartProgress(0);
//                    txt_oxgyen.setEndProgress(oxygenScore);
//                    txt_oxgyen.setProgressDuration(1000);
//                    txt_oxgyen.setTrackEnabled(true);
//                    txt_oxgyen.startProgressAnimation();
//                    txt_dark_circle.setEndProgress(dark_circle_percentage);
//                    txt_dark_circle.setStartProgress(0);
//                    txt_dark_circle.setProgressDuration(1000);
//                    txt_dark_circle.setTrackEnabled(true);
//                    txt_dark_circle.startProgressAnimation();
//                    txt_dark_spot.setEndProgress(dark_spot_percentage);
//                    txt_dark_spot.setStartProgress(0);
//                    txt_dark_spot.setProgressDuration(1000);
//                    txt_dark_spot.setTrackEnabled(true);
//                    txt_dark_spot.startProgressAnimation();
//                    txt_skin_score.setEndProgress(skin_health_score);
//                    txt_skin_health_score.setText(String.valueOf(skin_health_score));
//                    txt_skin_score.setStartProgress(0);
//                    txt_skin_score.setProgressDuration(1000);
//                    txt_skin_score.setTrackEnabled(true);
//                    txt_skin_score.startProgressAnimation();
//                    txt_acne.setEndProgress(acne_percentage);
//                    txt_acne.setStartProgress(0);
//                    txt_acne.setProgressDuration(1000);
//                    txt_acne.setTrackEnabled(true);
//                    txt_acne.startProgressAnimation();
//                    txt_uneven_skin.setEndProgress(uneven_skin_percentage);
//                    txt_uneven_skin.setStartProgress(0);
//                    txt_uneven_skin.setProgressDuration(1000);
//                    txt_uneven_skin.setTrackEnabled(true);
//                    txt_uneven_skin.startProgressAnimation();
//                    txt_skin_dullness.setEndProgress(skin_dullness_percentage);
//                    txt_skin_dullness.setStartProgress(0);
//                    txt_skin_dullness.setProgressDuration(1000);
//                    txt_skin_dullness.setTrackEnabled(true);
//                    txt_skin_dullness.startProgressAnimation();
//                    txt_face_wrinkle.setEndProgress(face_wrinkle_percentage);
//                    txt_face_wrinkle.setStartProgress(0);
//                    txt_face_wrinkle.setProgressDuration(1000);
//                    txt_face_wrinkle.setTrackEnabled(true);
//                    txt_face_wrinkle.startProgressAnimation();
//                    txtt_crowsfeet.setEndProgress(crowsfeet_percentage);
//                    txtt_crowsfeet.setStartProgress(0);
//                    txtt_crowsfeet.setProgressDuration(1000);
//                    txtt_crowsfeet.setTrackEnabled(true);
//                    txtt_crowsfeet.startProgressAnimation();
//                    txtt_eye_wrinkle.setEndProgress(crowsfeet_percentage);
//                    txtt_eye_wrinkle.setStartProgress(0);
//                    txtt_eye_wrinkle.setProgressDuration(1000);
//                    txtt_eye_wrinkle.setTrackEnabled(true);
//                    txtt_eye_wrinkle.startProgressAnimation();
//                    txtt_face_fairness.setEndProgress(face_firmness_percentage);
//                    txtt_face_fairness.setStartProgress(0);
//                    txtt_face_fairness.setProgressDuration(1000);
//                    txtt_face_fairness.setTrackEnabled(true);
//                    txtt_face_fairness.startProgressAnimation();
//                    txtt_smoothness.setEndProgress(smoothness);
//                    txtt_smoothness.setStartProgress(0);
//                    txtt_smoothness.setProgressDuration(1000);
//                    txtt_smoothness.setTrackEnabled(true);
//                    txtt_smoothness.startProgressAnimation();
//                    txtt_hydration.setEndProgress(smoothness);
//                    txtt_hydration.setStartProgress(0);
//                    txtt_hydration.setProgressDuration(1000);
//                    txtt_hydration.setTrackEnabled(true);
//                    txtt_hydration.startProgressAnimation();
//
//
//
//
//
//
//
//
//
//
//                    Toast.makeText(SkinAiActivity.this, "helloworld", Toast.LENGTH_SHORT).show();
//
//
//                } else {
//                    Toast.makeText(SkinAiActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OutputResponse> call, Throwable t) {
//                Log.e("throw_exp", String.valueOf(t));
//                Toast.makeText(SkinAiActivity.this, "error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

    public void getSkin(String img) {

        String accesstoken = "swUcLnLRFX32gEDY5YrcgND8zPuTryGofVNh5ZM49P9QzTZNdPIru5zcRJImOWwZ";
        File file = new File(img);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(),requestBody);
        String clientKey = "igGFb7qrUswANwHE";
//        MultipartBody.Part.createFormData("client_key","igGFb7qrUswANwHE");

        MultipartBody.Builder builder1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder1.addFormDataPart("image", new File(img).getName(), RequestBody.create(MultipartBody.FORM, new File(img)));
        builder1.addFormDataPart("client_key", clientKey);
        RequestBody requestBody = builder1.build();

        Log.e("body", String.valueOf(img));
        Log.e("token", accesstoken);

        Call<OutputResponse> call = apiServices.getSkinResult(requestBody);
        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        call.enqueue(new Callback<OutputResponse>() {
            @Override
            public void onResponse(Call<OutputResponse> call, Response<OutputResponse> response) {

                OutputResponse outputResponse = response.body();

                if (response.code() == 200) {
                    progressDialog.dismiss();
                    outData = outputResponse.getOutData();
                    inputData = outputResponse.getInputData();
                    outputImages = outData.getOutputImages();
                    acne_image = outputImages.getAcne_image();
                    face_wrinkle_image = outputImages.getFace_wrinkle_image();
                    eye_wrinkle_image = outputImages.getEye_wrinkle_image();
                    crowsfeet_image = outputImages.getCrowsfeet_image();
                    skin_dullness_image = outputImages.getSkin_dullness_image();
                    uneven_skin_image = outputImages.getUneven_skin_image();
                    darkspot_image = outputImages.getDark_spot_image();
                    darkcircle_image = outputImages.getDark_circle_image();
                    strenght_image = outputImages.getStrength_image();
                    strImagePath = inputData.getUrl();
                    Picasso.get()
                            .load(inputData.getUrl())
                            .placeholder(R.drawable.loading)
                            .into(img_pic);

                    outputData = outData.getOutputData();
                    int oxygenScore = outputData.getOxygen_score();
                    float newFloat = (float) oxygenScore;
                    Log.e("Oxygen Score", String.valueOf(oxygenScore));
                    int dark_circle_percentage = outputData.getDark_circle_percentage();
                    int dark_spot_percentage = outputData.getDark_spot_percentage();
                    int acne_percentage = outputData.getAcne_percentage();
                    int uneven_skin_percentage = outputData.getUneven_skin_percentage();
                    int skin_dullness_percentage = outputData.getSkin_dullness_percentage();
                    int face_wrinkle_percentage = outputData.getFace_wrinkle_percentage();
                    int crowsfeet_percentage = outputData.getCrowsfeet_percentage();
                    int eye_wrinkle_percentage = outputData.getEye_wrinkle_percentage();
                    int face_firmness_percentage = outputData.getFace_firmness_percentage();
                    int smoothness = outputData.getSmoothness();
                    int hydration_score = outputData.getHydration_score();
                    int skin_health_score = outputData.getSkin_health_score();
                    txt_oxgyen.setStartProgress(0);
                    txt_oxgyen.setEndProgress(oxygenScore);
                    txt_oxgyen.setProgressDuration(1000);
                    txt_oxgyen.setTrackEnabled(true);
                    txt_oxgyen.startProgressAnimation();
                    txt_dark_circle.setEndProgress(dark_circle_percentage);
                    txt_dark_circle.setStartProgress(0);
                    txt_dark_circle.setProgressDuration(1000);
                    txt_dark_circle.setTrackEnabled(true);
                    txt_dark_circle.startProgressAnimation();
                    txt_dark_spot.setEndProgress(dark_spot_percentage);
                    txt_dark_spot.setStartProgress(0);
                    txt_dark_spot.setProgressDuration(1000);
                    txt_dark_spot.setTrackEnabled(true);
                    txt_dark_spot.startProgressAnimation();
                    txt_skin_score.setEndProgress(skin_health_score);
                    txt_skin_health_score.setText(String.valueOf(skin_health_score));
                    txt_skin_score.setStartProgress(0);
                    txt_skin_score.setProgressDuration(1000);
                    txt_skin_score.setTrackEnabled(true);
                    txt_skin_score.startProgressAnimation();
                    txt_acne.setEndProgress(acne_percentage);
                    txt_acne.setStartProgress(0);
                    txt_acne.setProgressDuration(1000);
                    txt_acne.setTrackEnabled(true);
                    txt_acne.startProgressAnimation();
                    txt_uneven_skin.setEndProgress(uneven_skin_percentage);
                    txt_uneven_skin.setStartProgress(0);
                    txt_uneven_skin.setProgressDuration(1000);
                    txt_uneven_skin.setTrackEnabled(true);
                    txt_uneven_skin.startProgressAnimation();
                    txt_skin_dullness.setEndProgress(skin_dullness_percentage);
                    txt_skin_dullness.setStartProgress(0);
                    txt_skin_dullness.setProgressDuration(1000);
                    txt_skin_dullness.setTrackEnabled(true);
                    txt_skin_dullness.startProgressAnimation();
                    txt_face_wrinkle.setEndProgress(face_wrinkle_percentage);
                    txt_face_wrinkle.setStartProgress(0);
                    txt_face_wrinkle.setProgressDuration(1000);
                    txt_face_wrinkle.setTrackEnabled(true);
                    txt_face_wrinkle.startProgressAnimation();
                    txtt_crowsfeet.setEndProgress(crowsfeet_percentage);
                    txtt_crowsfeet.setStartProgress(0);
                    txtt_crowsfeet.setProgressDuration(1000);
                    txtt_crowsfeet.setTrackEnabled(true);
                    txtt_crowsfeet.startProgressAnimation();
                    txtt_eye_wrinkle.setEndProgress(crowsfeet_percentage);
                    txtt_eye_wrinkle.setStartProgress(0);
                    txtt_eye_wrinkle.setProgressDuration(1000);
                    txtt_eye_wrinkle.setTrackEnabled(true);
                    txtt_eye_wrinkle.startProgressAnimation();
                    txtt_face_fairness.setEndProgress(face_firmness_percentage);
                    txtt_face_fairness.setStartProgress(0);
                    txtt_face_fairness.setProgressDuration(1000);
                    txtt_face_fairness.setTrackEnabled(true);
                    txtt_face_fairness.startProgressAnimation();
                    txtt_smoothness.setEndProgress(smoothness);
                    txtt_smoothness.setStartProgress(0);
                    txtt_smoothness.setProgressDuration(1000);
                    txtt_smoothness.setTrackEnabled(true);
                    txtt_smoothness.startProgressAnimation();
                    txtt_hydration.setEndProgress(smoothness);
                    txtt_hydration.setStartProgress(0);
                    txtt_hydration.setProgressDuration(1000);
                    txtt_hydration.setTrackEnabled(true);
                    txtt_hydration.startProgressAnimation();
                    Toast.makeText(SkinAiActivity.this, "Analyzation Completed", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(SkinAiActivity.this, FailedActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<OutputResponse> call, Throwable t) {
                Log.e("throw_exp", String.valueOf(t));
                Toast.makeText(SkinAiActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Call_Api_to_answers() {
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.bootChat, null, new  com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d("responce",respo);
                        save_data(respo);

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo",error.toString());
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonObjectRequest);
    }

    public void save_data(String responce){
        try {
            JSONObject jsonObject=new JSONObject(responce);
            String code=jsonObject.optString("code");
            if(code.equals("200")){

                JSONArray msg=jsonObject.getJSONArray("msg");
                Map<String,Question_Answer_Get_Set> map=new HashMap();
                for(int i=0; i<msg.length();i++){
                    JSONObject data=msg.optJSONObject(i);
                    Question_Answer_Get_Set item=new Question_Answer_Get_Set();
                    item.id=data.optString("id");
                    item.question=data.optString("question");
                    item.answer=data.optString("answers");
                    item.level=data.optString("level");
                    map.put(data.optString("question"),item);
                }

                Save_Q_A(map);

            }
            else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void checkRuntimePermissionPickGallery(int code) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SkinAiActivity.this,
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

                Picasso.get()
                        .load(camera_gallery_uri)
                        .fit().into(img_upload);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "", null);
        return Uri.parse(path);
    }
}