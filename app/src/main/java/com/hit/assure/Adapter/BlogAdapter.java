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
import com.hit.assure.Model.Blog.BlogData;
import com.hit.assure.Model.Category.CategoryData;
import com.hit.assure.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<BlogData> blogData;

    public BlogAdapter(Context mContext, List<BlogData> blogData) {
        this.mContext = mContext;
        this.blogData = blogData;
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

//

//        if (blogData.get(position).getCategory_id().equalsIgnoreCase("1")){
//            holder.txt_type.setText("Skin Care");
//
//        }else if (blogData.get(position).getCategory_id().equalsIgnoreCase("2")){
//            holder.txt_type.setText("Hair Care");
//        }

//        holder.txt_name_author.setText(blogData.get(position).getAuthor());
//        holder.txt_hours.setText(blogData.get(position).getArticle_date());
        holder.txt_tittle.setText(blogData.get(position).getArticle_title());

        Picasso.get()
                .load(blogData.get(position).getArticle_image())
                .fit()
                .into(holder.img_recc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BlogDetailsActivity.class)
                        .putExtra("airtleId", blogData.get(position).getArticle_id())
                );
            }
        });


    }

    @Override
    public int getItemCount() {
        return blogData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView txt_type,txt_tittle,txt_name_author,txt_hours;
        ImageView img_recc;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_type = itemView.findViewById(R.id.txt_type);
            txt_tittle = itemView.findViewById(R.id.txt_tittle);
            txt_name_author = itemView.findViewById(R.id.txt_name_author);
            txt_hours = itemView.findViewById(R.id.txt_hours);
            img_recc = itemView.findViewById(R.id.img_recc);

        }
    }


}
