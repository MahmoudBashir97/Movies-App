package com.mahmoud.bashir.movies_app.responses;

import com.google.gson.annotations.SerializedName;
import com.mahmoud.bashir.movies_app.models.TVShowDetails;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
