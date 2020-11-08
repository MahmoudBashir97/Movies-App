package com.mahmoud.bashir.movies_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoud.bashir.movies_app.repositories.MostPopularTVShowsRepository;
import com.mahmoud.bashir.movies_app.responses.TVShowResponse;

public class MostPopularTvShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository repository;

    public MostPopularTvShowsViewModel(){
        repository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){
        return repository.getMostPopularTVShows(page);
    }
}
