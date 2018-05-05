package com.batya.allerria.yphoto.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

    @SerializedName("hits")
    @Expose
    private List<Image> images = new ArrayList<Image>();

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImages(List<Image> images) {
        this.images.addAll(images);
    }
}
