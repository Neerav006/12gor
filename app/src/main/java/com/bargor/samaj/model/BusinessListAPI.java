package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessListAPI implements Parcelable {

@SerializedName("businesslist")
@Expose
private List<Businesslist> businesslist = null;

public List<Businesslist> getBusinesslist() {
return businesslist;
}

public void setBusinesslist(List<Businesslist> businesslist) {
this.businesslist = businesslist;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.businesslist);
    }

    public BusinessListAPI() {
    }

    protected BusinessListAPI(Parcel in) {
        this.businesslist = new ArrayList<Businesslist>();
        in.readList(this.businesslist, Businesslist.class.getClassLoader());
    }

    public static final Parcelable.Creator<BusinessListAPI> CREATOR = new Parcelable.Creator<BusinessListAPI>() {
        @Override
        public BusinessListAPI createFromParcel(Parcel source) {
            return new BusinessListAPI(source);
        }

        @Override
        public BusinessListAPI[] newArray(int size) {
            return new BusinessListAPI[size];
        }
    };
}