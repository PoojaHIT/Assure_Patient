package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.VISITNEARBY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.hit.assure.Adapter.VisitNearByClinicAdapter;
import com.hit.assure.Model.VisitNearByClinic.VisitNearByData;
import com.hit.assure.Model.VisitNearByClinic.VistNearByClinicResponse;
import com.hit.assure.Permission.ActivityManagePermission;
import com.hit.assure.Permission.PermissionResult;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VistNearByClinicActivity extends ActivityManagePermission implements View.OnClickListener, ServerResponse, LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final int LOCATION_UPDATE_INTERVAL = 1500;
    private static final int LOCATION_UPDATE_FASTEST_INTERVAL = 3000;
    private static final int REQUEST_CODE_CHECK_SETTINGS = 8989;
    private RecyclerView recyclerSearchProducts;
    private LinearLayout ll_nodata;
    private ImageView img_back;
    private List<VisitNearByData> visitNearByData;
    private String userId;
    private String lat = "19.207237";
    private String loong = "72.834824";
    private final String page = "1";
    private ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vist_near_by_clinic);
        PreferenceServices.init(this);
        userId = PreferenceServices.getInstance().getUser_id();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //location
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        LocationServices
                .getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) -> {
                })
                .addOnFailureListener(this, ex -> {
                    if (ex instanceof ResolvableApiException) {
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(VistNearByClinicActivity.this, REQUEST_CODE_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                        }
                    }
                });

        askCompactPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        mGoogleApiClient = new GoogleApiClient.Builder(VistNearByClinicActivity.this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(VistNearByClinicActivity.this)
                                .addOnConnectionFailedListener(VistNearByClinicActivity.this)
                                .build();
                        mGoogleApiClient.connect();
                    }

                    @Override
                    public void permissionDenied() {
                    }
                });

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

        ll_nodata = findViewById(R.id.ll_nodata);
        recyclerSearchProducts = findViewById(R.id.recyclerSearchProducts);
        recyclerSearchProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerSearchProducts.setHasFixedSize(true);
        recyclerSearchProducts.setNestedScrollingEnabled(false);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);


    }

    //location
    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> resultPendingResult = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        resultPendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (mLocation == null) {
                            VistNearByClinicActivity.this.startLocationUpdates();
                        }
                        if (mLocation != null) {
                            double latitude = mLocation.getLatitude();
                            double longitude = mLocation.getLongitude();
                            getDisplayAddressFromLocation(latitude, longitude);
                        } else {
                            mGoogleApiClient = new GoogleApiClient.Builder(VistNearByClinicActivity.this)
                                    .addApi(LocationServices.API)
                                    .addConnectionCallbacks(VistNearByClinicActivity.this)
                                    .addOnConnectionFailedListener(VistNearByClinicActivity.this)
                                    .build();
                            mGoogleApiClient.connect();
                        }
                        break;
                }
            }
        });
    }

    private void getDisplayAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(VistNearByClinicActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        } else {
            Address location = addresses.get(0);
            String country = location.getAddressLine(0);
            Log.e("checklocation", country);
            Log.e("yellow", String.valueOf(latitude));
            Log.e("Black", String.valueOf(longitude));
            lat = String.valueOf(latitude);
            loong = String.valueOf(longitude);
            showProgressDialog();
            new Requestor(VISITNEARBY, this).getNearByClini(userId, lat, loong, page);
            PreferenceServices.getInstance().setUser_location(country);
            PreferenceServices.getInstance().setUser_lat(String.valueOf(latitude));
            PreferenceServices.getInstance().setUser_long(String.valueOf(longitude));


            Log.e("rr", PreferenceServices.getInstance().getUser_lat());
            Log.e("longituderr", PreferenceServices.getInstance().getUser_long());
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onClick(View v) {
        // switch (v.getId()){
        if(v.getId()==  R.id.img_back){
            finish();

        }
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case VISITNEARBY:
                VistNearByClinicResponse vistNearByClinicResponse = (VistNearByClinicResponse) o;
                if (vistNearByClinicResponse.getStatus() == 200) {
                    visitNearByData = vistNearByClinicResponse.getVisitNearByData();
                    if (visitNearByData != null && !visitNearByData.isEmpty()) {
                        hideProgressDialog();
                        VisitNearByClinicAdapter visitNearByClinicAdapter = new VisitNearByClinicAdapter(this, visitNearByData);
                        recyclerSearchProducts.setAdapter(visitNearByClinicAdapter);
                        ll_nodata.setVisibility(View.GONE);
                    } else {
                        hideProgressDialog();
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                        ll_nodata.setVisibility(View.VISIBLE);
                    }


                } else {
                    hideProgressDialog();
                    ll_nodata.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "" + vistNearByClinicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
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