package com.hit.assure.Adapter;


import static com.hit.assure.Retrofit.ServerCode.ACTIVEAPPOINTMENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.CancelAppointActivity;
import com.hit.assure.Activity.VideoCallingActivity;
import com.hit.assure.Model.ActiveAppointments.ActiveAppointmentsData;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

//public class ActiveAppointmentAdapter extends RecyclerView.Adapter<ActiveAppointmentAdapter.ViewHolder> implements ServerResponse {
//
//    //vars
//    private Context mContext;
//    private List<ActiveAppointmentsData> activeAppointmentsData;
//    private String drId;
//    private String bookingId;
//    private String bookingFromTime;
//    private String clinic_id;
//    private String destinationLatitude = "";
//    private String destinationLongitude = "";
////    private String destinationLatitude ="19.131861";
////    private String destinationLongitude ="72.835361";
//
//
//    public ActiveAppointmentAdapter(Context mContext, List<ActiveAppointmentsData> activeAppointmentsData) {
//        this.mContext = mContext;
//        this.activeAppointmentsData = activeAppointmentsData;
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_active_appointment, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
////        holder.txt_time.setText(activeAppointmentsData.get(position).getFrom_time());
//        drId = activeAppointmentsData.get(position).getDoctor_id();
//        bookingId = activeAppointmentsData.get(position).getBooking_id();
//        bookingFromTime = activeAppointmentsData.get(position).getDisplay_from_time();
//        destinationLatitude = activeAppointmentsData.get(position).getClinic_lat();
//        destinationLongitude = activeAppointmentsData.get(position).getClinic_lng();
//        holder.txt_dr_name.setText(activeAppointmentsData.get(position).getDoctor_name());
//        holder.txt_app_date.setText(activeAppointmentsData.get(position).getBooking_date());
//        holder.txt_app_time.setText(activeAppointmentsData.get(position).getFrom_time());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, CancelAppointActivity.class).putExtra("drId", activeAppointmentsData.get(position).getDoctor_id())
//                        .putExtra("bookingId", activeAppointmentsData.get(position).getBooking_id())
//                        .putExtra("bookingFromTime", activeAppointmentsData.get(position).getDisplay_from_time())
//                        .putExtra("clinic_id", activeAppointmentsData.get(position).getClinic_id()).putExtra("date", activeAppointmentsData.get(position).getBooking_date()).putExtra("consultingType", activeAppointmentsData.get(position).getConsultation_type()));
//            }
//        });
//        if (activeAppointmentsData.get(position).getConsultation_type().equalsIgnoreCase("video")) {
//            holder.txt_type.setText("Video Consultation");
//            Picasso.get()
//                    .load(R.drawable.img_vid_consultingg)
//                    .fit()
//                    .into(holder.img_userprofile);
//        }
//
//        if (activeAppointmentsData.get(position).getConsultation_type().equalsIgnoreCase("visit")) {
//            holder.txt_type.setText("In-Clinic Booking");
//            Picasso.get()
//                    .load(R.drawable.img_clinic)
//                    .fit()
//                    .into(holder.img_userprofile);
//
//            holder.txt_vc.setVisibility(View.GONE);
//            holder.txt_direction.setVisibility(View.VISIBLE);
//        }
//
//        holder.txt_vc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!activeAppointmentsData.get(position).getRoom_url().isEmpty()) {
//
//                    new Requestor(ACTIVEAPPOINTMENT, ActiveAppointmentAdapter.this).getUserendmeeting(activeAppointmentsData.get(position).getUser_id(),
//                            activeAppointmentsData.get(position).getBooking_id());
//
//                    mContext.startActivity(new Intent(mContext, VideoCallingActivity.class).putExtra("roomUrl", activeAppointmentsData.get(position).getRoom_url()));
//                } else {
//                    Toast.makeText(mContext, "Room url not generated", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        holder.txt_direction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String uri = "http://maps.google.com/maps?daddr=" + destinationLatitude + "," + destinationLongitude;
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                intent.setPackage("com.google.android.apps.maps");
//                mContext.startActivity(intent);
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return activeAppointmentsData.size();
//    }
//
//    @Override
//    public void success(Object o, int code) {
//
//    }
//
//    @Override
//    public void error(Object o, int code) {
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//
//        TextView txt_time, txt_type, txt_dr_name, txt_app_date, txt_app_time, txt_vc, txt_direction;
//        ImageView img_userprofile;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            img_userprofile = itemView.findViewById(R.id.img_userprofile);
//            txt_vc = itemView.findViewById(R.id.txt_vc);
//            txt_time = itemView.findViewById(R.id.txt_time);
//            txt_type = itemView.findViewById(R.id.txt_type);
//            txt_dr_name = itemView.findViewById(R.id.txt_dr_name);
//            txt_app_date = itemView.findViewById(R.id.txt_app_date);
//            txt_app_time = itemView.findViewById(R.id.txt_app_time);
//            txt_direction = itemView.findViewById(R.id.txt_direction);
//
//        }
//    }
//
//
//}
