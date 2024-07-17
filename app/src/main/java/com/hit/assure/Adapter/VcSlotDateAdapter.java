package com.hit.assure.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.ProductList.ProductListData;
import com.hit.assure.Model.Vcsoltdate.VcSlotDateData;
import com.hit.assure.R;
import com.hit.assure.Util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

//public class VcSlotDateAdapter extends RecyclerView.Adapter<VcSlotDateAdapter.ViewHolder> {
//
//    //vars
//    private Context mContext;
//  private List<VcSlotDateData> vcSlotDateData;
//
//    public VcSlotDateAdapter(Context mContext, List<VcSlotDateData> vcSlotDateData) {
//        this.mContext = mContext;
//        this.vcSlotDateData = vcSlotDateData;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_slots, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//
//
//        holder.txt_date.setText(vcSlotDateData.get(position).getDate());
//    }
//
//    @Override
//    public int getItemCount() {
//        return vcSlotDateData.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//
//
//        TextView txt_date;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            txt_date = itemView.findViewById(R.id.txt_date);
//
//
//        }
//    }
//
//
//}
