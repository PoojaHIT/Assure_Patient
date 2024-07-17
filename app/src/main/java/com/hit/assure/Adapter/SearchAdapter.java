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
import com.hit.assure.Model.Search.SearchData;
import com.hit.assure.R;
import com.hit.assure.Util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private List<SearchData> searchData;


    public SearchAdapter(Context mContext, List<SearchData> searchData) {
        this.mContext = mContext;
        this.searchData = searchData;
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

        holder.txt_product_title.setText(searchData.get(position).getPbname());
        holder.txt_product_price.setText(searchData.get(position).getPprice() + " $");

        Picasso.get()
                .load(searchData.get(position).getPimg())
                .fit()
                .into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return searchData.size();
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
