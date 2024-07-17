package com.hit.assure.Fragment;

import static com.hit.assure.Activity.HomeActivity.drawerLayout;
import static com.hit.assure.Activity.HomeActivity.llDrawerLayout;
import static com.hit.assure.Retrofit.ServerCode.BLOG;
import static com.hit.assure.Retrofit.ServerCode.RECOMMENDATION;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.RecommendationActivity;
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
import com.hit.assure.Util.PreferenceServices;

import java.util.List;
import java.util.Objects;

public class BlogFragment extends Fragment implements ServerResponse, View.OnClickListener {

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
    private RelativeLayout rl_hamburger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        PreferenceServices.init(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);


        init(view);
        showProgressDialog();
        new Requestor(BLOG, this).getBlog(catId, page);
        new Requestor(RECOMMENDATION, this).getRecommendation(page);
        ll_all.setBackground(getResources().getDrawable(R.drawable.bg_orange_full));
        txt_all.setTextColor(getResources().getColor(R.color.white));
        return view;
    }


    public void init(View view) {

        rl_hamburger = view.findViewById(R.id.rl_hamburger);
        ll_all = view.findViewById(R.id.ll_all);
        ll_hair_care = view.findViewById(R.id.ll_hair_care);
        ll_skincare = view.findViewById(R.id.ll_skincare);
        txt_haircare = view.findViewById(R.id.txt_haircare);
        txt_skincare = view.findViewById(R.id.txt_skincare);
        txt_all = view.findViewById(R.id.txt_all);
        rl_search = view.findViewById(R.id.rl_search);
        txt_see_more = view.findViewById(R.id.txt_see_more);
        ll_all = view.findViewById(R.id.ll_all);
        ll_hair_care = view.findViewById(R.id.ll_hair_care);
        ll_skincare = view.findViewById(R.id.ll_skincare);
        img_back = view.findViewById(R.id.img_back);
        rec_articles = view.findViewById(R.id.rec_articles);
        rec_articles.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rec_articles.setHasFixedSize(true);
        rec_articles.setNestedScrollingEnabled(false);
        rec_recommendation = view.findViewById(R.id.rec_recommendation);
        rec_recommendation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rec_recommendation.setHasFixedSize(true);
        rec_recommendation.setNestedScrollingEnabled(false);
//        img_back.setOnClickListener(this);
        ll_skincare.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        ll_hair_care.setOnClickListener(this);
        txt_see_more.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        rl_hamburger.setOnClickListener(this);

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
                        BlogAdapter blogAdapter = new BlogAdapter(getActivity(), blogData);
                        rec_articles.setAdapter(blogAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "" + blogResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case RECOMMENDATION:
                RecommendationResponse recommendationResponse = (RecommendationResponse) o;
                if (recommendationResponse.getResponseCode().equalsIgnoreCase("200")) {
                    recommendationData = recommendationResponse.getRecommendationData();
                    if (!recommendationData.isEmpty()) {
                        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getActivity(), recommendationData);
                        rec_recommendation.setAdapter(recommendationAdapter);

                    } else {
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else if (recommendationResponse.getResponseCode().equalsIgnoreCase("400")) {
                    Toast.makeText(getActivity(), "" + recommendationResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        //  switch (v.getId()) {
//            case R.id.img_back:
//                finish();
//                break;
        if(item_id== R.id.ll_all) {
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
            startActivity(new Intent(getActivity(), RecommendationActivity.class));
        }
        else if(item_id== R.id.rl_search) {
            startActivity(new Intent(getActivity(), BlogSearchActivity.class));
        }
        else if(item_id== R.id.rl_hamburger) {
            drawerLayout.openDrawer(llDrawerLayout);
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

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.iv_home_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_home.setVisibility(View.GONE);
        HomeActivity.iv_search.setVisibility(View.GONE);
        HomeActivity.iv_search_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_product.setVisibility(View.VISIBLE);
        HomeActivity.iv_product_unselected.setVisibility(View.GONE);
        HomeActivity.iv_chat.setVisibility(View.GONE);
        HomeActivity.iv_chat_unselected.setVisibility(View.VISIBLE);
    }
}
