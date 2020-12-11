package com.example.indexing;

import java.util.Arrays;

public class Post {
    private String id, shortcode, type, display_url, thumbnail_src, description;
    private OwnerData owner;
    private boolean is_video;
    private long comments, likes, views;
    private long taken_at_timestamp;

    private String[] hashtags, mentions;

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", shortcode='" + shortcode + '\'' +
                ", type='" + type + '\'' +
                ", display_url='" + display_url + '\'' +
                ", thumbnail_src='" + thumbnail_src + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", is_video=" + is_video +
                ", comments=" + comments +
                ", likes=" + likes +
                ", views=" + views +
                ", taken_at_timestamp=" + taken_at_timestamp +
                ", hashtags=" + Arrays.toString(hashtags) +
                ", mentions=" + Arrays.toString(mentions) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getThumbnail_src() {
        return thumbnail_src;
    }

    public void setThumbnail_src(String thumbnail_src) {
        this.thumbnail_src = thumbnail_src;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OwnerData getOwner() {
        return owner;
    }

    public void setOwner(OwnerData owner) {
        this.owner = owner;
    }

    public boolean isIs_video() {
        return is_video;
    }

    public void setIs_video(boolean is_video) {
        this.is_video = is_video;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getTaken_at_timestamp() {
        return taken_at_timestamp;
    }

    public void setTaken_at_timestamp(long taken_at_timestamp) {
        this.taken_at_timestamp = taken_at_timestamp;
    }

    public String[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(String[] hashtags) {
        this.hashtags = hashtags;
    }

    public String[] getMentions() {
        return mentions;
    }

    public void setMentions(String[] mentions) {
        this.mentions = mentions;
    }
}

