package com.hit.assure.Activity.MyJourney;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hit.assure.Activity.AppointmentActivity;
import com.hit.assure.R;
import com.hit.assure.Util.PreferenceServices;

public class MyjourneyActivity extends AppCompatActivity {

    private TextView txt_username;
    private LinearLayout ll_hair_treatment, ll_diet_plan, ll_appointments, ll_skin_treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjourney);
        inita();
    }

    private void inita() {
        ll_hair_treatment = findViewById(R.id.ll_hair_treatment);
        ll_diet_plan = findViewById(R.id.ll_diet_plan);
        ll_appointments = findViewById(R.id.ll_appointments);
        ll_skin_treatment = findViewById(R.id.ll_skin_treatment);

        txt_username = findViewById(R.id.txt_username);

        txt_username.setText("Hello " + PreferenceServices.getInstance().getUser_name() + ", ");

        // skincare = 1,  haircare = 2,   nutrition = 3

        ll_hair_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyjourneyActivity.this, DoctorlistActivity.class)
                        .putExtra("cat_id","2")
                        .putExtra("cat_name","Hair Consultation")
                );
            }
        });

        ll_diet_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyjourneyActivity.this, DoctorlistActivity.class)
                        .putExtra("cat_id","3")
                        .putExtra("cat_name","Diet Consultation"));
            }
        });

        ll_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyjourneyActivity.this, AppointmentActivity.class));
            }
        });

        ll_skin_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyjourneyActivity.this, DoctorlistActivity.class)
                        .putExtra("cat_id","1")
                        .putExtra("cat_name","Skin Consultation"));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

}