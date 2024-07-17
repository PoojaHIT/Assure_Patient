package com.hit.assure.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cuneytayyildiz.gestureimageview.GestureImageView;
import com.hit.assure.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private int position = 0;
    String images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Window window = this.getWindow();
        View decorView = this.getWindow().getDecorView();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        String imagesList;
        ViewPager imagesViewPager = findViewById(R.id.imagesViewPager);

        if (getIntent() != null) {

            imagesList = getIntent().getStringExtra("img");
            position = getIntent().getIntExtra("position", 0);
            if (!imagesList.isEmpty()) {
                GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(this, Collections.singletonList(imagesList));
                imagesViewPager.setAdapter(galleryPagerAdapter);
            }
        }
        imagesViewPager.setCurrentItem(position);
    }

    public class GalleryPagerAdapter extends PagerAdapter {

        private Context mContext;
        private LayoutInflater inflater;
        private List<String> projectList;

        public GalleryPagerAdapter(Context context, List<String> projectList) {
            this.mContext = context;
            inflater = LayoutInflater.from(context);
            this.projectList = projectList;
        }

        @Override
        public int getCount() {
            return projectList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = inflater.inflate(R.layout.layout_gallery, container, false);
            GestureImageView sliderImageView = itemView.findViewById(R.id.gestureImageView);
            TextView txtImgCount = itemView.findViewById(R.id.txtImgCount);
            int ImgPos = position + 1;
            txtImgCount.setText("" + ImgPos + "/" + projectList.size());
            if (!projectList.get(position).isEmpty()) {

                Picasso.get().load(projectList.get(position)).into(sliderImageView);
            }
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((RelativeLayout) object);
        }

    }

}