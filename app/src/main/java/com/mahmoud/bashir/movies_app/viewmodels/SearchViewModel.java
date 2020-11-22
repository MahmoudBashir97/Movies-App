package com.mahmoud.bashir.movies_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoud.bashir.movies_app.repositories.SearchTVShowrepository;
import com.mahmoud.bashir.movies_app.responses.TVShowDetailsResponse;
import com.mahmoud.bashir.movies_app.responses.TVShowResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowrepository searchTVShowrepository;

    public SearchViewModel(){
        searchTVShowrepository = new SearchTVShowrepository();
    }

    public LiveData<TVShowResponse> searchTVShow(String query, int page){
        return searchTVShowrepository.searchTVShow(query,page);
    }
}
