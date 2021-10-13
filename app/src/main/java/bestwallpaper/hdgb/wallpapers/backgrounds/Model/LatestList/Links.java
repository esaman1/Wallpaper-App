package bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Links implements Serializable {

    @SerializedName("followers")
    private String followers;

    @SerializedName("portfolio")
    private String portfolio;

    @SerializedName("following")
    private String following;

    @SerializedName("self")
    private String self;

    @SerializedName("html")
    private String html;

    @SerializedName("photos")
    private String photos;

    @SerializedName("likes")
    private String likes;

    @SerializedName("download")
    private String download;

    @SerializedName("download_location")
    private String downloadLocation;

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    @Override
    public String toString() {
        String str;
        str = "{followers :" + followers + ",portfolio :" + portfolio + ",following :" + following
                + ",self :" + self + ",html :" + html + ",photos :" + photos
                + ",likes :" + likes + ",download :" + download + ",downloadLocation :" + downloadLocation
                + "}";
        return str;

    }

}