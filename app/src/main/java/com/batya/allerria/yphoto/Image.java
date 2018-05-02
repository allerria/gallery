package com.batya.allerria.yphoto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("largeImageURL")
    @Expose
    private String largeImageURL;
    @SerializedName("webformatURL")
    @Expose
    private String webformatURL;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("previewURL")
    @Expose
    private String previewURL;

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

}
