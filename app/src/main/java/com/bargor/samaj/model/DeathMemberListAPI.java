package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeathMemberListAPI {

@SerializedName("deathlist")
@Expose
private List<Deathlist> deathlist = null;

public List<Deathlist> getDeathlist() {
return deathlist;
}

public void setDeathlist(List<Deathlist> deathlist) {
this.deathlist = deathlist;
}

}