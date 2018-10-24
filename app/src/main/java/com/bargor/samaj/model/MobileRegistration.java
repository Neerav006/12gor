package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileRegistration {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("n_place")
    @Expose
    private String nPlace;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("v_id")
    @Expose
    private String v_id;
    @SerializedName("relation")
    @Expose
    private String relation;
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
    @SerializedName("b_cat")
    @Expose
    private String b_cat;
    @SerializedName("b_other")
    @Expose
    private String b_other;
    @SerializedName("f_count")
    @Expose
    private String family_count;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dist")
    @Expose
    private String district;
    @SerializedName("gotra")
    @Expose
    private String gautra;
    @SerializedName("gor")
    @Expose
    private String gor;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("blood")
    @Expose
    private String bloodGrp;
    @SerializedName("m_id")
    @Expose
    private String mem_no;

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public String getB_cat() {
        return b_cat;
    }

    public void setB_cat(String b_cat) {
        this.b_cat = b_cat;
    }

    public String getB_other() {
        return b_other;
    }

    public void setB_other(String b_other) {
        this.b_other = b_other;
    }

    public String getFamily_count() {
        return family_count;
    }

    public void setFamily_count(String family_count) {
        this.family_count = family_count;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGautra() {
        return gautra;
    }

    public void setGautra(String gautra) {
        this.gautra = gautra;
    }

    public String getGor() {
        return gor;
    }

    public void setGor(String gor) {
        this.gor = gor;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public String getMem_no() {
        return mem_no;
    }

    public void setMem_no(String mem_no) {
        this.mem_no = mem_no;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNPlace() {
        return nPlace;
    }

    public void setNPlace(String nPlace) {
        this.nPlace = nPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}