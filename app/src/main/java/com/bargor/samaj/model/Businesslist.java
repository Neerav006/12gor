package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Businesslist implements Parcelable {

    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("native_place")
    @Expose
    private String nativePlace;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("exp")
    @Expose
    private String exp;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("group_no")
    @Expose
    private String groupNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
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
    @SerializedName("web")
    @Expose
    private String web;
    @SerializedName("study_cat")
    @Expose
    private String study_cat;
    @SerializedName("bus_cat")
    @Expose
    private String bus_cat;
    @SerializedName("bus_sub_cat")
    @Expose
    private String bus_sub_cat;
    @SerializedName("study")
    @Expose
    private String study;
    @SerializedName("b_addr")
    @Expose
    private String b_addr;
    @SerializedName("parinit")
    @Expose
    private String isParinit;

    public Businesslist() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
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

    public String getStudy_cat() {
        return study_cat;
    }

    public void setStudy_cat(String study_cat) {
        this.study_cat = study_cat;
    }

    public String getBus_cat() {
        return bus_cat;
    }

    public void setBus_cat(String bus_cat) {
        this.bus_cat = bus_cat;
    }

    public String getBus_sub_cat() {
        return bus_sub_cat;
    }

    public void setBus_sub_cat(String bus_sub_cat) {
        this.bus_sub_cat = bus_sub_cat;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getB_addr() {
        return b_addr;
    }

    public void setB_addr(String b_addr) {
        this.b_addr = b_addr;
    }

    public String getIsParinit() {
        return isParinit;
    }

    public void setIsParinit(String isParinit) {
        this.isParinit = isParinit;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.memberId);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.mobile);
        dest.writeString(this.nativePlace);
        dest.writeString(this.photo);
        dest.writeString(this.catId);
        dest.writeString(this.businessName);
        dest.writeString(this.address);
        dest.writeString(this.degree);
        dest.writeString(this.exp);
        dest.writeString(this.category);
        dest.writeString(this.id);
        dest.writeString(this.dateTime);
        dest.writeString(this.groupNo);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.city);
        dest.writeString(this.village);
        dest.writeString(this.dob);
        dest.writeString(this.profile);
        dest.writeString(this.relation);
        dest.writeString(this.mId);
        dest.writeString(this.role);
        dest.writeString(this.premiumStatus);
        dest.writeString(this.status);
        dest.writeString(this.web);
        dest.writeString(this.study_cat);
        dest.writeString(this.bus_cat);
        dest.writeString(this.bus_sub_cat);
        dest.writeString(this.study);
        dest.writeString(this.b_addr);
        dest.writeString(this.isParinit);
    }

    protected Businesslist(Parcel in) {
        this.memberId = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.mobile = in.readString();
        this.nativePlace = in.readString();
        this.photo = in.readString();
        this.catId = in.readString();
        this.businessName = in.readString();
        this.address = in.readString();
        this.degree = in.readString();
        this.exp = in.readString();
        this.category = in.readString();
        this.id = in.readString();
        this.dateTime = in.readString();
        this.groupNo = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.city = in.readString();
        this.village = in.readString();
        this.dob = in.readString();
        this.profile = in.readString();
        this.relation = in.readString();
        this.mId = in.readString();
        this.role = in.readString();
        this.premiumStatus = in.readString();
        this.status = in.readString();
        this.web = in.readString();
        this.study_cat = in.readString();
        this.bus_cat = in.readString();
        this.bus_sub_cat = in.readString();
        this.study = in.readString();
        this.b_addr = in.readString();
        this.isParinit = in.readString();
    }

    public static final Creator<Businesslist> CREATOR = new Creator<Businesslist>() {
        @Override
        public Businesslist createFromParcel(Parcel source) {
            return new Businesslist(source);
        }

        @Override
        public Businesslist[] newArray(int size) {
            return new Businesslist[size];
        }
    };
}