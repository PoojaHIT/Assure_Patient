package com.hit.assure.Fragment;

import static com.hit.assure.Retrofit.ServerCode.TIMINGDATA;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.VirtualConsult.BookVirtualConsultActivity;
import com.hit.assure.Model.VcSlotDateData.Timelist;
import com.hit.assure.Model.VcSlotDateData.TimingList;
import com.hit.assure.Model.VcSlotDateData.VcDataDate;
import com.hit.assure.Model.VcSlotDateData.VcSlotdateDataResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedSlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedSlotFragment extends Fragment implements ServerResponse {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_PHONE_CALL = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rec_morning, rec_afternoon, rec_evening, rec_night;
    private List<Timelist> slot_timelists_morning;
    private List<Timelist> slot_timelists_afternoon;
    private List<Timelist> slot_timelists_evening;
    private List<Timelist> slot_timelists_night;
    private List<TimingList> timingLists;
    private List<VcDataDate> vcDataDates;
    private String drId = "";
    private String clinicId = "";
    private String consulatingtype = "online";
    private String dateone = "";
    private int j;
    private String clinic_name;
    private String date = "";
    private TextView txt_date_time;
    private LinearLayout ll_morning, ll_afternoon, ll_evening, ll_night;
    private TextView txt_morning_no_slot, txt_afternoon_no_slot, txt_evening_no_slot, txt_night_no_slot;
    private LinearLayout fulllayout, ll_no_slots, ll_full_evening, ll_full_night;
    private LinearLayout txt_call;

    public SelectedSlotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectedSlotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedSlotFragment newInstance(String param1, String param2) {
        SelectedSlotFragment fragment = new SelectedSlotFragment();
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
        View view = inflater.inflate(R.layout.fragment_selected_slot, container, false);

        init(view);

        if (getArguments() != null) {
            drId = getArguments().getString("drId");
            clinicId = getArguments().getString("clinicId");
            consulatingtype = getArguments().getString("consultingType");
            dateone = getArguments().getString("Dateone");
            date = getArguments().getString("Date");
            clinic_name = getArguments().getString("clinic_name");
            txt_date_time.setText(dateone);

        }

        Log.e("check1", drId);
        Log.e("check2", clinicId);
        Log.e("check3", consulatingtype);
        //   Log.e("check4", dateone);

        new Requestor(TIMINGDATA, this).getVcSlotDateData(drId, clinicId, consulatingtype, date);

        return view;
    }

    public void init(View view) {
        txt_call = view.findViewById(R.id.txt_call);
        ll_full_evening = view.findViewById(R.id.ll_full_evening);
        ll_full_night = view.findViewById(R.id.ll_full_night);
        fulllayout = view.findViewById(R.id.fulllayout);
        ll_no_slots = view.findViewById(R.id.ll_no_slots);
        ll_morning = view.findViewById(R.id.ll_morning);
        txt_morning_no_slot = view.findViewById(R.id.txt_morning_no_slot);
        txt_afternoon_no_slot = view.findViewById(R.id.txt_afternoon_no_slot);
        txt_evening_no_slot = view.findViewById(R.id.txt_evening_no_slot);
        txt_night_no_slot = view.findViewById(R.id.txt_night_no_slot);
        ll_afternoon = view.findViewById(R.id.ll_afternoon);
        ll_evening = view.findViewById(R.id.ll_evening);
        ll_night = view.findViewById(R.id.ll_night);
        txt_date_time = view.findViewById(R.id.txt_date_time);
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

        txt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    String w = "tel:" + "9892071465";
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(w));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case TIMINGDATA:
                VcSlotdateDataResponse vcSlotdateDataResponse = (VcSlotdateDataResponse) o;
                if (vcSlotdateDataResponse.getStatus() == 200) {
                    vcSlotdateDataResponse.getCount();
                    vcDataDates = vcSlotdateDataResponse.getVcSlotDateData();

                    for (int i = 0; i < vcDataDates.size(); i++) {

                        timingLists = vcDataDates.get(i).getTimingLists();
                        slot_timelists_morning = timingLists.get(i).getMorningTimes();
                        if (!slot_timelists_morning.isEmpty()) {
                            MorningListingAdapter morningListingAdapter = new MorningListingAdapter(getActivity(), slot_timelists_morning, date);
                            rec_morning.setAdapter(morningListingAdapter);
                        } else {
                            txt_morning_no_slot.setVisibility(View.VISIBLE);
                        }

                        slot_timelists_afternoon = timingLists.get(1).getAfternoonTimes();
                        if (!slot_timelists_afternoon.isEmpty()) {
                            AfternoonListingAdapter afternoonListingAdapter = new AfternoonListingAdapter(getActivity(), slot_timelists_afternoon, date);
                            rec_afternoon.setAdapter(afternoonListingAdapter);
                        } else {
                            txt_afternoon_no_slot.setVisibility(View.VISIBLE);
                        }

                        slot_timelists_evening = timingLists.get(2).getEveningTimes();
                        if (!slot_timelists_evening.isEmpty()) {
                            EveningListingAdapter eveningListingAdapter = new EveningListingAdapter(getActivity(), slot_timelists_evening, date);
                            rec_evening.setAdapter(eveningListingAdapter);
                        } else {
                            txt_evening_no_slot.setVisibility(View.VISIBLE);
                        }


                        slot_timelists_night = timingLists.get(3).getNightTimes();
                        if (!slot_timelists_night.isEmpty()) {
                            NightlistAdapter nightlistAdapter = new NightlistAdapter(getActivity(), slot_timelists_night, date);
                            rec_night.setAdapter(nightlistAdapter);
                        } else {
                            txt_night_no_slot.setVisibility(View.VISIBLE);
                        }

                        if (slot_timelists_morning.isEmpty() && slot_timelists_afternoon.isEmpty() && slot_timelists_evening.isEmpty() && slot_timelists_night.isEmpty()) {

                            ll_no_slots.setVisibility(View.VISIBLE);
                            fulllayout.setVisibility(View.GONE);
                            ll_full_evening.setVisibility(View.GONE);
                            ll_full_night.setVisibility(View.GONE);
                        } else {
                            ll_no_slots.setVisibility(View.GONE);
                            fulllayout.setVisibility(View.VISIBLE);
                            ll_full_evening.setVisibility(View.VISIBLE);
                            ll_full_night.setVisibility(View.VISIBLE);
                        }

                    }


                } else {

                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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

        private String displayTime = "";
        private String disPlayToTime = "";
        private final String date;
        private String normalFromTime = "";
        private String normalToTime = "";

        public MorningListingAdapter(Context mContext, List<Timelist> morningTimes, String date) {
            this.mContext = mContext;
            this.morningTimes = morningTimes;
            this.date = date;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_time.setText(morningTimes.get(position).getDisplay_from_time());
            if (morningTimes.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);

            }


            String status = morningTimes.get(position).getStatus();
            Log.e("blueStatus", status);


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
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class).putExtra("drId", drId).putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone).putExtra("consultingType", consulatingtype));
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
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                holder.txt_time.setEnabled(false);

                if (Integer.parseInt(one) >= 12) {
                    // txt_morning_no_slot.setVisibility(View.VISIBLE);
                    holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                    holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                    holder.txt_time.setEnabled(false);


                } else {
                    ll_morning.setVisibility(View.VISIBLE);
                }

            }

        }

        @Override
        public int getItemCount() {
            return morningTimes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            RadioButton txt_time;
            private final LinearLayout ll_no_slot_availble;
            private final LinearLayout ll_acne;

            public ViewHolder(View itemView) {
                super(itemView);

                ll_no_slot_availble = itemView.findViewById(R.id.ll_no_slot_availble);
                txt_time = itemView.findViewById(R.id.txt_time);
                ll_acne = itemView.findViewById(R.id.ll_acne);

            }
        }


    }

    public class AfternoonListingAdapter extends RecyclerView.Adapter<AfternoonListingAdapter.ViewHolder> {

        //vars
        private final Context mContext;
        private final List<Timelist> afternoonTimes;
        private String displayTime;
        private final String date;


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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class).putExtra("drId", drId).putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone)
                            .putExtra("consultingType", consulatingtype));
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

                if (Integer.parseInt(one) >= 16) {
                    //       Toast.makeText(mContext, "Wow1", Toast.LENGTH_SHORT).show();
                    //   txt_afternoon_no_slot.setVisibility(View.VISIBLE);
                    holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                    holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                    holder.txt_time.setEnabled(false);

                }

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
        private final Context mContext;
        private final List<Timelist> eveningTimes;
        private String displayTime = "";
        private final String date;
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class).putExtra("drId", drId).putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone)
                            .putExtra("consultingType", consulatingtype));
                    Log.e("date", date);
                }
            });

            String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
            String fullname = currentTime;
            Log.e("checkfullname", fullname);
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


                if (Integer.parseInt(one) >= 20) {
                    //  Toast.makeText(mContext, "Wow1", Toast.LENGTH_SHORT).show();
                    //    txt_evening_no_slot.setVisibility(View.VISIBLE);
                    holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                    holder.txt_time.setTextColor(getResources().getColor(R.color.white));
                    holder.txt_time.setEnabled(false);


                }


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
        private final String date;
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
                    startActivity(new Intent(mContext, BookVirtualConsultActivity.class).putExtra("drId", drId).putExtra("time", normalFromTime)
                            .putExtra("clinic_name", clinic_name).putExtra("displayTime", displayTime).putExtra("date", date)
                            .putExtra("normalToTime", normalToTime).putExtra("displayToTime", disPlayToTime).putExtra("dateone", dateone)
                            .putExtra("consultingType", consulatingtype));
                    Log.e("date", date);
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


                if (Integer.parseInt(one) >= Integer.parseInt(ntimeone)) {
//                    holder.itemView.setVisibility(View.GONE);
//                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//                    holder.ll_acne.setBackgroundColor(getResources().getColor(R.color.lite_grey));
//                    holder.txt_time.setTextColor(getResources().getColor(R.color.white));
//                    holder.txt_time.setEnabled(false);


                }


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
}