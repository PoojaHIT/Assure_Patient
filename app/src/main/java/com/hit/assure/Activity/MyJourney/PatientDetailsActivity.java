package com.hit.assure.Activity.MyJourney;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.PatientInfo.PatientInfoResponse;
import com.hit.assure.Model.PatientInfo.PatientInfoSessionlist;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.RetrofitBase;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDetailsActivity extends AppCompatActivity {

    public static APIServices apiServices;
    private ImageView img_back, img_userprofile;
    private RecyclerView recycler_sessionlist;
    private TextView txt_view_images;
    private TextView txt_patient_name, txt_patient_age, txt_treatname;
    private ProgressDialog progressDialog;
    private String patient_id = "", doctor_id = "";
    private String doctor_name = "", doctor_specialization = "", doctor_img = "";
    private String strTreatmenttype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        apiServices = RetrofitBase.getClient().create(APIServices.class);
        if (getIntent() != null) {
            patient_id = getIntent().getStringExtra("patient_id");
            doctor_id = getIntent().getStringExtra("doctor_id");
            doctor_name = getIntent().getStringExtra("doctor_name");
            doctor_specialization = getIntent().getStringExtra("doctor_specialization");
            doctor_img = getIntent().getStringExtra("doctor_img");
        }
        inita();
        callPatientinfoapi();
    }

    private void inita() {
        txt_patient_age = findViewById(R.id.txt_patient_age);
        txt_patient_name = findViewById(R.id.txt_patient_name);
        txt_treatname = findViewById(R.id.txt_treatname);
        txt_view_images = findViewById(R.id.txt_view_images);

        recycler_sessionlist = findViewById(R.id.recycler_sessionlist);
        recycler_sessionlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_sessionlist.setHasFixedSize(true);

        img_userprofile = findViewById(R.id.img_userprofile);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txt_view_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientDetailsActivity.this, MyProgressSessionImagesActivity.class)
                        .putExtra("patient_id", patient_id)
                        .putExtra("doctor_id", doctor_id)
                );
            }
        });
    }

    private void callPatientinfoapi() {
        showProgressDialog();
        Call<PatientInfoResponse> call = apiServices.getPatientinfo_createPrescription(doctor_id,
                patient_id
        );
        call.enqueue(new Callback<PatientInfoResponse>() {
            @Override
            public void onResponse(Call<PatientInfoResponse> call, Response<PatientInfoResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    PatientInfoResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        txt_patient_age.setText(doctor_specialization);
                        txt_patient_name.setText(doctor_name);
                        txt_treatname.setText(resource.getPatientInfoData().getTreatment());
                        strTreatmenttype = resource.getPatientInfoData().getTreatment();
                        Picasso.get().load(doctor_img).placeholder(R.drawable.user).into(img_userprofile);
                        if (resource.getPatientInfoSessionlistList() != null && !resource.getPatientInfoSessionlistList().isEmpty()) {
                            SessionlistAdapter sessionlistAdapter = new SessionlistAdapter(PatientDetailsActivity.this, resource.getPatientInfoSessionlistList());
                            recycler_sessionlist.setAdapter(sessionlistAdapter);
                        }
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientInfoResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    public class SessionlistAdapter extends RecyclerView.Adapter<SessionlistAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<PatientInfoSessionlist> patientInfoSessionlistList;

        public SessionlistAdapter(Context mContext, List<PatientInfoSessionlist> patientInfoSessionlistList) {
            this.mContext = mContext;
            this.patientInfoSessionlistList = patientInfoSessionlistList;
        }

        @NonNull
        @Override
        public SessionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_sessionlist, parent, false);
            SessionlistAdapter.ViewHolder holder = new SessionlistAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(SessionlistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_session_name.setText(patientInfoSessionlistList.get(position).getSession_list());
            holder.txt_date.setText(patientInfoSessionlistList.get(position).getDate());

            if (patientInfoSessionlistList.get(position).getPrescription() == 0) {
                holder.txt_add.setVisibility(View.VISIBLE);
                holder.txt_view.setVisibility(View.GONE);
            } else {
                holder.txt_add.setVisibility(View.GONE);
                holder.txt_view.setVisibility(View.VISIBLE);
            }

//            holder.txt_add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (strTreatmenttype.equalsIgnoreCase("Nutrition Treatment")) {
//                        startActivity(new Intent(mContext, AddDietPrescriptionActivity.class)
//                                .putExtra("booking_id", patientInfoSessionlistList.get(position).getBooking_id())
//                                .putExtra("patient_id", patient_id));
//
//                    } else {
//                        startActivity(new Intent(mContext, AddPrescriptionActivity.class)
//                                .putExtra("booking_id", patientInfoSessionlistList.get(position).getBooking_id())
//                                .putExtra("patient_id", patient_id));
//                    }
//                }
//            });

            holder.txt_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (strTreatmenttype.equalsIgnoreCase("Nutrition Treatment")) {
                    startActivity(new Intent(mContext, UploadSessionImageActivity.class)
                            .putExtra("booking_id", patientInfoSessionlistList.get(position).getBooking_id())
                            .putExtra("patient_id", patient_id)
                            .putExtra("doctor_specialization", doctor_specialization)
                    );
//                    } else {
//                        startActivity(new Intent(mContext, UploadSessionImageActivity.class)
//                                .putExtra("booking_id", patientInfoSessionlistList.get(position).getBooking_id())
//                                .putExtra("patient_id", patient_id)
//                        );
//                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return patientInfoSessionlistList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_view, txt_date, txt_session_name, txt_add;

            public ViewHolder(View itemView) {
                super(itemView);
                txt_session_name = itemView.findViewById(R.id.txt_session_name);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_view = itemView.findViewById(R.id.txt_view);
                txt_add = itemView.findViewById(R.id.txt_add);

            }
        }

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
    protected void attachBaseContext(Context newBase) {
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    @Override
    protected void onResume() {
        super.onResume();

        callPatientinfoapi();
    }
}