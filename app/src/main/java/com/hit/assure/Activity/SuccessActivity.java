package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hit.assure.R;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_backtohome,txt_app_id,txt_app_date,txt_app_time,txt_more_view;
    private String bookingId;
    private String date;
    private String time;
    private ImageView img_gif;
    private String drname;
    private TextView txt_dr_name;
    private String clinic_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        inita();

        if (getIntent() != null){

            bookingId = getIntent().getStringExtra("bookingId");
            date = getIntent().getStringExtra("date");
            clinic_id = getIntent().getStringExtra("clinic_id");
            Log.e("yellowdate", date);
            time = getIntent().getStringExtra("time");
            drname = getIntent().getStringExtra("drname");
            txt_dr_name.setText(drname);
            txt_app_id.setText(bookingId);
            txt_app_date.setText(date);
            txt_app_time.setText(time);

        }

        Glide.with(this)
                .load(R.drawable.img_success)
                .into(img_gif);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    private void inita() {
        txt_backtohome = findViewById(R.id.txt_backtohome);
        txt_dr_name = findViewById(R.id.txt_dr_name);
        img_gif = findViewById(R.id.img_gif);
        txt_more_view = findViewById(R.id.txt_more_view);
        txt_app_date = findViewById(R.id.txt_app_date);
        txt_app_time = findViewById(R.id.txt_app_time);
        txt_backtohome.setOnClickListener(this);
        txt_more_view.setOnClickListener(this);
        txt_app_id =findViewById(R.id.txt_app_id);
    }

    @Override
    public void onClick(View v) {
        // switch (v.getId()) {
        if (v.getId() == R.id.txt_backtohome){
            startActivity(new Intent(SuccessActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    else if(v.getId()== R.id.txt_more_view){
                startActivity(new Intent(this, AppointmentActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SuccessActivity.this, HomeActivity.class));
    }
}