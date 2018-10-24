package com.bargor.samaj.model22;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Businesslist implements Parcelable {

    @SerializedName("Usermaster")
    @Expose
    private Usermaster usermaster;
    @SerializedName("Jobsubcategory")
    @Expose
    private Jobsubcategory jobsubcategory;

    public Usermaster getUsermaster() {
        return usermaster;
    }

    public void setUsermaster(Usermaster usermaster) {
        this.usermaster = usermaster;
    }

    public Jobsubcategory getJobsubcategory() {
        return jobsubcategory;
    }

    public void setJobsubcategory(Jobsubcategory jobsubcategory) {
        this.jobsubcategory = jobsubcategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.usermaster, flags);
        dest.writeParcelable(this.jobsubcategory, flags);
    }

    public Businesslist() {
    }

    protected Businesslist(Parcel in) {
        this.usermaster = in.readParcelable(Usermaster.class.getClassLoader());
        this.jobsubcategory = in.readParcelable(Jobsubcategory.class.getClassLoader());
    }

    public static final Parcelable.Creator<Businesslist> CREATOR = new Parcelable.Creator<Businesslist>() {
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