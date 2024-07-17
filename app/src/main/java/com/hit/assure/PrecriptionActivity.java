package com.hit.assure;

import static com.hit.assure.Retrofit.ServerCode.COMPLETEDAPPOINTMENT;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hit.assure.Adapter.CompletedAppointmentAdapter;
import com.hit.assure.Model.CompletedAppointments.CompletedAppointmentResponse;
import com.hit.assure.Model.CompletedAppointments.CompletedAppointmentsData;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;

import java.util.List;
import java.util.Objects;

public class PrecriptionActivity extends AppCompatActivity implements ServerResponse {

    private RecyclerView rec_completed;
    private List<CompletedAppointmentsData> completedAppointmentsData;
    private String userId = "";
    ImageView img_back;
    private View ll_completed_no_booking;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precription);


        userId = PreferenceServices.getInstance().getUser_id();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        Log.e("hhhhhhhhhhhhhhh", userId);
        init();
        showProgressDialog();
        new Requestor(COMPLETEDAPPOINTMENT, this).getCompletedAppointment(PreferenceServices.getInstance().getUser_id());


    }

    public void init() {
        ll_completed_no_booking = findViewById(R.id.ll_completed_no_booking);
        img_back = findViewById(R.id.img_back);
        rec_completed = findViewById(R.id.rec_completed);
        LinearLayoutManager mLiner = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rec_completed.setLayoutManager(mLiner);
        rec_completed.setHasFixedSize(true);
        rec_completed.setNestedScrollingEnabled(false);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code) {
            case COMPLETEDAPPOINTMENT:
                CompletedAppointmentResponse completedAppointmentResponse = (CompletedAppointmentResponse) o;
                if (completedAppointmentResponse.getStatus() == 200) {
                    completedAppointmentsData = completedAppointmentResponse.getCompletedAppointmentsData();

                    Log.d("","completedAppointmentsData "+new Gson().toJson(completedAppointmentsData));
                    if (!completedAppointmentsData.isEmpty()) {
                        CompletedAppointmentAdapter completedAppointmentAdapter = new CompletedAppointmentAdapter(getApplicationContext(), completedAppointmentsData,"1");
                        rec_completed.setAdapter(completedAppointmentAdapter);
                    } else {
                        ll_completed_no_booking.setVisibility(View.VISIBLE);
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "" + completedAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    public void error(Object o, int code) {
        hideProgressDialog();
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