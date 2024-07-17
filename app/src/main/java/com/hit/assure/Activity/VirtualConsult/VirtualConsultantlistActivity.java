package com.hit.assure.Activity.VirtualConsult;

import static com.hit.assure.Retrofit.ServerCode.CITYLIST;
import static com.hit.assure.Retrofit.ServerCode.DOCTORPROFILE;
import static com.hit.assure.Retrofit.ServerCode.DOCTORSEARCH;
import static com.hit.assure.Retrofit.ServerCode.VIRTUALCOSULTANT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.SearchActivity;
import com.hit.assure.Adapter.DoctorListingAdapter;
import com.hit.assure.Adapter.SearchDoctorListingAdapter;

import com.hit.assure.Model.CityList.CityData;
import com.hit.assure.Model.CityList.CityListData;
import com.hit.assure.Model.CityList.CityListResponse;
import com.hit.assure.Model.DoctorSearch.DoctorListData;
import com.hit.assure.Model.DoctorSearch.DoctorSearchResponse;
import com.hit.assure.Model.VirtualConsultant.DoctorPracticesData;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantData;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantResponse;
import com.hit.assure.Permission.PermissionResult;
import com.hit.assure.Permission.ActivityManagePermission;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class VirtualConsultantlistActivity extends ActivityManagePermission implements View.OnClickListener, ServerResponse, LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private ImageView img_back;
    private RecyclerView recycler_virtual_consultant, rec_search_doctor;
    private List<VirtualConsultantData> virtualConsultantDataList;
    private List<DoctorPracticesData> doctorPracticesDataList;
    private List<DoctorListData> doctorListData;
    private String lat = "";
    private String loong = "";
    private String cat = "";
    private String page = "1";
    int i;
    private EditText edt_search;
    private ImageView img_search;
    private String userId = "";
    private LinearLayout ll_city;
    private ProgressDialog progressDialog;
    private Spinner spinner_location;
    private CityListData cityListData;
    private List<CityData> cityData;
    private String location;
    private String latt = "";
    private String loongg = "";
    private RelativeLayout rl_search;

    private static int LOCATION_UPDATE_INTERVAL = 1500;
    private static int LOCATION_UPDATE_FASTEST_INTERVAL = 3000;
    private static int REQUEST_CODE_CHECK_SETTINGS = 8989;
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private Location mLocation;

    private int KEY_PERMISSION = 0;
    private PermissionResult permissionResult;
    private String permissionsAsk[];
    private String cityName = "";
    private String onlyVI = "";
    private String selected = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_consultantlist);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        userId = PreferenceServices.getInstance().getUser_id();
        Log.e("userId", userId);

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
                            resolvable.startResolutionForResult(VirtualConsultantlistActivity.this, REQUEST_CODE_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException sendEx) {
                        }
                    }
                });

        askCompactPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        mGoogleApiClient = new GoogleApiClient.Builder(VirtualConsultantlistActivity.this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(VirtualConsultantlistActivity.this)
                                .addOnConnectionFailedListener(VirtualConsultantlistActivity.this)
                                .build();
                        mGoogleApiClient.connect();
                    }

                    @Override
                    public void permissionDenied() {
                    }
                });


        inita();

        if (getIntent() != null) {
            cat = getIntent().getStringExtra("cat");
            onlyVI = getIntent().getStringExtra("onlyVI");
            selected = getIntent().getStringExtra("selected");
        }

        progressDialog.show();
        progressDialog.setContentView(R.layout.item_loader);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        new Requestor(CITYLIST, this).getCity();

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
        spinner_location = findViewById(R.id.spinner_location);


        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int last = -1;
                last = position;

                cityName = cityData.get(last).getCity_name();
                lat = cityData.get(last).getLatitude();
                loong = cityData.get(last).getLongitude();
                new Requestor(VIRTUALCOSULTANT, VirtualConsultantlistActivity.this).getDoctorList(userId, lat, loong, cat, page);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ll_city = findViewById(R.id.ll_city);
        rl_search = findViewById(R.id.rl_search);
        img_back.setOnClickListener(this);
        recycler_virtual_consultant = findViewById(R.id.recycler_virtual_consultant);
        recycler_virtual_consultant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_virtual_consultant.setHasFixedSize(true);
        recycler_virtual_consultant.setNestedScrollingEnabled(false);
//        edt_search = findViewById(R.id.edt_search);
        //       img_search = findViewById(R.id.img_search);
//        img_search.setOnClickListener(this);``
        rl_search.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        rec_search_doctor = findViewById(R.id.rec_search_doctor);
        rec_search_doctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_search_doctor.setHasFixedSize(true);
        rec_search_doctor.setNestedScrollingEnabled(false);


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
                            VirtualConsultantlistActivity.this.startLocationUpdates();
                        }
                        if (mLocation != null) {
                            double latitude = mLocation.getLatitude();
                            double longitude = mLocation.getLongitude();
                            getDisplayAddressFromLocation(latitude, longitude);
                        } else {
                            mGoogleApiClient = new GoogleApiClient.Builder(VirtualConsultantlistActivity.this)
                                    .addApi(LocationServices.API)
                                    .addConnectionCallbacks(VirtualConsultantlistActivity.this)
                                    .addOnConnectionFailedListener(VirtualConsultantlistActivity.this)
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
        geocoder = new Geocoder(VirtualConsultantlistActivity.this, Locale.getDefault());
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
            new Requestor(VIRTUALCOSULTANT, this).getDoctorList(userId, lat, loong, cat, page);
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
        int item_id=v.getId();
        // switch (v.getId()) {
        if(item_id== R.id.img_back) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else if(item_id== R.id.rl_search) {
            startActivity(new Intent(this, SearchActivity.class)
                    .putExtra("lat", lat)
                    .putExtra("loong", loong));
        }
        else if(item_id== R.id.ll_city) {
        }

        // }
    }


    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
        switch (code) {
            case CITYLIST:
                CityListResponse cityListResponse = (CityListResponse) o;
                Log.e("location", "befororo");
                if (cityListResponse.getStatus() == 200) {
                    cityListData = cityListResponse.getCityListData();
                    cityData = cityListData.getCityData();

                    if (!cityData.isEmpty() && cityData != null) {
                        Log.e("location", "befororo");

                        List<String> locationl = new ArrayList<>();
                        for (int i = 0; i < cityData.size(); i++) {
                            locationl.add(cityData.get(i).getCity_name());
                            //           Log.e("location",cityData.get(i).getCity_name() );
                            lat = cityData.get(i).getLatitude();
                            //           Log.e("lattt", lat);
                            loong = cityData.get(i).getLongitude();
                            //           Log.e("longgg", loong);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationl);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_location.setAdapter(dataAdapter);
                        recycler_virtual_consultant.setVisibility(View.GONE);
//
                        //         Log.e("lat", latt);
                        //          Log.e("loong", loongg);

                    }

                }
                break;
            case VIRTUALCOSULTANT:
                VirtualConsultantResponse virtualConsultantResponse = (VirtualConsultantResponse) o;
                if (virtualConsultantResponse.getStatus() == 200) {
                    virtualConsultantDataList = virtualConsultantResponse.getVirtualConsultantDataList();
                    if (!virtualConsultantDataList.isEmpty()) {
                        DoctorListingAdapter doctorListingAdapter = new DoctorListingAdapter(this, virtualConsultantDataList);
                        recycler_virtual_consultant.setVisibility(View.VISIBLE);
                        recycler_virtual_consultant.setAdapter(doctorListingAdapter);
                    } else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "" + virtualConsultantResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;


        }

    }

    @Override
    public void error(Object o, int code) {

    }

    public class DoctorListingAdapter extends RecyclerView.Adapter<DoctorListingAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<VirtualConsultantData> virtualConsultantDataList;
        private String drId = "";
        private String clinic_name;
        private String clinic_id;
        private String clinic_city;
        private String distance;
//    private List<DoctorPracticesData> doctorPracticesDataList;

        public DoctorListingAdapter(Context mContext, List<VirtualConsultantData> virtualConsultantDataList) {
            this.mContext = mContext;
            this.virtualConsultantDataList = virtualConsultantDataList;
//        this.doctorPracticesDataList = doctorPracticesDataList;
        }

        @NonNull
        @Override
        public DoctorListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_virtual_consultant, parent, false);
            DoctorListingAdapter.ViewHolder holder = new DoctorListingAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(DoctorListingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            drId = virtualConsultantDataList.get(position).getDoctor_user_id();
            Picasso.get()
                    .load(virtualConsultantDataList.get(position).getProfile_pic())
                    .fit()
                    .into(holder.img_userprofile);
            holder.txt_name.setText(virtualConsultantDataList.get(position).getDoctor_name());
            Log.e("name", virtualConsultantDataList.get(position).getDoctor_name());
            holder.txt_exp.setText(virtualConsultantDataList.get(position).getExperience() + " years experience overall");
            holder.txt_like.setText(virtualConsultantDataList.get(position).getReview());
            holder.txt_patients.setText(virtualConsultantDataList.get(position).getRating());
            //   holder.txt_fee_consultation.setText(" ~ ₹ " + virtualConsultantDataList.get(position).getVideo_charge() + " " + "Consultation Fees");
            distance = virtualConsultantDataList.get(position).getDistance();

//            if (onlyVI.equalsIgnoreCase("0")){

//                if (Integer.parseInt(distance) >= 50){
//
//                    holder.txt_bookappointment.setVisibility(View.VISIBLE);
//                    //holder.txt_bookappointment.setVisibility(View.GONE);
//                    // holder.txt_videoconsult.setVisibility(View.GONE);
//
//
//                }else {
//                    holder.txt_bookappointment.setVisibility(View.GONE);
//                    holder.txt_bookappointment.setVisibility(View.VISIBLE);
//                    // holder.txt_videoconsult.setVisibility(View.VISIBLE);
//                }



//            }else {
//                holder.txt_bookappointment.setVisibility(View.GONE);
//            }




//        if (distance.matches("\\s+")){
//            String removespace = distance.replace(" ","");
//            Log.e("checkremovespace", removespace);
//        }
//
//        if (distance.matches("\\s+")){
//            String remove = distance.replace(" ", "");
//            Log.e("remove", remove);
//        }
//
//        String fullname = distance;
//                       String[] parts = fullname.split("\\s+");
//                        String firstname = parts[0];
//                        Log.e("partone", firstname);
//                        String round = String.valueOf(Math.round(Float.parseFloat(firstname)));
//                        Log.e("checkloosf",round);
//                        String lastname = parts[1];
//     //   String.format("%. 2f", lastname);
//
//       if(round.matches(".*\\s.*")){
//         round =  firstname.replace(" ", "");
//         Log.e("spaceromove", round);
//       }
//
//        Log.e("parttwo", lastname);
//        Log.e("Yellowround",String.valueOf(round));
//        if (Integer.parseInt(round) >= 50&& Integer.parseInt(round) >= 100){
//
//            holder.txt_bookappointment.setVisibility(View.VISIBLE);
//
//        }else {
//            holder.txt_bookappointment.setVisibility(View.GONE);
//        }



            List<DoctorPracticesData> doctorPracticesDataList;
            doctorPracticesDataList = virtualConsultantDataList.get(position).getDoctorPracticesData();

            for (int i = 0; i < doctorPracticesDataList.size(); i++) {
                Log.e("checkdata", doctorPracticesDataList.get(i).getClinic_city());
                clinic_id = doctorPracticesDataList.get(i).getClinic_id();
                clinic_name = doctorPracticesDataList.get(i).getClinic_name();
                clinic_city = doctorPracticesDataList.get(i).getClinic_city();
                holder.txt_location.setText(doctorPracticesDataList.get(i).getClinic_city());
                holder.txt_clinic_name.setText(doctorPracticesDataList.get(i).getClinic_name());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ConsultantDetailsActivity.class).putExtra("drid", virtualConsultantDataList.get(position).getDoctor_user_id())
                            .putExtra("clinic_id", clinic_id).putExtra("clinic_name", clinic_name).putExtra("consultation_type", "online"));
                }
            });
            holder.txt_videoconsult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                for (int i = 0; i < doctorPracticesDataList.size(); i++) {
//
//                    List<DoctorPracticesData> doctorPracticesData;
//                    Log.e("checkdata", doctorPracticesDataList.get(i).getClinic_city());
//                    doctorPracticesData = virtualConsultantDataList.get(i).getDoctorPracticesData();
//                    for (int j = 0; j < doctorPracticesData.size(); j++) {
//                        clinic_name = doctorPracticesDataList.get(i).getClinic_name();
//                        mContext.startActivity(new Intent(mContext, SelectTimeSlotActivity.class)
//                                .putExtra("drname", virtualConsultantDataList.get(position).getDoctor_name())
//                                .putExtra("dr_id", virtualConsultantDataList.get(position).getDoctor_user_id())
//                                .putExtra("consultation_type", "online")
//                                .putExtra("clinic_id", virtualConsultantDataList.get(position).getDoctorPracticesData().get(j).getClinic_id())
//                                .putExtra("clinicname", clinic_name)
//                                .putExtra("drImage", virtualConsultantDataList.get(position).getProfile_pic()));
//                    }
//
//                }
                    mContext.startActivity(new Intent(mContext, ConsultantDetailsActivity.class)
                            .putExtra("drid", virtualConsultantDataList.get(position).getDoctor_user_id())
                            .putExtra("clinic_id", clinic_id)
                            .putExtra("clinic_name", clinic_name)
                            .putExtra("selected", selected)
                            .putExtra("consultation_type", "online"));
                }
            });
            holder.txt_bookappointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mContext.startActivity(new Intent(mContext, ConsultantDetailsActivity.class)
                            .putExtra("drname", virtualConsultantDataList.get(position).getDoctor_name())
                            .putExtra("clinicname", clinic_name)
                            .putExtra("drImage", virtualConsultantDataList.get(position).getProfile_pic())
                            .putExtra("consultation_type", "visit")
                            .putExtra("clinic_id", clinic_id)
                            .putExtra("selected", selected)
                            .putExtra("drid", virtualConsultantDataList.get(position).getDoctor_user_id()));


                }
            });


        }

        @Override
        public int getItemCount() {
            return virtualConsultantDataList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {


            CircleImageView img_userprofile;
            TextView txt_name, txt_dr_type, txt_exp, txt_like, txt_patients, txt_location, txt_fee_consultation, txt_clinic_name, txt_videoconsult, txt_bookappointment;
            LinearLayout ll_button;
            public ViewHolder(View itemView) {
                super(itemView);

                img_userprofile = itemView.findViewById(R.id.img_userprofile);
                // txt_fee_consultation = itemView.findViewById(R.id.txt_fee_consultation);
                txt_dr_type = itemView.findViewById(R.id.txt_dr_type);
                txt_exp = itemView.findViewById(R.id.txt_exp);
                txt_like = itemView.findViewById(R.id.txt_like);
                txt_patients = itemView.findViewById(R.id.txt_patients);
                txt_location = itemView.findViewById(R.id.txt_location);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_clinic_name = itemView.findViewById(R.id.txt_clinic_name);
                txt_bookappointment = itemView.findViewById(R.id.txt_bookappointment);
                txt_videoconsult = itemView.findViewById(R.id.txt_videoconsult);
                ll_button = itemView.findViewById(R.id.ll_button);

            }
        }


    }

}