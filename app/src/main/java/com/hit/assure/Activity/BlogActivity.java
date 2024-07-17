package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.BLOG;
import static com.hit.assure.Retrofit.ServerCode.RECOMMENDATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Adapter.BlogAdapter;
import com.hit.assure.Adapter.RecommendationAdapter;
import com.hit.assure.BlogSearchActivity;
import com.hit.assure.Model.Blog.BlogData;
import com.hit.assure.Model.Blog.BlogResponse;
import com.hit.assure.Model.Recommendation.RecommendationData;
import com.hit.assure.Model.Recommendation.RecommendationResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;

import java.util.List;
import java.util.Objects;

public class BlogActivity extends AppCompatActivity implements ServerResponse, View.OnClickListener {

    private ImageView img_back;
    private RecyclerView rec_articles;
    private RecyclerView rec_recommendation;
    private List<BlogData> blogData;
    private String catId = "";
    private String page = "1";
    private List<RecommendationData> recommendationData;
    private LinearLayout ll_all, ll_hair_care, ll_skincare;
    private ProgressDialog progressDialog;
    private TextView txt_see_more,txt_all,txt_haircare,txt_skincare;
    private RelativeLayout rl_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        init();
        showProgressDialog();

        new Requestor(BLOG, this).getBlog(catId, page);
        new Requestor(RECOMMENDATION, this).getRecommendation(page);
        ll_all.setBackground(getResources().getDrawable(R.drawable.bg_orange_full));
        txt_all.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init() {

        rl_search = findViewById(R.id.rl_search);
        txt_haircare = findViewById(R.id.txt_haircare);
        txt_skincare = findViewById(R.id.txt_skincare);
        txt_all = findViewById(R.id.txt_all);
        txt_see_more = findViewById(R.id.txt_see_more);
        img_back = findViewById(R.id.img_back);
        rec_articles = findViewById(R.id.rec_articles);
        rec_articles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rec_articles.setHasFixedSize(true);
        rec_articles.setNestedScrollingEnabled(false);
        rec_recommendation = findViewById(R.id.rec_recommendation);
        rec_recommendation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_recommendation.setHasFixedSize(true);
        rec_recommendation.setNestedScrollingEnabled(false);
        img_back.setOnClickListener(this);
        ll_skincare = findViewById(R.id.ll_skincare);
        ll_all = findViewById(R.id.ll_all);
        ll_hair_care = findViewById(R.id.ll_hair_care);
        ll_skincare.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        ll_hair_care.setOnClickListener(this);
        txt_see_more.setOnClickListener(this);
        rl_search.setOnClickListener(this);

    }


    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code) {
            case BLOG:
                BlogResponse blogResponse = (BlogResponse) o;
                if (blogResponse.getResult().equalsIgnoreCase("true")) {
                    blogData = blogResponse.getBlogData();
                    if (!blogData.isEmpty()) {
                        BlogAdapter blogAdapter = new BlogAdapter(this, blogData);
                        rec_articles.setAdapter(blogAdapter);
                    } else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "" + blogResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case RECOMMENDATION:
                RecommendationResponse recommendationResponse = (RecommendationResponse) o;
                if (recommendationResponse.getResponseCode().equalsIgnoreCase("200")) {
                    recommendationData = recommendationResponse.getRecommendationData();
                    if (!recommendationData.isEmpty()&& recommendationData != null) {
                        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(this, recommendationData);
                        rec_recommendation.setAdapter(recommendationAdapter);

                    } else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else if (recommendationResponse.getResponseCode().equalsIgnoreCase("400")) {
                    Toast.makeText(this, "" + recommendationResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {
        hideProgressDialog();
    }

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
        }
        else  if(item_id== R.id.ll_all) {
            showProgressDialog();
            ll_all.setBackground(getResources().getDrawable(R.drawable.bg_orange_full));
            txt_all.setTextColor(getResources().getColor(R.color.white));
            txt_haircare.setTextColor(getResources().getColor(R.color.liteorangenew));
            txt_skincare.setTextColor(getResources().getColor(R.color.liteorangenew));
            ll_hair_care.setBackground(getResources().getDrawable(R.drawable.bg_orange_stroke_rounded));
            ll_skincare.setBackground(getResources().getDrawable(R.drawable.bg_orange_stroke_rounded));
            new Requestor(BLOG, this).getBlog(catId, page);
        }
        else if(item_id== R.id.ll_hair_care) {
            showProgressDialog();
            ll_all.setBackground(getResources().getDrawable(R.drawable.bg_orange_stroke_rounded));
            txt_all.setTextColor(getResources().getColor(R.color.liteorangenew));
            txt_haircare.setTextColor(getResources().getColor(R.color.white));
            txt_skincare.setTextColor(getResources().getColor(R.color.liteorangenew));
            ll_hair_care.setBackground(getResources().getDrawable(R.drawable.bg_orange_full));
            ll_skincare.setBackground(getResources().getDrawable(R.drawable.bg_orange_stroke_rounded));
            new Requestor(BLOG, this).getBlog("2", page);
        }
        else if(item_id== R.id.ll_skincare) {
            showProgressDialog();
            ll_all.setBackground(getResources().getDrawable(R.drawable.bg_orange_stroke_rounded));
            txt_all.setTextColor(getResources().getColor(R.color.liteorangenew));
            txt_haircare.setTextColor(getResources().getColor(R.color.liteorangenew));
            txt_skincare.setTextColor(getResources().getColor(R.color.white));
            ll_hair_care.setBackground(getResources().getDrawable(R.drawable.bg_orange_stroke_rounded));
            ll_skincare.setBackground(getResources().getDrawable(R.drawable.bg_orange_full));
            new Requestor(BLOG, this).getBlog("1", page);
        }
        else if(item_id== R.id.txt_see_more) {
            startActivity(new Intent(this, RecommendationActivity.class));
        }
        else if(item_id== R.id.rl_search) {
            startActivity(new Intent(this, BlogSearchActivity.class));
        }

        // }
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