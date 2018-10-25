package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTeamPlayer {

@SerializedName("team_id")
@Expose
private String teamId;
@SerializedName("c_id")
@Expose
private String cId;
@SerializedName("c_size")
@Expose
private String cSize;
@SerializedName("c_name")
@Expose
private String cName;
@SerializedName("m_id")
@Expose
private List<MId> mId = null;

public String getTeamId() {
return teamId;
}

public void setTeamId(String teamId) {
this.teamId = teamId;
}

public String getCId() {
return cId;
}

public void setCId(String cId) {
this.cId = cId;
}

public String getCSize() {
return cSize;
}

public void setCSize(String cSize) {
this.cSize = cSize;
}

public String getCName() {
return cName;
}

public void setCName(String cName) {
this.cName = cName;
}

public List<MId> getMId() {
return mId;
}

public void setMId(List<MId> mId) {
this.mId = mId;
}

}