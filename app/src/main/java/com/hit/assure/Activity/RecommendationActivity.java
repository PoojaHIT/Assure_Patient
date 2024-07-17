package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.RECOMMENDATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hit.assure.Adapter.RecommendationAdapter;
import com.hit.assure.Model.Recommendation.RecommendationData;
import com.hit.assure.Model.Recommendation.RecommendationResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;

import java.util.List;
import java.util.Objects;

public class RecommendationActivity extends AppCompatActivity implements ServerResponse, View.OnClickListener {

    private RecyclerView rec_recommendation;
    private ImageView img_back_skin;
    private List<RecommendationData> recommendationData;
    private String page = "1";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        init();

        showProgressDialog();
        new Requestor(RECOMMENDATION, this).getRecommendation(page);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init(){

        rec_recommendation = findViewById(R.id.rec_recommendation);
        rec_recommendation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_recommendation.setHasFixedSize(true);
        rec_recommendation.setNestedScrollingEnabled(false);
        img_back_skin = findViewById(R.id.img_back);
        img_back_skin.setOnClickListener(this);



    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code){

            case RECOMMENDATION:
                RecommendationResponse recommendationResponse = (RecommendationResponse) o;
                if (recommendationResponse.getResponseCode().equalsIgnoreCase("200")){
                    recommendationData = recommendationResponse.getRecommendationData();
                    if (!recommendationData.isEmpty()){
                        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, recommendationData);
                        rec_recommendation.setAdapter(recommendationAdapter);

                    }else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    }

                }else if (recommendationResponse.getResponseCode().equalsIgnoreCase("400")){
                    Toast.makeText(this, "" + recommendationResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void onClick(View v) {
      //  switch (v.getId()){
            if(v.getId()== R.id.img_back){
                finish();

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