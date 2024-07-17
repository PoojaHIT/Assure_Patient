package com.hit.assure.Retrofit;

import android.text.AutoText;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.hit.assure.Model.ActiveAppointments.ActiveAppointmentsResponse;
import com.hit.assure.Model.Blog.BlogResponse;
import com.hit.assure.Model.BlogDetails.BlogDetailsResponse;
import com.hit.assure.Model.BookAppointment.BookAppointmentResponse;
import com.hit.assure.Model.CancelAppointment.CancelAppointmentResponse;
import com.hit.assure.Model.Category.CategoryResponse;
import com.hit.assure.Model.Centre.CentreResponse;
import com.hit.assure.Model.CheckmobilenumberResponse;
import com.hit.assure.Model.CityList.CityListResponse;
import com.hit.assure.Model.CompletedAppointments.CompletedAppointmentResponse;
import com.hit.assure.Model.ConsultationForm.ConsultationformResponse;
import com.hit.assure.Model.Coupon.ApplyCouponResponse;
import com.hit.assure.Model.CurrentSessionDoctorlist.CurrentSessionDoctorlistResponse;
import com.hit.assure.Model.DoctorSearch.DoctorSearchResponse;
import com.hit.assure.Model.Doctorprofile.DoctorProfileresponse;
import com.hit.assure.Model.InlineBanner.InlineBannerResponse;
import com.hit.assure.Model.InlineBannerTwo.InlineBannerTwoResponse;
import com.hit.assure.Model.LoginWithNumber.SendOtpResponse;
import com.hit.assure.Model.Notification.NotificationResponse;
import com.hit.assure.Model.NotificationCountResponse;
import com.hit.assure.Model.NotificationReadResponse;
import com.hit.assure.Model.OnlyResponse;
import com.hit.assure.Model.OtpVerification.OtpVerificationResponse;
import com.hit.assure.Model.PatientDetailsFrom.PatientDetailsResponse;
import com.hit.assure.Model.ProductList.ProductListResponse;
import com.hit.assure.Model.Rating.RatingResponse;
import com.hit.assure.Model.Recommendation.RecommendationResponse;
import com.hit.assure.Model.Register.RegisterResponse;
import com.hit.assure.Model.ResendOtp.ResendOtpVerification;
import com.hit.assure.Model.Review.ReviewResponse;
import com.hit.assure.Model.ReviewList.ReviewListResponse;
import com.hit.assure.Model.ReviewTwo.ReviewTwoResponse;
import com.hit.assure.Model.Search.SearchResponse;
import com.hit.assure.Model.SearchBlog.SearchBlogResponse;
import com.hit.assure.Model.SocialLogin.SocialLoginResponse;
import com.hit.assure.Model.SocialMedia.SocialMediaResponse;
import com.hit.assure.Model.SubCategory.SubCategoryResponse;
import com.hit.assure.Model.Testimonials.BeforeAfterResponse;
import com.hit.assure.Model.Testimonials.TestimonialResponse;
import com.hit.assure.Model.Testimonials.TestimonialReviewResponse;
import com.hit.assure.Model.TipOfTheDay.TipOfTheBanneResponse;
import com.hit.assure.Model.TopBanner.TopBanner;
import com.hit.assure.Model.UserDetails.UserDetailsResponse;
import com.hit.assure.Model.VcSlotDateData.VcSlotdateDataResponse;
import com.hit.assure.Model.Vcsoltdate.VcSlotdateResponse;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantResponse;
import com.hit.assure.Model.VisitNearByClinic.VistNearByClinicResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class Requestor {

    private final int code;
    private final ServerResponse serverResponse;
    public static APIServices apiServices;
    public static APIServices apiServices_mxface;
    public static APIServices apiServices_videoCl;


    public Requestor(int code, ServerResponse serverResponse) {
        this.code = code;
        this.serverResponse = serverResponse;
        this.apiServices = RetrofitBase.getClient().create(APIServices.class);
        this.apiServices_mxface = RetrofitBase.getClient_mxFace().create(APIServices.class);
        this.apiServices_videoCl = RetrofitBase.getClient_Videocl().create(APIServices.class);
    }

    @NonNull
    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        if (file == null) return null;
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void handleErrorRequest(int code) {
        switch (code) {
            case 400:
                serverResponse.error("No Result Found", this.code);
                break;
            case 404:
                serverResponse.error("Page Not found", this.code);
                break;
            default:
                serverResponse.error("Error", this.code);
                break;
        }
    }


    public void getOtp(String mobile_no, String lat,String token, String loong) {

        apiServices.getOtp(mobile_no, lat, token, loong).enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });


    }

    public void getOtpVerification(String phone, String otp) {
        apiServices.getOtpVerification(phone, otp).enqueue(new Callback<OtpVerificationResponse>() {
            @Override
            public void onResponse(Call<OtpVerificationResponse> call, Response<OtpVerificationResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<OtpVerificationResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });

    }

    public void getResendOtp(String phone) {
        apiServices.getResendOtp(phone).enqueue(new Callback<ResendOtpVerification>() {
            @Override
            public void onResponse(Call<ResendOtpVerification> call, Response<ResendOtpVerification> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ResendOtpVerification> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getUserdata(String user_id) {
        apiServices.getUserdata(user_id).enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getResgister(String full_name, String mobile_no, String agent, String token, String lat, String loong) {
        apiServices.getResgister(full_name, mobile_no, agent, token, lat, loong).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getCheckmobilenumberfor_signup(String mobile_no) {
        apiServices.getCheckmobilenumberfor_signup(mobile_no).enqueue(new Callback<CheckmobilenumberResponse>() {
            @Override
            public void onResponse(Call<CheckmobilenumberResponse> call, Response<CheckmobilenumberResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CheckmobilenumberResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getSociallogin(String oauth_provider, String oauth_uid, String email, String agent, String token, String name, String gender, String lat, String loong) {
        apiServices.getSociallogin(oauth_provider, oauth_uid, email, agent, token, name, gender, lat, loong).enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCategory() {
        apiServices.getCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getProductList() {
        apiServices.getProductList().enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getTopbanner(String city) {

        apiServices.getTopbanner(city).enqueue(new Callback<TopBanner>() {
            @Override
            public void onResponse(Call<TopBanner> call, Response<TopBanner> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<TopBanner> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getSearchResult(String keyword) {
        apiServices.getSearchResult(keyword).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });

    }

    public void getInlineBanner(String city) {

        apiServices.getInlineBanner(city).enqueue(new Callback<InlineBannerResponse>() {
            @Override
            public void onResponse(Call<InlineBannerResponse> call, Response<InlineBannerResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<InlineBannerResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getTipOfTheDay(String city) {

        apiServices.getTipOfTheDay(city).enqueue(new Callback<TipOfTheBanneResponse>() {
            @Override
            public void onResponse(Call<TipOfTheBanneResponse> call, Response<TipOfTheBanneResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<TipOfTheBanneResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getInlineBannerTwo(String city) {

        apiServices.getInlineBannerTwo(city).enqueue(new Callback<InlineBannerTwoResponse>() {
            @Override
            public void onResponse(Call<InlineBannerTwoResponse> call, Response<InlineBannerTwoResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<InlineBannerTwoResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getSubCategory(String cat_id) {

        apiServices.getSubCategory(cat_id).enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getDoctorList(String user_id, String latitude, String longitude, String category_id, String page) {
        apiServices.getDoctorList(user_id, latitude, longitude, category_id, page).enqueue(new Callback<VirtualConsultantResponse>() {
            @Override
            public void onResponse(Call<VirtualConsultantResponse> call, Response<VirtualConsultantResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<VirtualConsultantResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getDoctorProfile(String user_id, String cust_id, String clinic_id, String date) {
        apiServices.getDoctorProfile(user_id, cust_id, clinic_id, date).enqueue(new Callback<DoctorProfileresponse>() {
            @Override
            public void onResponse(Call<DoctorProfileresponse> call, Response<DoctorProfileresponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<DoctorProfileresponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getDoctorSearchList(String user_id, String latitude, String longitude, String page, String keyword) {
        apiServices.getDoctorSearchList(user_id, latitude, longitude, page, keyword).enqueue(new Callback<DoctorSearchResponse>() {
            @Override
            public void onResponse(Call<DoctorSearchResponse> call, Response<DoctorSearchResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<DoctorSearchResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getVcSlotDate(String doctor_id, String clinic_id, String consultation_type) {

        apiServices.getVcSlotDate(doctor_id, clinic_id, consultation_type).enqueue(new Callback<VcSlotdateResponse>() {
            @Override
            public void onResponse(Call<VcSlotdateResponse> call, Response<VcSlotdateResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<VcSlotdateResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getNotificationlist(String user_id, String offset) {

        apiServices.getNotificationlist(user_id, offset).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getVcSlotDateData(String doctor_id, String clinic_id, String consultation_type, String date) {

        apiServices.getVcSlotDateData(doctor_id, clinic_id, consultation_type, date).enqueue(new Callback<VcSlotdateDataResponse>() {
            @Override
            public void onResponse(Call<VcSlotdateDataResponse> call, Response<VcSlotdateDataResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<VcSlotdateDataResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getReviewList(String user_id, String doctor_id) {

        apiServices.getReviewList(user_id, doctor_id).enqueue(new Callback<ReviewListResponse>() {
            @Override
            public void onResponse(Call<ReviewListResponse> call, Response<ReviewListResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ReviewListResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getDoctorlist(String user_id, String category_id) {

        apiServices.getDoctorlist(user_id, category_id).enqueue(new Callback<CurrentSessionDoctorlistResponse>() {
            @Override
            public void onResponse(Call<CurrentSessionDoctorlistResponse> call, Response<CurrentSessionDoctorlistResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CurrentSessionDoctorlistResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getBookAppointment(String user_id, String doctor_id, String clinic_id, String booking_date, String from_time, String to_time, String connect_type, String fee, String transaction_id, String payment_method, String coupon_code, String booking_fee, String coupon_discount_amount, String total_paid_amount) {
        apiServices.getBookAppointment(user_id, doctor_id, clinic_id, booking_date, from_time, to_time, connect_type, fee, transaction_id, payment_method, coupon_code, booking_fee, coupon_discount_amount, total_paid_amount).enqueue(new Callback<BookAppointmentResponse>() {
            @Override
            public void onResponse(Call<BookAppointmentResponse> call, Response<BookAppointmentResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<BookAppointmentResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCity() {
        apiServices.getCity().enqueue(new Callback<CityListResponse>() {
            @Override
            public void onResponse(Call<CityListResponse> call, Response<CityListResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CityListResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getActiveAppointment(String user_id) {
        apiServices.getActiveAppointment(user_id).enqueue(new Callback<ActiveAppointmentsResponse>() {
            @Override
            public void onResponse(Call<ActiveAppointmentsResponse> call, Response<ActiveAppointmentsResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ActiveAppointmentsResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getUserendmeeting(String user_id, String booking_id) {
        apiServices.getUserendmeeting(user_id, booking_id).enqueue(new Callback<OnlyResponse>() {
            @Override
            public void onResponse(Call<OnlyResponse> call, Response<OnlyResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<OnlyResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCheckUserendmeeting(String patient_id, String booking_id) {
        apiServices.getCheckUserendmeeting(patient_id, booking_id).enqueue(new Callback<OnlyResponse>() {
            @Override
            public void onResponse(Call<OnlyResponse> call, Response<OnlyResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<OnlyResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCompletedAppointment(String user_id) {

        apiServices.getCompletedAppointment(user_id).enqueue(new Callback<CompletedAppointmentResponse>() {
            @Override
            public void onResponse(Call<CompletedAppointmentResponse> call, Response<CompletedAppointmentResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CompletedAppointmentResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCoupon(String user_id, String coupon_code, String total_amount, String type) {
        apiServices.getCoupon(user_id, coupon_code, total_amount, type).enqueue(new Callback<ApplyCouponResponse>() {
            @Override
            public void onResponse(Call<ApplyCouponResponse> call, Response<ApplyCouponResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ApplyCouponResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getReviewTwo(String user_id, String doctor_id) {

        apiServices.getReviewTwo(user_id, doctor_id).enqueue(new Callback<ReviewTwoResponse>() {
            @Override
            public void onResponse(Call<ReviewTwoResponse> call, Response<ReviewTwoResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ReviewTwoResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCancelAppointment(String booking_id) {

        apiServices.getCancelAppointment(booking_id).enqueue(new Callback<CancelAppointmentResponse>() {
            @Override
            public void onResponse(Call<CancelAppointmentResponse> call, Response<CancelAppointmentResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CancelAppointmentResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });

    }

    public void getBlog(String category_id, String page) {

        apiServices.getBlog(category_id, page).enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(Call<BlogResponse> call, Response<BlogResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<BlogResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getnotificationcount(String user_id) {

        apiServices.getnotificationcount(user_id).enqueue(new Callback<NotificationCountResponse>() {
            @Override
            public void onResponse(Call<NotificationCountResponse> call, Response<NotificationCountResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<NotificationCountResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getNotificationRead(String notification_id) {
        apiServices.getNotificationRead(notification_id).enqueue(new Callback<NotificationReadResponse>() {
            @Override
            public void onResponse(Call<NotificationReadResponse> call, Response<NotificationReadResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<NotificationReadResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getBolgDetails(String article_id) {

        apiServices.getBolgDetails(article_id).enqueue(new Callback<BlogDetailsResponse>() {
            @Override
            public void onResponse(Call<BlogDetailsResponse> call, Response<BlogDetailsResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<BlogDetailsResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getRecommendation(String page) {

        apiServices.getRecommendation(page).enqueue(new Callback<RecommendationResponse>() {
            @Override
            public void onResponse(Call<RecommendationResponse> call, Response<RecommendationResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<RecommendationResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getBlogSearch(String keyword, String page) {

        apiServices.getBlogSearch(keyword, page).enqueue(new Callback<SearchBlogResponse>() {
            @Override
            public void onResponse(Call<SearchBlogResponse> call, Response<SearchBlogResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<SearchBlogResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getNearByClini(String user_id, String latitude, String longitude, String page) {

        apiServices.getNearByClini(user_id, latitude, longitude, page).enqueue(new Callback<VistNearByClinicResponse>() {
            @Override
            public void onResponse(Call<VistNearByClinicResponse> call, Response<VistNearByClinicResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<VistNearByClinicResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getSocialMedia() {
        apiServices.getSocialMedia().enqueue(new Callback<SocialMediaResponse>() {
            @Override
            public void onResponse(Call<SocialMediaResponse> call, Response<SocialMediaResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<SocialMediaResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getTestimonialsVideos() {
        apiServices.getTestimonialsVideos().enqueue(new Callback<TestimonialResponse>() {
            @Override
            public void onResponse(Call<TestimonialResponse> call, Response<TestimonialResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<TestimonialResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getTestimonialsREviews() {
        apiServices.getTestimonialsREviews().enqueue(new Callback<TestimonialReviewResponse>() {
            @Override
            public void onResponse(Call<TestimonialReviewResponse> call, Response<TestimonialReviewResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<TestimonialReviewResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getBeforeAfter() {
        apiServices.getBeforeAfter().enqueue(new Callback<BeforeAfterResponse>() {
            @Override
            public void onResponse(Call<BeforeAfterResponse> call, Response<BeforeAfterResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<BeforeAfterResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getUserApplicationform(String user_id) {
        apiServices.getUserApplicationform(user_id).enqueue(new Callback<ConsultationformResponse>() {
            @Override
            public void onResponse(Call<ConsultationformResponse> call, Response<ConsultationformResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<ConsultationformResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getPatientDetails(String user_id, String first_name, String last_name, String mobile_number, String email_id,
                                  String age, String centre, String about_us, String medium_name, String family_hair_loss, String hair_loss_still_going, String medication,
                                  String medication_descrption, String medical_problem, String medical_problem_description, String diabetic, String hypertensive, String smoking, String drinking, String terms) {

        apiServices.getPatientDetails(user_id, first_name, last_name, mobile_number, email_id, age, centre, about_us, medium_name,
                family_hair_loss, hair_loss_still_going, medication,
                medication_descrption, medical_problem, medical_problem_description, diabetic, hypertensive, smoking, drinking, terms).enqueue(new Callback<PatientDetailsResponse>() {

            @Override
            public void onResponse(Call<PatientDetailsResponse> call, Response<PatientDetailsResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<PatientDetailsResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getSkinConsultantPatientDetails(String user_id, String first_name, String last_name, String mobile_number, String email_id,
                                                String dob, String centre, String about_us, String medium_name, String skin_concern, String skin_concern_description, String medication,
                                                String medication_descrption) {

        apiServices.getSkinConsultantPatientDetails(user_id, first_name, last_name, mobile_number, email_id, dob, centre, about_us, medium_name,
                skin_concern, skin_concern_description, medication,
                medication_descrption).enqueue(new Callback<PatientDetailsResponse>() {

            @Override
            public void onResponse(Call<PatientDetailsResponse> call, Response<PatientDetailsResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<PatientDetailsResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


    public void getNutritionConsultantPatientDetails(String user_id, String first_name, String last_name, String mobile_number, String email_id,
                                                     String age, String height, String weight, String goals, String nutrition_help) {

        apiServices.getNutritionConsultantPatientDetails(user_id, first_name, last_name, mobile_number, email_id, age, height, weight, goals,
                nutrition_help).enqueue(new Callback<PatientDetailsResponse>() {

            @Override
            public void onResponse(Call<PatientDetailsResponse> call, Response<PatientDetailsResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<PatientDetailsResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getRatingList() {

        apiServices.getRatingList().enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCentre() {

        apiServices.getCentre().enqueue(new Callback<CentreResponse>() {
            @Override
            public void onResponse(Call<CentreResponse> call, Response<CentreResponse> response) {
                if (response.code() == 200) {
                    serverResponse.success(response.body(), code);
                } else handleErrorRequest(response.code());
            }

            @Override
            public void onFailure(Call<CentreResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }


//    public void getBanners() {
//        apiServices.getBanners()
//                .enqueue(new Callback<BannerResponse>() {
//                    @Override
//                    public void onResponse(@NonNull Call<BannerResponse> call, @NonNull Response<BannerResponse> response) {
//                        if (response.code() == 200) {
//                            serverResponse.success(response.body(), code);
//                        } else handleErrorRequest(response.code());
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<BannerResponse> call, @NonNull Throwable t) {
//                        serverResponse.error(t.getMessage(), code);
//                    }
//                });
//    }

}