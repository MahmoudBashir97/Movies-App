package com.mahmoud.bashir.movies_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mahmoud.bashir.movies_app.R;
import com.mahmoud.bashir.movies_app.adapters.TVShowsAdapter;
import com.mahmoud.bashir.movies_app.databinding.ActivityMainBinding;
import com.mahmoud.bashir.movies_app.listeners.TVShowsListener;
import com.mahmoud.bashir.movies_app.models.TVShow;
import com.mahmoud.bashir.movies_app.viewmodels.MostPopularTvShowsViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private MostPopularTvShowsViewModel mostPopularTvShowsViewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TVShow> tvShowList = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();



    }

    private void doInitialization(){
        activityMainBinding.recTvShow.setHasFixedSize(true);
        mostPopularTvShowsViewModel = ViewModelProviders.of(this).get(MostPopularTvShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShowList,this);
        activityMainBinding.recTvShow.setAdapter(tvShowsAdapter);
        activityMainBinding.recTvShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.recTvShow.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage +=1;
                        getMostPopularTvShows();
                    }
                }
            }
        });

        activityMainBinding.imgWatchlist.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),WatchListActivity.class));
        });
        getMostPopularTvShows();

    }

    private void getMostPopularTvShows() {
        toggleLoading();
        mostPopularTvShowsViewModel.getMostPopularTVShows(currentPage).observe(this,
                response ->{
                        //Toast.makeText(this, "Total Pages " + response.getTotal_pages(), Toast.LENGTH_SHORT).show();
                    toggleLoading();
                    totalAvailablePages = response.getTotal_pages();
                        if (response != null){
                            if (response.getTv_shows() != null){
                                int oldcount = tvShowList.size();

                                tvShowList.addAll(response.getTv_shows());
                                tvShowsAdapter.notifyItemRangeInserted(oldcount,tvShowList.size());
                            }
                        }
        });
    }

    private void toggleLoading(){
        if (currentPage ==1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            }else {
                activityMainBinding.setIsLoading(true);
            }
        }else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent i = new Intent(getApplicationContext(),TVShowDetails_Activity.class);
        i.putExtra("tvShow", tvShow);
        startActivity(i);

    }
}
