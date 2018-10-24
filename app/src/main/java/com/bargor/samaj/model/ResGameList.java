package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResGameList {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("game_name")
    @Expose
    private String game_name;

    @SerializedName("team_size")
    @Expose
    private String team_size;


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
}
