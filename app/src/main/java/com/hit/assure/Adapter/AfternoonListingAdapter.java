package com.hit.assure.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.VcSlotDateData.Timelist;
import com.hit.assure.R;

import java.util.List;

public class AfternoonListingAdapter extends RecyclerView.Adapter<AfternoonListingAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<Timelist> afternoonTimes;

    public AfternoonListingAdapter(Context mContext, List<Timelist> afternoonTimes) {
        this.mContext = mContext;
        this.afternoonTimes = afternoonTimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots_orange, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_time.setText(afternoonTimes.get(position).getFrom_time());



    }

    @Override
    public int getItemCount() {
        return afternoonTimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_time;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_time = itemView.findViewById(R.id.txt_time);

        }
    }


}
