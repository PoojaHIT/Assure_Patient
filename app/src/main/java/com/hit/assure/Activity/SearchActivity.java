package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.DOCTORSEARCH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hit.assure.Adapter.DoctorListingAdapter;
import com.hit.assure.Adapter.SearchDoctorListingAdapter;
import com.hit.assure.Adapter.SearchDrNewAdapter;
import com.hit.assure.Model.DoctorSearch.DoctorListData;
import com.hit.assure.Model.DoctorSearch.DoctorSearchResponse;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantData;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.RetrofitBase;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements ServerResponse, View.OnClickListener {

    private ImageView img_search;
    private EditText edt_search;
    private RecyclerView recyclerSearchProducts;
    private String userId;
    private String page ="1";
    private String lat;
    private String loong;
    private List<DoctorListData>  doctorListData;
    public static APIServices apiServices;
    private ProgressDialog progressDialog;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        userId = PreferenceServices.getInstance().getUser_id();
        Log.e("userId", userId);
        init();

        if (getIntent() != null){
            lat = getIntent().getStringExtra("lat");
            loong = getIntent().getStringExtra("loong");
        }
    }
    public void init(){
        img_back = findViewById(R.id.img_back);

        img_search = findViewById(R.id.img_search);
        edt_search = findViewById(R.id.edt_search);
        recyclerSearchProducts = findViewById(R.id.recyclerSearchProducts);
        recyclerSearchProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerSearchProducts.setHasFixedSize(true);
        recyclerSearchProducts.setNestedScrollingEnabled(false);
        img_search.setOnClickListener(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }



    @Override
    public void onClick(View v) {
        // switch (v.getId()){
        if(v.getId()== R.id.img_search) {
//                new Requestor(DOCTORSEARCH, this).getDoctorSearchList(userId,lat,loong,page,edt_search.getText().toString());

            searchData(userId, lat, loong, page, edt_search.getText().toString().trim());
        }
        // }

    }

    private void searchData(String userid, String latt,String looong, String pagee, String search){


        try{


            apiServices = RetrofitBase.getClient().create(APIServices.class);
            Call<DoctorSearchResponse> call = apiServices.getDoctorSearchList(userid,latt,looong,pagee,search);
            showProgressDialog();
            call.enqueue(new Callback<DoctorSearchResponse>() {
                @Override
                public void onResponse(Call<DoctorSearchResponse> call, Response<DoctorSearchResponse> response) {

                    try {
                        hideProgressDialog();
                        DoctorSearchResponse doctorSearchResponse =response.body();
                        Log.d("","doctorListDatasssss "+new Gson().toJson(doctorSearchResponse));

                        if (doctorSearchResponse.getStatus() == 200){
                            doctorListData = doctorSearchResponse.getSearchData();

                            Log.d("","doctorListData "+new Gson().toJson(doctorListData));
                            if (!doctorListData.isEmpty()) {
                                Toast.makeText(SearchActivity.this, ""+doctorSearchResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                SearchDoctorListingAdapter searchDrNewAdapter = new SearchDoctorListingAdapter(SearchActivity.this, doctorListData);
                                recyclerSearchProducts.setAdapter(searchDrNewAdapter);
                            }
//


                        }else {
                            Toast.makeText(SearchActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                            Log.e("checkdatahello",doctorSearchResponse.getMessage());
                        }
                    }catch (Exception e){

                        hideProgressDialog();
                        e.printStackTrace();
                        Log.e("checkexception", String.valueOf(e));
                    }

                }

                @Override
                public void onFailure(Call<DoctorSearchResponse> call, Throwable t) {
                    hideProgressDialog();

                }
            });
        }catch (Exception e)
        {
            Log.d("","DoctorSearchResponse  "+e.getMessage());
        }

    }

    @Override
    public void success(Object o, int code) {
        switch (code){
            case DOCTORSEARCH:
//                try {
//                    DoctorSearchResponse doctorSearchResponse = (DoctorSearchResponse) o;
//                    if (doctorSearchResponse.getStatus() == 200){
//                        doctorListData = doctorSearchResponse.getSearchData();
//
//                        if (doctorListData.isEmpty()) {
//                            Toast.makeText(this, "There is no doctor available", Toast.LENGTH_SHORT).show();
//                        }else {
//                            SearchDoctorListingAdapter searchDoctorListingAdapter = new SearchDoctorListingAdapter(this, doctorListData);
//                            recyclerSearchProducts.setAdapter(searchDoctorListingAdapter);
//                        }
//
//
//                    }else {
//                        Toast.makeText(this, ""+ doctorSearchResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("checkdata",doctorSearchResponse.getMessage());
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Log.e("checkexception", String.valueOf(e));
//                }
                break;

        }


    }

    @Override
    public void error(Object o, int code) {

    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }




}