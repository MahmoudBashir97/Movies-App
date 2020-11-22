package com.mahmoud.bashir.movies_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mahmoud.bashir.movies_app.R;
import com.mahmoud.bashir.movies_app.adapters.WatchlistAdapter;
import com.mahmoud.bashir.movies_app.databinding.ActivityWatchListBinding;
import com.mahmoud.bashir.movies_app.listeners.WatchlistListener;
import com.mahmoud.bashir.movies_app.models.TVShow;
import com.mahmoud.bashir.movies_app.utilities.TempDataHolder;
import com.mahmoud.bashir.movies_app.viewmodels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel watchListViewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();



    }

    private void doInitialization() {
        watchListViewModel = ViewModelProviders.of(this).get(WatchListViewModel.class);
        activityWatchListBinding.imgback.setOnClickListener(view -> {
            onBackPressed();
        });
        watchlist = new ArrayList<>();
        loadWatchList();
    }

    private void loadWatchList() {
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    if (watchlist.size() > 0){
                        watchlist.clear();
                    }
                    watchlist.addAll(tvShows);
                    watchlistAdapter = new WatchlistAdapter(watchlist,this);
                    activityWatchListBinding.recWatchList.setAdapter(watchlistAdapter);
                    activityWatchListBinding.recWatchList.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATED){
            loadWatchList();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent i = new Intent(getApplicationContext(),TVShowDetails_Activity.class);
        i.putExtra("tvShow",tvShow);
        startActivity(i);
    }

    @Override
    public void removeTVShowFromWatclist(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.removeTVShowFromWatchlist(tvShow)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe( () -> {
            watchlist.remove(position);
            watchlistAdapter.notifyItemRemoved(position);
            watchlistAdapter.notifyItemRangeChanged(position,watchlistAdapter.getItemCount());
            compositeDisposable.dispose();
        } )
        );
    }
}