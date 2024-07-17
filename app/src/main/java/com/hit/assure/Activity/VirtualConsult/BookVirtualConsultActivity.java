package com.hit.assure.Activity.VirtualConsult;

import static com.hit.assure.Retrofit.ServerCode.APPLYCOUPON;
import static com.hit.assure.Retrofit.ServerCode.BOOKAPPOINTMENT;
import static com.hit.assure.Retrofit.ServerCode.DOCTORPROFILE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Activity.ApplicationFormActivity;
import com.hit.assure.Activity.ChatActivity;
import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.PaymentfailedActivity;
import com.hit.assure.Activity.SkinConsultationFormActivity;
import com.hit.assure.Activity.SuccessActivity;
import com.hit.assure.Model.BookAppointment.BookAppointmentResponse;
import com.hit.assure.Model.BookAppointment.BookingData;
import com.hit.assure.Model.Coupon.ApplyCouponResponse;
import com.hit.assure.Model.Doctorprofile.DoctorPractices;
import com.hit.assure.Model.Doctorprofile.DoctorProfileData;
import com.hit.assure.Model.Doctorprofile.DoctorProfileresponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class BookVirtualConsultActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse, PaymentResultListener {
    private ImageView img_back;
    private String drId = "";
    private List<DoctorProfileData> doctorProfileDataList;
    private List<DoctorPractices> doctorPractices;
    private TextView txt_dr_name, txt_dr_type, txt_dr_qualification, txt_dr_exp, dr_txt_like, dr_txt_patients_stories, txt_fee, txt_bookappointment;
    private TextView txt_consulation_fee, txt_coupon_amount, txt_total_amount, txt_clinic_name, txt_place, txt_apply;
    int i;
    private ImageView img_userprofile;
    private String time;
    private TextView txt_bottom_total;
    private String OrderId;
    private String email = "praful@handsintechnology.com";
    private String contact = "9892071465";
    private String userId = "";
    private TextView txt_book_ap;
    private String clinic_name;
    private RelativeLayout rel_discount;
    private String total;
    private EditText edtxt_coupon;
    private String type = "booking";
    private ProgressDialog progressDialog;
    private String clinic_id = "0";
    private String booking_date = "";
    private String connectType = "";
    private String couponCode = "";
    int discont;
    private String date;
    private String displayTime = "";
    private String bookingId = "";
    private String toTime = "";
    private String displayTOTime = "";
    private BookingData bookingData;
    private String fee = "0";
    private String dateone = "";
    private String drname = "";
    private String selected = "";
    private TextView txt_service_type;
    private String consultingType;
    private TextView txt_booking;
    private ImageView img_home;
    private LinearLayout bg;
    private String cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_virtual_consult);

        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        userId = PreferenceServices.getInstance().getUser_id();
        Log.e("useridd", userId);
        email = PreferenceServices.getInstance().getUser_email();
        contact = PreferenceServices.getInstance().getUser_mobile();

        inita();
        if (getIntent() != null) {
            drId = getIntent().getStringExtra("drId");
            time = getIntent().getStringExtra("time");
            clinic_name = getIntent().getStringExtra("clinic_name");
            displayTime = getIntent().getStringExtra("displayTime");
            displayTOTime = getIntent().getStringExtra("displayToTime");
            toTime = getIntent().getStringExtra("normalToTime");
            date = getIntent().getStringExtra("date");
            selected = getIntent().getStringExtra("selected");
            dateone = getIntent().getStringExtra("dateone");
            consultingType = getIntent().getStringExtra("consultingType");
            connectType = getIntent().getStringExtra("consultingType");
            txt_clinic_name.setText(clinic_name);
            txt_bookappointment.setText(displayTime);

        }

//        if (consultingType.equalsIgnoreCase("visit")){
//            txt_service_type.setText("In-Clinic Appointment");
//        }

        // Log.e("yellowDate", consultingType);
        Log.e("pinkDate", dateone);

        if (consultingType.equalsIgnoreCase("visit")) {
            txt_service_type.setText("In-Clinic Appointment");
            txt_booking.setText("Book In-Clinic Appointment");
            img_home.setBackground(getResources().getDrawable(R.drawable.img_home_btn));
            bg.setBackground(getResources().getDrawable(R.drawable.bg_top_orange_rounded));

        }

        new Requestor(DOCTORPROFILE, this).getDoctorProfile(drId, userId, clinic_id, date);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    private void inita() {
        bg = findViewById(R.id.bg);
        img_home = findViewById(R.id.img_home);
        txt_booking = findViewById(R.id.txt_booking);
        txt_service_type = findViewById(R.id.txt_service_type);
        img_back = findViewById(R.id.img_back);
        edtxt_coupon = findViewById(R.id.edtxt_coupon);
        txt_apply = findViewById(R.id.txt_apply);
        rel_discount = findViewById(R.id.rel_discount);
        txt_book_ap = findViewById(R.id.txt_book_ap);
//        txt_coupon_amount = findViewById(R.id.txt_coupon_amount);
        txt_book_ap.setOnClickListener(this);
        txt_bottom_total = findViewById(R.id.txt_bottom_total);
        txt_dr_name = findViewById(R.id.txt_dr_name);
        txt_clinic_name = findViewById(R.id.txt_clinic_name);
        txt_place = findViewById(R.id.txt_place);
        txt_dr_type = findViewById(R.id.txt_dr_type);
        txt_dr_qualification = findViewById(R.id.txt_dr_qualification);
        txt_bookappointment = findViewById(R.id.txt_bookappointment_time);
        txt_dr_exp = findViewById(R.id.txt_dr_exp);
        dr_txt_like = findViewById(R.id.dr_txt_like);
        dr_txt_patients_stories = findViewById(R.id.dr_txt_patients_stories);
        txt_fee = findViewById(R.id.txt_fee);
        txt_consulation_fee = findViewById(R.id.txt_consulation_fee);
        txt_coupon_amount = findViewById(R.id.txt_coupon_amount);
        txt_total_amount = findViewById(R.id.txt_total_amount);
        img_userprofile = findViewById(R.id.img_userprofile);
        img_back.setOnClickListener(this);
        txt_apply.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.img_back) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else if(item_id== R.id.txt_book_ap) {
            if (PreferenceServices.getInstance().getApplication_form().equalsIgnoreCase("0")) {
                startActivity(new Intent(BookVirtualConsultActivity.this, ApplicationFormActivity.class));
            } else {
                // startPayment();
                progressDialog.show();
                progressDialog.setContentView(R.layout.item_loader);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                String razorpayPaymentID = "";
                new Requestor(BOOKAPPOINTMENT, this).getBookAppointment(userId, drId, clinic_id, date, time, toTime, connectType, txt_bottom_total.getText().toString(), razorpayPaymentID, connectType, couponCode, fee, String.valueOf(discont), txt_bottom_total.getText().toString());
            }

        }
        else if(item_id== R.id.txt_apply) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            new Requestor(APPLYCOUPON, this).getCoupon(userId, edtxt_coupon.getText().toString().trim(), total, type);
        }
        //}
    }

    public void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay");
            options.put("description", "Assure");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "" + Integer.parseInt(txt_total_amount.getText().toString().replace("₹ ", "")) * 100);
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", contact);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

//        try {
        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        new Requestor(BOOKAPPOINTMENT, this).getBookAppointment(userId, drId, clinic_id, date, time, toTime, connectType, txt_bottom_total.getText().toString(), razorpayPaymentID, connectType, couponCode, fee, String.valueOf(discont), txt_bottom_total.getText().toString());

//        }catch (Exception e){
//            e.printStackTrace();
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//        }


    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            startActivity(new Intent(BookVirtualConsultActivity.this, PaymentfailedActivity.class));
        } catch (Exception e) {

        }
    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
        switch (code) {
            case DOCTORPROFILE:
                DoctorProfileresponse doctorProfileresponse = (DoctorProfileresponse) o;
                if (doctorProfileresponse.getStatus() == 200) {

                    doctorProfileDataList = doctorProfileresponse.getDoctorProfileDataList();
                    txt_dr_name.setText(doctorProfileDataList.get(i).getDoctor_name());
                    drname = doctorProfileDataList.get(i).getDoctor_name();
                    txt_dr_qualification.setText(doctorProfileDataList.get(i).getQualification());
                    txt_dr_exp.setText(doctorProfileDataList.get(i).getExperience() + " years experience overall");
                    dr_txt_like.setText(doctorProfileDataList.get(i).getRating());
                    dr_txt_patients_stories.setText(doctorProfileDataList.get(i).getReview());
                    txt_fee.setText("₹ " + doctorProfileDataList.get(i).getVideo_charge() + "Fees");
                    total = doctorProfileDataList.get(i).getVideo_charge();
                    txt_consulation_fee.setText("₹ " + doctorProfileDataList.get(i).getVideo_charge());
                    txt_total_amount.setText("₹ " + doctorProfileDataList.get(i).getVideo_charge());
                    txt_bottom_total.setText(doctorProfileDataList.get(i).getVideo_charge());
                    Picasso.get()
                            .load(doctorProfileDataList.get(i).getProfile_pic())
                            .fit()
                            .into(img_userprofile);
                    doctorPractices = doctorProfileDataList.get(i).getDoctorPractices();
                    txt_clinic_name.setText(doctorPractices.get(i).getClinic_name());
                    clinic_id = doctorPractices.get(i).getClinic_id();
                    txt_place.setText(doctorPractices.get(i).getClinic_city());


                } else {

                    Toast.makeText(this, "Doctor List", Toast.LENGTH_SHORT).show();

                }
                break;
            case APPLYCOUPON:
                ApplyCouponResponse applyCouponResponse = (ApplyCouponResponse) o;
                if (applyCouponResponse.getResult().equalsIgnoreCase("true")) {
                    rel_discount.setVisibility(View.VISIBLE);

                    discont = applyCouponResponse.getDiscount_amount();
                    txt_coupon_amount.setText("₹ " + String.valueOf(discont));
                    String totalAmout = String.valueOf(Integer.parseInt(total) - discont);
                    txt_total_amount.setText("₹ " + totalAmout);
                    txt_bottom_total.setText(totalAmout);

                } else if (applyCouponResponse.getResponseCode() == String.valueOf(401)) {
                    Toast.makeText(this, "" + applyCouponResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();

                } else if (applyCouponResponse.getResponseCode() == String.valueOf(400)) {
                    Toast.makeText(this, "" + applyCouponResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + applyCouponResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case BOOKAPPOINTMENT:
                BookAppointmentResponse bookAppointmentResponse = (BookAppointmentResponse) o;
                if (bookAppointmentResponse.getStatus() == 200) {
                    Toast.makeText(this, "" + bookAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    bookingData = bookAppointmentResponse.getBookingData();
                    bookingId = bookingData.getBooking_id();
                    startActivity(new Intent(this, SuccessActivity.class)
                            .putExtra("bookingId", bookingId)
                            .putExtra("date", dateone)
                            .putExtra("time", displayTime)
                            .putExtra("drname", drname)
                            .putExtra("clinic_id", clinic_id)
                    );
                } else if (bookingData.getStatus() == 400) {
                    Toast.makeText(this, "" + bookingData.getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "" + bookAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}