package challenge.dto;

public class Shot {

    private Long id;
    private String title;
    private int height;
    private int width;
    private Images images;
    private User user;
    private String created_at;
    private Long views_count;
    private Long likes_count;
    private Long buckets_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getViews_count() {
        return views_count;
    }

    public void setViews_count(Long views_count) {
        this.views_count = views_count;
    }

    public Long getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(Long likes_count) {
        this.likes_count = likes_count;
    }

    public Long getBuckets_count() {
        return buckets_count;
    }

    public void setBuckets_count(Long buckets_count) {
        this.buckets_count = buckets_count;
    }
}
