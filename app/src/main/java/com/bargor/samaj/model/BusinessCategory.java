package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessCategory {

@SerializedName("category_list")
@Expose
private List<BusCategoryList> categoryList = null;

public List<BusCategoryList> getCategoryList() {
return categoryList;
}

public void setCategoryList(List<BusCategoryList> categoryList) {
this.categoryList = categoryList;
}

}