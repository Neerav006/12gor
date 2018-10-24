package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

@SerializedName("categorylist")
@Expose
private List<Categorylist> categorylist = null;

public List<Categorylist> getCategorylist() {
return categorylist;
}

public void setCategorylist(List<Categorylist> categorylist) {
this.categorylist = categorylist;
}

}