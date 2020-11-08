package com.mahmoud.bashir.movies_app.responses;

import com.google.gson.annotations.SerializedName;
import com.mahmoud.bashir.movies_app.models.TVShow;

import java.util.List;

public class TVShowResponse {
    @SerializedName("page")
    int page;
    @SerializedName("pages")
    int total_pages;
    @SerializedName("tv_shows")
    List<TVShow> tv_shows;

    public TVShowResponse() {
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<TVShow> getTv_shows() {
        return tv_shows;
    }
}
