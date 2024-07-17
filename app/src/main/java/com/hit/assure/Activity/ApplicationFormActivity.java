package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.CENTRE;

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

import androidx.appcompat.app.AppCompatActivity;

import com.hit.assure.Model.Centre.CentreData;
import com.hit.assure.Model.Centre.CentreResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.Constant;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFormActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    private Spinner sp_category, sp_subcat, sp_internet, sp_centre;
    private EditText edt_firstname, edt_lastname, editMail, editMobile, edtxt_age, editother;
    private String aboutus = "", medium = "";
    private TextView txt_next;
    private LinearLayout ll_spinner_subcat, ll_spinner_internet;
    private String cat;
    private List<CentreData> centreData;
    private List<String> centre;
    private String centreId;
    private String onlyVI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

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
        txt_next = findViewById(R.id.txt_next);
        ll_spinner_subcat = findViewById(R.id.ll_spinner_subcat);
        txt_next.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {

        int item_id=v.getId();

        // switch (v.getId()) {
        if(item_id== R.id.txt_next) {
            if (edt_firstname.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
            } else if (edt_lastname.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
            } else if (editMobile.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
            } else if (!Constant.isValidEmail(editMail.getText().toString())) {
                editMail.requestFocus();
                Toast.makeText(this, "Enter Valid Email Id", Toast.LENGTH_SHORT).show();
            } else if (editMail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            } else if (edtxt_age.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your age", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, ToggleApplicationFormActivity.class)
                        .putExtra("firstname", edt_firstname.getText().toString())
                        .putExtra("lastname", edt_lastname.getText().toString())
                        .putExtra("mobile", editMobile.getText().toString())
                        .putExtra("email", editMail.getText().toString())
                        .putExtra("age", edtxt_age.getText().toString())
                        .putExtra("about", aboutus)
                        .putExtra("medium", medium)
                        .putExtra("cat", cat)
                        .putExtra("centreId", centreId)
                        .putExtra("onlyVI", onlyVI));

            }

        }
        //}
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
}