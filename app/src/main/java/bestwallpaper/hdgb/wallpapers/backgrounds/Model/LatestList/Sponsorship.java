package bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Sponsorship implements Serializable {

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

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getTaglineUrl() {
        return taglineUrl;
    }

    public void setTaglineUrl(String taglineUrl) {
        this.taglineUrl = taglineUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<String> getImpressionUrls() {
        return impressionUrls;
    }

    public void setImpressionUrls(List<String> impressionUrls) {
        this.impressionUrls = impressionUrls;
    }

    @Override
    public String toString() {

        String str;
        str = "{sponsor :" + sponsor.toString() + ",taglineUrl :" + taglineUrl + ",tagline :" + tagline
                + ",impressionUrls :" + impressionUrls
                + "}";
        return str;

    }
}