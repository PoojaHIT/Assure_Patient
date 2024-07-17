package com.hit.assure.Fragment;

import static android.content.Context.MODE_PRIVATE;


import static com.hit.assure.Activity.HomeActivity.drawerLayout;
import static com.hit.assure.Activity.HomeActivity.llDrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hit.assure.Activity.ChatActivity;
import com.hit.assure.Activity.DietActivity;
import com.hit.assure.Activity.HomeActivity;
import com.hit.assure.Activity.NotificationActivity;
import com.hit.assure.Activity.SkinAiActivity;
import com.hit.assure.Activity.SkinAiImageActivity;
import com.hit.assure.Activity.UploadSkinActivity;
import com.hit.assure.R;
import com.hit.assure.Suggestions.Question_Answer_Get_Set;
import com.hit.assure.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatFragment extends Fragment implements View.OnClickListener {

    private TextView txt_select, txt1, txt2, txt3, txt4, txt5, txt6;
    public LinearLayout ll_dry_skin, ll_oily_skin, ll_hairdfall, ll_sclap, ll_skin_analyse;
    private String selected_value;
    String catOne = "";
    String cateTwo = "";
    private String cat = "";
    private String onlyVI = "1";
    private RelativeLayout rl_hamburger;
    private ProgressDialog progressDialog;
    private ImageView img_notification;
    private LinearLayout ll_acne, ll_other;
    private RelativeLayout rl_nutritional;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Variables.sharedPreferences = getActivity().getSharedPreferences(Variables.pref, MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        init(view);
//        if (catOne.equals("skincare")){
//            Call_Api_to_answers_skincare();
//        }else if (cateTwo.equals("haircare")){
//            Call_Api_to_answers_haircare();
//        }else {
//            Call_Api_to_answers();
//        }
        if (getArguments() != null) {

            onlyVI = getArguments().getString("onlyVI");
        }


        return view;
    }

    public void init(View view) {

        img_notification = view.findViewById(R.id.img_notification);

        rl_nutritional = view.findViewById(R.id.rl_nutritional);

        txt1 = view.findViewById(R.id.txt1);
        txt2 = view.findViewById(R.id.txt2);
        txt3 = view.findViewById(R.id.txt3);
        txt4 = view.findViewById(R.id.txt4);
        txt5 = view.findViewById(R.id.txt5);
        txt6 = view.findViewById(R.id.txt6);


        ll_dry_skin = view.findViewById(R.id.ll_dry_skin);
        rl_hamburger = view.findViewById(R.id.rl_hamburger);
        ll_oily_skin = view.findViewById(R.id.ll_oily_skin);
        ll_hairdfall = view.findViewById(R.id.ll_hairdfall);
        ll_sclap = view.findViewById(R.id.ll_sclap);
        ll_acne = view.findViewById(R.id.ll_acne);
        ll_other = view.findViewById(R.id.ll_other);
        //       ll_skin_analyse = view.findViewById(R.id.ll_skin_analyse);


        rl_hamburger.setOnClickListener(this);
        ll_dry_skin.setOnClickListener(this);
        ll_oily_skin.setOnClickListener(this);
        ll_hairdfall.setOnClickListener(this);
        ll_sclap.setOnClickListener(this);
        ll_acne.setOnClickListener(this);
        ll_other.setOnClickListener(this);
        rl_nutritional.setOnClickListener(this);
        img_notification.setOnClickListener(this);
//        ll_skin_analyse.setOnClickListener(this);

        //  txt_select = view.findViewById(R.id.txt_select);
//        txt_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(Variables.sharedPreferences.getString(Variables.q_and_a,"").equals("")){
//                    Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
//                }else {
//                    open_chat();
//                }
//
//            }
//        });


    }

    private void Call_Api_to_answers() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.bootChat, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo = response.toString();
                        Log.d("responce", respo);
                        save_data(respo);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo", error.toString());

                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonObjectRequest);
    }

    public void save_data(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String code = jsonObject.optString("code");
            if (code.equals("200")) {

                JSONArray msg = jsonObject.getJSONArray("msg");
                Map<String, Question_Answer_Get_Set> map = new HashMap();
                for (int i = 0; i < msg.length(); i++) {
                    JSONObject data = msg.optJSONObject(i);
                    Question_Answer_Get_Set item = new Question_Answer_Get_Set();
                    item.id = data.optString("id");
                    item.question = data.optString("question");
                    item.answer = data.optString("answers");
                    item.level = data.optString("level");

                    map.put(data.optString("question"), item);
                }

                Save_Q_A(map);

            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void Call_Api_to_answers_skincare() {
        showProgressDialog();
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.skincare, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String respo = response.toString();
                        Log.d("responcerihsifaw", respo);
                        save_dataa(respo);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo", error.toString());
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonObjectRequest);
    }

    public void save_dataa(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String code = jsonObject.optString("code");
            if (code.equals("200")) {
                hideProgressDialog();
                open_chat();
                JSONArray msg = jsonObject.getJSONArray("msg");
                Map<String, Question_Answer_Get_Set> map = new HashMap();
                for (int i = 0; i < msg.length(); i++) {
                    JSONObject data = msg.optJSONObject(i);
                    Question_Answer_Get_Set item = new Question_Answer_Get_Set();
                    item.id = data.optString("id");
                    item.question = data.optString("question");
                    item.answer = data.optString("answers");
                    item.level = data.optString("level");
                    map.put(data.optString("question"), item);
                }

                Save_Q_A(map);

            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void Call_Api_to_answers_haircare() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.haircare, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo = response.toString();
                        Log.d("responce", respo);
                        save_datai(respo);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo", error.toString());

                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonObjectRequest);
    }

    public void save_datai(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String code = jsonObject.optString("code");
            if (code.equals("200")) {
                open_chat();
                JSONArray msg = jsonObject.getJSONArray("msg");
                Map<String, Question_Answer_Get_Set> map = new HashMap();
                for (int i = 0; i < msg.length(); i++) {

                    JSONObject data = msg.optJSONObject(i);
                    Question_Answer_Get_Set item = new Question_Answer_Get_Set();
                    item.id = data.optString("id");
                    item.question = data.optString("question");
                    item.answer = data.optString("answers");
                    item.level = data.optString("level");
                    map.put(data.optString("question"), item);

                }

                Save_Q_A(map);

            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void Save_Q_A(Map<String, Question_Answer_Get_Set> map) {
        SharedPreferences.Editor editor = Variables.sharedPreferences.edit();
        editor.clear();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        editor.putString(Variables.q_and_a, json);
        Log.e("checka_fajoi", json);
        Log.e("checka_fajoi", "json");
        editor.apply();
    }


    public void open_chat() {
        Intent intent = new Intent(getActivity(), ChatActivity.class)
                .putExtra("value", selected_value)
                .putExtra("category", catOne)
                .putExtra("cat", cat)
                .putExtra("onlyVI", onlyVI);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.iv_home_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_home.setVisibility(View.GONE);
        HomeActivity.iv_search.setVisibility(View.GONE);
        HomeActivity.iv_search_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_product.setVisibility(View.GONE);
        HomeActivity.iv_product_unselected.setVisibility(View.VISIBLE);
        HomeActivity.iv_chat.setVisibility(View.VISIBLE);
        HomeActivity.iv_chat_unselected.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        int item_id=v.getId();
        // switch (v.getId()){
        if(item_id== R.id.ll_acne) {
            txt1.setTextColor(getResources().getColor(R.color.black));
            txt2.setTextColor(getResources().getColor(R.color.black));
            txt3.setTextColor(getResources().getColor(R.color.black));
            txt4.setTextColor(getResources().getColor(R.color.black));
            txt5.setTextColor(getResources().getColor(R.color.white));
            txt6.setTextColor(getResources().getColor(R.color.black));
            ll_dry_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_oily_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_hairdfall.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_sclap.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_analyse_orange_rounded));
            ll_other.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            selected_value = "Acne";
            catOne = "skincare";
            cat = "1";

            if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            } else {
                Call_Api_to_answers_skincare();

            }
            Toast.makeText(getActivity(), "" + selected_value, Toast.LENGTH_SHORT).show();
        }
        else if(item_id== R.id.ll_other) {
            txt1.setTextColor(getResources().getColor(R.color.black));
            txt2.setTextColor(getResources().getColor(R.color.black));
            txt3.setTextColor(getResources().getColor(R.color.black));
            txt4.setTextColor(getResources().getColor(R.color.black));
            txt5.setTextColor(getResources().getColor(R.color.black));
            txt6.setTextColor(getResources().getColor(R.color.white));
            ll_dry_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_oily_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_hairdfall.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_sclap.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_other.setBackground(getResources().getDrawable(R.drawable.bg_analyse_orange_rounded));
            selected_value = "Other";
            catOne = "skincare";
            cat = "1";

            if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            } else {
                Call_Api_to_answers_skincare();

            }
            Toast.makeText(getActivity(), "" + selected_value, Toast.LENGTH_SHORT).show();
        }
        else if(item_id== R.id.ll_dry_skin) {
            txt1.setTextColor(getResources().getColor(R.color.white));
            txt2.setTextColor(getResources().getColor(R.color.black));
            txt3.setTextColor(getResources().getColor(R.color.black));
            txt4.setTextColor(getResources().getColor(R.color.black));
            txt5.setTextColor(getResources().getColor(R.color.black));
            txt6.setTextColor(getResources().getColor(R.color.black));
            ll_dry_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_orange_rounded));
            ll_oily_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_hairdfall.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_sclap.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_other.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            selected_value = "Dry Skin";
            catOne = "skincare";
            cat = "1";

            if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            } else {
                Call_Api_to_answers_skincare();
            }
            Toast.makeText(getActivity(), "" + selected_value, Toast.LENGTH_SHORT).show();
        }
        else if(item_id== R.id.ll_oily_skin) {
            txt2.setTextColor(getResources().getColor(R.color.white));
            txt1.setTextColor(getResources().getColor(R.color.black));
            txt3.setTextColor(getResources().getColor(R.color.black));
            txt4.setTextColor(getResources().getColor(R.color.black));
            txt5.setTextColor(getResources().getColor(R.color.black));
            txt6.setTextColor(getResources().getColor(R.color.black));
            ll_oily_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_orange_rounded));
            ll_dry_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_hairdfall.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_sclap.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_other.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            selected_value = "Oily Skin";
            catOne = "skincare";
            cat = "1";

            if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            } else {
                Call_Api_to_answers_skincare();

            }
            Toast.makeText(getActivity(), "" + selected_value, Toast.LENGTH_SHORT).show();
        }
        else if(item_id==  R.id.ll_hairdfall) {

            txt3.setTextColor(getResources().getColor(R.color.white));
            txt2.setTextColor(getResources().getColor(R.color.black));
            txt1.setTextColor(getResources().getColor(R.color.black));
            txt4.setTextColor(getResources().getColor(R.color.black));
            txt5.setTextColor(getResources().getColor(R.color.black));
            txt6.setTextColor(getResources().getColor(R.color.black));
            ll_hairdfall.setBackground(getResources().getDrawable(R.drawable.bg_analyse_orange_rounded));
            ll_dry_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_oily_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_sclap.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_other.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            selected_value = "Hairfall";
            cateTwo = "haircare";
            cat = "2";

            if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            } else {
                Call_Api_to_answers_haircare();

            }
            Toast.makeText(getActivity(), "" + selected_value, Toast.LENGTH_SHORT).show();
        }
        else if(item_id== R.id.ll_sclap) {
            txt4.setTextColor(getResources().getColor(R.color.white));
            txt2.setTextColor(getResources().getColor(R.color.black));
            txt3.setTextColor(getResources().getColor(R.color.black));
            txt1.setTextColor(getResources().getColor(R.color.black));
            txt5.setTextColor(getResources().getColor(R.color.black));
            txt6.setTextColor(getResources().getColor(R.color.black));
            ll_sclap.setBackground(getResources().getDrawable(R.drawable.bg_analyse_orange_rounded));
            ll_dry_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_hairdfall.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_oily_skin.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_acne.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            ll_other.setBackground(getResources().getDrawable(R.drawable.bg_analyse_rounded));
            selected_value = "Sclap";
            cateTwo = "haircare";
            cat = "2";

            if (Variables.sharedPreferences.getString(Variables.q_and_a, "").equals("")) {
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            } else {
                Call_Api_to_answers_haircare();

            }
            Toast.makeText(getActivity(), "" + selected_value, Toast.LENGTH_SHORT).show();
        }
        else if(item_id== R.id.rl_hamburger) {
            drawerLayout.openDrawer(llDrawerLayout);
        }
        //  }
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
