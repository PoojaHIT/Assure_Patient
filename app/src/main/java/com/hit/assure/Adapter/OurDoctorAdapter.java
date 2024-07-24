package com.hit.assure.Adapter;

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
import com.hit.assure.Model.DoctorSearch.DoctorListData;
import com.hit.assure.Model.DoctorSearch.DoctorPracticesDataList;
import com.hit.assure.Model.OrdeDoctorModel.OurDoctorListItem;
import com.hit.assure.Model.OrdeDoctorModel.OurDoctorPracticesItem;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OurDoctorAdapter extends RecyclerView.Adapter<OurDoctorAdapter.ViewHolder>{

    //vars
    private Context mContext;
    private List<OurDoctorListItem> doctorListData;
    private List<OurDoctorPracticesItem> doctorPracticesData;
    private String distance;
    private String clinic_name;
    private String clinic_id;


    public OurDoctorAdapter(Context mContext, List<OurDoctorListItem> doctorListData) {
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


      /*  Picasso.get()
                .load(doctorListData.get(position).getProfile_pic())
                .fit()
                .into(holder.img_userprofile);
*/
        Log.e("nameHello", doctorListData.get(position).getDoctorName());

        holder.txt_name.setText(doctorListData.get(position).getDoctorName());
        holder.txt_exp.setText(doctorListData.get(position).getExperience()+ " years experience overall");
        holder.txt_like.setText(doctorListData.get(position).getReview());
        holder.txt_patients.setText(doctorListData.get(position).getRating());
        //    holder.txt_fee_consultation.setText(" ~ ₹ " + doctorListData.get(position).getVideo_charge() +" "+ "Consultation Fees");

        distance = doctorListData.get(position).getDistance();

        holder.txt_bookappointment.setVisibility(View.VISIBLE);

        if (Integer.parseInt(distance) >= 50){

            holder.txt_bookappointment.setVisibility(View.VISIBLE);
            holder.txt_bookappointment.setVisibility(View.GONE);

        }else {
            holder.txt_bookappointment.setVisibility(View.GONE);
            holder.txt_bookappointment.setVisibility(View.VISIBLE);
        }

        doctorPracticesData = doctorListData.get(position).getDoctorPractices();
        for (int i=0; i<doctorPracticesData.size(); i++){

            Log.e("checkdata",doctorPracticesData.get(i).getCity());
            Log.e("getClinicName",doctorPracticesData.get(i).getClinicName());
            clinic_id = doctorPracticesData.get(i).getDoctorId();
            clinic_name = doctorPracticesData.get(i).getClinicName();
            holder.txt_location.setText(doctorPracticesData.get(i).getCity());
            holder.txt_clinic_name.setText(doctorPracticesData.get(i).getClinicName());
        }

        holder.txt_videoconsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mContext, ConsultantDetailsActivity.class);
                i.putExtra("drid", doctorListData.get(position).getDoctorPractices().get(0).getDoctorId());
                i.putExtra("clinic_id", clinic_id).putExtra("clinic_name", clinic_name);
                i.putExtra("consultation_type", "online");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(i);
            }
        });

        holder.txt_bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(mContext, ConsultantDetailsActivity.class);
                i.putExtra("drname", doctorListData.get(position).getDoctorName());
                        i.putExtra("clinicname", clinic_name);
                        i.putExtra("drImage", doctorListData.get(position).getDoctorPractices().get(0).getImage());
                        i.putExtra("consultation_type", "visit");
                       i .putExtra("clinic_id", clinic_id);
                       i.putExtra("drid", doctorListData.get(position).getDoctorPractices().get(0).getDoctorId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return doctorListData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView img_userprofile;
        TextView txt_name,txt_dr_type,txt_exp,txt_like,txt_patients,txt_location,txt_fee_consultation, txt_clinic_name,txt_bookappointment,txt_videoconsult;
        LinearLayout ll_rating;
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
            ll_rating = itemView.findViewById(R.id.ll_rating);
            ll_rating.setVisibility(View.GONE);

        }
    }


}
