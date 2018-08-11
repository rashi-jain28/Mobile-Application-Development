package com.example.rashi.hw04;

/**
 * Created by Rashi on 2/24/2018.
 */

public class Items {
    String title;
    String description;
    String publishedAt;
    String imageURL;
    String link;

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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return title+ "\t"+ description+ "\t"+publishedAt+ "\t"+imageURL;
    }
}
