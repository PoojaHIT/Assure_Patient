package com.hit.assure.Adapter;



import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Model.Category.CategoryData;
import com.hit.assure.R;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<CategoryData> categoryData;

    public CategoryAdapter(Context mContext, List<CategoryData> categoryData) {
        this.mContext = mContext;
        this.categoryData = categoryData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_categories, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_categories.setText(categoryData.get(position).getCat_name());

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView txt_categories;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_categories = itemView.findViewById(R.id.txt_categories);

        }
    }


}
