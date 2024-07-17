package com.hit.assure.Fragment;

import static com.hit.assure.Retrofit.ServerCode.COMPLETEDAPPOINTMENT;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hit.assure.Adapter.CompletedAppointmentAdapter;
import com.hit.assure.Model.CompletedAppointments.CompletedAppointmentResponse;
import com.hit.assure.Model.CompletedAppointments.CompletedAppointmentsData;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PreferenceServices;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedFragment extends Fragment implements ServerResponse {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView rec_completed;
    private List<CompletedAppointmentsData> completedAppointmentsData;
    private String userId = "";
    private View ll_completed_no_booking;
    private ProgressDialog progressDialog;

    public CompletedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletedFragment newInstance(String param1, String param2) {
        CompletedFragment fragment = new CompletedFragment();
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
        View view = inflater.inflate(R.layout.fragment_completed, container, false);
        userId = PreferenceServices.getInstance().getUser_id();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        Log.e("userID", userId);
        init(view);
        showProgressDialog();
        new Requestor(COMPLETEDAPPOINTMENT, this).getCompletedAppointment(PreferenceServices.getInstance().getUser_id());
        return view;
    }

    public void init(View view) {
        ll_completed_no_booking = view.findViewById(R.id.ll_completed_no_booking);
        rec_completed = view.findViewById(R.id.rec_completed);
        LinearLayoutManager mLiner = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rec_completed.setLayoutManager(mLiner);
        rec_completed.setHasFixedSize(true);
        rec_completed.setNestedScrollingEnabled(false);

    }

    @Override
    public void success(Object o, int code) {
        hideProgressDialog();
        switch (code) {
            case COMPLETEDAPPOINTMENT:
                CompletedAppointmentResponse completedAppointmentResponse = (CompletedAppointmentResponse) o;
                if (completedAppointmentResponse.getStatus() == 200) {
                    completedAppointmentsData = completedAppointmentResponse.getCompletedAppointmentsData();

                    if (!completedAppointmentsData.isEmpty()) {
                        CompletedAppointmentAdapter completedAppointmentAdapter = new CompletedAppointmentAdapter(getActivity(), completedAppointmentsData,"0");
                        rec_completed.setAdapter(completedAppointmentAdapter);
                    } else {
                        ll_completed_no_booking.setVisibility(View.VISIBLE);
                    }


                } else {

                    Toast.makeText(getActivity(), "" + completedAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume() {
        super.onResume();

        new Requestor(COMPLETEDAPPOINTMENT, this).getCompletedAppointment(PreferenceServices.getInstance().getUser_id());
    }
}