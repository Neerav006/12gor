package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Family {

@SerializedName("group_id")
@Expose
private String groupId;
@SerializedName("total")
@Expose
private String total;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("name")
@Expose
private String name;

public String getGroupId() {
return groupId;
}

public void setGroupId(String groupId) {
this.groupId = groupId;
}

public String getTotal() {
return total;
}

public void setTotal(String total) {
this.total = total;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}