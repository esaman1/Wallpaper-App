package bestwallpaper.hdgb.wallpapers.backgrounds.Model.PhotographerList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Urls implements Serializable {

    @SerializedName("small")
    private String small;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("raw")
    private String raw;

    @SerializedName("regular")
    private String regular;

    @SerializedName("full")
    private String full;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    @Override
    public String toString() {
        return
                "Urls{" +
                        "small = '" + small + '\'' +
                        ",thumb = '" + thumb + '\'' +
                        ",raw = '" + raw + '\'' +
                        ",regular = '" + regular + '\'' +
                        ",full = '" + full + '\'' +
                        "}";
    }
}