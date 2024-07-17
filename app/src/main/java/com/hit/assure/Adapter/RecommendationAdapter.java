package com.hit.assure.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.BlogDetailsActivity;
import com.hit.assure.Model.Category.CategoryData;
import com.hit.assure.Model.Recommendation.RecommendationData;
import com.hit.assure.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<RecommendationData> recommendationData;

    public RecommendationAdapter(Context mContext, List<RecommendationData> recommendationData) {
        this.mContext = mContext;
        this.recommendationData = recommendationData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recommendation, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (recommendationData.get(position).getCategory_id().equalsIgnoreCase("1")) {
            holder.txt_type.setText("Skin Care");
        } else if (recommendationData.get(position).getCategory_id().equalsIgnoreCase("2")) {
            holder.txt_type.setText("Hair Care");
        }
        Picasso.get()
                .load(recommendationData.get(position).getArticle_image())
                .fit()
                .into(holder.img_recc);
        holder.txt_tittle.setText(recommendationData.get(position).getArticle_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BlogDetailsActivity.class)
                        .putExtra("airtleId", recommendationData.get(position).getArticle_id())
                );
            }
        });


//        holder.txt_name.setText(recommendationData.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return recommendationData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_type;
        RoundedImageView img_recc;
        TextView txt_tittle, txt_name;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_type = itemView.findViewById(R.id.txt_type);
            img_recc = itemView.findViewById(R.id.img_recc);
            txt_tittle = itemView.findViewById(R.id.txt_tittle);
            txt_name = itemView.findViewById(R.id.txt_name);

        }
    }


}
