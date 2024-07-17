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

public class DietActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView txt_chat_with_nitritionist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
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
        txt_chat_with_nitritionist = findViewById(R.id.txt_chat_with_nitritionist);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_chat_with_nitritionist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
        }
          else if(item_id== R.id.txt_chat_with_nitritionist){
                startActivity(new Intent(DietActivity.this, ChatActivity.class)
                        .putExtra("value", "nutrition")
                        .putExtra("category","nutrition")
                        .putExtra("cat", "3")
                        .putExtra("onlyVI", "nutrition"));

        }
    }
}