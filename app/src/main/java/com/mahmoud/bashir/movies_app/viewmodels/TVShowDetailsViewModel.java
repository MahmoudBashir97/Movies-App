package com.mahmoud.bashir.movies_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoud.bashir.movies_app.repositories.TVShowDetailsRepository;
import com.mahmoud.bashir.movies_app.responses.TVShowDetailsResponse;

public class TVShowDetailsViewModel extends ViewModel {

    private TVShowDetailsRepository repository;

    public TVShowDetailsViewModel(){
        repository = new TVShowDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return repository.getTVShowDetails(tvShowId);
    }
}
