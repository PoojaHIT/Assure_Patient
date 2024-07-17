package com.hit.assure.Activity.MyJourney;

import static com.hit.assure.Retrofit.ServerCode.CURRENT_SESSION_DOCTORLIST;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.CurrentSessionDoctorlist.CurrentSessionDoctorlistData;
import com.hit.assure.Model.CurrentSessionDoctorlist.CurrentSessionDoctorlistResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class DoctorlistActivity extends AppCompatActivity implements ServerResponse {

    private ProgressDialog progressDialog;
    private RecyclerView recycler_doctorlist;
    private ImageView img_back;
    private LinearLayout ll_nodata;
    private String cat_id = "", cat_name = "";
    private List<CurrentSessionDoctorlistData> currentSessionDoctorlistData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlist);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        if (getIntent() != null) {
            cat_id = getIntent().getStringExtra("cat_id");
            cat_name = getIntent().getStringExtra("cat_name");
        }
        inita();
        showProgressDialog();
        new Requestor(CURRENT_SESSION_DOCTORLIST, DoctorlistActivity.this).getDoctorlist(PreferenceServices.getInstance().getUser_id(),
                cat_id);
    }

    private void inita() {

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_nodata = findViewById(R.id.ll_nodata);

        recycler_doctorlist = findViewById(R.id.recycler_doctorlist);
        LinearLayoutManager rxpatientlistlayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_doctorlist.setLayoutManager(rxpatientlistlayout);
        recycler_doctorlist.setHasFixedSize(true);
        recycler_doctorlist.setNestedScrollingEnabled(false);

    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case CURRENT_SESSION_DOCTORLIST:
                CurrentSessionDoctorlistResponse response = (CurrentSessionDoctorlistResponse) o;
                if (response.getResponseCode() == 200) {
                    currentSessionDoctorlistData = response.getCurrentSessionDoctorlistData();
                    if (currentSessionDoctorlistData != null && !currentSessionDoctorlistData.isEmpty()) {
                        hideProgressDialog();
                        ll_nodata.setVisibility(View.GONE);
                        recycler_doctorlist.setVisibility(View.VISIBLE);
                        DoctorlistAdapter doctorlistAdapter = new DoctorlistAdapter(this, currentSessionDoctorlistData);
                        recycler_doctorlist.setAdapter(doctorlistAdapter);
                    } else {
                        ll_nodata.setVisibility(View.VISIBLE);
                        recycler_doctorlist.setVisibility(View.GONE);
                        hideProgressDialog();
                    }
                } else {
                    ll_nodata.setVisibility(View.VISIBLE);
                    recycler_doctorlist.setVisibility(View.GONE);
                    hideProgressDialog();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }


    public class DoctorlistAdapter extends RecyclerView.Adapter<DoctorlistAdapter.ViewHolder> {

        private Context mContext;
        private List<CurrentSessionDoctorlistData> currentSessionDoctorlistData;

        public DoctorlistAdapter(Context mContext, List<CurrentSessionDoctorlistData> currentSessionDoctorlistData) {
            this.mContext = mContext;
            this.currentSessionDoctorlistData = currentSessionDoctorlistData;
        }

        @NonNull
        @Override
        public DoctorlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_current_session_doctorlist, parent, false);
            DoctorlistAdapter.ViewHolder holder = new DoctorlistAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(DoctorlistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.txt_doctor_name.setText(currentSessionDoctorlistData.get(position).getDoctor_name());
            holder.txt_specialization.setText(cat_name);
            if (!currentSessionDoctorlistData.get(position).getUser_image().isEmpty()) {
                Picasso.get().load(currentSessionDoctorlistData.get(position).getUser_image()).into(holder.img_doctor);
            } else {
                holder.img_doctor.setImageResource(R.drawable.user);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, PatientDetailsActivity.class)
                            .putExtra("doctor_name", currentSessionDoctorlistData.get(position).getDoctor_name())
                            .putExtra("doctor_specialization", cat_name)
                            .putExtra("doctor_img", currentSessionDoctorlistData.get(position).getUser_image())
                            .putExtra("patient_id", PreferenceServices.getInstance().getUser_id())
                            .putExtra("doctor_id", currentSessionDoctorlistData.get(position).getDoctor_id())
                    );
                }
            });

        }

        @Override
        public int getItemCount() {
            return currentSessionDoctorlistData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_doctor_name, txt_specialization;
            ImageView img_doctor;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_specialization = itemView.findViewById(R.id.txt_specialization);
                txt_doctor_name = itemView.findViewById(R.id.txt_doctor_name);
                img_doctor = itemView.findViewById(R.id.img_doctor);
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