package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hit.assure.R;

public class DoctorDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        inita();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    private void inita() {
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
       // }
        }
    }
}