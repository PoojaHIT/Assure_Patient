package com.hit.assure.Fragment;

import static com.hit.assure.Activity.HomeActivity.drawerLayout;
import static com.hit.assure.Activity.HomeActivity.llDrawerLayout;
import static com.hit.assure.Retrofit.ServerCode.ACTIVEAPPOINTMENT;
import static com.hit.assure.Retrofit.ServerCode.CHECK_USER_END_MEETING;
import static com.hit.assure.Retrofit.ServerCode.USER_END_MEETING;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Activity.ApplicationFormActivity;
import com.hit.assure.Activity.CancelAppointActivity;
import com.hit.assure.Activity.VideoCallingActivity;
import com.hit.assure.Activity.VirtualConsult.BookVirtualConsultActivity;
import com.hit.assure.Model.ActiveAppointments.ActiveAppointmentsData;
import com.hit.assure.Model.ActiveAppointments.ActiveAppointmentsResponse;
import com.hit.assure.Model.OnlyResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiveFragment extends Fragment implements ServerResponse {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rec_active;
    private TextView txt_viewform;
    private List<ActiveAppointmentsData> activeAppointmentsDataList;
    private String userId = "";
    private View ll_active_no_booking;
    private ProgressDialog progressDialog;
    private RelativeLayout rl_hamburger;

    ImageView img_back;
    public ActiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiveFragment newInstance(String param1, String param2) {
        ActiveFragment fragment = new ActiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active, container, false);
        PreferenceServices.init(getActivity());
        userId = PreferenceServices.getInstance().getUser_id();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        init(view);
        showProgressDialog();
        new Requestor(ACTIVEAPPOINTMENT, this).getActiveAppointment(userId);
        return view;
    }


    public void init(View view) {
//        txt_viewform = view.findViewById(R.id.txt_viewform);

//        if (PreferenceServices.getInstance().getApplication_form().equalsIgnoreCase("0")) {
//            txt_viewform.setVisibility(View.GONE);
//        } else {
//            txt_viewform.setVisibility(View.VISIBLE);
//        }



        rec_active = view.findViewById(R.id.rec_active);
        ll_active_no_booking = view.findViewById(R.id.ll_active_no_booking);
        LinearLayoutManager mLiner = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rec_active.setLayoutManager(mLiner);
        rec_active.setHasFixedSize(true);
        rec_active.setNestedScrollingEnabled(false);


    }


    @Override
    public void success(Object o, int code) {
        switch (code) {
            case ACTIVEAPPOINTMENT:
                ActiveAppointmentsResponse activeAppointmentsResponse = (ActiveAppointmentsResponse) o;
                if (activeAppointmentsResponse.getStatus() == 200) {
                    activeAppointmentsDataList = activeAppointmentsResponse.getActiveAppointmentsData();
                    if (activeAppointmentsDataList.isEmpty()) {
                        hideProgressDialog();
                        ll_active_no_booking.setVisibility(View.VISIBLE);
                    } else {
                        hideProgressDialog();
                        ActiveAppointmentAdapter activeAppointmentAdapter = new ActiveAppointmentAdapter(getActivity(), activeAppointmentsDataList);
                        rec_active.setAdapter(activeAppointmentAdapter);
                    }


                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void onResume() {
        super.onResume();
        new Requestor(ACTIVEAPPOINTMENT, this).getActiveAppointment(userId);

    }


    public class ActiveAppointmentAdapter extends RecyclerView.Adapter<ActiveAppointmentAdapter.ViewHolder> implements ServerResponse {

        //vars
        private Context mContext;
        private List<ActiveAppointmentsData> activeAppointmentsData;
        private String user_id = "";
        private String bookingId = "";
        private String drId = "";
        private String bookingFromTime = "";
        private String room_url = "";
        private String clinic_id;
        private String destinationLatitude = "";
        private String destinationLongitude = "";
//    private String destinationLatitude ="19.131861";
//    private String destinationLongitude ="72.835361";


        public ActiveAppointmentAdapter(Context mContext, List<ActiveAppointmentsData> activeAppointmentsData) {
            this.mContext = mContext;
            this.activeAppointmentsData = activeAppointmentsData;

        }

        @NonNull
        @Override
        public ActiveAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_active_appointment, parent, false);
            ActiveAppointmentAdapter.ViewHolder holder = new ActiveAppointmentAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ActiveAppointmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

//        holder.txt_time.setText(activeAppointmentsData.get(position).getFrom_time());
            drId = activeAppointmentsData.get(position).getDoctor_id();
            bookingId = activeAppointmentsData.get(position).getBooking_id();
            bookingFromTime = activeAppointmentsData.get(position).getDisplay_from_time();
            destinationLatitude = activeAppointmentsData.get(position).getClinic_lat();
            destinationLongitude = activeAppointmentsData.get(position).getClinic_lng();
            holder.txt_dr_name.setText(activeAppointmentsData.get(position).getDoctor_name());
            holder.txt_app_date.setText(activeAppointmentsData.get(position).getBooking_date());
            holder.txt_app_time.setText(activeAppointmentsData.get(position).getFrom_time());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, CancelAppointActivity.class).putExtra("drId", activeAppointmentsData.get(position).getDoctor_id())
                            .putExtra("bookingId", activeAppointmentsData.get(position).getBooking_id())
                            .putExtra("bookingFromTime", activeAppointmentsData.get(position).getDisplay_from_time())
                            .putExtra("clinic_id", activeAppointmentsData.get(position).getClinic_id()).putExtra("date", activeAppointmentsData.get(position).getBooking_date()).putExtra("consultingType", activeAppointmentsData.get(position).getConsultation_type()));
                }
            });
            if (activeAppointmentsData.get(position).getConsultation_type().equalsIgnoreCase("video")) {
                holder.txt_type.setText("Video Consultation");
                Picasso.get()
                        .load(R.drawable.img_vid_consultingg)
                        .fit()
                        .into(holder.img_userprofile);
            }

            if (activeAppointmentsData.get(position).getConsultation_type().equalsIgnoreCase("visit")) {
                holder.txt_type.setText("In-Clinic Booking");
                Picasso.get()
                        .load(R.drawable.img_clinic)
                        .fit()
                        .into(holder.img_userprofile);

                holder.txt_vc.setVisibility(View.GONE);
                holder.txt_direction.setVisibility(View.VISIBLE);
            }

            holder.txt_vc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!activeAppointmentsData.get(position).getRoom_url().isEmpty()) {
                        showProgressDialog();
                        new Requestor(USER_END_MEETING, ActiveAppointmentAdapter.this)
                                .getUserendmeeting(activeAppointmentsData.get(position).getUser_id(),
                                        activeAppointmentsData.get(position).getBooking_id());
                        user_id = activeAppointmentsData.get(position).getUser_id();
                        bookingId = activeAppointmentsData.get(position).getBooking_id();
                        room_url = activeAppointmentsData.get(position).getRoom_url();

                    } else {
                        Toast.makeText(mContext, "Room url not generated", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.txt_direction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = "http://maps.google.com/maps?daddr=" + destinationLatitude + "," + destinationLongitude;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    mContext.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return activeAppointmentsData.size();
        }

        @Override
        public void success(Object o, int code) {
            switch (code) {
                case USER_END_MEETING:
                    OnlyResponse onlyResponse = (OnlyResponse) o;
                    if (onlyResponse.getResponseCode().equals("200")) {
                        hideProgressDialog();
                        showProgressDialog();
                        new Requestor(CHECK_USER_END_MEETING, ActiveAppointmentAdapter.this)
                                .getCheckUserendmeeting(user_id,
                                        bookingId);
                    }
                    break;
                case CHECK_USER_END_MEETING:
                    OnlyResponse onlyResponse1 = (OnlyResponse) o;
                    if (onlyResponse1.getResponseCode().equals("200")) {
                        hideProgressDialog();
                        mContext.startActivity(new Intent(mContext, VideoCallingActivity.class)
                                .putExtra("roomUrl", room_url));
                    } else {
                        hideProgressDialog();
                        mContext.startActivity(new Intent(mContext, VideoCallingActivity.class)
                                .putExtra("roomUrl", room_url));
                    }
                    break;
            }
        }

        @Override
        public void error(Object o, int code) {

        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            TextView txt_time, txt_type, txt_dr_name, txt_app_date, txt_app_time, txt_vc, txt_direction;
            ImageView img_userprofile;

            public ViewHolder(View itemView) {
                super(itemView);
                img_userprofile = itemView.findViewById(R.id.img_userprofile);
                txt_vc = itemView.findViewById(R.id.txt_vc);
                txt_time = itemView.findViewById(R.id.txt_time);
                txt_type = itemView.findViewById(R.id.txt_type);
                txt_dr_name = itemView.findViewById(R.id.txt_dr_name);
                txt_app_date = itemView.findViewById(R.id.txt_app_date);
                txt_app_time = itemView.findViewById(R.id.txt_app_time);
                txt_direction = itemView.findViewById(R.id.txt_direction);

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


}