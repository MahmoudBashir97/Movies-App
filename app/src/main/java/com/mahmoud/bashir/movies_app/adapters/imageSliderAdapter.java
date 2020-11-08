package com.mahmoud.bashir.movies_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.movies_app.R;
import com.mahmoud.bashir.movies_app.databinding.ItemContainerSliderImgBinding;

public class imageSliderAdapter extends RecyclerView.Adapter<imageSliderAdapter.ViewHolder> {

    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    public imageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImgBinding SliderImgBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_slider_img,parent,false
        );
        return new ViewHolder(SliderImgBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindSliderImage(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerSliderImgBinding itemContainerSliderImgBinding;

        public ViewHolder( ItemContainerSliderImgBinding itemContainerSliderImgBinding) {
            super(itemContainerSliderImgBinding.getRoot());
            this.itemContainerSliderImgBinding=itemContainerSliderImgBinding;

        }

        public void bindSliderImage(String imgURL){
            itemContainerSliderImgBinding.setImageURL(imgURL);
        }
    }
}
