package com.hit.assure.Retrofit;

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
import com.hit.assure.Model.Note.NoteResponse;
import com.hit.assure.Model.Notification.NotificationResponse;
import com.hit.assure.Model.NotificationCountResponse;
import com.hit.assure.Model.NotificationReadResponse;
import com.hit.assure.Model.OnlyResponse;
import com.hit.assure.Model.OtpVerification.OtpVerificationResponse;
import com.hit.assure.Model.PatientBookingDetails.PatientBookingDetailsResponse;
import com.hit.assure.Model.PatientDetailsFrom.PatientDetailsResponse;
import com.hit.assure.Model.PatientInfo.PatientInfoResponse;
import com.hit.assure.Model.Prescriptionlist.PrescriptionlistResponse;
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
import com.hit.assure.Model.Sessionimage.SessionimageResponse;
import com.hit.assure.Model.SocialLogin.SocialLoginResponse;
import com.hit.assure.Model.SocialMedia.SocialMediaResponse;
import com.hit.assure.Model.SubCategory.SubCategoryResponse;
import com.hit.assure.Model.Testimonials.BeforeAfterResponse;
import com.hit.assure.Model.Testimonials.TestimonialResponse;
import com.hit.assure.Model.Testimonials.TestimonialReviewResponse;
import com.hit.assure.Model.TipOfTheDay.TipOfTheBanneResponse;
import com.hit.assure.Model.TopBanner.TopBanner;
import com.hit.assure.Model.UpdateUserProfile.UpdateUserDataResponse;
import com.hit.assure.Model.UploadSessionimageResponse;
import com.hit.assure.Model.UplodedPrescriptionResponse;
import com.hit.assure.Model.UserDetails.UserDetailsResponse;
import com.hit.assure.Model.UserimageResponse;
import com.hit.assure.Model.VcSlotDateData.VcSlotdateDataResponse;
import com.hit.assure.Model.Vcsoltdate.VcSlotdateResponse;
import com.hit.assure.Model.VideoCl.VideoMeetingResponse;
import com.hit.assure.Model.ViewDietPlanPrescription.ViewDietplanPrescriptionResponse;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantResponse;
import com.hit.assure.Model.VisitNearByClinic.VistNearByClinicResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServices {

    //    @POST("home_topbanner")
//    Call<BannerResponse> getBanners();
//
    @FormUrlEncoded
    @POST("auth/sociallogin")
    Call<SocialLoginResponse> getSociallogin(@Field("oauth_provider") String oauth_provider,
                                             @Field("oauth_uid") String oauth_uid,
                                             @Field("email") String email,
                                             @Field("agent") String agent,
                                             @Field("token") String token,
                                             @Field("name") String name,
                                             @Field("gender") String gender,
                                             @Field("lat") String lat,
                                             @Field("long") String loong
    );
//
//    @FormUrlEncoded
//    @POST("insert_order_card_details")
//    Call<OrderCardResponse> getOrderCard(@Field("user_id") String user_id,
//                                         @Field("full_name") String full_name,
//                                         @Field("mobile_no") String mobile_no,
//                                         @Field("pincode") String pincode,
//                                         @Field("address") String address,
//                                         @Field("area") String area,
//                                         @Field("landmark") String landmark,
//                                         @Field("city") String city,
//                                         @Field("state") String state
//    );

    @FormUrlEncoded
    @POST("auth/mba_send_otp")
    Call<SendOtpResponse> getOtp(
            @Field("mobile_no") String mobile_no,
            @Field("lat") String lat,
            @Field("token") String token,
            @Field("long") String loong);

    @FormUrlEncoded
    @POST("auth/mba_otp_verify")
    Call<OtpVerificationResponse> getOtpVerification(@Field("phone") String phone,
                                                     @Field("otp") String otp);

    @FormUrlEncoded
    @POST("auth/sendotp")
    Call<ResendOtpVerification> getResendOtp(@Field("phone") String phone);


    @FormUrlEncoded
    @POST("auth/userupdate")
    Call<UpdateUserDataResponse> getUpdateUserData(@Field("user_id") String user_id,
                                                   @Field("name") String name,
                                                   @Field("mobile_no") String mobile_no,
                                                   @Field("age") String age,
                                                   @Field("city") String city,
                                                   @Field("pincode") String pincode,
                                                   @Field("landmark") String landmark,
                                                   @Field("address") String address);

    @POST("auth/userimage")
    Call<UserimageResponse> getUserimage(@Body RequestBody file);


    @FormUrlEncoded
    @POST("auth/userdetails")
    Call<UserDetailsResponse> getUserdata(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("auth/register")
    Call<RegisterResponse> getResgister(@Field("full_name") String full_name,
                                        @Field("mobile_no") String mobile_no,
                                        @Field("agent") String agent,
                                        @Field("token") String token,
                                        @Field("lat") String lat,
                                        @Field("long") String loong);

    @FormUrlEncoded
    @POST("auth/check_mobile_number")
    Call<CheckmobilenumberResponse> getCheckmobilenumberfor_signup(@Field("mobile_no") String mobile_no);

    @GET("Healthmall/get_categories")
    Call<CategoryResponse> getCategory();

    @GET("Healthmall/top_product_list")
    Call<ProductListResponse> getProductList();

    @FormUrlEncoded
    @POST("Healthmall/top_banner")
    Call<TopBanner> getTopbanner(@Field("city") String city);

    @FormUrlEncoded
    @POST("Healthmall/search_product")
    Call<SearchResponse> getSearchResult(@Field("keyword") String keyword);


    @FormUrlEncoded
    @POST("Healthmall/inline_banner_1")
    Call<InlineBannerResponse> getInlineBanner(@Field("city") String city);

    @FormUrlEncoded
    @POST("Healthmall/tip_of_the_day_banner")
    Call<TipOfTheBanneResponse> getTipOfTheDay(@Field("city") String city);

    @FormUrlEncoded
    @POST("Healthmall/inline_banner_2")
    Call<InlineBannerTwoResponse> getInlineBannerTwo(@Field("city") String city);

    @FormUrlEncoded
    @POST("Healthmall/get_sub_categories")
    Call<SubCategoryResponse> getSubCategory(@Field("cat_id") String cat_id);


    @Headers({"Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY0NDMyODMxOSwianRpIjoiYmE0ZjhjOTMtYmI4My00NzNlLTg4OWUtYWU1ODllOGIzN2VhIiwidHlwZSI6ImFjY2VzcyIsImlkZW50aXR5IjoiZGV2LmhhbmRzaW50ZWNobm9sb2d5QGFhZGhhYXJhcGkuaW8iLCJuYmYiOjE2NDQzMjgzMTksImV4cCI6MTk1OTY4ODMxOSwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.r1Ur6AIZF0ulQxzhtG01JODnEJSdSfiOyNu6zP2xWRM"})
    @GET("meetings")
    Call<VideoMeetingResponse> getMeeting();

    @FormUrlEncoded
    @POST("doctor/doctor_list")
    Call<VirtualConsultantResponse> getDoctorList(@Field("user_id") String user_id,
                                                  @Field("latitude") String latitude,
                                                  @Field("longitude") String longitude,
                                                  @Field("category_id") String category_id,
                                                  @Field("page") String page);

    @FormUrlEncoded
    @POST("doctor/view_doctor_profile")
    Call<DoctorProfileresponse> getDoctorProfile(@Field("user_id") String user_id,
                                                 @Field("cust_id") String cust_id,
                                                 @Field("clinic_id") String clinic_id,
                                                 @Field("date") String date);


    @FormUrlEncoded
    @POST("doctor/doctor_list_search")
    Call<DoctorSearchResponse> getDoctorSearchList(@Field("user_id") String user_id,
                                                   @Field("latitude") String latitude,
                                                   @Field("longitude") String longitude,
                                                   @Field("page") String page,
                                                   @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("doctor/doctor_slot")
    Call<VcSlotdateDataResponse> getVcSlotDateData(@Field("doctor_id") String doctor_id,
                                                   @Field("clinic_id") String clinic_id,
                                                   @Field("consultation_type") String consultation_type,
                                                   @Field("date") String date);


    @FormUrlEncoded
    @POST("doctor/date_list")
    Call<VcSlotdateResponse> getVcSlotDate(@Field("doctor_id") String doctor_id,
                                           @Field("clinic_id") String clinic_id,
                                           @Field("consultation_type") String consultation_type);



    @FormUrlEncoded
    @POST("doctor/user_notification_list")
    Call<NotificationResponse> getNotificationlist(@Field("user_id") String user_id,
                                                   @Field("offset") String offset);

    @FormUrlEncoded
    @POST("doctor/patient_info")
    Call<PatientInfoResponse> getPatientinfo_createPrescription(
            @Field("doctor_id") String doctor_id,
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("doctor/view_images")
    Call<SessionimageResponse> getSessionimage(
            @Field("doctor_id") String doctor_id,
            @Field("patient_id") String patient_id
    );


    @FormUrlEncoded
    @POST("doctor/delete_prescription")
    Call<OnlyResponse> getDeletePrescription(@Field("booking_id") String booking_id);


    @POST("doctor/add_review")
    Call<ReviewResponse> getReview(@Body RequestBody file);

    @FormUrlEncoded
    @POST("doctor/review_list")
    Call<ReviewListResponse> getReviewList(@Field("user_id") String user_id,
                                           @Field("doctor_id") String doctor_id);

    @FormUrlEncoded
    @POST("doctor/cat_wise_doctor_list_base_on_appointment")
    Call<CurrentSessionDoctorlistResponse> getDoctorlist(@Field("user_id") String user_id,
                                                         @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("doctor/add_bookings")
    Call<BookAppointmentResponse> getBookAppointment(@Field("user_id") String user_id,
                                                     @Field("doctor_id") String doctor_id,
                                                     @Field("clinic_id") String clinic_id,
                                                     @Field("booking_date") String booking_date,
                                                     @Field("from_time") String from_time,
                                                     @Field("to_time") String to_time,
                                                     @Field("connect_type") String connect_type,
                                                     @Field("fee") String fee,
                                                     @Field("transaction_id") String transaction_id,
                                                     @Field("payment_method") String payment_method,
                                                     @Field("coupon_code") String coupon_code,
                                                     @Field("booking_fee") String booking_fee,
                                                     @Field("coupon_discount_amount") String coupon_discount_amount,
                                                     @Field("total_paid_amount") String total_paid_amount);

    @GET("doctor/city_list")
    Call<CityListResponse> getCity();

    @FormUrlEncoded
    @POST("doctor/view_appointments")
    Call<ActiveAppointmentsResponse> getActiveAppointment(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("doctor/user_click_here_to_join")
    Call<OnlyResponse> getUserendmeeting(
            @Field("user_id") String user_id,
            @Field("booking_id") String booking_id
                                                       );

    @FormUrlEncoded
    @POST("doctor/update_complete_appointment")
    Call<OnlyResponse> getCheckUserendmeeting(
            @Field("patient_id") String user_id,
            @Field("booking_id") String booking_id
                                                       );

    @FormUrlEncoded
    @POST("doctor/view_completed_appointments")
    Call<CompletedAppointmentResponse> getCompletedAppointment(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("doctor/apply_coupon")
    Call<ApplyCouponResponse> getCoupon(@Field("user_id") String user_id,
                                        @Field("coupon_code") String coupon_code,
                                        @Field("total_amount") String total_amount,
                                        @Field("type") String type
    );

    @FormUrlEncoded
    @POST("doctor/short_review_list")
    Call<ReviewTwoResponse> getReviewTwo(@Field("user_id") String user_id,
                                         @Field("doctor_id") String doctor_id);

    @FormUrlEncoded
    @POST("doctor/cancel_booking")
    Call<CancelAppointmentResponse> getCancelAppointment(@Field("booking_id") String booking_id);

    @FormUrlEncoded
    @POST("blogs/blog_list")
    Call<BlogResponse> getBlog(@Field("category_id") String category_id,
                               @Field("page") String page);

    @FormUrlEncoded
    @POST("doctor/user_notification_count")
    Call<NotificationCountResponse> getnotificationcount(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("doctor/notification_read")
    Call<NotificationReadResponse> getNotificationRead(@Field("notification_id") String notification_id);

    @FormUrlEncoded
    @POST("blogs/blog_details")
    Call<BlogDetailsResponse> getBolgDetails(@Field("article_id") String article_id);

    @FormUrlEncoded
    @POST("blogs/blogs_recommendation")
    Call<RecommendationResponse> getRecommendation(@Field("page") String page);

    @FormUrlEncoded
    @POST("blogs/article_list_by_keyword")
    Call<SearchBlogResponse> getBlogSearch(@Field("keyword") String keyword,
                                           @Field("page") String page);

    @FormUrlEncoded
    @POST("doctor/doctor_clinic_list")
    Call<VistNearByClinicResponse> getNearByClini(@Field("user_id") String user_id,
                                                  @Field("latitude") String latitude,
                                                  @Field("longitude") String longitude,
                                                  @Field("page") String page);

    @FormUrlEncoded
    @POST("doctor/patient_booking_details")
    Call<PatientBookingDetailsResponse> getPatientbookingdetails(
            @Field("booking_id") String booking_id
    );

    @FormUrlEncoded
    @POST("doctor/diet_plan_details")
    Call<ViewDietplanPrescriptionResponse> getDietplanPrescriptoin(
            @Field("booking_id") String booking_id
    );

    @FormUrlEncoded
    @POST("doctor/get_note")
    Call<NoteResponse> getNote(@Field("booking_id") String booking_id);


    @GET("doctor/social_media")
    Call<SocialMediaResponse> getSocialMedia();


    @GET("doctor/testimonials")
    Call<TestimonialResponse> getTestimonialsVideos();


    @GET("doctor/testimonials_review_list")
    Call<TestimonialReviewResponse> getTestimonialsREviews();


    @GET("doctor/testimonials_media")
    Call<BeforeAfterResponse> getBeforeAfter();

    @FormUrlEncoded
    @POST("doctor/user_application_form_details")
    Call<ConsultationformResponse> getUserApplicationform(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("doctor/patient_details")
    Call<PatientDetailsResponse> getPatientDetails(@Field("user_id") String user_id,
                                                   @Field("first_name") String first_name,
                                                   @Field("last_name") String last_name,
                                                   @Field("mobile_number") String mobile_number,
                                                   @Field("email_id") String email_id,
                                                   @Field("centre") String centre,
                                                   @Field("age") String age,
                                                   @Field("about_us") String about_us,
                                                   @Field("medium_name") String medium_name,
                                                   @Field("family_hair_loss") String family_hair_loss,
                                                   @Field("hair_loss_still_going") String hair_loss_still_going,
                                                   @Field("medication") String medication,
                                                   @Field("medication_descrption") String medication_descrption,
                                                   @Field("medical_problem") String medical_problem,
                                                   @Field("medical_problem_description") String medical_problem_description,
                                                   @Field("diabetic") String diabetic,
                                                   @Field("hypertensive") String hypertensive,
                                                   @Field("smoking") String smoking,
                                                   @Field("drinking") String drinking,
                                                   @Field("terms") String terms);

    @FormUrlEncoded
    @POST("doctor/skin_consultation_patient_details")
    Call<PatientDetailsResponse> getSkinConsultantPatientDetails(@Field("user_id") String user_id,
                                                   @Field("first_name") String first_name,
                                                   @Field("last_name") String last_name,
                                                   @Field("mobile_number") String mobile_number,
                                                   @Field("email_id") String email_id,
                                                   @Field("dob") String dob,
                                                   @Field("centre") String centre,
                                                   @Field("about_us") String about_us,
                                                   @Field("medium_name") String medium_name,
                                                   @Field("skin_concern") String skin_concern,
                                                   @Field("skin_concern_description") String skin_concern_description,
                                                   @Field("medication") String medication,
                                                   @Field("medication_descrption") String medication_descrption
    );

    @FormUrlEncoded
    @POST("doctor/nutrition_consultation_patient_details")
    Call<PatientDetailsResponse> getNutritionConsultantPatientDetails(@Field("user_id") String user_id,
                                                   @Field("first_name") String first_name,
                                                   @Field("last_name") String last_name,
                                                   @Field("mobile_number") String mobile_number,
                                                   @Field("email_id") String email_id,
                                                   @Field("age") String age,
                                                   @Field("height") String height,
                                                   @Field("weight") String weight,
                                                   @Field("goals") String goals,
                                                   @Field("nutrition_help") String nutrition_help
    );

    @GET("doctor/random_review_list")
    Call<RatingResponse> getRatingList();

    @GET("doctor/centre_list")
    Call<CentreResponse> getCentre();

    @POST("doctor/update_patient_booking_info")
    Call<UploadSessionimageResponse> getUpdate(@Body RequestBody file);

    @FormUrlEncoded
    @POST("doctor/prescription_list")
    Call<PrescriptionlistResponse> getPrescriptionlist(@Field("booking_id") String booking_id);

    @FormUrlEncoded
    @POST("doctor/get_upload_prescription")
    Call<UplodedPrescriptionResponse> getUploadedPrescriptionIMg(@Field("booking_id") String booking_id);


}