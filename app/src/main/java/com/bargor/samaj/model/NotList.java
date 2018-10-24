package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotList {

@SerializedName("name")
@Expose
private String name;
@SerializedName("link")
@Expose
private String link;
@SerializedName("date")
@Expose
private String date;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

}