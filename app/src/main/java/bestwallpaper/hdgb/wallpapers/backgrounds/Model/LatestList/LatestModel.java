package bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class LatestModel implements Serializable {

    @SerializedName("current_user_collections")
    private List<Object> currentUserCollections;

    @SerializedName("color")
    private String color;

    @SerializedName("sponsorship")
    private Sponsorship sponsorship;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("description")
    private Object description;

    @SerializedName("liked_by_user")
    private boolean likedByUser;

    @SerializedName("urls")
    private Urls urls;

    @SerializedName("alt_description")
    private String altDescription;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("width")
    private int width;

    @SerializedName("blur_hash")
    private String blurHash;

    @SerializedName("links")
    private Links links;

    @SerializedName("id")
    private String id;

    @SerializedName("categories")
    private List<Object> categories;

    @SerializedName("promoted_at")
    private Object promotedAt;

    @SerializedName("user")
    private User user;

    @SerializedName("height")
    private int height;

    @SerializedName("likes")
    private int likes;

    public List<Object> getCurrentUserCollections() {
        return currentUserCollections;
    }

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Sponsorship getSponsorship() {
        return sponsorship;
    }

    public void setSponsorship(Sponsorship sponsorship) {
        this.sponsorship = sponsorship;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public String getAltDescription() {
        return altDescription;
    }

    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getBlurHash() {
        return blurHash;
    }

    public void setBlurHash(String blurHash) {
        this.blurHash = blurHash;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public Object getPromotedAt() {
        return promotedAt;
    }

    public void setPromotedAt(Object promotedAt) {
        this.promotedAt = promotedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        String str;
        str = "{id :" + id + ",created_at :" + createdAt + ",updatedAt :" + updatedAt
                + ",promotedAt :" + promotedAt + ",width :" + width + ",height :" + height
                + ",color :" + color + ",blurHash :" + blurHash + ",description :" + description
                + ",altDescription :" + altDescription + ",urls :" + urls.toString() + ",links :" + links.toString()
                + ",categories :" + categories + ",likes :" + likes + ",likedByUser :" + likedByUser
                + ",currentUserCollections :" + currentUserCollections + ",sponsorship :" + sponsorship
                + ",user :" + user.toString() + "}";
        return str;

    }

}