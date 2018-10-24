package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notification {

@SerializedName("not_list")
@Expose
private List<NotList> notList = null;

public List<NotList> getNotList() {
return notList;
}

public void setNotList(List<NotList> notList) {
this.notList = notList;
}

}