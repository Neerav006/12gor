package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamDetailList {

@SerializedName("id")
@Expose
private String id;
@SerializedName("parent_id")
@Expose
private String parentId;
@SerializedName("member_id")
@Expose
private String memberId;
@SerializedName("tshirt_size")
@Expose
private String tshirtSize;
@SerializedName("palyer_name")
@Expose
private String palyerName;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getParentId() {
return parentId;
}

public void setParentId(String parentId) {
this.parentId = parentId;
}

public String getMemberId() {
return memberId;
}

public void setMemberId(String memberId) {
this.memberId = memberId;
}

public String getTshirtSize() {
return tshirtSize;
}

public void setTshirtSize(String tshirtSize) {
this.tshirtSize = tshirtSize;
}

public String getPalyerName() {
return palyerName;
}

public void setPalyerName(String palyerName) {
this.palyerName = palyerName;
}

}