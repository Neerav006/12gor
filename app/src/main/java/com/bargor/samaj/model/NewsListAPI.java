package com.bargor.samaj.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsListAPI {

@SerializedName("newslist")
@Expose
private List<Newslist> newslist = null;

public List<Newslist> getNewslist() {
return newslist;
}

public void setNewslist(List<Newslist> newslist) {
this.newslist = newslist;
}

}