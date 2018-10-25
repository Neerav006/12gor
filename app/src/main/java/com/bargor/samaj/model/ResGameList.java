package com.bargor.samaj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResGameList implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("game_name")
    @Expose
    private String game_name;

    @SerializedName("team_size")
    @Expose
    private String team_size;

    private boolean isChecked;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getTeam_size() {
        return team_size;
    }

    public void setTeam_size(String team_size) {
        this.team_size = team_size;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.game_name);
        dest.writeString(this.team_size);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public ResGameList() {
    }

    protected ResGameList(Parcel in) {
        this.id = in.readString();
        this.game_name = in.readString();
        this.team_size = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ResGameList> CREATOR = new Parcelable.Creator<ResGameList>() {
        @Override
        public ResGameList createFromParcel(Parcel source) {
            return new ResGameList(source);
        }

        @Override
        public ResGameList[] newArray(int size) {
            return new ResGameList[size];
        }
    };
}

