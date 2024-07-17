package com.hit.assure.Activity.VisitClinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hit.assure.R;

public class VisitclinicTimeslotActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitclinic_timeslot);
        inita();
    }

    private void inita() {
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        //switch (v.getId()) {
        if(item_id== R.id.img_back) {
            finish();
        }
        //}
    }
}