package com.bargor.samaj.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class BusCategoryList implements Parcelable {

    @SerializedName("main_cat")
    @Expose
    private MainCat mainCat;
    @SerializedName("sub_cat")
    @Expose
    private List<SubCat> subCat = null;

    public MainCat getMainCat() {
        return mainCat;
    }

    public void setMainCat(MainCat mainCat) {
        this.mainCat = mainCat;
    }

    public List<SubCat> getSubCat() {
        return subCat;
    }

    public void setSubCat(List<SubCat> subCat) {
        this.subCat = subCat;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mainCat, flags);
        dest.writeList(this.subCat);
    }

    public BusCategoryList() {
    }

    protected BusCategoryList(Parcel in) {
        this.mainCat = in.readParcelable(MainCat.class.getClassLoader());
        this.subCat = new ArrayList<SubCat>();
        in.readList(this.subCat, SubCat.class.getClassLoader());
    }

    public static final Parcelable.Creator<BusCategoryList> CREATOR = new Parcelable.Creator<BusCategoryList>() {
        @Override
        public BusCategoryList createFromParcel(Parcel source) {
            return new BusCategoryList(source);
        }

        @Override
        public BusCategoryList[] newArray(int size) {
            return new BusCategoryList[size];
        }
    };
}
