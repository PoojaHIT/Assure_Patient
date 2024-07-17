package com.hit.assure.Activity;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.hit.assure.Retrofit.Requestor.apiServices;
import static com.hit.assure.Retrofit.ServerCode.WRITEREVIEW;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.zxing.common.StringUtils;
import com.hit.assure.Activity.VirtualConsult.ConsultantDetailsActivity;
import com.hit.assure.Model.Review.ReviewResponse;
import com.hit.assure.Model.UserimageResponse;
import com.hit.assure.MultipleMediaPicker.Gallery;
import com.hit.assure.Permission.ActivityManagePermission;
import com.hit.assure.Permission.PermissionResult;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialog extends BottomSheetDialogFragment  implements ServerResponse {

    private Context context;
    private String userId = "";
    private String drId = "";
    private RatingBar ratingBar;
    private EditText edt_title;
    private EditText edt_description;
    private TextView txt_submit;
    private TextView txt_upload;
    private LinearLayout llFile1, llFile2, llFile3;
    private static final int PICK_IMAGE_MULTIPLE = 1;
    ArrayList<String> selectionResult;

    String fileName1 = "", fileName2 = "", fileName3 = "", fileName4 = "", fileName5 = "";

    String encodedImage1 = "", encodedImage2 = "", encodedImage3 = "", encodedImage4 = "",
            encodedImage5 = "";

    String a ,b, c;

    int selectedImageCount = 0;


    private int KEY_PERMISSION = 0;
    private PermissionResult permissionResult;
    private String permissionsAsk[];

    private TextView textFileName1, textFileName2, textFileName3;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.row_bottom_sheet_review,
                container, false);
        Window window = this.getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.liteorangenew));
        }

        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            drId = getArguments().getString("drId");
        }
        ratingBar = v.findViewById(R.id.ratingBar);
        edt_title = v.findViewById(R.id.edt_title);
        edt_description = v.findViewById(R.id.edt_description);
        txt_submit = v.findViewById(R.id.txt_submit);
        txt_upload = v.findViewById(R.id.txt_upload);
        llFile1 = v.findViewById(R.id.llFile1);
        llFile2 = v.findViewById(R.id.llFile2);
        llFile3 = v.findViewById(R.id.llFile3);
        textFileName1 = v.findViewById(R.id.textFileName1);
        textFileName2 = v.findViewById(R.id.textFileName2);
        textFileName3 = v.findViewById(R.id.textFileName3);
        ImageView imageFile1 = v.findViewById(R.id.imageFile1);
        ImageView imageFile2 = v.findViewById(R.id.imageFile2);
        ImageView imageFile3 = v.findViewById(R.id.imageFile3);

        imageFile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageCount = selectedImageCount - 1;
                llFile1.setVisibility(View.GONE);
                encodedImage1 = "";
                fileName1 = "";
            }
        });

        imageFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageCount = selectedImageCount - 1;
                llFile2.setVisibility(View.GONE);
                encodedImage2 = "";
                fileName2 = "";
            }
        });
        imageFile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageCount = selectedImageCount - 1;
                llFile3.setVisibility(View.GONE);
                encodedImage3 = "";
                fileName3 = "";
            }
        });

        txt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCompactPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        Intent multipleImageSelectIntent = new Intent(getActivity(), Gallery.class);
                        multipleImageSelectIntent.putExtra("title", "Select Media");
                        multipleImageSelectIntent.putExtra("mode", 2);
                        multipleImageSelectIntent.putExtra("maxSelection", 3); // Optional
                        startActivityForResult(multipleImageSelectIntent, PICK_IMAGE_MULTIPLE);
                    }

                    @Override
                    public void permissionDenied() {

                    }
                });
            }
        });

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() == 0) {
                    Toast.makeText(getActivity(), "Please add your ratings", Toast.LENGTH_SHORT).show();
                } else if (edt_title.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please add your tittle", Toast.LENGTH_SHORT).show();
                } else if (edt_description.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please add your description", Toast.LENGTH_SHORT).show();
                } else {
//                    String fullname = String.valueOf(selectionResult);
//                    String[] parts = fullname.split(",");
//                    String firstname = parts[0];
//                    String[] part = firstname.split();
//                    String lastname = parts[1];
//                    Log.e("onea", firstname);
//                    Log.e("twoa", lastname);
//                    String thrid = parts[2];
//                    Log.e("twoa", thrid);


                 uploadimage();
                    //  uploadimage(fileName1, fileName2);
                    //   new Requestor(WRITEREVIEW, BottomSheetDialog.this).getReview(userId, drId, String.valueOf(ratingBar.getRating()), edt_title.getText().toString(), edt_description.getText().toString());
                    dismiss();
                }
            }
        });


        return v;
    }

//    private void placeOrder() {
//
//
//        JSONArray jsonArray = new JSONArray();
//
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("0", fileName1);
//            jsonObject.put("1", fileName2);
//            jsonObject.put("2", fileName3);
//
//            jsonArray.put(jsonObject);
//
//            Log.e("checkobject", String.valueOf(jsonObject));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        uploadimage(jsonArray);
//
//        Log.e("JsonList",jsonArray.toString());
//}



 private void uploadimage() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", userId);
        builder.addFormDataPart("doctor_id", drId);
        builder.addFormDataPart("rating",String.valueOf(ratingBar.getRating()));
        builder.addFormDataPart("title", edt_title.getText().toString());
        builder.addFormDataPart("review", edt_description.getText().toString());
        if (a!= null) {
            builder.addFormDataPart("review_img_1", new File(a).getName(), RequestBody.create(MultipartBody.FORM, new File(a)));
        }
        if (b != null) {

            builder.addFormDataPart("review_img_2", new File(b).getName(), RequestBody.create(MultipartBody.FORM, new File(b)));

        }
        if (c != null){
            builder.addFormDataPart("review_img_3", new File(c).getName(), RequestBody.create(MultipartBody.FORM, new File(c)));
        }


        RequestBody requestBody = builder.build();
       // Log.e("check", String.valueOf(multipartBody));
      //  Log.e("checksrr", strImagePath);

        Call<ReviewResponse> call = apiServices.getReview(requestBody);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
               // progressDialog.dismiss();
                if (response.body() != null) {
                    ReviewResponse resource = response.body();
                    if (resource.getStatus() == 201) {
                        Log.e("heello", "hellooo");
                        Toast.makeText(getApplicationContext(), "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                call.cancel();
            }
        });

    }
    public boolean isPermissionGranted(Context context, String permission) {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    private void internalRequestPermission(String[] permissionAsk) {
        String arrayPermissionNotGranted[];
        ArrayList<String> permissionsNotGranted = new ArrayList<>();

        for (int i = 0; i < permissionAsk.length; i++) {
            if (!isPermissionGranted(getActivity(), permissionAsk[i])) {
                permissionsNotGranted.add(permissionAsk[i]);
            }
        }

        if (permissionsNotGranted.isEmpty()) {
            if (permissionResult != null)
                permissionResult.permissionGranted();

        } else {
            arrayPermissionNotGranted = new String[permissionsNotGranted.size()];
            arrayPermissionNotGranted = permissionsNotGranted.toArray(arrayPermissionNotGranted);
            ActivityCompat.requestPermissions(getActivity(), arrayPermissionNotGranted, KEY_PERMISSION);
        }
    }

    public void askCompactPermissions(String permissions[], PermissionResult permissionResult) {
        KEY_PERMISSION = 200;
        permissionsAsk = permissions;
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE) {
            if (resultCode == RESULT_OK && data != null) {
                 selectionResult = data.getStringArrayListExtra("result");
                selectedImageCount = selectionResult.size();
                //Log.e("selectedImageCount", String.valueOf(selectedImageCount));

                if (selectedImageCount == 0) {
                    Toast.makeText(getContext(), "You Haven't Selected Any Images", Toast.LENGTH_SHORT).show();
                } else {
                    encodedImage1 = "";

                    encodedImage2 = "";
                    encodedImage3 = "";
                    encodedImage4 = "";
                    encodedImage5 = "";
                    fileName1 = "";
                    fileName2 = "";
                    fileName3 = "";
                    fileName4 = "";
                    fileName5 = "";

                    for (int i = 0; i < selectionResult.size(); i++) {
                        File file = new File(selectionResult.get(i));

                        Log.e("selectFile", String.valueOf(selectionResult));
                        Log.e("file", String.valueOf(file));
                        Bitmap bm = BitmapFactory.decodeFile(selectionResult.get(i));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] byteArrayImage = baos.toByteArray();

                        if (i == 0) {
                            llFile1.setVisibility(View.VISIBLE);
                            a = String.valueOf(file);
                            fileName1 = file.getName();
                            textFileName1.setText(fileName1);

                            encodedImage1 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                            Log.e("fileName1", fileName1);
                            //Log.e("encodedImage1", encodedImage1);
                        } else if (i == 1) {
                            llFile2.setVisibility(View.VISIBLE);
                            fileName2 = file.getName();
                            b = String.valueOf(file);
                            textFileName2.setText(fileName2);

                            encodedImage2 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                            //Log.e("fileName2", fileName1);
                            //Log.e("encodedImage2", encodedImage1);
                        } else if (i == 2) {
                            llFile3.setVisibility(View.VISIBLE);
                            fileName3 = file.getName();
                            textFileName3.setText(fileName3);
                            c = String.valueOf(file);
                            Log.e("thridd",c);
                            encodedImage3 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                            //Log.e("fileName3", fileName1);
                            //Log.e("encodedImage3", encodedImage1);
                        }
                    }
                }
            } else {
                Toast.makeText(getActivity(), "You Haven't Selected Any Images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void success(Object o, int code) {
        switch (code){
            case WRITEREVIEW:
                ReviewResponse reviewResponse = (ReviewResponse) o;
                if (reviewResponse.getStatus()== 201){

                    Toast.makeText(getApplicationContext(), ""+ reviewResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity().getApplicationContext(), "" + reviewResponse.getMessage(), Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(getActivity().getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
                }else {
                   // Toast.makeText(getActivity().getApplicationContext(), "" + reviewResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void error(Object o, int code) {
    }


}
