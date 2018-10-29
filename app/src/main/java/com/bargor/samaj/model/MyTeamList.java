package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyTeamList {

    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cap_name")
    @Expose
    private String capName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("teamSize")
    @Expose
    private String team_size;
    @SerializedName("captainTShirtSize")
    @Expose
    private String t_size;
    @SerializedName("member_id")
    @Expose
    private String member_id;
    @SerializedName("papa_id")
    @Expose
    private String papa_id;
    @SerializedName("game_id")
    @Expose
    private String game_id;

    public String getTeam_size() {
        return team_size;
    }

    public void setTeam_size(String team_size) {
        this.team_size = team_size;
    }

    public String getT_size() {
        return t_size;
    }

    public void setT_size(String t_size) {
        this.t_size = t_size;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPapa_id() {
        return papa_id;
    }

    public void setPapa_id(String papa_id) {
        this.papa_id = papa_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapName() {
        return capName;
    }

    public void setCapName(String capName) {
        this.capName = capName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

}