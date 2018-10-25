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