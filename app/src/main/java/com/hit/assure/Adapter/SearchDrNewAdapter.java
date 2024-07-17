package com.hit.assure.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.DoctorSearch.DoctorListData;
import com.hit.assure.Model.Search.SearchData;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchDrNewAdapter extends RecyclerView.Adapter<SearchDrNewAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<DoctorListData>  doctorListData;
    String  name;

    public SearchDrNewAdapter(Context mContext, List<DoctorListData> doctorListData) {
        this.mContext = mContext;
        this.doctorListData = doctorListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_productlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

      name =  doctorListData.get(position).getDoctor_name();
        Log.e("Namee", name);



    }

    @Override
    public int getItemCount() {
        return doctorListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView img_product;
        TextView txt_product_title, txt_product_price;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_product_title = itemView.findViewById(R.id.txt_product_title);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            img_product = itemView.findViewById(R.id.img_product);

        }
    }


}
