package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VillagelistOF {

@SerializedName("village_list")
@Expose
private List<VillageList> villageList = null;

public List<VillageList> getVillageList() {
return villageList;
}

public void setVillageList(List<VillageList> villageList) {
this.villageList = villageList;
}

}