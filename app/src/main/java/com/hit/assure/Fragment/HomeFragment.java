package com.hit.assure.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.hit.assure.Activity.HomeActivity.drawerLayout;
import static com.hit.assure.Activity.HomeActivity.llDrawerLayout;
import static com.hit.assure.Retrofit.ServerCode.BANNER;
import static com.hit.assure.Retrofit.ServerCode.BEFORE_AFTER;
import static com.hit.assure.Retrofit.ServerCode.BLOG;
import static com.hit.assure.Retrofit.ServerCode.INLINEBANNER;
import static com.hit.assure.Retrofit.ServerCode.INLINEBANNERTWO;
import static com.hit.assure.Retrofit.ServerCode.NOTIFICATION_COUNT;
import static com.hit.assure.Retrofit.ServerCode.RATINGLIST;
import static com.hit.assure.Retrofit.ServerCode.TESTIMONIALVIDEOS;
import static com.hit.assure.Retrofit.ServerCode.TIPOFTHEDAY;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hit.assure.Activity.BannerWebviewActivity;
import com.hit.assure.Activity.BlogActivity;
import com.hit.assure.Activity.CartActivity;
import com.hit.assure.Activity.ChatActivity;
import com.hit.assure.Activity.DietActivity;
import com.hit.assure.Activity.NotificationActivity;
import com.hit.assure.Activity.NutritionPlanFormActivity;
import com.hit.assure.Activity.TestimonialsActivity;
import com.hit.assure.Activity.UploadSkinActivity;
import com.hit.assure.Activity.VirtualConsult.ConsultantDetailsActivity;
import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.VisitClinic.VisitClinicDetailsActivity;
import com.hit.assure.Activity.VistNearByClinicActivity;
import com.hit.assure.Adapter.BlogAdapter;
import com.hit.assure.Adapter.BlogHomeAdapter;
import com.hit.assure.AutoScrollViewPager.AutoScrollViewPager;
import com.hit.assure.Model.Blog.BlogData;
import com.hit.assure.Model.Blog.BlogResponse;
import com.hit.assure.Model.InlineBanner.InlineBannerData;
import com.hit.assure.Model.InlineBanner.InlineBannerResponse;
import com.hit.assure.Model.InlineBannerTwo.InlineBannerTwoData;
import com.hit.assure.Model.InlineBannerTwo.InlineBannerTwoResponse;
import com.hit.assure.Model.NotificationCountResponse;
import com.hit.assure.Model.Rating.RatingListData;
import com.hit.assure.Model.Rating.RatingResponse;
import com.hit.assure.Model.Testimonials.BeforeAfterDatalist;
import com.hit.assure.Model.Testimonials.BeforeAfterResponse;
import com.hit.assure.Model.Testimonials.TestimonialResponse;
import com.hit.assure.Model.Testimonials.TestimonialsDatalist;
import com.hit.assure.Model.TipOfTheDay.TipOfTheBanneResponse;
import com.hit.assure.Model.TipOfTheDay.TipOftheDayData;
import com.hit.assure.Model.TopBanner.BannerData;
import com.hit.assure.Model.TopBanner.TopBanner;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Suggestions.Question_Answer_Get_Set;
import com.hit.assure.Util.Constant;
import com.hit.assure.Util.PreferenceServices;
import com.hit.assure.Variables;
import com.kingfisher.easyviewindicator.RecyclerViewIndicator;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements View.OnClickListener, ServerResponse {

    private RelativeLayout rl_hamburger;
    private RecyclerView rec_beforeafter;
    private FrameLayout fl_cart;
    private LinearLayout ll_skin_consult;
    private LinearLayout img_dietplan;
    private AutoScrollViewPager autoScrollViewPagertwo, autoScrollViewPagerthree, autoScrollViewPagerfour;
    private ViewPager2 autoScrollViewPager;
    private SpringDotsIndicator circleIndicator, circleIndicatortwo, circleIndicatorthree, circleIndicatorfour;
    private List<BannerData> bannerData;
    private String city = "mumbai";
    private List<InlineBannerData> inlineBannerDataList;
    private List<TipOftheDayData> tipOftheDayData;
    private List<InlineBannerTwoData> inlineBannerTwoData;
    private LinearLayout ll_skin_ai, ll_anaylsis, ll_haircare, ll_visit_nearby, ll_notification;
    private TextView txt_blog, txt_seeall, txt_count_noti;
    private RecyclerView recycler_blogs;
    private String catId = "";
    private String page = "1";
    private List<RatingListData> ratingListData;
    private List<BlogData> blogData;
    private List<TestimonialsDatalist> testimonialsDatalists;
    private RecyclerView rec_testmonials_videos;
    private RoundedImageView img_recc;
    private RecyclerViewIndicator recyclerViewIndicator;
    private Handler sliderHandler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Variables.sharedPreferences = getActivity().getSharedPreferences(Variables.pref, MODE_PRIVATE);
        new Requestor(BANNER, this).getTopbanner(city);
        //     new Requestor(INLINEBANNER,this).getInlineBanner(city);
        new Requestor(TIPOFTHEDAY, this).getTipOfTheDay(city);
        //      new Requestor(INLINEBANNERTWO, this).getInlineBannerTwo(city);

        new Requestor(BLOG, this).getBlog(catId, page);
        new Requestor(NOTIFICATION_COUNT, this).getnotificationcount(PreferenceServices.getInstance().getUser_id());

        new Requestor(BEFORE_AFTER, this).getBeforeAfter();
        new Requestor(TESTIMONIALVIDEOS, this).getTestimonialsVideos();
        inita(view);

        Call_Api_to_answers_haircare();

        new Requestor(RATINGLIST, this).getRatingList();

        return view;
    }

    private void inita(View view) {

        ll_notification = view.findViewById(R.id.ll_notification);
        txt_count_noti = view.findViewById(R.id.txt_count_noti);
        img_recc = view.findViewById(R.id.img_recc);
        //   recyclerViewIndicator = view.findViewById(R.id.recyclerViewIndicator);
        rec_beforeafter = view.findViewById(R.id.rec_beforeafter);
        rec_beforeafter.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rec_beforeafter.setHasFixedSize(true);


        rec_testmonials_videos = view.findViewById(R.id.rec_testmonials_videos);
        rec_testmonials_videos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rec_testmonials_videos.setHasFixedSize(true);

        txt_seeall = view.findViewById(R.id.txt_seeall);
        recycler_blogs = view.findViewById(R.id.recycler_blogs);
        LinearLayoutManager mLiner = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_blogs.setLayoutManager(mLiner);
        recycler_blogs.setHasFixedSize(true);
        recycler_blogs.setNestedScrollingEnabled(false);

        ll_visit_nearby = view.findViewById(R.id.ll_visit_nearby);
        ll_anaylsis = view.findViewById(R.id.ll_anaylsis);
        ll_haircare = view.findViewById(R.id.ll_haircare);
        ll_skin_ai = view.findViewById(R.id.ll_skin_ai);

        autoScrollViewPagerthree = view.findViewById(R.id.autoScrollViewPagerthree);
        circleIndicatorthree = view.findViewById(R.id.circleIndicatorthree);

        rl_hamburger = view.findViewById(R.id.rl_hamburger);
        fl_cart = view.findViewById(R.id.fl_cart);
        img_dietplan = view.findViewById(R.id.img_dietplan);
        autoScrollViewPager = view.findViewById(R.id.autoScrollViewPager);
        txt_blog = view.findViewById(R.id.txt_blog);
        rl_hamburger.setOnClickListener(this);
        img_dietplan.setOnClickListener(this);
        fl_cart.setOnClickListener(this);
        ll_skin_ai.setOnClickListener(this);
        ll_haircare.setOnClickListener(this);
        ll_visit_nearby.setOnClickListener(this);
        txt_blog.setOnClickListener(this);
        txt_seeall.setOnClickListener(this);
        ll_notification.setOnClickListener(this);

    }

//    @Override
//    public void attachBaseContext(Context newBase) {
//
//        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
//        override.fontScale = 1.0f;
//        applyOverrideConfiguration(override);
//
//        super.attachBaseContext(newBase);
//    }

//    @Override
//    public void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }


    @Override
    public void onClick(View v) {
       // switch (v.getId()) {
        int item_id=v.getId();

        if(item_id== R.id.ll_notification) {
            startActivity(new Intent(getActivity(), NotificationActivity.class));
        }
           else if(item_id== R.id.txt_seeall) {
                startActivity(new Intent(getActivity(), TestimonialsActivity.class));
            }
            else if(item_id== R.id.rl_hamburger) {
                drawerLayout.openDrawer(llDrawerLayout);
            }
            else if(item_id== R.id.fl_cart) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
            else if(item_id== R.id.img_dietplan) {
                startActivity(new Intent(getActivity(), DietActivity.class));
            }
            else if(item_id== R.id.ll_skin_ai) {
                startActivity(new Intent(getActivity(), UploadSkinActivity.class).putExtra("onlyVI", "1"));
            }
            else if(item_id== R.id.ll_haircare) {
                if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                    Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
                } else {
                    open_chat();
                }
            }
            else if(item_id== R.id.ll_visit_nearby) {
                startActivity(new Intent(getActivity(), VistNearByClinicActivity.class));
            }
            else if(item_id== R.id.txt_blog){
                startActivity(new Intent(getActivity(), BlogActivity.class));

            }

    }

    private void Call_Api_to_answers_haircare() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.haircare, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo = response.toString();
                        save_datai(respo);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo", error.toString());

                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonObjectRequest);
    }

    public void save_datai(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String code = jsonObject.optString("code");
            if (code.equals("200")) {

                JSONArray msg = jsonObject.getJSONArray("msg");
                Map<String, Question_Answer_Get_Set> map = new HashMap();
                for (int i = 0; i < msg.length(); i++) {

                    JSONObject data = msg.optJSONObject(i);
                    Question_Answer_Get_Set item = new Question_Answer_Get_Set();
                    item.id = data.optString("id");
                    item.question = data.optString("question");
                    item.answer = data.optString("answers");
                    item.level = data.optString("level");
                    map.put(data.optString("question"), item);

                }

                Save_Q_A(map);

            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Save_Q_A(Map<String, Question_Answer_Get_Set> map) {
        SharedPreferences.Editor editor = Variables.sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        editor.putString(Variables.q_and_a, json);
        Log.e("checka_fajoi", json);
        Log.e("checka_fajoi", "json");
        editor.apply();
    }

    public void open_chat() {
        Intent intent = new Intent(getActivity(), ChatActivity.class)
                .putExtra("value", "Hairfall")
                .putExtra("category", "Hairfall")
                .putExtra("cat", "2")
                .putExtra("onlyVI", "1");
        startActivity(intent);
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case BANNER:
                TopBanner bannerResponse = (TopBanner) o;
                if (bannerResponse != null) {
                    bannerData = bannerResponse.getBannerData();
                    if (!bannerData.isEmpty()) {

                        autoScrollViewPager.setAdapter(new SliderAdapter(bannerData, autoScrollViewPager));
                        autoScrollViewPager.setClipToPadding(false);
                        autoScrollViewPager.setClipChildren(false);
                        autoScrollViewPager.setOffscreenPageLimit(3);
                        autoScrollViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                            @Override
                            public void transformPage(@NonNull View page, float position) {
                                float r = 1 - Math.abs(position);
                                page.setScaleY(0.85f + r * 0.15f);
                            }
                        });

                        autoScrollViewPager.setPageTransformer(compositePageTransformer);

                        autoScrollViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                            @Override
                            public void onPageSelected(int position) {
                                super.onPageSelected(position);
                                sliderHandler.removeCallbacks(sliderRunnable);
                                sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
                            }
                        });
                    }

                }
                break;
            case BLOG:
                BlogResponse blogResponse = (BlogResponse) o;
                if (blogResponse.getResult().equalsIgnoreCase("true")) {
                    blogData = blogResponse.getBlogData();
                    Log.e("hello", "yellow");
                    if (!blogData.isEmpty()) {
                        Log.e("helloadater", "yellowadapter");
                        BlogHomeAdapter blogHomeAdapter = new BlogHomeAdapter(getActivity(), blogData);
                        recycler_blogs.setAdapter(blogHomeAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "" + blogResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case NOTIFICATION_COUNT:
                NotificationCountResponse notificationCountResponse = (NotificationCountResponse) o;
                if (notificationCountResponse.getResult().equalsIgnoreCase("true")) {
                    txt_count_noti.setText("" + notificationCountResponse.getCount());
                } else {
//                    Toast.makeText(getActivity(), "" + notificationCountResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case TESTIMONIALVIDEOS:
                TestimonialResponse testimonialResponse = (TestimonialResponse) o;
                if (testimonialResponse.getResult().equalsIgnoreCase("true")) {
                    testimonialsDatalists = testimonialResponse.getTestimonialsDatalists();
                    if (!testimonialsDatalists.isEmpty()) {
                        TestimonialVideoAdapter testimonialVideoAdapter = new TestimonialVideoAdapter(getActivity(), testimonialsDatalists);
                        rec_testmonials_videos.setAdapter(testimonialVideoAdapter);

                    } else {
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else if (testimonialResponse.getResponseCode().equalsIgnoreCase("400")) {
                    Toast.makeText(getActivity(), "" + testimonialResponse.getResult(), Toast.LENGTH_SHORT).show();
                }
                break;
//            case INLINEBANNER:
//                InlineBannerResponse inlineBannerResponse = (InlineBannerResponse) o;
//                if (inlineBannerResponse != null){
//                    inlineBannerDataList = inlineBannerResponse.getInlineBannerDataList();
//                    if (!inlineBannerDataList.isEmpty()){
//                      PagerAdapter inlinePagerAdpater = new InlineSliderAdapter(getActivity(), inlineBannerResponse.getInlineBannerDataList());
//                        autoScrollViewPagertwo.setAdapter(inlinePagerAdpater);
//                        circleIndicatortwo.setViewPager(autoScrollViewPagertwo);
//                        autoScrollViewPagertwo.setInterval(4000);
//                        autoScrollViewPagertwo.startAutoScroll();
//                        autoScrollViewPagertwo.setVisibility(View.VISIBLE);
//                        circleIndicatortwo.setVisibility(View.VISIBLE);
//                    }
//                }
//                break;
            case TIPOFTHEDAY:
                TipOfTheBanneResponse tipOfTheBanneResponse = (TipOfTheBanneResponse) o;
                if (tipOfTheBanneResponse != null) {
                    tipOftheDayData = tipOfTheBanneResponse.getTipOdtheDayData();
                    if (tipOftheDayData != null) {
                        PagerAdapter tipOfThePagerAdapter = new TipOfTheSliderAdapter(getActivity(), tipOfTheBanneResponse.getTipOdtheDayData());
                        autoScrollViewPagerthree.setAdapter(tipOfThePagerAdapter);
                        circleIndicatorthree.setViewPager(autoScrollViewPagerthree);
                        autoScrollViewPagerthree.setInterval(4000);
                        autoScrollViewPagerthree.startAutoScroll();
                        autoScrollViewPagerthree.setVisibility(View.VISIBLE);
                        circleIndicatorthree.setVisibility(View.VISIBLE);
                    }
                }
                break;
//            case INLINEBANNERTWO:
//                InlineBannerTwoResponse inlineBannerTwoResponse = (InlineBannerTwoResponse) o;
//                if (inlineBannerTwoResponse != null){
//                  inlineBannerTwoData = inlineBannerTwoResponse.getInlineBannerDataList();
//                  if (inlineBannerTwoData!= null){
//
//                      PagerAdapter inlineBannerPagerAdapter = new InlineBannerSliderAdapter(getActivity(), inlineBannerTwoResponse.getInlineBannerDataList());
//                      autoScrollViewPagerfour.setAdapter(inlineBannerPagerAdapter);
//                      circleIndicatorfour.setViewPager(autoScrollViewPagerfour);
//                      autoScrollViewPagerfour.setInterval(4000);
//                      autoScrollViewPagerfour.startAutoScroll();
//                      autoScrollViewPagerfour.setVisibility(View.VISIBLE);
//                      circleIndicatorfour.setVisibility(View.VISIBLE);
//                  }
//                }
//                break;

            case RATINGLIST:
                RatingResponse ratingResponse = (RatingResponse) o;
                if (ratingResponse.getStatus() == 200) {
                    ratingListData = ratingResponse.getRatingListData();
                    if (!ratingListData.isEmpty()) {
                        TestimonialBeforeAfterAdapter testimonialReviewAdapter = new TestimonialBeforeAfterAdapter(getActivity(), ratingListData);
                        rec_beforeafter.setAdapter(testimonialReviewAdapter);
//                        recyclerViewIndicator.setRecyclerView(rec_beforeafter);
//                        recyclerViewIndicator.setItemCount(3);
//                        recyclerViewIndicator.setCurrentPosition(0);
//                        rec_beforeafter.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                            @Override
//                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                                super.onScrollStateChanged(recyclerView, newState);
//                                switch (newState){
//                                    case RecyclerView.SCROLL_STATE_IDLE:
//                                        int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//                                        recyclerViewIndicator.setCurrentPosition(position);
//                                        break;
//                                }
//                            }
//                        });
                    } else {
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
//                    Toast.makeText(getActivity(), "" + ratingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void error(Object o, int code) {

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            autoScrollViewPager.setCurrentItem(autoScrollViewPager.getCurrentItem() + 1);
        }
    };


    public class TestimonialBeforeAfterAdapter extends RecyclerView.Adapter<TestimonialBeforeAfterAdapter.ViewHolder> {

        private Context mContext;
        private List<RatingListData> ratingListData;

        public TestimonialBeforeAfterAdapter(Context mContext, List<RatingListData> ratingListData) {
            this.mContext = mContext;
            this.ratingListData = ratingListData;
        }

        @NonNull
        @Override
        public TestimonialBeforeAfterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review_with_ratings, parent, false);
            TestimonialBeforeAfterAdapter.ViewHolder holder = new TestimonialBeforeAfterAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(TestimonialBeforeAfterAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.txt_name.setText(ratingListData.get(position).getUsername());
            Picasso.get().load(ratingListData.get(position).getProfile_pic()).into(holder.img_recc);
            Float number;
            String str = ratingListData.get(position).getRating();
            number = Float.parseFloat(str);

            holder.ratingBar2.setRating(number);
            holder.txt_review.setText(ratingListData.get(position).getReview());
        }

        @Override
        public int getItemCount() {
            return ratingListData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_tittle, txt_review, txt_name;
            ImageView img_recc;
            RatingBar ratingBar2;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_name = itemView.findViewById(R.id.txt_name);
                txt_tittle = itemView.findViewById(R.id.txt_tittle);
                img_recc = itemView.findViewById(R.id.img_recc);
                ratingBar2 = itemView.findViewById(R.id.ratingBar2);
                txt_review = itemView.findViewById(R.id.txt_review);

            }
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        new Requestor(NOTIFICATION_COUNT, this).getnotificationcount(PreferenceServices.getInstance().getUser_id());

        HomeActivity.iv_home_unselected.setVisibility(View.GONE);
        HomeActivity.iv_home.setVisibility(View.VISIBLE);
        HomeActivity.iv_search.setVisibility(View.GONE);
        HomeActivity.iv_search_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_product.setVisibility(View.GONE);
        HomeActivity.iv_product_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_chat.setVisibility(View.GONE);
        HomeActivity.iv_chat_unselected.setVisibility(View.VISIBLE);


    }


    public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {


        private List<BannerData> bannerData;
        private ViewPager2 viewPager2;

        public SliderAdapter(List<BannerData> bannerData, ViewPager2 viewPager2) {
            this.bannerData = bannerData;
            this.viewPager2 = viewPager2;
        }

        @NonNull
        @Override
        public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SliderViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slider_ly, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

            holder.setImageView(bannerData.get(position));
            if (position == bannerData.size() - 2) {
                viewPager2.post(runnable);
            }

        }

        @Override
        public int getItemCount() {
            return bannerData.size();
        }

        class SliderViewHolder extends RecyclerView.ViewHolder {

            private RoundedImageView imageView;


            public SliderViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageBanner);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bannerLink = bannerData.get(getAdapterPosition()).getBanner_link();
                        if (bannerLink != null && !bannerLink.isEmpty()) {
                            startActivity(new Intent(getActivity(), BannerWebviewActivity.class).putExtra("url", bannerLink));
                        }
                    }
                });

            }

            void setImageView(BannerData sliderItem) {

                Picasso.get()
                        .load(sliderItem.getBanner_image())
                        .fit()
                        .into(imageView);


            }
        }

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bannerData.addAll(bannerData);
                notifyDataSetChanged();
            }
        };


    }

    public class InlineSliderAdapter extends PagerAdapter {

        private Context context;
        private List<InlineBannerData> bannerData;

        public InlineSliderAdapter(Context context, List<InlineBannerData> bannerData) {
            this.context = context;
            this.bannerData = bannerData;
        }

        @Override
        public int getCount() {
            return bannerData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

            ImageView imageBanner;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.row_banner, container, false);

            imageBanner = itemView.findViewById(R.id.imageBanner);


//            if (!bannerData.get(position).getBanner_image().isEmpty()) {
            Picasso.get()
                    .load(inlineBannerDataList.get(position).getBanner_image())
                    .fit()
                    .into(imageBanner);
//            }

            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    public class TipOfTheSliderAdapter extends PagerAdapter {

        private Context context;
        private List<TipOftheDayData> bannerData;

        public TipOfTheSliderAdapter(Context context, List<TipOftheDayData> bannerData) {
            this.context = context;
            this.bannerData = bannerData;
        }

        @Override
        public int getCount() {
            return bannerData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

            ImageView imageBanner;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.row_banner_tips, container, false);

            imageBanner = itemView.findViewById(R.id.imageBanner);


//            if (!bannerData.get(position).getBanner_image().isEmpty()) {
            Picasso.get()
                    .load(bannerData.get(position).getBanner_image())
                    .fit()
                    .into(imageBanner);
//            }

            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    public class InlineBannerSliderAdapter extends PagerAdapter {

        private Context context;
        private List<InlineBannerTwoData> bannerData;

        public InlineBannerSliderAdapter(Context context, List<InlineBannerTwoData> bannerData) {
            this.context = context;
            this.bannerData = bannerData;
        }

        @Override
        public int getCount() {
            return bannerData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

            ImageView imageBanner;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.row_banner, container, false);

            imageBanner = itemView.findViewById(R.id.imageBanner);


//            if (!bannerData.get(position).getBanner_image().isEmpty()) {
            Picasso.get()
                    .load(bannerData.get(position).getBanner_image())
                    .fit()
                    .into(imageBanner);
//            }

            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView((View) object);
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


}






