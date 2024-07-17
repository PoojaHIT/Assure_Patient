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
import com.hit.assure.Model.ReviewList.ReviewListData;
import com.hit.assure.R;
import com.hit.assure.Util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewListingAdapter extends RecyclerView.Adapter<ReviewListingAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<ReviewListData> reviewListData;

    public ReviewListingAdapter(Context mContext, List<ReviewListData> reviewListData) {
        this.mContext = mContext;
        this.reviewListData = reviewListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review_comment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_namee.setText(reviewListData.get(position).getUsername());
        holder.txt_date.setText(reviewListData.get(position).getReview_date() );
        holder.txt_title.setText(reviewListData.get(position).getTitle());
        holder.txt_dec.setText(reviewListData.get(position).getReview());


        Picasso.get()
                .load(reviewListData.get(position).getProfile_pic())
                .fit()
                .into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return reviewListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView img_product;
        TextView txt_namee, txt_date,txt_title,txt_dec;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_namee = itemView.findViewById(R.id.txt_namee);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_dec = itemView.findViewById(R.id.txt_dec);
            img_product = itemView.findViewById(R.id.que_image);


        }
    }


}
