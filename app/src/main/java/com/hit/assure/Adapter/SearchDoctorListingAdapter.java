package com.hit.assure.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.VirtualConsult.ConsultantDetailsActivity;
import com.hit.assure.Model.DoctorSearch.DoctorConsultationDataList;
import com.hit.assure.Model.DoctorSearch.DoctorListData;
import com.hit.assure.Model.DoctorSearch.DoctorPracticesDataList;
import com.hit.assure.Model.VirtualConsultant.DoctorPracticesData;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantData;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchDoctorListingAdapter extends RecyclerView.Adapter<SearchDoctorListingAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<DoctorListData>  doctorListData;
   private List<DoctorPracticesDataList> doctorPracticesData;
    private String distance;
    private String clinic_name;
    private String clinic_id;


    public SearchDoctorListingAdapter(Context mContext, List<DoctorListData> doctorListData) {
        this.mContext = mContext;
        this.doctorListData = doctorListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_virtual_consultant, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Picasso.get()
                .load(doctorListData.get(position).getProfile_pic())
                .fit()
                .into(holder.img_userprofile);

        Log.e("nameHello", doctorListData.get(position).getDoctor_name());

        holder.txt_name.setText(doctorListData.get(position).getDoctor_name());
        Log.e("name_search",doctorListData.get(position).getDoctor_name() );
        holder.txt_exp.setText(doctorListData.get(position).getExperience()+ " years experience overall");
        holder.txt_like.setText(doctorListData.get(position).getReview());
        holder.txt_patients.setText(doctorListData.get(position).getRating());
    //    holder.txt_fee_consultation.setText(" ~ ₹ " + doctorListData.get(position).getVideo_charge() +" "+ "Consultation Fees");

        distance = doctorListData.get(position).getDistance();


        if (Integer.parseInt(distance) >= 50){

            holder.txt_bookappointment.setVisibility(View.VISIBLE);
            holder.txt_bookappointment.setVisibility(View.GONE);

        }else {
            holder.txt_bookappointment.setVisibility(View.GONE);
            holder.txt_bookappointment.setVisibility(View.VISIBLE);
        }


        doctorPracticesData = doctorListData.get(position).getDoctorPracticesData();
        for (int i=0; i<doctorPracticesData.size(); i++){

            Log.e("checkdata",doctorPracticesData.get(i).getClinic_city());
            clinic_id = doctorPracticesData.get(i).getClinic_id();
            clinic_name = doctorPracticesData.get(i).getClinic_name();
            holder.txt_location.setText(doctorPracticesData.get(i).getClinic_city());
            holder.txt_clinic_name.setText(doctorPracticesData.get(i).getClinic_name());
        }

        holder.txt_videoconsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, ConsultantDetailsActivity.class).putExtra("drid", doctorListData.get(position).getDoctor_user_id())
                        .putExtra("clinic_id", clinic_id).putExtra("clinic_name", clinic_name).putExtra("consultation_type", "online"));
            }
        });

        holder.txt_bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, ConsultantDetailsActivity.class).putExtra("drname", doctorListData.get(position).getDoctor_name())
                        .putExtra("clinicname", clinic_name).putExtra("drImage", doctorListData.get(position).getProfile_pic()).putExtra("consultation_type", "visit")
                        .putExtra("clinic_id", clinic_id).putExtra("drid", doctorListData.get(position).getDoctor_user_id()));


            }
        });


    }

    @Override
    public int getItemCount() {
        return doctorListData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView img_userprofile;
        TextView  txt_name,txt_dr_type,txt_exp,txt_like,txt_patients,txt_location,txt_fee_consultation, txt_clinic_name,txt_bookappointment,txt_videoconsult;

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

        }
    }


}
