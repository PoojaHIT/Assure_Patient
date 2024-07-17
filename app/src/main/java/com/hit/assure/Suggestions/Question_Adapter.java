package com.hit.assure.Suggestions;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.hit.assure.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AQEEL on 3/20/2018.
 */

public class Question_Adapter extends RecyclerView.Adapter<Question_Adapter.CustomViewHolder >{

    public Context context;
    List<String> datalist = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public Question_Adapter(Context context, List<String> urllist, OnItemClickListener listener) {
        this.context = context;
        this.datalist=urllist;
        this.listener = listener;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question_hint_layout,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
       return datalist.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView question_txt;

        public CustomViewHolder(View view) {
            super(view);
            question_txt=view.findViewById(R.id.question_txt);
        }

        public void bind(final String item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }

    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int i) {
        holder.bind(datalist.get(i),listener);
        holder.question_txt.setText(datalist.get(i));
   }

}