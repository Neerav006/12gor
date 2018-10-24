package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YuvatiInfo implements Parcelable {

@SerializedName("marriagelist")
@Expose
private List<Marriagelist> marriagelist = null;

public List<Marriagelist> getMarriagelist() {
return marriagelist;
}

public void setMarriagelist(List<Marriagelist> marriagelist) {
this.marriagelist = marriagelist;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.marriagelist);
    }

    public YuvatiInfo() {
    }

    protected YuvatiInfo(Parcel in) {
        this.marriagelist = in.createTypedArrayList(Marriagelist.CREATOR);
    }

    public static final Parcelable.Creator<YuvatiInfo> CREATOR = new Parcelable.Creator<YuvatiInfo>() {
        @Override
        public YuvatiInfo createFromParcel(Parcel source) {
            return new YuvatiInfo(source);
        }

        @Override
        public YuvatiInfo[] newArray(int size) {
            return new YuvatiInfo[size];
        }
    };
}