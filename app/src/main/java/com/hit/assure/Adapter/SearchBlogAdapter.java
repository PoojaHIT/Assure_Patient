package com.hit.assure.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.BlogDetailsActivity;
import com.hit.assure.Model.Category.CategoryData;
import com.hit.assure.Model.SearchBlog.SearchBlogData;
import com.hit.assure.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchBlogAdapter extends RecyclerView.Adapter<SearchBlogAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<SearchBlogData> searchBlogData;

    public SearchBlogAdapter(Context mContext, List<SearchBlogData> searchBlogData) {
        this.mContext = mContext;
        this.searchBlogData = searchBlogData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_blog, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txt_tittle.setText(searchBlogData.get(position).getArticle_title());
        Picasso.get()
                .load(searchBlogData.get(position).getArticle_image())
                .fit()
                .into(holder.img_recc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BlogDetailsActivity.class).putExtra("airtleId", searchBlogData.get(position).getArticle_id()));
            }
        });



    }

    @Override
    public int getItemCount() {
        return searchBlogData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView img_recc;
        TextView txt_tittle;

        public ViewHolder(View itemView) {
            super(itemView);

            img_recc = itemView.findViewById(R.id.img_recc);
            txt_tittle = itemView.findViewById(R.id.txt_tittle);

        }
    }


}
