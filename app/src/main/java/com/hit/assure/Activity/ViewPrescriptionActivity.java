package com.hit.assure.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.hit.assure.Model.Note.NoteResponse;
import com.hit.assure.Model.OnlyResponse;
import com.hit.assure.Model.PatientBookingDetails.PatientBookingDetailsResponse;
import com.hit.assure.Model.Prescriptionlist.Prescriprionlistdata;
import com.hit.assure.Model.Prescriptionlist.PrescriptionlistResponse;
import com.hit.assure.Model.Sessionimage.SessionimageResponse;
import com.hit.assure.Model.UplodedPrescriptionResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.RetrofitClientInstance;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPrescriptionActivity extends AppCompatActivity {

    private TextView txt_booking_no, txt_clinic_name, txt_clinic_date, txt_time, txt_type;
    private EditText edtxt_doctor_note;
    private ProgressDialog progressDialog;
    private RecyclerView rec_prescription;
    private TextView txt_delete_prescription;
    private ImageView img_prescription, img_session1;
    private LinearLayout ll_session_images, ll_doctor_note;
    private TextView txt_view_all_images;
    private View view_1;
    private String booking_id = "";
    private String patient_id = "";
    private String doctor_id = "";
    private String strprescimg = "";
    private ImageView img_back, img_type;
    public static APIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        apiServices = RetrofitClientInstance.getRetrofitInstance().create(APIServices.class);
        if (getIntent() != null) {
            booking_id = getIntent().getStringExtra("booking_id");
           // patient_id = getIntent().getStringExtra("patient_id");
            doctor_id = getIntent().getStringExtra("doctor_id");
        }
        inita();
        callPatientbookingdetailsapi();
        callgetSessionimage();
        callGetNoteapi();
    }

    private void inita() {
        edtxt_doctor_note = findViewById(R.id.edtxt_doctor_note);
        ll_doctor_note = findViewById(R.id.ll_doctor_note);
        rec_prescription = findViewById(R.id.rec_prescription);
        LinearLayoutManager appointmenttypelayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_prescription.setLayoutManager(appointmenttypelayoutmanager);
        rec_prescription.setHasFixedSize(true);
        rec_prescription.setNestedScrollingEnabled(false);

        view_1 = findViewById(R.id.view_1);
        txt_type = findViewById(R.id.txt_type);
        txt_booking_no = findViewById(R.id.txt_booking_no);
        txt_clinic_name = findViewById(R.id.txt_clinic_name);
        txt_clinic_date = findViewById(R.id.txt_clinic_date);
        txt_time = findViewById(R.id.txt_time);
        img_type = findViewById(R.id.img_type);
        img_prescription = findViewById(R.id.img_prescription);
        txt_delete_prescription = findViewById(R.id.txt_delete_prescription);
        ll_session_images = findViewById(R.id.ll_session_images);
        txt_view_all_images = findViewById(R.id.txt_view_all_images);
        img_session1 = findViewById(R.id.img_session1);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewPrescriptionActivity.this, GalleryActivity.class)
                        .putExtra("img", strprescimg));
            }
        });

        txt_delete_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDeletePrescriptionapi();
            }
        });

        txt_view_all_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewPrescriptionActivity.this, MyProgressSessionImagesActivity1.class)
                        .putExtra("patient_id", patient_id)
                );
            }
        });
    }


    private void callPatientbookingdetailsapi() {
        showProgressDialog();
        Call<PatientBookingDetailsResponse> call = apiServices.getPatientbookingdetails(booking_id
        );
        call.enqueue(new Callback<PatientBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<PatientBookingDetailsResponse> call, Response<PatientBookingDetailsResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    PatientBookingDetailsResponse resource = response.body();

                    Log.d("","resource "+new Gson().toJson(resource));
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        txt_booking_no.setText(resource.getPatientBookingDetailsData().getBooking_id());
                        txt_clinic_name.setText(resource.getPatientBookingDetailsData().getClinic_name());
                        txt_clinic_date.setText(resource.getPatientBookingDetailsData().getDate());
                        txt_time.setText(resource.getPatientBookingDetailsData().getTime());
                        if (resource.getPatientBookingDetailsData().getType().equalsIgnoreCase("Video")) {
                            img_type.setImageResource(R.drawable.icon_video);
                            txt_type.setText("Virtual Consultion Appointment");
                        } else {
                            img_type.setImageResource(R.drawable.img_clinic);
                            txt_type.setText("In Clinic Appointment");
                        }

                        callPrescriptionlistapi();
                        callgetUploadedPrescapi();
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientBookingDetailsResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }


    private void callGetNoteapi() {
        showProgressDialog();
        Call<NoteResponse> call = apiServices.getNote(booking_id
        );
        call.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    NoteResponse noteResponse = response.body();
                    if (noteResponse.getResponseCode() == 200) {
                        ll_doctor_note.setVisibility(View.VISIBLE);
                        edtxt_doctor_note.setText(noteResponse.getNoteData().getNote());
                    } else {
                        ll_doctor_note.setVisibility(View.GONE);
                        Toast.makeText(ViewPrescriptionActivity.this, "" + noteResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    private void callPrescriptionlistapi() {
        showProgressDialog();
        Call<PrescriptionlistResponse> call = apiServices.getPrescriptionlist(booking_id
        );
        call.enqueue(new Callback<PrescriptionlistResponse>() {
            @Override
            public void onResponse(Call<PrescriptionlistResponse> call, Response<PrescriptionlistResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    PrescriptionlistResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        if (resource.getPrescriprionlistdataList() != null && !resource.getPrescriprionlistdataList().isEmpty()) {
                            hideProgressDialog();
                            PrescriptionlistAdapter prescriptionlistAdapter = new PrescriptionlistAdapter(ViewPrescriptionActivity.this, resource.getPrescriprionlistdataList());
                            rec_prescription.setAdapter(prescriptionlistAdapter);
                        } else {
                            hideProgressDialog();
                        }
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrescriptionlistResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    private void callgetUploadedPrescapi() {
        showProgressDialog();
        Call<UplodedPrescriptionResponse> call = apiServices.getUploadedPrescriptionIMg(booking_id
        );
        call.enqueue(new Callback<UplodedPrescriptionResponse>() {
            @Override
            public void onResponse(Call<UplodedPrescriptionResponse> call, Response<UplodedPrescriptionResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    UplodedPrescriptionResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        Picasso.get().load(resource.getData()).into(img_prescription);
                        img_prescription.setVisibility(View.VISIBLE);
                        strprescimg = resource.getData();
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<UplodedPrescriptionResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    private void callgetSessionimage() {
        showProgressDialog();
      //  Call<SessionimageResponse> call = apiServices.getSessionimage(PreferenceServices.getInstance().getdoctor_id(), patient_id
        Call<SessionimageResponse> call = apiServices.getSessionimage(doctor_id, patient_id
        );
        call.enqueue(new Callback<SessionimageResponse>() {
            @Override
            public void onResponse(Call<SessionimageResponse> call, Response<SessionimageResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    SessionimageResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        if (!resource.getSessionimageData().get(0).getSession_image().isEmpty()) {
                            Picasso.get().load(resource.getSessionimageData().get(0).getSession_image()).into(img_session1);
                            ll_session_images.setVisibility(View.VISIBLE);
                            view_1.setVisibility(View.VISIBLE);
                        }
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<SessionimageResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }


    private void callDeletePrescriptionapi() {
        showProgressDialog();
        Call<OnlyResponse> call = apiServices.getDeletePrescription(booking_id
        );
        call.enqueue(new Callback<OnlyResponse>() {
            @Override
            public void onResponse(Call<OnlyResponse> call, Response<OnlyResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    OnlyResponse resource = response.body();
                    if (resource.getResponseCode().equals("200")) {
                        hideProgressDialog();
                        Toast.makeText(ViewPrescriptionActivity.this, "" + resource.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        hideProgressDialog();
                        Toast.makeText(ViewPrescriptionActivity.this, "" + resource.getResponseMsg(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<OnlyResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    public class PrescriptionlistAdapter extends RecyclerView.Adapter<PrescriptionlistAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<Prescriprionlistdata> prescriprionlistdataList;

        public PrescriptionlistAdapter(Context mContext, List<Prescriprionlistdata> prescriprionlistdataList) {
            this.mContext = mContext;
            this.prescriprionlistdataList = prescriprionlistdataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_added_medicine, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_medicine_name.setText(prescriprionlistdataList.get(position).getMedicine_name());
            holder.txt_date.setText(prescriprionlistdataList.get(position).getDate());
            holder.txt_dosage.setText(prescriprionlistdataList.get(position).getDosage());
            holder.txt_taken.setText(prescriprionlistdataList.get(position).getTaken());
            holder.txt_medicine_note.setText(prescriprionlistdataList.get(position).getNote());
        }

        @Override
        public int getItemCount() {
            return prescriprionlistdataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_medicine_name, txt_date, txt_dosage, txt_taken, txt_medicine_note;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_medicine_name = itemView.findViewById(R.id.txt_medicine_name);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_dosage = itemView.findViewById(R.id.txt_dosage);
                txt_taken = itemView.findViewById(R.id.txt_taken);
                txt_medicine_note = itemView.findViewById(R.id.txt_medicine_note);

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
}