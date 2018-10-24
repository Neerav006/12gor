package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictList {

@SerializedName("get_list")
@Expose
private List<GetList> getList = null;

public List<GetList> getGetList() {
return getList;
}

public void setGetList(List<GetList> getList) {
this.getList = getList;
}

}