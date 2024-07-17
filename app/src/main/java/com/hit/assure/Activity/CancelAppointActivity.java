package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.CANCELAPPOINTMENT;
import static com.hit.assure.Retrofit.ServerCode.DOCTORPROFILE;
import static com.hit.assure.Retrofit.ServerCode.REVIEWTWO;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Adapter.ReviewTwoListingAdapter;
import com.hit.assure.Model.CancelAppointment.CancelAppointmentResponse;
import com.hit.assure.Model.Doctorprofile.DoctorPractices;
import com.hit.assure.Model.Doctorprofile.DoctorProfileData;
import com.hit.assure.Model.Doctorprofile.DoctorProfileresponse;
import com.hit.assure.Model.ReviewTwo.ReviewTwoData;
import com.hit.assure.Model.ReviewTwo.ReviewTwoResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CancelAppointActivity extends AppCompatActivity implements ServerResponse {

    private List<DoctorProfileData> doctorProfileDataList;
    private String drId ="";
    private TextView txt_write_review;
    private RecyclerView recycler_review;
    private List<ReviewTwoData> reviewTwoDataList;
    private List<DoctorPractices> doctorPractices;
    private String userId="";
    private TextView txt_view_slot;
    private String drName,clinicName;
    private String clinicId;
    private String drImage;
    private TextView txt_readAllStories, txt_book_in;
    private TextView txt_clinic_name;
    private TextView txt_sentence;
    private ProgressDialog progressDialog;
    private ImageView img_userprofile,img_back;
    private String bookingId="";
    private TextView txt_name,txt_dr_type,txt_exp,txt_like,txt_patients,txt_cancel_appointment,txt_clinic_date;
    private String bookingFromTime;
    private TextView txt_clinic_time;
    private String clinic_id;
    private String date;
    private String consultingType;
    private TextView txt_service_type;
    private TextView txt_bookking_id;
    private ImageView img_home;
    private LinearLayout bg;
    private View blue, orange;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appoint);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);



        init();
        if (getIntent() != null) {
            drId = getIntent().getStringExtra("drId");
            bookingId = getIntent().getStringExtra("bookingId");
            bookingFromTime = getIntent().getStringExtra("bookingFromTime");
            clinic_id = getIntent().getStringExtra("clinic_id");
            date = getIntent().getStringExtra("date");
            consultingType = getIntent().getStringExtra("consultingType");
            Log.e("clinicId", clinic_id);
            Log.e("drID", drId);
            Log.e("bookingId", bookingId);
            Log.e("BookingFromTime", bookingFromTime);
            txt_bookking_id.setText(bookingId);



            String currentDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
            Log.e("currentDate", currentDate);


        }

        if (consultingType.equalsIgnoreCase("visit")){
            txt_service_type.setText("In-Clinic Appointment");
            img_home.setBackground(getResources().getDrawable(R.drawable.img_home_btn));
            bg.setBackground(getResources().getDrawable(R.drawable.bg_top_orange_rounded));
            txt_cancel_appointment.setTextColor(getResources().getColor(R.color.liteorangenew));
            txt_cancel_appointment.setBackground(getResources().getDrawable(R.drawable.bg_btn_cancel_border));
            orange.setVisibility(View.VISIBLE);
        }else {
            orange.setVisibility(View.GONE);
            blue.setVisibility(View.VISIBLE);
        }







        txt_clinic_time.setText(bookingFromTime);
        txt_clinic_date.setText(date);
        String currentDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        Log.e("currentDate", currentDate);
        progressDialog.show();
        new Requestor(DOCTORPROFILE, this).getDoctorProfile(drId,userId,clinic_id,currentDate);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init(){
        blue = findViewById(R.id.blue);
        orange = findViewById(R.id.orange);
        img_home = findViewById(R.id.img_home);
        bg = findViewById(R.id.bg);
        txt_bookking_id = findViewById(R.id.txt_bookking_id);
        txt_service_type = findViewById(R.id.txt_service_type);
        txt_clinic_time = findViewById(R.id.txt_clinic_time);
        txt_clinic_date = findViewById(R.id.txt_clinic_date);
        txt_cancel_appointment = findViewById(R.id.txt_cancel_appointment);
        txt_sentence = findViewById(R.id.txt_sentence);
        txt_name = findViewById(R.id.txt_name);
        txt_dr_type = findViewById(R.id.txt_dr_type);
        txt_exp = findViewById(R.id.txt_exp);
        txt_like = findViewById(R.id.txt_like);
        txt_clinic_name = findViewById(R.id.txt_clinic_name);
        txt_patients = findViewById(R.id.txt_patients);
        img_back = findViewById(R.id.img_back);
        img_userprofile = findViewById(R.id.img_userprofile);



        txt_cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetCancelDialog bottomCancel = new BottomSheetCancelDialog();
                Bundle args = new Bundle();
                args.putString("drId", drId);
                args.putString("userId", userId);
                args.putString("bookingId",bookingId);
                bottomCancel.setArguments(args);
                bottomCancel.show(getSupportFragmentManager(),
                        "ModalBottomSheet");

            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CancelAppointActivity.this, HomeActivity.class));
            }
        });
    }

    @Override
    public void success(Object o, int code) {
        progressDialog.hide();
        switch (code){
            case DOCTORPROFILE:
                DoctorProfileresponse doctorProfileresponse = (DoctorProfileresponse) o;
                if (doctorProfileresponse.getStatus() == 200){

                    doctorProfileDataList = doctorProfileresponse.getDoctorProfileDataList();
                    for (int i = 0; i <doctorProfileDataList.size(); i++){
                        drName = doctorProfileDataList.get(i).getDoctor_name();
                        txt_name.setText(drName);
        //                txt_dr_qualification.setText(doctorProfileDataList.get(i).getQualification());
                        txt_exp.setText(doctorProfileDataList.get(i).getExperience() + " years experience overall");
                        txt_like.setText(doctorProfileDataList.get(i).getRating());
                        txt_patients.setText(doctorProfileDataList.get(i).getReview());
        //                txt_second_like.setText(doctorProfileDataList.get(i).getRating());
                        drImage = doctorProfileDataList.get(i).getProfile_pic();
                        txt_sentence.setText(doctorProfileDataList.get(i).getSentence());
      //                  txt_fees.setText("₹ " + doctorProfileDataList.get(i).getVideo_charge());
//                        if (doctorProfileDataList.get(i).getReview_enable() == 0){
//                            txt_write_review.setVisibility(View.VISIBLE);
//                        }

                        Picasso.get()
                                .load(drImage)
                                .fit()
                                .into(img_userprofile);

                        doctorPractices = doctorProfileDataList.get(i).getDoctorPractices();
                        clinicId = doctorPractices.get(i).getClinic_id();
                        clinicName = doctorPractices.get(i).getClinic_name();
                        txt_clinic_name.setText(clinicName);

                    }

                }else {

                    Toast.makeText(this, "Doctor List", Toast.LENGTH_SHORT).show();

                }
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