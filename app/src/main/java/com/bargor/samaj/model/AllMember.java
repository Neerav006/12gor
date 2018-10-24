package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMember implements Parcelable {

@SerializedName("memberlist")
@Expose
private List<Memberlist> memberlist = null;

public List<Memberlist> getMemberlist() {
return memberlist;
}

public void setMemberlist(List<Memberlist> memberlist) {
this.memberlist = memberlist;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.memberlist);
    }

    public AllMember() {
    }

    protected AllMember(Parcel in) {
        this.memberlist = new ArrayList<Memberlist>();
        in.readList(this.memberlist, Memberlist.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllMember> CREATOR = new Parcelable.Creator<AllMember>() {
        @Override
        public AllMember createFromParcel(Parcel source) {
            return new AllMember(source);
        }

        @Override
        public AllMember[] newArray(int size) {
            return new AllMember[size];
        }
    };
}