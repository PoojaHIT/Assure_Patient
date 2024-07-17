package com.hit.assure.Fragment;

import static com.hit.assure.Retrofit.ServerCode.DOCTORPROFILE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.Activity.VirtualConsult.BookVirtualConsultActivity;
import com.hit.assure.Adapter.NightlistAdapter;
import com.hit.assure.Model.Doctorprofile.DoctorPractices;
import com.hit.assure.Model.Doctorprofile.DoctorProfileData;
import com.hit.assure.Model.Doctorprofile.DoctorProfileresponse;
import com.hit.assure.Model.Doctorprofile.WorkingHours;
import com.hit.assure.Model.VcSlotDateData.Timelist;
import com.hit.assure.Model.VcSlotDateData.TimingList;
import com.hit.assure.Model.VcSlotDateData.VcDataDate;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsPageSlotsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsPageSlotsFragment extends Fragment implements ServerResponse {


    private List<DoctorProfileData> doctorProfileDataList;
    private List<Timelist> slot_timelists_morning;
    private List<Timelist> slot_timelists_afternoon;
    private List<Timelist> slot_timelists_evening;
    private List<Timelist> slot_timelists_night;
    private List<TimingList> timingLists;
    private List<WorkingHours> workingHours;
    private List<VcDataDate> vcDataDates;
    private String drId = "";
    private String date = "";
    private RecyclerView rec_morning, rec_afternoon, rec_evening, rec_night;
    private List<DoctorPractices> doctorPractices;
    private String clinic_name;
    private String clinic_Id;
    private String dateone;
    private String visiblity;
    private LinearLayout ll_morning, ll_afternoon, ll_evening, ll_night;
    private LinearLayout ll_name_morning, ll_name_afternoon, ll_name_evening, ll_name_night;
    private LinearLayout ll_no_slot_availble, ll_no_afternoon_slot_available, ll_no_evening_slot_availble, ll_no_night_slot_availble;
    private String userId;
    private String consultingType;
    private String selected = "";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsPageSlotsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsPageSlotsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsPageSlotsFragment newInstance(String param1, String param2) {
        DetailsPageSlotsFragment fragment = new DetailsPageSlotsFragment();
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
        View view = inflater.inflate(R.layout.fragment_details_page_slots, container, false);

        init(view);

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.e("detailpage", date);

        userId = PreferenceServices.getInstance().getUser_id();
        Log.e("userIdd", userId);

        if (getArguments() != null) {
            visiblity = getArguments().getString("morning");
            drId = getArguments().getString("drId");
            clinic_Id = getArguments().getString("clinicId");
            consultingType = getArguments().getString("consultingType");
            selected = getArguments().getString("selected");
            //    Log.e("detaildrId", drId);
            Log.e("morning", visiblity);
        }

        if (Integer.parseInt(visiblity) == 1) {
            //    ll_no_slot_availble.setVisibility(View.GONE);
            ll_morning.setVisibility(View.VISIBLE);
            ll_afternoon.setVisibility(View.GONE);
            ll_evening.setVisibility(View.GONE);
            ll_night.setVisibility(View.GONE);


        }

        if (Integer.parseInt(visiblity) == 2) {
            //     ll_no_slot_availble.setVisibility(View.GONE);
            ll_morning.setVisibility(View.GONE);
            ll_afternoon.setVisibility(View.VISIBLE);
            ll_evening.setVisibility(View.GONE);
            ll_night.setVisibility(View.GONE);


        }

        if (Integer.parseInt(visiblity) == 3) {
            //          ll_no_slot_availble.setVisibility(View.GONE);
            ll_morning.setVisibility(View.GONE);
            ll_afternoon.setVisibility(View.GONE);
            ll_evening.setVisibility(View.VISIBLE);
            ll_night.setVisibility(View.GONE);


        }

        if (Integer.parseInt(visiblity) == 4) {
            //        ll_no_slot_availble.setVisibility(View.GONE);
            ll_morning.setVisibility(View.GONE);
            ll_afternoon.setVisibility(View.GONE);
            ll_evening.setVisibility(View.GONE);
            ll_night.setVisibility(View.VISIBLE);

        }

        new Requestor(DOCTORPROFILE, this).getDoctorProfile(drId, userId, clinic_Id, date);
        return view;
    }

    public void init(View view) {

        ll_no_slot_availble = view.findViewById(R.id.ll_no_slot_availble);
        ll_no_afternoon_slot_available = view.findViewById(R.id.ll_no_afternoon_slot_available);
        ll_no_evening_slot_availble = view.findViewById(R.id.ll_no_evening_slot_availble);
        ll_no_night_slot_availble = view.findViewById(R.id.ll_no_night_slot_availble);
        ll_name_morning = view.findViewById(R.id.ll_name_morning);
        ll_name_afternoon = view.findViewById(R.id.ll_name_afternoon);
        ll_name_evening = view.findViewById(R.id.ll_name_evening);
        ll_name_night = view.findViewById(R.id.ll_name_night);
        ll_morning = view.findViewById(R.id.ll_morning);
        ll_afternoon = view.findViewById(R.id.ll_afternoon);
        ll_evening = view.findViewById(R.id.ll_evening);
        ll_night = view.findViewById(R.id.ll_night);

        rec_morning = view.findViewById(R.id.rec_morning);
        rec_morning.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rec_morning.setHasFixedSize(true);
        rec_morning.setNestedScrollingEnabled(false);

        rec_afternoon = view.findViewById(R.id.rec_afternoon);
        rec_afternoon.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rec_afternoon.setHasFixedSize(true);
        rec_afternoon.setNestedScrollingEnabled(false);

        rec_evening = view.findViewById(R.id.rec_evening);
        rec_evening.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rec_evening.setHasFixedSize(true);
        rec_evening.setNestedScrollingEnabled(false);

        rec_night = view.findViewById(R.id.rec_night);
        rec_night.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rec_night.setHasFixedSize(true);
        rec_night.setNestedScrollingEnabled(false);

    }

    @Override
    public void success(Object o, int code) {

        switch (code) {

            case DOCTORPROFILE:
                DoctorProfileresponse doctorProfileresponse = (DoctorProfileresponse) o;
                if (doctorProfileresponse.getStatus() == 200) {

                    doctorProfileDataList = doctorProfileresponse.getDoctorProfileDataList();
                    for (int i = 0; i < doctorProfileDataList.size(); i++) {
                        doctorPractices = doctorProfileDataList.get(i).getDoctorPractices();
                        workingHours = doctorProfileDataList.get(i).getWorkingHours();
                        clinic_name = doctorPractices.get(i).getClinic_name();
                        vcDataDates = doctorProfileDataList.get(i).getVcDataDates();
                        for (int j = 0; j < vcDataDates.size(); j++) {
                            dateone = vcDataDates.get(i).getDate();
                            timingLists = vcDataDates.get(j).getTimingLists();
                            slot_timelists_morning = timingLists.get(j).getMorningTimes();
                            if (slot_timelists_morning != null && !slot_timelists_morning.isEmpty()) {
                                MorningListingAdapter morningListingAdapter = new MorningListingAdapter(getActivity(), slot_timelists_morning, date);
                                rec_morning.setAdapter(morningListingAdapter);
                            } else {
                                ll_no_slot_availble.setVisibility(View.VISIBLE);
                                ll_name_morning.setVisibility(View.GONE);
                            }

                            slot_timelists_afternoon = timingLists.get(1).getAfternoonTimes();
                            if (slot_timelists_afternoon != null && !slot_timelists_afternoon.isEmpty()) {
                                AfternoonListingAdapter afternoonListingAdapter = new AfternoonListingAdapter(getActivity(), slot_timelists_afternoon, date);
                                rec_afternoon.setAdapter(afternoonListingAdapter);

                            } else {
                                ll_no_afternoon_slot_available.setVisibility(View.VISIBLE);
                                ll_name_afternoon.setVisibility(View.GONE);
                            }


                            slot_timelists_evening = timingLists.get(2).getEveningTimes();
                            if (slot_timelists_evening != null && !slot_timelists_evening.isEmpty()) {
                                EveningListingAdapter eveningListingAdapter = new EveningListingAdapter(getActivity(), slot_timelists_evening, date);
                                rec_evening.setAdapter(eveningListingAdapter);
                            } else {
                                ll_no_evening_slot_availble.setVisibility(View.VISIBLE);
                                ll_name_evening.setVisibility(View.GONE);
                            }


                            slot_timelists_night = timingLists.get(3).getNightTimes();
                            if (slot_timelists_night != null && !slot_timelists_night.isEmpty()) {
                                NightlistAdapter nightlistAdapter = new NightlistAdapter(getActivity(), slot_timelists_night, date);
                                rec_night.setAdapter(nightlistAdapter);
                            } else {
                                ll_no_night_slot_availble.setVisibility(View.VISIBLE);
                                ll_name_night.setVisibility(View.GONE);
                            }


                        }


                    }

                } else {

                    Toast.makeText(getActivity(), "Doctor List", Toast.LENGTH_SHORT).show();

                }
                break;


        }


    }

    @Override
    public void error(Object o, int code) {

    }

    public class MorningListingAdapter extends RecyclerView.Adapter<MorningListingAdapter.ViewHolder> {
        //vars
        Context mContext;
        List<Timelist> morningTimes;
        private String endTime;
        private String endTimePart;

        private String displayTime = "";
        private String disPlayToTime = "";
        private String date;
        private String normalFromTime = "";
        private String normalToTime = "";

//        public MorningListingAdapter(Context mContext, List<Timelist> morningTimes, String date) {
//            this.mContext = mContext;
//            this.morningTimes = morningTimes;
//            this.date = date;
//        }


        public MorningListingAdapter(Context mContext, List<Timelist> morningTimes, String date) {
            this.mContext = mContext;
            this.morningTimes = morningTimes;
            this.date = date;
        }

        @NonNull
        @Override
        public MorningListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            MorningListingAdapter.ViewHolder holder = new MorningListingAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(MorningListingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


            holder.txt_time.setText(morningTimes.get(position).getDisplay_from_time());

            if (morningTimes.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);
            }

            holder.txt_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayTime = morningTimes.get(position).getDisplay_from_time();
                    Log.e("displayFromTime", displayTime);
                    disPlayToTime = morningTimes.get(position).getDisplay_to_time();
                    Log.e("disPlayToTime", disPlayToTime);
                    normalFromTime = morningTimes.get(position).getFrom_time();
                    Log.e("normalFromTime", normalFromTime);
                    normalToTime = morningTimes.get(position).getTo_time();
                    Log.e("normalToTime", normalToTime);
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class)
                            .putExtra("drId", drId)
                            .putExtra("selected", selected)
                            .putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone).putExtra("consultingType", consultingType));
                    Log.e("date", date);

                }
            });
            String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
            String fullname = currentTime;
            String[] parts = fullname.split("\\s+");
            String firstname = parts[0];
            String lastname = parts[1];
            Log.e("firstname", firstname);
            Log.e("lastname", lastname);
            String splitTime = firstname;
            String[] time = splitTime.split(":");
            String one = time[0];
            Log.e("yelloOne", one);
            String ntime = morningTimes.get(position).getTo_time();
            String[] mtimeparts = ntime.split(":");
            String ntimeone = mtimeparts[0];
            Log.e("ntimeone", ntimeone);


            if (Integer.parseInt(one) >= Integer.parseInt(ntimeone)) {
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);
            }

//            for (int i = 0; i < morningWorkingHours.size(); i++) {
//
//                if (morningWorkingHours.get(i).getTimeSlot1().equalsIgnoreCase("Morning"))
//                    endTime = morningWorkingHours.get(i).getEnd_time();
//                Log.e("EndTimeCheck", endTime);
//                String part[] = endTime.split(":");
//                endTimePart = part[0];
//                Log.e("endTimepArt", endTimePart);
//
//            }

            if (Integer.parseInt(one) >= 12) {
                ll_name_morning.setVisibility(View.GONE);
                ll_name_afternoon.setVisibility(View.GONE);
                ll_no_afternoon_slot_available.setVisibility(View.VISIBLE);
                ll_no_slot_availble.setVisibility(View.GONE);
                ll_no_night_slot_availble.setVisibility(View.GONE);
                ll_no_evening_slot_availble.setVisibility(View.GONE);
                rec_morning.setVisibility(View.GONE);


            } else {
                ll_name_morning.setVisibility(View.GONE);
                ll_name_afternoon.setVisibility(View.GONE);
                ll_no_afternoon_slot_available.setVisibility(View.GONE);
                ll_no_slot_availble.setVisibility(View.GONE);
                ll_no_night_slot_availble.setVisibility(View.GONE);
                ll_no_evening_slot_availble.setVisibility(View.GONE);

            }

        }

        @Override
        public int getItemCount() {
            return morningTimes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            RadioButton txt_time;
            LinearLayout ll_no_slot_availble;
            LinearLayout ll_acne;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_time = itemView.findViewById(R.id.txt_time);
                ll_no_slot_availble = itemView.findViewById(R.id.ll_no_slot_availble);
                ll_acne = itemView.findViewById(R.id.ll_acne);


            }
        }


    }

    public class AfternoonListingAdapter extends RecyclerView.Adapter<AfternoonListingAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<Timelist> afternoonTimes;
        private String displayTime;
        private String date;


        private String disPlayToTime = "";
        private String normalFromTime = "";
        private String normalToTime = "";

        public AfternoonListingAdapter(Context mContext, List<Timelist> afternoonTimes, String date) {
            this.mContext = mContext;
            this.afternoonTimes = afternoonTimes;
            this.date = date;
        }

        @NonNull
        @Override
        public AfternoonListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            AfternoonListingAdapter.ViewHolder holder = new AfternoonListingAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(AfternoonListingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_time.setText(afternoonTimes.get(position).getDisplay_from_time());
            if (afternoonTimes.get(position).getStatus().equalsIgnoreCase("1")) {
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);


            }


            holder.txt_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayTime = afternoonTimes.get(position).getDisplay_from_time();
                    Log.e("displayFromTime", displayTime);
                    disPlayToTime = afternoonTimes.get(position).getDisplay_to_time();
                    Log.e("disPlayToTime", disPlayToTime);
                    normalFromTime = afternoonTimes.get(position).getFrom_time();
                    Log.e("normalFromTime", normalFromTime);
                    normalToTime = afternoonTimes.get(position).getTo_time();
                    Log.e("normalToTime", normalToTime);
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class)
                            .putExtra("drId", drId)
                            .putExtra("selected", selected)
                            .putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone).putExtra("consultingType", consultingType));
                    Log.e("date", date);
                }
            });
            String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
            String fullname = currentTime;
            String[] parts = fullname.split("\\s+");
            String firstname = parts[0];
            String lastname = parts[1];
            Log.e("firstname", firstname);
            Log.e("lastname", lastname);
            String splitTime = firstname;
            String[] time = splitTime.split(":");
            String one = time[0];
            Log.e("yelloOne", one);
            String ntime = afternoonTimes.get(position).getTo_time();
            String[] mtimeparts = ntime.split(":");
            String ntimeone = mtimeparts[0];
            Log.e("ntimeone", ntimeone);


            if (Integer.parseInt(one) >= Integer.parseInt(ntimeone)) {
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);


            }

            if (Integer.parseInt(one) >= 16) {

                // Toast.makeText(mContext, "WowAfternoono", Toast.LENGTH_SHORT).show();
                ll_name_afternoon.setVisibility(View.GONE);
                ll_no_afternoon_slot_available.setVisibility(View.VISIBLE);
                ll_no_slot_availble.setVisibility(View.GONE);
                ll_no_night_slot_availble.setVisibility(View.GONE);
                ll_no_evening_slot_availble.setVisibility(View.GONE);
                rec_afternoon.setVisibility(View.GONE);


            } else {

                ll_name_afternoon.setVisibility(View.VISIBLE);
                ll_no_afternoon_slot_available.setVisibility(View.GONE);
                ll_no_slot_availble.setVisibility(View.GONE);
                ll_no_night_slot_availble.setVisibility(View.GONE);
                ll_no_evening_slot_availble.setVisibility(View.GONE);

            }


        }

        @Override
        public int getItemCount() {
            return afternoonTimes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            TextView txt_time;
            LinearLayout ll_acne;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_time = itemView.findViewById(R.id.txt_time);
                ll_acne = itemView.findViewById(R.id.ll_acne);

            }
        }


    }

    public class EveningListingAdapter extends RecyclerView.Adapter<EveningListingAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<Timelist> eveningTimes;
        private String displayTime = "";
        private String date;
        private String disPlayToTime = "";
        private String normalFromTime = "";
        private String normalToTime = "";

        public EveningListingAdapter(Context mContext, List<Timelist> eveningTimes, String date) {
            this.mContext = mContext;
            this.eveningTimes = eveningTimes;
            this.date = date;
        }

        @NonNull
        @Override
        public EveningListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            EveningListingAdapter.ViewHolder holder = new EveningListingAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(EveningListingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_time.setText(eveningTimes.get(position).getDisplay_from_time());
            if (eveningTimes.get(position).getStatus().equalsIgnoreCase("1")) {
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);
            }

            holder.txt_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayTime = eveningTimes.get(position).getDisplay_from_time();
                    Log.e("displayFromTime", displayTime);
                    disPlayToTime = eveningTimes.get(position).getDisplay_to_time();
                    Log.e("disPlayToTime", disPlayToTime);
                    normalFromTime = eveningTimes.get(position).getFrom_time();
                    Log.e("normalFromTime", normalFromTime);
                    normalToTime = eveningTimes.get(position).getTo_time();
                    Log.e("normalToTime", normalToTime);
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class)
                            .putExtra("drId", drId)
                            .putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name)
                            .putExtra("displayTime", displayTime)
                            .putExtra("selected", selected)
                            .putExtra("date", date)
                            .putExtra("normalToTime", normalToTime)
                            .putExtra("displayToTime", disPlayToTime)
                            .putExtra("dateone", dateone)
                            .putExtra("consultingType", consultingType));
                    Log.e("date", date);
                    Log.e("cstype", consultingType);
                }
            });

            String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
            String fullname = currentTime;
            String[] parts = fullname.split("\\s+");
            String firstname = parts[0];
            String lastname = parts[1];
            Log.e("firstname", firstname);
            Log.e("lastname", lastname);
            String splitTime = firstname;
            String[] time = splitTime.split(":");
            String one = time[0];
            Log.e("yelloOne", one);
            String ntime = eveningTimes.get(position).getTo_time();
            String[] mtimeparts = ntime.split(":");
            String ntimeone = mtimeparts[0];
            Log.e("ntimeone", ntimeone);


            if (Integer.parseInt(one) >= Integer.parseInt(ntimeone)) {
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);


            }

            if (Integer.parseInt(one) >= 20) {
                //        Toast.makeText(mContext, "Wowevening", Toast.LENGTH_SHORT).show();
                ll_name_afternoon.setVisibility(View.GONE);
                ll_no_afternoon_slot_available.setVisibility(View.VISIBLE);
                ll_no_slot_availble.setVisibility(View.GONE);
                ll_no_evening_slot_availble.setVisibility(View.GONE);
                ll_no_night_slot_availble.setVisibility(View.GONE);
                rec_evening.setVisibility(View.GONE);

            } else {
                ll_name_afternoon.setVisibility(View.VISIBLE);
                ll_no_slot_availble.setVisibility(View.GONE);
                ll_no_evening_slot_availble.setVisibility(View.GONE);
                ll_no_night_slot_availble.setVisibility(View.GONE);
            }


        }

        @Override
        public int getItemCount() {
            return eveningTimes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_time;
            LinearLayout ll_acne;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_time = itemView.findViewById(R.id.txt_time);
                ll_acne = itemView.findViewById(R.id.ll_acne);


            }
        }


    }

    public class NightlistAdapter extends RecyclerView.Adapter<NightlistAdapter.ViewHolder> {
        //vars
        Context mContext;
        List<Timelist> nightTimes;
        private String displayTime = "";
        private String date;
        private String disPlayToTime = "";
        private String normalFromTime = "";
        private String normalToTime = "";

        public NightlistAdapter(Context mContext, List<Timelist> nightTimes, String date) {
            this.mContext = mContext;
            this.nightTimes = nightTimes;
            this.date = date;
        }

        @NonNull
        @Override
        public NightlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            NightlistAdapter.ViewHolder holder = new NightlistAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(NightlistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_time.setText(nightTimes.get(position).getDisplay_from_time());


            if (nightTimes.get(position).getStatus().equalsIgnoreCase("1")) {
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);


            }


            holder.txt_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayTime = nightTimes.get(position).getDisplay_from_time();
                    Log.e("displayFromTime", displayTime);
                    disPlayToTime = nightTimes.get(position).getDisplay_to_time();
                    Log.e("disPlayToTime", disPlayToTime);
                    normalFromTime = nightTimes.get(position).getFrom_time();
                    Log.e("normalFromTime", normalFromTime);
                    normalToTime = nightTimes.get(position).getTo_time();
                    Log.e("normalToTime", normalToTime);
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class)
                            .putExtra("drId", drId)
                            .putExtra("selected", selected)
                            .putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone).putExtra("consultingType", consultingType));
                    Log.e("date", date);
                    Log.e("consultingType", consultingType);

                }
            });
            String dateone = date;
            String[] dateparts = dateone.split("-");
            String datepartone = dateparts[0];
            String dateparttwo = dateparts[1];
            String datepartthree = dateparts[2];
            Log.e("datepartthree", datepartthree);

            String currentDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
            Log.e("currentDate", currentDate);
            if (Integer.parseInt(datepartthree) == Integer.parseInt(currentDate)) {
                String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                String fullname = currentTime;
                String[] parts = fullname.split("\\s+");
                String firstname = parts[0];
                String lastname = parts[1];
                Log.e("firstname", firstname);
                Log.e("lastname", lastname);
                String splitTime = firstname;
                String[] time = splitTime.split(":");
                String one = time[0];
                Log.e("yelloOne", one);
                String ntime = nightTimes.get(position).getTo_time();
                String[] mtimeparts = ntime.split(":");
                String ntimeone = mtimeparts[0];
                Log.e("ntimeone", ntimeone);


//                if (Integer.parseInt(one) >= Integer.parseInt(ntimeone)) {
//                    holder.itemView.setVisibility(View.GONE);
//                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//
//
//
//
//
//                }


            }
        }

        @Override
        public int getItemCount() {
            return nightTimes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_time;
            LinearLayout ll_acne;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_time = itemView.findViewById(R.id.txt_time);
                ll_acne = itemView.findViewById(R.id.ll_acne);

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ll_name_afternoon.setVisibility(View.GONE);
        ll_no_slot_availble.setVisibility(View.GONE);
        ll_no_evening_slot_availble.setVisibility(View.GONE);
        ll_no_night_slot_availble.setVisibility(View.GONE);

    }
}