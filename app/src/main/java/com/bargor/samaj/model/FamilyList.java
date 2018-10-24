package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyList implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("group_no")
    @Expose
    private String groupNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("m_id")
    @Expose
    private String mId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("premium_status")
    @Expose
    private String premiumStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("blood")
    @Expose
    private String blood;
    @SerializedName("gotra")
    @Expose
    private String shakh;
    @SerializedName("study")
    @Expose
    private String study;
    @SerializedName("study_cat")
    @Expose
    private String studyCat;
    @SerializedName("bus_cat")
    @Expose
    private String busCat;
    @SerializedName("bus_sub_cat")
    @Expose
    private String busSubCat;

    public FamilyList() {
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getShakh() {
        return shakh;
    }

    public void setShakh(String shakh) {
        this.shakh = shakh;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPremiumStatus() {
        return premiumStatus;
    }

    public void setPremiumStatus(String premiumStatus) {
        this.premiumStatus = premiumStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudyCat() {
        return studyCat;
    }

    public void setStudyCat(String studyCat) {
        this.studyCat = studyCat;
    }

    public String getBusCat() {
        return busCat;
    }

    public void setBusCat(String busCat) {
        this.busCat = busCat;
    }

    public String getBusSubCat() {
        return busSubCat;
    }

    public void setBusSubCat(String busSubCat) {
        this.busSubCat = busSubCat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.dateTime);
        dest.writeString(this.groupNo);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.password);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeString(this.village);
        dest.writeString(this.dob);
        dest.writeString(this.profile);
        dest.writeString(this.relation);
        dest.writeString(this.mId);
        dest.writeString(this.role);
        dest.writeString(this.premiumStatus);
        dest.writeString(this.status);
        dest.writeString(this.blood);
        dest.writeString(this.shakh);
        dest.writeString(this.study);
        dest.writeString(this.studyCat);
        dest.writeString(this.busCat);
        dest.writeString(this.busSubCat);
    }

    protected FamilyList(Parcel in) {
        this.id = in.readString();
        this.dateTime = in.readString();
        this.groupNo = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.password = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.village = in.readString();
        this.dob = in.readString();
        this.profile = in.readString();
        this.relation = in.readString();
        this.mId = in.readString();
        this.role = in.readString();
        this.premiumStatus = in.readString();
        this.status = in.readString();
        this.blood = in.readString();
        this.shakh = in.readString();
        this.study = in.readString();
        this.studyCat = in.readString();
        this.busCat = in.readString();
        this.busSubCat = in.readString();
    }

    public static final Creator<FamilyList> CREATOR = new Creator<FamilyList>() {
        @Override
        public FamilyList createFromParcel(Parcel source) {
            return new FamilyList(source);
        }

        @Override
        public FamilyList[] newArray(int size) {
            return new FamilyList[size];
        }
    };
}