package com.mahmoud.bashir.movies_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mahmoud.bashir.movies_app.models.TVShow;
import com.mahmoud.bashir.movies_app.room.TVShowDatabase;

import java.util.List;

import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {
    private TVShowDatabase database;

    public WatchListViewModel(@NonNull Application application) {
        super(application);

        database =TVShowDatabase.getTVShowDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchList(){
        return database.tvShowDao().getWatchList();
    }
}
