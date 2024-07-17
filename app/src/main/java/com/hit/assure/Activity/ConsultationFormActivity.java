package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.BEFORE_AFTER;
import static com.hit.assure.Retrofit.ServerCode.FETCH_CONSULTATION_FORM;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.angads25.toggle.widget.LabeledSwitch;
import com.hit.assure.Model.ConsultationForm.ConsultationformData;
import com.hit.assure.Model.ConsultationForm.ConsultationformResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;

import java.util.Objects;

public class ConsultationFormActivity extends AppCompatActivity implements ServerResponse {

    private ImageView img_back;
    private ProgressDialog progressDialog;
    private LinearLayout ll_desc;
    private TextView txt_firstname, txt_lastname, txt_phonenumber, txt_email,
            txt_about, txt_medium, txt_hairloss, txt_stillgoing, txt_medication, txt_desc;
    private ToggleButton toogle_hypertensive, toogle_driniking, toogle_smoking;
    private ToggleButton toogle_diabetic;
    private ConsultationformData consultationformData;
    private LinearLayout ll_medium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_form);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        showProgressDialog();
        inita();
//        new Requestor(FETCH_CONSULTATION_FORM, this).getUserApplicationform("3");
        new Requestor(FETCH_CONSULTATION_FORM, this).getUserApplicationform(PreferenceServices.instance().getUser_id());
        Log.e("checkiduser", PreferenceServices.instance().getUser_id());
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
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toogle_diabetic = findViewById(R.id.toogle_diabetic);
        toogle_hypertensive = findViewById(R.id.toogle_hypertensive);
        toogle_driniking = findViewById(R.id.toogle_driniking);
        toogle_smoking = findViewById(R.id.toogle_smoking);
        ll_desc = findViewById(R.id.ll_desc);

        toogle_diabetic.setEnabled(false);
        toogle_hypertensive.setEnabled(false);
        toogle_driniking.setEnabled(false);
        toogle_smoking.setEnabled(false);

        ll_medium = findViewById(R.id.ll_medium);
        txt_lastname = findViewById(R.id.txt_lastname);
        txt_firstname = findViewById(R.id.txt_firstname);
        txt_phonenumber = findViewById(R.id.txt_phonenumber);
        txt_email = findViewById(R.id.txt_email);
        txt_about = findViewById(R.id.txt_about);
        txt_medium = findViewById(R.id.txt_medium);
        txt_hairloss = findViewById(R.id.txt_hairloss);
        txt_stillgoing = findViewById(R.id.txt_stillgoing);
        txt_medication = findViewById(R.id.txt_medication);
        txt_desc = findViewById(R.id.txt_desc);
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case FETCH_CONSULTATION_FORM:
                ConsultationformResponse consultationformResponse = (ConsultationformResponse) o;
                if (consultationformResponse.getResult().equalsIgnoreCase("true")) {
                    hideProgressDialog();
                    consultationformData = consultationformResponse.getConsultationformData();
                    if (consultationformData != null) {
                        txt_firstname.setText(consultationformData.getFirst_name());
                        txt_lastname.setText(consultationformData.getLast_name());
                        txt_phonenumber.setText(consultationformData.getMobile_number());
                        txt_email.setText(consultationformData.getEmail_id());
                        txt_about.setText(consultationformData.getAbout_us());
                        if (consultationformData.getAbout_us().equalsIgnoreCase("Walk in")) {
                            ll_medium.setVisibility(View.GONE);
                        } else {
                            ll_medium.setVisibility(View.VISIBLE);
                        }


                        if (!consultationformData.getMedication_descrption().isEmpty() && consultationformData.getMedication_descrption() != null) {
                            txt_desc.setText(consultationformData.getMedication_descrption());
                        } else {
                            ll_desc.setVisibility(View.GONE);
                        }
                        if (consultationformData.getFamily_hair_loss().equalsIgnoreCase("1")) {
                            txt_hairloss.setText("Yes");
                        } else {
                            txt_hairloss.setText("No");
                        }
                        if (consultationformData.getHair_loss_still_going().equalsIgnoreCase("1")) {
                            txt_stillgoing.setText("Yes");
                        } else {
                            txt_stillgoing.setText("No");
                        }
                        if (consultationformData.getMedication().equalsIgnoreCase("1")) {
                            txt_medication.setText("Yes");
                        } else {
                            txt_medication.setText("No");
                        }

                        if (consultationformData.getDiabetic().equalsIgnoreCase("1")) {
                            toogle_diabetic.setChecked(true);
                        } else {
                            toogle_diabetic.setChecked(false);
                        }
                        if (consultationformData.getHypertensive().equalsIgnoreCase("1")) {
                            toogle_hypertensive.setChecked(true);
                        } else {
                            toogle_hypertensive.setChecked(false);
                        }

                        if (consultationformData.getSmoking().equalsIgnoreCase("1")) {
                            toogle_smoking.setChecked(true);
                        } else {
                            toogle_smoking.setChecked(false);
                        }

                        if (consultationformData.getDrinking().equalsIgnoreCase("1")) {
                            toogle_driniking.setChecked(true);
                        } else {
                            toogle_driniking.setChecked(false);
                        }

                    }
                } else {
                    hideProgressDialog();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}