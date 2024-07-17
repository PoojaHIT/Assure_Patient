package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.BEFORE_AFTER;
import static com.hit.assure.Retrofit.ServerCode.RECOMMENDATION;
import static com.hit.assure.Retrofit.ServerCode.TESTIMONIALREVIEWS;
import static com.hit.assure.Retrofit.ServerCode.TESTIMONIALVIDEOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Adapter.RecommendationAdapter;
import com.hit.assure.Model.Recommendation.RecommendationData;
import com.hit.assure.Model.Recommendation.RecommendationResponse;
import com.hit.assure.Model.Testimonials.BeforeAfterDatalist;
import com.hit.assure.Model.Testimonials.BeforeAfterResponse;
import com.hit.assure.Model.Testimonials.TestimonialResponse;
import com.hit.assure.Model.Testimonials.TestimonialReviewResponse;
import com.hit.assure.Model.Testimonials.TestimonialsDatalist;
import com.hit.assure.Model.Testimonials.TestimonialsReviewDatalist;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.RetrofitBase;
import com.hit.assure.Retrofit.ServerResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestimonialsActivity extends AppCompatActivity implements ServerResponse, View.OnClickListener {

    private RecyclerView rec_testmonials_videos, rec_patientreview, rec_beforeafter;
    private ImageView img_back;
    private ProgressDialog progressDialog;
    private List<TestimonialsDatalist> testimonialsDatalists;
    private List<TestimonialsReviewDatalist> testimonialsReviewDatalists;
    private List<BeforeAfterDatalist> beforeAfterDatalists;
    private APIServices apiServices = RetrofitBase.getClient().create(APIServices.class);
    private static final int REQUEST_CODE_POSTCROPGALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonials);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        showProgressDialog();
        new Requestor(TESTIMONIALVIDEOS, this).getTestimonialsVideos();
        new Requestor(TESTIMONIALREVIEWS, this).getTestimonialsREviews();
        new Requestor(BEFORE_AFTER, this).getBeforeAfter();

        init();

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init() {

        img_back = findViewById(R.id.img_back);
        rec_testmonials_videos = findViewById(R.id.rec_testmonials_videos);
        rec_testmonials_videos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rec_testmonials_videos.setHasFixedSize(true);
        rec_patientreview = findViewById(R.id.rec_patientreview);
        rec_patientreview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_patientreview.setHasFixedSize(true);
        img_back.setOnClickListener(this);
        rec_beforeafter = findViewById(R.id.rec_beforeafter);
        rec_beforeafter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rec_beforeafter.setHasFixedSize(true);

    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        if (code == TESTIMONIALVIDEOS) {
            TestimonialResponse testimonialResponse = (TestimonialResponse) o;
            if (testimonialResponse.getResult().equalsIgnoreCase("true")) {
                testimonialsDatalists = testimonialResponse.getTestimonialsDatalists();
                if (!testimonialsDatalists.isEmpty()) {
                    TestimonialVideoAdapter testimonialVideoAdapter = new TestimonialVideoAdapter(this, testimonialsDatalists);
                    rec_testmonials_videos.setAdapter(testimonialVideoAdapter);

                } else {
                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            } else if (testimonialResponse.getResponseCode().equalsIgnoreCase("400")) {
                Toast.makeText(this, "" + testimonialResponse.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
        if (code == TESTIMONIALREVIEWS) {
            TestimonialReviewResponse testimonialReviewResponse = (TestimonialReviewResponse) o;
            if (testimonialReviewResponse.getResult().equalsIgnoreCase("true")) {
                testimonialsReviewDatalists = testimonialReviewResponse.getTestimonialsReviewDatalists();
                if (!testimonialsReviewDatalists.isEmpty()) {
                    TestimonialReviewAdapter testimonialReviewAdapter = new TestimonialReviewAdapter(this, testimonialsReviewDatalists);
                    rec_patientreview.setAdapter(testimonialReviewAdapter);
                } else {
//                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            } else if (testimonialReviewResponse.getResponseCode().equalsIgnoreCase("400")) {
//                Toast.makeText(this, "" + testimonialReviewResponse.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
        if (code == BEFORE_AFTER) {

            BeforeAfterResponse beforeAfterResponse = (BeforeAfterResponse) o;
            if (beforeAfterResponse.getResult().equalsIgnoreCase("true")) {
                beforeAfterDatalists = beforeAfterResponse.getBeforeAfterDatalists();
                if (!beforeAfterDatalists.isEmpty()) {
                    TestimonialBeforeAfterAdapter testimonialReviewAdapter = new TestimonialBeforeAfterAdapter(this, beforeAfterDatalists);
                    rec_beforeafter.setAdapter(testimonialReviewAdapter);
                } else {
                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            } else if (beforeAfterResponse.getResponseCode().equalsIgnoreCase("400")) {
                Toast.makeText(this, "" + beforeAfterResponse.getResult(), Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void onClick(View v) {
        //switch (v.getId()) {
        if(v.getId()== R.id.img_back){
            finish();

        }
    }


    public class TestimonialVideoAdapter extends RecyclerView.Adapter<TestimonialVideoAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<TestimonialsDatalist> testimonialsDatalists;
        //    private String movie_url = "Uf3P6ru3u9A";

        public TestimonialVideoAdapter(Context mContext, List<TestimonialsDatalist> testimonialsDatalists) {
            this.mContext = mContext;
            this.testimonialsDatalists = testimonialsDatalists;
        }

        @NonNull
        @Override
        public TestimonialVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_gallery, parent, false);
            TestimonialVideoAdapter.ViewHolder holder = new TestimonialVideoAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(TestimonialVideoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            String video_id = testimonialsDatalists.get(position).getVideo_id().trim();
            String img_thumbnail = "http://img.youtube.com/vi/" + video_id + "/0.jpg";
            Log.e("img_thumbnail", img_thumbnail);
            Picasso.get().load(img_thumbnail).into(holder.imageView_video);

            holder.imageView_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_id));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + video_id));
                    try {
                        mContext.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        mContext.startActivity(webIntent);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return testimonialsDatalists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView_video;

            public ViewHolder(View itemView) {
                super(itemView);

                imageView_video = itemView.findViewById(R.id.imageView_video);

            }
        }


    }


    public class TestimonialReviewAdapter extends RecyclerView.Adapter<TestimonialReviewAdapter.ViewHolder> {

        private Context mContext;
        private List<TestimonialsReviewDatalist> testimonialsReviewDatalists;

        public TestimonialReviewAdapter(Context mContext, List<TestimonialsReviewDatalist> testimonialsReviewDatalists) {
            this.mContext = mContext;
            this.testimonialsReviewDatalists = testimonialsReviewDatalists;
        }

        @NonNull
        @Override
        public TestimonialReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_review, parent, false);
            TestimonialReviewAdapter.ViewHolder holder = new TestimonialReviewAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(TestimonialReviewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.txt_name.setText(testimonialsReviewDatalists.get(position).getUsername());
            holder.txt_review.setText(testimonialsReviewDatalists.get(position).getReview());
        }

        @Override
        public int getItemCount() {
            return testimonialsReviewDatalists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_name, txt_review;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_name = itemView.findViewById(R.id.txt_name);
                txt_review = itemView.findViewById(R.id.txt_review);

            }
        }


    }


    public class TestimonialBeforeAfterAdapter extends RecyclerView.Adapter<TestimonialBeforeAfterAdapter.ViewHolder> {

        private Context mContext;
        private List<BeforeAfterDatalist> beforeAfterDatalists;

        public TestimonialBeforeAfterAdapter(Context mContext, List<BeforeAfterDatalist> beforeAfterDatalists) {
            this.mContext = mContext;
            this.beforeAfterDatalists = beforeAfterDatalists;
        }

        @NonNull
        @Override
        public TestimonialBeforeAfterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_before_after, parent, false);
            TestimonialBeforeAfterAdapter.ViewHolder holder = new TestimonialBeforeAfterAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(TestimonialBeforeAfterAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.txt_name.setText(beforeAfterDatalists.get(position).getName());
            Picasso.get().load("https://assure.handsintechnology.in/admin/images/testimonial_media/"+beforeAfterDatalists.get(position).getImage()).into(holder.img_before);

            holder.img_before.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(getApplicationContext(), ZoomImage.class)
                            .putExtra("imagepath", "https://assure.handsintechnology.in/admin/images/testimonial_media/"+beforeAfterDatalists.get(position).getImage())
                            .putExtra("comingfrom", "gallery"), REQUEST_CODE_POSTCROPGALLERY
                    );
                }
            });
        }

        @Override
        public int getItemCount() {
            return beforeAfterDatalists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_name;
            ImageView img_before;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_name = itemView.findViewById(R.id.txt_name);
                img_before = itemView.findViewById(R.id.img_before);

            }
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