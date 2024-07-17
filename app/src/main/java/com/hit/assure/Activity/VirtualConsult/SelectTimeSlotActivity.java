package com.hit.assure.Activity.VirtualConsult;

import static com.hit.assure.Retrofit.ServerCode.CITYLIST;
import static com.hit.assure.Retrofit.ServerCode.TIMINGDATA;
import static com.hit.assure.Retrofit.ServerCode.VCSLOTDATA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.hit.assure.Adapter.MorningListingAdapter;
import com.hit.assure.Fragment.SelectedSlotFragment;
import com.hit.assure.Fragment.SlotsFragment;
import com.hit.assure.Model.CityList.CityData;
import com.hit.assure.Model.CityList.CityListData;
import com.hit.assure.Model.CityList.CityListResponse;
import com.hit.assure.Model.VcSlotDateData.TimingList;
import com.hit.assure.Model.VcSlotDateData.VcDataDate;
import com.hit.assure.Model.Vcsoltdate.VcSlotDateData;
import com.hit.assure.Model.Vcsoltdate.VcSlotdateResponse;
import com.hit.assure.Permission.PermissionResult;
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

public class SelectTimeSlotActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse , LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private ImageView img_back;

    private List<VcSlotDateData> vcSlotDateData;
    private String drid = "";
    private String clinicid = "";
    private String consultingType = "";

    private List<VcDataDate> vcDataDates;
    private List<TimingList> timingLists;
    private String drName;
    private String clinicName;
    private String clinicCity;
    private ImageView img_userprofile;
    private TextView txt_drname, txt_clinicName;
    private String drImage;
    private TextView txt_date_time;
    private String date, dr_id, consultation_type, clinic_id;
    private String day;
    private MorningListingAdapter morningListingAdapter;
    private RecyclerView rec_date_slot;
    private String firstDate;
    private String bothDate;
    private Spinner spinner_location;
    private CityListData cityListData;
    private List<CityData> cityData;
    private String location;
    private String lat = "";
    private String loong = "";
    private String datee= "";
    private String selected= "";

    private static int LOCATION_UPDATE_INTERVAL = 1500;
    private static int LOCATION_UPDATE_FASTEST_INTERVAL = 3000;
    private static int REQUEST_CODE_CHECK_SETTINGS = 8989;
    private GoogleApiClient mGoogleApiClient;
    private int KEY_PERMISSION = 0;
    private PermissionResult permissionResult;
    private String permissionsAsk[];

    private LocationRequest mLocationRequest;
    private Location mLocation;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_slot);

        PreferenceServices.init(this);

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
                            resolvable.startResolutionForResult(SelectTimeSlotActivity.this, REQUEST_CODE_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                        }
                    }
                });

        askCompactPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        mGoogleApiClient = new GoogleApiClient.Builder(SelectTimeSlotActivity.this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(SelectTimeSlotActivity.this)
                                .addOnConnectionFailedListener(SelectTimeSlotActivity.this)
                                .build();
                        mGoogleApiClient.connect();
                    }

                    @Override
                    public void permissionDenied() {
                    }
                });

        inita();
        if (getIntent() != null) {
            drName = getIntent().getStringExtra("drname");
            clinicName = getIntent().getStringExtra("clinicName");
            drImage = getIntent().getStringExtra("drImage");
            dr_id = getIntent().getStringExtra("dr_id");
            consultation_type = getIntent().getStringExtra("consultation_type");
            selected = getIntent().getStringExtra("selected");
            clinic_id = getIntent().getStringExtra("clinic_id");
            Log.e("pinkclinic", clinic_id);
            drid = dr_id;

           clinicid = clinic_id;
            Log.e("check11", dr_id);
            Log.e("clinic_id", clinic_id);
            Log.e("consulting", consultation_type);

            Picasso.get()
                    .load(drImage)
                    .fit()
                    .into(img_userprofile);
            txt_drname.setText(drName);
            txt_clinicName.setText(clinicName);


        }

//            txt_date_time.setText(firstDate);




        new Requestor(VCSLOTDATA, this).getVcSlotDate(dr_id, clinic_id, consultation_type);
        new Requestor(CITYLIST, this).getCity();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    private void askCompactPermissions(String[] strings, PermissionResult permissionResult) {
        KEY_PERMISSION = 200;
        permissionsAsk = strings;
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);

    }

    private void internalRequestPermission(String[] permissionAsk) {
        String arrayPermissionNotGranted[];
        ArrayList<String> permissionsNotGranted = new ArrayList<>();

        for (int i = 0; i < permissionAsk.length; i++) {
            if (!isPermissionGranted(SelectTimeSlotActivity.this, permissionAsk[i])) {
                permissionsNotGranted.add(permissionAsk[i]);
            }
        }

        if (permissionsNotGranted.isEmpty()) {
            if (permissionResult != null)
                permissionResult.permissionGranted();

        } else {
            arrayPermissionNotGranted = new String[permissionsNotGranted.size()];
            arrayPermissionNotGranted = permissionsNotGranted.toArray(arrayPermissionNotGranted);
            ActivityCompat.requestPermissions(SelectTimeSlotActivity.this, arrayPermissionNotGranted, KEY_PERMISSION);
        }
    }

    public boolean isPermissionGranted(Context context, String permission) {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    public void loadFragments(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_two, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void inita() {
        spinner_location = findViewById(R.id.spinner_location);

        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cityData.get(position).getCity_name().equalsIgnoreCase("North and Middle Andaman")) {
                    location = "Andaman";
                }

                else  {
                    location = cityData.get(position).getCity_name();
                }
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        img_userprofile = findViewById(R.id.img_userprofile);
      //  txt_date_time = findViewById(R.id.txt_date_time);

        txt_drname = findViewById(R.id.txt_drname);
        txt_clinicName = findViewById(R.id.txt_clinicName);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        rec_date_slot = findViewById(R.id.rec_date_slot);
        rec_date_slot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rec_date_slot.setHasFixedSize(true);
        rec_date_slot.setNestedScrollingEnabled(false);

    }

    @Override
    public void onClick(View v) {
        //  switch (v.getId()) {
        if(v.getId()== R.id.img_back){
            finish();
            // }
        }
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
                            SelectTimeSlotActivity.this.startLocationUpdates();
                        }
                        if (mLocation != null) {
                            double latitude = mLocation.getLatitude();
                            double longitude = mLocation.getLongitude();
                            getDisplayAddressFromLocation(latitude, longitude);
                        } else {
                            mGoogleApiClient = new GoogleApiClient.Builder(SelectTimeSlotActivity.this)
                                    .addApi(LocationServices.API)
                                    .addConnectionCallbacks(SelectTimeSlotActivity.this)
                                    .addOnConnectionFailedListener(SelectTimeSlotActivity.this)
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
        geocoder = new Geocoder(SelectTimeSlotActivity.this, Locale.getDefault());
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
            Log.e("latitude", String.valueOf(latitude));
            Log.e("longitude", String.valueOf(longitude));

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
    public void success(Object o, int code) {

        switch (code) {
            case VCSLOTDATA:
                VcSlotdateResponse vcSlotdateResponse = (VcSlotdateResponse) o;
                if (vcSlotdateResponse.getStatus() == 200) {
                    vcSlotDateData = vcSlotdateResponse.getVcSlotDateData();
                    VcSlotDateAdapter vcSlotDateAdapter = new VcSlotDateAdapter(this, vcSlotDateData);
                    rec_date_slot.setAdapter(vcSlotDateAdapter);
                }
                break;
            case CITYLIST:
                CityListResponse cityListResponse = (CityListResponse) o;
                Log.e("location","befororo");
                if (cityListResponse.getStatus() == 200){
                    cityListData = cityListResponse.getCityListData();
                    cityData = cityListData.getCityData();

                    if (!cityData.isEmpty() && cityData != null) {
                        Log.e("location","befororo");

                        List<String> locationl = new ArrayList<>();
                        for (int i = 0; i < cityData.size(); i++) {
                            locationl.add(cityData.get(i).getCity_name());
                            lat = cityData.get(i).getLatitude();
                            Log.e("lat", lat);
                            loong = cityData.get(i).getLongitude();
                            Log.e("long", loong);
                            Log.e("location",cityData.get(i).getCity_name() );
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationl);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_location.setAdapter(dataAdapter);
                    }

                }
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    public class VcSlotDateAdapter extends RecyclerView.Adapter<VcSlotDateAdapter.ViewHolder> implements ServerResponse {

        //vars
        private Context mContext;
        private List<VcSlotDateData> vcSlotDateData;
        private int j;
//        private String drid = "";
//        private String clinicid = "";
//        private String consultingType = "";
        private int lastSelectedPosition = -1;
        private int postionn;


        public VcSlotDateAdapter(Context mContext, List<VcSlotDateData> vcSlotDateData) {
            this.mContext = mContext;
            this.vcSlotDateData = vcSlotDateData;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


            if (vcSlotDateData.get(position).getCount() == 0){
                holder.txt_slot_count.setText("No slot available");
                holder.txt_slot_count.setTextColor(getResources().getColor(R.color.red));
            }else {
                String count = String.valueOf(vcSlotDateData.get(position).getCount());
                holder.txt_slot_count.setText(count + " slot available");
                holder.txt_slot_count.setTextColor(getResources().getColor(R.color.green));
            }


//            if (lastSelectedPosition == 0){
//                holder.radio.setChecked(lastSelectedPosition == position);
//            }else {
//                holder.radio.setChecked(false);
//            }
//            holder.radio.setChecked(position == lastSelectedPosition);
            if ((lastSelectedPosition == -1 && position == 0)){
                postionn = position;
                Log.e("postion", String.valueOf(postionn));
                holder.radio.setChecked(true);
                Fragment fragment = new SelectedSlotFragment();
                Bundle bundle = new Bundle();
                bundle.putString("drId", drid);
                bundle.putString("clinicId", clinicid);
                bundle.putString("consultingType", consultation_type);
                bundle.putString("clinic_name", clinicName);
                // bundle.putString("clinic_city", cl);
                bundle.putString("Date", vcSlotDateData.get(0).getDate_1());
                bundle.putString("Dateone", vcSlotDateData.get(0).getDate());
                //    Log.e("radiodate", date);
                fragment.setArguments(bundle);
                loadFragments(fragment);
            } else{
                if (lastSelectedPosition == position){
                    holder.radio.setChecked(true);
                }
                else{
                    holder.radio.setChecked(false);
                }
            }

//            if(holder.radio.isChecked())
//            {
//
//                 Fragment fragment = new SelectedSlotFragment();
//
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString("drId", drid);
//                                            bundle.putString("clinicId", clinicid);
//                                            bundle.putString("consultingType", consultingType);
//                                            bundle.putString("clinic_name", clinicName);
//                                            // bundle.putString("clinic_city", cl);
//                                            bundle.putString("Date", vcSlotDateData.get(0).getDate_1());
//                                            bundle.putString("Dateone", vcSlotDateData.get(0).getDate());
//                                        //    Log.e("radiodate", date);
//                                            fragment.setArguments(bundle);
//                                            loadFragments(fragment);
//            }

//            for (int i = 0; i< position; i++ ){
//                if (i == 0){
//                    holder.radio.setChecked(true);
//                }else{
//                    holder.radio.setChecked(lastSelectedPosition == position);
//                }
//
//
//            }



//            if(position == 0 && holder.radio.isChecked())
//            {
//                holder.radio.setChecked(true);
//                lastSelectedPosition = position;
//                lastSelectedPosition = 0;
//            }








            holder.radio.setText(vcSlotDateData.get(position).getDate());
            datee = vcSlotDateData.get(0).getDate();
//            if (position == 0){
//                txt_date_time.setText(datee);
//            }else {
//                txt_date_time.setText("");
//            }

          //  holder.radio.setChecked(position == 0);
            firstDate = vcSlotDateData.get(0).getDate_1();
       //     bothDate = String.valueOf(vcDataDates.get(1).getDay()+ " " + vcDataDates.get(1).getDate());
       //     txt_date_time.setText(bothDate);
//            if (firstDate != null){
//                Fragment fragment = new SelectedSlotFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("drId", drid);
//                bundle.putString("clinicId", clinicid);
//                bundle.putString("consultingType", consultingType);
//                bundle.putString("clinic_name", clinicName);
//                bundle.putString("Date", firstDate);
//                bundle.putString("Dateone", vcSlotDateData.get(0).getDate());
//                fragment.setArguments(bundle);
//                loadFragments(fragment);
//
//            }



//            holder.img_plus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    new Requestor(TIMINGDATA, VcSlotDateAdapter.this).getVcSlotDateData(drid,clinicid,consultingType,vcSlotDateData.get(position).getDate_1());
//                    Fragment fragment= new SlotsFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("drId", drid);
//                    bundle.putString("clinicId", clinicid);
//                    bundle.putString("consultingType", consultingType);
//                    bundle.putString("Date", vcSlotDateData.get(position).getDate_1());
//                    loadFragments(fragment);
//
//                }
//            });
        }

        // new Requestor(TIMINGDATA, SelectTimeSlotActivity.this).getVcSlotDateData(drid,clinicid,consultingType,vcSlotDateData.get(position).getDate_1());
        @Override
        public int getItemCount() {
            return vcSlotDateData.size();
        }

        @Override
        public void success(Object o, int code) {
            switch (code) {
                case TIMINGDATA:
//                        VcSlotdateDataResponse vcSlotdateDataResponse = (VcSlotdateDataResponse) o;
//                        if (vcSlotdateDataResponse.getStatus() == 200){
//                            vcDataDates = vcSlotdateDataResponse.getVcSlotDateData();
//
//
//
//                        }
                    break;

            }
        }

        @Override
        public void error(Object o, int code) {

        }


        public class ViewHolder extends RecyclerView.ViewHolder {


            RadioButton radio;
            TextView txt_slot_count;

            public ViewHolder(View itemView) {
                super(itemView);

                radio = itemView.findViewById(R.id.radio);
                txt_slot_count = itemView.findViewById(R.id.txt_slot_count);

                radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int copyOfLastCheckedPosition = lastSelectedPosition;
                        lastSelectedPosition = getAdapterPosition();
                        notifyItemChanged(copyOfLastCheckedPosition);
                        notifyItemChanged(lastSelectedPosition);
                        Log.e("lastselected", String.valueOf(lastSelectedPosition));
                       notifyDataSetChanged();
//                        strBrandname = String.valueOf(radio.getText());
                        Toast.makeText(mContext,
                                "selected " + radio.getText(),
                                Toast.LENGTH_SHORT).show();

                        if (radio.isChecked()) {

                            Fragment fragment = new SlotsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("drId", drid);
                            bundle.putString("clinicId", clinicid);
                            bundle.putString("consultingType", consultation_type);
                            bundle.putString("selected", selected);
                            bundle.putString("clinic_name", clinicName);
                            bundle.putString("Date", vcSlotDateData.get(lastSelectedPosition).getDate_1());
                            bundle.putString("Dateone", vcSlotDateData.get(lastSelectedPosition).getDate());
                            Log.e("radiodate", vcSlotDateData.get(lastSelectedPosition).getDate());
                            fragment.setArguments(bundle);
                            loadFragments(fragment);
                        }
                    }
                });

            }
        }

        public void loadFragments(Fragment fragment) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_two, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }

}