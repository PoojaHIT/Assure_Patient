package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hit.assure.R;

public class ShiptoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_plus, img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipto);

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
        img_plus = findViewById(R.id.img_plus);
        img_back = findViewById(R.id.img_back);
        img_plus.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       // switch (v.getId()) {
            if(v.getId()== R.id.img_plus) {
                startActivity(new Intent(ShiptoActivity.this, AddAddressActivity.class));
            }
            else if(v.getId()== R.id.img_back){
                finish();

        }
    }
}