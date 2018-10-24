package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetail {

@SerializedName("profile")
@Expose
private String photo;
@SerializedName("fn_photo")
@Expose
private String fnPhoto;
@SerializedName("sn_photo")
@Expose
private String snPhoto;

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

public String getFnPhoto() {
return fnPhoto;
}

public void setFnPhoto(String fnPhoto) {
this.fnPhoto = fnPhoto;
}

public String getSnPhoto() {
return snPhoto;
}

public void setSnPhoto(String snPhoto) {
this.snPhoto = snPhoto;
}

}


