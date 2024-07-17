package com.hit.assure;

import static com.hit.assure.Retrofit.ServerCode.REVIEWLIST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hit.assure.Adapter.ReviewListingAdapter;
import com.hit.assure.Model.ReviewList.ReviewListData;
import com.hit.assure.Model.ReviewList.ReviewListResponse;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;

import java.util.List;
import java.util.Objects;

public class ReviewListActivity extends AppCompatActivity implements ServerResponse {

    private RecyclerView rec_review_list;
    private ImageView img_back;
    private List<ReviewListData> reviewListData;
    private String userId;
    private String drId;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        PreferenceServices.init(this);

        userId = PreferenceServices.getInstance().getUser_id();
        init();

        if (getIntent() != null){
            drId = getIntent().getStringExtra("drId");

        }
        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        new Requestor(REVIEWLIST, this).getReviewList(userId,drId);
    }

    public void init(){

        rec_review_list = findViewById(R.id.rec_review_list);
        rec_review_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_review_list.setHasFixedSize(true);
        rec_review_list.setNestedScrollingEnabled(false);

        img_back = findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
            switch (code){
                case REVIEWLIST:
                    ReviewListResponse reviewListResponse = (ReviewListResponse) o;
                    if(reviewListResponse.getStatus() == 200){
                        reviewListData = reviewListResponse.getReviewListData();
                        ReviewListingAdapter reviewListingAdapter = new ReviewListingAdapter(this, reviewListData);
                        rec_review_list.setAdapter(reviewListingAdapter);

                    }else {
                        Toast.makeText(this, ""+ reviewListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
    }

    @Override
    public void error(Object o, int code) {

    }
}