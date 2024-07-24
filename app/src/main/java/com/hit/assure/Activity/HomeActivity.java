package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.UPDATEUSERDATA;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hit.assure.Activity.MyJourney.MyjourneyActivity;
import com.hit.assure.Fragment.ActiveFragment;
import com.hit.assure.Fragment.BlogFragment;
import com.hit.assure.Fragment.ChatFragment;
import com.hit.assure.Fragment.HomeFragment;
import com.hit.assure.Fragment.SearchDoctors;
import com.hit.assure.Fragment.SearchFragment;
import com.hit.assure.Model.UserDetails.UserDetailsData;
import com.hit.assure.Model.UserDetails.UserDetailsResponse;
import com.hit.assure.PrecriptionActivity;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.Constant;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;
//import com.zoho.salesiqembed.ZohoSalesIQ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    @SuppressLint("StaticFieldLeak")
    private LinearLayout ll_profile_appointment,ll_myprescription;
    private RelativeLayout llHome, llSearch, ll_product, ll_chat;
    @SuppressLint("StaticFieldLeak")
    public static ImageView iv_home, iv_search, iv_product, iv_chat, iv_home_unselected, iv_search_unselected, iv_product_unselected, iv_chat_unselected;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout llDrawerLayout;
    private boolean doubleBackToExitPressedOnce = false;

    public static LinearLayout ll_editprofile, ll_logout, ll_myjourney;
    private List<UserDetailsData> userDetailsDataList;
    private String userId = "";
    private ProgressDialog progressDialog;

    @SuppressLint("StaticFieldLeak")
    public static DrawerLayout drawerLayout;
    private CircleImageView img_userprofile;

    private FrameLayout frameLayout_container;
    private TextView txt_ourdoctors, txt_name, txt_mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        PreferenceServices.init(this);
//        ZohoSalesIQ.showLauncher(true);

        userId = PreferenceServices.getInstance().getUser_id();
        showProgressDialog();
        new Requestor(UPDATEUSERDATA, this).getUserdata(userId);

        inta();

        if (savedInstanceState == null) {
            displayView();
        }

        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        Log.e("current", String.valueOf(currentTime));
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }


    private void inta() {
        iv_product_unselected = findViewById(R.id.iv_product_unselected);
        iv_chat_unselected = findViewById(R.id.iv_chat_unselected);
        iv_home_unselected = findViewById(R.id.iv_home_unselected);
        iv_search_unselected = findViewById(R.id.iv_search_unselected);
        llHome = findViewById(R.id.llHome);
        ll_myprescription = findViewById(R.id.ll_myprescription);
        ll_profile_appointment = findViewById(R.id.ll_profile_appointment);
        llSearch = findViewById(R.id.llSearch);
        ll_product = findViewById(R.id.ll_product);
        ll_chat = findViewById(R.id.ll_chat);
        ll_editprofile = findViewById(R.id.ll_editprofile);
        ll_logout = findViewById(R.id.ll_logout);
        ll_myjourney = findViewById(R.id.ll_myjourney);
        img_userprofile = findViewById(R.id.img_userprofile);
        img_userprofile.setBackground(getResources().getDrawable(R.drawable.img_background));

        txt_ourdoctors = findViewById(R.id.txt_ourdoctors);
        txt_name = findViewById(R.id.txt_name);
        txt_mobile = findViewById(R.id.txt_mobile);
        frameLayout_container = findViewById(R.id.frameLayout_container);
        llDrawerLayout = findViewById(R.id.llDrawerLayout);
        drawerLayout = findViewById(R.id.drawerLayout);
        iv_home = findViewById(R.id.iv_home);
        iv_search = findViewById(R.id.iv_search);
        iv_product = findViewById(R.id.iv_product);
        iv_chat = findViewById(R.id.iv_chat);
        txt_name.setText(PreferenceServices.getInstance().getUser_name());
        Log.d("","rrrrrrrrrrrrr 00 "+PreferenceServices.getInstance().getUser_name());

        if (!PreferenceServices.getInstance().getUser_mobile().isEmpty() && PreferenceServices.getInstance().getUser_mobile() != null) {
            txt_mobile.setText(PreferenceServices.getInstance().getUser_mobile());
            txt_mobile.setVisibility(View.VISIBLE);
        } else {
            txt_mobile.setVisibility(View.GONE);
        }

        llHome.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        ll_product.setOnClickListener(this);
        ll_editprofile.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        txt_ourdoctors.setOnClickListener(this);
        ll_profile_appointment.setOnClickListener(this);
        ll_myjourney.setOnClickListener(this);
        ll_myprescription.setOnClickListener(this);
    }

    private void displayView() {
        Fragment fragment = null;
        switch (0) {
            case 0:
                fragment = new HomeFragment();
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameLayout_container, fragment).commit();
        } else {
        }
    }

 /*   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llHome:

                Fragment fragmentHome = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
                if (!(fragmentHome instanceof HomeFragment)) {
                    Fragment fragment = new HomeFragment();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.llSearch:
                Fragment fragmentSearch = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
                if (!(fragmentSearch instanceof SearchFragment)) {
                    Fragment fragment = new SearchFragment();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.ll_product:

//                startActivity(new Intent(HomeActivity.this, ProductdetailsActivity.class));

                Fragment fragmentProduct = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
                if (!(fragmentProduct instanceof BlogFragment)) {
                    Fragment fragment = new BlogFragment();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.ll_chat:
                Fragment fragmentChat = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
                if (!(fragmentChat instanceof ChatFragment)) {
                    Fragment fragment = new ChatFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("onlyVI", "0");
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.ll_editprofile:
                startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                break;
            case R.id.ll_myjourney:
                startActivity(new Intent(HomeActivity.this, MyjourneyActivity.class));
                break;
            case R.id.txt_ourdoctors:
                startActivity(new Intent(HomeActivity.this, OurDoctorActivity.class));
                break;
            case R.id.ll_logout:
                final Dialog dialog = new Dialog(this, R.style.CustomDialog);
                dialog.setContentView(R.layout.logout_popup);
                dialog.setCancelable(true);
                TextView no = (TextView) dialog.findViewById(R.id.no);
                TextView yes = (TextView) dialog.findViewById(R.id.yes);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!PreferenceServices.getInstance().getUser_id().equalsIgnoreCase("")) {
                            PreferenceServices.getInstance().setUser_id("");
                            PreferenceServices.getInstance().clearPreferences();
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
            case R.id.ll_profile_appointment:
                startActivity(new Intent(this, AppointmentActivity.class));
                break;
        }
    }
*/

    @Override
    public void onClick(View v) {
        int item_id=v.getId();
        //switch (v.getId()) {
        if(item_id== R.id.llHome) {

            Fragment fragmentHome = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
            if (!(fragmentHome instanceof HomeFragment)) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
        else if(item_id== R.id.llSearch) {

            Fragment fragmentSearch = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
            if (!(fragmentSearch instanceof SearchDoctors)) {
                Fragment fragment = new SearchDoctors();
                FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
           /* Fragment fragmentSearch = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
            if (!(fragmentSearch instanceof ActiveFragment)) {
                Fragment fragment = new ActiveFragment();
                FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }*/
        }
        else if(item_id== R.id.ll_product) {

//                startActivity(new Intent(HomeActivity.this, ProductdetailsActivity.class));

            Fragment fragmentProduct = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
            if (!(fragmentProduct instanceof BlogFragment)) {
                Fragment fragment = new BlogFragment();
                FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
        else if(item_id== R.id.ll_chat) {
            Fragment fragmentChat = getSupportFragmentManager().findFragmentById(R.id.frameLayout_container);
            if (!(fragmentChat instanceof ChatFragment)) {
                Fragment fragment = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("onlyVI", "0");
                fragment.setArguments(bundle);
                FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
        else if(item_id== R.id.ll_editprofile) {
            startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
        }
  else if(item_id== R.id.ll_myprescription) {
            startActivity(new Intent(HomeActivity.this, PrecriptionActivity.class));
        }

        else if(item_id==  R.id.ll_myjourney) {
            startActivity(new Intent(HomeActivity.this, MyjourneyActivity.class));
        } else if(item_id== R.id.txt_ourdoctors) {
            startActivity(new Intent(HomeActivity.this, OurDoctorActivity.class));
        }
        else if(item_id== R.id.ll_logout) {
            final Dialog dialog = new Dialog(this, R.style.CustomDialog);
            dialog.setContentView(R.layout.logout_popup);
            dialog.setCancelable(true);
            TextView no = (TextView) dialog.findViewById(R.id.no);
            TextView yes = (TextView) dialog.findViewById(R.id.yes);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!PreferenceServices.getInstance().getUser_id().equalsIgnoreCase("")) {
                        PreferenceServices.getInstance().setUser_id("");
                        PreferenceServices.getInstance().clearPreferences();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    dialog.cancel();
                }
            });
            dialog.show();
        }
        else if(item_id== R.id.ll_profile_appointment){
            startActivity(new Intent(this, AppointmentActivity.class));
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.frameLayout_container) instanceof HomeFragment) {
            if (doubleBackToExitPressedOnce) {
                finish();
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                doubleBackToExitPressedOnce = true;
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code) {

            case UPDATEUSERDATA:
                UserDetailsResponse userDetailsResponse = (UserDetailsResponse) o;
                if (userDetailsResponse.getStatus() == 200) {
                    userDetailsDataList = userDetailsResponse.getUserDetailsData();
                    int i = 0;

                    Picasso.get().load(Constant.UrlPath.USER_IMAGE + userDetailsDataList.get(i).getProfile_image())
                            .fit().into(img_userprofile);
                    txt_name.setText(userDetailsDataList.get(i).getName());
                    PreferenceServices.getInstance().setUser_name(userDetailsDataList.get(i).getName());

                    txt_mobile.setVisibility(View.VISIBLE);
                    txt_mobile.setText(userDetailsDataList.get(i).getPhone());

                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}