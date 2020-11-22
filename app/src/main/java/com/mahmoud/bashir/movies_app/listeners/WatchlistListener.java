package com.mahmoud.bashir.movies_app.listeners;

import com.mahmoud.bashir.movies_app.models.TVShow;

public interface WatchlistListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatclist(TVShow tvShow,int position);
}
