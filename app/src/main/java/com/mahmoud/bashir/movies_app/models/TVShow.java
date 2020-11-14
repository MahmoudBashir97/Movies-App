package com.mahmoud.bashir.movies_app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "tvshows")
public class TVShow implements Serializable {

    @PrimaryKey
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage_thumbnail_path(String image_thumbnail_path) {
        this.image_thumbnail_path = image_thumbnail_path;
    }
}
