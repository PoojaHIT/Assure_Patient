package com.hit.assure.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceServices {

    private static final String PREFS_NAME = "ScienceVeda";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static PreferenceServices mSingleton = new PreferenceServices();

    private static final String user_id = "user_id";
    private static final String user_name = "user_name";
    private static final String userLastName = "userLastName";
    private static final String user_email = "user_email";
    private static final String user_mobile = "user_mobile";
    private static final String userRole = "userRole";
    private static final String userToken = "userToken";
    private static final String userValidity = "userValidity";
    private static final String user_location = "user_location";
    private static final String user_lat = "user_lat";
    private static final String user_long = "user_long";
    private static final String application_form = "application_form";
    private static final String skin_consultation_application_form = "skin_consultation_application_form";
    private static final String nutrition_consultation_application_form = "nutrition_consultation_application_form";
    private static final String categoryID = "categoryID";
    private static final String user_token = "user_token";

    private PreferenceServices() {
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static PreferenceServices instance() {
        return mSingleton;
    }

    public static PreferenceServices getInstance() {
        return mSingleton;
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getUser_location() {
        return getPrefs().getString(user_location, "0");
    }

    public void setUser_location(String location) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_location, location);
        editor.apply();
    }

    public String getUser_lat() {
        return getPrefs().getString(user_lat, "0");
    }

    public void setUser_lat(String lat) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_lat, lat);
        editor.apply();
    }

    public String getUser_long() {
        return getPrefs().getString(user_long, "0");
    }

    public void setUser_long(String user_longg) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_long, user_longg);
        editor.apply();
    }

    public String getApplication_form() {
        return getPrefs().getString(application_form, "");
    }

    public void setApplication_form(String form) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(application_form, form);
        editor.apply();
    }

    public String getSkin_consultation_application_form() {
        return getPrefs().getString(skin_consultation_application_form, "");
    }

    public void setSkin_consultation_application_form(String skin) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(skin_consultation_application_form, skin);
        editor.apply();
    }

    public String getNutrition_consultation_application_form() {
        return getPrefs().getString(nutrition_consultation_application_form, "");
    }

    public void setNutrition_consultation_application_form(String nutrition) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(nutrition_consultation_application_form, nutrition);
        editor.apply();
    }

    public String getCategoryID() {
        return getPrefs().getString(categoryID, "");
    }

    public void setCategoryID(String catid) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(categoryID, catid);
        editor.apply();
    }

    public String getUser_id() {
        return getPrefs().getString(user_id, "");
    }

    public void setUser_id(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_id, id);
        editor.apply();
    }

    public String getUser_name() {
        return getPrefs().getString(user_name, "");
    }

    public void setUser_name(String pass) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_name, pass);
        editor.commit();
    }

    public String getUserLastName() {
        return getPrefs().getString(userLastName, "");
    }

    public void setUserLastName(String pass) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(userLastName, pass);
        editor.commit();
    }

    public String getUser_email() {
        return getPrefs().getString(user_email, "");
    }

    public void setUser_email(String pass) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_email, pass);
        editor.commit();
    }

    public String getUser_mobile() {
        return getPrefs().getString(user_mobile, "");
    }

    public void setUser_mobile(String mobile) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_mobile, mobile);
        editor.commit();
    }

    public String getUserRole() {
        return getPrefs().getString(userRole, "");
    }

    public void setUserRole(String role) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(userRole, role);
        editor.commit();
    }

    public String getUserToken() {
        return getPrefs().getString(userToken, "");
    }

    public void setUserToken(String token) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(userToken, token);
        editor.commit();
    }

    public String getUserValidity() {
        return getPrefs().getString(userValidity, "");
    }

    public void setUserValidity(String validity) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(userValidity, validity);
        editor.commit();
    }


    public String getUser_token() {
        return getPrefs().getString(user_token, "");
    }

    public void setUser_token(String token) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(user_token, token);
        editor.apply();
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.clear();
        editor.apply();
    }

}
