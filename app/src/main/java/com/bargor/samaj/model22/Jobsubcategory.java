package com.bargor.samaj.model22;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jobsubcategory implements Parcelable {

@SerializedName("name")
@Expose
private String name;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Jobsubcategory() {
    }

    protected Jobsubcategory(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Jobsubcategory> CREATOR = new Parcelable.Creator<Jobsubcategory>() {
        @Override
        public Jobsubcategory createFromParcel(Parcel source) {
            return new Jobsubcategory(source);
        }

        @Override
        public Jobsubcategory[] newArray(int size) {
            return new Jobsubcategory[size];
        }
    };
}