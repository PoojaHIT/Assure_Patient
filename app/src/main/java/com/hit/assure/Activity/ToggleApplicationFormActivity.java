package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.FORM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.hit.assure.Activity.VirtualConsult.VirtualConsultantlistActivity;
import com.hit.assure.Model.PatientDetailsFrom.PatientDetailsResponse;
import com.hit.assure.Model.Search.SearchResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;

import java.util.Objects;

public class ToggleApplicationFormActivity extends AppCompatActivity implements ServerResponse {

    private ToggleButton toogle_hair, toogle_still_going_on, toogle_medication,toogle_diabetic, toogle_hypertensive, toogle_driniking, toogle_smoking, toogle_medicational;
    private String hairValue = "0", stillgoingon = "0", medication = "0", diabetic = "0", hypertensive = "0", smoking = "0", drinking = "0", terms = "0", medical = "0";
    private EditText edt_specify;
    private String firstname, lastname, mobile, about, medium, email, age, medicationdescription = "";
    private TextView txt_save;
    private String userId;
    private ProgressDialog progressDialog;
    CheckBox checkBox;
    private String cat;
    private String centreId;
    private EditText edt_medical;
    private String onlyVI= "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_application_form);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        userId = PreferenceServices.getInstance().getUser_id();


        if (getIntent() != null) {
            firstname = getIntent().getStringExtra("firstname");
            lastname = getIntent().getStringExtra("lastname");
            mobile = getIntent().getStringExtra("mobile");
            email = getIntent().getStringExtra("email");
            age = getIntent().getStringExtra("age");
            about = getIntent().getStringExtra("about");
            medium = getIntent().getStringExtra("medium");
            cat = getIntent().getStringExtra("cat");
            centreId = getIntent().getStringExtra("centreId");
            onlyVI = getIntent().getStringExtra("onlyVI");
        }

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


        checkBox = findViewById(R.id.check_box);
        if (checkBox.isChecked()) {
            terms = "1";
            Log.e("terms", terms);

        } else {
            terms = "0";
        }

        edt_medical = findViewById(R.id.edt_medical);
        txt_save = findViewById(R.id.txt_save);
        toogle_medicational = findViewById(R.id.toogle_medicational);
        toogle_hair = findViewById(R.id.toogle_hair);
        toogle_still_going_on = findViewById(R.id.toogle_still_going_on);
        toogle_medication = findViewById(R.id.toogle_medication);
        toogle_diabetic = findViewById(R.id.toogle_diabetic);
        toogle_hypertensive = findViewById(R.id.toogle_hypertensive);
        toogle_driniking = findViewById(R.id.toogle_driniking);
        toogle_smoking = findViewById(R.id.toogle_smoking);
        edt_specify = findViewById(R.id.edt_specify);

//        toogle_hair.onT(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn) {
//                    hairValue = "1";
//                    Log.e("hair", hairValue);
//
//                } else {
//                    hairValue = "0";
//                    Log.e("hairfalse", hairValue);
//                }
//            }
//        });



//        toogle_still_going_on.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn) {
//                    stillgoingon = "1";
//                    Log.e("hair", stillgoingon);
//
//                } else {
//                    stillgoingon = "0";
//                    Log.e("hairfalse", stillgoingon);
//                }
//            }
//        });
//
//        toogle_medication.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn) {
//                    medication = "1";
//                    Log.e("hair", medication);
//                    edt_specify.setVisibility(View.VISIBLE);
//                    medicationdescription = edt_specify.getText().toString();
//
//                } else {
//                    medication = "0";
//                    Log.e("hairfalse", medication);
//                    edt_specify.setVisibility(View.GONE);
//                }
//            }
//        });

//        toogle_diabetic.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn) {
//                    diabetic = "1";
//                    Log.e("hair", diabetic);
//
//                } else {
//                    diabetic = "0";
//                    Log.e("hairfalse", diabetic);
//                }
//            }
//        });
//
//        toogle_hypertensive.setOnToggledListener((toggleableView, isOn) -> {
//            if (isOn) {
//                hypertensive = "1";
//                Log.e("hair", hypertensive);
//
//            } else {
//                hypertensive = "0";
//                Log.e("hairfalse", hypertensive);
//            }
//        });
//
//        toogle_smoking.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn) {
//                    smoking = "1";
//                    Log.e("hair", smoking);
//
//                } else {
//                    smoking = "0";
//                    Log.e("hairfalse", smoking);
//                }
//            }
//        });
//
//        toogle_driniking.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn) {
//                    drinking = "1";
//                    Log.e("hair", drinking);
//
//                } else {
//                    drinking = "0";
//                    Log.e("hairfalse", drinking);
//                }
//            }
//        });

        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    showProgressDialog();
                    new Requestor(FORM, ToggleApplicationFormActivity.this).getPatientDetails(
                            userId,
                            firstname,
                            lastname,
                            mobile,
                            email,
                            age,
                            centreId,
                            about,
                            medium,
                            hairValue,
                            stillgoingon,
                            medication,
                            medicationdescription,
                            medical,
                            edt_medical.getText().toString().trim(),
                            diabetic,
                            hypertensive,
                            smoking,
                            drinking,
                            terms);
                } else {
                    Toast.makeText(ToggleApplicationFormActivity.this, "Please agree terms and conditions.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case FORM:
                PatientDetailsResponse patientDetailsResponse = (PatientDetailsResponse) o;
                if (patientDetailsResponse.getResponseCode().equalsIgnoreCase("200")) {
                    hideProgressDialog();
                    Toast.makeText(this, "" + patientDetailsResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceServices.getInstance().setApplication_form(patientDetailsResponse.getApplication_form());
                    if (!PreferenceServices.getInstance().getCategoryID().isEmpty()) {
                        startActivity(new Intent(this, VirtualConsultantlistActivity.class).putExtra("cat", PreferenceServices.getInstance().getCategoryID()).putExtra("onlyVI", onlyVI));
                        finish();
                    }
                } else if (patientDetailsResponse.getResponseCode().equalsIgnoreCase("400")) {
                    hideProgressDialog();
                    Toast.makeText(this, "" + patientDetailsResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            hairValue = "1";
            Log.e("hair", hairValue);
        } else {
            // Disable vibrate
            hairValue = "0";
            Log.e("hairfalse", hairValue);
        }
    }

    public void onToggleClickedStill(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            stillgoingon = "1";
            Log.e("hair", stillgoingon);
        } else {
            // Disable vibrate
            stillgoingon = "0";
            Log.e("hairfalse", stillgoingon);
        }
    }

    public void onToggleClickedMedical(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            medical = "1";
            Log.e("hair", medical);
            edt_medical.setVisibility(View.VISIBLE);
        } else {
            // Disable vibrate
            medical = "0";
            Log.e("hairfalse", medical);
            edt_medical.setVisibility(View.GONE);
        }
    }


    public void onToggleClickedDiabetic(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            diabetic = "1";
            Log.e("hair", diabetic);
        } else {
            // Disable vibrate
            diabetic = "0";
            Log.e("hairfalse", diabetic);
        }
    }

    public void onToggleClickedHyper(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            hypertensive = "1";
            Log.e("hair", hypertensive);
        } else {
            // Disable vibrate
            hypertensive = "0";
            Log.e("hairfalse", hypertensive);
        }
    }

    public void onToggleClickedSmoking(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            smoking = "1";
            Log.e("hair", smoking);
        } else {
            // Disable vibrate
            smoking = "0";
            Log.e("hairfalse", smoking);
        }
    }

    public void onToggleClickedDrinking(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            drinking = "1";
            Log.e("hair", drinking);
        } else {
            // Disable vibrate
            drinking = "0";
            Log.e("hairfalse", drinking);
        }
    }

    public void onToggleClickedMedication(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            medication = "1";
            Log.e("hair", medication);
            edt_specify.setVisibility(View.VISIBLE);
            medicationdescription = edt_specify.getText().toString();
        } else {
            // Disable vibrate
            medication = "0";
            Log.e("hairfalse", medication);
            edt_specify.setVisibility(View.GONE);
        }
    }



    @Override
    public void error(Object o, int code) {

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
}