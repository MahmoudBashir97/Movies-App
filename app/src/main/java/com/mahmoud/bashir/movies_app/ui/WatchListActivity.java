package com.mahmoud.bashir.movies_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.mahmoud.bashir.movies_app.R;
import com.mahmoud.bashir.movies_app.databinding.ActivityWatchListBinding;
import com.mahmoud.bashir.movies_app.viewmodels.WatchListViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel watchListViewModel;

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
    }

    private void loadWatchList() {
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    Toast.makeText(this, "watchList:" + tvShows.size(), Toast.LENGTH_SHORT).show();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchList();
    }
}