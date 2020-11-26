package com.mahmoud.bashir.movies_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mahmoud.bashir.movies_app.R;

import com.mahmoud.bashir.movies_app.adapters.EpisodesAdapter;
import com.mahmoud.bashir.movies_app.adapters.imageSliderAdapter;
import com.mahmoud.bashir.movies_app.databinding.ActivityMainBinding;
import com.mahmoud.bashir.movies_app.databinding.ActivityTVShowDetailsBinding;
import com.mahmoud.bashir.movies_app.databinding.LayoutEpisodesBottomSheetBinding;
import com.mahmoud.bashir.movies_app.models.TVShow;
import com.mahmoud.bashir.movies_app.responses.TVShowDetailsResponse;
import com.mahmoud.bashir.movies_app.utilities.TempDataHolder;
import com.mahmoud.bashir.movies_app.viewmodels.TVShowDetailsViewModel;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetails_Activity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding actBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TVShow tvShow;
    private Boolean isTVShowAvailableInWatchlist=false;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details_);
        actBinding.imgback.setOnClickListener(view -> {
            onBackPressed();
        });
        tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        String i = String.valueOf(tvShow.getId());

            Toast.makeText(this, "id >>>> "+i, Toast.LENGTH_LONG).show();

        checkTVShowAvailableInWatchlist();
        doInitialization();

    }

    private void doInitialization() {
        tvShowDetailsViewModel = ViewModelProviders.of(this).get(TVShowDetailsViewModel.class);
        getTVShowDetails();
    }

    private void checkTVShowAvailableInWatchlist(){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvShowDetailsViewModel.getTvShowFromWatchlist(29560)
        .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        .subscribe( tvShow ->{
            isTVShowAvailableInWatchlist = true;
            actBinding.imgwatchlist.setImageResource(R.drawable.ic_check);
            compositeDisposable.dispose();
        }));
    }

    private void getTVShowDetails() {
        actBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(this,
                response -> {
                    actBinding.setIsLoading(false);
                    // Toast.makeText(this, ""+response.getTvShowDetails().getUrl(), Toast.LENGTH_SHORT).show();
                    if (response.getTvShowDetails() != null) {
                        if (response.getTvShowDetails().getPictures() != null) {
                            loadingSliderImages(response.getTvShowDetails().getPictures());
                        }
                        actBinding.setTvShowImageURL(
                                response.getTvShowDetails().getImage_path()
                        );
                        actBinding.imageTVShow.setVisibility(View.VISIBLE);
                        actBinding.setDescription(
                                String.valueOf(HtmlCompat.fromHtml(
                                        response.getTvShowDetails().getDescription(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                ))
                        );
                        actBinding.txtDescription.setVisibility(View.VISIBLE);
                        actBinding.txtReadMore.setVisibility(View.VISIBLE);
                        actBinding.txtReadMore.setOnClickListener(view -> {
                            if (actBinding.txtReadMore.getText().toString().equals("Read More")) {
                                actBinding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                                actBinding.txtDescription.setEllipsize(null);
                                actBinding.txtReadMore.setText(R.string.read_less);
                            } else {
                                actBinding.txtDescription.setMaxLines(4);
                                actBinding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                                actBinding.txtReadMore.setText(R.string.read_more);
                            }
                        });
                        actBinding.setRating(
                                String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        Double.parseDouble(response.getTvShowDetails().getRating()))
                        );

                        if (response.getTvShowDetails().getGenres() != null) {
                            actBinding.setGenre(response.getTvShowDetails().getGenres()[0]);
                        } else {
                            actBinding.setGenre("N/A");
                        }
                        actBinding.setRuntime(response.getTvShowDetails().getRuntime() + " Min");
                        actBinding.viewDividers.setVisibility(View.VISIBLE);
                        actBinding.layoutMisc.setVisibility(View.VISIBLE);
                        actBinding.viewDivider2.setVisibility(View.VISIBLE);

                        actBinding.btnWebsite.setOnClickListener(view -> {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(response.getTvShowDetails().getUrl()));
                            startActivity(i);
                        });
                        actBinding.btnWebsite.setVisibility(View.VISIBLE);
                        actBinding.btnEpisodes.setVisibility(View.VISIBLE);
                        actBinding.btnEpisodes.setOnClickListener(view -> {
                            if (episodesBottomSheetDialog == null) {
                                episodesBottomSheetDialog = new BottomSheetDialog(TVShowDetails_Activity.this);
                                layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                                        LayoutInflater.from(TVShowDetails_Activity.this),
                                        R.layout.layout_episodes_bottom_sheet,
                                        findViewById(R.id.episodesContainer),
                                        false
                                );
                                episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                                layoutEpisodesBottomSheetBinding.recEpisodes.setAdapter(
                                        new EpisodesAdapter(response.getTvShowDetails().getEpisodes())
                                );
                                layoutEpisodesBottomSheetBinding.txtTitle.setText("Episodes | " +tvShow.getName());
                                layoutEpisodesBottomSheetBinding.imgClose.setOnClickListener(view1 -> {
                                    episodesBottomSheetDialog.dismiss();
                                });
                            }

                            //---- optional section start ---//
                            FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                                    com.google.android.material.R.id.design_bottom_sheet
                            );

                            if (frameLayout != null) {
                                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                            // end
                            episodesBottomSheetDialog.show();

                        });

                        // add to my watchlist
                        actBinding.imgwatchlist.setOnClickListener(view -> {

                            {
                                CompositeDisposable compositeDisposable = new CompositeDisposable();
                                if (isTVShowAvailableInWatchlist){
                                    compositeDisposable.add(tvShowDetailsViewModel.removeTVShowFromWatchlist(tvShow)
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(()->{
                                        isTVShowAvailableInWatchlist = false;
                                        TempDataHolder.IS_WATCHLIST_UPDATED=true;
                                        actBinding.imgwatchlist.setImageResource(R.drawable.ic_watch_24);
                                        Toast.makeText(this, "Removed from watchList", Toast.LENGTH_SHORT).show();
                                        compositeDisposable.dispose();
                                    }));
                                }else{
                                    compositeDisposable.add(tvShowDetailsViewModel.addToWatchList(tvShow)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                TempDataHolder.IS_WATCHLIST_UPDATED=true;
                                                actBinding.imgwatchlist.setImageResource(R.drawable.ic_check);
                                                Toast.makeText(this, "Added to watchList", Toast.LENGTH_SHORT).show();
                                                compositeDisposable.dispose();
                                            })
                                    );
                                }
                            }

                        });

                        actBinding.imgwatchlist.setVisibility(View.VISIBLE);
                        loadBasicTVShowDetails();
                    }
                });
    }

    private void loadingSliderImages(String[] sliderImgs) {
        actBinding.sliderViewPager2.setOffscreenPageLimit(1);
        actBinding.sliderViewPager2.setAdapter(new imageSliderAdapter(sliderImgs));
        actBinding.sliderViewPager2.setVisibility(View.VISIBLE);
        actBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImgs.length);
        actBinding.sliderViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    public void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            actBinding.linSliderIndicators.addView(indicators[i]);
        }

        actBinding.linSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);

    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = actBinding.linSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) actBinding.linSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)
                );
            }
        }
    }

    private void loadBasicTVShowDetails() {
        actBinding.setTvShowName(tvShow.getName());
        actBinding.setNetworkCountry(tvShow.getNetwork() + " (" + tvShow.getCountry() + ")");
        actBinding.setStatus(tvShow.getStatus());
        actBinding.setStartedDate(tvShow.getStart_date());

        actBinding.txtName.setVisibility(View.VISIBLE);
        actBinding.txtNetworkCountry.setVisibility(View.VISIBLE);
        actBinding.txtStatus.setVisibility(View.VISIBLE);
        actBinding.txtStarted.setVisibility(View.VISIBLE);

    }
}