package com.mahmoud.bashir.movies_app.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowDetails {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("permalink")
    String permalink;
    @SerializedName("url")
    String url;
    @SerializedName("description")
    String description;
    @SerializedName("description_source")
    String description_source;
    @SerializedName("start_date")
    String start_date;
    @SerializedName("end_date")
    String end_date;
    @SerializedName("country")
    String country;
    @SerializedName("status")
    String status;
    @SerializedName("runtime")
    int runtime;
    @SerializedName("network")
    String network;
    @SerializedName("youtube_link")
    String youtube_link;
    @SerializedName("image_path")
    String image_path;
    @SerializedName("image_thumbnail_path")
    String image_thumbnail_path;
    @SerializedName("rating")
    String rating;
    @SerializedName("rating_count")
    String rating_count;
    @SerializedName("countdown")
    String countdown;
    @SerializedName("genres")
    String[] genres;
    @SerializedName("pictures")
    String[] pictures;
    @SerializedName("episodes")
    List<Episode> episodes;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getDescription_source() {
        return description_source;
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

    public String getStatus() {
        return status;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getNetwork() {
        return network;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public String getRating() {
        return rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public String getCountdown() {
        return countdown;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
