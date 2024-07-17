package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.LONGINWITHOTP;
import static com.hit.assure.Retrofit.ServerCode.SOCIALLOGIN;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hit.assure.Model.LoginWithNumber.SendOtpResponse;
import com.hit.assure.Model.SocialLogin.SocialLoginResponse;
import com.hit.assure.Model.SocialLogin.SocialUserData;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
//import com.zoho.salesiqembed.ZohoSalesIQ;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, ServerResponse {

    private EditText editTextFirstName;
    private TextView txt_register, txt_signin;
    private RelativeLayout rl_googlelogin;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth;
    private static final int GOOGLE_LOGIN_REQUEST_CODE = 2;
    private ProgressDialog progressDialog;
    private String strEmail, strName, strMobile, strSocialId, strSocialType;
    private String otp;
    private String agent = "android";
    //    private String token = "5QivVQP1mVxhX0ybWfoa3T4zD00tOrprX1p6TbMjUXfgHmpn3psckVKtVkon";
    private String token = "";
    private String lat = "19.207237";
    private String loong = "72.834824";
    private RelativeLayout login_with_fb;
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private String gender = "Male";
    private List<SocialUserData> socialUserDataList;

    //Location
    private static int LOCATION_UPDATE_INTERVAL = 1500;
    private static int LOCATION_UPDATE_FASTEST_INTERVAL = 3000;
    private static int REQUEST_CODE_CHECK_SETTINGS = 8989;
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private Location mLocation;

    //END LOCATION

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ZohoSalesIQ.showLauncher(false);
        FacebookSdk.sdkInitialize(this);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

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

        PreferenceServices.init(this);
        printHashKey(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


//
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@androidx.annotation.NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            return;
//                        }
//                        PreferenceServices.getInstance().setFirebaseToken(task.getResult().getToken());
//                    }
//                });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("201801941036-lt2su3dneel9mj2md4o74hd3qhobg2e2.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
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
        editTextFirstName = findViewById(R.id.editTextFirstName);

        txt_register = findViewById(R.id.txt_register);
        txt_signin = findViewById(R.id.txt_signin);
        login_with_fb = findViewById(R.id.login_with_fb);

        rl_googlelogin = findViewById(R.id.rl_googlelogin);


        fbLoginButton = findViewById(R.id.fbLoginButton);
        fbLoginButton.setReadPermissions(Arrays.asList("email"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        showProgressDialog();
                        try {
                            strSocialId = object.getString("id");
                            strSocialType = "facebook";

                            if (object.has("email"))
                                strEmail = object.getString("email");
                            strName = object.getString("name");
                            gender = object.getString("gender");

                            PreferenceServices.getInstance().setUser_name(strName);
                            PreferenceServices.getInstance().setUser_email(strEmail);
                            LoginManager.getInstance().logOut();

                            socialLogin();
                        } catch (JSONException e) {
                            //Log.e("Facebook Error", e.getMessage());
                            hideProgressDialog();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Facebook Login Failed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("checkerror", String.valueOf(error));
                Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

        txt_register.setOnClickListener(this);
        rl_googlelogin.setOnClickListener(this);
        txt_signin.setOnClickListener(this);
        login_with_fb.setOnClickListener(this);
    }

  /*  @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_register:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.login_with_fb:
                fbLoginButton.performClick();
                break;
            case R.id.txt_signin:
                if (editTextFirstName.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your number", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressDialog();
                    new Requestor(LONGINWITHOTP, this).getOtp(editTextFirstName.getText().toString().trim(), lat, token, loong);
                }
                break;
            case R.id.rl_googlelogin:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, GOOGLE_LOGIN_REQUEST_CODE);
                break;
        }
    }*/

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.txt_register) {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }
        else if(item_id== R.id.login_with_fb) {
            fbLoginButton.performClick();
        }
        else if(item_id== R.id.txt_signin) {
            if (editTextFirstName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your number", Toast.LENGTH_SHORT).show();
            } else {
                showProgressDialog();
                new Requestor(LONGINWITHOTP, this).getOtp(editTextFirstName.getText().toString().trim(), lat,token, loong);
            }
        }
        else if(item_id== R.id.rl_googlelogin) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, GOOGLE_LOGIN_REQUEST_CODE);
        }
        // }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOOGLE_LOGIN_REQUEST_CODE:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    Toast.makeText(this, "Google login failed: " + result.getStatus().getStatusCode() + "::" + result.getStatus().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;

                        strEmail = user.getEmail();
                        strName = user.getDisplayName();
                        strSocialId = acct.getId();
                        strSocialType = "google";
                        Log.e("checkgoogle", strEmail);

//                        String fullname = strName;
//                        String[] parts = fullname.split("\\s+");
//                        String firstname = parts[0];
//                        String lastname = parts[1];

                        PreferenceServices.getInstance().setUser_name(strName);
                        PreferenceServices.getInstance().setUser_email(strEmail);

                        socialLogin();

                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(status -> mAuth.signOut());
                    } else {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), "Google login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void socialLogin() {

        new Requestor(SOCIALLOGIN, LoginActivity.this).getSociallogin(strSocialType, strSocialId, strEmail, agent, token, strName, gender, lat, loong);


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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code) {

            case LONGINWITHOTP:
                SendOtpResponse sendOtpResponse = (SendOtpResponse) o;
                if (sendOtpResponse.getStatus() == 200) {
//                    Toast.makeText(this, "" + sendOtpResponse.getOtp_code(), Toast.LENGTH_SHORT).show();
//                    otp = sendOtpResponse.getOtp_code();
//                    String form = String.valueOf(sendOtpResponse.getApplication_form());
//                    PreferenceServices.getInstance().setApplication_form(form);
                    startActivity(new Intent(this, OtpActivity.class)
                            .putExtra("from_login_signup", "login")
                            .putExtra("user_id", sendOtpResponse.getId())
                            .putExtra("token", sendOtpResponse.getToken())
                            .putExtra("application_form", sendOtpResponse.getApplication_form())
                            .putExtra("skin_application_form", sendOtpResponse.getSkin_application_form())
                            .putExtra("nutrition_application_form", sendOtpResponse.getNutrition_application_form())
                            .putExtra("phone", editTextFirstName.getText().toString()));
                } else if (sendOtpResponse.getStatus() == 404) {
                    Toast.makeText(this, "" + sendOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.e("checkerror1", sendOtpResponse.getMessage());
                } else {
                    Toast.makeText(this, "" + sendOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("checkerror2", sendOtpResponse.getMessage());
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class).putExtra("number", editTextFirstName.getText().toString().trim()));
                }
                break;
            case SOCIALLOGIN:
                SocialLoginResponse socialLoginResponse = (SocialLoginResponse) o;
                if (socialLoginResponse.getStatus() == 200) {
                    socialUserDataList = socialLoginResponse.getSocialUserData();
                    for (int i = 0; i < socialUserDataList.size(); i++) {
                        PreferenceServices.getInstance().setUser_id(socialUserDataList.get(i).getId());
                        PreferenceServices.getInstance().setUser_name(socialUserDataList.get(i).getName());
                        PreferenceServices.getInstance().setApplication_form(String.valueOf(socialLoginResponse.getSocialUserData().get(0).getApplication_form()));
                        PreferenceServices.getInstance().setSkin_consultation_application_form(String.valueOf(socialLoginResponse.getSocialUserData().get(0).getSkin_application_form()));
                        PreferenceServices.getInstance().setNutrition_consultation_application_form(String.valueOf(socialLoginResponse.getSocialUserData().get(0).getNutrition_application_form()));

                    }
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                }
                break;

        }
    }

    @Override
    public void error(Object o, int code) {
        hideProgressDialog();
    }

    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }
}