package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gor {

@SerializedName("gor_list")
@Expose
private List<GorList> gorList = null;

public List<GorList> getGorList() {
return gorList;
}

public void setGorList(List<GorList> gorList) {
this.gorList = gorList;
}

}