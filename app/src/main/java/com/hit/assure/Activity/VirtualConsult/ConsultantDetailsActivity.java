package com.hit.assure.Activity.VirtualConsult;

import static com.hit.assure.Retrofit.ServerCode.DOCTORPROFILE;
import static com.hit.assure.Retrofit.ServerCode.REVIEWLIST;
import static com.hit.assure.Retrofit.ServerCode.REVIEWTWO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.hit.assure.Activity.BottomSheetDialog;
import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Adapter.ReviewListingAdapter;
import com.hit.assure.Adapter.ReviewTwoListingAdapter;
import com.hit.assure.Fragment.ActiveFragment;
import com.hit.assure.Fragment.CompletedFragment;
import com.hit.assure.Fragment.DetailsPageSlotsFragment;
import com.hit.assure.Fragment.SelectedSlotFragment;
import com.hit.assure.Model.Doctorprofile.DoctorPractices;
import com.hit.assure.Model.Doctorprofile.DoctorProfileData;
import com.hit.assure.Model.Doctorprofile.DoctorProfileresponse;
import com.hit.assure.Model.ReviewList.ReviewListData;
import com.hit.assure.Model.ReviewList.ReviewListResponse;
import com.hit.assure.Model.ReviewTwo.ReviewTwoData;
import com.hit.assure.Model.ReviewTwo.ReviewTwoResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.ReviewListActivity;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ConsultantDetailsActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    private ImageView img_back, img_userprofile;
    private TextView txt_dr_name, txt_dr_type, txt_dr_qualification, txt_exp, txt_like, txt_patients_story, txt_fees, txt_second_like, txt_next_available;
    private List<DoctorProfileData> doctorProfileDataList;
    private String drId;
    private TextView txt_write_review;
    private RecyclerView recycler_review;
    private List<ReviewTwoData> reviewTwoDataList;
    private List<DoctorPractices> doctorPractices;
    private String userId = "";
    private TextView txt_view_slot;
    private String drName, clinicName;
    private String clinicId;
    private String drImage;
    private TextView txt_readAllStories, txt_book_in;
    private TextView txt_clinic_name;
    private TextView txt_sentence;
    private TabLayout tabLayout;
    private ProgressDialog progressDialog;
    private FrameLayout frame_timing;
    private String consultingType = "";
    private String selected = "";
    private TextView txt_service_type;
    private LinearLayout bg;
    private ImageView img_home;
    private TextView txt_booking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_details);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        inita();
        userId = PreferenceServices.getInstance().getUser_id();

        Log.e("useridd", userId);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.e("currentDate", currentDate);

        if (getIntent() != null) {
            drId = getIntent().getStringExtra("drid");
            // Log.e("drId", drId);
            clinicName = getIntent().getStringExtra("clinic_name");
            txt_clinic_name.setText(clinicName);
            clinicId = getIntent().getStringExtra("clinic_id");
            consultingType = getIntent().getStringExtra("consultation_type");
            selected = getIntent().getStringExtra("selected");

        }

        if (consultingType.equalsIgnoreCase("visit")) {
            txt_service_type.setText("In-Clinic Appointment");
            bg.setBackground(getResources().getDrawable(R.drawable.bg_top_orange_rounded));
            img_home.setBackground(getResources().getDrawable(R.drawable.img_home_btn));
            txt_booking.setText("Book In-Clinic Appointment");
        }

        load();
        tabClick();

        userId = PreferenceServices.getInstance().getUser_id();
        Log.e("userIdd", userId);
        Log.e("consultantdrid", drId);
        showProgressDialog();
        new Requestor(DOCTORPROFILE, this).getDoctorProfile(drId, userId, clinicId, currentDate);
        new Requestor(REVIEWTWO, this).getReviewTwo(userId, drId);

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    private void inita() {
        img_home = findViewById(R.id.img_home);
        txt_booking = findViewById(R.id.txt_booking);
        bg = findViewById(R.id.bg);
        tabLayout = findViewById(R.id.tabs);
        txt_service_type = findViewById(R.id.txt_service_type);
        frame_timing = findViewById(R.id.frame_timing);
        txt_sentence = findViewById(R.id.txt_sentence);
        recycler_review = findViewById(R.id.recycler_review);
        txt_clinic_name = findViewById(R.id.txt_clinic_name);
        recycler_review.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_review.setHasFixedSize(true);
        recycler_review.setNestedScrollingEnabled(false);
        img_back = findViewById(R.id.img_back);
        txt_book_in = findViewById(R.id.txt_book_in);
        txt_view_slot = findViewById(R.id.txt_view_slot);
        img_userprofile = findViewById(R.id.img_userprofile);
        txt_write_review = findViewById(R.id.txt_write_review);
        img_back.setOnClickListener(this);
        txt_write_review.setOnClickListener(this);
        txt_dr_name = findViewById(R.id.txt_dr_name);
//        txt_next_available = findViewById(R.id.txt_next_available);
        txt_dr_type = findViewById(R.id.txt_dr_type);
        txt_dr_qualification = findViewById(R.id.txt_dr_qualification);
        txt_exp = findViewById(R.id.txt_exp);
        txt_like = findViewById(R.id.txt_like);
        txt_patients_story = findViewById(R.id.txt_patients_story);
        txt_fees = findViewById(R.id.txt_fees);
        txt_second_like = findViewById(R.id.txt_second_like);
        txt_readAllStories = findViewById(R.id.txt_readAllStories);
        txt_view_slot.setOnClickListener(this);
        txt_readAllStories.setOnClickListener(this);
        txt_book_in.setOnClickListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Morning"));
        tabLayout.addTab(tabLayout.newTab().setText("Afternoon"));
        tabLayout.addTab(tabLayout.newTab().setText("Evening"));
        tabLayout.addTab(tabLayout.newTab().setText("Night"));


    }


    public void tabClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DetailsPageSlotsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("morning", "1");
                        bundle.putString("drId", drId);
                        bundle.putString("consultingType", consultingType);
                        bundle.putString("selected", selected);
                        fragment.setArguments(bundle);
                        loadFragments(fragment);
                        break;
                    case 1:
                        fragment = new DetailsPageSlotsFragment();
                        Bundle afternoon = new Bundle();
                        afternoon.putString("morning", "2");
                        afternoon.putString("drId", drId);
                        afternoon.putString("consultingType", consultingType);
                        afternoon.putString("selected", selected);
                        fragment.setArguments(afternoon);
                        loadFragments(fragment);
                        break;
                    case 2:
                        fragment = new DetailsPageSlotsFragment();
                        Bundle evening = new Bundle();
                        evening.putString("morning", "3");
                        evening.putString("drId", drId);
                        evening.putString("consultingType", consultingType);
                        evening.putString("selected", selected);
                        fragment.setArguments(evening);
                        loadFragments(fragment);
                        break;
                    case 3:
                        fragment = new DetailsPageSlotsFragment();
                        Bundle nigth = new Bundle();
                        nigth.putString("morning", "4");
                        nigth.putString("drId", drId);
                        nigth.putString("consultingType", consultingType);
                        nigth.putString("selected", selected);
                        fragment.setArguments(nigth);
                        loadFragments(fragment);
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void load() {
        Fragment fragment = null;
        if (fragment == null) {
            fragment = new DetailsPageSlotsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("morning", "1");
            bundle.putString("drId", drId);
            bundle.putString("clinic_id", clinicId);
            bundle.putString("consultingType", consultingType);
            fragment.setArguments(bundle);
            loadFragments(fragment);

        }
    }

    private void loadFragments(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_timing, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        //  switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
        }
        else if(item_id== R.id.txt_write_review) {
            BottomSheetDialog bottomSheet = new BottomSheetDialog();
            Bundle args = new Bundle();
            args.putString("drId", drId);
            args.putString("userId", userId);
            bottomSheet.setArguments(args);
            bottomSheet.show(getSupportFragmentManager(),
                    "ModalBottomSheet");
        }
        else if(item_id== R.id.txt_view_slot) {
            startActivity(new Intent(this, SelectTimeSlotActivity.class).putExtra("dr_id", drId).putExtra("clinic_id", clinicId).putExtra("drname", drName)
                    .putExtra("clinicName", clinicName).putExtra("drImage", drImage).putExtra("consultation_type", consultingType));
            Log.e("yellow", clinicId);
        }
        else if(item_id== R.id.txt_readAllStories) {
            startActivity(new Intent(this, ReviewListActivity.class).putExtra("drId", drId));
        }
        else if(item_id== R.id.txt_book_in) {
            getInSlots();
        }
        //  }
    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code) {
            case DOCTORPROFILE:
                DoctorProfileresponse doctorProfileresponse = (DoctorProfileresponse) o;
                if (doctorProfileresponse.getStatus() == 200) {

                    doctorProfileDataList = doctorProfileresponse.getDoctorProfileDataList();
                    for (int i = 0; i < doctorProfileDataList.size(); i++) {
                        drName = doctorProfileDataList.get(i).getDoctor_name();
                        txt_dr_name.setText(drName);
                        txt_dr_qualification.setText(doctorProfileDataList.get(i).getQualification());
                        txt_exp.setText(doctorProfileDataList.get(i).getExperience() + " years experience overall");
                        txt_like.setText(doctorProfileDataList.get(i).getRating());
                        txt_patients_story.setText(doctorProfileDataList.get(i).getReview());
                        txt_second_like.setText(doctorProfileDataList.get(i).getRating());
                        drImage = doctorProfileDataList.get(i).getProfile_pic();
                        txt_sentence.setText(doctorProfileDataList.get(i).getSentence());
                        txt_fees.setText("₹ " + doctorProfileDataList.get(i).getVideo_charge() + " Fees");
                        if (doctorProfileDataList.get(i).getReview_enable() == 0) {
                            txt_write_review.setVisibility(View.VISIBLE);
                        }
                        Picasso.get()
                                .load(drImage)
                                .fit()
                                .into(img_userprofile);
                        doctorPractices = doctorProfileDataList.get(i).getDoctorPractices();
                        clinicId = doctorPractices.get(i).getClinic_id();
                        clinicName = doctorPractices.get(i).getClinic_name();
                        txt_clinic_name.setText(doctorPractices.get(i).getClinic_name());

                    }

                } else {
                    Toast.makeText(this, "Doctor List", Toast.LENGTH_SHORT).show();

                }
                break;
            case REVIEWTWO:
                ReviewTwoResponse reviewListResponse = (ReviewTwoResponse) o;
                if (reviewListResponse.getResponseCode().equalsIgnoreCase("200")) {
                    reviewTwoDataList = reviewListResponse.getReviewTwoDataList();
                    for (int i = 0; i < reviewTwoDataList.size(); i++) {
                        ReviewTwoListingAdapter reviewListingAdapter = new ReviewTwoListingAdapter(this, reviewTwoDataList);
                        recycler_review.setAdapter(reviewListingAdapter);
                    }
                } else {
                    Toast.makeText(this, "" + reviewListResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    public void getInSlots() {
        startActivity(new Intent(this, SelectTimeSlotActivity.class).putExtra("dr_id", drId).putExtra("clinic_id", clinicId).putExtra("drname", drName)
                .putExtra("clinicName", clinicName).putExtra("drImage", drImage).putExtra("consultation_type", consultingType));

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