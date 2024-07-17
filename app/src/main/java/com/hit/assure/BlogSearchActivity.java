package com.hit.assure;

import static com.hit.assure.Retrofit.ServerCode.BLOGSEARCH;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.Adapter.SearchBlogAdapter;
import com.hit.assure.Model.SearchBlog.SearchBlogData;
import com.hit.assure.Model.SearchBlog.SearchBlogResponse;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;

import java.util.List;

public class BlogSearchActivity extends AppCompatActivity implements ServerResponse, View.OnClickListener {

    private EditText edt_search;
    private ImageView img_search;
    private RecyclerView recyclerSearchBlog;
    private String articleId;
    private String page = "1";
    private List<SearchBlogData> searchBlogData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_search);


        init();


    }

    public void init() {
        edt_search = findViewById(R.id.edt_search);
        img_search = findViewById(R.id.img_search);
        recyclerSearchBlog = findViewById(R.id.recyclerSearchBlog);
        recyclerSearchBlog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerSearchBlog.setHasFixedSize(true);
        recyclerSearchBlog.setNestedScrollingEnabled(false);
        img_search.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
       // switch (v.getId()) {
            if(v.getId()== R.id.img_search){
                new Requestor(BLOGSEARCH, this).getBlogSearch(edt_search.getText().toString(), page);

        }
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case BLOGSEARCH:
                SearchBlogResponse searchBlogResponse = (SearchBlogResponse) o;
                if (searchBlogResponse.getResponseCode().equalsIgnoreCase("200")) {
                    searchBlogData = searchBlogResponse.getSearchDataList();
                    if (searchBlogData != null && !searchBlogData.isEmpty()) {
                        SearchBlogAdapter searchBlogAdapter = new SearchBlogAdapter(this, searchBlogData);
                        recyclerSearchBlog.setAdapter(searchBlogAdapter);
                    } else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "" + searchBlogResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
        }


    }

    @Override
    public void error(Object o, int code) {

    }


}