package com.hit.assure.Fragment;

import static com.hit.assure.Retrofit.ServerCode.CATEGORIES;
import static com.hit.assure.Retrofit.ServerCode.PRODUCTLIST;
import static com.hit.assure.Retrofit.ServerCode.SEARCH;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.SearchActivity;
import com.hit.assure.Adapter.CategoryAdapter;
import com.hit.assure.Adapter.PrductsListingAdapter;
import com.hit.assure.Adapter.SearchAdapter;
import com.hit.assure.Model.Category.CategoryData;
import com.hit.assure.Model.Category.CategoryResponse;
import com.hit.assure.Model.ProductList.ProductListData;
import com.hit.assure.Model.ProductList.ProductListResponse;
import com.hit.assure.Model.Search.SearchData;
import com.hit.assure.Model.Search.SearchResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.ServerResponse;

import java.util.List;

public class SearchFragment extends Fragment implements ServerResponse {
    private RecyclerView recyclerCategory, recycler_product, rec_search;
    private List<CategoryData> categoryData;
    private List<ProductListData> productListData;
    private EditText edt_search;

    private String lat = "19.207237";
    private String loong = "72.834824";
    private RelativeLayout rl_search;
    private List<SearchData> searchData;
    private ImageView img_search;
    private TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        new Requestor(CATEGORIES, this).getCategory();
//        new Requestor(PRODUCTLIST, this).getProductList();
        init(view);
        return view;
    }

    public void init(View view) {

        rl_search = view.findViewById(R.id.rl_search);
        txt = view.findViewById(R.id.txt);
        rec_search = view.findViewById(R.id.rec_search);
        LinearLayoutManager mLineer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rec_search.setLayoutManager(mLineer);
        rec_search.setHasFixedSize(true);
        rec_search.setNestedScrollingEnabled(false);


        recyclerCategory = view.findViewById(R.id.recyclerCategory);
//        LinearLayoutManager mLiner = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
//        recyclerCategory.setLayoutManager(mLiner);
//        recyclerCategory.setHasFixedSize(true);
//        recyclerCategory.setNestedScrollingEnabled(false);

        recycler_product = view.findViewById(R.id.recycler_product);
        LinearLayoutManager mLinerr = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_product.setLayoutManager(mLinerr);
        recycler_product.setHasFixedSize(true);
        recycler_product.setNestedScrollingEnabled(false);

        img_search = view.findViewById(R.id.img_search);
//        edt_search = view.findViewById(R.id.edt_search);

//        img_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadSearch(edt_search.getText().toString());
//            }
//        });
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class)
                        .putExtra("lat", lat)
                        .putExtra("loong", loong));
            }
        });


    }


//    void loadSearch(String keyword){
//        new Requestor(SEARCH, this).getSearchResult(keyword);
//    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.iv_home_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_home.setVisibility(View.GONE);
        HomeActivity.iv_search.setVisibility(View.VISIBLE);
        HomeActivity.iv_search_unselected.setVisibility(View.GONE);
        HomeActivity.iv_product.setVisibility(View.GONE);
        HomeActivity.iv_product_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_chat.setVisibility(View.GONE);
        HomeActivity.iv_chat_unselected.setVisibility(View.VISIBLE);
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case CATEGORIES:
                CategoryResponse categoryResponse = (CategoryResponse) o;
                if (categoryResponse.getStatus() == 200) {
                    categoryData = categoryResponse.getCategoryData();
                    CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryData);
                    recyclerCategory.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(getActivity(), "" + categoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case PRODUCTLIST:
                ProductListResponse productListResponse = (ProductListResponse) o;
                if (productListResponse.getStatus() == 200) {
                    productListData = productListResponse.getProductListData();
                    PrductsListingAdapter prductsListingAdapter = new PrductsListingAdapter(getActivity(), productListData);
                    recycler_product.setAdapter(prductsListingAdapter);

                } else {
                    Toast.makeText(getActivity(), "" + productListResponse, Toast.LENGTH_SHORT).show();
                }
                break;

            case SEARCH:
                SearchResponse searchResponse = (SearchResponse) o;
                if (searchResponse.getStatus() == 200) {
                    searchData = searchResponse.getSearchData();
                    SearchAdapter searchAdapter = new SearchAdapter(getActivity(), searchData);
                    rec_search.setVisibility(View.VISIBLE);
                    recyclerCategory.setVisibility(View.GONE);
                    recycler_product.setVisibility(View.GONE);
                    txt.setVisibility(View.GONE);

                    rec_search.setAdapter(searchAdapter);


                } else {
                    Toast.makeText(getActivity(), "" + searchResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    public void error(Object o, int code) {

    }
}
