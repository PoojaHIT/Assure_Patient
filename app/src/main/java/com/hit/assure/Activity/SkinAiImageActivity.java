package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hit.assure.R;
import com.hit.assure.Suggestions.Question_Answer_Get_Set;
import com.hit.assure.Variables;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SkinAiImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_user, img_back;
    private LinearLayout ll_acne,ll_face_wrinkle,ll_eye_wrinkle,ll_crowsfeet,ll_skin_dullness,ll_uneven_skin,ll_darkspot,LL_strenght,ll_darkcircle,ll_check_again,ll_continue;
    private String acne_image, face_wrinkle_image,eye_wrinkle_image,crowsfeet_image,skin_dullness_image,uneven_skin_image,darkspot_image,darkcircle_image,strenght_image;
    private String strImagePath;
    private TextView txt_acne,txt_face_wrinkle,txtt_eye_wrinkle,txt_crowsfeet,txt_skin_dullness,txt_uneven_skin,txt_dark_circle,txt_dark_spot,txt_strenght;
    private String onlyVI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_ai_image);

        Variables.sharedPreferences=getSharedPreferences(Variables.pref,MODE_PRIVATE);

        init();

        if(getIntent() != null){


            acne_image = getIntent().getStringExtra("acne");
            face_wrinkle_image = getIntent().getStringExtra("facewrinkle");
            eye_wrinkle_image = getIntent().getStringExtra("eyewrinkle");
            crowsfeet_image = getIntent().getStringExtra("crowsfeet");
            skin_dullness_image = getIntent().getStringExtra("skindullness");
            darkspot_image = getIntent().getStringExtra("darkspot");
            darkcircle_image = getIntent().getStringExtra("darkcircle");
            strenght_image = getIntent().getStringExtra("strenght");
            uneven_skin_image = getIntent().getStringExtra("uneven");
            strImagePath = getIntent().getStringExtra("image");
            onlyVI = getIntent().getStringExtra("onlyVI");

            Picasso.get()
                    .load(strImagePath)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);

        }
        Call_Api_to_answers();


    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    public void init(){
        img_back = findViewById(R.id.img_back);
        txt_acne = findViewById(R.id.txt_acne);
        txt_face_wrinkle = findViewById(R.id.txt_face_wrinkle);
        txtt_eye_wrinkle = findViewById(R.id.txtt_eye_wrinkle);
        txt_crowsfeet = findViewById(R.id.txt_crowsfeet);
        txt_skin_dullness = findViewById(R.id.txt_skin_dullness);
        txt_uneven_skin = findViewById(R.id.txt_uneven_skin);
        txt_dark_circle = findViewById(R.id.txt_dark_circle);
        txt_dark_spot = findViewById(R.id.txt_dark_spot);
        txt_strenght = findViewById(R.id.txt_strenght);
        ll_check_again = findViewById(R.id.ll_check_again);
        ll_continue = findViewById(R.id.ll_continue);
        ll_continue.setOnClickListener(this);
        ll_check_again.setOnClickListener(this);
        ll_darkcircle = findViewById(R.id.ll_darkcircle);
        ll_darkcircle.setOnClickListener(this);
        img_user = findViewById(R.id.img_user);
        ll_acne = findViewById(R.id.ll_acne);
        ll_uneven_skin = findViewById(R.id.ll_uneven_skin);
        ll_face_wrinkle = findViewById(R.id.ll_face_wrinkle);
        LL_strenght = findViewById(R.id.LL_strenght);
        ll_darkspot = findViewById(R.id.ll_darkspot);
        ll_face_wrinkle.setOnClickListener(this);
        LL_strenght.setOnClickListener(this);
        ll_darkspot.setOnClickListener(this);
        ll_eye_wrinkle = findViewById(R.id.ll_eye_wrinkle);
        ll_crowsfeet = findViewById(R.id.ll_crowsfeet);
        ll_skin_dullness = findViewById(R.id.ll_skin_dullness);
        ll_skin_dullness.setOnClickListener(this);
        ll_crowsfeet.setOnClickListener(this);
        ll_eye_wrinkle.setOnClickListener(this);
        ll_acne.setOnClickListener(this);
        img_back.setOnClickListener(this);
        ll_uneven_skin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        //  switch (v.getId()){

        if(item_id== R.id.ll_acne) {
            txt_acne.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));

            Picasso.get()
                    .load(acne_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.img_back) {
            finish();
        }
        else if(item_id== R.id.ll_eye_wrinkle) {
            txtt_eye_wrinkle.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            Picasso.get()
                    .load(eye_wrinkle_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_face_wrinkle) {
            txt_face_wrinkle.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            Picasso.get()
                    .load(face_wrinkle_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_crowsfeet) {
            txt_crowsfeet.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));

            Picasso.get()
                    .load(crowsfeet_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_uneven_skin) {
            txt_uneven_skin.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            Picasso.get()
                    .load(uneven_skin_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_darkspot) {
            txt_dark_spot.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            Picasso.get()
                    .load(darkspot_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_darkcircle) {
            txt_dark_circle.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            Picasso.get()
                    .load(darkcircle_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.LL_strenght) {
            txt_strenght.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            Picasso.get()
                    .load(strenght_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_check_again) {
            startActivity(new Intent(SkinAiImageActivity.this, UploadSkinActivity.class));
        }
        else if(item_id== R.id.ll_skin_dullness) {
            txt_skin_dullness.setTextColor(getResources().getColor(R.color.black));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_eye_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_face_wrinkle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_crowsfeet.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_uneven_skin.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkspot.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_darkcircle.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            LL_strenght.setBackground(getResources().getDrawable(R.drawable.bg_black_stroke_rounded));
            ll_skin_dullness.setBackground(getResources().getDrawable(R.drawable.bg_white_stroke_rounded));
            Picasso.get()
                    .load(skin_dullness_image)
                    .placeholder(R.drawable.loading_skin)
                    .fit()
                    .into(img_user);
        }
        else if(item_id== R.id.ll_continue){
            if(Variables.sharedPreferences.getString(Variables.q_and_a,"").equals("")){
                Toast.makeText(SkinAiImageActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
            }else {
                open_chat();
            }




        }
    }
    private void Call_Api_to_answers() {
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.skincare, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d("responce",respo);
                        save_data(respo);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo",error.toString());
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonObjectRequest);
    }

    public void save_data(String responce){
        try {
            JSONObject jsonObject=new JSONObject(responce);
            String code=jsonObject.optString("code");
            if(code.equals("200")){

                JSONArray msg=jsonObject.getJSONArray("msg");
                Map<String, Question_Answer_Get_Set> map=new HashMap();
                for(int i=0; i<msg.length();i++){
                    JSONObject data=msg.optJSONObject(i);
                    Question_Answer_Get_Set item=new Question_Answer_Get_Set();
                    item.id=data.optString("id");
                    item.question=data.optString("question");
                    item.answer=data.optString("answers");
                    item.level=data.optString("level");

                    map.put(data.optString("question"),item);
                }

                Save_Q_A(map);

            }
            else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Save_Q_A(Map<String,Question_Answer_Get_Set> map) {
        SharedPreferences.Editor editor = Variables.sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        editor.putString(Variables.q_and_a,json);
        Log.e("checka_fajoi",json);
        Log.e("checka_fajoi","json");
        editor.apply();
    }


    public void open_chat(){
        Intent intent=new Intent(this,ChatActivity.class).putExtra("cat","1").putExtra("onlyVI", onlyVI);
        startActivity(intent);
    }
}