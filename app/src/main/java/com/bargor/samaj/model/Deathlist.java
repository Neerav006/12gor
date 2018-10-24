package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deathlist implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("l_id")
@Expose
private String lId;
@SerializedName("member_id")
@Expose
private String memberId;
@SerializedName("join_date")
@Expose
private String joinDate;
@SerializedName("name")
@Expose
private String name;
@SerializedName("address")
@Expose
private String address;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("native_place")
@Expose
private String nativePlace;
@SerializedName("city")
@Expose
private String city;
@SerializedName("taluka")
@Expose
private String taluka;
@SerializedName("dist")
@Expose
private String dist;
@SerializedName("state")
@Expose
private String state;
@SerializedName("pin")
@Expose
private String pin;
@SerializedName("email")
@Expose
private String email;
@SerializedName("age")
@Expose
private String age;
@SerializedName("dob")
@Expose
private String dob;
@SerializedName("photo")
@Expose
private String photo;
@SerializedName("fn_name")
@Expose
private String fnName;
@SerializedName("fn_age")
@Expose
private String fnAge;
@SerializedName("fn_relation")
@Expose
private String fnRelation;
@SerializedName("fn_gardian")
@Expose
private String fnGardian;
@SerializedName("fn_photo")
@Expose
private String fnPhoto;
@SerializedName("sn_name")
@Expose
private String snName;
@SerializedName("sn_age")
@Expose
private String snAge;
@SerializedName("sn_relation")
@Expose
private String snRelation;
@SerializedName("sn_photo")
@Expose
private String snPhoto;
@SerializedName("identifier1_name")
@Expose
private String identifier1Name;
@SerializedName("identifier1_no")
@Expose
private String identifier1No;
@SerializedName("identifier2_name")
@Expose
private String identifier2Name;
@SerializedName("identifier2_no")
@Expose
private String identifier2No;
@SerializedName("status")
@Expose
private String status;
@SerializedName("expireddate")
@Expose
private String expireddate;
@SerializedName("amount")
@Expose
private String amount;

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

public String getLId() {
return lId;
}

public void setLId(String lId) {
this.lId = lId;
}

public String getMemberId() {
return memberId;
}

public void setMemberId(String memberId) {
this.memberId = memberId;
}

public String getJoinDate() {
return joinDate;
}

public void setJoinDate(String joinDate) {
this.joinDate = joinDate;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
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

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getTaluka() {
return taluka;
}

public void setTaluka(String taluka) {
this.taluka = taluka;
}

public String getDist() {
return dist;
}

public void setDist(String dist) {
this.dist = dist;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getPin() {
return pin;
}

public void setPin(String pin) {
this.pin = pin;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getAge() {
return age;
}

public void setAge(String age) {
this.age = age;
}

public String getDob() {
return dob;
}

public void setDob(String dob) {
this.dob = dob;
}

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

public String getFnName() {
return fnName;
}

public void setFnName(String fnName) {
this.fnName = fnName;
}

public String getFnAge() {
return fnAge;
}

public void setFnAge(String fnAge) {
this.fnAge = fnAge;
}

public String getFnRelation() {
return fnRelation;
}

public void setFnRelation(String fnRelation) {
this.fnRelation = fnRelation;
}

public String getFnGardian() {
return fnGardian;
}

public void setFnGardian(String fnGardian) {
this.fnGardian = fnGardian;
}

public String getFnPhoto() {
return fnPhoto;
}

public void setFnPhoto(String fnPhoto) {
this.fnPhoto = fnPhoto;
}

public String getSnName() {
return snName;
}

public void setSnName(String snName) {
this.snName = snName;
}

public String getSnAge() {
return snAge;
}

public void setSnAge(String snAge) {
this.snAge = snAge;
}

public String getSnRelation() {
return snRelation;
}

public void setSnRelation(String snRelation) {
this.snRelation = snRelation;
}

public String getSnPhoto() {
return snPhoto;
}

public void setSnPhoto(String snPhoto) {
this.snPhoto = snPhoto;
}

public String getIdentifier1Name() {
return identifier1Name;
}

public void setIdentifier1Name(String identifier1Name) {
this.identifier1Name = identifier1Name;
}

public String getIdentifier1No() {
return identifier1No;
}

public void setIdentifier1No(String identifier1No) {
this.identifier1No = identifier1No;
}

public String getIdentifier2Name() {
return identifier2Name;
}

public void setIdentifier2Name(String identifier2Name) {
this.identifier2Name = identifier2Name;
}

public String getIdentifier2No() {
return identifier2No;
}

public void setIdentifier2No(String identifier2No) {
this.identifier2No = identifier2No;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getExpireddate() {
return expireddate;
}

public void setExpireddate(String expireddate) {
this.expireddate = expireddate;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.dateTime);
        dest.writeString(this.lId);
        dest.writeString(this.memberId);
        dest.writeString(this.joinDate);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.mobile);
        dest.writeString(this.nativePlace);
        dest.writeString(this.city);
        dest.writeString(this.taluka);
        dest.writeString(this.dist);
        dest.writeString(this.state);
        dest.writeString(this.pin);
        dest.writeString(this.email);
        dest.writeString(this.age);
        dest.writeString(this.dob);
        dest.writeString(this.photo);
        dest.writeString(this.fnName);
        dest.writeString(this.fnAge);
        dest.writeString(this.fnRelation);
        dest.writeString(this.fnGardian);
        dest.writeString(this.fnPhoto);
        dest.writeString(this.snName);
        dest.writeString(this.snAge);
        dest.writeString(this.snRelation);
        dest.writeString(this.snPhoto);
        dest.writeString(this.identifier1Name);
        dest.writeString(this.identifier1No);
        dest.writeString(this.identifier2Name);
        dest.writeString(this.identifier2No);
        dest.writeString(this.status);
        dest.writeString(this.expireddate);
        dest.writeString(this.amount);
    }

    public Deathlist() {
    }

    protected Deathlist(Parcel in) {
        this.id = in.readString();
        this.dateTime = in.readString();
        this.lId = in.readString();
        this.memberId = in.readString();
        this.joinDate = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.mobile = in.readString();
        this.nativePlace = in.readString();
        this.city = in.readString();
        this.taluka = in.readString();
        this.dist = in.readString();
        this.state = in.readString();
        this.pin = in.readString();
        this.email = in.readString();
        this.age = in.readString();
        this.dob = in.readString();
        this.photo = in.readString();
        this.fnName = in.readString();
        this.fnAge = in.readString();
        this.fnRelation = in.readString();
        this.fnGardian = in.readString();
        this.fnPhoto = in.readString();
        this.snName = in.readString();
        this.snAge = in.readString();
        this.snRelation = in.readString();
        this.snPhoto = in.readString();
        this.identifier1Name = in.readString();
        this.identifier1No = in.readString();
        this.identifier2Name = in.readString();
        this.identifier2No = in.readString();
        this.status = in.readString();
        this.expireddate = in.readString();
        this.amount = in.readString();
    }

    public static final Parcelable.Creator<Deathlist> CREATOR = new Parcelable.Creator<Deathlist>() {
        @Override
        public Deathlist createFromParcel(Parcel source) {
            return new Deathlist(source);
        }

        @Override
        public Deathlist[] newArray(int size) {
            return new Deathlist[size];
        }
    };
}