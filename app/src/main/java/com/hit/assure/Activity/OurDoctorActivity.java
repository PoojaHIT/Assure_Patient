package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hit.assure.Adapter.OurDoctorAdapter;
import com.hit.assure.Adapter.SearchDoctorListingAdapter;
import com.hit.assure.Model.DoctorSearch.DoctorListData;
import com.hit.assure.Model.DoctorSearch.DoctorSearchResponse;
import com.hit.assure.Model.OrdeDoctorModel.OurDoctorListItem;
import com.hit.assure.Model.OrdeDoctorModel.OurDoctorResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.RetrofitBase;
import com.hit.assure.Util.PreferenceServices;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OurDoctorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;

    private RecyclerView recyclerSearchProducts;
    private String userId;
    private String page ="1";
    private String lat="18.9667";
    private String loong="72.8333";
    private List<OurDoctorListItem> doctorListData;
    public static APIServices apiServices;
    private ProgressDialog progressDialog;
    LinearLayout ll_hair,ll_skin,ll_nutri;
    TextView count_nutri,skin_count,hair_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_doctor);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        userId = PreferenceServices.getInstance().getUser_id();
        inita();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    private void inita() {
        img_back = findViewById(R.id.img_back);
        ll_hair = findViewById(R.id.ll_hair);
        ll_skin = findViewById(R.id.ll_skin);
        ll_nutri = findViewById(R.id.ll_nutri);
        count_nutri = findViewById(R.id.count_nutri);
        skin_count = findViewById(R.id.skin_count);
        hair_count = findViewById(R.id.hair_count);

        recyclerSearchProducts = findViewById(R.id.recycler_ourdoctors);
        recyclerSearchProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerSearchProducts.setHasFixedSize(true);
        recyclerSearchProducts.setNestedScrollingEnabled(false);

        img_back.setOnClickListener(this);
        ll_hair.setOnClickListener(this);
        ll_skin.setOnClickListener(this);
        ll_nutri.setOnClickListener(this);

        searchData("hair");
    }

    @Override
    public void onClick(View v) {
        //switch (v.getId()) {
            if(v.getId()== R.id.img_back) {
                finish();
            }
            else if(v.getId()== R.id.ll_hair) {
                searchData("hair");
                ll_hair.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.liteorange));
                ll_skin.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                ll_nutri.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            }
            else if(v.getId()== R.id.ll_skin) {
                searchData("skin");
                ll_hair.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                ll_skin.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.liteorange));
                ll_nutri.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            }
            else if(v.getId()== R.id.ll_nutri) {
                searchData("Nutritionist");

                ll_hair.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                ll_skin.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                ll_nutri.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.liteorange));

            }
       // }
    }


    private void searchData(String flag){


        try{
            apiServices = RetrofitBase.getClient().create(APIServices.class);
            Call<OurDoctorResponse> call = apiServices.getoutDoctorlist(flag);
            showProgressDialog();
            call.enqueue(new Callback<OurDoctorResponse>() {
                @Override
                public void onResponse(Call<OurDoctorResponse> call, Response<OurDoctorResponse> response) {

                    try {
                        hideProgressDialog();
                        OurDoctorResponse doctorSearchResponse =response.body();
                        Log.d("","doctorListDatasssss "+new Gson().toJson(doctorSearchResponse));

                        if (doctorSearchResponse.getResponseCode() == 200){

                            doctorListData = doctorSearchResponse.getData().getDoctorList();

                            String Count= String.valueOf(doctorSearchResponse.getData().getCount());

                            if(flag.equals("hair"))
                            {
                                hair_count.setText(Count);
                            }else if(flag.equals("skin"))
                            {
                                skin_count.setText(Count);
                            }
                            else if(flag.equals("Nutritionist"))
                            {
                                count_nutri.setText(Count);
                            }

                            Log.d("","doctorListData "+new Gson().toJson(doctorListData));
                            if (!doctorListData.isEmpty()) {
                                OurDoctorAdapter searchDrNewAdapter = new OurDoctorAdapter(getApplicationContext(), doctorListData);
                                recyclerSearchProducts.setAdapter(searchDrNewAdapter);
                            }
//


                        }else {
                            Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                            Log.e("checkdatahello",doctorSearchResponse.getResponseMsg());
                        }
                    }catch (Exception e){

                        hideProgressDialog();
                        e.printStackTrace();
                        Log.e("checkexception", String.valueOf(e));
                    }

                }

                @Override
                public void onFailure(Call<OurDoctorResponse> call, Throwable t) {
                    hideProgressDialog();

                }
            });
        }catch (Exception e)
        {
            Log.d("","DoctorSearchResponse  "+e.getMessage());
        }

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