package bestwallpaper.hdgb.wallpapers.backgrounds.Model.PhotographerList;

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

    @Override
    public String toString() {
        return
                "Links{" +
                        "followers = '" + followers + '\'' +
                        ",portfolio = '" + portfolio + '\'' +
                        ",following = '" + following + '\'' +
                        ",self = '" + self + '\'' +
                        ",html = '" + html + '\'' +
                        ",photos = '" + photos + '\'' +
                        ",likes = '" + likes + '\'' +
                        "}";
    }
}