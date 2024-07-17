package com.hit.assure.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hit.assure.Model.Sessionimage.SessionimageData;
import com.hit.assure.Model.Sessionimage.SessionimageResponse;
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

public class MyProgressSessionImagesActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public static APIServices apiServices;
    private ImageView img_back;
    private String patient_id = "";
    private RecyclerView rec_session_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_progress_session_images);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        apiServices = RetrofitClientInstance.getRetrofitInstance().create(APIServices.class);
        if (getIntent() != null) {
            patient_id = getIntent().getStringExtra("patient_id");
        }
        inita();
        callgetSessionimage();
    }

    private void inita() {
        rec_session_img = findViewById(R.id.rec_session_img);
        rec_session_img.setLayoutManager(new GridLayoutManager(this, 3));
        rec_session_img.setItemAnimator(new DefaultItemAnimator());
        rec_session_img.setHasFixedSize(true);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void callgetSessionimage() {
        showProgressDialog();
      //  Call<SessionimageResponse> call = apiServices.getSessionimage(PreferenceServices.getInstance().getdoctor_id(), patient_id
        Call<SessionimageResponse> call = apiServices.getSessionimage("1", patient_id
        );
        call.enqueue(new Callback<SessionimageResponse>() {
            @Override
            public void onResponse(Call<SessionimageResponse> call, Response<SessionimageResponse> response) {
                if (response.body() != null) {
                    SessionimageResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        if (!resource.getSessionimageData().isEmpty()) {
                            hideProgressDialog();
                            SessionimageAdapter sessionimageAdapter = new SessionimageAdapter(MyProgressSessionImagesActivity.this, resource.getSessionimageData());
                            rec_session_img.setAdapter(sessionimageAdapter);
                        } else {
                            hideProgressDialog();
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


    public class SessionimageAdapter extends RecyclerView.Adapter<SessionimageAdapter.ViewHolder> {

        private Context mContext;
        private List<SessionimageData> sessionimageData;

        public SessionimageAdapter(Context mContext, List<SessionimageData> sessionimageData) {
            this.mContext = mContext;
            this.sessionimageData = sessionimageData;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_session_img, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.txt_session_name.setText(sessionimageData.get(position).getSession_list());
            holder.txt_date.setText(sessionimageData.get(position).getDate());
            if (!sessionimageData.get(position).getSession_image().isEmpty()) {
                Picasso.get().load(sessionimageData.get(position).getSession_image()).into(holder.img_session);
            } else {
                holder.img_session.setImageResource(R.drawable.img_no_session);
            }

        }

        @Override
        public int getItemCount() {
            return sessionimageData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_session_name, txt_date;
            ImageView img_session;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_date = itemView.findViewById(R.id.txt_date);
                txt_session_name = itemView.findViewById(R.id.txt_session_name);
                img_session = itemView.findViewById(R.id.img_session);
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