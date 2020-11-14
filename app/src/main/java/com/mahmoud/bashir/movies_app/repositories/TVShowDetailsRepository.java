package com.mahmoud.bashir.movies_app.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mahmoud.bashir.movies_app.network.ApiClient;
import com.mahmoud.bashir.movies_app.network.ApiService;
import com.mahmoud.bashir.movies_app.responses.TVShowDetailsResponse;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private ApiService apiService;

    public TVShowDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String TVShowId){
        MutableLiveData<TVShowDetailsResponse>  data = new MutableLiveData<>();

        apiService.getTVShowDetails(TVShowId).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull  Call<TVShowDetailsResponse> call,@NonNull Response<TVShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailsResponse> call,@NonNull Throwable t) {
                t.getMessage();
            }
        });

        return data;
    }
}
