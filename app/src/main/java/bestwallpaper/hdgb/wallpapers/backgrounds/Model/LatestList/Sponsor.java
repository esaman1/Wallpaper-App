package bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sponsor implements Serializable {

    @SerializedName("total_photos")
    private int totalPhotos;

    @SerializedName("accepted_tos")
    private boolean acceptedTos;

    @SerializedName("twitter_username")
    private String twitterUsername;

    @SerializedName("last_name")
    private Object lastName;

    @SerializedName("bio")
    private String bio;

    @SerializedName("total_likes")
    private int totalLikes;

    @SerializedName("portfolio_url")
    private String portfolioUrl;

    @SerializedName("profile_image")
    private ProfileImage profileImage;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private Object location;

    @SerializedName("links")
    private Links links;

    @SerializedName("total_collections")
    private int totalCollections;

    @SerializedName("id")
    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("instagram_username")
    private String instagramUsername;

    @SerializedName("username")
    private String username;

    @Override
    public String toString() {

        String str;
        str = "{totalPhotos :" + totalPhotos + ",acceptedTos :" + acceptedTos + ",twitterUsername :" + twitterUsername
                + ",lastName :" + lastName + ",bio :" + bio + ",totalLikes :" + totalLikes
                + ",portfolioUrl :" + portfolioUrl + ",profileImage :" + profileImage.toString() + ",updatedAt :" + updatedAt
                + ",name :" + name + ",location :" + location + ",links :" + links.toString()
                + ",totalCollections :" + totalCollections + ",id :" + id + ",firstName :" + firstName
                + ",instagramUsername :" + instagramUsername + ",username :" + username
                + "}";
        return str;

    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public boolean isAcceptedTos() {
        return acceptedTos;
    }

    public void setAcceptedTos(boolean acceptedTos) {
        this.acceptedTos = acceptedTos;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}