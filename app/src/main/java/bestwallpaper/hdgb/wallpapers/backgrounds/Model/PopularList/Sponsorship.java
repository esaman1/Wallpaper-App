package bestwallpaper.hdgb.wallpapers.backgrounds.Model.PopularList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sponsorship {

    @SerializedName("sponsor")
    private Sponsor sponsor;

    @SerializedName("tagline_url")
    private String taglineUrl;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("impression_urls")
    private List<String> impressionUrls;

    public Sponsor getSponsor() {
        return sponsor;
    }

    public String getTaglineUrl() {
        return taglineUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public List<String> getImpressionUrls() {
        return impressionUrls;
    }
}