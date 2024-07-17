package com.hit.assure.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.R;

public class ProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.iv_home.setImageResource(R.drawable.unselected_home);
        HomeActivity.iv_search.setImageResource(R.drawable.unselected_search);
        HomeActivity.iv_product.setImageResource(R.drawable.selected_product);
        HomeActivity.iv_chat.setImageResource(R.drawable.unselected_chat);
    }
}
