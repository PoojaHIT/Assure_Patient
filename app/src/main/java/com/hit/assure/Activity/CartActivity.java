package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hit.assure.R;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_checkout;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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
        txt_checkout = findViewById(R.id.txt_checkout);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_checkout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.txt_checkout) {
            startActivity(new Intent(CartActivity.this, ShiptoActivity.class));
        }
        else if(item_id== R.id.img_back) {
            finish();
        }
        // }
    }
}