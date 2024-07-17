package com.hit.assure.Model.Doctorprofile;

import com.google.gson.annotations.SerializedName;
import com.hit.assure.Model.VcSlotDateData.VcDataDate;

import java.util.List;

public class DoctorProfileData {

    @SerializedName("doctor_user_id")
    private String doctor_user_id;

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

    @SerializedName("qualification")
    private String qualification;

    @SerializedName("experience")
    private String experience;

    @SerializedName("reg_council")
    private String reg_council;

    @SerializedName("reg_number")
    private String reg_number;

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

    @SerializedName("other_name")
    private String other_name;

    @SerializedName("other_name_type")
    private String other_name_type;

    @SerializedName("placeses")
    private String placeses;

    @SerializedName("healthwall_category")
    private String healthwall_category;

    @SerializedName("relation_ship_status")
    private String relation_ship_status;

    @SerializedName("language_selection")
    private String language_selection;

    @SerializedName("bio")
    private String bio;

    @SerializedName("rating")
    private String rating;

    @SerializedName("review")
    private String review;

    @SerializedName("followers")
    private String followers;

    @SerializedName("following")
    private String following;

    @SerializedName("profile_views")
    private String profile_views;

    @SerializedName("call_charge")
    private String call_charge;

    @SerializedName("chat_charge")
    private String chat_charge;

    @SerializedName("video_charge")
    private String video_charge;

    @SerializedName("sentence")
    private String sentence;

    @SerializedName("review_enable")
    private int review_enable;

    public int getReview_enable() {
        return review_enable;
    }

    public void setReview_enable(int review_enable) {
        this.review_enable = review_enable;
    }

    @SerializedName("area_expertise")
    private List<DoctorAreaExpertise> doctorAreaExpertiseList;

    @SerializedName("doctor_specialization")
    private List<DoctorSpecializationList> doctorSpecializationListList;

    @SerializedName("doctor_services")
    private List<DoctorServicesList> doctorServicesLists;

    @SerializedName("doctor_degree")
    private List<DoctorDegreeList> doctorDegreeLists;

    @SerializedName("doctor_practices")
    private List<DoctorPractices> doctorPractices;

    @SerializedName("timeslot")
    private List<VcDataDate> vcDataDates;

    @SerializedName("working_hour")
    private List<WorkingHours> workingHours;

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public List<VcDataDate> getVcDataDates() {
        return vcDataDates;
    }

    public void setVcDataDates(List<VcDataDate> vcDataDates) {
        this.vcDataDates = vcDataDates;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<DoctorAreaExpertise> getDoctorAreaExpertiseList() {
        return doctorAreaExpertiseList;
    }

    public void setDoctorAreaExpertiseList(List<DoctorAreaExpertise> doctorAreaExpertiseList) {
        this.doctorAreaExpertiseList = doctorAreaExpertiseList;
    }

    public List<DoctorSpecializationList> getDoctorSpecializationListList() {
        return doctorSpecializationListList;
    }

    public void setDoctorSpecializationListList(List<DoctorSpecializationList> doctorSpecializationListList) {
        this.doctorSpecializationListList = doctorSpecializationListList;
    }

    public List<DoctorServicesList> getDoctorServicesLists() {
        return doctorServicesLists;
    }

    public void setDoctorServicesLists(List<DoctorServicesList> doctorServicesLists) {
        this.doctorServicesLists = doctorServicesLists;
    }

    public List<DoctorDegreeList> getDoctorDegreeLists() {
        return doctorDegreeLists;
    }

    public void setDoctorDegreeLists(List<DoctorDegreeList> doctorDegreeLists) {
        this.doctorDegreeLists = doctorDegreeLists;
    }

    public List<DoctorPractices> getDoctorPractices() {
        return doctorPractices;
    }

    public void setDoctorPractices(List<DoctorPractices> doctorPractices) {
        this.doctorPractices = doctorPractices;
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

    public String getDoctor_user_id() {
        return doctor_user_id;
    }

    public void setDoctor_user_id(String doctor_user_id) {
        this.doctor_user_id = doctor_user_id;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
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

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getOther_name_type() {
        return other_name_type;
    }

    public void setOther_name_type(String other_name_type) {
        this.other_name_type = other_name_type;
    }

    public String getPlaceses() {
        return placeses;
    }

    public void setPlaceses(String placeses) {
        this.placeses = placeses;
    }

    public String getHealthwall_category() {
        return healthwall_category;
    }

    public void setHealthwall_category(String healthwall_category) {
        this.healthwall_category = healthwall_category;
    }

    public String getRelation_ship_status() {
        return relation_ship_status;
    }

    public void setRelation_ship_status(String relation_ship_status) {
        this.relation_ship_status = relation_ship_status;
    }

    public String getLanguage_selection() {
        return language_selection;
    }

    public void setLanguage_selection(String language_selection) {
        this.language_selection = language_selection;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public String getProfile_views() {
        return profile_views;
    }

    public void setProfile_views(String profile_views) {
        this.profile_views = profile_views;
    }
}
