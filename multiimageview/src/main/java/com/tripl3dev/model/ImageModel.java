package com.tripl3dev.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Rohyme on 4/11/2018.
 */

public class ImageModel implements ImageModelI {
    public String imageUrl;
    public String imageDetails = null;
    public boolean isSelected = false;

    public ImageModel(String imageUrl, String imageDetails) {
        this.imageUrl = imageUrl;
        this.imageDetails = imageDetails;
    }

    public ImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull
    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getImageDetails() {
        return imageDetails;
    }

    @Nullable
    @Override
    public Boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
