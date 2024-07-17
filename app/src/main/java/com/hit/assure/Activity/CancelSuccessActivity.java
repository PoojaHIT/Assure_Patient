package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hit.assure.R;

public class CancelSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_return_to_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_success);


        init();

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init(){
        txt_return_to_home = findViewById(R.id.txt_return_to_home);
        txt_return_to_home.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       // switch (v.getId()){
            if(v.getId()== R.id.txt_return_to_home) {
                startActivity(new Intent(this, HomeActivity.class));
            }
        //}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}