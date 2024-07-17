package com.hit.assure.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.PrecriptionDetailsPage;
import com.hit.assure.Activity.ViewPrescriptionActivity;
import com.hit.assure.Model.CompletedAppointments.CompletedAppointmentsData;
import com.hit.assure.PrecriptionActivity;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompletedAppointmentAdapter extends RecyclerView.Adapter<CompletedAppointmentAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<CompletedAppointmentsData> completedAppointmentsData;

    String flag;

    public CompletedAppointmentAdapter(Context mContext, List<CompletedAppointmentsData> completedAppointmentsData, String flag) {
        this.mContext = mContext;
        this.completedAppointmentsData = completedAppointmentsData;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activesec_appointment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.txt_time.setText(activeAppointmentsData.get(position).getFrom_time());
       // holder.txt_type.setText(completedAppointmentsData.get(position).getConsultation_type());
        holder.txt_dr_name.setText(completedAppointmentsData.get(position).getDoctor_name());
        holder.txt_app_date.setText(completedAppointmentsData.get(position).getBooking_date());
        holder.txt_app_time.setText(completedAppointmentsData.get(position).getFrom_time());
//        if (completedAppointmentsData.get(position).getStatus().equalsIgnoreCase("txt_app_status")){
//            holder.ll_status.setVisibility(View.GONE);
//            holder.txt_app_status.setVisibility(View.VISIBLE);
//            holder.txt_app_status.setText(completedAppointmentsData.get(position).getStatus());
//
//
//        }
        if (completedAppointmentsData.get(position).getStatus().equalsIgnoreCase("Completed")){
            holder.ll_status.setVisibility(View.VISIBLE);
            holder.txt_app_status.setVisibility(View.VISIBLE);
            holder.txt_app_status.setText(completedAppointmentsData.get(position).getStatus());
        }

        if (completedAppointmentsData.get(position).getStatus().equalsIgnoreCase("Cancelled")){
            holder.ll_status.setVisibility(View.VISIBLE);
            holder.txt_app_status.setVisibility(View.VISIBLE);
            holder.txt_app_status.setTextColor(Color.RED);
            holder.txt_app_status.setText(completedAppointmentsData.get(position).getStatus());
        }

        if (completedAppointmentsData.get(position).getConsultation_type().equalsIgnoreCase("video")){
            holder.txt_type.setText("Video Consultation");
            Log.e("woowo", "wowowo");
            Picasso.get()
                    .load(R.drawable.img_vid_consultingg)
                    .fit()
                    .into(holder.img_userprofile);
        }
        if (completedAppointmentsData.get(position).getConsultation_type().equalsIgnoreCase("visit")){
            Log.e("checkwowo", "wowowo");
            holder.txt_type.setText("In-Clinic Appointment");
            Picasso.get()
                    .load(R.drawable.img_clinic)
                    .fit()
                    .into(holder.img_userprofile);
        }

//        if (completedAppointmentsData.get(position).getStatus().equalsIgnoreCase("No Show")){
//
//            holder.ll_status.setVisibility(View.GONE);
//            holder.txt_app_status.setVisibility(View.VISIBLE);
//            holder.txt_app_status.setTextColor(Color.GRAY);
//            holder.txt_app_status.setText(completedAppointmentsData.get(position).getStatus());
//
//        }

        holder.ll_item_precri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("1"))
                {
                    Intent i=new Intent(mContext, ViewPrescriptionActivity.class);
                    i.putExtra("booking_id",completedAppointmentsData.get(position).getBooking_id());
                    i.putExtra("doctor_id",completedAppointmentsData.get(position).getDoctor_id());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return completedAppointmentsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_time,txt_type, txt_dr_name,txt_app_date, txt_app_time, txt_app_status;
        LinearLayout ll_status,ll_item_precri;
        ImageView img_userprofile;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_time = itemView.findViewById(R.id.txt_time);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_dr_name = itemView.findViewById(R.id.txt_dr_name);
            txt_app_date = itemView.findViewById(R.id.txt_app_date);
            txt_app_time = itemView.findViewById(R.id.txt_app_time);
            ll_status = itemView.findViewById(R.id.ll_status);
            txt_app_status = itemView.findViewById(R.id.txt_app_status);
            img_userprofile = itemView.findViewById(R.id.img_userprofile);
            ll_item_precri = itemView.findViewById(R.id.ll_item_precri);

        }
    }


}
