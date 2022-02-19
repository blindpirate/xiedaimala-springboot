package hello.entity;

import java.time.Instant;

public class Blog {
    private Integer id;
    private User user;
    private String title;
    private String description;
    private String content;
    private boolean atIndex;
    private Instant updatedAt;
    private Instant createdAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAtIndex() {
        return atIndex;
    }

    public void setAtIndex(boolean atIndex) {
        this.atIndex = atIndex;
    }

    public Integer getUserId() {
        return user == null ? null : user.getId();
    }
}
