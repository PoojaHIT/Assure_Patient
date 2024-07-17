package com.hit.assure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.LoginActivity;
import com.hit.assure.Util.Helpers;
import com.hit.assure.Util.PreferenceServices;
//import com.zoho.salesiqembed.ZohoSalesIQ;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceServices.init(this);
//        ZohoSalesIQ.showLauncher(false);
//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if (!task.isSuccessful()) {
//                    return;
//                }
//                Log.e("FIREBASE-TOKEN", task.getResult().getToken());
//                PreferenceServices.getInstance().setFirebaseToken(task.getResult().getToken());
//            }
//        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!PreferenceServices.getInstance().getUser_id().isEmpty()) {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2500);
    }

//    private boolean isLoggedIn() {
//        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
//        int userValidity = preferences.getInt("userValidity", 0);
//        if (userValidity == 1) {
//            return true;
//        }else{
//            return false;
//        }
//    }

}