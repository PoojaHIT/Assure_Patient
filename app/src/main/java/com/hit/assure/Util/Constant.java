package com.hit.assure.Util;

import com.hit.assure.Retrofit.ServerCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant implements ServerCode {

    public interface TimeOut {
        int SOCKET_TIME_OUT = 60;
        int CONNECTION_TIME_OUT = 60;
    }

    public interface UrlPath {
        String BASE_URL = "http://3.111.84.237/index.php/";
        String BASE_URL_MXFACE = "https://faceapi.mxface.ai/";
        String BASE_URL_VIDEOCL = "https://api.whereby.dev/v1/";
        String USER_IMAGE = "http://3.111.84.237/uploads/user_image/";
        String PRODUCT_IMAGE = "http://3.111.84.237/index.php/admin/admin/medicalmall/uploads/product/";
        String TOPBANNER = "http://3.111.84.237/index.php/admin/admin/medicalmall/uploads/banner/";
    }

    public static final String searchProducts = UrlPath.BASE_URL + "listingSearch";
    public static final String catListingSearch = UrlPath.BASE_URL + "catListingSearch";
    public static final String getSubCategoryFilter = UrlPath.BASE_URL + "getSubCategoryFilter";

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean validateLetters(String target) {
        if (target == null) {
            return false;
        } else {
            return target.matches("[a-zA-Z.? ]*");
        }
    }

    public static boolean validateGST(String trim) {
        //Pattern pattern = Pattern.compile("^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$");
        Pattern pattern = Pattern.compile("[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[0-9]{1}[a-zA-Z]{1}[0-9 a-zA-Z]{1}");
        Matcher matcher = pattern.matcher(trim);

        return matcher.matches();
    }

}
