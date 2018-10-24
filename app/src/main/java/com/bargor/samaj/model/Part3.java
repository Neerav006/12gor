package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Part3 {

@SerializedName("sn_name")
@Expose
private String snName;
@SerializedName("sn_age")
@Expose
private String snAge;
@SerializedName("sn_relation")
@Expose
private String snRelation;
@SerializedName("sn_photo")
@Expose
private String snPhoto;
@SerializedName("id1_name")
@Expose
private String id1Name;
@SerializedName("id1_no")
@Expose
private String id1No;
@SerializedName("id2_name")
@Expose
private String id2Name;
@SerializedName("id2_no")
@Expose
private String id2No;

public String getSnName() {
return snName;
}

public void setSnName(String snName) {
this.snName = snName;
}

public String getSnAge() {
return snAge;
}

public void setSnAge(String snAge) {
this.snAge = snAge;
}

public String getSnRelation() {
return snRelation;
}

public void setSnRelation(String snRelation) {
this.snRelation = snRelation;
}

public String getSnPhoto() {
return snPhoto;
}

public void setSnPhoto(String snPhoto) {
this.snPhoto = snPhoto;
}

public String getId1Name() {
return id1Name;
}

public void setId1Name(String id1Name) {
this.id1Name = id1Name;
}

public String getId1No() {
return id1No;
}

public void setId1No(String id1No) {
this.id1No = id1No;
}

public String getId2Name() {
return id2Name;
}

public void setId2Name(String id2Name) {
this.id2Name = id2Name;
}

public String getId2No() {
return id2No;
}

public void setId2No(String id2No) {
this.id2No = id2No;
}

}