package com.hit.assure.Activity;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.hit.assure.Retrofit.ServerCode.CANCELAPPOINTMENT;
import static com.hit.assure.Retrofit.ServerCode.WRITEREVIEW;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hit.assure.Activity.VirtualConsult.BookVirtualConsultActivity;
import com.hit.assure.Model.CancelAppointment.CancelAppointmentResponse;
import com.hit.assure.Model.Review.ReviewResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;

public class BottomSheetCancelDialog extends BottomSheetDialogFragment implements ServerResponse {

    private Context context;
    private String userId= "";
    private String drId = "";
    private String bookingId = "";
    View v;
    private View window;
    private LinearLayout ll_home_appointment,ll_yes_no;
    private TextView txt_return_to_home;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.row_cancel_appointment,
                container, false);



        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            drId = getArguments().getString("drId");
            bookingId = getArguments().getString("bookingId");
        }

        ll_home_appointment = v.findViewById(R.id.ll_home_appointment);
        ll_yes_no = v.findViewById(R.id.ll_yes_no);
        txt_return_to_home = v.findViewById(R.id.txt_return_to_home);


        TextView txt_no_ap = v.findViewById(R.id.txt_no_ap);
        TextView txt_yes = v.findViewById(R.id.txt_yes);

        txt_return_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Requestor(CANCELAPPOINTMENT, BottomSheetCancelDialog.this).getCancelAppointment(bookingId);


            }
        });
        txt_no_ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;

    }

    @Override
    public void success(Object o, int code) {
        switch (code){
            case CANCELAPPOINTMENT:
                CancelAppointmentResponse cancelAppointmentResponse = (CancelAppointmentResponse) o;
                if (cancelAppointmentResponse.getStatus() == 200){
                    Toast.makeText(getApplicationContext(), ""+ cancelAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    ll_yes_no.setVisibility(View.GONE);
                    ll_home_appointment.setVisibility(View.VISIBLE);


                }else if (cancelAppointmentResponse.getStatus() == 400){
                    Toast.makeText(getApplicationContext(), ""+ cancelAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }








    public View getWindow() {
        return window;
    }
}
