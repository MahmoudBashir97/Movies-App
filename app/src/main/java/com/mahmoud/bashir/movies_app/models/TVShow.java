package com.mahmoud.bashir.movies_app.models;

import com.google.gson.annotations.SerializedName;

public class TVShow {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("permalink")
    String permalink;
    @SerializedName("start_date")
    String start_date;
    @SerializedName("end_date")
    String end_date;
    @SerializedName("country")
    String country;
    @SerializedName("network")
    String network;
    @SerializedName("status")
    String status;
    @SerializedName("image_thumbnail_path")
    String image_thumbnail_path;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }
}
