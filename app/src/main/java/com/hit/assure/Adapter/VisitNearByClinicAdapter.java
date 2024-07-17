package com.hit.assure.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.VirtualConsult.ConsultantDetailsActivity;
import com.hit.assure.Model.VirtualConsultant.DoctorPracticesData;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantData;
import com.hit.assure.Model.VisitNearByClinic.VisitNearByData;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitNearByClinicAdapter extends RecyclerView.Adapter<VisitNearByClinicAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<VisitNearByData> virtualConsultantDataList;
    private String drId = "";
    private String clinic_name;
    private String clinic_id;
    private String clinic_city;
    private String distance;
//    private List<DoctorPracticesData> doctorPracticesDataList;


    public VisitNearByClinicAdapter(Context mContext, List<VisitNearByData> virtualConsultantDataList) {
        this.mContext = mContext;
        this.virtualConsultantDataList = virtualConsultantDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inclinic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
       // holder.txt_fee_consultation.setText(" ~ ₹ " + virtualConsultantDataList.get(position).getVideo_charge() + " " + "Consultation Fees");
        distance = virtualConsultantDataList.get(position).getDistance();


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


        holder.txt_bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mContext.startActivity(new Intent(mContext, ConsultantDetailsActivity.class).putExtra("drname", virtualConsultantDataList.get(position).getDoctor_name())
                            .putExtra("clinicname", clinic_name).putExtra("drImage", virtualConsultantDataList.get(position).getProfile_pic()).putExtra("consultation_type", "visit")
                            .putExtra("clinic_id", clinic_id).putExtra("drid", virtualConsultantDataList.get(position).getDoctor_user_id()));


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
        //    txt_fee_consultation = itemView.findViewById(R.id.txt_fee_consultation);
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
