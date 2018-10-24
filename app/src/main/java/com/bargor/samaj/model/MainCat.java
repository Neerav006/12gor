package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainCat implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("category_name")
@Expose
private String name;
@SerializedName("status")
@Expose
private String status;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
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
        dest.writeString(this.name);
        dest.writeString(this.status);
    }

    public MainCat() {
    }

    protected MainCat(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<MainCat> CREATOR = new Parcelable.Creator<MainCat>() {
        @Override
        public MainCat createFromParcel(Parcel source) {
            return new MainCat(source);
        }

        @Override
        public MainCat[] newArray(int size) {
            return new MainCat[size];
        }
    };
}