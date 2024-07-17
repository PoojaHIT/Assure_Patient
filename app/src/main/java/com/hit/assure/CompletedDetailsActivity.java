package com.hit.assure;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompletedDetailsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_completed_details);

        init();
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

    }
}