package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Part2 {

@SerializedName("fn_name")
@Expose
private String fnName;
@SerializedName("fn_age")
@Expose
private String fnAge;
@SerializedName("fn_relation")
@Expose
private String fnRelation;
@SerializedName("fn_grd")
@Expose
private String fnGrd;
@SerializedName("fn_photo")
@Expose
private String fnPhoto;

public String getFnName() {
return fnName;
}

public void setFnName(String fnName) {
this.fnName = fnName;
}

public String getFnAge() {
return fnAge;
}

public void setFnAge(String fnAge) {
this.fnAge = fnAge;
}

public String getFnRelation() {
return fnRelation;
}

public void setFnRelation(String fnRelation) {
this.fnRelation = fnRelation;
}

public String getFnGrd() {
return fnGrd;
}

public void setFnGrd(String fnGrd) {
this.fnGrd = fnGrd;
}

public String getFnPhoto() {
return fnPhoto;
}

public void setFnPhoto(String fnPhoto) {
this.fnPhoto = fnPhoto;
}

}