package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.OTPVerification;
import static com.hit.assure.Retrofit.ServerCode.REGISTER;
import static com.hit.assure.Retrofit.ServerCode.RESENDOTPVERIFICATION;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hit.assure.Model.OtpVerification.OtpVerificationResponse;
import com.hit.assure.Model.OtpVerification.UserData;
import com.hit.assure.Model.Register.RegisterResponse;
import com.hit.assure.Model.ResendOtp.ResendOtpVerification;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
//import com.zoho.salesiqembed.ZohoSalesIQ;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class OtpActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    private ProgressDialog progressDialog;
    private TextView txt_continue;
    private String otp;
    private PinView otp_pin;
    private LinearLayout ll_resend_otp;
    private String phone = "", user_name = "", codebySystem, user_id, token, from_login_signup = "";
    private int application_form, skin_application_form, nutrition_application_form;
    private TextView txt_phone;
    private List<UserData> userData;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
//        ZohoSalesIQ.showLauncher(false);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        inta();

        if (getIntent() != null) {
            user_name = getIntent().getStringExtra("user_name");
            phone = getIntent().getStringExtra("phone");
            user_id = getIntent().getStringExtra("user_id");
            token = getIntent().getStringExtra("token");
            from_login_signup = getIntent().getStringExtra("from_login_signup");
            application_form = getIntent().getIntExtra("application_form", 0);
            skin_application_form = getIntent().getIntExtra("skin_application_form", 0);
            nutrition_application_form = getIntent().getIntExtra("nutrition_application_form", 0);
            txt_phone.setText(phone);
        }
        mAuth = FirebaseAuth.getInstance();

/*
        if (phone.equals("9967610130")) {
         } else {
            sendVerificationCode("+91" + phone);
        }
*/

        sendVerificationCode("+91" + phone);


    }

    private void inta() {
        txt_continue = findViewById(R.id.txt_continue);
        txt_continue.setOnClickListener(this);
        otp_pin = findViewById(R.id.otp_pin);
        ll_resend_otp = findViewById(R.id.ll_resend_otp);
        txt_phone = findViewById(R.id.txt_phone);
        ll_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode("+91" + phone);
            }
        });

    }


    private void sendVerificationCode(String getnumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(getnumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallback)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    Toast.makeText(OtpActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    codebySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        otp_pin.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(OtpActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("check firebase msg", e.getMessage());
                }
            };

    //
    private void callNextActivity() {
        String code = otp_pin.getText().toString();
        if (!code.isEmpty()) {

                verifyCode(code);


        } else {
            Toast.makeText(OtpActivity.this, "Please Enter OTP.", Toast.LENGTH_SHORT).show();
        }

    }

    private void verifyCode(String code) {
        progressDialog.show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codebySystem, code);
        signinUsingCredential(credential);
    }

    //
    private void signinUsingCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (from_login_signup.equals("signup")) {
                        progressDialog.show();
                        new Requestor(REGISTER, OtpActivity.this).getResgister(user_name, phone, "android", token, "19.207237", "72.834824");
                    } else {
                        PreferenceServices.getInstance().setUser_id(user_id);
                        PreferenceServices.getInstance().setSkin_consultation_application_form(String.valueOf(skin_application_form));
                        PreferenceServices.getInstance().setNutrition_consultation_application_form(String.valueOf(nutrition_application_form));
                        PreferenceServices.getInstance().setApplication_form(String.valueOf(application_form));

//                    PreferenceServices.getInstance().setUser_name();
                        startActivity(new Intent(OtpActivity.this, HomeActivity.class));
                        finish();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(OtpActivity.this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        //switch (v.getId()) {
            if(v.getId()== R.id.txt_continue) {
                callNextActivity();
            }
      //  }
    }


    @Override
    public void success(Object o, int code) {
        switch (code) {


            case REGISTER:
                RegisterResponse registerResponse = (RegisterResponse) o;
                if (registerResponse.getStatus() == 200) {
                    progressDialog.dismiss();

                    PreferenceServices.getInstance().setUser_id(registerResponse.getId());
                    PreferenceServices.getInstance().setSkin_consultation_application_form(String.valueOf(registerResponse.getSkin_application_form()));
                    PreferenceServices.getInstance().setNutrition_consultation_application_form(String.valueOf(registerResponse.getNutrition_application_form()));
                    PreferenceServices.getInstance().setApplication_form(String.valueOf(registerResponse.getApplication_form()));
                    startActivity(new Intent(OtpActivity.this, HomeActivity.class));
                    finish();

                } else {
                    progressDialog.dismiss();
                    PreferenceServices.getInstance().setUser_id(user_id);
                    PreferenceServices.getInstance().setSkin_consultation_application_form(String.valueOf(skin_application_form));
                    PreferenceServices.getInstance().setNutrition_consultation_application_form(String.valueOf(nutrition_application_form));
                    PreferenceServices.getInstance().setApplication_form(String.valueOf(application_form));
//                    PreferenceServices.getInstance().setUser_name();
                    startActivity(new Intent(OtpActivity.this, HomeActivity.class));
                    finish();
                }
                break;

        }
    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }


}