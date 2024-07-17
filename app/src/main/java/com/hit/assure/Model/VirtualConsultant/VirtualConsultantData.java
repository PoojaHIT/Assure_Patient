package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VirtualConsultantData {

    @SerializedName("doctor_user_id")
    private String doctor_user_id;

    @SerializedName("listing_type")
    private String listing_type;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    @SerializedName("distance")
    private String distance;

    @SerializedName("doctor_name")
    private String doctor_name;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("doctor_phone")
    private String doctor_phone;

    @SerializedName("dob")
    private String dob;

    @SerializedName("experience")
    private String experience;

    @SerializedName("reg_council")
    private String reg_council;

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("doctor_consultation")
    private List<DoctorConsultationData> doctorConsultationDataList;

    @SerializedName("visit_charge")
    private String visit_charge;

    @SerializedName("call_charge")
    private String call_charge;

    @SerializedName("chat_charge")
    private String chat_charge;

    @SerializedName("video_charge")
    private String video_charge;

    @SerializedName("area_expertise")
    private List<AreaExpertiseData> areaExpertiseData;

    @SerializedName("doctor_specialization")
    private List<DoctorSpecializationData>  doctorSpecializationData;

    @SerializedName("doctor_services")
    private List<DoctorServicesData> doctorServicesData;

    @SerializedName("doctor_degree")
    private List<DoctorDegreeData> doctorDegreeData;

    @SerializedName("doctor_practices")
    private List<DoctorPracticesData> doctorPracticesData;

    @SerializedName("rating")
    private String rating;

    @SerializedName("review")
    private String review;

    @SerializedName("followers")
    private String followers;

    @SerializedName("following")
    private String following;

    @SerializedName("is_follow")
    private String is_follow;

    @SerializedName("discount")
    private String discount;

    @SerializedName("mba")
    private String mba;

    @SerializedName("certified")
    private String certified;

    @SerializedName("recommended")
    private String recommended;

    @SerializedName("online_status")
    private String online_status;

    public String getVisit_charge() {
        return visit_charge;
    }

    public void setVisit_charge(String visit_charge) {
        this.visit_charge = visit_charge;
    }

    public String getCall_charge() {
        return call_charge;
    }

    public void setCall_charge(String call_charge) {
        this.call_charge = call_charge;
    }

    public String getChat_charge() {
        return chat_charge;
    }

    public void setChat_charge(String chat_charge) {
        this.chat_charge = chat_charge;
    }

    public String getVideo_charge() {
        return video_charge;
    }

    public void setVideo_charge(String video_charge) {
        this.video_charge = video_charge;
    }

    public List<AreaExpertiseData> getAreaExpertiseData() {
        return areaExpertiseData;
    }

    public void setAreaExpertiseData(List<AreaExpertiseData> areaExpertiseData) {
        this.areaExpertiseData = areaExpertiseData;
    }

    public List<DoctorSpecializationData> getDoctorSpecializationData() {
        return doctorSpecializationData;
    }

    public void setDoctorSpecializationData(List<DoctorSpecializationData> doctorSpecializationData) {
        this.doctorSpecializationData = doctorSpecializationData;
    }

    public List<DoctorServicesData> getDoctorServicesData() {
        return doctorServicesData;
    }

    public void setDoctorServicesData(List<DoctorServicesData> doctorServicesData) {
        this.doctorServicesData = doctorServicesData;
    }

    public List<DoctorDegreeData> getDoctorDegreeData() {
        return doctorDegreeData;
    }

    public void setDoctorDegreeData(List<DoctorDegreeData> doctorDegreeData) {
        this.doctorDegreeData = doctorDegreeData;
    }

    public List<DoctorPracticesData> getDoctorPracticesData() {
        return doctorPracticesData;
    }

    public void setDoctorPracticesData(List<DoctorPracticesData> doctorPracticesData) {
        this.doctorPracticesData = doctorPracticesData;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMba() {
        return mba;
    }

    public void setMba(String mba) {
        this.mba = mba;
    }

    public String getCertified() {
        return certified;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getDoctor_user_id() {
        return doctor_user_id;
    }

    public void setDoctor_user_id(String doctor_user_id) {
        this.doctor_user_id = doctor_user_id;
    }

    public String getListing_type() {
        return listing_type;
    }

    public void setListing_type(String listing_type) {
        this.listing_type = listing_type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDoctor_phone() {
        return doctor_phone;
    }

    public void setDoctor_phone(String doctor_phone) {
        this.doctor_phone = doctor_phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getReg_council() {
        return reg_council;
    }

    public void setReg_council(String reg_council) {
        this.reg_council = reg_council;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public List<DoctorConsultationData> getDoctorConsultationDataList() {
        return doctorConsultationDataList;
    }

    public void setDoctorConsultationDataList(List<DoctorConsultationData> doctorConsultationDataList) {
        this.doctorConsultationDataList = doctorConsultationDataList;
    }
}
