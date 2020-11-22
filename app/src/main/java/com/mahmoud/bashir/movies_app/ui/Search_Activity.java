package com.mahmoud.bashir.movies_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.mahmoud.bashir.movies_app.R;
import com.mahmoud.bashir.movies_app.adapters.TVShowsAdapter;
import com.mahmoud.bashir.movies_app.databinding.ActivitySearchBinding;
import com.mahmoud.bashir.movies_app.listeners.TVShowsListener;
import com.mahmoud.bashir.movies_app.models.TVShow;
import com.mahmoud.bashir.movies_app.responses.TVShowResponse;
import com.mahmoud.bashir.movies_app.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Search_Activity extends AppCompatActivity implements TVShowsListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    List<TVShow> tvShows = new ArrayList<>();
    TVShowsAdapter tvShowsAdapter;
    int currentPage = 1;
    int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_);
        doInitializtion();
    }

    private void toggleLoading(){
        if (currentPage ==1){
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()){
                activitySearchBinding.setIsLoading(false);
            }else {
                activitySearchBinding.setIsLoading(true);
            }
        }else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()){
                activitySearchBinding.setIsLoadingMore(false);
            }else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }

    private void doInitializtion (){
        activitySearchBinding.imgback.setOnClickListener(view -> {
            onBackPressed();
        });
        activitySearchBinding.tvShowRec.setHasFixedSize(true);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activitySearchBinding.tvShowRec.setAdapter(tvShowsAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer !=null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                   currentPage = 1 ;
                                   totalAvailablePages = 1;
                                   searchTVShow(editable.toString());
                                }
                            });
                        }
                    },800);
                }else {
                    tvShows.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });

        activitySearchBinding.tvShowRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!activitySearchBinding.tvShowRec.canScrollVertically(1)){
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                        if (currentPage < totalAvailablePages){
                            currentPage +=1;
                            searchTVShow(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }

    private void searchTVShow(String query){
        toggleLoading();
        viewModel.searchTVShow(query,currentPage).observe(this, new Observer<TVShowResponse>() {
            @Override
            public void onChanged(TVShowResponse tvShowResponse) {
                toggleLoading();
                if (tvShowResponse != null){
                    totalAvailablePages = tvShowResponse.getTotal_pages();
                    if (tvShowResponse.getTv_shows() !=null){
                        int oldCount = tvShows.size();
                        tvShows.addAll(tvShowResponse.getTv_shows());
                        tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                    }
                }
            }
        });
     //   activitySearchBinding.
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent i = new Intent(getApplicationContext(),TVShowDetails_Activity.class);
        i.putExtra("tvShow", tvShow);
        startActivity(i);
    }
}