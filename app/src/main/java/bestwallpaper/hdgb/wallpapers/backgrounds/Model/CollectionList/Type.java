package bestwallpaper.hdgb.wallpapers.backgrounds.Model.CollectionList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Type implements Serializable {

    @SerializedName("pretty_slug")
    private String prettySlug;

    @SerializedName("slug")
    private String slug;

    public String getPrettySlug() {
        return prettySlug;
    }

    public void setPrettySlug(String prettySlug) {
        this.prettySlug = prettySlug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}