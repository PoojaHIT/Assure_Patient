package com.hit.assure.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.ReviewList.ReviewListData;
import com.hit.assure.Model.ReviewTwo.ReviewTwoData;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewTwoListingAdapter extends RecyclerView.Adapter<ReviewTwoListingAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<ReviewTwoData> reviewListData;

    public ReviewTwoListingAdapter(Context mContext, List<ReviewTwoData> reviewListData) {
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
                .load( reviewListData.get(position).getProfile_pic())
                .fit()
                .into(holder.que_image);

    }

    @Override
    public int getItemCount() {
        return reviewListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        TextView txt_namee, txt_date,txt_title,txt_dec;
        CircleImageView que_image;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_namee = itemView.findViewById(R.id.txt_namee);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_dec = itemView.findViewById(R.id.txt_dec);
            que_image = itemView.findViewById(R.id.que_image);


        }
    }


}
