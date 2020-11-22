package com.mahmoud.bashir.movies_app.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mahmoud.bashir.movies_app.models.TVShow;
import com.mahmoud.bashir.movies_app.repositories.TVShowDetailsRepository;
import com.mahmoud.bashir.movies_app.responses.TVShowDetailsResponse;
import com.mahmoud.bashir.movies_app.room.TVShowDatabase;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository repository;
    private TVShowDatabase tvShowDatabase;

    public TVShowDetailsViewModel(Application application){
        super(application);

        repository = new TVShowDetailsRepository();
        tvShowDatabase = TVShowDatabase.getTVShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return repository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow){
        return tvShowDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTvShowFromWatchlist(String id){
        return tvShowDatabase.tvShowDao().getTvShowFromWatchlist(id);
    }

    public Completable removeTVShowFromWatchlist(TVShow tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
