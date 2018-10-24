package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BirthDayList {

@SerializedName("bod")
@Expose
private List<Bod> bod = null;

public List<Bod> getBod() {
return bod;
}

public void setBod(List<Bod> bod) {
this.bod = bod;
}

}