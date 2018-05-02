package com.batya.allerria.yphoto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Gallery {

    @SerializedName("hits")
    @Expose
    private List<Image> images = null;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
