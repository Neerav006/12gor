package com.bargor.samaj.model22;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessListAPI22 implements Parcelable {

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
        dest.writeTypedList(this.businesslist);
    }

    public BusinessListAPI22() {
    }

    protected BusinessListAPI22(Parcel in) {
        this.businesslist = in.createTypedArrayList(Businesslist.CREATOR);
    }

    public static final Parcelable.Creator<BusinessListAPI22> CREATOR = new Parcelable.Creator<BusinessListAPI22>() {
        @Override
        public BusinessListAPI22 createFromParcel(Parcel source) {
            return new BusinessListAPI22(source);
        }

        @Override
        public BusinessListAPI22[] newArray(int size) {
            return new BusinessListAPI22[size];
        }
    };
}