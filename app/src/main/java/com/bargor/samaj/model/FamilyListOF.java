package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyListOF {

@SerializedName("family_list")
@Expose
private List<FamilyList> familyList = null;

public List<FamilyList> getFamilyList() {
return familyList;
}

public void setFamilyList(List<FamilyList> familyList) {
this.familyList = familyList;
}

}