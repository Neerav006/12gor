package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCat implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("sub_cat")
@Expose
private String subId;
@SerializedName("sub_name")
@Expose
private String subName;
@SerializedName("status")
@Expose
private String status;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getSubId() {
return subId;
}

public void setSubId(String subId) {
this.subId = subId;
}

public String getSubName() {
return subName;
}

public void setSubName(String subName) {
this.subName = subName;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.subId);
        dest.writeString(this.subName);
        dest.writeString(this.status);
    }

    public SubCat() {
    }

    protected SubCat(Parcel in) {
        this.id = in.readString();
        this.subId = in.readString();
        this.subName = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<SubCat> CREATOR = new Parcelable.Creator<SubCat>() {
        @Override
        public SubCat createFromParcel(Parcel source) {
            return new SubCat(source);
        }

        @Override
        public SubCat[] newArray(int size) {
            return new SubCat[size];
        }
    };
}