package bestwallpaper.hdgb.wallpapers.backgrounds.Model.CollectionList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsItem implements Serializable {

    @SerializedName("featured")
    private boolean featured;

    @SerializedName("private")
    private boolean jsonMemberPrivate;

    @SerializedName("cover_photo")
    private CoverPhoto coverPhoto;

    @SerializedName("total_photos")
    private int totalPhotos;

    @SerializedName("share_key")
    private String shareKey;

    @SerializedName("description")
    private Object description;

    @SerializedName("title")
    private String title;

    @SerializedName("tags")
    private List<TagsItem> tags;

    @SerializedName("preview_photos")
    private List<PreviewPhotosItem> previewPhotos;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("curated")
    private boolean curated;

    @SerializedName("last_collected_at")
    private String lastCollectedAt;

    @SerializedName("links")
    private Links links;

    @SerializedName("id")
    private String id;

    @SerializedName("published_at")
    private String publishedAt;

    @SerializedName("user")
    private User user;

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isJsonMemberPrivate() {
        return jsonMemberPrivate;
    }

    public void setJsonMemberPrivate(boolean jsonMemberPrivate) {
        this.jsonMemberPrivate = jsonMemberPrivate;
    }

    public CoverPhoto getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhoto coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TagsItem> getTags() {
        return tags;
    }

    public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }

    public List<PreviewPhotosItem> getPreviewPhotos() {
        return previewPhotos;
    }

    public void setPreviewPhotos(List<PreviewPhotosItem> previewPhotos) {
        this.previewPhotos = previewPhotos;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isCurated() {
        return curated;
    }

    public void setCurated(boolean curated) {
        this.curated = curated;
    }

    public String getLastCollectedAt() {
        return lastCollectedAt;
    }

    public void setLastCollectedAt(String lastCollectedAt) {
        this.lastCollectedAt = lastCollectedAt;
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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}