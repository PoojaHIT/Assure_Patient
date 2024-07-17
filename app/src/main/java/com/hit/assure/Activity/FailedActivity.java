package com.hit.assure.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hit.assure.R;

public class FailedActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView txt_checkagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed);

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
        img_back = findViewById(R.id.img_back_skin);
        txt_checkagain = findViewById(R.id.txt_checkagain);

        img_back.setOnClickListener(this);
        txt_checkagain.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        // switch (v.getId()){
        if(item_id== R.id.img_back_skin) {
            startActivity(new Intent(this, UploadSkinActivity.class));
        }
        else if(item_id== R.id.txt_checkagain) {
            startActivity(new Intent(this, UploadSkinActivity.class));
        }
        // }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}