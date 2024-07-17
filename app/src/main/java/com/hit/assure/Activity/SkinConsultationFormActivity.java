package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.CENTRE;
import static com.hit.assure.Retrofit.ServerCode.FORM;
import static com.hit.assure.Retrofit.ServerCode.SKIN_CONSULT_FORM;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hit.assure.Activity.VirtualConsult.VirtualConsultantlistActivity;
import com.hit.assure.Model.Centre.CentreData;
import com.hit.assure.Model.Centre.CentreResponse;
import com.hit.assure.Model.PatientDetailsFrom.PatientDetailsResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.Constant;
import com.hit.assure.Util.PreferenceServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkinConsultationFormActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    private Spinner sp_category, sp_subcat, sp_internet, sp_centre;
    private ToggleButton toogle_skin_concern, toogle_medication;
    private EditText edt_firstname, edt_lastname, editMail, editMobile, edtxt_age, editother, edt_medication, edt_skinconcern;
    private String aboutus = "", medium = "";
    private TextView txt_submit;
    private LinearLayout ll_spinner_subcat, ll_spinner_internet;
    private String cat;
    private ProgressDialog progressDialog;
    private List<CentreData> centreData;
    private List<String> centre;
    private String medication = "0", skincare = "0";
    private String centreId;
    private String onlyVI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_consultation_form);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        if (getIntent() != null) {
            cat = getIntent().getStringExtra("cat");
            onlyVI = getIntent().getStringExtra("onlyVI");

        }

        init();

        new Requestor(CENTRE, this).getCentre();
    }


    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }


    public void init() {

        toogle_skin_concern = findViewById(R.id.toogle_skin_concern);
        edt_skinconcern = findViewById(R.id.edt_skinconcern);
        toogle_medication = findViewById(R.id.toogle_medication);
        edt_medication = findViewById(R.id.edt_medication);
        editother = findViewById(R.id.editother);
        ll_spinner_internet = findViewById(R.id.ll_spinner_internet);

        sp_centre = findViewById(R.id.sp_centre);
        sp_category = findViewById(R.id.sp_category);
        sp_subcat = findViewById(R.id.sp_subcat);
        sp_internet = findViewById(R.id.sp_internet);
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        editMail = findViewById(R.id.editMail);
        editMobile = findViewById(R.id.editMobile);
        edtxt_age = findViewById(R.id.edtxt_age);
        txt_submit = findViewById(R.id.txt_submit);
        ll_spinner_subcat = findViewById(R.id.ll_spinner_subcat);
        txt_submit.setOnClickListener(this);


        ArrayAdapter<CharSequence> datesAdapter = ArrayAdapter.createFromResource(this, R.array.hear_about, android.R.layout.simple_spinner_item);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(datesAdapter);
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) parent.getChildAt(0)).setTextSize(14);

                aboutus = String.valueOf(sp_category.getSelectedItem());
                if (aboutus.equalsIgnoreCase("Newspaper")) {
                    ll_spinner_subcat.setVisibility(View.VISIBLE);
                    ll_spinner_internet.setVisibility(View.GONE);
                    editother.setVisibility(View.GONE);
                } else if (aboutus.equalsIgnoreCase("Internet")) {
                    editother.setVisibility(View.GONE);
                    ll_spinner_subcat.setVisibility(View.GONE);
                    ll_spinner_internet.setVisibility(View.VISIBLE);
                } else if (aboutus.equalsIgnoreCase("Other")) {
                    editother.setVisibility(View.VISIBLE);
                    ll_spinner_subcat.setVisibility(View.GONE);
                    ll_spinner_internet.setVisibility(View.GONE);
                } else {
                    editother.setVisibility(View.GONE);
                    ll_spinner_subcat.setVisibility(View.GONE);
                    ll_spinner_internet.setVisibility(View.GONE);
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        medium = editother.getText().toString().trim();

        ArrayAdapter<CharSequence> datesnewspaper = ArrayAdapter.createFromResource(this, R.array.newspaperlist, android.R.layout.simple_spinner_item);
        datesnewspaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_subcat.setAdapter(datesnewspaper);
        sp_subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) parent.getChildAt(0)).setTextSize(14);

                medium = String.valueOf(sp_subcat.getSelectedItem());

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> datesinternet = ArrayAdapter.createFromResource(this, R.array.internet, android.R.layout.simple_spinner_item);
        datesinternet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_internet.setAdapter(datesinternet);
        sp_internet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) parent.getChildAt(0)).setTextSize(14);

                medium = String.valueOf(sp_internet.getSelectedItem());

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_centre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                centreId = centreData.get(position).getCentre_id();
                Log.e("centreId", centreId);
                ((TextView) parent.getChildAt(0)).setTextSize(14);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onToggleClickedMedicationn(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            medication = "1";
            edt_medication.setVisibility(View.VISIBLE);
        } else {
            medication = "0";
            edt_medication.setVisibility(View.GONE);
        }
    }

    public void onToggleClickedskinconcern(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            skincare = "1";
            edt_skinconcern.setVisibility(View.VISIBLE);
        } else {
            skincare = "0";
            edt_skinconcern.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
       // switch (v.getId()) {
            if(v.getId()== R.id.txt_submit){
                if (edt_firstname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
                } else if (edt_lastname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
                } else if (editMobile.getText().toString().length() != 10) {
                    Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (editMobile.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                } else if (!Constant.isValidEmail(editMail.getText().toString())) {
                    editMail.requestFocus();
                    Toast.makeText(this, "Enter Valid Email Id", Toast.LENGTH_SHORT).show();
                } else if (editMail.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
                } else if (edtxt_age.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your age", Toast.LENGTH_SHORT).show();
                } else if (skincare.equalsIgnoreCase("1")) {
                    if (edt_skinconcern.getText().toString().trim().isEmpty()) {
                        Toast.makeText(this, "Enter skin concern", Toast.LENGTH_SHORT).show();
                    } else {

                        showProgressDialog();
                        new Requestor(SKIN_CONSULT_FORM, SkinConsultationFormActivity.this).getSkinConsultantPatientDetails(
                                PreferenceServices.getInstance().getUser_id(),
                                edt_firstname.getText().toString().trim(),
                                edt_lastname.getText().toString().trim(),
                                editMobile.getText().toString().trim(),
                                editMail.getText().toString().trim(),
                                edtxt_age.getText().toString().trim(),
                                centreId,
                                aboutus,
                                medium,
                                skincare,
                                edt_skinconcern.getText().toString().trim(),
                                medication,
                                edt_medication.getText().toString().trim()
                        );
                    }
                } else if (medication.equalsIgnoreCase("1")) {
                    if (edt_medication.getText().toString().trim().isEmpty()) {
                        Toast.makeText(this, "Enter medication", Toast.LENGTH_SHORT).show();
                    } else {

                        showProgressDialog();
                        new Requestor(SKIN_CONSULT_FORM, SkinConsultationFormActivity.this).getSkinConsultantPatientDetails(
                                PreferenceServices.getInstance().getUser_id(),
                                edt_firstname.getText().toString().trim(),
                                edt_lastname.getText().toString().trim(),
                                editMobile.getText().toString().trim(),
                                editMail.getText().toString().trim(),
                                edtxt_age.getText().toString().trim(),
                                centreId,
                                aboutus,
                                medium,
                                skincare,
                                edt_skinconcern.getText().toString().trim(),
                                medication,
                                edt_medication.getText().toString().trim()
                        );
                    }
                } else {

                    showProgressDialog();
                    new Requestor(SKIN_CONSULT_FORM, SkinConsultationFormActivity.this).getSkinConsultantPatientDetails(
                            PreferenceServices.getInstance().getUser_id(),
                            edt_firstname.getText().toString().trim(),
                            edt_lastname.getText().toString().trim(),
                            editMobile.getText().toString().trim(),
                            editMail.getText().toString().trim(),
                            edtxt_age.getText().toString().trim(),
                            centreId,
                            aboutus,
                            medium,
                            skincare,
                            edt_skinconcern.getText().toString().trim(),
                            medication,
                            edt_medication.getText().toString().trim()
                    );
                }


        }
    }


    @Override
    public void success(Object o, int code) {
        switch (code) {

            case CENTRE:
                CentreResponse centreResponse = (CentreResponse) o;
                if (centreResponse.getResponseCode().equalsIgnoreCase("200")) {
                    centreData = centreResponse.getCentreData();
                    centre = new ArrayList<>();

                    for (int i = 0; i < centreData.size(); i++) {
                        centre.add(centreData.get(i).getCentre_name());
                        //  subCategory.add(subCategoryData.get(i).getId());

                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, centre);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_centre.setAdapter(dataAdapter);

                } else {

                    Toast.makeText(this, "" + centreResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;

            case SKIN_CONSULT_FORM:
                PatientDetailsResponse patientDetailsResponse = (PatientDetailsResponse) o;
                if (patientDetailsResponse.getResponseCode().equalsIgnoreCase("200")) {
                    hideProgressDialog();
                    Toast.makeText(this, "" + patientDetailsResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceServices.getInstance().setSkin_consultation_application_form(patientDetailsResponse.getApplication_form());
                    if (!PreferenceServices.getInstance().getCategoryID().isEmpty()) {
                        startActivity(new Intent(this, VirtualConsultantlistActivity.class)
                                .putExtra("cat", PreferenceServices.getInstance().getCategoryID())
                                .putExtra("onlyVI", onlyVI));
                        finish();
                    }
                } else if (patientDetailsResponse.getResponseCode().equalsIgnoreCase("400")) {
                    hideProgressDialog();
                    Toast.makeText(this, "" + patientDetailsResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
//            case SOCIALMEDIA:
//                SocialMediaResponse socialMediaResponse = (SocialMediaResponse) o;
//                if (socialMediaResponse.getResponseCode().equalsIgnoreCase("200")) {
//                    subCategory = new ArrayList<>();
//                    socialMediaData = socialMediaResponse.getSocialMediaData();
//                    for (int i = 0; i < socialMediaData.size(); i++) {
//                        subCategory.add(socialMediaData.get(i).getSocial_media());
//                        //  subCategory.add(subCategoryData.get(i).getId());
//
//                    }
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCategory);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    sp_category.setAdapter(dataAdapter);
//                }
//                break;
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