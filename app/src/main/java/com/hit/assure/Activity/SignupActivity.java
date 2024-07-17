package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.CHECK_MOBILE_NUMBER;
import static com.hit.assure.Retrofit.ServerCode.REGISTER;
import static com.hit.assure.Retrofit.ServerCode.RESENDOTPVERIFICATION;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hit.assure.Model.CheckmobilenumberResponse;
import com.hit.assure.Model.Register.RegisterResponse;
import com.hit.assure.Model.ResendOtp.ResendOtpVerification;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
//import com.zoho.salesiqembed.ZohoSalesIQ;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity implements ServerResponse {

    private EditText edt_fullname, editTextFirstName;
    private TextView txt_singup;
    private String agent = "android";
    //    private String token = "5QivVQP1mVxhX0ybWfoa3T4zD00tOrprX1p6TbMjUXfgHmpn3psckVKtVkon";
    private String token = "";
    private String lat = "19.207237";
    private String loong = "72.834824";
    private ProgressDialog progressDialog;
    private TextView txt_singin;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        ZohoSalesIQ.showLauncher(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        init();

        final String[] token1 = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isComplete()) {
                    token1[0] = task.getResult();
                    Log.e("AppConstants", "onComplete: new Token got: " + token1[0]);
                    PreferenceServices.getInstance().setUser_token(token1[0]);
                    token = token1[0];
                }
            }
        });
        if (getIntent() != null) {
            number = getIntent().getStringExtra("number");
            //       Log.e("number", number);
            editTextFirstName.setText(number);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init() {

        edt_fullname = findViewById(R.id.edt_fullname);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextFirstName.setText(number);
        txt_singup = findViewById(R.id.txt_singup);
        txt_singin = findViewById(R.id.txt_singin);

        txt_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFirstName.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter your Number", Toast.LENGTH_SHORT).show();
                } else if (edt_fullname.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    new Requestor(CHECK_MOBILE_NUMBER, SignupActivity.this).getCheckmobilenumberfor_signup(editTextFirstName.getText().toString().trim());
                }
            }
        });

        txt_singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }


    @Override
    public void success(Object o, int code) {
        switch (code) {
            case CHECK_MOBILE_NUMBER:
                CheckmobilenumberResponse checkmobilenumberResponse = (CheckmobilenumberResponse) o;
                if (checkmobilenumberResponse.getStatus() == 200) {
                    progressDialog.dismiss();

                    startActivity(new Intent(SignupActivity.this, OtpActivity.class)
                            .putExtra("from_login_signup", "signup")
                            .putExtra("user_name", edt_fullname.getText().toString().trim())
                            .putExtra("phone", editTextFirstName.getText().toString().trim())
                            .putExtra("token", token)
                    );

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(this, "" + checkmobilenumberResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}