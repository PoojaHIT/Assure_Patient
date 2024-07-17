package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.BLOGDETAILS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hit.assure.Model.BlogDetails.BlogDetailsData;
import com.hit.assure.Model.BlogDetails.BlogDetailsResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity implements ServerResponse {

    private TextView txt_type, txt_tittle, txt_name_author, txt_hour, txt_para, txt_para_two;
    private ImageView round_image;
    private BlogDetailsData blogDetailsData;
    private String airtleId = "";
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        if (getIntent() != null) {
            airtleId = getIntent().getStringExtra("airtleId");
        }

        init();

        new Requestor(BLOGDETAILS, this).getBolgDetails(airtleId);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init() {
        img_back = findViewById(R.id.img_back);
        txt_type = findViewById(R.id.txt_type);
        txt_tittle = findViewById(R.id.txt_tittle);
        txt_name_author = findViewById(R.id.txt_name_author);
        txt_hour = findViewById(R.id.txt_hour);
        txt_para = findViewById(R.id.txt_para);
        txt_para_two = findViewById(R.id.txt_para_two);
        round_image = findViewById(R.id.round_image);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case BLOGDETAILS:
                BlogDetailsResponse blogDetailsResponse = (BlogDetailsResponse) o;
                if (blogDetailsResponse.getResult().equalsIgnoreCase("true")) {
                    blogDetailsData = blogDetailsResponse.getBlogDetailsData();

                    if (blogDetailsData.getCategory_id().equalsIgnoreCase("1")) {
                        txt_type.setText("Skin Care");
                    } else if (blogDetailsData.getCategory_id().equalsIgnoreCase("2")) {
                        txt_type.setText("Hair Care");
                    }
                    txt_tittle.setText(blogDetailsData.getArticle_title());
                    txt_para.setText(blogDetailsData.getArticle_description());
                    Picasso.get()
                            .load(blogDetailsData.getArticle_image())
                            .fit()
                            .into(round_image);


                }
        }
    }

    @Override
    public void error(Object o, int code) {
    }
}