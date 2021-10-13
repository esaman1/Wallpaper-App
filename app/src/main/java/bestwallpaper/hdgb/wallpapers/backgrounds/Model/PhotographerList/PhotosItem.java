package bestwallpaper.hdgb.wallpapers.backgrounds.Model.PhotographerList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhotosItem implements Serializable {

    @SerializedName("urls")
    private Urls urls;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("blur_hash")
    private String blurHash;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBlurHash() {
        return blurHash;
    }

    public void setBlurHash(String blurHash) {
        this.blurHash = blurHash;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "PhotosItem{" +
                        "urls = '" + urls + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",blur_hash = '" + blurHash + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}